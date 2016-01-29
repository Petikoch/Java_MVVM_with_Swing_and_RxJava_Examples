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

import static ch.petikoch.examples.mvvm_rxjava.utils.SwingUtilities2.executeOnAwtEdt

@SuppressWarnings("GroovyAccessibility")
class Example_2_ViewTest extends Specification {

    def viewModel = new Example_2_ViewModel()
    def testee = createTestee()

    private Example_2_View createTestee() {
        return executeOnAwtEdt({
            def view = new Example_2_View()
            view.bind(viewModel)
            return view
        })
    }

    def 'Binding View -> ViewModel works'() {
        when:
        def name1 = 'John'
        def firstname1 = 'Smith'
        executeOnAwtEdt { testee.nameTextField.setText(name1) }
        executeOnAwtEdt { testee.firstnameTextField.setText(firstname1) }
        then:
        viewModel.v2vm_name.getValue() == name1
        viewModel.v2vm_firstname.getValue() == firstname1

        when:
        executeOnAwtEdt { testee.submitButton.doClick() }
        then:
        viewModel.vm2m_nameFirstname.getValue() == new NameFirstname(name1, firstname1)

        when:
        def name2 = 'Ben'
        def firstname2 = 'Boom'
        executeOnAwtEdt { testee.nameTextField.setText(name2) }
        executeOnAwtEdt { testee.firstnameTextField.setText(firstname2) }
        then:
        viewModel.v2vm_name.getValue() == name2
        viewModel.v2vm_firstname.getValue() == firstname2

        when:
        executeOnAwtEdt { testee.submitButton.doClick() }
        then:
        viewModel.vm2m_nameFirstname.getValue() == new NameFirstname(name2, firstname2)
    }
}
