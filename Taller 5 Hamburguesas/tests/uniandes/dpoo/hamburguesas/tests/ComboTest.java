package uniandes.dpoo.hamburguesas.tests;
import uniandes.dpoo.hamburguesas.mundo.Combo;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ComboTest
{
    private Combo combo;

    @BeforeEach
    void prepararPrueba()
    {
        ArrayList<ProductoMenu> productos = new ArrayList<>();
        productos.add(new ProductoMenu("Hamburguesa sencilla", 12000));
        productos.add(new ProductoMenu("Papas medianas", 8000));
        combo = new Combo("Especial", 0.25, productos);
    }

    @Test
    void testGetNombre()
    {
        assertEquals("Especial", combo.getNombre());
    }

    @Test
    void testGetPrecio()
    {
        assertEquals(15000, combo.getPrecio());
    }

    @Test
    void testGenerarTextoFactura()
    {
        String textoEsperado = "Combo Especial\n" + " Descuento: 0.25\n" + "            15000\n";
        assertEquals(textoEsperado, combo.generarTextoFactura());
    }
}