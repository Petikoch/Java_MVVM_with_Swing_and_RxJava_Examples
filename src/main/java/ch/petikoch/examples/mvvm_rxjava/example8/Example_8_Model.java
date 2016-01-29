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
package ch.petikoch.examples.mvvm_rxjava.example8;

import ch.petikoch.examples.mvvm_rxjava.datatypes.LogRow;
import ch.petikoch.examples.mvvm_rxjava.utils.SysOutUtils;
import ch.petikoch.examples.mvvm_rxjava.utils.VariousUtils;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

class Example_8_Model {

    public Observable<LogRow> getLogs() {

        SerializedSubject<LogRow, LogRow> subject
                = new SerializedSubject<>(PublishSubject.create());

        ThreadFactory threadFactory = new ThreadFactoryBuilder().setDaemon(true).setNameFormat(Example_8_Model.class.getSimpleName() + "-thread-%d").build();
        final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), threadFactory);

        IntStream.range(1, Runtime.getRuntime().availableProcessors() + 1).forEach(value -> {
            executorService.submit(() -> {
                SysOutUtils.sysout(Thread.currentThread().getName() + " will briefly start creating lots of log rows...");
                VariousUtils.sleep(1000);
                long incrementingNumber = 1;
                while (true) {
                    subject.onNext(new LogRow(
                            DateTimeFormatter.ISO_DATE_TIME.format(LocalDateTime.now()),
                            "Status " + Integer.toString(ThreadLocalRandom.current().nextInt(1, 5)),
                            "Action " + incrementingNumber + " from " + Thread.currentThread().getName()));
                }
            });
        });

        return subject;
    }

}
