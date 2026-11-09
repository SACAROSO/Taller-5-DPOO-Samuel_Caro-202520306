package uniandes.dpoo.hamburguesas.mundo;

import java.util.ArrayList;

/**
 * Un producto ajustado es un producto para el cual el cliente solicitó alguna modificación.
 */
public class ProductoAjustado implements Producto
{
    /**
     * El producto base que el cliente sobre el cual el cliente quiere hacer ajustes
     */
    private ProductoMenu productoBase;

    /**
     * La lista de ingrediente que el usuario quiere agregar. El mismo ingrediente puede aparecer varias veces.
     */
    private ArrayList<Ingrediente> agregados;

    /**
     * La lista de ingrediente que el usuario quiere eliminar.
     */
    private ArrayList<Ingrediente> eliminados;

    /**
     * Construye un nuevo producto ajustado a partir del producto base y sin modificaciones
     * @param productoBase El producto base que se va a ajustar
     */
    public ProductoAjustado( ProductoMenu productoBase )
    {
        this.productoBase = productoBase;
        agregados = new ArrayList<Ingrediente>( );
        eliminados = new ArrayList<Ingrediente>( );
    }
    
    public void agregarIngrediente(Ingrediente ingrediente)
    {
        agregados.add(ingrediente);
    }
    
    public void eliminarIngrediente(Ingrediente ingrediente)
    {
        eliminados.add(ingrediente);
    }

    @Override
    public String getNombre( )
    {
        return productoBase.getNombre( );
    }

    /**
     * Retorna el precio del producto ajustado, que debe ser igual al del producto base, sumándole el precio de los ingredientes adicionales.
     */
    @Override
    public int getPrecio()
    {
        int precioTotal = productoBase.getPrecio();

        for (Ingrediente ingrediente : agregados)
        {
            precioTotal += ingrediente.getCostoAdicional();
        }

        return precioTotal;
    }

    /**
     * Genera el texto que debe aparecer en la factura.
     * 
     * El texto incluye el producto base, los ingredientes adicionales con su costo, los ingredientes eliminados, y el precio total
     */
    @Override
    public String generarTextoFactura()
    {
        StringBuilder factura = new StringBuilder(productoBase.generarTextoFactura());

        for (Ingrediente ingrediente : agregados)
        {
            factura.append("    +").append(ingrediente.getNombre()).append("\n");
            factura.append("                ").append(ingrediente.getCostoAdicional()).append("\n");
        }

        for (Ingrediente ingrediente : eliminados)
        {
            factura.append("    -").append(ingrediente.getNombre()).append("\n");
        }

        factura.append("            ").append(getPrecio()).append("\n");
        return factura.toString();
    }

}
