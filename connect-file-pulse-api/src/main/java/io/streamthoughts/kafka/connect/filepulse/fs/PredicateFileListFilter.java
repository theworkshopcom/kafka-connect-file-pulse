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
package io.streamthoughts.kafka.connect.filepulse.fs;

import io.streamthoughts.kafka.connect.filepulse.source.FileObjectMeta;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A {@code PredicateFileListFilter} can be used to filter input files based on a given {@link Predicate}.
 */
public abstract class PredicateFileListFilter implements FileListFilter, Predicate<FileObjectMeta> {

    /**
     * {@inheritDoc}
     */
    @Override
    public final Collection<FileObjectMeta> filterFiles(final Collection<FileObjectMeta> files) {
        List<FileObjectMeta> accepted = new ArrayList<>();
        if (files != null) {
            accepted = files
            .stream()
            .filter(this)
            .collect(Collectors.toList());
        }
        return accepted;
    }
}