/*
 * Copyright 2023 StreamThoughts.
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
package io.streamthoughts.kafka.connect.filepulse.avro.internal;

import org.apache.avro.Schema;
import org.apache.kafka.connect.data.SchemaBuilder;
import org.apache.kafka.connect.data.Timestamp;

public final class LongSchemaConverter extends AbstracConnectSchemaConverter {

    static final String AVRO_LOGICAL_TYPE_PROP = "logicalType";
    static final String AVRO_LOGICAL_TIMESTAMP_MILLIS = "timestamp-millis";

    LongSchemaConverter() {}

    /** {@inheritDoc} **/
    @Override
    public org.apache.kafka.connect.data.Schema toConnectSchema(Schema schema,
                                                                Options options,
                                                                CyclicContext context) {
        final SchemaBuilder builder;

        String logicalType = schema.getProp(AVRO_LOGICAL_TYPE_PROP);
        if (AVRO_LOGICAL_TIMESTAMP_MILLIS.equalsIgnoreCase(logicalType)) {
            builder = Timestamp.builder();
        } else {
            builder = SchemaBuilder.int64();
        }

        addSchemaMetadata(schema, options, builder);

        return builder;
    }
}
