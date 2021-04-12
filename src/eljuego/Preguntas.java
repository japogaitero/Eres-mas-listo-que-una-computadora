/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package eljuego;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;
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
    
    public Preguntas() throws ScriptException {
        
        
    ArrayList <String> respuestas = new ArrayList ();
    
    respuestas.add(adivinaPalabra());
    respuestas.add(respuestaMates());
    respuestas.add(preguntaIngles());
    
        System.out.println("------------");
    
    for (String i : respuestas){
        System.out.print(i + "\t\t");
        
    }
    
    }
    
    public static String adivinaPalabra () throws ScriptException{
        String palabra = "" ;
        HashMap<String, String> preguntaRespuesta = new HashMap<>();
        
        System.out.println("Adivine la letras que faltan");
        try {
            
            File f = new File ("src/pruebas/diccionario.txt");
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
            //System.out.println("la palabra tiene " + palabra.length() + " posiciones y tendra " + asteriscos +" asteriscos");
            
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
            
            preguntaRespuesta.put(palabraadivina, palabra);
            
            
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
        System.out.println("Realice la siguiente operacion");
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
        //System.out.println(respuesta);
        
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
        
        try {
            Scanner teclado = new Scanner(System.in);
            int x = (int) (Math.random()*4);
            
            File f = new File ("src/pruebas2222/ingles.txt");
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
                //System.out.println(i);
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
            
            char opc = 'A';
            for (int i = 1 ; i < pregunta.length - 1; i++){
            //for (String i : pregunta ){
                System.out.println(opc + " - " + i);
                opc++;
            }
            
            preguntaRespuesta.put(pregunta, respuesta);
            
            
            
        }catch (FileNotFoundException | NumberFormatException | NoSuchElementException e ){
            System.out.println("Error: "+ e);
        }
        return respuesta;
    }
}

