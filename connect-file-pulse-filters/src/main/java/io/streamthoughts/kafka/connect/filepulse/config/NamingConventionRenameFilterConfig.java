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
package io.streamthoughts.kafka.connect.filepulse.config;

import static io.streamthoughts.kafka.connect.filepulse.config.NamingConvention.CAMEL_CASE;

import java.util.Map;
import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;


public class NamingConventionRenameFilterConfig extends AbstractConfig {
    public static final String DEFAULT_FIELD_NAMING_CONVENTION = "default.field.naming.convention";
    public static final String DEFAULT_FIELD_NAMING_CONVENTION_DELIMITER_CONFIG = "default.field.naming.convention.delimiter";
    private static final String DEFAULT_FIELD_NAMING_CONVENTION_DOC = "Default field naming convention, possible values are: camelCase, snakeCase, pascalCase";
    private static final String DEFAULT_FIELD_NAMING_CONVENTION_DELIMITER_DOC_TEMPLATE = "Set of characters to determine casing of the field, default values are %s";

    private static final String defaultDelimiter = "_ ,-()[]{}";

    public NamingConventionRenameFilterConfig(Map<?, ?> originals) {
        super(getConfigDef(), originals);
    }

    public String getDefaultNamingConvention() {
        return getString(DEFAULT_FIELD_NAMING_CONVENTION);
    }

    public char[] getColumnHeaderDelimiters() {
        return getString(DEFAULT_FIELD_NAMING_CONVENTION_DELIMITER_CONFIG).toCharArray();
    }

    public static ConfigDef getConfigDef() {
        return new ConfigDef()
                .define(DEFAULT_FIELD_NAMING_CONVENTION,
                        ConfigDef.Type.STRING,
                        CAMEL_CASE.getConfigValue(),
                        ConfigDef.Importance.HIGH,
                        DEFAULT_FIELD_NAMING_CONVENTION_DOC)
                .define(DEFAULT_FIELD_NAMING_CONVENTION_DELIMITER_CONFIG,
                        ConfigDef.Type.STRING,
                        defaultDelimiter,
                        ConfigDef.Importance.HIGH,
                        String.format(DEFAULT_FIELD_NAMING_CONVENTION_DELIMITER_DOC_TEMPLATE, defaultDelimiter));
    }
}
