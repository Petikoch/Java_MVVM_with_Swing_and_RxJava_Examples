/*
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
package ch.petikoch.examples.mvvm_rxjava.utils

import ch.petikoch.examples.mvvm_rxjava.TestingClock
import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.FinishedIndicator
import rx.Single
import rx.observers.TestSubscriber
import spock.lang.Specification
import spock.lang.Timeout

import java.util.concurrent.Callable
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicReference

import static ch.petikoch.examples.mvvm_rxjava.AwaitUtils.awaitUntil

@Timeout(value = 10, unit = TimeUnit.SECONDS)
class AsyncUtilsTest extends Specification {

    def testingClock = new TestingClock()
    def testSubscriber = new TestSubscriber()

    def 'executeAsync: is executed in any case, even nobody every subscribes'() {
        setup:
        AtomicBoolean wasExecuted = new AtomicBoolean(false)
        assert testingClock.getTime() == 0

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
        assert testingClock.getTime() == 0

        when:
        Single<String> result = AsyncUtils.executeAsync({
            try {
                return expectedResult
            } finally {
                testingClock.advanceTime()
            }
        } as Callable<String>)
        testingClock.awaitTime(1)
        result.subscribe(testSubscriber)

        then:
        testSubscriber.assertValue(expectedResult)
        testSubscriber.assertNoErrors()
    }

    def 'executeAsync: propagates exceptions'() {
        when:
        Single<FinishedIndicator> result = AsyncUtils.executeAsync {
            throw new RuntimeException("Boom!")
        }
        result.subscribe(testSubscriber)
        awaitUntil { !testSubscriber.getOnErrorEvents().isEmpty() }

        then:
        testSubscriber.assertError(RuntimeException)
        testSubscriber.assertNoValues()
    }

    def 'executeAsync: value of Single-Result is published to all interested subscribers at any time after completion'() {
        setup:
        assert testingClock.getTime() == 0
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
        assert testingClock.getTime() == 0
        def testSubscriber2 = new TestSubscriber()
        def testSubscriber3 = new TestSubscriber()
        def AtomicReference<Exception> exceptionHolder = new AtomicReference<>()
        Single<String> result = AsyncUtils.executeAsync {
            testingClock.advanceTime()
            try {
                TimeUnit.DAYS.sleep(Long.MAX_VALUE)
            } catch (InterruptedException ex) {
                exceptionHolder.set(ex)
            }
        }
        testingClock.awaitTime(1)
        result.subscribe(testSubscriber)
        result.subscribe(testSubscriber2)
        result.subscribe(testSubscriber3)

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
        awaitUntil { exceptionHolder.get() != null }

        then:
        exceptionHolder.get() != null
    }
}