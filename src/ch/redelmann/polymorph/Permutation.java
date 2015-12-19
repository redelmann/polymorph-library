package ch.redelmann.polymorph;

import java.math.BigInteger;

/** Utility class to compute permutations of strings. */
public class Permutation {

    /** Computes the kth permutation of a {@code string}.
     *
     * @param string The initial string.
     * @param k      The permutation number. Must be non-negative.
     * @return the kth permutation of the {@code string}.
     */
    public static String getKthPermutation(String string, BigInteger k) {

        // Only works for positive integers.
        assert(k.compareTo(BigInteger.ZERO) >= 0);

        // Computes the radix representation of k.
        int size = string.length();
        int[] radices = new int[size];

        for (int i = 0; i < size; i++) {
            BigInteger n = BigInteger.valueOf(size - i);
            BigInteger[] divRem = k.divideAndRemainder(n);
            radices[i] = divRem[1].intValue();
            k = divRem[0];
        }

        // Converting the radix representation to direct representation
        for (int i = size - 1; i >= 0; i--) {
            for (int j = i + 1; j < size; j++) {
                if (radices[j] >= radices[i]) {
                    radices[j]++;
                }
            }
        }

        // Building the out string given the permutations.
        StringBuilder output = new StringBuilder(size);
        for (int i : radices) {
            output.append(string.charAt(i));
        }

        return output.toString();
    }
}
