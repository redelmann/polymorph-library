package ch.redelmann.polymorph.library;

import ch.redelmann.polymorph.library.schema.Safe;
import ch.redelmann.polymorph.library.schema.Schema;
import ch.redelmann.polymorph.library.schema.Alphanumeric;
import org.junit.Test;

import static org.junit.Assert.*;

public class PolymorphTest {

    @Test
    public void safeDefaultSizePasswords() {
        Schema safe = new Safe();

        String password = "pony1234";
        String code = "AGDE2-DGXA4-33DLQ-WEDAP-GYPQ9";

        assertEquals(Polymorph.derive(safe, "github", password, code), "5pZYE8$jY5nY]j}3|#");
        assertEquals(Polymorph.derive(safe, "facebook", password, code), "l9_75Uc*fiW.Sfdo0!");
    }

    @Test
    public void safeCustomSizePasswords() {
        Schema safe = new Safe(8);

        String password = "pony1234";
        String code = "AGDE2-DGXA4-33DLQ-WEDAP-GYPQ9";

        assertEquals(Polymorph.derive(safe, "github", password, code), "Ri6z5c]N");
        assertEquals(Polymorph.derive(safe, "facebook", password, code), "Kh0Zp~te");
    }

    @Test
    public void alphaDefaultSizePasswords() {
        Schema alpha = new Alphanumeric();

        String password = "pony1234";
        String code = "AGDE2-DGXA4-33DLQ-WEDAP-GYPQ9";

        assertEquals(Polymorph.derive(alpha, "github", password, code), "ZVeiS4aVernaTqf343");
        assertEquals(Polymorph.derive(alpha, "facebook", password, code), "2lFatbXftwy0a09pjM");
    }

    @Test
    public void alphaCustomSizePasswords() {
        Schema alpha = new Alphanumeric(4);

        String password = "pony1234";
        String code = "AGDE2-DGXA4-33DLQ-WEDAP-GYPQ9";

        assertEquals(Polymorph.derive(alpha, "github", password, code), "oJk9");
        assertEquals(Polymorph.derive(alpha, "facebook", password, code), "T7qi");
    }
}