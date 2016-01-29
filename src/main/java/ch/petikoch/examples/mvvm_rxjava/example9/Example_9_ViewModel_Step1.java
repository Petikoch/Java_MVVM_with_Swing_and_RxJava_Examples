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
import rx.subjects.PublishSubject;

import java.awt.event.ActionEvent;

@ThreadSafe
class Example_9_ViewModel_Step1 implements IViewModel<Example_9_Model> {

    public final PublishSubject<ActionEvent> v2vm_startButtonEvents = PublishSubject.create();

    public Example_9_ViewModel_Step1() {
        wireInternally();
    }

    private void wireInternally() {
        // NO-OP
    }

    @Override
    public void connectTo(final Example_9_Model model) {
        // NO-OP
    }

}
