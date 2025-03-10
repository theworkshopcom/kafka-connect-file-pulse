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

import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;

/**
 * An object representing a single record value that was read from an input file.
 *
 * @param <T>   the record-value type.
 */
public interface FileRecord<T> {

    /**
     * Gets the record value.
     *
     * @return  the value.
     */
    T value();

    /**
     * Returns the offset this record.
     *
     * @return a {@link FileRecordOffset} instance.
     */
    FileRecordOffset offset();

    /**
     * Transforms this file record into a connect {@link SourceRecord} instance.
     *
     * @param sourcePartition       the source partition.
     * @param sourceOffset          the source offset.
     * @param metadata              the {@link LocalFileObjectMeta} to be used.
     * @param defaultTopic          the default topic to be used.
     * @param defaultPartition      the default partition to be used.
     * @param connectSchemaSupplier the connect Schema to be used which can be {@code null}.
     *
     * @return                  the new {@link SourceRecord} instance.
     */
    SourceRecord toSourceRecord(
            final Map<String, ?> sourcePartition,
            final Map<String, ?> sourceOffset,
            final FileObjectMeta metadata,
            final String defaultTopic,
            final Integer defaultPartition,
            final Function<String, Schema> connectSchemaSupplier,
            final ConnectSchemaMapperOptions options
           );


    class ConnectSchemaMapperOptions {

        private final boolean connectSchemaMergeEnabled;
        private final boolean keepSchemaLeadingUnderscore;

        private final Pattern connectSchemaConditionTopicPattern;

        public ConnectSchemaMapperOptions(final boolean connectSchemaMergeEnabled,
                                          final boolean keepSchemaLeadingUnderscore,
                                          final Pattern connectSchemaConditionTopicPattern) {
            this.connectSchemaMergeEnabled = connectSchemaMergeEnabled;
            this.keepSchemaLeadingUnderscore = keepSchemaLeadingUnderscore;
            this.connectSchemaConditionTopicPattern = connectSchemaConditionTopicPattern;
        }

        public boolean isConnectSchemaMergeEnabled() {
            return connectSchemaMergeEnabled;
        }

        public boolean isKeepSchemaLeadingUnderscore() {
            return keepSchemaLeadingUnderscore;
        }

        public Pattern getConnectSchemaConditionTopicPattern() {
            return connectSchemaConditionTopicPattern;
        }
    }
}
