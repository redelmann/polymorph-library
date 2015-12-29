package ch.redelmann.polymorph.library.schema;

import ch.redelmann.polymorph.library.Generator;

/** Password schema that generate passwords consisting of
 *  a mix of upper and lower case letters, and digits.
 *
 *  The size of the generated passwords is at least 3 characters long.
 *  By default, the passwords generated are 18 characters long.
 */
public class Alphanumeric extends Schema {

    public static final String NAME = "alpha";

    public static final int DEFAULT_SIZE = 18;

    /** Builds an alphanumeric schema with a default password size of 18. */
    public Alphanumeric() {
        super(DEFAULT_SIZE);
    }

    /** Builds an alphanumeric schema with a specified password size.
     *
     * @param size The size of the generated passwords, at least 3 characters long.
     */
    public Alphanumeric(int size) {
        super(size);

        assert(size >= 3);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    protected String generate(Generator gen) {

        int size = getSize();
        int min = Math.max(1, size / 8);
        int max = 1 + (size - 1) / 4;

        int nUpper = gen.nextInt(min, max);
        int nDigit = gen.nextInt(min, max);

        int nLower = size - nUpper - nDigit;

        StringBuilder output = new StringBuilder(size);

        for (int i = 0; i < nUpper; i++) {
            output.append(gen.nextUpperLetter());
        }
        for (int i = 0; i < nDigit; i++) {
            output.append(gen.nextDigit());
        }
        for (int i = 0; i < nLower; i++) {
            output.append(gen.nextLowerLetter());
        }

        return gen.nextPermutation(output.toString());
    }
}
