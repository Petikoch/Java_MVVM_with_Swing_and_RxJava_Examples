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

import ch.petikoch.examples.mvvm_rxjava.utils.SwingUtilities2
import spock.lang.Specification

@SuppressWarnings(["GroovyAccessibility", "GroovyPointlessBoolean"])
class Example_3_ViewTest extends Specification {

    def viewModel = new Example_3_ViewModel()
    def testee = createTestee()

    private Example_3_View createTestee() {
        return SwingUtilities2.executeOnAwtEdt({
            def view = new Example_3_View()
            view.bind(viewModel)
            return view
        })
    }

    def 'Binding ViewModel -> View works for enabled property of submit button'() {
        setup:
        assert SwingUtilities2.executeOnAwtEdt { testee.submitButton.enabled } == false

        when:
        viewModel.vm2v_submitButtonEnabled.onNext(true)
        then:
        SwingUtilities2.executeOnAwtEdt { testee.submitButton.enabled } == true

        when:
        viewModel.vm2v_submitButtonEnabled.onNext(false)
        then:
        SwingUtilities2.executeOnAwtEdt { testee.submitButton.enabled } == false
    }
}
