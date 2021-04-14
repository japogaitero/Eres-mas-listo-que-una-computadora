/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package eljuego;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.script.ScriptException;

/**
 *
 * @author Victor Alvarez <japogaitero at gmail.com>
 */
public class Partida {
    private int nJugadores;
    private int nRondas;
    private ArrayList <Jugadores> JugadoresPartida;
    private ArrayList <String> PosiblesJugadores = new ArrayList ();

   
    
    public Partida (){
        
    }
    
    public Partida(int nJugadores, int nRondas, ArrayList JugadoresPartida) {
        this.nJugadores = nJugadores;
        this.nRondas = nRondas;
        this.JugadoresPartida = new ArrayList<>();
    }

    public int getnJugadores() {
        return nJugadores;
    }
    
    public void setnJugadores(int nJugadores) {
        this.nJugadores = nJugadores;
    }
    
    public int getnRondas() {
        return nRondas;
    }
    
    public void setnRondas(int nRondas) {
        this.nRondas = nRondas;
    }
    
    public ArrayList<Jugadores> getJugadoresPartida() {
        return JugadoresPartida;
    }

    public void setJugadoresPartida(ArrayList<Jugadores> JugadoresPartida) {
        this.JugadoresPartida = JugadoresPartida;
    }

    public ArrayList<String> getPosiblesJugadores() {
        return PosiblesJugadores;
    }

    public void setPosiblesJugadores(ArrayList<String> PosiblesJugadores) {
        this.PosiblesJugadores = PosiblesJugadores;
    }
    
    
    
    
    public void menu()throws Exception, IOException{        
        Partida nuevaPartida = new Partida();
        int opcion;
        boolean continua;
        Scanner teclado = new Scanner (System.in);
        
        
        do {
            System.out.println("Elije cuántos usuarios van a jugar. 1 mínimo o 4 como máximo");
            try{
                continua = false;
                teclado = new Scanner (System.in);
                opcion = teclado.nextInt();
                if (opcion <= 0 || opcion > 4 ){
                    throw new MiExcepcion(14);
                }else if (opcion == 1){
                    nuevaPartida.setnJugadores(opcion);
                    System.out.println("Has elejido jugar tu solo");
                    continua = false;
                }else{                   
                    nuevaPartida.setnJugadores(opcion);
                    System.out.println("Has elegido una partida de " + nuevaPartida.getnJugadores() + " jugadores");
                    continua = false;
                }
                
            }catch (InputMismatchException e ){
                System.out.println("Valor no válido, por favor introduzca un número de jugadores entre 1 y 4");
                continua = true;
            }catch (MiExcepcion ex){
                System.out.println(ex.getMessage());
                continua = true;
                
            }
        }while (continua);
        
        do {
            System.out.println("Elije qué tipo de partida quieres jugar\n1. Partida rápida (3 rondas)\n2. Partida corta (5 rondas)\n"
                    + "3. Partida normal (10 rondas)\n4. Partida larga (20 rondas)");
            try{
                continua = false;
                teclado = new Scanner (System.in);
                opcion = teclado.nextInt();
                if (opcion <= 0 || opcion > 4 ){
                    throw new MiExcepcion(14);
                }else{                    
                    String partidas = "";
                    switch (opcion){
                        case 1:
                            nuevaPartida.setnRondas(3);
                            partidas = " rapida de 3 rondas";
                            break;
                        case 2:
                            nuevaPartida.setnRondas(5);
                            partidas = " corta de 5 rondas";
                            break;
                        case 3:
                            nuevaPartida.setnRondas(10);
                            partidas = " normal de 10 rondas";
                            break;
                        case 4:
                            nuevaPartida.setnRondas(20);
                            partidas = " larga de 20 rondas";
                            break;
                    }                    
                    System.out.println("Has elegido una partida" + partidas);
                    continua = false;
                }
            }catch (InputMismatchException e ){
                System.out.println("Valor no válido, por favor introduzca un número de jugadores entre 1 y 4");
                continua = true;
            }catch (MiExcepcion ex){
                System.out.println(ex.getMessage());                
                continua = true;
            }
            
        }while (continua);
        
        JugadoresPartida = pedirJugadores(nuevaPartida.getnJugadores());
        System.out.println("\nY los jugadores para esta partida son:");
        for (Jugadores i : JugadoresPartida){
            System.out.print(i.getNombre());
            System.out.println("\t");            
        }
        System.out.println("Vamos a decidir aleatoriamente el orden de los jugadores...");
        
        Collections.shuffle(JugadoresPartida);
        System.out.println("\nEl orden de los jugadores para responder es:");
        for (Jugadores i : JugadoresPartida){
            System.out.print(i.getNombre());
            System.out.println("\t");            
        }
        jugandoPartida(JugadoresPartida, nuevaPartida.getnRondas());
        
    }
    
    public ArrayList pedirJugadores (int numJugadores)throws IOException, MiExcepcion{
        
        ArrayList <Jugadores> JugadoresPartida = new ArrayList ();
        ArrayList <String> PosiblesJugadores = new ArrayList ();
        int opcion; 
        String nombreJugador = "";
        //String nombreJugadorPartida = "";
        boolean continua, jugadorOK;
        Scanner teclado = new Scanner (System.in);
        
        if (numJugadores == 1){
            do{
                System.out.println("\nSeleciona:\n1. Jugador existente\n2. Jugador nuevo\n");                
                try{
                    continua = false;                    
                    opcion = teclado.nextInt();
                    switch(opcion) {
                        case 1:
                            GestionJugadores gestion1 = new GestionJugadores ();
                            nombreJugador = gestion1.jugadorExistente(); 
                            if (nombreJugador.isEmpty()){
                                continua = true;
                                break;
                            }else if (PosiblesJugadores.isEmpty()){
                                PosiblesJugadores.add(nombreJugador);
                                Jugadores nuevo = new Jugadores (nombreJugador);
                                JugadoresPartida.add(nuevo);                                
                            }                            
                            continua = false; 
                            break;
                        case 2:
                            GestionJugadores gestion2 = new GestionJugadores ();
                            nombreJugador = gestion2.nuevoJugador();
                            if (PosiblesJugadores.isEmpty()){
                                PosiblesJugadores.add(nombreJugador);
                                Jugadores nuevo = new Jugadores (nombreJugador);
                                JugadoresPartida.add(nuevo);                                
                            }  
                            continua = false;                            
                            break;
                        
                        default:
                            System.out.println("Valor no valido, por favor selecciones :");
                            continua = true;
                    }                    
                    
                }catch (InputMismatchException e ){
                    System.out.println("Valor no válido, por favor introduzca una opción válida (entre 1 y 3)");
                    continua = true;
                } catch (IOException ex){
                    System.out.println("Error: "+ ex);
                    continua = true;
                }
                }while (continua == true);            
        }else if (numJugadores > 1){
                
        
        while (numJugadores > 0){
            //System.out.println("Selecciona a los jugadores:");
            do{
                
                System.out.println("\n1. Jugador existente\n2. Jugador nuevo\n3. Juega contra el ordenador (CPU)");                
                try{
                    continua = false;                    
                    opcion = teclado.nextInt();
                    switch(opcion) {
                        case 1:
                            GestionJugadores gestion1 = new GestionJugadores ();                            
                            nombreJugador = gestion1.jugadorExistente();
                            if (nombreJugador.isEmpty()){
                                continua = true;
                                break;
                            }else{
                            jugadorOK = comprobarJugadores(nombreJugador, PosiblesJugadores, JugadoresPartida);
                            if (jugadorOK == false){
                                continua = true;
                                break;
                            }else{
                                if (numJugadores > 1){
                                    System.out.println("Selecciona el siguiente Jugador / Ordenador");
                                }else if (numJugadores ==2 ){
                                    System.out.println("Selecciona al ultimo jugador");
                                }
                            }
                            }
                            continua = false;
                            break;
                        case 2:
                            GestionJugadores gestion2 = new GestionJugadores ();
                            nombreJugador = gestion2.nuevoJugador();
                            jugadorOK = comprobarJugadores(nombreJugador, PosiblesJugadores, JugadoresPartida);
                            if (jugadorOK == false){
                                continua = true;
                                break;
                            }else{
                                if (numJugadores > 1){
                                    System.out.println("Selecciona el siguiente Jugador / Ordenador");
                                }else if (numJugadores ==2 ){
                                    System.out.println("Selecciona al ultimo jugador");
                                }
                            }
                            continua = false;
                            break;
                        case 3:
                            GestionJugadores gestion3 = new GestionJugadores ();
                            nombreJugador = gestion3.nuevoCPU();
                            jugadorOK = comprobarJugadores(nombreJugador, PosiblesJugadores, JugadoresPartida);
                            if (jugadorOK == false){
                                continua = true;
                                break;
                            }else{
                                if (numJugadores > 1){
                                    System.out.println("Selecciona el siguiente Jugador / Ordenador");
                                }else if (numJugadores ==2 ){
                                    System.out.println("Selecciona al ultimo jugador");
                                }
                            }
                            continua = false;
                            break;
                        default:
                            System.out.println("Valor no válido, por favor introduzca una opción válida (entre 1 y 3)");
                            continua = true;
                    }
                    
                    
                }catch (InputMismatchException e ){
                    System.out.println("Valor no valido, por favor introduzca una opción valida (entre 1 y 3)");
                    continua = true;
                } catch (IOException ex){
                    System.out.println("Error: "+ ex);
                    continua = true;
                }/*catch (MiExcepcion ex){
                System.out.println(ex.getMessage());
                continua = true;
                    
                }*/
                }while (continua == true);
                
                numJugadores--;
                
            }
        }
        System.out.println("A jugaaaaaaaaaar");
        return JugadoresPartida;
    }
    
    public boolean comprobarJugadores (String nombre, ArrayList PosiblesJugadores, ArrayList JugadoresPartida){
        boolean continua = true;
        if (PosiblesJugadores.contains(nombre)){
            continua = false;
            System.out.println("Este jugador ya ha sido elejido para jugar, prueba con otro");
        }else{
            PosiblesJugadores.add (nombre);
            Jugadores nuevo = new Jugadores (nombre);
            JugadoresPartida.add(nuevo);
            System.out.println( nombre + " listo para jugar");
            continua = true;
        }
        
        return continua;
    }
    
    public ArrayList jugadoresDesordenados (ArrayList JugadoresPartida){
        
        
        
        return JugadoresPartida;
    }
    
    public ArrayList jugandoPartida (ArrayList <Jugadores> JugadoresPartida, int rondas) throws ScriptException, IOException{
        
        int nRonda = 1;
        int partida = 1;
        String linea;
        String expresion="^Cpu\\d*$";
        String posibleRespuesta;
        String resultadoPartida = "";
        boolean respuestaCorrecta = false;
        ArrayList<String> escribeTodo = new ArrayList<>();
        
        File historico = new File ("src/eljuego/Historico.txt");
        
        for (Jugadores i : JugadoresPartida){//se resetea los puntos iniciales para la partida nueva de todos los jugadores
            i.setPuntosPartida(0);
        }
        
        while(rondas > 0){
            System.out.println("\nRonda: " + nRonda);
            for (Jugadores i : JugadoresPartida){
                System.out.println("Turno de: " + i.getNombre());
                Preguntas nuevaPregunta = new Preguntas();
                
                if (i.getNombre().matches(expresion)){
                    System.out.println("oh va a jugar la " + i.getNombre());
                    respuestaCorrecta = nuevaPregunta.preguntasAleatoriasCPU(i.getNombre());
                    if (respuestaCorrecta == true){                        
                        System.out.println(i.getNombre() + " Ha acertado la pregunta");
                        i.setPuntosPartida(i.getPuntosPartida()+1);
                        i.setPuntosTotal(i.getPuntosTotal()+1);
                        
                    }else{
                        System.out.println(i.getNombre() + " ha fallado la pregunta");
                    }
                    
                }else{
                    respuestaCorrecta = nuevaPregunta.preguntasAleatorias();
                    if (respuestaCorrecta == true){
                        
                        System.out.println("Muy bien! "+ i.getNombre() + " ha acertado la pregunta");
                        i.setPuntosPartida(i.getPuntosPartida()+1);
                        i.setPuntosTotal(i.getPuntosTotal()+1);
                        
                    }else{
                        System.out.println(i.getNombre() + " ha fallado la pregunta");
                    }
                }
            }
            nRonda++;
            rondas--;
        }
        for (Jugadores i : JugadoresPartida){
            resultadoPartida = resultadoPartida + i.getNombre() + " " + i.getPuntosPartida() + " " ;
        } 
        
        System.out.println("FIN DE LA PARTIDA");
        System.out.println("Este es el resultado de la partida\n" + resultadoPartida);
        
        File lectura1 = new File ("src/eljuego/Historico.txt");
        Scanner archivoLeo = new Scanner(lectura1);
        while (archivoLeo.hasNext()){
            linea = archivoLeo.nextLine();
            escribeTodo.add(linea);
            
        }
        
        FileWriter escribo = new FileWriter (historico);
               
        escribo.write (""  +/* "Partida Nº " + partida + ": " +*/ resultadoPartida + "\n");
        
        for (String i : escribeTodo){// Se vuelve a escribir el archivo Jugadores.txt con lo que contenia enteriormente
                escribo.write ("" + i + "\n");
            }
        
        escribo.close();
        
        return JugadoresPartida;
    }
}
        
 
   
        
        
 
    
    

