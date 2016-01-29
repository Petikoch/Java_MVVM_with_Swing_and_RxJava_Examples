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
package ch.petikoch.examples.mvvm_rxjava.example6a;

import ch.petikoch.examples.mvvm_rxjava.datatypes.NameFirstname;
import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.FinishedIndicator;
import ch.petikoch.examples.mvvm_rxjava.utils.AsyncUtils;
import ch.petikoch.examples.mvvm_rxjava.utils.SysOutUtils;
import net.jcip.annotations.ThreadSafe;
import rx.Single;

import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
class Example_6a_Model {

    private final AtomicInteger failToken = new AtomicInteger(1);

    public Single<FinishedIndicator> createAcount(NameFirstname nameFirstname) {
        return AsyncUtils.<FinishedIndicator>executeAsync(() -> {
            try {
                SysOutUtils.sysout("Processing createAcount: " + nameFirstname.toString());

                if (failToken.incrementAndGet() % 3 == 0) {
                    throw new RuntimeException("Ooops in createAcount");
                }

                Thread.sleep(1000);
                SysOutUtils.sysout("Finished createAcount: " + nameFirstname.toString());
            } catch (InterruptedException e) {
                SysOutUtils.sysout("Interrupted (=cancelled) -> good!");
            }
        });
    }

    public Single<FinishedIndicator> sendEmail(NameFirstname nameFirstname) {
        return AsyncUtils.<FinishedIndicator>executeAsync(() -> {
            try {
                SysOutUtils.sysout("Processing sendEmail: " + nameFirstname.toString());

                if (failToken.incrementAndGet() % 3 == 0) {
                    throw new RuntimeException("Ooops in sendEmail");
                }

                Thread.sleep(1500);
                SysOutUtils.sysout("Finished sendEmail: " + nameFirstname.toString());
            } catch (InterruptedException e) {
                SysOutUtils.sysout("Interrupted (=cancelled) -> good!");
            }
        });
    }

}
