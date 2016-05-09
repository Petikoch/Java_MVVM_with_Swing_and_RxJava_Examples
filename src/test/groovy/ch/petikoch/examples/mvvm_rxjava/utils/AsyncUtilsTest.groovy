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

import ch.petikoch.examples.mvvm_rxjava.TestingClock
import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.FinishedIndicator
import rx.Single
import rx.Subscriber
import rx.observers.TestSubscriber
import spock.lang.Specification
import spock.lang.Timeout

import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

import static com.jayway.awaitility.Awaitility.await

@Timeout(value = 10, unit = TimeUnit.SECONDS)
class AsyncUtilsTest extends Specification {

    def testingClock = new TestingClock()
    def testSubscriber = new TestSubscriber()

    def 'executeAsync: is executed in any case, even nobody every subscribes'() {
        setup:
        AtomicBoolean wasExecuted = new AtomicBoolean(false)

        when:
        AsyncUtils.executeAsync {
            wasExecuted.set(true)
            testingClock.advanceTime()
        }
        testingClock.awaitTime(1)

        then:
        wasExecuted.get() == true
    }

    def 'executeAsync: returns result when finished'() {
        setup:
        def expectedResult = UUID.randomUUID().toString()

        when:
        Single<String> result = AsyncUtils.executeAsync({
            return expectedResult
        } as Callable<String>)
        result.subscribe(testSubscriber)
        await().until({ !testSubscriber.getOnNextEvents().isEmpty() } as Callable<Boolean>)

        then:
        testSubscriber.assertValue(expectedResult)
        testSubscriber.assertNoErrors()
    }

    def 'executeAsync: propagates exceptions'() {
        when:
        Single<FinishedIndicator> result = AsyncUtils.executeAsync {
            throw new RuntimeException('Boom!')
        }
        result.subscribe(testSubscriber)
        await().until({ !testSubscriber.getOnErrorEvents().isEmpty() } as Callable<Boolean>)

        then:
        testSubscriber.assertError(RuntimeException)
        testSubscriber.getOnErrorEvents().size() == 1
        testSubscriber.getOnErrorEvents().getAt(0).getMessage() == 'Boom!'
        testSubscriber.assertNoValues()
    }

    def 'executeAsync: propagated exceptions has cause to async origin'() {
        when:
        Single<FinishedIndicator> result = AsyncUtils.executeAsync {
            throw new RuntimeException("Boom!")
        }
        result.subscribe(testSubscriber)
        await().until({ !testSubscriber.getOnErrorEvents().isEmpty() } as Callable<Boolean>)

        then:
        testSubscriber.assertError(RuntimeException)
        testSubscriber.getOnErrorEvents().size() == 1
        testSubscriber.getOnErrorEvents().getAt(0).getStackTrace().any { it.className == this.class.name } == true
        testSubscriber.getOnErrorEvents().getAt(0).getCause() == null
        testSubscriber.assertNoValues()
    }

    def 'executeAsync: value of Single-Result is published to all interested at any time after completion '() {
        setup:
        def expectedResult = UUID.randomUUID().toString()
        Single<String> singleResult = AsyncUtils.executeAsync({
            return expectedResult
        } as Callable<String>)
        singleResult.toBlocking().value()

        when:
        def againTheResult = singleResult.toBlocking().value()
        then:
        againTheResult == expectedResult

        when:
        def againTheResult2 = singleResult.toBlocking().value()
        then:
        againTheResult2 == expectedResult

        when:
        AtomicReference<String> againTheResult3 = new AtomicReference<>()
        Thread.start {
            againTheResult3.set(singleResult.toBlocking().value())
            testingClock.advanceTime()
        }
        testingClock.awaitTime(1)

        then:
        againTheResult3.get() == expectedResult
    }

    def 'executeAsync: provided code is cancelled as soon as all subscribers did unsubscribe'() {
        setup:
        def testSubscriber2 = new TestSubscriber()
        def testSubscriber3 = new TestSubscriber()
        def AtomicReference<Exception> exceptionHolder = new AtomicReference<>()
        Single<FinishedIndicator> result = AsyncUtils.executeAsync {
            testingClock.advanceTime()
            try {
                TimeUnit.DAYS.sleep(Long.MAX_VALUE)
            } catch (InterruptedException ex) {
                exceptionHolder.set(ex)
            }
        }
        testingClock.awaitTime(1)
        result.subscribe(testSubscriber as Subscriber)
        result.subscribe(testSubscriber2 as Subscriber)
        result.subscribe(testSubscriber3 as Subscriber)

        when:
        testSubscriber.unsubscribe()
        TimeUnit.MILLISECONDS.sleep(100)  // just to be really really sure that no cancel happens!

        then:
        exceptionHolder.get() == null

        when:
        testSubscriber2.unsubscribe()
        TimeUnit.MILLISECONDS.sleep(100)   // just to be really really sure that no cancel happens!

        then:
        exceptionHolder.get() == null

        when:
        testSubscriber3.unsubscribe()
        await().until({ exceptionHolder.get() != null } as Callable<Boolean>)

        then:
        noExceptionThrown()
    }
}