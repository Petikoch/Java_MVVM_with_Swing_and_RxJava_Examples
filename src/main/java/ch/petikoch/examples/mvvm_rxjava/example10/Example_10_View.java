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
package ch.petikoch.examples.mvvm_rxjava.example10;

import ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.IView;
import ch.petikoch.examples.mvvm_rxjava.widgets.StrictThreadingJCheckBox;
import ch.petikoch.examples.mvvm_rxjava.widgets.StrictThreadingJFrame;
import ch.petikoch.examples.mvvm_rxjava.widgets.StrictThreadingJLabel;

import java.awt.*;
import java.lang.management.ManagementFactory;

import static ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.RxSwingView2ViewModelBinder.bindSwingView;
import static ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.RxViewModel2SwingViewBinder.bindViewModelString;

class Example_10_View extends StrictThreadingJFrame implements IView<Example_10_ViewModel> {

    private final StrictThreadingJCheckBox editCheckBox;
    private final StrictThreadingJLabel customerLabel;
    private final Example_10_View_AddressPanel addressPanel;
    private final Example_10_View_NotesPanel notesPanel;

    @Override
    public void bind(final Example_10_ViewModel viewModel) {
        bindSwingView(editCheckBox).toViewModel(viewModel.v2vm_edit);
        bindViewModelString(viewModel.vm2v_customer).toSwingViewLabel(customerLabel);

        addressPanel.bind(viewModel.addressViewModel);
        notesPanel.bind(viewModel.notesViewModel);
    }

    public Example_10_View() {
        super();
        setTitle(getClass().getSimpleName() + " " + ManagementFactory.getRuntimeMXBean().getName());

        setBounds(100, 100, 450, 300);
        setDefaultCloseOperation(StrictThreadingJFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);

        editCheckBox = new StrictThreadingJCheckBox("Edit");
        GridBagConstraints gbc_editCheckBox = new GridBagConstraints();
        gbc_editCheckBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_editCheckBox.insets = new Insets(5, 5, 5, 5);
        gbc_editCheckBox.gridx = 0;
        gbc_editCheckBox.gridy = 0;
        getContentPane().add(editCheckBox, gbc_editCheckBox);

        customerLabel = new StrictThreadingJLabel("");
        GridBagConstraints gbc_customerLabel = new GridBagConstraints();
        gbc_customerLabel.anchor = GridBagConstraints.EAST;
        gbc_customerLabel.insets = new Insets(5, 0, 5, 5);
        gbc_customerLabel.gridx = 1;
        gbc_customerLabel.gridy = 0;
        getContentPane().add(customerLabel, gbc_customerLabel);

        addressPanel = new Example_10_View_AddressPanel();
        GridBagConstraints gbc_addressPanel = new GridBagConstraints();
        gbc_addressPanel.insets = new Insets(0, 0, 5, 5);
        gbc_addressPanel.gridwidth = 2;
        gbc_addressPanel.fill = GridBagConstraints.BOTH;
        gbc_addressPanel.gridx = 0;
        gbc_addressPanel.gridy = 1;
        getContentPane().add(addressPanel, gbc_addressPanel);

        notesPanel = new Example_10_View_NotesPanel();
        GridBagConstraints gbv_notesPanel = new GridBagConstraints();
        gbv_notesPanel.gridwidth = 2;
        gbv_notesPanel.insets = new Insets(0, 0, 5, 5);
        gbv_notesPanel.fill = GridBagConstraints.BOTH;
        gbv_notesPanel.gridx = 0;
        gbv_notesPanel.gridy = 2;
        getContentPane().add(notesPanel, gbv_notesPanel);
    }
}
