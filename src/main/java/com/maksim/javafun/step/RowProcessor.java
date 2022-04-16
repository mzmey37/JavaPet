package com.maksim.javafun.step;

import com.maksim.javafun.model.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class RowProcessor implements ItemProcessor<Row, Row> {
    private static final Logger log = LoggerFactory.getLogger(RowProcessor.class);

    @Override
    public Row process(final Row row) throws Exception {
        final String key = row.getKey().toLowerCase();
        final String value = row.getValue().toLowerCase();
        final Row newRow = new Row(key, value);
        log.info("Converting " + row + " into " + newRow);
        return newRow;
    }
}
