/*
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
package ch.petikoch.examples.mvvm_rxjava.example4

import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import java.awt.event.ActionEvent
import java.util.concurrent.CountDownLatch

@SuppressWarnings(["GroovyAccessibility", "GroovyPointlessBoolean"])
class Example_4_ViewModelTest extends Specification {

    def testee = new Example_4_ViewModel()
    def modelMock = Mock(Example_4_Model)

    def conditions = new PollingConditions(timeout: 1)

    def 'when submit Button click happend, the form widgets are immediately disabled,  then the call to the model is executed and afterwards the from widgets re-enabled'() {
        setup:
        testee.connectTo(modelMock)
        def modelSubmitLatch = new CountDownLatch(1)
        modelMock.createAccount(_) >> {
            modelSubmitLatch.await()
        }
        testee.v2vm_name.onNext('Smith')
        testee.v2vm_firstname.onNext('John')

        when:
        testee.v2vm_submitButtonEvents.onNext(new ActionEvent(new Object(), 42, "Submit click!"))
        then:
        // immediate
        testee.vm2v_nameEnabled.getValue() == false
        testee.vm2v_firstnameEnabled.getValue() == false
        testee.vm2v_submitButtonEnabled.getValue() == false

        when:
        modelSubmitLatch.countDown()

        then:
        conditions.eventually {
            // done by a different thread asynchronous
            assert testee.vm2v_nameEnabled.getValue() == true
            assert testee.vm2v_firstnameEnabled.getValue() == true
            assert testee.vm2v_submitButtonEnabled.getValue() == true
        }
    }
}
