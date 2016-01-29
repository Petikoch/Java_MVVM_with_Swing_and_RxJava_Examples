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
import ch.petikoch.examples.mvvm_rxjava.widgets.StrictThreadingJButton;
import ch.petikoch.examples.mvvm_rxjava.widgets.StrictThreadingJPanel;

import java.awt.*;

import static ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.RxSwingView2ViewModelBinder.bindSwingView;

public class Example_9_View_Step1Panel extends StrictThreadingJPanel implements IView<Example_9_ViewModel_Step1> {

    private final StrictThreadingJButton startButton;

    @Override
    public void bind(final Example_9_ViewModel_Step1 viewModel) {
        bindSwingView(startButton).toViewModel(viewModel.v2vm_startButtonEvents);
    }

    public Example_9_View_Step1Panel() {
        super();

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        startButton = new StrictThreadingJButton("Start");
        GridBagConstraints gbc_startButton = new GridBagConstraints();
        gbc_startButton.gridx = 0;
        gbc_startButton.gridy = 0;
        add(startButton, gbc_startButton);
    }
}
