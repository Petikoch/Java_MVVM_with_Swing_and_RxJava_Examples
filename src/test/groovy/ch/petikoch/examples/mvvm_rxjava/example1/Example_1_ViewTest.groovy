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
package ch.petikoch.examples.mvvm_rxjava.example1

import spock.lang.Specification

import static ch.petikoch.examples.mvvm_rxjava.utils.SwingUtilities2.executeOnAwtEdt

@SuppressWarnings("GroovyAccessibility")
class Example_1_ViewTest extends Specification {

    def viewModel = new Example_1_ViewModel()
    def testee = createTestee()

    private Example_1_View createTestee() {
        return executeOnAwtEdt({
            def view = new Example_1_View()
            view.bind(viewModel)
            return view
        })
    }

    def 'Binding ViewModel -> View works'() {
        when:
        def text1 = "infotext_1: " + System.nanoTime()
        viewModel.vm2v_info.onNext(text1)
        then:
        executeOnAwtEdt({ testee.label.getText() }) == text1

        when:
        def text2 = "infotext_2: " + System.nanoTime()
        viewModel.vm2v_info.onNext(text2)
        then:
        executeOnAwtEdt({ testee.label.getText() }) == text2
    }

}
