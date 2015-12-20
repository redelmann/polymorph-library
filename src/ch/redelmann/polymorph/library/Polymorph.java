package ch.redelmann.polymorph.library;

import ch.redelmann.polymorph.library.schema.Schema;
import com.lambdaworks.crypto.SCrypt;

import java.math.BigInteger;

/** Password generator using SCrypt. */
public class Polymorph {

    // SCrypt parameters.
    private final static int N = 16384;    // CPU cost parameter. Has to be a power of 2.
    private final static int R = 8;        // Memory cost parameter.
    private final static int P = 50;       // Parallelization parameter.
    private final static int LENGTH = 64;  // Intended hash size.

    /**
     * Derives a cryptographic hash using SCrypt.
     * This function is compute intensive.
     *
     * @param domain   The domain for which the password is used.
     * @param password The master password from which to derive other passwords.
     * @param salt     Extra salt, to prevent rainbow table attacks.
     * @return a cryptographically secure hash, in integer form.
     */
    private static BigInteger hash(byte[] domain, byte[] password, byte[] salt) {
        try {
            // Concatenating the password and salt.
            byte[] passwordCode = new byte[password.length + salt.length];
            System.arraycopy(password, 0, passwordCode, 0, password.length);
            System.arraycopy(salt, 0, passwordCode, password.length, salt.length);

            // Using SCrypt to get a byte array hash.
            byte[] encrypted = SCrypt.scrypt(passwordCode, domain, N, R, P, LENGTH);

            // Converting the byte array to a large integer.
            BigInteger result = BigInteger.ZERO;
            BigInteger factor = BigInteger.ONE;
            BigInteger range = BigInteger.valueOf(256);
            for (int i : encrypted) {
                BigInteger current = BigInteger.valueOf(i & 0xFF).multiply(factor);
                result = result.add(current);
                factor = factor.multiply(range);
            }
            return result;
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid parameters for SCrypt.", e);
        }
    }

    /**
     * Derives a cryptographic hash using SCrypt.
     * This function is compute intensive.
     *
     * @param schema   The schema to use for the password.
     * @param domain   The domain for which the password is used.
     * @param password The master password from which to derive other passwords.
     * @param salt     Extra salt, to prevent rainbow table attacks.
     * @return a secure password following the schema.
     */
    public static String derive(Schema schema, String domain, String password, String salt) {
        return schema.generate(hash(domain.getBytes(), password.getBytes(), salt.getBytes()));
    }
}
