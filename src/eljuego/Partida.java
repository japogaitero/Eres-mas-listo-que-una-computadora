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
    //private ArrayList <String> PosiblesJugadores = new ArrayList ();

   
    
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

    
    public static ArrayList <Jugadores> pedirJugadores (int numJugadores)throws IOException, MiExcepcion{
        
        ArrayList <Jugadores> JugadoresPartida = new ArrayList ();
        //ArrayList <Jugadores> PosiblesJugadores = new ArrayList ();
        int opcion; 
        //String nombreJugador = "";
        Jugadores jugadorNuevaPartida;
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
                            JugadoresPartida.add(GestionJugadores.jugadorExistente());
                                continua = false;
                                break;
                           
                        case 2:
                            JugadoresPartida.add(GestionJugadores.nuevoJugador());
                            continua = false;                            
                            break;
                        
                        default:
                            System.out.println("Valor no valido, por favor seleccione :");
                            continua = true;
                            teclado.nextLine();
                    }                    
                    
                }catch (InputMismatchException e ){
                    System.out.println("Valor no válido, por favor introduzca una opción válida (entre 1 y ñññññññññ)");
                    continua = true;
                    teclado.nextLine();
                } catch (IOException ex){
                    System.out.println("Error: "+ ex);
                    continua = true;
                    teclado.nextLine();
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
                            jugadorNuevaPartida = GestionJugadores.jugadorExistente();
                            if (JugadoresPartida.contains(jugadorNuevaPartida)){
                                continua = true;
                                System.out.println("esteeeeeeeeeeej ugador ya estaaaaaaaa");
                            }else{
                                JugadoresPartida.add(jugadorNuevaPartida);
                                continua = false;
                            }
                                break;
                            
                            
                        case 2:
                            
                            jugadorNuevaPartida = GestionJugadores.nuevoJugador();
                            if (JugadoresPartida.contains(jugadorNuevaPartida)){
                                continua = true;
                                System.out.println("esteeeeeeeeeeej ugador ya estaaaaaaaa");
                            }else{
                                JugadoresPartida.add(jugadorNuevaPartida);
                                continua = false;
                            }
                                break;
                            
                           
                        case 3:
                            
                            jugadorNuevaPartida = GestionJugadores.nuevoCPU();
                            if (JugadoresPartida.contains(jugadorNuevaPartida)){
                                continua = true;
                                System.out.println("esteeeeeeeeeeej ugador ya estaaaaaaaa");
                            }else{
                                JugadoresPartida.add(jugadorNuevaPartida);
                                continua = false;
                            }
                                break;
                                
                            
                        default:
                            System.out.println("Valor no válido, por favor introduzca una opción válida (entre 1 y 3)");
                            continua = true;
                            teclado.nextLine();
                    }
                    
                    
                }catch (InputMismatchException e ){
                    System.out.println("Valor no valido, por favor introduzca una opción valida (entre 1 y 3)");
                    continua = true;
                    teclado.nextLine();
                } catch (IOException ex){
                    System.out.println("Error: "+ ex);
                    continua = true;
                    teclado.nextLine();
                }
                }while (continua == true);
                
                numJugadores--;
                
            }
        }
        System.out.println("A jugaaaaaaaaaar");
        return JugadoresPartida;
    }
    
    public static boolean comprobarJugadores (String nombre, ArrayList PosiblesJugadores, ArrayList JugadoresPartida){
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
    
    
    
    public static ArrayList <Jugadores> jugandoPartida (ArrayList <Jugadores> JugadoresPartida, int rondas) throws ScriptException, IOException{
        
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
                        System.out.println(i.getNombre() + " ha acertado la pregunta");
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
        
        ElJuego.actualizoHistorico(resultadoPartida);
        
        System.out.println("FIN DE LA PARTIDA");
        System.out.println("Este es el resultado de la partida\n" + resultadoPartida);
        
        ArrayList<String> historicoNuevo = new ArrayList<>();
        ArrayList<Jugadores> arrayJugadoresExistentes = new ArrayList<>();
        
        for (Jugadores i : JugadoresPartida){
            i.setPuntosPartida(0);
        }
        
        
        return JugadoresPartida;
    }
}
        
 
   
        
        
 
    
    

