/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package eljuego;

import java.io.File;
import java.io.FileNotFoundException;
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
        
        //este metodo selecciona de manera aleatoria una pregunta y asigna su corresponiendte respuesta       
        switch(random) {
            case 1:
                respuesta = adivinaPalabra();
                posibleRespuesta = teclado.nextLine();
                if (posibleRespuesta.equals(respuesta)){
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
        char [] letrasCpu = {'a','b','c','d'};
        char opcionCpu;
        String respuesta = "";
        random = ElJuego.aleatorio(1,3);  
        randomIngles = ElJuego.aleatorio(1, 4);
        /*este metodo selecciona de manera aleatoria una pregunta y asigna su corresponiendte respuesta
        
        */
        switch(random) {
            case 1:
                respuesta = adivinaPalabra();
                break;
            case 2:
                respuesta = respuestaMates();
                acierto = true;
                System.out.println(cpu + " responde..." + respuesta + "(seguramente acierte, los ordenadores son muy buenos en calculos");
                break;
            case 3:
                ArrayList <String> preguntaConLetra = new ArrayList();
                preguntaConLetra = preguntaInglesCPU();
                opcionCpu = letrasCpu[randomIngles];
                System.out.println("El ordenador elige la letra " + opcionCpu);
                if(String.valueOf(opcionCpu).equalsIgnoreCase(preguntaConLetra.get(1))){
                    System.out.println("");
                    acierto = true;
                }else{
                    System.out.println("La ocpión correcta era " + preguntaConLetra.get(1) + " - " + preguntaConLetra.get(0));
                }
                break;
        }
        
        System.out.println("Y la respuesta es " + respuesta);
        
        return acierto;
    }
    
    public static String adivinaPalabra () throws ScriptException{
        String palabra = "" ;        
        
        System.out.print("Adivine la letras que faltan en la palabra: ");
        try {
            
            File f = new File ("src/eljuego/diccionario.txt");
            Scanner diccionario = new Scanner (f);
            palabra = diccionario.next();
            
            ArrayList <String> palabras = new ArrayList();
            while(diccionario.hasNext() ){
                String pal = diccionario.next();
                if (pal.length() > 3){
                    palabras.add(pal);
                }
            }
            palabra = palabras.get((int) Math.floor(Math.random()*(palabras.size())));
            //System.out.println(palabra);
            
            char[] arrayTemp = palabra.toCharArray();
            int asteriscos = palabra.length()/3;
            
            
            int posicion = (int) Math.floor(Math.random()*(palabra.length()));
            //System.out.println("el primer posicion es " + posicion);
            ArrayList <Integer> posiciones = new ArrayList();
            posiciones.add(posicion);
            boolean continua = true;
            
            while(asteriscos > 0 ){
                //System.out.println("laaaa! "+ posicion);
                continua = true;
                //arrayTemp[posicion] = '*';
                posicion = (int) Math.floor(Math.random()*(palabra.length()));
                //System.out.println("siguiente int es  " + posicion);
                if (posiciones.contains(posicion)){
                    continua = false;
                    //System.out.println("esta repetido");
                    posicion = (int) Math.floor(Math.random()*(palabra.length()));
                    //posiciones.remove(posiciones.size()-1);
                }
                if (continua == true){
                    posiciones.add(posicion);
                    arrayTemp[posicion] = '*';
                    asteriscos--;
                }
            }
            //arrayTemp[5] = '*'; //Donde 'x' es la posición que buscas
            String palabraadivina = String.valueOf(arrayTemp);
            System.out.println(palabraadivina);
            
            System.out.println("la respuesta para probar el programa " + palabra);
            
            
        }catch (FileNotFoundException | NumberFormatException | NoSuchElementException e ){
            System.out.println("Error: "+ e);
        }
        
        return palabra;
    }
    
    public static String respuestaMates () throws ScriptException{/// ESTE METODO ESTA YA RESUELTOOOOOO
        
        String pregunta;
        String respuesta;
        int resultado;
        String[] operandos = {"+", "-", "*"};
        HashMap<String, String> preguntaRespuesta = new HashMap<>();
        
        //Calcula el numero aleatorio de valores a calcular, entre 4 y 8 (incluidos)
        int nEnteros = (int) Math.floor(Math.random()*5+4 );
        //Calcula un valor aleatorio entre 2 y 12 (incluidos)
        int valorDado = valorDado = (int) Math.floor(Math.random()*11+2);
        //Selecciona un operando aleatorio de los que contiene el Array de operandos
        int operandoRandom = (int) Math.floor(Math.random()*3);
        //Crea un Array con el numero exacto de posiciones contando valores y operandos (+,-,*)
        String operacion [] = new String [nEnteros + (nEnteros-1)];
        
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
        //String Ppregunta = "Realice la siguiente operacion:  " + pregunta;
        //System.out.print(pregunta);
        
        //System.out.println("");
        
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");
        Object result = engine.eval(pregunta);
        resultado = Integer.decode(result.toString());
        respuesta = String.valueOf(resultado);
        System.out.println("la respuesta para probar el programa " + respuesta);
        
        preguntaRespuesta.put(pregunta, respuesta);
        
        return respuesta;
        
    }
    
    public static String preguntaIngles () throws ScriptException{
        String respuesta= "";
        //HashMap <String, String> preguntaConLetra = new HashMap<>();
        String [] pregunta = new String[5];// Array compuesto por la pregunta y las posibles erepsuestas
        ArrayList <String[]> preguntas = new ArrayList();// arrayList compuesto por
        HashMap<String [], String> preguntaRespuesta = new HashMap<>();
        int index;
        String temp;
        char opc = 'A';
        
        try {
            Scanner teclado = new Scanner(System.in);
            int x = (int) (Math.random()*4);
            
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
            //System.out.println("-----------------");
            
            // esto desordena las respuestas de la pregunta
            for (int i = pregunta.length - 1; i > 0; i--){
                //tiene en cuenta que la pregunta no hay que desordenarla (posiciones del array (1-4))
                index = (int) Math.floor(Math.random()*4+1);
                temp = pregunta[index];
                pregunta[index] = pregunta[i];
                pregunta[i] = temp;
            }
            
            for (String i : pregunta ){
                //System.out.println(i);
            }
            //System.out.println("-----------------");
            
            
            for (int i = 1 ; i < pregunta.length ; i++){
                //for (String i : pregunta ){
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
        
        try {   
            
            File f = new File ("src/eljuego/ingles.txt");
            Scanner ingles = new Scanner (f);
            
            while(ingles.hasNext() ){    // rellena el arrayPreguntas con arrays (pregunta) formados  por la pregunta las posibles opciones
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
            System.out.println("Error: "+ e);
        }
        return preguntaConLetra;
    }
}
