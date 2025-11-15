package aed;

public class ColaDePrioridadEstudiantes {
    private HandleHeap[] heap;
    private int tamaño;
    private int capacidad;

    public ColaDePrioridadEstudiantes(int cant_estudiantes) {
        this.capacidad = cant_estudiantes;
        this.heap = new HandleHeap[cant_estudiantes];
        this.tamaño = 0;
    }

    public ColaDePrioridadEstudiantes(HandleHeap[] elementos) {
        this.tamaño = elementos.length;
        this.capacidad = elementos.length;
        this.heap = new HandleHeap[this.capacidad];

        for (int i = 0; i < this.tamaño; i++) {
            this.heap[i] = elementos[i];
        }

        int indiceUltimoNoHoja = obtenerIndicePadre(this.tamaño - 1);
        for (int i = indiceUltimoNoHoja; i >= 0; i--) {
            bajar(i);
        }
    }

    private void swap(int i, int j) {
        HandleHeap temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;

        heap[i].setearPosicion(i);
        heap[j].setearPosicion(j);
    }

    private void subir(int indice) {
        HandleHeap handle = heap[indice];
        Estudiante elemento = handle.estudiante;
        int indicePadre;

        boolean sigueSubiendo = true;


        while (indice > 0 && sigueSubiendo) {
            indicePadre = obtenerIndicePadre(indice);
            HandleHeap handlePadre = heap[indicePadre];
            Estudiante padre = handlePadre.estudiante;

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
            HandleHeap handleHijoIzquierdo = heap[menorIndiceHijo];
            Estudiante hijoIzquierdo = handleHijoIzquierdo.estudiante;

            if (tieneHijoDerecho(indice)) {
                HandleHeap handleHijoDerecho = heap[obtenerIndiceHijoDerecho(indice)];
                Estudiante hijoDerecho = handleHijoDerecho.estudiante;
                if (hijoDerecho.compareTo(hijoIzquierdo) < 0) {
                    menorIndiceHijo = obtenerIndiceHijoDerecho(indice);
                }
            }

            HandleHeap handleActual = heap[indice];
            HandleHeap handleMenorHijo = heap[menorIndiceHijo];

            Estudiante actual = handleActual.estudiante;
            Estudiante menorHijo = handleMenorHijo.estudiante;

            if (actual.compareTo(menorHijo) > 0) {
                swap(indice, menorIndiceHijo);
                indice = menorIndiceHijo;
            } else {
                sigueBajando = false;
            }
        }
    }

    public void actualizarPrioridad(HandleHeap hestudiante) {
        this.heap[hestudiante.posicion()] = hestudiante;

        int indiceEnHeap = hestudiante.posicion();
        if (indiceEnHeap < 0 || indiceEnHeap >= tamaño) return;

        int indicePadre = obtenerIndicePadre(indiceEnHeap);

        if (indiceEnHeap == 0 || heap[indiceEnHeap].estudiante().compareTo(heap[indicePadre].estudiante()) > 0 ) {
            bajar(indiceEnHeap);
        } else {
            subir(indiceEnHeap);
        }
    }

    public HandleHeap encolar(Estudiante elemento) {
        HandleHeap hestudiante = new HandleHeap(elemento, tamaño);
        heap[tamaño] = hestudiante;
        subir(tamaño);
        tamaño++;
        return hestudiante;
    }

    public Estudiante desencolar() {
        if (!esVacia()) {
            HandleHeap menorhEstudiante = heap[0];
            Estudiante menorEstudiante = menorhEstudiante.estudiante();

            heap[0] = heap[tamaño - 1];
            heap[tamaño - 1] = null;
            tamaño --;

            if (!esVacia()) {
                heap[0].posicion = 0;
                bajar(0);
            }

            menorhEstudiante.posicion = -1;

            return menorEstudiante;
        }
        return null;
        
    }

    public boolean esVacia() {
        return tamaño == 0;
    }

    public Estudiante proximo() {
        return heap[0].estudiante();
    }

    public int tamaño() {
        return tamaño;
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
                if (posicionActual > 0 && heap[posicionActual].estudiante().compareTo(heap[padre].estudiante()) < 0){
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

    public static class HandleHeap implements Handle {
        private Estudiante estudiante;
        private int posicion;

        public HandleHeap (Estudiante e, int p) {
            this.estudiante = e;
            this.posicion = p;
        }

        private void setearPosicion(int p) {
            this.posicion = p;
        }

        public Estudiante estudiante() {
            return estudiante;
        }

        public int posicion() {
            return posicion;
        }
    }


    private int obtenerIndicePadre(int i) { return (i - 1) / 2; }
    private int obtenerIndiceHijoIzquierdo(int i) { return 2 * i + 1; }
    private int obtenerIndiceHijoDerecho(int i) { return 2 * i + 2; }

    private boolean tieneHijoIzquierdo(int i) { return obtenerIndiceHijoIzquierdo(i) < tamaño; }
    private boolean tieneHijoDerecho(int i) { return obtenerIndiceHijoDerecho(i) < tamaño; }

    
}