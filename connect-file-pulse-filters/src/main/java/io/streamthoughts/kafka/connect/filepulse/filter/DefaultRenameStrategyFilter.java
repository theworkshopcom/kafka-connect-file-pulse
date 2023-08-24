/*
 * Copyright 2023 StreamThoughts.
 *
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.streamthoughts.kafka.connect.filepulse.filter;

import static io.streamthoughts.kafka.connect.filepulse.config.RenameStrategyType.buildRenameStrategyNotFoundErrorMsg;

import io.streamthoughts.kafka.connect.filepulse.config.DefaultRenameStrategyFilterConfig;
import io.streamthoughts.kafka.connect.filepulse.config.RenameStrategyType;
import io.streamthoughts.kafka.connect.filepulse.data.TypedField;
import io.streamthoughts.kafka.connect.filepulse.data.TypedStruct;
import io.streamthoughts.kafka.connect.filepulse.reader.RecordsIterable;
import java.util.Iterator;
import java.util.Map;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;
import org.apache.commons.text.CaseUtils;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class DefaultRenameStrategyFilter extends AbstractRecordFilter<DefaultRenameStrategyFilter> {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultRenameStrategyFilter.class);
    private DefaultRenameStrategyFilterConfig config;

    @Override
    public void configure(final Map<String, ?> props) {
        super.configure(props);
        config = new DefaultRenameStrategyFilterConfig(props);
    }

    @Override
    public ConfigDef configDef() {
        return DefaultRenameStrategyFilterConfig.getConfigDef();
    }

    @Override
    public RecordsIterable<TypedStruct> apply(final FilterContext context,
                                              final TypedStruct record,
                                              final boolean hasNext) throws FilterException {
        RenameStrategyType renameStrategy = RenameStrategyType.getByConfigValue(config.getDefaultRenameStrategy());
        Iterator<TypedField> typedFieldsIterator = record.schema().iterator();

        StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(typedFieldsIterator, Spliterator.ORDERED), false)
                .map(TypedField::name)
                .peek(name -> LOG.trace("Renaming field {} to {}", name, renameColumn(name, renameStrategy)))
                .forEach(name -> record.rename(name, renameColumn(name, renameStrategy)));

        return new RecordsIterable<>(record);
    }

    String renameColumn(String columnName, RenameStrategyType renameStrategy) {
        switch (renameStrategy) {
            case CAMEL_CASE:
                return toCamelCase(columnName);
            case PASCAL_CASE:
                return toPascalCase(columnName);
            case SNAKE_CASE:
                return toSnakeCase(columnName);
            default:
                throw new ConfigException(buildRenameStrategyNotFoundErrorMsg(renameStrategy.getConfigValue()));
        }
    }

    private String toCamelCase(String columnName) {
        return CaseUtils.toCamelCase(columnName, false, config.getColumnHeaderDelimiters());
    }

    private String toPascalCase(String columnName) {
        return CaseUtils.toCamelCase(columnName, true, config.getColumnHeaderDelimiters());
    }

    private String toSnakeCase(String columnName) {
        return camelCaseToSnakeCase(toCamelCase(columnName));
    }

    private String camelCaseToSnakeCase(String str) {
        String regex = "([a-z0-9])([A-Z]+)";
        String replacement = "$1_$2";

        return str.replaceAll(regex, replacement).toLowerCase();
    }
}