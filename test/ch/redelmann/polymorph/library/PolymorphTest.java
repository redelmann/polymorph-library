package ch.redelmann.polymorph.library;

import ch.redelmann.polymorph.library.schema.Safe;
import ch.redelmann.polymorph.library.schema.Schema;
import ch.redelmann.polymorph.library.schema.Alphanumeric;
import org.junit.Test;

import static org.junit.Assert.*;

public class PolymorphTest {

    private static Configuration getDefaultConfigWithCode(String code) {
        return new Configuration(
                Configuration.DEFAULT_LOG_N,
                Configuration.DEFAULT_R,
                Configuration.DEFAULT_P,
                code);
    }

    @Test
    public void safeLargeSizePasswords() {
        Schema safe = new Safe(18);

        String password = "pony1234";
        String code = "AGDE2-DGXA4-33DLQ-WEDAP-GYPQ9";

        assertEquals(
                Polymorph.derive(safe, "github", password, getDefaultConfigWithCode(code)),
                "5pZYE8$jY5nY]j}3|#");
        assertEquals(
                Polymorph.derive(safe, "facebook", password, getDefaultConfigWithCode(code)),
                "l9_75Uc*fiW.Sfdo0!");
    }

    @Test
    public void safeSmallSizePasswords() {
        Schema safe = new Safe(8);

        String password = "pony1234";
        String code = "AGDE2-DGXA4-33DLQ-WEDAP-GYPQ9";

        assertEquals(
                Polymorph.derive(safe, "github", password, getDefaultConfigWithCode(code)),
                "Ri6z5c]N");
        assertEquals(
                Polymorph.derive(safe, "facebook", password, getDefaultConfigWithCode(code)),
                "Kh0Zp~te");
    }

    @Test
    public void alphaLargeSizePasswords() {
        Schema alpha = new Alphanumeric(18);

        String password = "pony1234";
        String code = "AGDE2-DGXA4-33DLQ-WEDAP-GYPQ9";

        assertEquals(
                Polymorph.derive(alpha, "github", password, getDefaultConfigWithCode(code)),
                "ZVeiS4aVernaTqf343");
        assertEquals(
                Polymorph.derive(alpha, "facebook", password, getDefaultConfigWithCode(code)),
                "2lFatbXftwy0a09pjM");
    }

    @Test
    public void alphaSmallSizePasswords() {
        Schema alpha = new Alphanumeric(4);

        String password = "pony1234";
        String code = "AGDE2-DGXA4-33DLQ-WEDAP-GYPQ9";

        assertEquals(
                Polymorph.derive(alpha, "github", password, getDefaultConfigWithCode(code)),
                "oJk9");
        assertEquals(
                Polymorph.derive(alpha, "facebook", password, getDefaultConfigWithCode(code)),
                "T7qi");
    }
}