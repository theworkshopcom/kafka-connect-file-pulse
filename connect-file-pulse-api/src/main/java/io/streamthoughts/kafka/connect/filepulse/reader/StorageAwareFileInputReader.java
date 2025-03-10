/*
 * Copyright 2019-2021 StreamThoughts.
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
package io.streamthoughts.kafka.connect.filepulse.reader;

import io.streamthoughts.kafka.connect.filepulse.fs.Storage;
import io.streamthoughts.kafka.connect.filepulse.fs.StorageProvider;
import io.streamthoughts.kafka.connect.filepulse.source.FileObjectMeta;
import java.net.URI;

public interface StorageAwareFileInputReader<T extends Storage> extends StorageProvider<T>, FileInputReader {

    /**
     * {@inheritDoc}
     */
    default FileObjectMeta getObjectMetadata(final URI objectURI) {
        return storage().getObjectMetadata(objectURI);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    default boolean canBeRead(final URI objectURI) {
        return storage().exists(objectURI);
    }
}
