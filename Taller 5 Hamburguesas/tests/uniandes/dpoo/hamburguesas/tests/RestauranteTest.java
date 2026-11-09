package uniandes.dpoo.hamburguesas.tests;

import uniandes.dpoo.hamburguesas.excepciones.NoHayPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.excepciones.YaHayUnPedidoEnCursoException;
import uniandes.dpoo.hamburguesas.mundo.Pedido;
import uniandes.dpoo.hamburguesas.mundo.Restaurante;
import uniandes.dpoo.hamburguesas.mundo.ProductoMenu;
import uniandes.dpoo.hamburguesas.excepciones.IngredienteRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoRepetidoException;
import uniandes.dpoo.hamburguesas.excepciones.ProductoFaltanteException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;


public class RestauranteTest
{
    private Restaurante restaurante;

    @BeforeEach
    void prepararPrueba()
    {
        restaurante = new Restaurante();
    }

    @Test
    void testRestauranteSinPedidoEnCurso()
    {
        assertNull(restaurante.getPedidoEnCurso());
    }

    @Test
    void testIniciarPedido() throws YaHayUnPedidoEnCursoException
    {
        restaurante.iniciarPedido("Ana", "Calle 10");
        assertEquals("Ana", restaurante.getPedidoEnCurso().getNombreCliente());
    }

    @Test
    void testIniciarPedidoCuandoYaHayUnoEnCurso() throws YaHayUnPedidoEnCursoException
    {
        restaurante.iniciarPedido("Ana", "Calle 10");
        Pedido pedidoOriginal = restaurante.getPedidoEnCurso();
        assertThrows(YaHayUnPedidoEnCursoException.class, () -> restaurante.iniciarPedido("Luis", "Calle 20"));
        assertSame(pedidoOriginal, restaurante.getPedidoEnCurso());
    }

    @Test
    void testCerrarSinPedidoEnCurso()
    {
        assertThrows(NoHayPedidoEnCursoException.class, () -> restaurante.cerrarYGuardarPedido());
    }
    
    @Test
    void testCerrarYGuardarPedido() throws Exception
    {
        restaurante.iniciarPedido("Ana", "Calle 10");
        Pedido pedido = restaurante.getPedidoEnCurso();
        pedido.agregarProducto(new ProductoMenu("Hamburguesa sencilla", 12000));
        File factura = new File("facturas/factura_" + pedido.getIdPedido() + ".txt");
        restaurante.cerrarYGuardarPedido();
        assertTrue(factura.isFile());
        assertEquals(1, restaurante.getPedidos().size());
        assertSame(pedido, restaurante.getPedidos().get(0));
        assertNull(restaurante.getPedidoEnCurso());
    }
    
    @Test
    void testCargarInformacionRestaurante() throws Exception
    {
        File archivoIngredientes = new File( "data/ingredientes.txt" );
        File archivoMenu = new File( "data/menu.txt" );
        File archivoCombos = new File( "data/combos.txt" );
        restaurante.cargarInformacionRestaurante( archivoIngredientes, archivoMenu, archivoCombos );
        assertEquals( 15, restaurante.getIngredientes().size() );
        assertEquals( "lechuga", restaurante.getIngredientes().get( 0 ).getNombre() );
        assertEquals( 1000, restaurante.getIngredientes().get( 0 ).getCostoAdicional() );
        assertEquals( 22, restaurante.getMenuBase().size() );
        assertEquals( "corral", restaurante.getMenuBase().get( 0 ).getNombre() );
        assertEquals( 14000, restaurante.getMenuBase().get( 0 ).getPrecio() );
        assertEquals( 4, restaurante.getMenuCombos().size() );
        assertEquals( "combo corral", restaurante.getMenuCombos().get( 0 ).getNombre() );
        assertEquals( 22050, restaurante.getMenuCombos().get( 0 ).getPrecio() );
    }
    
    @Test
    void testCargarIngredienteRepetido()
    {
        File archivoIngredientes = new File( "data/ingredientes_repetidos.txt" );
        File archivoMenu = new File( "data/menu.txt" );
        File archivoCombos = new File( "data/combos.txt" );
        assertThrows( IngredienteRepetidoException.class, () -> restaurante.cargarInformacionRestaurante( archivoIngredientes, archivoMenu, archivoCombos ) );
    }
    
    @Test
    void testCargarProductoRepetido()
    {
        File archivoIngredientes = new File( "data/ingredientes.txt" );
        File archivoMenu = new File( "data/menu_repetido.txt" );
        File archivoCombos = new File( "data/combos.txt" );
        assertThrows( ProductoRepetidoException.class, () -> restaurante.cargarInformacionRestaurante( archivoIngredientes, archivoMenu, archivoCombos ) );
    }

    @Test
    void testCargarComboRepetido()
    {
        File archivoIngredientes = new File( "data/ingredientes.txt" );
        File archivoMenu = new File( "data/menu.txt" );
        File archivoCombos = new File( "data/combos_repetidos.txt" );
        assertThrows( ProductoRepetidoException.class, () -> restaurante.cargarInformacionRestaurante( archivoIngredientes, archivoMenu, archivoCombos ) );
    }

    @Test
    void testCargarComboConProductoFaltante()
    {
        File archivoIngredientes = new File( "data/ingredientes.txt" );
        File archivoMenu = new File( "data/menu.txt" );
        File archivoCombos = new File( "data/combo_producto_faltante.txt" );
        assertThrows( ProductoFaltanteException.class, () -> restaurante.cargarInformacionRestaurante( archivoIngredientes, archivoMenu, archivoCombos ) );
    }
}







