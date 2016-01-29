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
package ch.petikoch.examples.mvvm_rxjava.example2;

import ch.petikoch.examples.mvvm_rxjava.datatypes.NameFirstname;
import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.IViewModel;
import net.jcip.annotations.ThreadSafe;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import java.awt.event.ActionEvent;

import static ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.RxModelInvoker.onEventFrom;

@ThreadSafe
class Example_2_ViewModel implements IViewModel<Example_2_Model> {

    public final BehaviorSubject<String> v2vm_name = BehaviorSubject.create();
    public final BehaviorSubject<String> v2vm_firstname = BehaviorSubject.create();
    public final PublishSubject<ActionEvent> v2vm_submitButtonEvents = PublishSubject.create();

    public final BehaviorSubject<NameFirstname> vm2m_nameFirstname = BehaviorSubject.create();

    public Example_2_ViewModel() {
        wireInternally();
    }

    private void wireInternally() {
        v2vm_submitButtonEvents
                .map(actionEvent -> new NameFirstname(v2vm_name.getValue(), v2vm_firstname.getValue()))
                .subscribe(vm2m_nameFirstname);
    }

    @Override
    public void connectTo(final Example_2_Model model) {
        onEventFrom(vm2m_nameFirstname).executeAsync(model::createAccount);
    }

}
