package almacen;

import java.util.ArrayList;
import java.util.List;

import tdas.*;

public class Almacen implements IAlmacen {

    private String nombre;
    private String direccion;
    private String telefono;

    private TArbolBB<Producto> productos;

    public Almacen(String nombre) {
        this.nombre = nombre;
        this.productos = new TArbolBB<Producto>();
    }

    /**
     * Insertar un producto en el almacen.
     * @param unProducto (tipo Producto)
     */
    public void insertarProducto(Producto unProducto) {
        TElementoAB<Producto> unElemento = new TElementoAB<Producto>(unProducto.getEtiqueta(), unProducto);
        if (!productos.insertar(unElemento)) {   // Si el producto existe, incremento su stock.
            agregarStock(unProducto.getEtiqueta(), unProducto.getStock());
        }
    }

    /**
     * Agregar stock al producto.
     * @return boolean
     */
    @Override
    public Boolean agregarStock(Comparable clave, Integer cantidad) { 
        Producto elProducto = productos.buscar(clave).getDatos();
        if (elProducto != null) {
            elProducto.agregarStock(cantidad);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String imprimirProductos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Restar stock al producto.
     * @return stock final del producto, -1 si el producto queda sin stock o no existe.
     */
    public Integer restarStock(Comparable clave, Integer cantidad) {
        if (productos.buscar(clave) != null) {
            Producto elProducto = this.productos.buscar(clave).getDatos();
            int stock = elProducto.restarStock(cantidad);
            if (stock <= 0) {   // Si el stock es cero o negativo eliminar el producto.
                this.productos.eliminar(elProducto.getEtiqueta());
            }
            return stock;
        } else {
            return -1;
        }
    }


    public Producto buscarPorCodigo(Comparable clave) {
        TElementoAB<Producto> producto = productos.buscar(clave);
        if (producto != null) {
            return producto.getDatos();
        }
        return null;
    }

    /**
     * Eliminar productos del almacen.
     * @return boolean, true si eliminó el producto.
     */
    public boolean eliminarProducto(Comparable clave) {
        if (productos.buscar(clave) != null) {
            productos.eliminar(clave);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Listar productos por código, incluyendo su stock.
     */
    public void listarProductoCodigo() { 
        // Crear una lista.
        Lista<Producto> listaProductos = productos.inorden();
        // Imprimir por consola.
        Nodo<Producto> nodoActual = listaProductos.getPrimero();
        while (nodoActual != null) {
            System.out.println("Cód." + nodoActual.getEtiqueta() + " Stock: " + nodoActual.getDato().getStock());
            nodoActual = nodoActual.getSiguiente();
        }
    }

    /**
     * Valor del stock del almacen.
     * @return entero
     */
    public int valorStock() { 
        int valorStock = 0;
        // Crear una lista.
        Lista<Producto> listaProductos = productos.inorden();
        // Imprimir por consola.
        Nodo<Producto> nodoActual = listaProductos.getPrimero();
        while (nodoActual != null) {
            valorStock += nodoActual.getDato().getPrecio() * nodoActual.getDato().getStock();
            nodoActual = nodoActual.getSiguiente();
        }
        return valorStock;
    }

    /**
     * Obtener la cantidad de elementos del almacen.
     * @return cantidad de elementos.
     */
    public int cantidadProductos() {
        return productos.obtenerTamanio();
    }

    /**
     * Listar productos por descripción, ordenados alfabeticamente. 
     * Guardar un archivo el resultado.
     */
    public String[] listaOrdenadaDescripcion() {

        // Árbol resultado.
        TArbolBB<Producto> arbolOrdenado = new TArbolBB<>();
  
        // Obtener los productos del árbol desordenado y reordenarlos, obteniendo el árbol ordenado.
        Lista<Producto> listaProductos = this.productos.inorden();

        Nodo<Producto> nodoActual = listaProductos.getPrimero();
        while (nodoActual != null) {
            TElementoAB<Producto> unElemento = new TElementoAB<Producto>( (Comparable) nodoActual.getDato().getNombre(), nodoActual.getDato());
            arbolOrdenado.insertar(unElemento);
            nodoActual = nodoActual.getSiguiente();
        }
  
        // Lista resultado.
        List<String> listaOrdenada = new ArrayList<>();
  
        // Listar por consola, obtener salida para archivo.
        int valorTotalStock = 0;

        Lista<Producto> listaAux = arbolOrdenado.inorden();

        Nodo<Producto> nodoAux = listaAux.getPrimero();
        while (nodoAux != null) {
            String dato = nodoAux.getDato().getNombre() + "," + nodoAux.getDato().getPrecio();
            valorTotalStock += nodoAux.getDato().getPrecio() * nodoAux.getDato().getStock();
            listaOrdenada.add(dato);
            nodoAux = nodoAux.getSiguiente();
        }
        System.out.println("Valor Total del Stock: " + valorTotalStock);
  
        // Salida por archivo.
        return listaOrdenada.toArray(new String[0]);
  
    }

}