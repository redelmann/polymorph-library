package ch.redelmann.polymorph.library;

import ch.redelmann.polymorph.library.schema.Alphanumeric;
import ch.redelmann.polymorph.library.schema.Safe;
import org.json.JSONException;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class HistoryTest {

    @Test
    public void testGetAll() {
        Entry entry1 = new Entry("github", new Safe(18), 0);
        Entry entry2 = new Entry("google", new Safe(25), 0);
        Entry entry3 = new Entry("facebook", new Alphanumeric(12), 1);
        Entry entry4 = new Entry("google", new Safe(12), 3);
        Entry entry5 = new Entry("google", new Alphanumeric(17), 1);

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(entry1);
        entries.add(entry2);
        entries.add(entry3);
        entries.add(entry4);
        entries.add(entry5);

        History history = new History(entries);

        // Get all returns all elements sorted.
        List<Entry> all = history.getAll();
        assertEquals(all.size(), 5);
        assertEquals(all.get(0), entry3);
        assertEquals(all.get(1), entry1);
        assertEquals(all.get(2), entry5);
        assertEquals(all.get(3), entry4);
        assertEquals(all.get(4), entry2);
    }

    @Test
    public void testJSON() throws Exception {
        Entry entry1 = new Entry("github", new Safe(18), 0);
        Entry entry2 = new Entry("google", new Safe(25), 0);
        Entry entry3 = new Entry("facebook", new Alphanumeric(12), 0);

        List<Entry> entries = new ArrayList<>();
        entries.add(entry1);
        entries.add(entry2);
        entries.add(entry3);

        History history = new History(entries);

        String json = "[{\"schema\":\"alpha\",\"size\":12,\"domain\":\"facebook\",\"version\":0}," +
                      "{\"schema\":\"safe\",\"size\":18,\"domain\":\"github\",\"version\":0}," +
                      "{\"schema\":\"safe\",\"size\":25,\"domain\":\"google\",\"version\":0}]";
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        history.saveTo(output);
        assertEquals(json, output.toString());

        ByteArrayInputStream input = new ByteArrayInputStream(json.getBytes());

        history = History.loadFrom(input);
        entries = history.getAll();
        assertEquals(entries.size(), 3);
        assertEquals(entries.get(0), entry3);
        assertEquals(entries.get(1), entry1);
        assertEquals(entries.get(2), entry2);
    }

    @Test(expected = JSONException.class)
    public void testIncompleteJSON() throws Exception {
        String json =  "[{\"schema\":\"alpha\",";
        ByteArrayInputStream input = new ByteArrayInputStream(json.getBytes());
        History.loadFrom(input);
    }

    @Test(expected = JSONException.class)
    public void testRootObjectJSON() throws Exception {
        String json =  "{\"schema\":\"alpha\",\"domain\":\"facebook\",\"size\":12}";
        ByteArrayInputStream input = new ByteArrayInputStream(json.getBytes());
        History.loadFrom(input);
    }

    @Test(expected = IOException.class)
    public void testIOExceptionInInputStream() throws Exception {
        String json = "[{\"schema\":\"alpha\",\"size\":12,\"domain\":\"facebook\",\"version\":0}," +
                "{\"schema\":\"safe\",\"size\":18,\"domain\":\"github\",\"version\":0}," +
                "{\"schema\":\"safe\",\"size\":25,\"domain\":\"google\",\"version\":0}]";
        InputStream input = new IOExceptionInputStream(new ByteArrayInputStream(json.getBytes()), 10);
        History.loadFrom(input);
    }

    @Test
    public void testAddRemove() {
        Entry entry1 = new Entry("github", new Safe(18), 0);
        Entry entry2 = new Entry("google", new Safe(25), 1);
        Entry entry3 = new Entry("facebook", new Alphanumeric(12), 3);


        List<Entry> entries = new ArrayList<>();
        entries.add(entry1);
        entries.add(entry2);

        History history = new History(entries);

        assertEquals(history.getAll().size(), 2);

        history.add(entry3);

        assertEquals(history.getAll().size(), 3);

        history.add(entry1);

        assertEquals(history.getAll().size(), 3);

        history.remove(entry1);

        assertEquals(history.getAll().size(), 2);

        history.remove(entry1);

        assertEquals(history.getAll().size(), 2);

        history.remove(entry3);

        assertEquals(history.getAll().size(), 1);

        history.add(entry1);

        assertEquals(history.getAll().size(), 2);
    }
}


class IOExceptionInputStream extends InputStream {

    private final int _chars;
    private final InputStream _input;
    private int _read = 0;

    public IOExceptionInputStream(InputStream input, int chars) {
        _input = input;
        _chars = chars;
    }

    @Override
    public long skip(long n) throws IOException {
        if (_read + n > _chars) {
            throw new IOException();
        }
        _read += n;
        return _input.skip(n);
    }

    @Override
    public int available() throws IOException {
        return _input.available();
    }

    @Override
    public void close() throws IOException {
        _input.close();
    }

    @Override
    public synchronized void mark(int read_limit) {
        _input.mark(read_limit);
    }

    @Override
    public synchronized void reset() throws IOException {
        _input.reset();
    }

    @Override
    public boolean markSupported() {
        return _input.markSupported();
    }

    @Override
    public int read() throws IOException {
        if (_read >= _chars) {
            throw new IOException();
        }
        _read += 1;
        return _input.read();
    }
}