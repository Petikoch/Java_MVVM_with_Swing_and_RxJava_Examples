/**
 * Copyright (c) 2015-2016 Peti Koch
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
import rx.Observable;
import rx.Single;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;

import java.util.concurrent.Callable;

import static ch.petikoch.examples.mvvm_rxjava.utils.PreserveFullStackTraceOperator.preserveFullStackTrace;

/**
 * Something like <a href="https://github.com/ReactiveX/RxJavaAsyncUtil/blob/0.x/src/main/java/rx/util/async/Async.java">RxJavaAsyncUtil</a>
 * but with {@link Single} as return types.
 */
@Deprecated // This is kind of "imperative" and not lazy like typical Rx
public class AsyncUtils {

    /**
     * @see #executeAsync(Callable)
     */
    // experimental
    public static Single<FinishedIndicator> executeAsync(Runnable runnable) {
        return executeAsync(() -> {
            runnable.run();
            return FinishedIndicator.INSTANCE;
        });
    }

    /**
     * Starts immediately the execution of the given Callable with a thread from
     * {@link Schedulers#io()}.
     *
     * The caller can await the execution termination through subscribing to the {@link Single} return value.
     * It's safe to "share" the {@link Single} return value reference and subscribe to it as many times as you want.
     * All subscribers get the result value (or the error) individually.
     *
     * Cancellation? The execution is automatically cancelled when all subscribers do unsubscribe while
     * the execution is still running. The given {@link Callable} will be interrupted.
     *
     * If there is no subscriber ever to the {@link Single} return value, the callable will be executed unobserved.
     * Make sure to have some kind of "exception handling" also for that case (like try-catch-logging blocks or
     * {@link Thread.UncaughtExceptionHandler}) to not "miss" issues.
     *
     * @param callable the code to execute
     * @param <T>      type of result
     * @return Single instance delivering asynchronously the result of the callable
     */
    // experimental
    public static <T> Single<T> executeAsync(Callable<T> callable) {
        AsyncSubject<T> resultSubject = AsyncSubject.create();

	    final Subscription asyncOp = Observable.fromCallable(callable)
			    .subscribeOn(Schedulers.io())
			    .lift(preserveFullStackTrace())
                .subscribe(
                        t -> {
                            resultSubject.onNext(t);
                            resultSubject.onCompleted();
                        },
                        throwable -> {
                            resultSubject.onError(throwable);
                            resultSubject.onCompleted();
                        }
                );

        return resultSubject.doOnUnsubscribe(asyncOp::unsubscribe)
                .share()
                .toSingle();
    }
}

