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
package ch.petikoch.examples.mvvm_rxjava.example2

import ch.petikoch.examples.mvvm_rxjava.datatypes.NameFirstname
import spock.lang.Specification
import spock.util.concurrent.PollingConditions

class Example_2_ViewModelTest extends Specification {

    def modelMock = Mock(Example_2_Model)
    def testee = new Example_2_ViewModel()

    def conditions = new PollingConditions(timeout: 1)

    def 'Binding ViewModel -> Model works'() {
        setup:
        testee.connectTo(modelMock)
        def nameFirstname = new NameFirstname('John', 'Smith')

        when:
        testee.vm2m_nameFirstname.onNext(nameFirstname)

        then:
        conditions.eventually {
            assert { 1 * modelMock.createAccount(nameFirstname) }
        }
    }
}
