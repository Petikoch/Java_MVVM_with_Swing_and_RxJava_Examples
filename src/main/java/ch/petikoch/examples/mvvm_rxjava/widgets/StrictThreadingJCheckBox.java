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

/**
 * Eine {@link JCheckBox} Subklasse, bei welcher bei bestimmten Methoden das korrekte "Threading" forciert wird.
 */
public class StrictThreadingJCheckBox extends JCheckBox {

    public StrictThreadingJCheckBox() {
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJCheckBox(final Icon icon) {
        super(icon);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJCheckBox(final Icon icon, final boolean selected) {
        super(icon, selected);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJCheckBox(final String text) {
        super(text);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJCheckBox(final Action a) {
        super(a);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJCheckBox(final String text, final boolean selected) {
        super(text, selected);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJCheckBox(final String text, final Icon icon) {
        super(text, icon);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJCheckBox(final String text, final Icon icon, final boolean selected) {
        super(text, icon, selected);
        GuiPreconditions.assertOnAwtEdt();
    }

    @Override
    public void setEnabled(final boolean enabled) {
        GuiPreconditions.assertOnAwtEdt();
        super.setEnabled(enabled);
    }

    @Override
    public boolean isSelected() {
        GuiPreconditions.assertOnAwtEdt();
        return super.isSelected();
    }

    @Override
    public void setSelected(final boolean b) {
        GuiPreconditions.assertOnAwtEdt();
        super.setSelected(b);
    }
}
