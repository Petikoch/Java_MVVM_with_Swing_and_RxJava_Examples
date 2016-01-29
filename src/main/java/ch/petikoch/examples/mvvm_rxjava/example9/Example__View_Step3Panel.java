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
import ch.petikoch.examples.mvvm_rxjava.widgets.StrictThreadingJLabel;
import ch.petikoch.examples.mvvm_rxjava.widgets.StrictThreadingJPanel;

import javax.swing.*;
import java.awt.*;

import static ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.RxSwingView2ViewModelBinder.bindSwingView;
import static ch.petikoch.examples.mvvm_rxjava.rxjava_mvvm.RxViewModel2SwingViewBinder.bindViewModelString;

public class Example__View_Step3Panel extends StrictThreadingJPanel implements IView<Example_9_ViewModel_Step3> {

    private final StrictThreadingJLabel displayedNameLabel;
    private final StrictThreadingJLabel displayedFirstnameLabel;
    private final StrictThreadingJButton restartButton;

    @Override
    public void bind(final Example_9_ViewModel_Step3 viewModel) {
        bindViewModelString(viewModel.vm_name).toSwingViewLabel(displayedNameLabel);
        bindViewModelString(viewModel.vm_firstname).toSwingViewLabel(displayedFirstnameLabel);
        bindSwingView(restartButton).toViewModel(viewModel.v2vm_restartButtonEvents);
    }

    public Example__View_Step3Panel() {
        super();

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        JLabel titelLabel = new JLabel("Your input:");
        GridBagConstraints gbc_titelLabel = new GridBagConstraints();
        gbc_titelLabel.anchor = GridBagConstraints.WEST;
        gbc_titelLabel.gridwidth = 2;
        gbc_titelLabel.insets = new Insets(0, 0, 5, 5);
        gbc_titelLabel.gridx = 0;
        gbc_titelLabel.gridy = 0;
        add(titelLabel, gbc_titelLabel);

        JLabel nameLabel = new JLabel("Name");
        GridBagConstraints gbc_nameLabel = new GridBagConstraints();
        gbc_nameLabel.anchor = GridBagConstraints.WEST;
        gbc_nameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_nameLabel.gridx = 0;
        gbc_nameLabel.gridy = 1;
        add(nameLabel, gbc_nameLabel);

        displayedNameLabel = new StrictThreadingJLabel();
        displayedNameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD, 14));
        displayedNameLabel.setForeground(Color.BLUE);
        GridBagConstraints gbc_nameTextField = new GridBagConstraints();
        gbc_nameTextField.gridwidth = 2;
        gbc_nameTextField.insets = new Insets(0, 0, 5, 0);
        gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameTextField.gridx = 1;
        gbc_nameTextField.gridy = 1;
        add(displayedNameLabel, gbc_nameTextField);

        JLabel firstnameLabel = new JLabel("Firstname");
        GridBagConstraints gbc_firstnameLabel = new GridBagConstraints();
        gbc_firstnameLabel.anchor = GridBagConstraints.WEST;
        gbc_firstnameLabel.insets = new Insets(0, 0, 5, 5);
        gbc_firstnameLabel.gridx = 0;
        gbc_firstnameLabel.gridy = 2;
        add(firstnameLabel, gbc_firstnameLabel);

        displayedFirstnameLabel = new StrictThreadingJLabel();
        displayedFirstnameLabel.setFont(displayedNameLabel.getFont());
        displayedFirstnameLabel.setForeground(displayedNameLabel.getForeground());
        GridBagConstraints gbc_firstnameTextField = new GridBagConstraints();
        gbc_firstnameTextField.gridwidth = 2;
        gbc_firstnameTextField.insets = new Insets(0, 0, 5, 0);
        gbc_firstnameTextField.fill = GridBagConstraints.HORIZONTAL;
        gbc_firstnameTextField.gridx = 1;
        gbc_firstnameTextField.gridy = 2;
        add(displayedFirstnameLabel, gbc_firstnameTextField);

        restartButton = new StrictThreadingJButton("Restart");
        GridBagConstraints gbc_restartButton = new GridBagConstraints();
        gbc_restartButton.anchor = GridBagConstraints.EAST;
        gbc_restartButton.gridx = 2;
        gbc_restartButton.gridy = 4;
        add(restartButton, gbc_restartButton);
    }
}
