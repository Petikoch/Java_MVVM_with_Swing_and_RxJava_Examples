/**
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
package ch.petikoch.examples.mvvm_rxjava.example9;

import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.IViewModel;
import net.jcip.annotations.ThreadSafe;
import rx.subjects.BehaviorSubject;

@ThreadSafe
class Example_9_ViewModel implements IViewModel<Example_9_Model> {

    public final BehaviorSubject<Object> vm2v_mainPanel = BehaviorSubject.create();

    public final Example_9_ViewModel_Status vm2v_status = new Example_9_ViewModel_Status();

    public Example_9_ViewModel() {
        wireInternally();
    }

    private void wireInternally() {
        vm2v_mainPanel.onNext(createSchritt1ViewModel());
    }

    private Example_9_ViewModel_Step1 createSchritt1ViewModel() {
        Example_9_ViewModel_Step1 viewModelSchritt1 = new Example_9_ViewModel_Step1();
        viewModelSchritt1.v2vm_startButtonEvents.subscribe(actionEvent -> {
            vm2v_mainPanel.onNext(createSchritt2ViewModel());
        });
        return viewModelSchritt1;
    }

    private Example_9_ViewModel_Step2 createSchritt2ViewModel() {
        vm2v_status.vm2v_status.onNext("Please complete the form");
        Example_9_ViewModel_Step2 viewModelSchritt2 = new Example_9_ViewModel_Step2();
        viewModelSchritt2.v2vm_submitButtonEvents.subscribe(actionEvent -> {
            vm2v_mainPanel.onNext(
                    createSchritt3ViewModel(
                            viewModelSchritt2.v2vm_name.getValue(),
                            viewModelSchritt2.v2vm_firstname.getValue()
                    )
            );
        });
        return viewModelSchritt2;
    }

    private Example_9_ViewModel_Step3 createSchritt3ViewModel(String name, String vorname) {
        vm2v_status.vm2v_status.onNext("Thanks");
        Example_9_ViewModel_Step3 viewModelSchritt3 = new Example_9_ViewModel_Step3();
        viewModelSchritt3.vm_name.onNext(name);
        viewModelSchritt3.vm_vorname.onNext(vorname);
        viewModelSchritt3.v2vm_restartButtonEvents.subscribe(actionEvent -> {
            vm2v_mainPanel.onNext(createSchritt2ViewModel());
        });
        return viewModelSchritt3;
    }

    @Override
    public void connectTo(final Example_9_Model model) {
        vm2v_status.connectTo(model);
    }

}
