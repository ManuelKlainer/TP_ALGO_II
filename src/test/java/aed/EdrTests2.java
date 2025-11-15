/*package aed;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Arrays;


class EdrTests2 {
    Edr edr;
    int d_aula;
    int cant_alumnos;
    int[] solucion;

    @BeforeEach
    void setUp(){
        d_aula = 5;
        cant_alumnos = 4;
        solucion = new int[]{0,1,2,3,4,5,6,7,8,9};

        edr = new Edr(d_aula, cant_alumnos, solucion);
    }

    @Test
    void nuevo_edr() {
        double[] notas = edr.notas();
        double[] notas_esperadas = new double[]{0.0, 0.0, 0.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
    }
    
    @Test
    void los_alumnos_resuelven_un_problema() {
        double[] notas;
        double[] notas_esperadas;

        edr.resolver(0, 0, 0);
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 0.0, 0.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.resolver(1, 0, 2);
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 0.0, 0.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.resolver(2, 4, 4);
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 0.0, 10.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.resolver(3, 9, 8);
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 0.0, 10.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
    }

    @Test
    void los_alumnos_resuelven_varios_problemas() {
        double[] notas;
        double[] notas_esperadas;

        for(int pregunta = 0; pregunta < 5; pregunta++){
            edr.resolver(0, pregunta, pregunta);
            notas = edr.notas();
            notas_esperadas = new double[]{10.0*(pregunta+1), 0.0, 0.0, 0.0};
            assertTrue(Arrays.equals(notas_esperadas, notas));
        }

        for(int pregunta = 9; pregunta > 2; pregunta--){
            edr.resolver(1, pregunta, 9-pregunta);
            notas = edr.notas();
            notas_esperadas = new double[]{50.0, 0.0, 0.0, 0.0};
            assertTrue(Arrays.equals(notas_esperadas, notas));
        }

        for(int pregunta = 0; pregunta < 10; pregunta+=2){
            edr.resolver(2, pregunta, pregunta);
            notas = edr.notas();
            notas_esperadas = new double[]{50.0, 0.0, 10.0*(pregunta/2+1), 0.0};
            assertTrue(Arrays.equals(notas_esperadas, notas));
        }

        double nota_3 = 0.0;
        for(int pregunta = 0; pregunta < 10; pregunta++){
            //si la pregunta es par, responde bien, si no es par responde mal.
            int respuesta = pregunta % 2 == 0 ? pregunta : 9 - pregunta;
            nota_3 += 10.0 * ((pregunta+1)%2);
            edr.resolver(3, pregunta, respuesta);
            notas = edr.notas();
            notas_esperadas = new double[]{50.0, 0.0, 50.0, nota_3};
            assertTrue(Arrays.equals(notas_esperadas, notas));
        }

        for(int pregunta = 5; pregunta < 10; pregunta++){
            edr.resolver(0, pregunta, pregunta);
            notas = edr.notas();
            notas_esperadas = new double[]{10.0*(pregunta+1), 0.0, 50.0, 50.0};
            assertTrue(Arrays.equals(notas_esperadas, notas));
        }
    }

    @Test
    void revisar_copias_examenes_completos() {
        double[] notas;
        double[] notas_esperadas;

        for(int pregunta = 0; pregunta < 10; pregunta++){
            edr.resolver(0, pregunta, pregunta);
            notas = edr.notas();
            notas_esperadas = new double[]{10.0*(pregunta+1), 0.0, 0.0, 0.0};
            assertTrue(Arrays.equals(notas_esperadas, notas));
        }
        edr.entregar(0);

        for(int pregunta = 9; pregunta > -1; pregunta--){
            edr.resolver(1, pregunta, 9-pregunta);
            notas = edr.notas();
            notas_esperadas = new double[]{100.0, 0.0, 0.0, 0.0};
            assertTrue(Arrays.equals(notas_esperadas, notas));
        }
        edr.entregar(1);

        double nota_2 = 0.0;
        for(int pregunta = 0; pregunta < 10; pregunta++){
            //solo se responden bien las preguntas pares
            edr.resolver(2, pregunta, (pregunta % 2 == 0 ? pregunta : 0));
            nota_2 += pregunta % 2 == 0 ? 10.0 : 0.0;
            notas = edr.notas();
            notas_esperadas = new double[]{100.0, 0.0, nota_2, 0.0};
            assertTrue(Arrays.equals(notas_esperadas, notas));
        }
        edr.entregar(2);

        double nota_3 = 0.0;
        for(int pregunta = 0; pregunta < 10; pregunta++){
            //si la pregunta es par, responde bien, si no es par responde mal.
            int respuesta = pregunta % 2 == 0 ? pregunta : 9 - pregunta;
            nota_3 += 10.0 * ((pregunta+1)%2);
            edr.resolver(3, pregunta, respuesta);
            notas = edr.notas();
            notas_esperadas = new double[]{100.0, 0.0, 50.0, nota_3};
            assertTrue(Arrays.equals(notas_esperadas, notas));
        }
        edr.entregar(3);

        int[] copiones = edr.chequearCopias(); 
        int[] copiones_esperados = new int[]{3};
        assertTrue(Arrays.equals(copiones_esperados, copiones));
        
    }

    @Test
    void revisar_copias_examenes_incompletos() {
        double[] notas;
        double[] notas_esperadas;

        for(int pregunta = 0; pregunta < 3; pregunta++){
            edr.resolver(0, pregunta, pregunta);
            notas = edr.notas();
            notas_esperadas = new double[]{10.0*(pregunta+1), 0.0, 0.0, 0.0};
            assertTrue(Arrays.equals(notas_esperadas, notas));
        }
        edr.entregar(0);

        for(int pregunta = 9; pregunta > 8; pregunta--){
            edr.resolver(1, pregunta, 9-pregunta);
            notas = edr.notas();
            notas_esperadas = new double[]{30.0, 0.0, 0.0, 0.0};
            assertTrue(Arrays.equals(notas_esperadas, notas));
        }
        edr.entregar(1);

        edr.resolver(2, 9, 9);
        notas = edr.notas();
        notas_esperadas = new double[]{30.0, 0.0, 10.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
        edr.entregar(2);

        for(int pregunta = 0; pregunta < 5; pregunta++){
            edr.resolver(3, pregunta, pregunta);
            notas = edr.notas();
            notas_esperadas = new double[]{30.0, 0.0, 10.0, 10.0*(pregunta+1)};
            assertTrue(Arrays.equals(notas_esperadas, notas));
        }
        edr.entregar(3);

        int[] copiones = edr.chequearCopias(); 
        int[] copiones_esperados = new int[]{0};
        assertTrue(Arrays.equals(copiones_esperados, copiones));
        
    }

    @Test
    void no_hay_copiones() {
        double[] notas;
        double[] notas_esperadas;

        for(int pregunta = 0; pregunta < 10; pregunta++){
            edr.resolver(0, pregunta, 0);
            edr.resolver(1, pregunta, 1);
            edr.resolver(2, pregunta, 2);
            edr.resolver(3, pregunta, 3);
        }
        for(int alumno = 0; alumno < 4; alumno++){
            edr.entregar(alumno);
        }
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 10.0, 10.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        int[] copiones = edr.chequearCopias();
        int[] copiones_esperados = new int[]{};
        assertTrue(Arrays.equals(copiones_esperados, copiones));

        NotaFinal[] notas_finales = edr.corregir();
        NotaFinal[] notas_finales_esperadas = new NotaFinal[]{
            new NotaFinal(10.0, 3),
            new NotaFinal(10.0, 2),
            new NotaFinal(10.0, 1),
            new NotaFinal(10.0, 0)
        };

        assertTrue(Arrays.equals(notas_finales_esperadas, notas_finales));

    }

    @Test
    void todos_copiones() {    //cambiar este test
        double[] notas;
        double[] notas_esperadas;

        for(int pregunta = 0; pregunta < 3; pregunta+=2){
            edr.resolver(0, pregunta, 5);
            edr.resolver(1, pregunta, 5);
            edr.resolver(2, pregunta, 5);
            edr.resolver(3, pregunta, 5);
        }

        for(int pregunta = 5; pregunta < 8; pregunta+=2){
            edr.resolver(0, pregunta, 5);
            edr.resolver(1, pregunta, 5);
            edr.resolver(2, pregunta, 5);
            edr.resolver(3, pregunta, 5);
        }

        for(int alumno = 0; alumno < 4; alumno++){
            edr.entregar(alumno);
        }

        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 10.0, 10.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        int[] copiones = edr.chequearCopias();
        int[] copiones_esperados = new int[]{0,1,2,3};
        assertTrue(Arrays.equals(copiones_esperados, copiones));

        NotaFinal[] notas_finales = edr.corregir();
        NotaFinal[] notas_finales_esperadas = new NotaFinal[]{};

        assertTrue(Arrays.equals(notas_finales_esperadas, notas_finales));

    }

    @Test
    void copias_de_exacto_25_porciento() {
        Edr edr_9 = new Edr(d_aula, 9, solucion);
        double[] notas;
        double[] notas_esperadas;

        for(int pregunta = 0; pregunta < 3; pregunta++){
            edr_9.resolver(0, pregunta, 5);
            edr_9.resolver(1, pregunta, 5);
            edr_9.resolver(2, pregunta, 5);
        }

        for(int pregunta = 5; pregunta < 9; pregunta++){
            edr_9.resolver(3, pregunta, pregunta);
            edr_9.resolver(4, pregunta, pregunta);
            edr_9.resolver(5, pregunta, pregunta);
        }
        edr_9.resolver(3, 9, 9);

        for(int alumno = 0; alumno < 9; alumno++){
            edr_9.entregar(alumno);
        }

        notas = edr_9.notas();
        notas_esperadas = new double[]{0.0, 0.0, 0.0, 50.0, 40.0, 40.0, 0.0, 0.0, 0.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        int[] copiones = edr_9.chequearCopias();
        int[] copiones_esperados = new int[]{0,1,2,4,5};
        assertTrue(Arrays.equals(copiones_esperados, copiones));

        NotaFinal[] notas_finales = edr_9.corregir();
        NotaFinal[] notas_finales_esperadas = new NotaFinal[]{
            new NotaFinal(50.0, 3),
            new NotaFinal(0.0, 8),
            new NotaFinal(0.0, 7),
            new NotaFinal(0.0, 6)
        };

        assertTrue(Arrays.equals(notas_finales_esperadas, notas_finales));

    }

    @Test
    void alumnos_se_copian_una_vez(){
        double[] notas;
        double[] notas_esperadas;

        edr.resolver(0, 0, 0);
        edr.resolver(1, 1, 1);
        edr.resolver(2, 2, 2);
        edr.resolver(3, 3, 3);

        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 10.0, 10.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.copiarse(2);
        
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 10.0, 20.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
        
        edr.copiarse(3);
        
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 10.0, 20.0, 20.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        for(int alumno = 0; alumno < 4; alumno++){
            edr.entregar(alumno);
        }

        int[] copiones = edr.chequearCopias();
        int[] copiones_esperados = new int[]{2,3};
        assertTrue(Arrays.equals(copiones_esperados, copiones));

        NotaFinal[] notas_finales = edr.corregir();
        NotaFinal[] notas_finales_esperadas = new NotaFinal[]{
            new NotaFinal(10.0, 1),
            new NotaFinal(10.0, 0)
        };

        assertTrue(Arrays.equals(notas_finales_esperadas, notas_finales));

    }

    @Test
    void alumnos_se_copian_mas_de_una_vez(){
        double[] notas;
        double[] notas_esperadas;

        edr.resolver(0, 0, 0);
        edr.resolver(1, 1, 1);
        edr.resolver(2, 2, 2);
        edr.resolver(3, 3, 3);

        
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 10.0, 10.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
        
        edr.copiarse(1);
        
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 20.0, 10.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.copiarse(1);
        
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 30.0, 10.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
        
        edr.copiarse(2);
        
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 30.0, 20.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));

        edr.copiarse(2);
        
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 30.0, 30.0, 10.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
        
        edr.copiarse(3);
        
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 30.0, 30.0, 20.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
        
        edr.resolver(0, 4, 0);
        edr.resolver(1, 5, 1);

        edr.copiarse(2);
        
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 30.0, 40.0, 20.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
        
        edr.copiarse(3);
        
        notas = edr.notas();
        notas_esperadas = new double[]{10.0, 30.0, 40.0, 30.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));


        for(int alumno = 0; alumno < 4; alumno++){
            edr.entregar(alumno);
        }

        int[] copiones = edr.chequearCopias();
        int[] copiones_esperados = new int[]{2,3};
        assertTrue(Arrays.equals(copiones_esperados, copiones));

        NotaFinal[] notas_finales = edr.corregir();
        NotaFinal[] notas_finales_esperadas = new NotaFinal[]{
            new NotaFinal(30.0, 1),
            new NotaFinal(10.0, 0)
        };

        assertTrue(Arrays.equals(notas_finales_esperadas, notas_finales));
    }

    @Test 
    void un_alumno_se_copia_de_la_darkweb(){
        double[] notas;
        double[] notas_esperadas;
        //todes resuelven bien un ejercicio excepto el estudiante 0
        for(int estudiante = 1; estudiante < 4; estudiante++){
            for(int pregunta = 0; pregunta <= estudiante; pregunta++){
                edr.resolver(estudiante, pregunta, estudiante);
            }
        }

        //alguien sube la solución con acceso para una persona, debe acceder el alumno 0
        edr.consultarDarkWeb(1, solucion);

        notas = edr.notas();
        notas_esperadas = new double[]{100.0, 10.0, 10.0, 10.0};

        assertTrue(Arrays.equals(notas, notas_esperadas));

        for(int estudiante = 0; estudiante < 4; estudiante++){
            edr.entregar(estudiante);
        }
        int[] copiones = edr.chequearCopias();
        int[] copiones_esperados = new int[]{};

        assertTrue(Arrays.equals(copiones, copiones_esperados));

        NotaFinal[] notas_finales = edr.corregir();
        NotaFinal[] notas_finales_esperadas = new NotaFinal[]{
            new NotaFinal(100.0, 0),
            new NotaFinal(10.0, 3),
            new NotaFinal(10.0, 2),
            new NotaFinal(10.0, 1),
        };

        assertTrue(Arrays.equals(notas_finales_esperadas, notas_finales));
    }

    @Test 
    void varios_alumnos_se_copian_de_la_darkweb(){
        double[] notas;
        double[] notas_esperadas;
        //todes resuelven bien un ejercicio excepto el estudiante 0
        for(int estudiante = 1; estudiante < 4; estudiante++){
            edr.resolver(estudiante, estudiante, estudiante);
        }

        //alguien sube la solución con acceso para una persona, debe acceder el alumno 0
        edr.consultarDarkWeb(3, solucion);

        notas = edr.notas();
        notas_esperadas = new double[]{100.0, 100.0, 100.0, 10.0};

        assertTrue(Arrays.equals(notas, notas_esperadas));

        for(int estudiante = 0; estudiante < 4; estudiante++){
            edr.entregar(estudiante);
        }
        int[] copiones = edr.chequearCopias();
        int[] copiones_esperados = new int[]{0,1,2,3};

        assertTrue(Arrays.equals(copiones, copiones_esperados));

        NotaFinal[] notas_finales = edr.corregir();
        NotaFinal[] notas_finales_esperadas = new NotaFinal[]{};

        assertTrue(Arrays.equals(notas_finales_esperadas, notas_finales));
    }

    @Test
    void darkweb_no_incluye_estudiantes_que_entregaron() {
        int[] solucion = new int[]{0, 1, 2, 3, 4};
        Edr edr = new Edr(5, 5, solucion);
        
        // Estudiante 0 entrega primero (con peor nota)
        edr.entregar(0);
        
        // Estudiantes 1-4 tienen notas bajas
        edr.resolver(1, 0, 0);
        edr.resolver(2, 1, 1);
        edr.resolver(3, 2, 2);
        edr.resolver(4, 3, 3);
        
        edr.consultarDarkWeb(2, solucion);
        
        double[] notas = edr.notas();
        double[] notas_esperadas = new double[]{0.0, 100.0, 100.0, 20.0, 20.0};
        assertTrue(Arrays.equals(notas_esperadas, notas));
    }

    @Test
    void varios_alumnos_se_copian_de_varios_examenes(){
        double[] notas;
        double[] notas_esperadas;
        Edr edr_8 = new Edr(d_aula, 8, solucion);
        int[] resolucion_1 = new int[]{9,8,7,6,5,4,3,2,1,0};
        int[] resolucion_2 = new int[]{2,2,2,2,2,2,2,2,2,2};
        int[] resolucion_3 = new int[]{0,0,2,2,5,6,7,0,0,9};

        for(int capa = 7; capa >= 0; capa--){
            for(int estudiante = 0; estudiante <= capa; estudiante++){
                edr_8.resolver(estudiante, capa, capa);
            }
        }

        notas = edr_8.notas();
        notas_esperadas = new double[]{80.0, 70.0, 60.0, 50.0, 40.0, 30.0, 20.0, 10.0};

        assertTrue(Arrays.equals(notas, notas_esperadas));

        edr_8.consultarDarkWeb(3, resolucion_1);

        notas = edr_8.notas();
        notas_esperadas = new double[]{80.0, 70.0, 60.0, 50.0, 40.0, 0.0, 0.0, 0.0};

        assertTrue(Arrays.equals(notas, notas_esperadas));

        edr_8.consultarDarkWeb(5, resolucion_2);

        notas = edr_8.notas();
        notas_esperadas = new double[]{80.0, 70.0, 60.0, 10.0, 10.0, 10.0, 10.0, 10.0};

        assertTrue(Arrays.equals(notas, notas_esperadas));

        edr_8.consultarDarkWeb(2, resolucion_3);

        notas = edr_8.notas();
        notas_esperadas = new double[]{80.0, 70.0, 60.0, 30.0, 30.0, 10.0, 10.0, 10.0};

        assertTrue(Arrays.equals(notas, notas_esperadas));

        for(int estudiante = 0; estudiante < 8; estudiante++){
            edr_8.entregar(estudiante);
        }
        int[] copiones = edr_8.chequearCopias();
        int[] copiones_esperados = new int[]{2,5,6,7};

        assertTrue(Arrays.equals(copiones, copiones_esperados));

        NotaFinal[] notas_finales = edr_8.corregir();
        NotaFinal[] notas_finales_esperadas = new NotaFinal[]{
            new NotaFinal(80.0, 0),
            new NotaFinal(70.0, 1),
            new NotaFinal(30.0, 4),
            new NotaFinal(30.0, 3),
            
        };

        assertTrue(Arrays.equals(notas_finales_esperadas, notas_finales));
    }

    @Test 
    void no_hay_aliasing_con_los_examenes_subidos(){
        double[] notas;
        double[] notas_esperadas;
        int[] resolucion_dark = new int[]{0,0,2,2,5,6,7,0,0,9};
        //todes resuelven bien un ejercicio excepto el estudiante 0
        for(int estudiante = 1; estudiante < 4; estudiante++){
            edr.resolver(estudiante, estudiante, estudiante);
        }

        //alguien sube la solución con acceso para una persona, debe acceder el alumno 0
        edr.consultarDarkWeb(1, resolucion_dark);

        notas = edr.notas();
        notas_esperadas = new double[]{30.0, 10.0, 10.0, 10.0};

        assertTrue(Arrays.equals(notas, notas_esperadas));

        //al cambiar la resolución, no debería cambiar la nota ni el resultado del examen
        resolucion_dark[1] = 1;
        resolucion_dark[3] = 3;

        notas = edr.notas();
        double[] notas_erroneas = new double[]{50.0, 10.0, 10.0, 10.0};
        
        assertFalse(Arrays.equals(notas, notas_erroneas));

        for(int estudiante = 0; estudiante < 4; estudiante++){
            edr.entregar(estudiante);
        }

        int[] copiones = edr.chequearCopias();
        int[] copiones_esperados = new int[]{2};

        assertTrue(Arrays.equals(copiones, copiones_esperados));

        NotaFinal[] notas_finales = edr.corregir();
        NotaFinal[] notas_finales_esperadas = new NotaFinal[]{
            new NotaFinal(30.0, 0),
            new NotaFinal(10.0, 3),
            new NotaFinal(10.0, 1),
        };

        assertTrue(Arrays.equals(notas_finales_esperadas, notas_finales));
    }

    // -------------------------------------------------- nuevos test -----------------------------------------------

    // ------------------------------------- EdR y notas -----------------------------------//
    @Test
    void estructura_correcta() {

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);
        // Cantidad de estudiantes
        assertEquals(cant_alumnos, edr.estudiantes.length);
        // cada nota pertenece a un Estudiante
        assertEquals(cant_alumnos, edr.notas().length);
    }


    //------------------------------------- copiarse -----------------------------------------------//

    // No hay vecinos 

    @Test
    void  no_hay_vecinos() {
        d_aula = 2; 
        cant_alumnos = 1; 

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        double[] notas_antes = edr.notas();

        edr.copiarse(0);

        double[] notas_despues = edr.notas();

        assertTrue(Arrays.equals(notas_antes, notas_despues));
    }    

    // Todos los vecinos no tienen respuestas, -1 ---------------------------------------------------------

    @Test
    void  tres_vecinos_lentos() {
        d_aula = 5;
        cant_alumnos = 5;

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        edr.resolver(4, 0, 0);
        edr.resolver(4, 1, 1);
        edr.resolver(4, 2, 2);
        edr.resolver(4, 3, 3);

        double[] notasAntes = edr.notas();

        edr.copiarse(4);

        double[] notasDepues = edr.notas();

        assertTrue(Arrays.equals(notasAntes,notasDepues));
    }

    @Test
    void  dos_vecinos_lentos() {
        d_aula = 5;
        cant_alumnos = 3;

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        edr.resolver(2, 0, 0);
        edr.resolver(2, 1, 1);
        edr.resolver(2, 2, 2);
        edr.resolver(2, 3, 3);

        double[] notasAntes = edr.notas();

        edr.copiarse(2);

        double[] notasDepues = edr.notas();

        assertTrue(Arrays.equals(notasAntes,notasDepues));

    }


    // No se puede copiar a alguien que ya entregó--------------------------------------------------------------

    @Test
    void  el_mas_copiable_entrego() {
        d_aula = 5;
        cant_alumnos = 3;

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        // el estudiante 1 resuelve su examen tranquilo
        edr.resolver(1, 0, 0);
        edr.resolver(1, 1, 1);
        edr.resolver(1, 2, 2);
        edr.resolver(1, 3, 3);

        // y entrega 
        edr.entregar(1);

        double[] notas_antes = edr.notas();

        // el estudiante 0 esta que se quiere matar pero al único que le puede copiar ya se fue (está en la esquina)
        edr.copiarse(0);

        // como no se puede copiar las notas deberian quedar igual
        double[] notas_despues = edr.notas();

        assertTrue(Arrays.equals(notas_antes,notas_despues));


    }

    @Test
    void  el_mas_copiable_entrego_pero_hay_alguien_mas() {
        d_aula = 5;
        cant_alumnos = 3;

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        // el estudiante 2 resuelve su examen tranquilo
        edr.resolver(2, 1, 1);
        edr.resolver(2, 2, 2);
        edr.resolver(2, 3, 3);

        // y entrega 
        edr.entregar(2);

        // y también el estudiante 0 la tiene re clara y va lento pero seguro resolviendo
        edr.resolver(0, 0, 0);
        edr.resolver(0, 1, 1);
  
        double[] notas_antes = edr.notas();
        
        // el estudiante 1 pobrecito está en otra, la mejor opción para copiar es el estudiante 2 pero ya se fue, le copia al 0
        edr.copiarse(1);
        

        double[] notas_despues = edr.notas();

        // las notas deberían ser distintas porque estudiante 1 copio
        assertFalse(Arrays.equals(notas_antes, notas_despues));

        // y la primera respuesta de estudiante 1 debe ser la misma que estudiante 0, así, le copió a estudiante 0 y no a estudiante 2
        int[] examen_0 = edr.estudiantes[0].obtenerExamen();
        int[] examen_1 = edr.estudiantes[1].obtenerExamen();

        assertTrue(examen_0[0] == examen_1[0]);
    }

    // empate de vecinos 

    @Test
    void  vecinos_con_mismas_cant_respuestas() {
        d_aula = 5;
        cant_alumnos = 3;

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        // otra vez los 3 de antes, solo que esta vez 0 y 2 tienen la misma cantidad de respuestas y misma copiabilidad o-o, debería desempatar por IDmayor

        edr.resolver(2, 1, 1);
        edr.resolver(2, 2, 2);
        edr.resolver(2, 3, 3);

        edr.resolver(0, 0, 0);
        edr.resolver(0, 1, 1);
        edr.resolver(0,2,2 );

        double[] notas_antes = edr.notas();

        // otra vez 1 quiere copiar, hay que hablar con estudiante 1.. (debería de copiarle a 2)
        edr.copiarse(1);
        
        double[] notas_despues = edr.notas();

        assertFalse(Arrays.equals(notas_antes, notas_despues));

        // pregunta de 1 igual a la de 2 

        int[] examen_2 = edr.estudiantes[2].obtenerExamen();
        int[] examen_1 = edr.estudiantes[1].obtenerExamen();
        
        assertTrue(examen_1[1] == examen_2[1]);

    }    

    // Alumno ya resolvió todo 
    @Test
    void  alumno_ya_resolivo_todo() {
        d_aula = 5;
        cant_alumnos = 5;
        solucion = new int[]{0,1,2,3};

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        // en verdad el estudiante 1 tiene potencial, pero le faltaba motivación (se motivó)
        edr.resolver(1, 0, 3);
        edr.resolver(1, 1, 2);
        edr.resolver(1, 2, 1);
        edr.resolver(1, 3, 0);

        //mientras los otros siguen haciendo el examen el ya terminó
        edr.resolver(0,0,0);
        edr.resolver(0,1,1);

        edr.resolver(2, 1, 1);

        //guardar momento
        double[] notasAntes = edr.notas();

        // pero aunque haya terminado, intenta copiar porque bueno, la costumbre
        edr.copiarse(1);

        // pero no debería haber logrado nada porque anotó con lapicero las respuestas
        double[] notasDepues = edr.notas();

        assertTrue(Arrays.equals(notasAntes,notasDepues));
    }

    // --------------- resolver ----------------
    @Test
    void  resolver_algo_resuelto() {
        d_aula = 5;
        cant_alumnos = 5;
        solucion = new int[]{0,1,2,3};

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        edr.resolver(1,0,0);
        edr.resolver(1,1,1);

        int[] examen_antes = edr.estudiantes[1].obtenerExamen();

        edr.resolver(1, 0, 1);

        int[] examen_despues = edr.estudiantes[1].obtenerExamen();
     
        assertTrue(Arrays.equals(examen_antes,examen_despues));
    }

    @Test
    void ya_entregado() {
        d_aula = 5;
        cant_alumnos = 5;
        solucion = new int[]{0,1,2,3};

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        edr.resolver(1,0,0);
        edr.resolver(1,1,1);

        edr.entregar(1);

        double[] notasAntes = edr.notas();

        edr.resolver(1, 2, 2);

        double[] notas_despues = edr.notas();

        assertTrue(Arrays.equals(notasAntes,notas_despues));

    }

    @Test
    void respuestas_incorrecta() {
        d_aula = 5;
        cant_alumnos = 5;
        solucion = new int[]{0,1,2,3};

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        edr.resolver(1, 0, 0);
        edr.resolver(1, 1, 1);
        edr.resolver(1, 2, 2);

        double nota_actual = edr.estudiantes[1].obtenerNota();

        edr.resolver(1,3,0);

        // como la respuesta nueva está mal, se mantiene la nota antigua
        double nota_nueva = edr.estudiantes[1].obtenerNota();

        assertTrue(nota_actual == nota_nueva);
    
    }
    
    @Test
    void respuestas_correcta() {
        d_aula = 5;
        cant_alumnos = 5;
        solucion = new int[]{0,1,2,3};

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        edr.resolver(1, 0, 0);
        edr.resolver(1, 1, 1);
        edr.resolver(1, 2, 2);

        double nota_actual = edr.estudiantes[1].obtenerNota();

        edr.resolver(1,3,3);

        // como la respuesta está bien, suma puntos
        double nota_nueva = edr.estudiantes[1].obtenerNota();

        assertTrue(nota_actual < nota_nueva);
    
    }

// ------------------------------------------ DarkWeb--------------------------------------------------
    @Test
    void darkweb_con_mas_k_que_alumnos() {
        d_aula = 5;
        cant_alumnos = 2;
        int[] solucion = new int[]{1,2,3,4,5};
        int[] solucionDW = new int[]{2,2,2,4,5};
        
        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        // no habían hecho nada antes de consultadrDarkWeb
        edr.consultarDarkWeb(10, solucionDW);

        // ahora todos deberían tener el mismo examen
        assertTrue(Arrays.equals(edr.estudiantes[0].obtenerExamen(), solucionDW));
        assertTrue(Arrays.equals(edr.estudiantes[1].obtenerExamen(), solucionDW));
        
    }

    @Test
    void darkweb_desepate_por_id() {
        d_aula = 5;
        cant_alumnos = 3;
        int[] solucion = new int[]{0,1,2,3,4,5};
        int[] solucionDW = new int[]{1,2,2,2,4,5};
        
        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        // hay 3 alumnos, el alumno 0 está resolviendo
        edr.resolver(0, 0, 0);
        edr.resolver(0, 1, 1);
        edr.resolver(0, 2, 2);


        // el 1 y el 2 están medio mal, pero uno se arriesga a consultarDarkWeb
        // debería cambiar solo el alumno 1

        int[] examen_0 = edr.estudiantes[0].obtenerExamen();
        int[] examen_2 = edr.estudiantes[2].obtenerExamen();

        edr.consultarDarkWeb(1, solucionDW);


        // ahora todos deberían tener el mismo examen menos el 1 
        assertTrue(Arrays.equals(edr.estudiantes[1].obtenerExamen(), solucionDW));

        assertTrue(Arrays.equals(edr.estudiantes[0].obtenerExamen(), examen_0));
        assertTrue(Arrays.equals(edr.estudiantes[2].obtenerExamen(), examen_2));
    }


    @Test
    void darkweb_todos_respondieron_al_menos_1() {
        d_aula = 5;
        cant_alumnos = 3;
        int[] solucion = new int[]{0,1,2,3,4,5};
        int[] solucionDW = new int[]{1,2,2,2,4,5};
        
        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        // alumno 0 
        edr.resolver(0, 0, 0);
        edr.resolver(0, 1, 1);
        edr.resolver(0, 2, 2);
        
        // alumno 1
        edr.resolver(1, 0, 0);
        edr.resolver(1, 2, 1);
        edr.resolver(1, 3, 2);

        // alumno 2
        edr.resolver(2, 0, 0);
        edr.resolver(2, 3, 1);
        edr.resolver(2, 2, 2);

        // misma lógica que la anterior 
        int[] examen_0 = edr.estudiantes[0].obtenerExamen();
        int[] examen_2 = edr.estudiantes[2].obtenerExamen();

        edr.consultarDarkWeb(1, solucionDW);


        // ahora todos deberían tener el mismo examen menos el 1 
        assertTrue(Arrays.equals(edr.estudiantes[1].obtenerExamen(), solucionDW));

        assertTrue(Arrays.equals(edr.estudiantes[0].obtenerExamen(), examen_0));
        assertTrue(Arrays.equals(edr.estudiantes[2].obtenerExamen(), examen_2));

    }
    
    @Test
    void darkweb_algunos_k_peores() {
        d_aula = 5;
        cant_alumnos = 3;
        int[] solucion = new int[]{0,1,2,3,4,5};
        int[] solucionDW = new int[]{1,2,2,2,4,5};
        
        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        // alumno 0 
        edr.resolver(0, 0, 0);
        edr.resolver(0, 1, 1);
        edr.resolver(0, 2, 2);
        
        // alumno 1
        edr.resolver(1, 0, 0);
        edr.resolver(1, 2, 1);
        edr.resolver(1, 3, 2);

        // alumno 2
        edr.resolver(2, 0, 0);
        edr.resolver(2, 3, 1);
        edr.resolver(2, 2, 2);

        // ahora se van a copiar el alumno 1 y 2
        int[] examen_0 = edr.estudiantes[0].obtenerExamen();

        edr.consultarDarkWeb(2, solucionDW);

        // ahora todos deberían tener el mismo examen menos el 1 y 2
        assertTrue(Arrays.equals(edr.estudiantes[1].obtenerExamen(), solucionDW));
        assertTrue(Arrays.equals(edr.estudiantes[2].obtenerExamen(), solucionDW));

        assertTrue(Arrays.equals(edr.estudiantes[0].obtenerExamen(), examen_0));

    }

    @Test
    void darkweb_alguno_entrego() {
        d_aula = 5;
        cant_alumnos = 4;
        int[] solucion = new int[]{0,1,2,3,4,5};
        int[] solucionDW = new int[]{1,2,2,2,4,5};
        
        Edr edr = new Edr(d_aula, cant_alumnos, solucion);
 
        // alumno 0 - 3 bien
        edr.resolver(0, 0, 0);
        edr.resolver(0, 1, 1);
        edr.resolver(0, 2, 2);
        
        // alumno 1 - 1 bien
        edr.resolver(1, 0, 0);
        edr.resolver(1, 2, 1);
        edr.resolver(1, 3, 2);

        // alumno 2 - 2 bien - entrega
        edr.resolver(2, 0, 0);
        edr.resolver(2, 3, 1);
        edr.resolver(2, 2, 2);

        // alumno 3 - 3 bien
        edr.resolver(3, 0, 0);
        edr.resolver(3, 3, 3);
        edr.resolver(3, 2, 2);

        // guardo los que no deberían cambiar 
        int[] examen_3 = edr.estudiantes[3].obtenerExamen();
        int[] examen_2 = edr.estudiantes[2].obtenerExamen();

        edr.entregar(2);

        edr.consultarDarkWeb(2, solucionDW);

        // ahora todos deberían tener el mismo examen menos el 1 y 2
        assertTrue(Arrays.equals(edr.estudiantes[1].obtenerExamen(), solucionDW));
        assertTrue(Arrays.equals(edr.estudiantes[0].obtenerExamen(), solucionDW));

        assertTrue(Arrays.equals(edr.estudiantes[2].obtenerExamen(), examen_2));
        assertTrue(Arrays.equals(edr.estudiantes[3].obtenerExamen(), examen_3));

    }



    @Test
    void peor_nota_entrego() {
        d_aula = 5;
        cant_alumnos = 6; // 3 van a tener muy buena nota pero no entregar, 3 van a tener peor nota pero entregar
        int[] solucion = new int[]{0,1,2,3};
        int[] solucionDW = new int[]{1,2,2,2};
        
        Edr edr = new Edr(d_aula, cant_alumnos, solucion);
 
        // alumno 0 ---- buena nota (no entrega)
        edr.resolver(0, 0, 0);
        edr.resolver(0, 1, 1);
        edr.resolver(0, 2, 2);
        edr.resolver(0, 3, 3);
        
        // alumno 1 ---- mas o menos (entrega)
        edr.resolver(1, 0, 0);
        edr.resolver(1, 1, 0);
        edr.resolver(1, 2, 0);

        edr.entregar(1);

        // alumno 2 - 2 buena nota (no entrega)
        edr.resolver(2, 0, 0);
        edr.resolver(2, 1, 1);
        edr.resolver(2, 2, 2);
        edr.resolver(2, 3, 0);

        // alumno 3 - bien (no entrega)
        edr.resolver(3, 0, 0);
        edr.resolver(3, 1, 3);
        edr.resolver(3, 2, 2);
        edr.resolver(3, 3, 3);

        // alumno 4 - mal (entrega)
        edr.resolver(4,3,0);

        edr.entregar(4);

        // alumno 5 - mal (entrega) 
        edr.resolver(5,2,3);

        edr.entregar(5);

        // no deberían cambiar los examenes del 1, 4 y 5 
        int[] examen_1 = edr.estudiantes[1].obtenerExamen();
        int[] examen_4 = edr.estudiantes[4].obtenerExamen();
        int[] examen_5 = edr.estudiantes[5].obtenerExamen();

        
        edr.consultarDarkWeb(3, solucionDW);

        // deberian haber cambiando los examenes de los alumnos 0, 2 y 3
        assertTrue(Arrays.equals(edr.estudiantes[0].obtenerExamen(), solucionDW));
        assertTrue(Arrays.equals(edr.estudiantes[2].obtenerExamen(), solucionDW));
        assertTrue(Arrays.equals(edr.estudiantes[3].obtenerExamen(), solucionDW));

        assertTrue(Arrays.equals(edr.estudiantes[1].obtenerExamen(), examen_1));
        assertTrue(Arrays.equals(edr.estudiantes[4].obtenerExamen(), examen_4));
        assertTrue(Arrays.equals(edr.estudiantes[5].obtenerExamen(), examen_5));
    }

    @Test
    void darkweb_triple_empate() {
        d_aula = 5;
        cant_alumnos = 3; 
        int[] solucion = new int[]{0,1,2,3};
        int[] solucionDW = new int[]{1,2,2,2};
        
        Edr edr = new Edr(d_aula, cant_alumnos, solucion);
        
        edr.resolver(0, 1, 1);

        edr.consultarDarkWeb(2, solucionDW);

        assertTrue(Arrays.equals(edr.estudiantes[1].obtenerExamen(), solucionDW));
        assertTrue(Arrays.equals(edr.estudiantes[2].obtenerExamen(), solucionDW));

    }

    // ----------------------------------------COLA-----------------------------------------------------

    @Test
    void lo_esperado_de_la_cola() { 
        d_aula = 5;
        cant_alumnos = 4;
        int[] solucion = new int[]{0,1,2,3,4};
        
        //vamos plantear lo esperado por la cola:
    
        int[] esperado = {1,3,0,2};

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        ColaDePrioridadEstudiantes cola = new ColaDePrioridadEstudiantes();
    
        // todo bien
        edr.resolver(2,0,0);
        edr.resolver(2,1,1);
        edr.resolver(2,2,2);
        edr.resolver(2,3,3);
        edr.resolver(2,4,4);

        // todo bien menos uno
        edr.resolver(0,0,0);
        edr.resolver(0,1,1);
        edr.resolver(0,2,2);
        edr.resolver(0,3,1);
        edr.resolver(0,4,4);

        int i = 0;
        while (cant_alumnos > i) {
            cola.encolar(edr.estudiantes[i]);
            i++;
        }
        
        int[] devuelve = new int[cant_alumnos];
        int j = 0;

        while (!cola.esVacia()) {
            devuelve[j] = cola.desencolar().obtenerId();
            j++;
        }

        assertTrue(Arrays.equals(devuelve, esperado));
            
    }


    // -------------------------------------------corregir() + chequearCopia() ------------------------------------------


    @Test
    void corregir_sin_copiones_y_con_desempate() { 
        d_aula = 5;
        cant_alumnos = 4;
        int[] solucion = new int[]{0,1,2,3};
        

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        double[] notasEsperadas = {50.0,50.0,25.0,25.0};
        int[] estudiantesEsperados = {3,0,2,1};

        // alumno_0 debería tener 50 puntos 
        edr.resolver(0,3,3);
        edr.resolver(0,2,2);
        edr.entregar(0);

        // alumno_1 debería tener 25 puntos
        edr.resolver(1,1,2);
        edr.resolver(1,2,2);
        edr.entregar(1);

        // alumno_2 debería tener 25 puntos
        edr.resolver(2,1,0);
        edr.resolver(2,2,2);
        edr.entregar(2);

        // alumno_3 debería tener 50 puntos
        edr.resolver(3,1,1);
        edr.resolver(3,2,2);
        edr.entregar(3);

        // por especificación 
        int[] copiones = edr.chequearCopias();

        NotaFinal [] notas_alumnos = edr.corregir();

        double [] notasCorregir = new double[4];
        int [] alumnos = new int[4];

        int i = 0;

        while(i < notas_alumnos.length){
            notasCorregir[i] = notas_alumnos[i]._nota;
            alumnos[i] = notas_alumnos[i]._id;
            i++;
        }

        assertTrue(Arrays.equals(notasEsperadas, notasCorregir));
        assertTrue(Arrays.equals(estudiantesEsperados, alumnos));
        assertEquals(copiones.length, 0);
    }

    
    // ---################# no terminado ################---
    @Test 
    void copiones_segun_criterio_de_copia() {  // pasan muchas cosas acá O-O 
        d_aula = 5;
        cant_alumnos = 7;
        int[] solucion = new int[]{0,1,2,3,4,5,6,7}; 
        int[] solucionDW = new int[]{0,0,0,0,0,0,0,0};

        Edr edr = new Edr(d_aula, cant_alumnos, solucion);

        double[] notasEsperadas = {50.0, 50.0, 25.0,25.0,12.5,12.5};
        int[] estudiantesEsperados = {1,0,5,3,6,4};
        int[] copionesEsperados = {2};

        // ----------proceso de alumno_0
        
        edr.resolver(0,1,1);
        edr.resolver(0,2,2);
        edr.resolver(0, 6, 6);
        edr.copiarse(0); // 1 no hizo nada todavía así q no copia nada
        edr.resolver(0, 4, 4);

        // alumno_0 -> tiene 4 respuestas correctas {1,2,6,4}


        // ---------alumno_1...
        edr.copiarse(1);
        edr.copiarse(1);
        edr.copiarse(1);
        edr.copiarse(1);
        // se copia las 4 preguntas de alumno 0
        // tiene 4 bien {1,2,6,4}

        // ---------alumno_2
        edr.copiarse(2);
        edr.copiarse(2);
        // se copia las 2 primeras preguntas de 1, quien las había copiado de 0, tiene 2 preguntas bien {1,2}


        // alumno_3 
        edr.resolver(3,0,2);
        edr.resolver(3,3,3);
        edr.copiarse(3);
        // tiene 2 bien {1,3}


        // alumno_4 solo entrega
        // tiene 0 


        // alumno_5
        edr.resolver(5,2,0);
        edr.resolver(5,3,2);
        edr.resolver(5, 5, 5);
        edr.resolver(5,4,4);
        // tiene 2 bien 


        // alumno_6

        edr.copiarse(6);
        edr.copiarse(6);
        edr.copiarse(6);

        // se copia 3 mal, tiene 1 puntos 


        edr.consultarDarkWeb(2, solucionDW); // los que deberían cambiar serían alumno_4 y alumno_6 -> cant_correcta = 1



        // se acabó el tiempo, todos entregan
        for(int i = 0; i < cant_alumnos ;i++){
            edr.entregar(i);
        }

        // 25% de cant_estudiantes - 1 = 1.5 como los darkWeb Son solo 2 no entran como sospechosos 


        int[] copiones = edr.chequearCopias();
        NotaFinal [] notas_alumnos = edr.corregir();

        double [] notasCorregir = new double[notas_alumnos.length];
        int [] alumnos = new int[notas_alumnos.length];

        int i = 0;

        while(i < notas_alumnos.length){
            notasCorregir[i] = notas_alumnos[i]._nota;
            alumnos[i] = notas_alumnos[i]._id;
            i++;
        }

        System.out.println("Esperadas: " + Arrays.toString(notasEsperadas));
        System.out.println("Obtenidas: " + Arrays.toString(notasCorregir));
        System.out.println("IDs esperados: " + Arrays.toString(estudiantesEsperados));
        System.out.println("IDs obtenidos: " + Arrays.toString(alumnos));
        System.out.println("Copiones esperados: " + Arrays.toString(copionesEsperados));
        System.out.println("Copiones obtenidos: " + Arrays.toString(copiones));


        //assertTrue(Arrays.equals(notasEsperadas, notasCorregir));
        //assertTrue(Arrays.equals(estudiantesEsperados, alumnos));
        //assertTrue(Arrays.equals(copiones, copionesEsperados));


    }



}*/