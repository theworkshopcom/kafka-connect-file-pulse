/*
 * Copyright 2019-2020 StreamThoughts.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.streamthoughts.kafka.connect.filepulse.expression;

import io.streamthoughts.kafka.connect.filepulse.errors.ConnectFilePulseException;

public class ExpressionException extends ConnectFilePulseException {

    /**
     * Creates a new {@link ExpressionException} instance.
     *
     * @param message   the message to be used.
     */
    public ExpressionException(final String message) {
        super(message);
    }
}
