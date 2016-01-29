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
import ch.petikoch.examples.mvvm_rxjava.widgets.StrictThreadingJFrame;
import ch.petikoch.examples.mvvm_rxjava.widgets.StrictThreadingJPanel;
import rx.schedulers.SwingScheduler;

import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.lang.management.ManagementFactory;

import static ch.petikoch.examples.mvvm_rxjava.utils.PreserveFullStackTraceOperator.preserveFullStackTrace;

class Example_9_View extends StrictThreadingJFrame implements IView<Example_9_ViewModel> {

    private final Example_9_View_StatusPanel statusPanel = new Example_9_View_StatusPanel();
    private final StrictThreadingJPanel mainPanel;
    private final Example_9_View_TitelPanel titlePanel;

    @Override
    public void bind(final Example_9_ViewModel viewModel) {
        statusPanel.bind(viewModel.vm2v_status);
        viewModel.vm2v_mainPanel
                .observeOn(SwingScheduler.getInstance())
                .lift(preserveFullStackTrace())
                .subscribe(mainContentViewModel -> {
                    if (mainContentViewModel instanceof Example_9_ViewModel_Step1) {
                        Example_9_View_Step1Panel step1Panel = new Example_9_View_Step1Panel();
                        step1Panel.bind((Example_9_ViewModel_Step1) mainContentViewModel);

                        mainPanel.removeAll();
                        mainPanel.add(step1Panel, BorderLayout.CENTER);
                        mainPanel.revalidate();
                    } else if (mainContentViewModel instanceof Example_9_ViewModel_Step2) {
                        Example_9_View_Step2Panel step2Panel = new Example_9_View_Step2Panel();
                        step2Panel.bind((Example_9_ViewModel_Step2) mainContentViewModel);

                        mainPanel.removeAll();
                        mainPanel.add(step2Panel, BorderLayout.CENTER);
                        mainPanel.revalidate();
                    } else if (mainContentViewModel instanceof Example_9_ViewModel_Step3) {
                        Example__View_Step3Panel step3Panel = new Example__View_Step3Panel();
                        step3Panel.bind((Example_9_ViewModel_Step3) mainContentViewModel);

                        mainPanel.removeAll();
                        mainPanel.add(step3Panel, BorderLayout.CENTER);
                        mainPanel.revalidate();
                    } else {
                        throw new IllegalStateException("Unhandled: " + mainContentViewModel);
                    }
                });
    }

    public Example_9_View() {
        super();
        setTitle(getClass().getSimpleName() + " " + ManagementFactory.getRuntimeMXBean().getName());

        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(StrictThreadingJFrame.EXIT_ON_CLOSE);

        titlePanel = new Example_9_View_TitelPanel();
        getContentPane().add(titlePanel, BorderLayout.NORTH);

        getContentPane().add(statusPanel, BorderLayout.SOUTH);

        mainPanel = new StrictThreadingJPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new BorderLayout(0, 0));
        mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
    }
}
