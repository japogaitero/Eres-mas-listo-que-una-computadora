/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package eljuego;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 *
 * @author Victor Alvarez <japogaitero at gmail.com>
 */
public class Preguntas {
    //private HashMap<String, String> preguntaRespuesta = new HashMap<>();
    //private ArrayList <HashMap> preguntas = new ArrayList ();
    
    
    public Preguntas() {
    }
    
    public boolean preguntasAleatorias() throws ScriptException, FileNotFoundException {
        int random;
        boolean acierto = false;
        String respuesta = "";
        String posibleRespuesta;
        char posibleLetraRespuesta;
        random = ElJuego.aleatorio(1,3);
        Scanner teclado = new Scanner (System.in);
        
        switch(random) {
            case 1:
                respuesta = adivinaPalabra();
                respuesta = Normalizer.normalize(respuesta , Normalizer.Form.NFD);
                respuesta = respuesta.replaceAll("[^\\p{ASCII}]", "");
                posibleRespuesta = teclado.nextLine();
                if (posibleRespuesta.equalsIgnoreCase(respuesta)){
                    acierto = true;
                }
                break;
            case 2:
                respuesta = respuestaMates();
                posibleRespuesta = teclado.nextLine();
                if (posibleRespuesta.equals(respuesta)){
                    acierto = true;
                }
                break;
            case 3:
                ArrayList <String> preguntaConLetra = new ArrayList();
                preguntaConLetra = preguntaInglesCPU();
                System.out.println("introduce la opcion que creas");
                posibleLetraRespuesta=teclado.next().charAt(0);
                if(String.valueOf(posibleLetraRespuesta).equalsIgnoreCase(preguntaConLetra.get(1))){
                    //System.out.println("Vale has acertado");
                    acierto = true;
                }else{
                    System.out.println("La ocpión correcta era " + preguntaConLetra.get(1) + " - " + preguntaConLetra.get(0));
                }
                break;
        }
        if(acierto == false){
            System.out.println("La respuesta era " + respuesta);
        }
        return acierto;
    }
    
    public boolean preguntasAleatoriasCPU(String cpu) throws ScriptException, FileNotFoundException {
        boolean acierto = false;
        int random, randomIngles;
        char [] letrasCpu = {'A','B','C','D'};
        char opcionCpu;
        String respuesta = "";
        random = ElJuego.aleatorio(1,3);
        randomIngles = ElJuego.aleatorio(0, 3);
       
        switch(random) {
            case 1:
                respuesta = adivinaPalabra();                
                System.out.println("A los ordenadores no se le dan nada bien las palabras y no sabe que contestar.");
                ElJuego.escribeParaContinuar("Presiona enter para ver la respuesta y continuar");
                System.out.println("La respuesta era " + respuesta);
                break;
            case 2:
                respuesta = respuestaMates();
                acierto = true;
                System.out.println(cpu + " responde\t" + respuesta); 
                System.out.println("Por supuesto acertará, los ordenadores son muy buenos en calculos");
                ElJuego.escribeParaContinuar("Presiona enter para ver el resultado y continuar");
                System.out.println("La respuesta es " + respuesta);
                break;
            case 3:
                ArrayList <String> preguntaConLetra = new ArrayList();
                preguntaConLetra = preguntaInglesCPU();
                opcionCpu = letrasCpu[randomIngles];
                System.out.println("El ordenador elige al azar la letra " + opcionCpu);
                ElJuego.escribeParaContinuar("Presiona enter para ver la respuesta correcta y ver si el CPU ha acertado.");
                if(String.valueOf(opcionCpu).equalsIgnoreCase(preguntaConLetra.get(1))){
                    System.out.println("La ocpión correcta es " + preguntaConLetra.get(1) + " - " + preguntaConLetra.get(0));                    
                    acierto = true;
                }else{
                    System.out.println("La ocpión correcta es " + preguntaConLetra.get(1) + " - " + preguntaConLetra.get(0));
                }
                break;
        }
        
        
        
        return acierto;
    }
    
    public static String adivinaPalabra () throws ScriptException{
        String palabra = "" ;
        int asteriscos, posicion;
        boolean continua;
        char[] arrayTemp ;
        ArrayList <Integer> posiciones = new ArrayList();
        ArrayList <String> palabras = new ArrayList();
        System.out.print("Adivina la letras que faltan en la palabra: (introduce la palabra sin acentos) ");
        try {
            
            File f = new File ("src/eljuego/diccionario.txt");
            Scanner diccionario = new Scanner (f);
            palabra = diccionario.next();
            
            while(diccionario.hasNext() ){
                String pal = diccionario.next();
                if (pal.length() > 3){
                    palabras.add(pal);
                }
            }
            palabra = palabras.get((int) Math.floor(Math.random()*(palabras.size())));
            arrayTemp = palabra.toCharArray();
            asteriscos = palabra.length()/3;
            posicion = (int) Math.floor(Math.random()*(palabra.length()));
            posiciones.add(posicion);
            
            while(asteriscos > 0 ){
                continua = true;
                posicion = (int) Math.floor(Math.random()*(palabra.length()));
                
                if (posiciones.contains(posicion)){
                    continua = false;
                    posicion = (int) Math.floor(Math.random()*(palabra.length()));
                }
                if (continua == true){
                    posiciones.add(posicion);
                    arrayTemp[posicion] = '*';
                    asteriscos--;
                }
            }
            
            String palabraadivina = String.valueOf(arrayTemp);
            System.out.println(palabraadivina);
            
            System.out.println("la respuesta para probar el programa " + palabra);
            
            
        }catch (FileNotFoundException | NumberFormatException | NoSuchElementException e ){
            System.out.println("Error: "+ e);
        }
        
        return palabra;
    }
    
    public static String respuestaMates () throws ScriptException{
        String pregunta;
        String respuesta;
        int resultado, nEnteros, valorDado, operandoRandom;
        String operacion [];
        String[] operandos = {"+", "-", "*"};
        HashMap<String, String> preguntaRespuesta = new HashMap<>();
        
        //Calcula el numero aleatorio de valores a calcular, entre 4 y 8 (incluidos)
        nEnteros = ElJuego.aleatorio(4, 8);
        //Calcula un valor aleatorio entre 2 y 12 (incluidos)
        valorDado = ElJuego.aleatorio(2,12);
        //Selecciona un operando aleatorio de los que contiene el Array de operandos
        operandoRandom = ElJuego.aleatorio(1, 3);
        //Crea un Array con el numero exacto de posiciones contando valores y operandos (+,-,*)
        operacion = new String [nEnteros + (nEnteros-1)];
        
        //Recorre el aray por primera vez y añade los enteros aleatorios en las posiciones n+2 (empezando en 0)
        for (int i = 0; i < operacion.length; i = i+2){
            operacion[i] = String.valueOf(valorDado);
            valorDado = (int) Math.floor(Math.random()*11+2);
        }
        //Recorre el array por segunda vez añadiendo los operandos en los huecos entre los enteros anteriormente añadidos
        for (int i = 1; i < operacion.length-1; i = i+2){
            operandoRandom = (int) Math.floor(Math.random()*3);
            operacion[i] = operandos[operandoRandom];
            //System.out.print(" ");
        }
        // Y el resultado es...
        System.out.print("Realice la siguiente operacion: ");
        for (String i : operacion) {
            System.out.print(i);
            System.out.print(" ");
        }
        
        System.out.println("");
        //aqui convertido a String para poder calcularlo con el ScriptEngineManager después
        pregunta = String.join(" ",operacion );
        
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        Object result = engine.eval(pregunta);
        resultado = Integer.decode(result.toString());
        respuesta = String.valueOf(resultado);
        
        System.out.println("la respuesta para probar el programa " + respuesta);
        
        return respuesta;
    }
    
    public static String preguntaIngles () throws ScriptException{
        String respuesta= "";
        String temp;
        char opc = 'A';
        int index;
        String [] pregunta = new String[5];// Array compuesto por la pregunta y las posibles erepsuestas
        ArrayList <String[]> preguntas = new ArrayList();// arrayList compuesto por
        HashMap<String [], String> preguntaRespuesta = new HashMap<>();
        Scanner teclado = new Scanner(System.in);
        
        try {
            
            File f = new File ("src/eljuego/ingles.txt");
            Scanner ingles = new Scanner (f);
            
            while(ingles.hasNext() ){    // rellena el Array pregunta y lo intoduce en el ArrayList preguntas
                pregunta = new String[5];
                pregunta [0] = ingles.nextLine();
                pregunta [1] = ingles.nextLine();
                pregunta [2] = ingles.nextLine();
                pregunta [3] = ingles.nextLine();
                pregunta [4] = ingles.nextLine();
                preguntas.add(pregunta);
            }
            // calcula un valor aleatorio de entre las posiciones (o preguntas) que tiene el ArrayList preguntas
            int y = (int) (Math.random()*preguntas.size());
            System.out.println(preguntas.get(y)[0]);// Array correspondiente a una pregunta aleatoria
            pregunta = preguntas.get(y); // pregunta
            respuesta = pregunta[1];
            for (String i : pregunta ){
                System.out.println(i);
            }
            
            // esto desordena las respuestas de la pregunta
            for (int i = pregunta.length - 1; i > 0; i--){
                //tiene en cuenta que la pregunta no hay que desordenarla (posiciones del array (1-4))
                index = ElJuego.aleatorio(1, 4);
                temp = pregunta[index];
                pregunta[index] = pregunta[i];
                pregunta[i] = temp;
            }
            
            for (int i = 1 ; i < pregunta.length ; i++){
                System.out.println(opc + " - " + pregunta[i]);
                opc++;
            }
            
            preguntaRespuesta.put(pregunta, respuesta);
            
            
            
        }catch (FileNotFoundException | NumberFormatException | NoSuchElementException e ){
            System.out.println("Error: "+ e);
        }
        return respuesta;
    }
    
    public static ArrayList preguntaInglesCPU () throws ScriptException, FileNotFoundException{
        char opc = 'A';
        String respuesta= "";
        String letraCorrecta = "";
        ArrayList <String> pregunta = new ArrayList();
        ArrayList  <ArrayList> arrayPreguntas = new ArrayList();
        HashMap < String , Character > letraRespuesta = new HashMap <> ();
        ArrayList <String> preguntaConLetra = new ArrayList();
        Scanner ingles = null;
        
        try {
            
            File f = new File ("src/eljuego/ingles.txt");
            ingles = new Scanner (f);
            
            while(ingles.hasNext() ){// rellena el arrayPreguntas con arrays (pregunta) formados  por la pregunta las posibles opciones
                pregunta = new ArrayList();
                pregunta.add(ingles.nextLine());
                pregunta.add(ingles.nextLine());
                pregunta.add(ingles.nextLine());
                pregunta.add(ingles.nextLine());
                pregunta.add(ingles.nextLine());
                arrayPreguntas.add(pregunta);
            }
            // calcula un valor aleatorio de entre las posiciones (o preguntas) que tiene el ArrayList preguntas
            int y = (int) (Math.random()*arrayPreguntas.size());
            pregunta = arrayPreguntas.get(y); // pregunta al azar con sus posibles respuestas
            System.out.println(pregunta.get(0));// pinta solo el encunciado de una pregunta al azar
            pregunta.remove(0);// borra el enunciado para luego después solo desordenar las opciones (A,B,C,D)
            respuesta = pregunta.get(0); // la primera opcion es la correcta
            
            for (String i : pregunta ){// pinta las preguntas aun en orden
                System.out.println(i);
            }
            Collections.shuffle(pregunta); // desordena las respuestas
            for (String i : pregunta) {// pinta las posibles opciones desordenadas y aginadas a (A,B,C,D)
                System.out.println( opc + " - " + i );
                letraRespuesta.put(i,opc);//lo introduce en un hash map para luego asignar la letra a la opcion correcta
                opc++;
            }
            for (String i : letraRespuesta.keySet()) {//recorre el HashMap y se comprueba que respuesta equivale a la opcion correcta para obtener la letra
                if(respuesta.equals(i)){
                    letraCorrecta = Character.toString(letraRespuesta.get(i));
                }
            }
            preguntaConLetra.add(respuesta);
            preguntaConLetra.add(letraCorrecta);
            
            
        }catch (FileNotFoundException | NumberFormatException | NoSuchElementException e ){
            e.printStackTrace();
            
        }
        ingles.close();
        return preguntaConLetra;
    }
}
