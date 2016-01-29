/*
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
package ch.petikoch.examples.mvvm_rxjava

import org.junit.runner.notification.RunNotifier
import org.junit.runners.model.InitializationError
import org.spockframework.runtime.RunContext
import org.spockframework.runtime.Sputnik

import javax.swing.*
import java.util.concurrent.CountDownLatch

// Experimental
// Well: GUI widgets are created/modified/... by using the "right" thread (AWT-EDT)
// Not so well: There is only one GUI thread per JVM -> No parallel execution of multiple these kind of tests
class AwtEdtSputnik extends Sputnik {

    AwtEdtSputnik(final Class<?> clazz) throws InitializationError {
        super(clazz);
    }

    void run(RunNotifier notifier) {
        def latch = new CountDownLatch(1)

        SwingUtilities.invokeLater() {
            super.runExtensionsIfNecessary();
            super.generateSpecDescriptionIfNecessary();
            RunContext.get().createSpecRunner(super.getSpec(), notifier).run();

            latch.countDown()
        }

        latch.await()
    }
}
