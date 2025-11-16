package aed;
public class Estudiante implements Comparable<Estudiante> {
    private double nota;
    private int id;
    private int[] examen;
    private boolean entregado;
    private int cantidadResueltos;
    private int posicionHeap;
    private int fila, columna;

        public Estudiante(int id, int cantidadEjercicios) { // -- O(R)
        this.id = id;
        this.nota = 0.0;
        this.examen = new int[cantidadEjercicios];
        for (int i = 0; i < cantidadEjercicios; i++) {
            this.examen[i] = -1; // -1 indica que el ejercicio no ha sido resuelto
        }
        this.entregado = false;
        this.cantidadResueltos = 0;
        this.posicionHeap = -1;
    }

    public double obtenerNota(){
        return nota;
    }

    public int obtenerId(){
        return id;
    }

    public int obtenerFila() {
        return fila;
    }

    public int obtenerColumna() {
        return columna;
    }

    public void setearNota(double nuevaNota) {
        this.nota = nuevaNota;
    }

    public void setearFila(int fila) {
        this.fila = fila;
    }

    public void setearColumna(int columna) {
        this.columna = columna;
    }

    public boolean entrego() {
        return entregado;
    }

    public void entregar() {
        this.entregado = true;
    }

    public int[] obtenerExamen() {
        return examen;
    }

    public int obtenerCantidadResueltos() {
        return cantidadResueltos;
    }

    public int obtenerPosicionHeap() {
        return posicionHeap;
    }

    public void setearPosicionHeap(int posicion) {
        this.posicionHeap = posicion;
    }

    public void reemplazarExamen(int[] nuevoExamen, int[] examenCanonico) {
        this.cantidadResueltos = 0;
        this.nota = 0.0;
        for (int i = 0; i < examen.length; i++) { // -- O(R)
            if (nuevoExamen[i] != -1) {
                this.examen[i] = nuevoExamen[i];
                this.cantidadResueltos++;
                if (nuevoExamen[i] == examenCanonico[i]) {
                    this.nota += 100.0 / examenCanonico.length;
                }
            } else {
                this.examen[i] = -1;
            }
        }
    } // -- O(R)

    public void resolverEjercicio(int indiceEjercicio, int respuesta, int[] examenCanonico) { // -- O(1)
        if (examen[indiceEjercicio] == -1) {
            examen[indiceEjercicio] = respuesta;
            cantidadResueltos++;
            if (respuesta == examenCanonico[indiceEjercicio]) {
                nota += 100.0 / examenCanonico.length;
            }
        }
    }

    @Override
    public int compareTo(Estudiante otro) {
        int compNota = Double.compare(this.nota, otro.nota);
        if (compNota != 0) {
            return compNota;
        }
        return Integer.compare(this.id, otro.id);
    }
}
