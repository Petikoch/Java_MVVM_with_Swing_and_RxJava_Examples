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
package ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm;

import rx.Observable;
import rx.Observer;
import rx.functions.Func0;
import rx.observables.SwingObservable;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.event.ActionEvent;

public class RxSwingView2ViewModelBinder {

    public static BindOfAble<ActionEvent> bindSwingView(AbstractButton source) {
        return new BindOfAble<>(SwingObservable.fromButtonAction(source));
    }

    public static BindOfAble<Boolean> bindSwingView(JToggleButton source) {
        return new BindOfAble<>(SwingObservable.fromButtonAction(source).map(actionEvent -> source.isSelected()));
    }

    public static BindOfAble<String> bindSwingView(JTextComponent source) {
        return new BindOfAble<>(SwingObservable.fromDocumentEvents(source.getDocument())
                .map(documentEvent1 -> source.getText()));
    }

    public static BindOfAble<Integer> bindSwingView(JTextComponent source, AbstractButton sourceEventTrigger) {
        return new BindOfAble<>(SwingObservable.fromButtonAction(sourceEventTrigger)
                .map(actionEvent -> Integer.parseInt(source.getText())));
    }

    public static <T> BindOfAble<T> bindSwingView(AbstractButton sourceEventTrigger, Func0<T> function) {
        return new BindOfAble<>(
                SwingObservable.fromButtonAction(sourceEventTrigger)
                        .map(buttonClick -> function.call()));
    }

    public static class BindOfAble<T> {

        private final Observable<T> source;

        private BindOfAble(final Observable<T> source) {
            this.source = source;
        }

        public void toViewModel(Observer<T> target) {
            source.subscribe(target);
        }
    }
}
