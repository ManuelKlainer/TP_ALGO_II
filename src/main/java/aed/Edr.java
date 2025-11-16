package aed;
import java.util.ArrayList;

import aed.ColaDePrioridadEstudiantes.HandleHeap;

public class Edr {

    public ColaDePrioridadEstudiantes.HandleHeap[] estudiantes;
    private ColaDePrioridadEstudiantes estudiantesPorNota;
    private ColaDePrioridadEstudiantes.HandleHeap[][] aula;
    private int[] examenCanonico;
    private boolean[] copiones;
    private final int LadoAula;
    private final int R; // cant respuestas 
    private final int E; // cant estudiantes

    public Edr(int LadoAula, int Cant_estudiantes, int[] ExamenCanonico) {
        this.LadoAula = LadoAula;
        this.E = Cant_estudiantes;
        this.R = ExamenCanonico.length;

        this.estudiantes = new ColaDePrioridadEstudiantes.HandleHeap[Cant_estudiantes]; // -- O(E)
        this.aula = new ColaDePrioridadEstudiantes.HandleHeap[LadoAula][LadoAula];
        this.examenCanonico = new int[this.R]; // -- O(R)

        for (int i = 0; i < this.R; i++) { // -- O(R)
            this.examenCanonico[i] = ExamenCanonico[i];
        }

        for (int i = 0; i < Cant_estudiantes; i++) { // -- O(E)
            Estudiante est = new Estudiante(i, this.R); // -- O(R)


            int estudiantesPorFila = (LadoAula + 1) / 2;
            int fila = est.obtenerId() / estudiantesPorFila;
            int posicionEnFila = est.obtenerId() % estudiantesPorFila;
            int col = posicionEnFila * 2;


            est.setearFila(fila);
            est.setearColumna(col);
            est.setearNota(0.0);

            ColaDePrioridadEstudiantes.HandleHeap hest = new ColaDePrioridadEstudiantes.HandleHeap(est,i);

            this.estudiantes[i] = hest;
            this.aula[fila][col] = hest; 
        }

        this.estudiantesPorNota = new ColaDePrioridadEstudiantes(this.estudiantes); // -- O(E)
    } // O(E*R)

//-------------------------------------------------NOTAS--------------------------------------------------------------------------

    public double[] notas() {
        double[] notas = new double[this.E]; // -- O(E)
        for (int i = 0; i < this.E; i++) { // -- O(E)
            notas[i] = this.estudiantes[i].estudiante().obtenerNota();
        }
        return notas;
    } // O(E)

//------------------------------------------------COPIARSE------------------------------------------------------------------------




    public void copiarse(int id_estudiante) {
        ColaDePrioridadEstudiantes.HandleHeap hestudiante = this.estudiantes[id_estudiante];
        Estudiante estudiante = hestudiante.estudiante();
        
        
        int filaVecino1 = estudiante.obtenerFila();
        int colVecino1 = estudiante.obtenerColumna() - 2;
        int filaVecino2 = estudiante.obtenerFila();
        int colVecino2 = estudiante.obtenerColumna() + 2;
        int filaVecino3 = estudiante.obtenerFila() - 1;
        int colVecino3 = estudiante.obtenerColumna();



        ColaDePrioridadEstudiantes.HandleHeap hVecino1 = null;
        ColaDePrioridadEstudiantes.HandleHeap hVecino2 = null;
        ColaDePrioridadEstudiantes.HandleHeap hVecino3 = null;
        Estudiante vecino1 = null;
        Estudiante vecino2 = null;
        Estudiante vecino3 = null;

        if (0 <= filaVecino1 && filaVecino1 < this.LadoAula && 0 <= colVecino1 && colVecino1 < this.LadoAula) {
            hVecino1 = this.aula[filaVecino1][colVecino1];
            vecino1 = hVecino1.estudiante();
        }
        if (0 <= filaVecino2 && filaVecino2 < this.LadoAula && 0 <= colVecino2 && colVecino2 < this.LadoAula) {
            hVecino2 = this.aula[filaVecino2][colVecino2];
            if (hVecino2 != null) { 
                // agregamos este if, porque dado que la última fila puede no llenar su capacidad, un estudiante puede no tener a su derecha a nadie aunque esté en rango
                vecino2 = hVecino2.estudiante();
            }
        }
        if (0 <= filaVecino3 && filaVecino3 < this.LadoAula && 0 <= colVecino3 && colVecino3 < this.LadoAula) {
            hVecino3 = this.aula[filaVecino3][colVecino3];
            vecino3 = hVecino3.estudiante();
        }

        int[] respuestasCopiablesVecinos = new int[3];
        int[] primerRespuestaCopiableVecinos = new int[3];
        Estudiante[] vecinos = new Estudiante[3];
        ColaDePrioridadEstudiantes.HandleHeap[] hvecinos = new ColaDePrioridadEstudiantes.HandleHeap[3];

        primerRespuestaCopiableVecinos[0] = -1; primerRespuestaCopiableVecinos[1] = -1; primerRespuestaCopiableVecinos[2] = -1;
        respuestasCopiablesVecinos[0] = 0; respuestasCopiablesVecinos[1] = 0; respuestasCopiablesVecinos[2] = 0;

        int[] examenEstudiante = estudiante.obtenerExamen();
        int[] examenVecino1 = null;
        if (vecino1 != null) {
            vecinos[0] = vecino1;
            hvecinos[0] = hVecino1;
            examenVecino1 = vecino1.obtenerExamen();
        }
        int[] examenVecino2 = null;
        if (vecino2 != null) {
            vecinos[1] = vecino2;
            hvecinos[1] = hVecino2;
            examenVecino2 = vecino2.obtenerExamen();
        }
        int[] examenVecino3 = null;
        if (vecino3 != null) {
            vecinos[2] = vecino3;
            hvecinos[2] = hVecino3;
            examenVecino3 = vecino3.obtenerExamen();
        }


        for (int i = 0; i < this.R; i++) { // -- O(R)
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
        if (respuestasCopiablesVecinos[1] > maxRespuestasCopiables) {
            maxRespuestasCopiables = respuestasCopiablesVecinos[1];
            indMejorVecino = 1;
        }
        if (respuestasCopiablesVecinos[0] > maxRespuestasCopiables) {
            maxRespuestasCopiables = respuestasCopiablesVecinos[0];
            indMejorVecino = 0;
        }
        if (respuestasCopiablesVecinos[2] > maxRespuestasCopiables) {
            maxRespuestasCopiables = respuestasCopiablesVecinos[2];
            indMejorVecino = 2;
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

        estudiante.resolverEjercicio(indicePreguntaACopiar, respuestaCopiada, examenCanonico); // -- O(1)

        if (respuestaCopiada == examenCanonico[indicePreguntaACopiar]) {
            estudiantesPorNota.actualizarPrioridad(hestudiante); // -- O(log E)
        }
    } // -- O(R + log E)



//-----------------------------------------------RESOLVER----------------------------------------------------------------




    public void resolver(int id_estudiante, int NroEjercicio, int res) {
        HandleHeap hestudiante = this.estudiantes[id_estudiante];
        Estudiante estudiante = hestudiante.estudiante();
        if(!estudiante.entrego()){
            estudiante.resolverEjercicio(NroEjercicio, res, examenCanonico);

            if (res == examenCanonico[NroEjercicio]) {
                estudiantesPorNota.actualizarPrioridad(hestudiante); // -- O(log E)
            }
        }
    } // -- O(log E)



//------------------------------------------------CONSULTAR DARK WEB-------------------------------------------------------

    public void consultarDarkWeb(int k, int[] examenDW) {
        ArrayList<Estudiante> estudiantesConsultantes = new ArrayList<>();

        for (int i = 0; i < k && !this.estudiantesPorNota.esVacia(); i++) {
            Estudiante est = this.estudiantesPorNota.desencolar(); // -- O(log E)
            if (est != null) {
                estudiantesConsultantes.add(est); // -- O(1)
            }
        } // -- O(k log E)

        for (int i = 0; i < estudiantesConsultantes.size(); i++) {
            Estudiante est = estudiantesConsultantes.get(i);
            est.reemplazarExamen(examenDW, examenCanonico); // -- O(R)
        } // -- O(kR)

        for (int i = 0; i < estudiantesConsultantes.size(); i++) {
            Estudiante est = estudiantesConsultantes.get(i);
            this.estudiantesPorNota.encolar(est); // -- O(log E)
            /*ColaDePrioridadEstudiantes.HandleHeap hest =  this.estudiantesPorNota.encolar(est);
            this.estudiantes[est.obtenerId()] = hest;*/
        } // -- O(k log E)
    } // -- O(k (R + log E))
 

//-------------------------------------------------ENTREGAR-------------------------------------------------------------



    public void entregar(int estudiante) {
        HandleHeap hest = this.estudiantes[estudiante];
        Estudiante est = hest.estudiante();
        if (!est.entrego()) {
            est.entregar();
            this.estudiantesPorNota.eliminar(est); // -- O(log E)
        }
    } // -- O(log E)

    

//-----------------------------------------------------CORREGIR---------------------------------------------------------

    public NotaFinal[] corregir() {

        boolean[] esCopion = this.copiones;

        ColaDePrioridadNota colaNotas = new ColaDePrioridadNota(this.E, false); // max-heap     O(E)
        for (int i = 0; i < this.E; i++) {
            HandleHeap hest = this.estudiantes[i];
            Estudiante est = hest.estudiante();
            if (!esCopion[i] && est.entrego()) {
                colaNotas.encolar(est); // O(log E)
            }
        } // O (E log E)

        NotaFinal[] notasFinales = new NotaFinal[colaNotas.size()]; // O(E)
        for (int i = 0; i < notasFinales.length; i++) {
            Estudiante est = colaNotas.desencolar(); // O (log E)
            NotaFinal notaFinal = new NotaFinal(est.obtenerNota(), est.obtenerId());
            notasFinales[i] = notaFinal;
        } // O (E log E)

        return notasFinales;
    } // O (E log E)

//-------------------------------------------------------CHEQUEAR COPIAS-------------------------------------------------

    public int[] chequearCopias() {
        this.copiones = new boolean[this.E];
        int[][] cantidades = new int[this.R][10];
        for (int i = 0; i < this.E; i++) {//O(E)
            HandleHeap hest = this.estudiantes[i];
            Estudiante est = hest.estudiante();//O(1)
            int[] examenEst = est.obtenerExamen();//O(1)
            for (int j = 0; j < this.R; j++) {//O(R)
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

        for (int id = 0; id < this.E; id++) {//O(E)
            HandleHeap hest = this.estudiantes[id];
            Estudiante est = hest.estudiante();
            int[] examenEst = est.obtenerExamen();
            boolean esSospechoso = true;
            boolean resolvioAlguno = false;

            for (int ej = 0; ej < this.R && esSospechoso; ej++) {//O(R)
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
        for (int i = 0; i < resultado.length; i++) {//O(R)
            resultado[i] = sospechosos.get(i);
        }
        return resultado;
    }
    //O(E*R + E*R + R ) = O(2*E*R) = O(E*R)


}



