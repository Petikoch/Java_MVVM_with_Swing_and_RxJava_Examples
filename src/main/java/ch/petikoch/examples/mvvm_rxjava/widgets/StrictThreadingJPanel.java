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
import javax.swing.border.Border;
import java.awt.*;

/**
 * A {@link JPanel} subclass, on which some methods are overloaded to enforce the correct threading.
 */
public class StrictThreadingJPanel extends JPanel {

    public StrictThreadingJPanel(final LayoutManager layout, final boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJPanel(final LayoutManager layout) {
        super(layout);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJPanel(final boolean isDoubleBuffered) {
        super(isDoubleBuffered);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJPanel() {
        GuiPreconditions.assertOnAwtEdt();
    }

    @Override
    public Component add(final Component comp) {
        GuiPreconditions.assertOnAwtEdt();
        return super.add(comp);
    }

    @Override
    public Component add(final Component comp, final int index) {
        GuiPreconditions.assertOnAwtEdt();
        return super.add(comp, index);
    }

    @Override
    public Component add(final String name, final Component comp) {
        GuiPreconditions.assertOnAwtEdt();
        return super.add(name, comp);
    }

    @Override
    public void remove(final Component comp) {
        GuiPreconditions.assertOnAwtEdt();
        super.remove(comp);
    }

    @Override
    public void removeAll() {
        GuiPreconditions.assertOnAwtEdt();
        super.removeAll();
    }

    @Override
    public void remove(final int index) {
        GuiPreconditions.assertOnAwtEdt();
        super.remove(index);
    }

    @Override
    public void setLayout(final LayoutManager mgr) {
        GuiPreconditions.assertOnAwtEdt();
        super.setLayout(mgr);
    }

    @Override
    public void setBorder(final Border border) {
        GuiPreconditions.assertOnAwtEdt();
        super.setBorder(border);
    }
}
