package ch.redelmann.polymorph.library;

import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class GeneratorTest {

    @Test
    public void testNextIntSeedZero() throws Exception {
        long seed = 0;
        Generator gen = new Generator(BigInteger.valueOf(seed));

        assertEquals(gen.nextInt(20), 0);
        assertEquals(gen.nextInt(40), 0);
        assertEquals(gen.nextInt(30), 0);
    }

    @Test
    public void testNextIntSeed() throws Exception {
        long seed = 217;

        seed *= 30;
        seed += 29;

        seed *= 40;
        seed += 11;

        seed *= 20;
        seed += 17;

        Generator gen = new Generator(BigInteger.valueOf(seed));

        assertEquals(gen.nextInt(20), 17);
        assertEquals(gen.nextInt(40), 11);
        assertEquals(gen.nextInt(30), 29);
    }

    @Test
    public void testNextBigInteger() throws Exception {
        long seed = 88;

        seed *= 137;
        seed += 111;

        seed *= 17;
        seed += 0;

        seed *= 45;
        seed += 44;

        Generator gen = new Generator(BigInteger.valueOf(seed));

        assertEquals(gen.nextBigInteger(BigInteger.valueOf(45)), BigInteger.valueOf(44));
        assertEquals(gen.nextBigInteger(BigInteger.valueOf(17)), BigInteger.valueOf(0));
        assertEquals(gen.nextBigInteger(BigInteger.valueOf(137)), BigInteger.valueOf(111));
    }

    @Test
    public void testNextIntRange() throws Exception {
        long seed = 314;

        seed *= 11;
        seed += 0;

        seed *= 14;
        seed += 10;

        seed *= 10;
        seed += 4;

        Generator gen = new Generator(BigInteger.valueOf(seed));

        assertEquals(gen.nextInt(100, 109), 104);
        assertEquals(gen.nextInt(200, 213), 210);
        assertEquals(gen.nextInt(312, 322), 312);
    }

    @Test
    public void testNextChar() throws Exception {
        long seed = 12;

        seed *= 26;
        seed += 'o' - 'a';

        seed *= 1;
        seed += 0;

        seed *= 24;
        seed += 'l' - 'b';

        seed *= 26;
        seed += 'e' - 'a';

        seed *= 26;
        seed += 'H' - 'A';

        Generator gen = new Generator(BigInteger.valueOf(seed));

        assertEquals(gen.nextChar('A', 'Z'), 'H');
        assertEquals(gen.nextChar('a', 'z'), 'e');
        assertEquals(gen.nextChar('b', 'y'), 'l');
        assertEquals(gen.nextChar('l', 'l'), 'l');
        assertEquals(gen.nextChar('a', 'z'), 'o');
    }

    @Test
    public void testNextUpperLetter() throws Exception {
        long seed = 872;

        seed *= 26;
        seed += 'D' - 'A';

        seed *= 26;
        seed += 'L' - 'A';

        seed *= 26;
        seed += 'R' - 'A';

        seed *= 26;
        seed += 'O' - 'A';

        seed *= 26;
        seed += 'W' - 'A';


        Generator gen = new Generator(BigInteger.valueOf(seed));

        assertEquals(gen.nextUpperLetter(), 'W');
        assertEquals(gen.nextUpperLetter(), 'O');
        assertEquals(gen.nextUpperLetter(), 'R');
        assertEquals(gen.nextUpperLetter(), 'L');
        assertEquals(gen.nextUpperLetter(), 'D');
    }

    @Test
    public void testNextLowerLetter() throws Exception {
        long seed = 872;

        seed *= 26;
        seed += 'D' - 'A';

        seed *= 26;
        seed += 'L' - 'A';

        seed *= 26;
        seed += 'R' - 'A';

        seed *= 26;
        seed += 'O' - 'A';

        seed *= 26;
        seed += 'W' - 'A';


        Generator gen = new Generator(BigInteger.valueOf(seed));

        assertEquals(gen.nextLowerLetter(), 'w');
        assertEquals(gen.nextLowerLetter(), 'o');
        assertEquals(gen.nextLowerLetter(), 'r');
        assertEquals(gen.nextLowerLetter(), 'l');
        assertEquals(gen.nextLowerLetter(), 'd');
    }

    @Test
    public void testNextSpecialChar() throws Exception {
        long seed = 131;

        seed *= 32;
        seed += 31;

        seed *= 32;
        seed += 1;

        seed *= 32;
        seed += 0;

        Generator gen = new Generator(BigInteger.valueOf(seed));

        assertEquals(gen.nextSpecialChar(), '!');
        assertEquals(gen.nextSpecialChar(), '"');
        assertEquals(gen.nextSpecialChar(), '~');
    }

    @Test
    public void testNextDigit() throws Exception {
        long seed = 213;

        seed *= 10;
        seed += 9;

        seed *= 10;
        seed += 5;

        seed *= 10;
        seed += 0;

        Generator gen = new Generator(BigInteger.valueOf(seed));

        assertEquals(gen.nextDigit(), '0');
        assertEquals(gen.nextDigit(), '5');
        assertEquals(gen.nextDigit(), '9');
    }

    @Test
    public void testNextInString() throws Exception {
        long seed = 968;

        seed *= 6;
        seed += 4;

        seed *= 6;
        seed += 2;

        seed *= 6;
        seed += 1;

        seed *= 6;
        seed += 3;

        seed *= 6;
        seed += 5;

        Generator gen = new Generator(BigInteger.valueOf(seed));
        String from = "ROMAIN";

        assertEquals(gen.nextInString(from), 'N');
        assertEquals(gen.nextInString(from), 'A');
        assertEquals(gen.nextInString(from), 'O');
        assertEquals(gen.nextInString(from), 'M');
        assertEquals(gen.nextInString(from), 'I');
    }

    @Test
    public void testNextPermutation() throws Exception {

        long seed = 12;

        seed *= 720;
        seed += 0;

        seed *= 720;
        seed += 719;

        seed *= 720;
        seed += 533;

        Generator gen = new Generator(BigInteger.valueOf(seed));
        String from = "ROMAIN";

        assertEquals(gen.nextPermutation(from), "NAOMIR");
        assertEquals(gen.nextPermutation(from), "NIAMOR");
        assertEquals(gen.nextPermutation(from), "ROMAIN");
    }
}