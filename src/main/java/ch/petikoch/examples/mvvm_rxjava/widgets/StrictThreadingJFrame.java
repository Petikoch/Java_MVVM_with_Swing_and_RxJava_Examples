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
package ch.petikoch.examples.mvvm_rxjava.widgets;

import ch.petikoch.examples.mvvm_rxjava.utils.GuiPreconditions;

import javax.swing.*;
import java.awt.*;

/**
 * A {@link JFrame} subclass, on which some methods are overloaded to enforce the correct threading.
 */
public class StrictThreadingJFrame extends JFrame {

    public StrictThreadingJFrame() throws HeadlessException {
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJFrame(final GraphicsConfiguration gc) {
        super(gc);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJFrame(final String title) throws HeadlessException {
        super(title);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJFrame(final String title, final GraphicsConfiguration gc) {
        super(title, gc);
        GuiPreconditions.assertOnAwtEdt();
    }

    @Override
    public void setVisible(final boolean b) {
        GuiPreconditions.assertOnAwtEdt();
        super.setVisible(b);
    }
}
