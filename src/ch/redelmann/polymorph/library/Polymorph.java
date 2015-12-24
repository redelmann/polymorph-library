package ch.redelmann.polymorph.library;

import ch.redelmann.polymorph.library.schema.Schema;
import com.lambdaworks.crypto.SCrypt;

import java.math.BigInteger;

/** Password generator using SCrypt. */
public class Polymorph {

    // Intended hash size.
    private final static int LENGTH = 64;

    /**
     * Derives a cryptographic hash using {@code SCrypt}.
     * This function can be compute intensive.
     *
     * @param domain        The domain for which the password is used.
     * @param password      The master password from which to derive other passwords.
     * @param configuration Configuration for SCrypt.
     * @return a cryptographically secure hash, in integer form.
     */
    private static BigInteger hash(byte[] domain, byte[] password, Configuration configuration) {
        try {
            byte[] salt = configuration.code.getBytes();
            // Concatenating the password and salt.
            byte[] passwordCode = new byte[password.length + salt.length];
            System.arraycopy(password, 0, passwordCode, 0, password.length);
            System.arraycopy(salt, 0, passwordCode, password.length, salt.length);

            // Using SCrypt to get a byte array hash.
            byte[] encrypted = SCrypt.scrypt(
                    passwordCode,
                    domain,
                    (1 << configuration.logN),
                    configuration.r,
                    configuration.p,
                    LENGTH);

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
     * Computes a password following the given {@code schema}.
     * This function makes use of {@code SCrypt} and thus can be very compute intensive.
     *
     * @param schema        The schema used to derive the password.
     * @param domain        The domain for which the derived password is used.
     * @param password      The master password.
     * @param configuration Configuration parameters for SCrypt.
     * @return a password following the given {@code schema}.
     */
    public static String derive(Schema schema, String domain, String password, Configuration configuration) {
        return schema.generate(hash(domain.getBytes(), password.getBytes(), configuration));
    }
}
