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
package ch.petikoch.examples.mvvm_rxjava.example2;

import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.SwingUtilities.getWindowAncestor;

import java.awt.event.ActionEvent;

import javax.swing.AbstractButton;

import ch.petikoch.examples.mvvm_rxjava.datatypes.NameFirstname;
import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.IViewModel;
import ch.petikoch.examples.mvvm_rxjava.utils.AsyncUtils;
import net.jcip.annotations.ThreadSafe;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.SwingScheduler;
import rx.subjects.BehaviorSubject;
import rx.subjects.PublishSubject;

@ThreadSafe
class Example_2_ViewModel implements IViewModel<Example_2_Model> {

    public final BehaviorSubject<String> v2vm_name = BehaviorSubject.create();
    public final BehaviorSubject<String> v2vm_firstname = BehaviorSubject.create();
    public final PublishSubject<ActionEvent> v2vm_submitButtonEvents = PublishSubject.create();

    public final Observable<NameFirstname> vm2m_nameFirstname = 
        Observable.combineLatest(v2vm_name, v2vm_firstname, NameFirstname::new);

    private void setupCreateAccount( Example_2_Model model ) {
        v2vm_submitButtonEvents.withLatestFrom(vm2m_nameFirstname, SaveNameInput::new)
                               .subscribe( updateModel(model) );
    }

	private Action1<? super SaveNameInput> updateModel(Example_2_Model model) {
		return input -> {
			   AbstractButton button = (AbstractButton) input.getContext().getSource();
			   button.setEnabled( false );
			   
			   AsyncUtils.executeAsync(() -> model.createAccount( input.getInput() ) )
			             .observeOn(SwingScheduler.getInstance())
			             .subscribe(finished -> button.setEnabled( true ),
			            		    handleError(button) );
		   };
	}

	private Action1<Throwable> handleError(AbstractButton button) {
		return error -> {
			showMessageDialog(getWindowAncestor(button), 
					          "Failed to create account!",
					          "Error...",
					          ERROR_MESSAGE);
			button.setEnabled( true );
		};
	}
    
    @Override
    public void connectTo(final Example_2_Model model) {
    	setupCreateAccount(model);
    }
    
    private static interface ContextualInput<I, C>
    {
    	I getInput();
    	
    	C getContext();
    }
    
    private static class SaveNameInput implements ContextualInput<NameFirstname, ActionEvent>
    {
    	private final ActionEvent actionEvent;
    	private final NameFirstname nameFirstName;
    	
		public SaveNameInput(ActionEvent actionEvent, NameFirstname nameFirstName) {
			this.nameFirstName = nameFirstName;
			this.actionEvent = actionEvent;
		}
		
		@Override
		public NameFirstname getInput() {
			return nameFirstName;
		}
		
		@Override
		public ActionEvent getContext() {
			return actionEvent;
		}
    }
}
