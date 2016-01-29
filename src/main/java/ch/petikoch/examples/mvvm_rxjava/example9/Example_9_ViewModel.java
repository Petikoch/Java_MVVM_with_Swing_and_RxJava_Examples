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
        vm2v_mainPanel.onNext(createStep1ViewModel());
    }

    private Example_9_ViewModel_Step1 createStep1ViewModel() {
        Example_9_ViewModel_Step1 viewModelStep1 = new Example_9_ViewModel_Step1();
        viewModelStep1.v2vm_startButtonEvents.subscribe(actionEvent -> {
            vm2v_mainPanel.onNext(createStep2ViewModel());
        });
        return viewModelStep1;
    }

    private Example_9_ViewModel_Step2 createStep2ViewModel() {
        vm2v_status.vm2v_status.onNext("Please complete the form");
        Example_9_ViewModel_Step2 viewModelStep2 = new Example_9_ViewModel_Step2();
        viewModelStep2.v2vm_submitButtonEvents.subscribe(actionEvent -> {
            vm2v_mainPanel.onNext(
                    createStep3ViewModel(
                            viewModelStep2.v2vm_name.getValue(),
                            viewModelStep2.v2vm_firstname.getValue()
                    )
            );
        });
        return viewModelStep2;
    }

    private Example_9_ViewModel_Step3 createStep3ViewModel(String name, String firstname) {
        vm2v_status.vm2v_status.onNext("Thanks");
        Example_9_ViewModel_Step3 viewModelStep3 = new Example_9_ViewModel_Step3();
        viewModelStep3.vm_name.onNext(name);
        viewModelStep3.vm_firstname.onNext(firstname);
        viewModelStep3.v2vm_restartButtonEvents.subscribe(actionEvent -> {
            vm2v_mainPanel.onNext(createStep2ViewModel());
        });
        return viewModelStep3;
    }

    @Override
    public void connectTo(final Example_9_Model model) {
        vm2v_status.connectTo(model);
    }

}
