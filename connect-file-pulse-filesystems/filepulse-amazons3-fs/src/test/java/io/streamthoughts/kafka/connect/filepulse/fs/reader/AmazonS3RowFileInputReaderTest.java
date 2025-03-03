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
package io.streamthoughts.kafka.connect.filepulse.fs.reader;

import io.streamthoughts.kafka.connect.filepulse.data.TypedStruct;
import io.streamthoughts.kafka.connect.filepulse.fs.AmazonS3Storage;
import io.streamthoughts.kafka.connect.filepulse.fs.BaseAmazonS3Test;
import io.streamthoughts.kafka.connect.filepulse.fs.S3BucketKey;
import io.streamthoughts.kafka.connect.filepulse.reader.FileInputIterator;
import io.streamthoughts.kafka.connect.filepulse.reader.RecordsIterable;
import io.streamthoughts.kafka.connect.filepulse.source.FileRecord;
import io.streamthoughts.kafka.connect.filepulse.source.GenericFileObjectMeta;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class AmazonS3RowFileInputReaderTest extends BaseAmazonS3Test {

    private static final String LF = "\n";

    private static final int NLINES = 10;

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private File objectFile;

    private AmazonS3RowFileInputReader reader;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        objectFile = testFolder.newFile();
        BufferedWriter writer = Files.newBufferedWriter(objectFile.toPath(), Charset.defaultCharset());
        generateLines(writer);

        reader = new AmazonS3RowFileInputReader();
        reader.setStorage(new AmazonS3Storage(client));
        reader.configure(unmodifiableCommonsProperties);
    }

    @Override
    public void tearDown() throws Exception {
        super.tearDown();
        reader.close();
    }

    @Test
    public void should_read_all_lines() {
        client.createBucket(S3_TEST_BUCKET);
        client.putObject(S3_TEST_BUCKET, "my-key", objectFile);

        final GenericFileObjectMeta meta = new GenericFileObjectMeta.Builder()
                .withUri(new S3BucketKey(S3_TEST_BUCKET, "my-key").toURI())
                .build();

        final FileInputIterator<FileRecord<TypedStruct>> iterator = reader.newIterator(meta.uri());
        List<FileRecord<TypedStruct>> results = new ArrayList<>();
        while (iterator.hasNext()) {
            final RecordsIterable<FileRecord<TypedStruct>> next = iterator.next();
            results.addAll(next.collect());
        }
        Assert.assertEquals(10, results.size());
    }

    private void generateLines(final BufferedWriter writer) throws IOException {

        for (int i = 0; i < NLINES; i++) {
            String line = "00000000-" + i;
            writer.write(line);
            if (i + 1 < NLINES) {
                writer.write(LF);
            }
        }
        writer.flush();
    }
}