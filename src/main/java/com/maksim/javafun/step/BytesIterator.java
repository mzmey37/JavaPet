package com.maksim.javafun.step;

import org.apache.lucene.search.suggest.InputIterator;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BytesIterator<T> implements InputIterator {

    private List<? extends T> bytesList;
    private int currentInd;

    BytesIterator(List<? extends T> stringList) {
        this.bytesList = stringList;
        this.currentInd = 0;
    }

    @Override
    public long weight() {
        return 1;
    }

    @Override
    public BytesRef payload() {
        return null;
    }

    @Override
    public boolean hasPayloads() {
        return false;
    }

    @Override
    public Set<BytesRef> contexts() {
        return null;
    }

    @Override
    public boolean hasContexts() {
        return false;
    }

    @Override
    public BytesRef next() throws IOException {
        if (currentInd < bytesList.size()) {
            currentInd += 1;
            return new BytesRef(bytesList.get(currentInd - 1).toString().getBytes());
        }
        return null;
    }
}
