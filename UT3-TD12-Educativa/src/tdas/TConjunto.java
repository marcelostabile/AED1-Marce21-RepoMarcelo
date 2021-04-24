package tdas;

public class TConjunto<T> extends Lista<T> implements ITConjunto<T> {

    ILista<T> listaConjunto;

    // Constructor, lista vacía, la llenamos con los métodos insertar de lista.
    public TConjunto() {
        this.listaConjunto = new Lista<T>();
    }

    /**
     * UNION
     * El conjunto UNION contiene todos los elementos del conjunto A y del conjunto B.
     * 
     * Creo una nueva lista resultado, recorro el conjunto A y agrego todos los elementos en mi lista.
     * Recorro el conjunto B, verifico si cada elemento no existe previamente, si no existe lo agrego.
     * Retorno el conjunto resultado conteniendo la union de ambos conjuntos.
    */
    public TConjunto<T> union(TConjunto<T> otroConjunto) {

        TConjunto<T> conjuntoResultado = new TConjunto<T>();

        // Conjunto A
        INodo<T> actual = this.listaConjunto.getPrimero();    
        while (actual != null) {
            conjuntoResultado.insertarUltimo(actual.clonar());
            actual = actual.getSiguiente();
        }
        // Conjunto B
        actual = otroConjunto.getPrimero();
        while (actual != null) {
            if (conjuntoResultado.buscar(actual.getEtiqueta()) == null) {
                conjuntoResultado.insertarPrimero(actual.clonar());
            }
            actual = actual.getSiguiente();
        }
        return conjuntoResultado;
    }

    /**
     * El conjunto INTERSECCIÓN contiene todos los elementos comunes de ambos conjuntos.
     * 
     * Creo una nueva lista resultado, verifico cúal de mis conjuntos contiene menos elementos.
     * Utilizaremos el conjunto más pequeño como referencia, buscando en el más largo si existe el elemento.
     * Esto es más eficiente que recorrer el conjunto más largo como referencia.
     * Para cada coincidencia, copiamos el elemento al conjunto resultado, que finalmente retornamos.
     */
    public TConjunto<T> interseccion(TConjunto<T> otroConjunto) {
        
        TConjunto<T> conjuntoResultado = new TConjunto<T>();

        ILista<T> listaPrincipal = new Lista<T>();
        ILista<T> listaReferencia = new Lista<T>();

        // Verificar el conjunto más pequeño.
        if (this.listaConjunto.cantElementos() <= otroConjunto.cantElementos()) {
            listaPrincipal = listaConjunto;
            listaReferencia = otroConjunto;
        } else {
            listaPrincipal = otroConjunto;
            listaReferencia = listaConjunto;
        }

        INodo<T> actual = listaPrincipal.getPrimero();
        while (actual != null) {
            if (listaReferencia.buscar(actual.getEtiqueta()) != null) {
                conjuntoResultado.insertarPrimero(actual.clonar());
            }
            actual = actual.getSiguiente();
        }
        return conjuntoResultado;
    }

    /**
     * El conjunto DIFERENCIA contiene elementos de A que no pertenecen al conjunto B.
     * 
     * Creo una nueva lista para el conjunto resultado.
     * Recorro el conjunto A y para cada elemento verifico si existe en el conjunto B.
     * Si no existe agrego el elemento al conjunto resultado, al final devuelvo estos resultados.
     */
    public TConjunto<T> diferencia(TConjunto<T> otroConjunto) {

        TConjunto<T> conjuntoResultado = new TConjunto<T>();

        INodo<T> actual = this.listaConjunto.getPrimero();
        while (actual != null) {
            if (otroConjunto.buscar(actual.getEtiqueta()) == null) { 
                conjuntoResultado.insertarPrimero(actual.clonar());
            }
            actual = actual.getSiguiente();
        }
        return conjuntoResultado;
    }

    /**
     * El conjunto DIFERENCIA SIMÉTRICA contiene los elementos de A y B que no son comunes.
     *
     * Recorro el primer conjunto, para cada elemento verifico si existe en el conjunto B, 
     * si no existe lo agrego al conjunto resultado.
     * Luego recorreo el segundo conjunto, para cada elemento verifico si existe en el conjunto A 
     * y en el conjunto resultado. Si no existe en ambos lo agrego al conjunto resultado.
    */
    public TConjunto<T> diferenciaSimetrica(TConjunto<T> otroConjunto) {

        TConjunto<T> conjuntoResultado = new TConjunto<T>();

        // Conjunto A
        INodo<T> actual = this.listaConjunto.getPrimero();
        while (actual != null) {
            if (otroConjunto.buscar(actual.getEtiqueta()) == null) {
                conjuntoResultado.insertarPrimero(actual.clonar());
            }
            actual = actual.getSiguiente();
        }

        // Conjunto B
        actual = otroConjunto.getPrimero();
        while (actual != null) {
            if ( (this.listaConjunto.buscar(actual.getEtiqueta()) == null) && (conjuntoResultado.buscar(actual.getEtiqueta()) == null) ) {
                conjuntoResultado.insertarPrimero(actual.clonar());
            }
        }
        return conjuntoResultado;
    }

    /**
     * El conjunto COMPLEMENTO son todos los elementos que no pertenecen al conjunto A.
     * 
     * Recibimos un conjunto B que define el universo de números ha evaluar.
     * Para cada elemento del conjunto B, verifico si exite en el conjunto A.
     * Los números que no existen los agrego al conjunto resultado, que luego devuelvo.
     */
    public TConjunto<T> complemento(TConjunto<T> otroConjunto) {

        TConjunto<T> conjuntoResultado = new TConjunto<>();

        INodo<T> actual = otroConjunto.getPrimero();
        while (actual != null) {
            if (this.listaConjunto.buscar(actual.getEtiqueta()) == null) {
                conjuntoResultado.insertarPrimero(actual.clonar());
            }
            actual = actual.getSiguiente();
        }
        return conjuntoResultado;
    }

    /**
    * PRODUCTO CARTESIANO
    * El conjunto PRODUCTO CARTESIANO A x B contiene todos los pares ordenados (a,b) cuyo primer elemento 
    * pertenece a A y su segundo elemento pertenece a B.
    * Ejem. A={1,2,3,4} B={a,b} Resultado AxB={(1,a),(1,b),(2,a),(2,b),(3,a),(3,b),(4,a),(4,b)}
    *
    * Recibimos dos listas conteniendo los conjuntos (listaConjuntoA y listaConjuntoB).
    * Creamos una lista (listaProductoCartesiano).
    * Recorremos la lista del conjunto A, para cada elemento recorremos el conjunto B y por cada valor guardamos un par ordenado (a, b)
    * que luego guardamos en la lista resultado. 
    * Cuando terminamos devolvemos la lista resultado.
     */
    public TConjunto<T> productoCartesiano(TConjunto<T> otroConjunto) {

        TConjunto<T> conjuntoResultado = new TConjunto<>();

        

        int[] parOrdenado = new int[2];

        return conjuntoResultado;
    }






    
    //     int[] parOrdenado = new int[2];    // Par ordenado.

    //     for (int elemA : listaConjuntoA) {
    //         for (int elemB : listaConjuntoB) {
    //             parOrdenado[0] = elemA;
    //             parOrdenado[1] = elemB;
    //             listaProductoCartesiano.add(parOrdenado);
    //         }
    //     }
    //     return listaProductoCartesiano;    // Retornamos la lista resultado.
    // }
     
}