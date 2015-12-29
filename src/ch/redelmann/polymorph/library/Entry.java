package ch.redelmann.polymorph.library;

import ch.redelmann.polymorph.library.schema.Alphanumeric;
import ch.redelmann.polymorph.library.schema.Safe;
import ch.redelmann.polymorph.library.schema.Schema;
import org.json.JSONObject;

/** Contains a domain and the password schema used for that domain. */
public class Entry {

    /** The domain. */
    public final String domain;

    /** The schema used to generate passwords for that domain. */
    public final Schema schema;

    /** Builds an {@code Entry}.
     *
     * @param domain The domain on which the password is used.
     * @param schema The schema of the password.
     */
    public Entry(String domain, Schema schema) {
        this.domain = domain;
        this.schema = schema;
    }

    private static final String KEY_DOMAIN = "domain";
    private static final String KEY_SIZE = "size";
    private static final String KEY_SCHEMA = "schema";

    public static Entry fromJSON(JSONObject root) {
        assert(root.has(KEY_DOMAIN));

        String domain = root.getString(KEY_DOMAIN);
        String schemaName = root.optString(KEY_SCHEMA, Safe.NAME);

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

        return new Entry(domain, schema);
    }

    public JSONObject toJSON() {
        return new JSONObject()
                .put(KEY_DOMAIN, domain)
                .put(KEY_SIZE, schema.getSize())
                .put(KEY_SCHEMA, schema.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry that = (Entry) o;

        return this.domain.equals(that.domain) && this.schema.equals(that.schema);
    }

    @Override
    public int hashCode() {
        return domain.hashCode() + 31 * schema.hashCode();
    }
}
