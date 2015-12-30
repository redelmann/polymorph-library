package ch.redelmann.polymorph.library;

import org.json.JSONObject;

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

    // JSON parameter names.
    private static String KEY_LOG_N = "log_n";
    private static String KEY_R = "r";
    private static String KEY_P = "p";
    private static String KEY_CODE = "code";

    /** Gets a {@code Configuration} from its JSON representation.
     *
     * @param root The JSON object representing the configuration.
     * @return the configuration represented.
     */
    public static Configuration fromJSON(JSONObject root) {
        int logN = root.optInt(KEY_LOG_N, DEFAULT_LOG_N);
        int r = root.optInt(KEY_R, DEFAULT_R);
        int p = root.optInt(KEY_P, DEFAULT_P);
        String code = root.optString(KEY_CODE, DEFAULT_CODE);

        return new Configuration(logN, r, p, code);
    }

    /** Gets the JSON representation of this {@code Configuration}.
     *
     * @return the JSON representation of this {@code Configuration}.
     */
    public JSONObject toJSON() {
        return new JSONObject()
                .put(KEY_LOG_N, logN)
                .put(KEY_R, r)
                .put(KEY_P, p)
                .put(KEY_CODE, code);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Configuration that = (Configuration) o;

        if (logN != that.logN) return false;
        if (r != that.r) return false;
        if (p != that.p) return false;
        return code.equals(that.code);

    }

    @Override
    public int hashCode() {
        int result = logN;
        result = 31 * result + r;
        result = 31 * result + p;
        result = 31 * result + code.hashCode();
        return result;
    }
}
