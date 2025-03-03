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
package io.streamthoughts.kafka.connect.filepulse.source;

import java.util.Arrays;

/**
 * An enum representing the status for a {@link FileObject}.
 */
public enum FileObjectStatus {
    /**
     * The file has been scheduled by the connector thread.
     */
    SCHEDULED,

    /**
     * The file can't be scheduled because it is not readable.
     */
    INVALID,

    /**
     * The file is starting to be processed by a task.
     */
    STARTED,

    /**
     * The is file is currently being read by a task.
     */
    READING,

    /**
     * The file processing is completed.
     */
    COMPLETED,

    /**
     * The completed file is committed.
     */
    COMMITTED,

    /**
     * The file processing failed.
     */
    FAILED,

    /**
     * The file has been successfully clean up (depending on the configured strategy).
     */
    CLEANED;

    public boolean isOneOf(final FileObjectStatus...states) {
        return Arrays.asList(states).contains(this);
    }

    public boolean isDone() {
        return isOneOf(COMMITTED, FAILED, CLEANED);
    }
}
