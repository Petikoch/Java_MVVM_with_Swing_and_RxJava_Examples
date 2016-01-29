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
package ch.petikoch.examples.mvvm_rxjava.example9
import ch.petikoch.examples.mvvm_rxjava.AwtEdtSputnik
import org.junit.runner.RunWith
import spock.lang.Specification

import javax.swing.*
import java.util.concurrent.TimeUnit

@RunWith(AwtEdtSputnik.class)
@SuppressWarnings(["GroovyAccessibility", "GroovyPointlessBoolean"])
class Beispiel_9_AcceptanceTest extends Specification {

    Example_9_Model model
    Example_9_ViewModel viewModel
    Example_9_View view

    long startTimeNanos

    def setup() {
        assert SwingUtilities.isEventDispatchThread()

        startTimeNanos = System.nanoTime()
        model = new Example_9_Model()
        viewModel = new Example_9_ViewModel();
        viewModel.connectTo(model);
        view = new Example_9_View();
        view.bind(viewModel);
    }

    def cleanup() {
        view.setVisible(false)
        long durationMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTimeNanos)
        println "Test finished in ${durationMs} ms"
    }


    def 'Full round trip acceptance Test'() {
        when:
        view.setVisible(true);
        def step1Panel = view.mainPanel.getComponent(0) as Example_9_View_Step1Panel

        then:
        view.titlePanel.welcomeLabel.text == 'Welcome to this example'
        step1Panel.startButton.text == 'Start'
        step1Panel.startButton.enabled == true

        when:
        step1Panel = view.mainPanel.getComponent(0) as Example_9_View_Step1Panel
        step1Panel.startButton.doClick()
        def step2Panel = view.mainPanel.getComponent(0) as Example_9_View_Step2Panel

        then:
        view.statusPanel.statusLabel.text == 'Please complete the form'
        step2Panel.nameTextField.text == ''
        step2Panel.nameTextField.enabled == true
        step2Panel.firstnameTextField.text == ''
        step2Panel.firstnameTextField.enabled == true
        step2Panel.nextButton.text == 'Next'
        step2Panel.nextButton.enabled == true

        when:
        def name = "Smith_${System.nanoTime()}".toString()
        def firstname = "Smith_${System.nanoTime()}".toString()
        step2Panel = view.mainPanel.getComponent(0) as Example_9_View_Step2Panel
        step2Panel.nameTextField.text = name
        step2Panel.firstnameTextField.text = firstname
        step2Panel.nextButton.doClick()
        def step3Panel = view.mainPanel.getComponent(0) as Example__View_Step3Panel

        then:
        view.statusPanel.statusLabel.text == "Thanks"
        step3Panel.displayedNameLabel.text == name
        step3Panel.displayedFirstnameLabel.text == firstname

        when:
        step3Panel = view.mainPanel.getComponent(0) as Example__View_Step3Panel
        step3Panel.restartButton.doClick()
        step2Panel = view.mainPanel.getComponent(0) as Example_9_View_Step2Panel

        then:
        view.statusPanel.statusLabel.text == 'Please complete the form'
        step2Panel.nameTextField.text == ''
        step2Panel.firstnameTextField.text == ''
    }
}
