package ch.redelmann.polymorph.library;

import org.json.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

/** History contains a sorted collection of unique entries. */
public class History {

    /** The set of entries. */
    private final TreeSet<Entry> _entries;

    /** Builds an empty {@code History}. */
    public History() {
        _entries = new TreeSet<>(new Comparator<Entry>() {
            @Override
            public int compare(Entry o1, Entry o2) {
                // Compare by name.
                int compareDomain = String.CASE_INSENSITIVE_ORDER.compare(o1.domain, o2.domain);
                if (compareDomain != 0) {
                    return compareDomain;
                }

                // And then by schema name.
                int compareSchemaName = String.CASE_INSENSITIVE_ORDER.compare(o1.schema.getName(), o2.schema.getName());
                if (compareSchemaName != 0) {
                    return compareSchemaName;
                }

                // And finally schema size.
                return o1.schema.getSize() - o2.schema.getSize();
            }
        });
    }

    /** Builds an {@code History} from a list of entries.
     *
     * @param entries The list of entries. Does not need to be sorted.
     */
    public History(List<Entry> entries) {
        this();
        _entries.addAll(entries);
    }

    /** Loads an {@code History} from a JSON {@code InputStream}.
     *
     * @param input The input stream, containing a JSON description of the {@code History}.
     * @return the {@code History} loaded from the stream.
     * @throws JSONException if the stream does not contain a valid representation or throws an {@code IOException}.
     */
    public static History loadFrom(InputStream input) throws JSONException {
        ArrayList<Entry> entries = new ArrayList<>();
        JSONArray array = new JSONArray(new JSONTokener(input));
        for (int i = 0; i < array.length(); i++) {
            JSONObject object = array.optJSONObject(i);
            if (object != null) {
                Entry entry = Entry.fromJSON(object);
                entries.add(entry);
            }
        }
        return new History(entries);
    }

    /** Saves the JSON representation of {@code this} {@code History} to an output stream.
     *
     * @param output The output stream.
     * @throws IOException if the output throws an {@code IOException} when written.
     */
    public void saveTo(OutputStream output) throws IOException {
        Writer writer = new OutputStreamWriter(output);
        JSONWriter json = new JSONWriter(writer);
        json.array();
        for (Entry entry : _entries) {
            json.value(entry.toJSON());
        }
        json.endArray();
        writer.flush();
    }

    /** Adds an {@code entry}. Has no effect if the entry already exists.
     *
     * @param entry The entry to add.
     */
    public void add(Entry entry) {
        _entries.add(entry);
    }

    /** Removes an {@code entry}. Has no effect if the entry does not exist.
     *
     * @param entry The entry to remove.
     */
    public void remove(Entry entry) {
        _entries.remove(entry);
    }

    /** Returns all entries.
     *
     * @return all entries, sorted.
     */
    public List<Entry> getAll() {
        return new ArrayList<>(_entries);
    }
}
