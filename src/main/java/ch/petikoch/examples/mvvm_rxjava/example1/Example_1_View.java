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
package ch.petikoch.examples.mvvm_rxjava.example1;

import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.IView;
import ch.petikoch.examples.mvvm_rxjava.widgets.StrictThreadingJFrame;
import ch.petikoch.examples.mvvm_rxjava.widgets.StrictThreadingJLabel;

import javax.swing.*;
import java.awt.*;
import java.lang.management.ManagementFactory;

import static ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.RxViewModel2SwingViewBinder.bindViewModelString;

class Example_1_View extends StrictThreadingJFrame implements IView<Example_1_ViewModel> {

    private final StrictThreadingJLabel label;

    @Override
    public void bind(final Example_1_ViewModel viewModel) {
        bindViewModelString(viewModel.vm2v_info).toSwingViewLabel(label);
    }

    public Example_1_View() {
        super();
        setTitle(getClass().getSimpleName() + " " + ManagementFactory.getRuntimeMXBean().getName());

        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(StrictThreadingJFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout(0, 0));

        label = new StrictThreadingJLabel();
        label.setForeground(Color.BLUE);
        label.setFont(new Font("Tahoma", Font.BOLD, 20));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        getContentPane().add(label, BorderLayout.CENTER);
    }
}
