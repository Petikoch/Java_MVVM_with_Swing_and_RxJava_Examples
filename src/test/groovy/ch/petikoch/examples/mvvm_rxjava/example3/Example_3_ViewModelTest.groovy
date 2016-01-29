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
package ch.petikoch.examples.mvvm_rxjava.example3

import spock.lang.Specification

@SuppressWarnings(["GroovyAccessibility", "GroovyPointlessBoolean"])
class Example_3_ViewModelTest extends Specification {

    def testee = new Example_3_ViewModel()

    def 'submit button depends on name and firstname are filled'() {
        setup:
        assert testee.vm2v_submitButtonEnabled.getValue() == false

        when:
        testee.v2vm_name.onNext('Smith')
        then:
        testee.vm2v_submitButtonEnabled.getValue() == false

        when:
        testee.v2vm_firstname.onNext('John')
        then:
        testee.vm2v_submitButtonEnabled.getValue() == true

        when:
        testee.v2vm_firstname.onNext('   ')
        then:
        testee.vm2v_submitButtonEnabled.getValue() == false
    }
}
