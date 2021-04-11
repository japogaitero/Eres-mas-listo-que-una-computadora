/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package eljuego;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

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
            System.out.println("Elije cuantos usuarios van a jugar. 1 minimo o 4 como máximo");
            try{
                continua = false;
                teclado = new Scanner (System.in);
                opcion = teclado.nextInt();
                if (opcion <= 0 || opcion > 4 ){
                    throw new MiExcepcion(14);
                }else{                    
                    nuevaPartida.setnJugadores(opcion);
                    System.out.println("Has elegido una partida de " + nuevaPartida.getnJugadores() + " jugadores");
                    continua = false;
                }
                
            }catch (InputMismatchException e ){
                System.out.println("Valor no valido, por favor introduzca un número de jugadores entre 1 y 4");
                continua = true;
            }catch (MiExcepcion ex){
                System.out.println(ex.getMessage());
                continua = true;
                teclado.nextInt();
            }
        }while (continua);
        
        do {
            System.out.println("Elije que tipo de partida quieres jugar\n1. Partida rápida (3 rondas)\n2. Partida corta (5 rondas)\n"
                    + "3. Partida normal (10 rondas)\n4. Partida larga (20 rondas)");
            try{
                continua = false;
                teclado = new Scanner (System.in);
                opcion = teclado.nextInt();
                if (opcion <= 0 || opcion > 4 ){
                    throw new MiExcepcion(14);
                }else{
                    nuevaPartida.setnRondas(opcion);
                    String partidas = "";
                    switch (opcion){
                        case 1:
                            partidas = " rapida de 3 rondas";
                            break;
                        case 2:
                            partidas = " corta de 5 rondas";
                            break;
                        case 3:
                            partidas = " normal de 10 rondas";
                            break;
                        case 4:
                            partidas = " larga de 20 rondas";
                            break;
                    }                    
                    System.out.println("Has elegido una partida " + partidas);
                    continua = false;
                }
            }catch (InputMismatchException e ){
                System.out.println("Valor no valido, por favor introduzca un número de jugadores entre 1 y 4");
                continua = true;
            }catch (MiExcepcion ex){
                System.out.println(ex.getMessage());
                continua = true;
            }
            
        }while (continua);
        
        JugadoresPartida = pedirJugadores(nuevaPartida.getnJugadores());
        System.out.println("\nY los jugadores para esta partida son:");
        for (Jugadores i : JugadoresPartida){
            System.out.println(i.toString());
            
        }
        
    }
    
    public ArrayList pedirJugadores (int numJugadores)throws IOException{
        
        ArrayList <Jugadores> JugadoresPartida = new ArrayList ();
        ArrayList <String> PosiblesJugadores = new ArrayList ();
        int opcion; 
        String nombreJugador = "";
        //String nombreJugadorPartida = "";
        boolean continua, jugadorOK;
        Scanner teclado = new Scanner (System.in);
        
        if (numJugadores == 1){
            do{
                System.out.println("\nSeleciona:\n1. Jugador existente\n2. Jugador nuevo\n3. Juega contra el Ordenador (CPU)");                
                try{
                    continua = false;                    
                    opcion = teclado.nextInt();
                    switch(opcion) {
                        case 1:
                            GestionJugadores gestion1 = new GestionJugadores ();                            
                            nombreJugador = teclado.next();  
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
                        case 3:
                            break;
                        default:
                            System.out.println("Valor no valido, por favor introduzca una opción valida (entre 1 y 3)");
                            continua = true;
                    }                    
                    
                }catch (InputMismatchException e ){
                    System.out.println("Valor no valido, por favor introduzca una opción valida (entre 1 y 3)");
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
                
                System.out.println("\n1. Jugador existente\n2. Jugador nuevo\n3. Juega contra el Ordenador (CPU)");                
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
                            break;
                        default:
                            System.out.println("Valor no valido, por favor introduzca una opción valida (entre 1 y 3)");
                            continua = true;
                    }
                    
                    
                }catch (InputMismatchException e ){
                    System.out.println("Valor no valido, por favor introduzca una opción valida (entre 1 y 3)");
                    continua = true;
                } catch (IOException ex){
                    System.out.println("Error: "+ ex);
                    continua = true;
                }
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
    
}
        
 
   
        
        
 
    
    

