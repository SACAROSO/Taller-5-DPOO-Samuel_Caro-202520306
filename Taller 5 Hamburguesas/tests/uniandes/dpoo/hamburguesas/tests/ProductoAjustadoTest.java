package uniandes.dpoo.hamburguesas.tests;

import uniandes.dpoo.hamburguesas.mundo.ProductoAjustado;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import uniandes.dpoo.hamburguesas.mundo.Ingrediente;

public class ProductoAjustadoTest
{
    @Test
    void testGetPrecioSinAjustes()
    {
        ProductoMenu productoBase = new ProductoMenu("Hamburguesa sencilla", 12000);
        ProductoAjustado productoAjustado = new ProductoAjustado(productoBase);
        assertEquals(12000, productoAjustado.getPrecio());
    }
    
    @Test
    void testGetPrecioConIngredienteAgregado()
    {
        ProductoMenu productoBase = new ProductoMenu("Hamburguesa sencilla", 12000);
        ProductoAjustado productoAjustado = new ProductoAjustado(productoBase);
        Ingrediente queso = new Ingrediente("queso", 1500);
        productoAjustado.agregarIngrediente(queso);
        assertEquals(13500, productoAjustado.getPrecio());
    }
    
    @Test
    void testGetPrecioConIngredienteEliminado()
    {
        ProductoMenu productoBase = new ProductoMenu("Hamburguesa sencilla", 12000);
        ProductoAjustado productoAjustado = new ProductoAjustado(productoBase);
        Ingrediente tomate = new Ingrediente("tomate", 1000);
        productoAjustado.eliminarIngrediente(tomate);
        assertEquals(12000, productoAjustado.getPrecio());
    }
    
    @Test
    void testGetNombre()
    {
        ProductoMenu productoBase = new ProductoMenu("Hamburguesa sencilla", 12000);
        ProductoAjustado productoAjustado = new ProductoAjustado(productoBase);
        assertEquals("Hamburguesa sencilla", productoAjustado.getNombre());
    }

    @Test
    void testGenerarTextoFactura()
    {
        ProductoMenu productoBase = new ProductoMenu("Hamburguesa sencilla", 12000);
        ProductoAjustado productoAjustado = new ProductoAjustado(productoBase);
        productoAjustado.agregarIngrediente(new Ingrediente("queso", 1500));
        productoAjustado.eliminarIngrediente(new Ingrediente("tomate", 1000));
        String textoEsperado = "Hamburguesa sencilla\n" + "            12000\n" + "    +queso\n" + "                1500\n" + "    -tomate\n" + "            13500\n";
        assertEquals(textoEsperado, productoAjustado.generarTextoFactura());
    }
}