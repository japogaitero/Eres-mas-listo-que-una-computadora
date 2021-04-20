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
        int opcion;
        boolean continua;
        boolean jugadorNuevo = false;
        ArrayList <Jugadores> JugadoresPartida = new ArrayList ();
        Jugadores jugador;
        Scanner teclado = new Scanner (System.in);
        
        if (numJugadores == 1){
            do{
                System.out.println("\nSeleciona:\n1. Jugador existente\n2. Jugador nuevo\n");                
                try{
                    continua = true;
                    opcion = teclado.nextInt();
                    switch(opcion) {
                        case 1:
                            jugador = GestionJugadores.jugadorExistente();
                            if (jugador.isExists() == false){
                                break;
                            }
                            JugadoresPartida.add(jugador);
                            continua = false;                            
                            break;                            
                        case 2:
                            jugador = GestionJugadores.nuevoJugador();
                            if (jugador.isExists() == false){
                                break;
                            }
                            JugadoresPartida.add(jugador);
                            continua = false;
                            break;                            
                        default:
                            System.out.println("Valor no valido, por favor seleccione una opción valida: 1 o 2 ");
                            continua = true;
                            teclado.nextLine();
                    }                    
                }catch (InputMismatchException e ){
                    System.out.println("Valor no válido, por favor introduzca una opción válida");
                    continua = true;
                    teclado.nextLine();
                } catch (IOException ex){
                    System.out.println("Error: "+ ex);
                    continua = true;
                    teclado.nextLine();
                }
                }while (continua == true);            
        }else {
        while (numJugadores > 0){           
            do{                
                System.out.println("\n1. Jugador existente\n2. Jugador nuevo\n3. Juega contra el ordenador (CPU)");                
                try{
                    continua = true;
                    jugadorNuevo = true;
                    opcion = teclado.nextInt();
                    switch(opcion) {
                        case 1:    
                            jugador = GestionJugadores.jugadorExistente();
                            if (jugador.isExists() == false){
                                break;
                            }    
                            for (Jugadores i : JugadoresPartida){
                                if(jugador.getNombre().equals(i.getNombre())){
                                    System.out.println("Este jugador ya ha sido seleccionado para jugar. Elije otro:");
                                    jugadorNuevo = false; 
                                }
                            }                            
                            if (jugadorNuevo){
                                JugadoresPartida.add(jugador);
                                continua = false;
                            }
                            break;
                        case 2:
                            jugador = GestionJugadores.nuevoJugador();
                            if (jugador.isExists() == false){
                                break;
                            }
                            if (JugadoresPartida.contains(jugador)){
                                System.out.println("esteeeeeeeeeeej ugador ya estaaaaaaaa");
                            }else{
                                JugadoresPartida.add(jugador);
                                continua = false;
                            }
                            break;
                        case 3:
                            jugador = GestionJugadores.nuevoCPU();
                                JugadoresPartida.add(jugador);
                                continua = false;
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
        
        return JugadoresPartida;
    }
    
    public static ArrayList <Jugadores> jugandoPartida (ArrayList <Jugadores> JugadoresPartida, int rondas) throws ScriptException, IOException{
        int nRonda = 1;
        int partida = 1;
        String resultadoPartida = "";
        boolean respuestaCorrecta;
        File historico = new File ("src/eljuego/Historico.txt");
        
        for (Jugadores i : JugadoresPartida){//se resetea los puntos iniciales para la partida nueva de todos los jugadores
            i.setPuntosPartida(0);
        }
        while(rondas > 0){            
            System.out.println("\nRonda: " + nRonda);
            
            if( JugadoresPartida.size() == 1){
                Preguntas nuevaPregunta = new Preguntas();
                Jugadores unJugador = JugadoresPartida.get(0);
                    respuestaCorrecta = nuevaPregunta.preguntasAleatorias();
                    if (respuestaCorrecta == true){                        
                        System.out.println("Muy bien! Has acertado la pregunta");
                        unJugador.setPuntosPartida(unJugador.getPuntosPartida()+1);
                        unJugador.setPuntosTotal(unJugador.getPuntosTotal()+1);
                        
                    }else{
                        System.out.println("Ohh no! Has fallado la pregunta");
                    }
            
            }else{
            
            for (Jugadores i : JugadoresPartida){
                System.out.println("\nTurno de: " + i.getNombre());
                Preguntas nuevaPregunta = new Preguntas();
                
                if (i.isHumano() == false){
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
            }
            if (rondas >1 ){
                ElJuego.escribeParaContinuar("Presiona enter para continuar con la siguiente ronda");
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
        System.out.println("");
        
        for (Jugadores i : JugadoresPartida){
            i.setPuntosPartida(0);
        }
        
        
        return JugadoresPartida;
    }
    
    public static Partida ordenJugadores(Partida nuevaPartida) throws IOException, MiExcepcion{
        int num =1;        
        ArrayList <Jugadores> jugadoresPartida = new ArrayList <>();
        
        jugadoresPartida = pedirJugadores(nuevaPartida.getnJugadores());        
        nuevaPartida.setJugadoresPartida(jugadoresPartida);
        
        if(jugadoresPartida.size() == 1){
            System.out.println(jugadoresPartida.get(0).getNombre() + "Vas a jugar tu solo");
        }else{
            
        System.out.println("\nLos jugadores para esta partida son:");
        for (Jugadores i : nuevaPartida.getJugadoresPartida()){
            System.out.print(i.getNombre());            
        }
        System.out.println("Vamos a decidir aleatoriamente el orden de los jugadores...");
        
        Collections.shuffle(nuevaPartida.getJugadoresPartida());
        System.out.println("\nEl orden de los jugadores para responder es:");
        for (Jugadores i : nuevaPartida.getJugadoresPartida()){
            System.out.println(num + "º - " + i.getNombre());
            num ++;
        }
        }
        return nuevaPartida;
    }

}
        
 
   
        
        
 
    
    

