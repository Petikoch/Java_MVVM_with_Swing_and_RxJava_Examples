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

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * A RxJava {@link rx.Observable.Operator} which takes care to preserve stacktraces across asynchronous
 * {@link rx.Scheduler}-boundaries.
 * <p>
 * This {@link rx.Observable.Operator} is typically placed AFTER {@link Observable#observeOn(Scheduler)} or {@link
 * Observable#subscribeOn(Scheduler)}.
 * <p>
 * See <a href="https://github.com/ReactiveX/RxJava/issues/3521#issuecomment-163571622">RxJava Issue 3521</a> for more
 * background about the topic. This {@link rx.Observable.Operator} can be used as kind of a workaround for this unresolved
 * RxJava issue (concerning RxJava versions <= 1.1.0 and probably also later versions).
 * <p>
 * Advantages: Analysing issues (stacktraces) gets much simpler for asynchronous use cases, since you have a "full" stacktrace<br>
 * Disadvantage: Performance. Creating the internal RuntimeException-object to capture the stacktrace is "not cheap".
 * But since we are operating at an asynchronous boundary, this doesn't add much additional overhead and can probably be neglected
 */
public class PreserveFullStackTraceOperator<T> implements Observable.Operator<T, T> {

    private final RuntimeException asyncOriginStackTraceProvider = new RuntimeException("async origin");
    private final long originThreadId = Thread.currentThread().getId(); // should be "enough" unique. See http://stackoverflow.com/a/591664/1662412

    public static <T> PreserveFullStackTraceOperator<T> preserveFullStackTrace() {
        return new PreserveFullStackTraceOperator<>();
    }

    @Override
    public Subscriber<? super T> call(Subscriber<? super T> child) {
        Subscriber<T> parent = new Subscriber<T>() {

            @Override
            public void onCompleted() {
                child.onCompleted();
            }

            @Override
            public void onError(Throwable throwable) {
                if (Thread.currentThread().getId() != originThreadId) {
                    List<StackTraceElement> originalStackTraceElements = Lists.newArrayList(throwable.getStackTrace());
                    List<StackTraceElement> additionalAsyncOriginStackTraceElements = asList(asyncOriginStackTraceProvider.getStackTrace()).stream()
                            .filter(stackTraceElement -> !PreserveFullStackTraceOperator.class.getName().equals(stackTraceElement.getClassName()))
                            .collect(Collectors.toList());
                    Iterable<StackTraceElement> modifiedStackTraceElements = Iterables.concat(originalStackTraceElements, additionalAsyncOriginStackTraceElements);
                    throwable.setStackTrace(Iterables.toArray(modifiedStackTraceElements, StackTraceElement.class));
                }
                child.onError(throwable);
            }

            @Override
            public void onNext(T t) {
                child.onNext(t);
            }
        };

        child.add(parent);

        return parent;
    }

}