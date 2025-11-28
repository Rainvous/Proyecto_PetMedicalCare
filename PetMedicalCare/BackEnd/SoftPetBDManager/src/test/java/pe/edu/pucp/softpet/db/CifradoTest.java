package pe.edu.pucp.softpet.db;

import pe.edu.pucp.softpet.util.Cifrado;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CifradoTest {

    public CifradoTest() {
    }

    /**
     * Test of cifrarMD5 method, of class Cifrado.
     */
    @Test
    public void testCifrarMD5() {
        System.out.println("cifrarMD5");
        String texto = "FullChow#G3";
        String resultado = Cifrado.cifrarMD5(texto);
        System.out.println(resultado);
        assertNotNull(resultado);
    }

    /**
     * Test of descifrarMD5 method, of class Cifrado.
     */
    @Test
    public void testDescifrarMD5() {
        System.out.println("descifrarMD5");
        String textoEncriptado = "Gm5mkqhEb3d80sQVmaXiWg==";
        String resultado_esperado = "FullChow#G3";
        String resultado = Cifrado.descifrarMD5(textoEncriptado);
        System.out.println(resultado);
        assertEquals(resultado_esperado, resultado);
    }
}
