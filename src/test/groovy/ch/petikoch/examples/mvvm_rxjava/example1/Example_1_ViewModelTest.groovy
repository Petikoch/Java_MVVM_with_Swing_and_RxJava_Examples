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

import rx.Observable
import spock.lang.Specification

class Example_1_ViewModelTest extends Specification {

    def modelMock = Mock(Example_1_Model)
    def testee = new Example_1_ViewModel()

    def 'ViewModel <-> Model works'() {
        setup:
        def text1 = "JustThis_" + System.nanoTime()
        def text2 = "AndThat_" + System.nanoTime()
        modelMock.infos() >> Observable.just(text1, text2)
        def captured = []
        testee.vm2v_info.subscribe { captured.add(it) }

        when:
        testee.connectTo(modelMock)

        then:
        captured == [text1, text2]
    }
}
