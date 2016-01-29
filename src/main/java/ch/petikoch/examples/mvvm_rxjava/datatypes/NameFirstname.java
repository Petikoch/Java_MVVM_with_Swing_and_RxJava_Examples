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
package ch.petikoch.examples.mvvm_rxjava.datatypes;

public class NameFirstname {

    private final String name;
    private final String firstname;

    public NameFirstname(String name, String firstname) {
        this.name = name;
        this.firstname = firstname;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NameFirstname that = (NameFirstname) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(firstname != null ? !firstname.equals(that.firstname) : that.firstname != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "NameFirstname{" +
                "name='" + name + '\'' +
                ", firstname='" + firstname + '\'' +
                '}';
    }
}
