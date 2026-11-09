package uniandes.dpoo.hamburguesas.tests;

import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.FileNotFoundException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.io.TempDir;

public class PedidoTest
{
    private Pedido pedido;
    @TempDir
    Path carpetaTemporal;
    
    @BeforeEach
    void prepararPrueba()
    {
        pedido = new Pedido("Ana", "Calle 10");
    }

    @Test
    void testGetNombreCliente()
    {
        assertEquals("Ana", pedido.getNombreCliente());
    }

    @Test
    void testIdentificadoresConsecutivos()
    {
        Pedido segundoPedido = new Pedido("Luis", "Calle 20");
        assertEquals(pedido.getIdPedido() + 1, segundoPedido.getIdPedido());
    }

    @Test
    void testPrecioTotalPedidoVacio()
    {
        assertEquals(0, pedido.getPrecioTotalPedido());
    }

    @Test
    void testPrecioTotalPedidoConProductos()
    {
        pedido.agregarProducto(new ProductoMenu("Hamburguesa sencilla", 12000));
        pedido.agregarProducto(new ProductoMenu("Papas medianas", 8000));
        assertEquals(23800, pedido.getPrecioTotalPedido());
    }
    
    @Test
    void testGenerarTextoFactura()
    {
        pedido.agregarProducto(new ProductoMenu("Hamburguesa sencilla", 12000));
        pedido.agregarProducto(new ProductoMenu("Papas medianas", 8000));

        String textoEsperado = "Cliente: Ana\n" + "Dirección: Calle 10\n" + "----------------\n" + "Hamburguesa sencilla\n" + "            12000\n" 
        + "Papas medianas\n" + "            8000\n" + "----------------\n" + "Precio Neto:  20000\n" + "IVA:          3800\n"+ "Precio Total: 23800\n";
        assertEquals(textoEsperado, pedido.generarTextoFactura());
    }

    @Test
    void testGuardarFactura() throws java.io.IOException
    {
        pedido.agregarProducto(new ProductoMenu("Hamburguesa sencilla", 12000));
        Path archivoFactura = carpetaTemporal.resolve("factura.txt");
        pedido.guardarFactura(archivoFactura.toFile());
        String textoGuardado = Files.readString(archivoFactura, Charset.defaultCharset());
        assertEquals(pedido.generarTextoFactura(), textoGuardado);
    }

    @Test
    void testGuardarFacturaEnCarpetaInexistente()
    {
        Path archivoFactura = carpetaTemporal.resolve("inexistente").resolve("factura.txt");
        assertThrows(FileNotFoundException.class, () -> pedido.guardarFactura(archivoFactura.toFile()));
    }
}