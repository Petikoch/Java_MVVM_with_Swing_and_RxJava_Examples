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

import ch.petikoch.examples.mvvm_rxjava.utils.AsyncUtils;
import rx.Observable;
import rx.functions.Action1;

public class RxModelInvoker {

    public static <T> ModelInvoker<T> onEventFrom(Observable<T> source) {
        return new ModelInvoker<>(source);
    }

    public static class ModelInvoker<T> {

        private final Observable<T> source;

        private ModelInvoker(final Observable<T> source) {
            this.source = source;
        }

        public void executeAsync(final Action1<? super T> onNext) {
            source.subscribe(t -> {
                AsyncUtils.executeAsync(() -> onNext.call(t));
            });
        }
    }
}
