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
package ch.petikoch.examples.mvvm_rxjava;

import com.google.common.base.Stopwatch;
import com.google.common.base.Throwables;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * A utility class for testing multi-threaded code.
 *
 * What's the difference e.g. to a {@link java.util.concurrent.CountDownLatch}?
 *
 * In simple testcases one {@link java.util.concurrent.CountDownLatch} instance is typically
 * fine and perfect to coordinate 2 or more threads.
 * But if you have more than one {@link java.util.concurrent.CountDownLatch} in a testcase,
 * it's probably easier to use <b>one</b> {@link TestingClock} instead.
 */
// experimental
public class TestingClock {

    private final AtomicLong internalTime;

    public TestingClock() {
        this(0);
    }

    public TestingClock(long startTime) {
        internalTime = new AtomicLong(startTime);
    }

    public void advanceTime() {
        long time = internalTime.incrementAndGet();
        System.out.println("Time is now: " + time + ". The time was advanced by '" + Thread.currentThread().getName() + "'.");
    }

    public long getTime() {
        return internalTime.get();
    }

    public void awaitTime(long time) {
        try {
            awaitTime(time, Long.MAX_VALUE);
        } catch (TimeoutException e) {
            throw Throwables.propagate(e);
        }
    }

    public void awaitTime(long time, long timeoutMs) throws TimeoutException {
        System.out.println("Thread '" + Thread.currentThread().getName() + "' is waiting for time: " + time);

        Stopwatch stopWatch = Stopwatch.createStarted();
        Stopwatch showLifeSignStopWatch = Stopwatch.createStarted();

        while (internalTime.get() < time) {
            sleep_a_little_while();
            checkTimeout(timeoutMs, stopWatch);
            if (showLifeSignStopWatch.elapsed(TimeUnit.SECONDS) >= 1) {
                showLifeSignStopWatch = Stopwatch.createStarted();
                System.out.println("Thread '" + Thread.currentThread().getName() + "' is still waiting for time: " + time);
            }
        }

        System.out.println("Time " + time + " arrived for Thread '" + Thread.currentThread().getName() + "'");
    }

    private void checkTimeout(long timeoutMs, Stopwatch stopWatch) throws TimeoutException {
        if (stopWatch.elapsed(TimeUnit.MILLISECONDS) >= timeoutMs) {
            throw new TimeoutException();
        }
    }

    private void sleep_a_little_while() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final TestingClock that = (TestingClock) o;

        return internalTime.get() == that.internalTime.get();
    }

    @Override
    public int hashCode() {
        return Long.valueOf(internalTime.get()).intValue();
    }
}
