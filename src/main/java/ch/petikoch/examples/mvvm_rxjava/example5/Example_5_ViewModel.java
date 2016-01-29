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
package ch.petikoch.examples.mvvm_rxjava.example5;

import ch.petikoch.examples.mvvm_rxjava.datatypes.NameFirstname;
import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.IViewModel;
import ch.petikoch.examples.mvvm_rxjava.utils.SysOutUtils;
import net.jcip.annotations.ThreadSafe;
import org.apache.commons.lang3.StringUtils;
import rx.Observable;
import rx.Single;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

import java.awt.event.ActionEvent;

import static ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.RxModelInvoker.onEventFrom;

@ThreadSafe
class Example_5_ViewModel implements IViewModel<Example_5_Model> {

    public final BehaviorSubject<String> v2vm_name = BehaviorSubject.create();
    public final BehaviorSubject<Boolean> vm2v_nameEnabled = BehaviorSubject.create();

    public final BehaviorSubject<String> v2vm_firstname = BehaviorSubject.create();
    public final BehaviorSubject<Boolean> vm2v_firstnameEnabled = BehaviorSubject.create();

    public final PublishSubject<ActionEvent> v2vm_submitButtonEvents = PublishSubject.create();
    public final BehaviorSubject<Boolean> vm2v_submitButtonEnabled = BehaviorSubject.create(false /* initial state */);

    public final PublishSubject<ActionEvent> v2vm_cancelButtonEvents = PublishSubject.create();
    public final BehaviorSubject<Boolean> vm2v_cancelButtonEnabled = BehaviorSubject.create(false /* initial state */);

    public final BehaviorSubject<NameFirstname> vm2m_nameFirstname = BehaviorSubject.create();

    public Example_5_ViewModel() {
        wireInternally();
    }

    private void wireInternally() {
        v2vm_submitButtonEvents
                .map(actionEvent -> new NameFirstname(v2vm_name.getValue(), v2vm_firstname.getValue()))
                .subscribe(nameFirstname -> {
                    vm2v_nameEnabled.onNext(false);
                    vm2v_firstnameEnabled.onNext(false);
                    vm2v_submitButtonEnabled.onNext(false);
                    vm2v_cancelButtonEnabled.onNext(true);

                    vm2m_nameFirstname.onNext(nameFirstname);
                });

        Observable.merge(v2vm_name, v2vm_firstname)
                .map(nameOrFirstname -> StringUtils.isNotBlank(v2vm_name.getValue()) && StringUtils.isNotBlank(v2vm_firstname.getValue()))
                .subscribe(vm2v_submitButtonEnabled);
    }

    @Override
    public void connectTo(final Example_5_Model model) {
        onEventFrom(vm2m_nameFirstname).executeAsync(nameFirstname -> {

            Single<Boolean> modelCallFinished = Single.<Boolean>create(singleSubscriber -> {
                try {
                    model.createAccount(nameFirstname);
                    if (!singleSubscriber.isUnsubscribed()) {
                        singleSubscriber.onSuccess(true);
                    }
                } catch (InterruptedException e) {
                    SysOutUtils.sysout("Interrupted (=cancelled) -> good!");
                }
            }).subscribeOn(Schedulers.io());

            Single.merge(modelCallFinished, v2vm_cancelButtonEvents.first().toSingle())
                    .toBlocking()
                    .first();

            vm2v_nameEnabled.onNext(true);
            vm2v_firstnameEnabled.onNext(true);
            vm2v_submitButtonEnabled.onNext(true);
            vm2v_cancelButtonEnabled.onNext(false);
        });
    }
}
