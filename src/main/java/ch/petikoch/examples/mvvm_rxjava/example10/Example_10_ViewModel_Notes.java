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
package ch.petikoch.examples.mvvm_rxjava.example10;

import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.IViewModel;
import net.jcip.annotations.ThreadSafe;
import rx.subjects.BehaviorSubject;

@ThreadSafe
class Example_10_ViewModel_Notes implements IViewModel<Example_10_Model> {

    public final BehaviorSubject<Boolean> vm2v_edit = BehaviorSubject.create();

    public final BehaviorSubject<String> v2vm_notes = BehaviorSubject.create();
    public final BehaviorSubject<String> vm2v_notes = BehaviorSubject.create();

    public Example_10_ViewModel_Notes() {
        wireInternally();
    }

    private void wireInternally() {
        // Simulating some data
        vm2v_notes.onNext("No notes");
    }

    @Override
    public void connectTo(final Example_10_Model model) {
        // NO-OP
    }

}
