/**
 * Copyright 2015 Peti Koch
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

public class RxModel2ViewModelBinder {

    public static <T> BindOfAble<T> bindModel(Observable<T> source) {
        return new BindOfAble<>(source);
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
