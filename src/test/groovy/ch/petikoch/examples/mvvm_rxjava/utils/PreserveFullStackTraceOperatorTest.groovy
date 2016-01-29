/*
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
package ch.petikoch.examples.mvvm_rxjava.utils

import com.jayway.awaitility.Awaitility
import rx.Observable
import rx.observers.TestSubscriber
import rx.schedulers.Schedulers
import spock.lang.Specification

import java.util.concurrent.Callable

import static ch.petikoch.examples.mvvm_rxjava.utils.PreserveFullStackTraceOperator.preserveFullStackTrace

class PreserveFullStackTraceOperatorTest extends Specification {

    def 'operator adds asynchronous origin stack trace elements'() {
        given:
        def asyncPipeline = Observable.fromCallable { throw new IllegalStateException("Boom!") }
                .subscribeOn(Schedulers.io())
        def originalThrowable = getThrowableFromFailingObservable(asyncPipeline)
        assert originalThrowable instanceof IllegalStateException
        assert originalThrowable.getMessage() == 'Boom!'
        assert originalThrowable.getCause() == null

        when:
        def enhancedThrowable = getThrowableFromFailingObservable(
                asyncPipeline.lift(preserveFullStackTrace())
        )

        then:
        enhancedThrowable instanceof IllegalStateException
        enhancedThrowable.getMessage() == 'Boom!'
        enhancedThrowable.stackTrace.length > originalThrowable.stackTrace.length
        enhancedThrowable.stackTrace.any { it.className == this.class.name } == true
        enhancedThrowable.getCause() == null
    }


    def 'operator does not add additional stack trace elements if code is synchronous'() {
        given:
        def syncPipeline = Observable.fromCallable { throw new IllegalStateException("Boom!") }
        def originalThrowable = getThrowableFromFailingObservable(syncPipeline)
        assert originalThrowable instanceof IllegalStateException
        assert originalThrowable.getMessage() == 'Boom!'
        assert originalThrowable.getCause() == null

        when:
        def enhancedThrowable = getThrowableFromFailingObservable(
                syncPipeline.lift(preserveFullStackTrace())
        )

        then:
        enhancedThrowable instanceof IllegalStateException
        enhancedThrowable.getMessage() == 'Boom!'
        enhancedThrowable.stackTrace.length == originalThrowable.stackTrace.length
        enhancedThrowable.getCause() == null
    }

    private Throwable getThrowableFromFailingObservable(Observable observable) {
        TestSubscriber testSubscriber = new TestSubscriber()
        observable.subscribe(testSubscriber)
        Awaitility.await().until({ testSubscriber.getOnErrorEvents().size() == 1 } as Callable<Boolean>)
        return testSubscriber.getOnErrorEvents().get(0)
    }
}