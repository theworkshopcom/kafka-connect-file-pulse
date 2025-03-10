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
package io.streamthoughts.kafka.connect.filepulse.storage;


/**
 * Generic interface for callbacks
 */
public interface Callback<V> {

    /**
     * Invoked upon completion of the operation.
     *
     * @param error the error that caused the operation to fail, or null if no error occurred
     * @param result the return value, or null if the operation failed
     */
    void onCompletion(Throwable error, V result);
}