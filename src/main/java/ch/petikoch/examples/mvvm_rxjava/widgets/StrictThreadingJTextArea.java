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
import javax.swing.text.Document;

/**
 * A {@link JTextArea} subclass, on which some methods are overloaded to enforce the correct threading.
 */
public class StrictThreadingJTextArea extends JTextArea {

    public StrictThreadingJTextArea() {
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJTextArea(final String text) {
        super(text);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJTextArea(final int rows, final int columns) {
        super(rows, columns);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJTextArea(final String text, final int rows, final int columns) {
        super(text, rows, columns);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJTextArea(final Document doc) {
        super(doc);
        GuiPreconditions.assertOnAwtEdt();
    }

    public StrictThreadingJTextArea(final Document doc, final String text, final int rows, final int columns) {
        super(doc, text, rows, columns);
        GuiPreconditions.assertOnAwtEdt();
    }

    @Override
    public String getText() {
        GuiPreconditions.assertOnAwtEdt();
        return super.getText();
    }

    @Override
    public void setText(final String t) {
        GuiPreconditions.assertOnAwtEdt();
        super.setText(t);
    }

    @Override
    public boolean isEnabled() {
        GuiPreconditions.assertOnAwtEdt();
        return super.isEnabled();
    }

    @Override
    public void setEnabled(final boolean enabled) {
        GuiPreconditions.assertOnAwtEdt();
        super.setEnabled(enabled);
    }
}
