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
package ch.petikoch.examples.mvvm_rxjava.example5a;

import ch.petikoch.examples.mvvm_rxjava.datatypes.NameFirstname;
import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.FinishedIndicator;
import ch.petikoch.examples.mvvm_rxjava.utils.SysOutUtils;
import net.jcip.annotations.ThreadSafe;
import rx.Single;
import rx.schedulers.Schedulers;

@ThreadSafe
class Example_5a_Model {

    public Single<FinishedIndicator> createAccount(NameFirstname nameFirstname) {
        return Single.fromCallable(() -> {
            try {
                SysOutUtils.sysout("Processing: " + nameFirstname.toString());
                Thread.sleep(5000);    // = Simulation of a "very slow" backend
                SysOutUtils.sysout("Finished: " + nameFirstname.toString());
            } catch (InterruptedException e) {
                SysOutUtils.sysout("Interrupted (=cancelled) -> good!");
            }
            return FinishedIndicator.INSTANCE;
        }).subscribeOn(Schedulers.io());
    }

}
