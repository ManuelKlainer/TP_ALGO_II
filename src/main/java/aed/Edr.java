package aed;
import java.util.ArrayList;

public class Edr {

    public Estudiante[] estudiantes;
    private ColaDePrioridadEstudiantes estudiantesPorNota;
    private Estudiante[][] aula;
    private int[] examenCanonico;
    private boolean[] copiones;
    private final int LadoAula;
    private final int R; // cant respuestas 
    private final int E; // cant estudiantes

    public Edr(int LadoAula, int Cant_estudiantes, int[] ExamenCanonico) {
        this.LadoAula = LadoAula;
        this.E = Cant_estudiantes;
        this.R = ExamenCanonico.length;

        this.estudiantes = new Estudiante[Cant_estudiantes];
        this.aula = new Estudiante[LadoAula][LadoAula];
        this.examenCanonico = new int[this.R];

        for (int i = 0; i < this.R; i++) { // -- O(R)
            this.examenCanonico[i] = ExamenCanonico[i];
        }

        for (int i = 0; i < Cant_estudiantes; i++) { // -- O(E)
            Estudiante est = new Estudiante(i, this.R);


            int estudiantesPorFila = (LadoAula + 1) / 2;
            int fila = est.obtenerId() / estudiantesPorFila;
            int posicionEnFila = est.obtenerId() % estudiantesPorFila;
            int col = posicionEnFila * 2;


            est.setearFila(fila);
            est.setearColumna(col);
            est.setearNota(0.0);

            this.estudiantes[i] = est;
            this.aula[fila][col] = est;
        }

        this.estudiantesPorNota = new ColaDePrioridadEstudiantes(this.estudiantes);
    }

//-------------------------------------------------NOTAS--------------------------------------------------------------------------

    public double[] notas() {
        double[] notas = new double[this.E];
        for (int i = 0; i < this.E; i++) { // O(E)
            notas[i] = this.estudiantes[i].obtenerNota();
        }
        return notas;
    }

//------------------------------------------------COPIARSE------------------------------------------------------------------------




    public void copiarse(int id_estudiante) {
        Estudiante estudiante = this.estudiantes[id_estudiante];
        //System.out.println("-----------------");
        //System.out.println("Estudiante: " + estudiante.id);
        //System.out.println("Fila: " + estudiante.fila);
        //System.out.println("Columna: " + estudiante.col);
        int filaVecino1 = estudiante.obtenerFila();
        int colVecino1 = estudiante.obtenerColumna() - 2;
        int filaVecino2 = estudiante.obtenerFila();
        int colVecino2 = estudiante.obtenerColumna() + 2;
        int filaVecino3 = estudiante.obtenerFila() - 1;
        int colVecino3 = estudiante.obtenerColumna();


        Estudiante vecino1 = null;
        Estudiante vecino2 = null;
        Estudiante vecino3 = null;

        /*//System.out.println("Aula");
        for (int i = 0; i < this.aula.length; i++) {
            for (int j = 0; j < this.aula[i].length; j++) {
                if (this.aula[i][j] != null) {
                    //System.out.print(this.aula[i][j].id + " ");
                } else {
                    //System.out.print("X ");
                }
            }
            //System.out.println();
        }*/
        

        //System.out.println(filaVecino1 + "," + colVecino1);
        //System.out.println(filaVecino2 + "," + colVecino2);
        //System.out.println(filaVecino3 + "," + colVecino3);
        //System.out.println(filaVecino4 + "," + colVecino4);
        //System.out.println(filaVecino5 + "," + colVecino5);

        if (0 <= filaVecino1 && filaVecino1 < this.LadoAula && 0 <= colVecino1 && colVecino1 < this.LadoAula) {
            vecino1 = this.aula[filaVecino1][colVecino1];
            /*if (vecino1 != null) {
                //System.out.println("Vecino 1: " + vecino1.id);
            } else {
                //System.out.println("Vecino 1: null");
            }*/
        }
        if (0 <= filaVecino2 && filaVecino2 < this.LadoAula && 0 <= colVecino2 && colVecino2 < this.LadoAula) {
            vecino2 = this.aula[filaVecino2][colVecino2];
            /*if (vecino2 != null) {
                //System.out.println("Vecino 2: " + vecino2.id);
            } else {
                //System.out.println("Vecino 2: null");
            }*/
        }
        if (0 <= filaVecino3 && filaVecino3 < this.LadoAula && 0 <= colVecino3 && colVecino3 < this.LadoAula) {
            vecino3 = this.aula[filaVecino3][colVecino3];
            /*if (vecino3 != null) {
                //System.out.println("Vecino 3: " + vecino3.id);
            } else {
                //System.out.println("Vecino 3: null");
            }*/
        }

        int[] respuestasCopiablesVecinos = new int[3];
        int[] primerRespuestaCopiableVecinos = new int[3];
        Estudiante[] vecinos = new Estudiante[3];

        primerRespuestaCopiableVecinos[0] = -1; primerRespuestaCopiableVecinos[1] = -1; primerRespuestaCopiableVecinos[2] = -1;
        respuestasCopiablesVecinos[0] = 0; respuestasCopiablesVecinos[1] = 0; respuestasCopiablesVecinos[2] = 0;

        int[] examenEstudiante = estudiante.obtenerExamen();
        int[] examenVecino1 = null;
        if (vecino1 != null) {
            vecinos[0] = vecino1;
            examenVecino1 = vecino1.obtenerExamen();
        }
        int[] examenVecino2 = null;
        if (vecino2 != null) {
            vecinos[1] = vecino2;
            examenVecino2 = vecino2.obtenerExamen();
        }
        int[] examenVecino3 = null;
        if (vecino3 != null) {
            vecinos[2] = vecino3;
            examenVecino3 = vecino3.obtenerExamen();
        }


        for (int i = 0; i < this.R; i++) {
            if (examenEstudiante[i] == -1) {
                if (vecino1 != null && !vecino1.entrego() && examenVecino1[i] != -1) {
                    respuestasCopiablesVecinos[0]++;
                    if (primerRespuestaCopiableVecinos[0] == -1) {
                        primerRespuestaCopiableVecinos[0] = i;
                    }
                }
                if (vecino2 != null && !vecino2.entrego() && examenVecino2[i] != -1) {
                    respuestasCopiablesVecinos[1]++;
                    if (primerRespuestaCopiableVecinos[1] == -1) {
                        primerRespuestaCopiableVecinos[1] = i;
                    }
                }
                if (vecino3 != null && !vecino3.entrego() && examenVecino3[i] != -1) {
                    respuestasCopiablesVecinos[2]++;
                    if (primerRespuestaCopiableVecinos[2] == -1) {
                        primerRespuestaCopiableVecinos[2] = i;
                    }
                }
            }
        }

        int maxRespuestasCopiables = 0;
        int indMejorVecino = -1;
        if (respuestasCopiablesVecinos[2] > maxRespuestasCopiables) {
            maxRespuestasCopiables = respuestasCopiablesVecinos[2];
            indMejorVecino = 2;
        }
        if (respuestasCopiablesVecinos[0] > maxRespuestasCopiables ||  (vecino1 != null && maxRespuestasCopiables != 0 && respuestasCopiablesVecinos[0] == maxRespuestasCopiables && vecinos[0].obtenerId() > vecinos[indMejorVecino].obtenerId())) {
            maxRespuestasCopiables = respuestasCopiablesVecinos[0];
            indMejorVecino = 0;
        }
        if (respuestasCopiablesVecinos[1] > maxRespuestasCopiables ||  (vecino2 != null && maxRespuestasCopiables != 0 && respuestasCopiablesVecinos[1] == maxRespuestasCopiables && vecinos[1].obtenerId() > vecinos[indMejorVecino].obtenerId())) {
            maxRespuestasCopiables = respuestasCopiablesVecinos[1];
            indMejorVecino = 1;
        }
        
        // --------------------------------------    
        if (indMejorVecino == -1){return;}
        // --------------------------------------


        int indicePreguntaACopiar = primerRespuestaCopiableVecinos[indMejorVecino];
        int respuestaCopiada = -1;

        if (indMejorVecino == 0) {
            respuestaCopiada = examenVecino1[indicePreguntaACopiar];
        } else if (indMejorVecino == 1) {
            respuestaCopiada = examenVecino2[indicePreguntaACopiar];
        } else if (indMejorVecino == 2) {
            respuestaCopiada = examenVecino3[indicePreguntaACopiar];
        }

        estudiante.resolverEjercicio(indicePreguntaACopiar, respuestaCopiada, examenCanonico);

        if (respuestaCopiada == examenCanonico[indicePreguntaACopiar]) {
            estudiantesPorNota.actualizarPrioridad(estudiante);
        }
    }



//-----------------------------------------------RESOLVER----------------------------------------------------------------




    public void resolver(int id_estudiante, int NroEjercicio, int res) {
        Estudiante estudiante = this.estudiantes[id_estudiante];
        if(!estudiante.entrego()){
            estudiante.resolverEjercicio(NroEjercicio, res, examenCanonico);

            if (res == examenCanonico[NroEjercicio]) {
                estudiantesPorNota.actualizarPrioridad(estudiante);
            }
        }
    }



//------------------------------------------------CONSULTAR DARK WEB-------------------------------------------------------

    public void consultarDarkWeb(int n, int[] examenDW) {
        ArrayList<Estudiante> estudiantesConsultantes = new ArrayList<>();

        for (int i = 0; i < n && !this.estudiantesPorNota.esVacia(); i++) {
            Estudiante est = this.estudiantesPorNota.desencolar();
            if (est != null && !est.entrego()) { // Podríamos sacarlo ya que si entregó, no está en la cola
                estudiantesConsultantes.add(est);
            } else if (est != null && est.entrego()) {
                i--; // no contar este estudiante
                this.estudiantesPorNota.encolar(est);
            }
        }

        for (int i = 0; i < estudiantesConsultantes.size(); i++) {
            Estudiante est = estudiantesConsultantes.get(i);
            est.reemplazarExamen(examenDW, examenCanonico);
        }

        for (int i = 0; i < estudiantesConsultantes.size(); i++) {
            Estudiante est = estudiantesConsultantes.get(i);
            this.estudiantesPorNota.encolar(est);
        }
    }
 

//-------------------------------------------------ENTREGAR-------------------------------------------------------------



    public void entregar(int estudiante) {
        Estudiante est = this.estudiantes[estudiante];
        if (!est.entrego()) {
            est.entregar();
            this.estudiantesPorNota.eliminar(est); // eliminar
        }
    }

    

//-----------------------------------------------------CORREGIR---------------------------------------------------------

    public NotaFinal[] corregir() {

        boolean[] esCopion = this.copiones;

        ColaDePrioridadNota colaNotas = new ColaDePrioridadNota(this.E, false); // max-heap
        for (int i = 0; i < this.E; i++) {
            Estudiante est = this.estudiantes[i];
            if (!esCopion[i] && est.entrego()) {
                colaNotas.encolar(est);
            }
        }

        NotaFinal[] notasFinales = new NotaFinal[colaNotas.size()];
        for (int i = 0; i < notasFinales.length; i++) {
            Estudiante est = colaNotas.desencolar();
            NotaFinal notaFinal = new NotaFinal(est.obtenerNota(), est.obtenerId());
            notasFinales[i] = notaFinal;
        }

        return notasFinales;
    }

//-------------------------------------------------------CHEQUEAR COPIAS-------------------------------------------------

    public int[] chequearCopias() {
        this.copiones = new boolean[this.E];
        int[][] cantidades = new int[this.R][10];
        for (int i = 0; i < this.E; i++) {
            Estudiante est = this.estudiantes[i];
            int[] examenEst = est.obtenerExamen();
            for (int j = 0; j < this.R; j++) {
                if (examenEst[j] != -1) {
                    cantidades[j][examenEst[j]]++;
                }
            }
        }


        ArrayList<Integer> sospechosos = new ArrayList<>();
        double umbral = 0.25 * (this.E - 1);
        if (this.E == 1) {
            umbral = 0;
        }

        for (int id = 0; id < this.E; id++) {
            Estudiante est = this.estudiantes[id];
            int[] examenEst = est.obtenerExamen();
            boolean esSospechoso = true;
            boolean resolvioAlguno = false;

            for (int ej = 0; ej < this.R && esSospechoso; ej++) {
                int resp = examenEst[ej];
                if (resp != -1) {
                    resolvioAlguno = true;
                    if (cantidades[ej][resp] - 1 < umbral) {
                        esSospechoso = false;
                    }
                }
            }

            if (esSospechoso && resolvioAlguno) {
                sospechosos.add(id);
                this.copiones[id] = true;
            }
        }
        int[] resultado = new int[sospechosos.size()];
        for (int i = 0; i < resultado.length; i++) {
            resultado[i] = sospechosos.get(i);
        }
        return resultado;
    }

}



