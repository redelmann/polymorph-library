package ch.redelmann.polymorph.library;

import ch.redelmann.polymorph.library.schema.Schema;

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
