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
import rx.functions.Action1;
import rx.schedulers.SwingScheduler;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import static ch.petikoch.examples.mvvm_rxjava.utils.PreserveFullStackTraceOperator.preserveFullStackTrace;

public class RxViewModel2SwingViewBinder {

    public static BooleanBindOfAble bindViewModelBoolean(Observable<Boolean> source) {
        return new BooleanBindOfAble(source);
    }

    public static StringBindOfAble bindViewModelString(Observable<String> source) {
        return new StringBindOfAble(source);
    }

    public static <T> BindOfAble<T> bindViewModel(Observable<T> source) {
        return new BindOfAble<>(source);
    }

    public static class BooleanBindOfAble {

        private final Observable<Boolean> source;

        private BooleanBindOfAble(final Observable<Boolean> source) {
            this.source = source;
        }

        public void toSwingViewEnabledPropertyOf(JComponent target) {
            source.onBackpressureLatest()
                    .observeOn(SwingScheduler.getInstance())
                    .lift(preserveFullStackTrace())
                    .subscribe(target::setEnabled);
        }
    }

    public static class StringBindOfAble {

        private final Observable<String> source;

        private StringBindOfAble(final Observable<String> source) {
            this.source = source;
        }

        public void toSwingViewText(JTextComponent target) {
            source.onBackpressureLatest()
                    .observeOn(SwingScheduler.getInstance())
                    .lift(preserveFullStackTrace())
                    .subscribe(target::setText);
        }

        public void toSwingViewLabel(JLabel target) {
            source.onBackpressureLatest()
                    .observeOn(SwingScheduler.getInstance())
                    .lift(preserveFullStackTrace())
                    .subscribe(target::setText);
        }
    }

    public static class BindOfAble<T> {

        private final Observable<T> source;

        private BindOfAble(final Observable<T> source) {
            this.source = source;
        }

        public void toAction(Action1<T> action) {
            source.observeOn(SwingScheduler.getInstance())
                    .lift(preserveFullStackTrace())
                    .subscribe(action);
        }
    }

}
