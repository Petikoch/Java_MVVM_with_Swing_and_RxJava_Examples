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
package ch.petikoch.examples.mvvm_rxjava.example5a

import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.FinishedIndicator
import rx.Single
import rx.schedulers.Schedulers
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

import java.awt.event.ActionEvent
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicBoolean

@SuppressWarnings(["GroovyAccessibility", "GroovyPointlessBoolean"])
class Example_5A_ViewModelTest extends Specification {

    def testee = new Example_5a_ViewModel()
    def modelMock = Mock(Example_5a_Model)
    def conditions = new PollingConditions(timeout: 1)

    def 'when submit button click happend then you can cancel using cancel button'() {
        setup:
        testee.connectTo(modelMock)
        def modelSubmitLatch = new CountDownLatch(1)
        def modelSubmitStarted = new CountDownLatch(1)
        def interruptHappened = new AtomicBoolean(false)
        modelMock.createAccount(_) >> {
            return Single.<FinishedIndicator> create { subscriber ->
                modelSubmitStarted.countDown()
                try {
                    modelSubmitLatch.await()
                    assert false // should never be here
                } catch (InterruptedException iex) {
                    interruptHappened.set(true) // well!
                }
            }.subscribeOn(Schedulers.io())
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
        modelSubmitStarted.await()
        testee.v2vm_cancelButtonEvents.onNext(new ActionEvent(new Object(), 42, "Cancel click!"))

        then:
        conditions.eventually {
            // asynchronous
            assert testee.vm2v_nameEnabled.getValue() == true
            assert testee.vm2v_firstnameEnabled.getValue() == true
            assert testee.vm2v_submitButtonEnabled.getValue() == true
            assert interruptHappened.get() == true
        }
    }
}
