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
package ch.petikoch.examples.mvvm_rxjava.utils;

import com.google.common.base.Throwables;

import javax.annotation.Nullable;

public class SysOutUtils {

    public static void sysout(String text) {
        System.out.println("[" + Thread.currentThread().getName() + "] " + text);
    }

    public static void syserr(String text, @Nullable Throwable throwable) {
        String output = "[" + Thread.currentThread().getName() + "] " + text;
        if (throwable != null) {
            output += "\n" + Throwables.getStackTraceAsString(throwable);
        }
        System.err.println(output);
    }
}
