package aed;

public class ColaDePrioridadEstudiantes {
    private Estudiante[] heap;
    private int tamaño;
    private int capacidad;
    private static final int CAPACIDAD_DEFAULT = 0;

    public ColaDePrioridadEstudiantes() {
        this.capacidad = CAPACIDAD_DEFAULT;
        this.heap = new Estudiante[CAPACIDAD_DEFAULT];
        this.tamaño = 0;
    }

    public ColaDePrioridadEstudiantes(Estudiante[] elementos) {
        this.tamaño = elementos.length;
        this.capacidad = max(CAPACIDAD_DEFAULT, this.tamaño);
        this.heap = new Estudiante[this.capacidad];

        for (int i = 0; i < this.tamaño; i++) {
            this.heap[i] = elementos[i];
            this.heap[i].setearPosicionHeap(i);
        }

        int indiceUltimoNoHoja = obtenerIndicePadre(this.tamaño - 1);
        for (int i = indiceUltimoNoHoja; i >= 0; i--) {
            bajar(i);
        }
    }

    private int max(int a, int b) {
        if (a>b) {
            return a;
        }
        return b;
    }

    private void swap(int i, int j) {
        Estudiante temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;

        heap[i].setearPosicionHeap(i);
        heap[j].setearPosicionHeap(j);
    }

    private void subir(int indice) {
        Estudiante elemento = heap[indice];
        int indicePadre;

        boolean sigueSubiendo = true;


        while (indice > 0 && sigueSubiendo) {
            indicePadre = obtenerIndicePadre(indice);
            Estudiante padre = heap[indicePadre];

            if (elemento.compareTo(padre) < 0) {
                swap(indice, indicePadre);
                indice = indicePadre;
            } else {
                sigueSubiendo = false;
            }
        }
    }

    private void bajar(int indice) {
        boolean sigueBajando = true;


        while (tieneHijoIzquierdo(indice) && sigueBajando) {
            int menorIndiceHijo = obtenerIndiceHijoIzquierdo(indice);
            Estudiante hijoIzquierdo = heap[menorIndiceHijo];

            if (tieneHijoDerecho(indice)) {
                Estudiante hijoDerecho = heap[obtenerIndiceHijoDerecho(indice)];
                if (hijoDerecho.compareTo(hijoIzquierdo) < 0) {
                    menorIndiceHijo = obtenerIndiceHijoDerecho(indice);
                }
            }

            Estudiante actual = heap[indice];
            Estudiante menorHijo = heap[menorIndiceHijo];

            if (actual.compareTo(menorHijo) > 0) {
                swap(indice, menorIndiceHijo);
                indice = menorIndiceHijo;
            } else {
                sigueBajando = false;
            }
        }
    }

    public void actualizarPrioridad(Estudiante elemento) {
        int indiceEnHeap = elemento.obtenerPosicionHeap();
        if (indiceEnHeap < 0 || indiceEnHeap >= tamaño) return;

        int indicePadre = obtenerIndicePadre(indiceEnHeap);

        if (indiceEnHeap == 0 || heap[indiceEnHeap].compareTo(heap[indicePadre]) > 0) {
            bajar(indiceEnHeap);
        } else {
            subir(indiceEnHeap);
        }
    }

    public void encolar(Estudiante elemento) {
        asegurarCapacidad();

        heap[tamaño] = elemento;
        heap[tamaño].setearPosicionHeap(tamaño);
        subir(tamaño);
        tamaño++;
    }

    public Estudiante desencolar() {
        if (!estaVacio()) {
            Estudiante menorElemento = heap[0];

            heap[0] = heap[tamaño - 1];
            heap[tamaño - 1] = null;
            tamaño --;

            if (!estaVacio()) {
                heap[0].setearPosicionHeap(0);
                bajar(0);
            }

            menorElemento.setearPosicionHeap(-1);

            return menorElemento;
        }
        return null;
        
    }

    public boolean esVacia() {
        return tamaño == 0;
    }

    public Estudiante proximo() {
        return heap[0];
    }

    public boolean estaVacio() {
        return tamaño == 0;
    }

    public int tamaño() {
        return tamaño;
    }

    private void asegurarCapacidad() {
        if (tamaño == capacidad) {
            int nuevaCapacidad = 1;
            if (capacidad != 0) {
                nuevaCapacidad = capacidad * 2;
            }


            Estudiante[] nuevoHeap = new Estudiante[nuevaCapacidad];
            for (int i = 0; i < tamaño; i++) {
                nuevoHeap[i] = heap[i];
            }
            heap = nuevoHeap;
            capacidad = nuevaCapacidad;
        }
    }


    // ---------- en la cola, planteo la eliminación de un estudiante específico aprovechando el seteo que teníamos (esto sería el handle?)

    public void eliminar(Estudiante est){
        int posicionActual = est.obtenerPosicionHeap();

        // bordes para que no pase nada raro
        if (posicionActual < 0 || posicionActual >= tamaño){return;}

        // si la posición está tan abajo se elimina direcctamente 
        if (posicionActual == tamaño - 1){
            heap[tamaño - 1] = null;
            tamaño --;
        } // si no, hay que hacer un swap 
        else{
            swap(posicionActual, tamaño - 1);

            // y hacer lo mismo 
            heap[tamaño - 1] = null;
            tamaño --;

            // ahora lo que pasa es que en la posicionActual hay alguien más (por el swap) 

            if (posicionActual < tamaño){
                int padre = obtenerIndicePadre(posicionActual);
                if (posicionActual > 0 && heap[posicionActual].compareTo(heap[padre]) < 0){
                    subir(posicionActual);
                }
                else{
                    bajar(posicionActual);
                }
                
            }
        }
        
        // eliminar la posición del heap
        est.setearPosicionHeap(-1);
    }


    private int obtenerIndicePadre(int i) { return (i - 1) / 2; }
    private int obtenerIndiceHijoIzquierdo(int i) { return 2 * i + 1; }
    private int obtenerIndiceHijoDerecho(int i) { return 2 * i + 2; }

    private boolean tieneHijoIzquierdo(int i) { return obtenerIndiceHijoIzquierdo(i) < tamaño; }
    private boolean tieneHijoDerecho(int i) { return obtenerIndiceHijoDerecho(i) < tamaño; }

    
}