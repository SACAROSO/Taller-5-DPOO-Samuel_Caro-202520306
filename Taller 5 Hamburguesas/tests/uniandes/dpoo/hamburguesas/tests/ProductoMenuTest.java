package uniandes.dpoo.hamburguesas.tests;

import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductoMenuTest
{
    private ProductoMenu producto;

    @BeforeEach
    void prepararPrueba()
    {
        producto = new ProductoMenu("Hamburguesa sencilla", 12000);
    }

    @Test
    void testGetNombre()
    {
        assertEquals("Hamburguesa sencilla", producto.getNombre());
    }
    
    @Test
    void testGetPrecio()
    {
        assertEquals(12000, producto.getPrecio());
    }

    @Test
    void testGenerarTextoFactura()
    {
        String textoEsperado = "Hamburguesa sencilla\n" + "            12000\n";
        assertEquals(textoEsperado, producto.generarTextoFactura());
    }
}