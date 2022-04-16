package com.maksim.javafun.step;

import org.apache.lucene.search.suggest.fst.WFSTCompletionLookup;
import org.apache.lucene.store.ByteBuffersDirectory;
import org.apache.lucene.util.BytesRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WFSTWriter<T> implements ItemWriter<T> {
    private final Logger log = LoggerFactory.getLogger(WFSTWriter.class);
    @Value("${writer.outputFileName}")
    private String outputFileName;

    @Override
    public void write(List<? extends T> list) throws Exception {
        BytesIterator<T> bytesIterator = new BytesIterator<T>(list);
        WFSTCompletionLookup wfstCompletionLookup = new WFSTCompletionLookup(new ByteBuffersDirectory(), "prefix", true);
        wfstCompletionLookup.build(bytesIterator);
        wfstCompletionLookup.store(new FileOutputStream(outputFileName));
    }
}
