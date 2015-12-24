package ch.redelmann.polymorph.library;

/** Configuration parameters for the hash function. */
public class Configuration {

    /** Base 2 logarithm of the parameter N used by SCrypt. Running time and memory is proportional to N. */
    public final int logN;

    /** Memory cost r used by SCrypt. Running time and memory is proportional to r. */
    public final int r;

    /** Parallelization cost p used by SCrypt. Running time is proportional to p. Memory is not. */
    public final int p;

    /** Salt concatenated to the master password to help prevent rainbow tables attacks. */
    public final String code;

    // Default set of parameters.
    public static final int DEFAULT_LOG_N = 14;
    public static final int DEFAULT_R = 2;
    public static final int DEFAULT_P = 1;
    public static final String DEFAULT_CODE = "";

    public Configuration(int logN, int r, int p, String code) {
        assert(logN > 0 && r > 0 && p > 0);
        assert(r * p < (1 << 30));

        this.logN = logN;
        this.r = r;
        this.p = p;
        this.code = code;
    }
}
