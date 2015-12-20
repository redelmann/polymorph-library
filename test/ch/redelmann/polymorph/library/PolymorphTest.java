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

        assertEquals(Polymorph.derive(safe, "github", password, code), "k2'8n?QXwmptbJ7D44");
        assertEquals(Polymorph.derive(safe, "facebook", password, code), "{g6e2hsKjh#Ra*\\ks(");
    }

    @Test
    public void safeCustomSizePasswords() {
        Schema safe = new Safe(8);

        String password = "pony1234";
        String code = "AGDE2-DGXA4-33DLQ-WEDAP-GYPQ9";

        assertEquals(Polymorph.derive(safe, "github", password, code), "a,:6ss7Z");
        assertEquals(Polymorph.derive(safe, "facebook", password, code), "Mnae3r&g");
    }

    @Test
    public void alphaDefaultSizePasswords() {
        Schema alpha = new Alphanumeric();

        String password = "pony1234";
        String code = "AGDE2-DGXA4-33DLQ-WEDAP-GYPQ9";

        assertEquals(Polymorph.derive(alpha, "github", password, code), "il8cw9gtNQMKo2d9h9");
        assertEquals(Polymorph.derive(alpha, "facebook", password, code), "wcyfzsaa0gndb5vQpT");
    }

    @Test
    public void alphaCustomSizePasswords() {
        Schema alpha = new Alphanumeric(4);

        String password = "pony1234";
        String code = "AGDE2-DGXA4-33DLQ-WEDAP-GYPQ9";

        assertEquals(Polymorph.derive(alpha, "github", password, code), "Y5lv");
        assertEquals(Polymorph.derive(alpha, "facebook", password, code), "7pSa");
    }
}