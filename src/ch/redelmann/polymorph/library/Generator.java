package ch.redelmann.polymorph.library;

import java.math.BigInteger;

/** Deterministic generator.
 *
 * Contains an inner state from which values can be consumed.
 */
public class Generator {

    /** Current state of the generator. */
    private BigInteger _value;

    /** Builds a generator using an initial seed.
     *
     * @param seed The initial seed.
     */
    public Generator(BigInteger seed) {
        _value = seed;
    }

    /** Generates an integer between 0 inclusive and {@code numberOfValues} exclusive.
     *
     * @param numberOfValues The number of values to choose from. Must be strictly positive.
     * @return an integer between 0 inclusive and {@code numberOfValues} exclusive.
     */
    public int nextInt(int numberOfValues) {
        return nextBigInteger(BigInteger.valueOf(numberOfValues)).intValue();
    }

    /** Generates an integer between 0 inclusive and {@code numberOfValues} exclusive.
     *
     * @param numberOfValues The number of values to choose from. Must be strictly positive.
     * @return an integer between 0 inclusive and {@code numberOfValues} exclusive.
     */
    public BigInteger nextBigInteger(BigInteger numberOfValues) {
        assert(numberOfValues.compareTo(BigInteger.ZERO) > 0);

        BigInteger[] divRem = _value.divideAndRemainder(numberOfValues);
        _value = divRem[0];
        return divRem[1];
    }

    /** Generates an integer between {@code min} and {@code max} inclusive.
     *
     * @param min The minimal integer that can be generated.
     * @param max The maximal integer that can be generated.
     * @return an integer between {@code min} and {@code max} inclusive.
     */
    public int nextInt(int min, int max) {
        assert(min <= max);

        int delta = max - min;

        if (delta == 0) {
            return min;
        }

        int n = nextInt(delta + 1);

        return min + n;
    }

    /** Generates a character between {@code min} and {@code max} inclusive.
     *
     * @param min The minimal character that can be generated.
     * @param max The maximal character that can be generated.
     * @return a character between {@code min} and {@code max} inclusive.
     */
    public char nextChar(char min, char max) {
        return (char)nextInt(min, max);
    }

    /** Generates an upper case letter.
     *
     * @return one of the 26 uppercase letters.
     */
    public char nextUpperLetter() {
        return nextChar('A', 'Z');
    }

    /** Generates an upper case letter.
     *
     * @return one of the 26 lowercase letters.
     */
    public char nextLowerLetter() {
        return nextChar('a', 'z');
    }

    /** Generates a special character.
     *
     * @return a printable ASCII special character.
     */
    public char nextSpecialChar() {
        return nextInString("!\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~");
    }

    /** Generates a digit.
     *
     * @return a character between {@code '0'} and {@code '9'}.
     */
    public char nextDigit() {
        return nextChar('0', '9');
    }

    /** Generates a character from the given string.
     *
     * @param string The string from which characters are taken.
     * @return one of the characters of the string.
     */
    public char nextInString(String string) {
        int i = nextInt(string.length());
        return string.charAt(i);
    }

    /** Generates a permutation of the input {@code string}.
     *
     * @param string The string from which to take a permutation.
     *               The size of the string must not exceed 20 characters.
     * @return a permutation of the input {@code string}.
     */
    public String nextPermutation(String string) {

        // Computing the number of permutations.
        BigInteger numberPermutations = BigInteger.ONE;
        for (long i = 2; i <= string.length(); i++) {
            numberPermutations = numberPermutations.multiply(BigInteger.valueOf(i));
        }

        // Picking one of those permutations.
        BigInteger k = nextBigInteger(numberPermutations);
        return Permutation.getKthPermutation(string, k);
    }
}
