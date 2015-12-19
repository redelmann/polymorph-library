package ch.redelmann.polymorph.schema;

import ch.redelmann.polymorph.Generator;

import java.math.BigInteger;

/** Schemas are used to define how to generate passwords using an initial seed. */
public abstract class Schema {

    /** The intended size of the generated password. */
    private int _size;

    /**
     * Builds a Schema.
     *
     * @param size The intended size of the passwords generated.
     */
    public Schema(int size) {
        _size = size;
    }

    /**
     * Returns the intended size of the passwords generated.
     *
     * @return the intended size of passwords.
     */
    protected final int getSize() {
        return _size;
    }

    /**
     * Generates a password from an integer seed.
     *
     * @param seed The integer seed.
     * @return a password following the schema.
     */
    public final String generate(BigInteger seed) {
        return generate(new Generator(seed));
    }

    /**
     * Generates a password from a {@link Generator}.
     *
     * @param gen The generator used.
     * @return a password following the schema.
     */
    protected abstract String generate(Generator gen);
}