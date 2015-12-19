package ch.redelmann.polymorph;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class PermutationTest {

    @Test
    public void testEmptyString() throws Exception {
        assertEquals(Permutation.getKthPermutation("", BigInteger.valueOf(0)), "");
        assertEquals(Permutation.getKthPermutation("", BigInteger.valueOf(1)), "");
        assertEquals(Permutation.getKthPermutation("", BigInteger.valueOf(17)), "");
    }

    @Test(expected = AssertionError.class)
    public void testNegativeIndex() throws Exception {
        Permutation.getKthPermutation("1234", BigInteger.valueOf(-1));
    }

    @Test
    public void testEmptyPermutations() throws Exception {

        String[] permutations = new String[]{
                "perm",
                "eprm",
                "rpem",
                "mper",
                "prem",
                "erpm",
                "repm",
                "mepr",
                "pmer",
                "empr",
                "rmpe",
                "mrpe",
                "pemr",
                "epmr",
                "rpme",
                "mpre",
                "prme",
                "ermp",
                "remp",
                "merp",
                "pmre",
                "emrp",
                "rmep",
                "mrep"
        };

        for (int i = 0; i < 100; i++) {
            assertEquals(
                    Permutation.getKthPermutation("perm", BigInteger.valueOf(i)),
                    permutations[i % permutations.length]);
        }
    }
}