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
package ch.petikoch.examples.mvvm_rxjava.example9;

import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.IView;
import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.RxViewModel2SwingViewBinder;
import ch.petikoch.examples.mvvm_rxjava.widgets.StrictThreadingJLabel;
import ch.petikoch.examples.mvvm_rxjava.widgets.StrictThreadingJPanel;

import javax.swing.border.TitledBorder;
import java.awt.*;

public class Example_9_View_StatusPanel extends StrictThreadingJPanel implements IView<Example_9_ViewModel_Status> {

    private final StrictThreadingJLabel statusLabel;

    @Override
    public void bind(final Example_9_ViewModel_Status viewModel) {
        RxViewModel2SwingViewBinder.bindViewModelString(viewModel.vm2v_status).toSwingViewLabel(statusLabel);
    }

    public Example_9_View_StatusPanel() {
        super();
        setBorder(new TitledBorder(null, "Status", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        setLayout(new BorderLayout(0, 0));

        statusLabel = new StrictThreadingJLabel("");
        add(statusLabel, BorderLayout.CENTER);
    }
}
