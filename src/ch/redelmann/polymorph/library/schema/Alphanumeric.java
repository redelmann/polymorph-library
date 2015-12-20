package ch.redelmann.polymorph.library.schema;

import ch.redelmann.polymorph.library.Generator;

/** Password schema that generate passwords consisting of
 *  a mix of upper and lower case letters, and digits.
 *
 *  The size of the generated passwords is between 4 and 20 characters long.
 *  By default, the passwords generated are 18 characters long.
 */
public class Alphanumeric extends Schema {

    /** Builds an alphanumeric schema with a default password size of 18. */
    public Alphanumeric() {
        super(18);
    }

    /** Builds an alphanumeric schema with a specified password size.
     *
     * @param size The size of the generated passwords, between 4 and 20 inclusive.
     */
    public Alphanumeric(int size) {
        super(size);

        assert(size >= 4 && size <= 20);
    }

    @Override
    protected String generate(Generator gen) {
        int minSize = 4;

        int size = Math.max(minSize, getSize());
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
