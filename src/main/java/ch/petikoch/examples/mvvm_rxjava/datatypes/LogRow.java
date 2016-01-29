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

public class LogRow {

    private final String timestamp;
    private final String status;
    private final String text;

    public LogRow(String timestamp, String status, String text) {
        this.timestamp = timestamp;
        this.status = status;
        this.text = text;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getStatus() {
        return status;
    }

    public String getText() {
        return text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LogRow logRow = (LogRow) o;

        if (timestamp != null ? !timestamp.equals(logRow.timestamp) : logRow.timestamp != null)
            return false;
        if (status != null ? !status.equals(logRow.status) : logRow.status != null) return false;
        return !(text != null ? !text.equals(logRow.text) : logRow.text != null);

    }

    @Override
    public int hashCode() {
        int result = timestamp != null ? timestamp.hashCode() : 0;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "LogRow{" +
                "timestamp='" + timestamp + '\'' +
                ", status='" + status + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
