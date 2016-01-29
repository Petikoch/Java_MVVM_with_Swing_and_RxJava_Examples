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
package ch.petikoch.examples.mvvm_rxjava.example5a;

import ch.petikoch.examples.mvvm_rxjava.utils.SwingUtilities2;
import ch.petikoch.examples.mvvm_rxjava.utils.SysOutUtils;
import ch.petikoch.examples.mvvm_rxjava.utils.UncaughtExceptionHandlerInitializer;

import javax.swing.*;
import java.lang.management.ManagementFactory;

class Example_5a_Main {

    public static void main(String[] args) {
        SysOutUtils.sysout(ManagementFactory.getRuntimeMXBean().getName());
        UncaughtExceptionHandlerInitializer.initUncaughtExceptionHandler();

        Example_5a_Model model = new Example_5a_Model();
        Example_5a_ViewModel viewModel = new Example_5a_ViewModel();
        viewModel.connectTo(model);

        SwingUtilities2.invokeLater(() -> {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            Example_5a_View view = new Example_5a_View();
            view.bind(viewModel);
            view.setVisible(true);
        });
    }
}
