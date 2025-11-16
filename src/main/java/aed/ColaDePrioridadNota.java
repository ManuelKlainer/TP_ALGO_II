package aed;

public class ColaDePrioridadNota {

    private Estudiante[] heap; 
    private int size;
    
    public ColaDePrioridadNota(int capacidad){
        this.heap =  new Estudiante[capacidad];
        this.size = 0;
    } // O(E)

    public ColaDePrioridadNota(int capacidad, boolean esMinHeap){
        this.heap =  new Estudiante[capacidad];
        this.size = 0;
    } // O(E)

    public boolean esVacia(){
        return size == 0;
    }

    public Estudiante primero(){
        if (esVacia()) return null;

        return heap[0];
    }
    
    public int size() {
        return size;
    }

    private int left(int i){
        return 2*i + 1;
    }

    private int right(int i){
        return 2*i + 2;
    }

    private int parent(int i){
        return (i-1) / 2;
    }

    private void swap(int i, int j){
        Estudiante est = heap[i];
        heap[i] = heap[j];
        heap[j] = est;
    }

    // e1 tiene mayor prioridad que e2 ?
    private boolean mayorPrioridad(Estudiante e1, Estudiante e2){
        if (e1 == null) return false;
        if (e2 == null) return true;

        if (e1.obtenerNota() != e2.obtenerNota()){
            return e1.obtenerNota() > e2.obtenerNota();
        }
        else {
            return e1.obtenerId() > e2.obtenerId();
        }
    }
    
    public void encolar(Estudiante e){
        if (size >= heap.length) throw new IllegalStateException("Cola llena");
        int i = size;
        heap[i] = e;
        size ++;

        while (i > 0 && mayorPrioridad(heap[i], heap[parent(i)])){
            swap(i, parent(i));
            i = parent(i);
        } // -- O(log E) -- Ya que en el peor caso hay que ir desde una hoja a la raíz, haciendo log E swaps de O(1)
    } // O(log E)

    public Estudiante desencolar(){
        if (esVacia()) return null;

        Estudiante res = heap[0];
        size = size - 1;

        heap[0] = (size > 0) ? heap[size] : null;
        heap[size] = null;

        int i = 0;
        boolean seguir = true;

        while(seguir){
            int mayor = i;
            int l = left(i);
            int r = right(i);

            if (l < size && mayorPrioridad(heap[l], heap[mayor])){
                mayor = l;
            } 
            if (r < size && mayorPrioridad(heap[r], heap[mayor])){
                mayor = r;
            }   

            if (mayor != i){
                swap(i, mayor);
                i = mayor;
            } else {
                seguir = false;
            }
        }
        
        return res;
    }
    
}