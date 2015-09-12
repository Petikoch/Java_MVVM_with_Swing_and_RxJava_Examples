/**
 * Copyright 2015 Peti Koch
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.petikoch.examples.mvvm_rxjava.utils;

import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.FinishedIndicator;
import rx.Single;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.ReplaySubject;

import java.util.concurrent.Callable;

public class AsyncUtils {

    // experimental
    public static Single<FinishedIndicator> executeAsync(Runnable runnable) {
        ReplaySubject<FinishedIndicator> finished = ReplaySubject.create();

        final Subscription asyncOp = Single.<FinishedIndicator>create(singleSubscriber -> {
            try {
                runnable.run();
                if (!singleSubscriber.isUnsubscribed()) {
                    singleSubscriber.onSuccess(FinishedIndicator.INSTANCE);
                }
            } catch (Exception e) {
                if (!singleSubscriber.isUnsubscribed()) {
                    singleSubscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe(
                finishedIndicator -> {
                    finished.onNext(finishedIndicator);
                    finished.onCompleted();
                },
                finished::onError
        );

        return finished.share().doOnUnsubscribe(asyncOp::unsubscribe).toSingle();
    }

    // experimental
    public static <T> Single<T> executeAsync(Callable<T> callable) {
        ReplaySubject<T> finished = ReplaySubject.create();

        final Subscription asyncOp = Single.<T>create(singleSubscriber -> {
            try {
                T result = callable.call();
                if (!singleSubscriber.isUnsubscribed()) {
                    singleSubscriber.onSuccess(result);
                }
            } catch (Exception e) {
                if (!singleSubscriber.isUnsubscribed()) {
                    singleSubscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).subscribe(
                finishedIndicator -> {
                    finished.onNext(finishedIndicator);
                    finished.onCompleted();
                },
                finished::onError
        );

        return finished.share().doOnUnsubscribe(asyncOp::unsubscribe).toSingle();
    }

    // experimental
    public static Single<FinishedIndicator> executeLazyAsync(Runnable runnable) {
        return Single.<FinishedIndicator>create(singleSubscriber -> {
            try {
                runnable.run();
                if (!singleSubscriber.isUnsubscribed()) {
                    singleSubscriber.onSuccess(FinishedIndicator.INSTANCE);
                }
            } catch (Exception e) {
                if (!singleSubscriber.isUnsubscribed()) {
                    singleSubscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).toObservable().share().toSingle();
    }

    // experimental
    public static <T> Single<T> executeLazyAsync(Callable<T> callable) {
        return Single.<T>create(singleSubscriber -> {
            try {
                T result = callable.call();
                if (!singleSubscriber.isUnsubscribed()) {
                    singleSubscriber.onSuccess(result);
                }
            } catch (Exception e) {
                if (!singleSubscriber.isUnsubscribed()) {
                    singleSubscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).toObservable().share().toSingle();
    }
}

