package ch.redelmann.polymorph.library;

import ch.redelmann.polymorph.library.schema.Alphanumeric;
import ch.redelmann.polymorph.library.schema.Safe;
import ch.redelmann.polymorph.library.schema.Schema;
import org.json.JSONObject;

import java.util.Objects;

/** Contains a domain and the password schema used for that domain. */
public class Entry {

    /** The domain. */
    public final String domain;

    /** The schema used to generate passwords for that domain. */
    public final Schema schema;

    /** The version of the password. */
    public final int version;

    /** Builds an {@code Entry}.
     *
     * @param domain The domain on which the password is used.
     * @param schema The schema of the password.
     */
    public Entry(String domain, Schema schema, int version) {
        this.domain = domain;
        this.schema = schema;
        this.version = version;
    }

    private static final String KEY_DOMAIN = "domain";
    private static final String KEY_SIZE = "size";
    private static final String KEY_SCHEMA = "schema";
    private static final String KEY_VERSION = "version";

    public static Entry fromJSON(JSONObject root) {
        assert(root.has(KEY_DOMAIN));

        String domain = root.getString(KEY_DOMAIN);
        String schemaName = root.optString(KEY_SCHEMA, Safe.NAME);
        int version = root.optInt(KEY_VERSION, 0);

        Schema schema;
        switch (schemaName) {
            case Safe.NAME: {
                int schemaSize = root.optInt(KEY_SIZE, Safe.DEFAULT_SIZE);
                schema = new Safe(schemaSize);
                break;
            }
            case Alphanumeric.NAME: {
                int schemaSize = root.optInt(KEY_SIZE, Alphanumeric.DEFAULT_SIZE);
                schema = new Alphanumeric(schemaSize);
                break;
            }
            default:
                throw new IllegalArgumentException("Unknown schema name " + schemaName);
        }

        return new Entry(domain, schema, version);
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .put(KEY_DOMAIN, domain)
                .put(KEY_SIZE, schema.getSize())
                .put(KEY_SCHEMA, schema.getName())
                .put(KEY_VERSION, version);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return version == entry.version &&
                domain.equals(entry.domain) &&
                schema.equals(entry.schema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domain, schema, version);
    }
}
