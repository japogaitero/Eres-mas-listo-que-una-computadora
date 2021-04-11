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
    private ArrayList Jugadores;
    
    public Partida (){
        
    }
    
    public Partida(int nJugadores, int nRondas, ArrayList Jugadores) {
        this.nJugadores = nJugadores;
        this.nRondas = nRondas;
        this.Jugadores = new ArrayList<>();
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
    
    
    
    public void menu()throws Exception, IOException{
        //ArrayList<String> arrayNombres = new ArrayList<String>();
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
                    System.out.println("Has elegido " + nuevaPartida.getnRondas());
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
        
        PedirJugadores(nuevaPartida.getnJugadores());
        //opcion = ElJuego.aleatorio(2,3);
        /*do{
        
        System.out.println("Seleccione un número del siguiente menu:\n");
        System.out.println( "1. Ver jugadores \n2. Añadir jugador \n3. Eliminar jugador \n4. Volver " );
        
        try{
        continua = false;
        teclado = new Scanner (System.in);
        opcion = teclado.nextInt();
        switch(opcion) {
        case 1:
        //verJugadores();
        continua = true;
        break;
        case 2:
        //nuevoJugador();
        continua = true;
        break;
        case 3:
        // code block
        break;
        case 4:
        continua = false;
        break;
        default:
        System.out.println("Valor no valido, por favor introduzca una opción valida (entre 1 y 4)");
        continua = true;
        
        }
        
        }catch (InputMismatchException e ){
        System.out.println("Valor no valido, por favor introduzca una opción valida (entre 1 y 5)");
        continua = true;
        } catch (IOException ex){
        System.out.println("Error: "+ ex);
        continua = true;
        }
        
        
        }while (continua);
        */
    }
    
    public void PedirJugadores (int numJugadores)throws IOException{
        
        ArrayList <Jugadores> partida = new ArrayList ();
        int opcion; 
        String nombre = "";        
        boolean continua;
        Scanner teclado = new Scanner (System.in);
        
        System.out.println("Selecciona a los jugadores:");
        
        
        while (numJugadores > 0){
            do{
                System.out.println("\n1. Jugador existente\n2. Jugador nuevo\n3. Juega contra el Ordenador (CPU)");
                
                try{
                    continua = false;
                    teclado = new Scanner (System.in);
                    opcion = teclado.nextInt();
                    switch(opcion) {
                        case 1:
                            GestionJugadores gestion1 = new GestionJugadores ();
                            System.out.println("Que usuario vas a utilizar?");
                            nombre = teclado.next();
                            //gestion1.comprobarJugadorHistorial(nombre);
                            if (numJugadores > 1){
                                System.out.println("Selecciona el siguiente Jugador / Ordenador");
                                continua = false;
                            }else if (numJugadores ==1 ){
                                System.out.println("Selecciona al ultimo jugador");
                                continua = false;                                
                            }
                            break;
                        case 2:
                            GestionJugadores gestion2 = new GestionJugadores ();
                            gestion2.nuevoJugador();
                            //gestion2.comprobarJugadorHistorial(nombre);
                            if (numJugadores > 1){
                                System.out.println("Selecciona el siguiente Jugador / Ordenador");
                                continua = false;
                            }else if (numJugadores ==1 ){
                                System.out.println("Selecciona al ultimo jugador");
                                continua = false;
                                
                            }else if (numJugadores == 0 ){
                                System.out.println("A jugaaaaaaaaaar");
                                continua = false;
                            }
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Valor no valido, por favor introduzca una opción valida (entre 1 y 4)");
                            continua = true;
                    }
                    
                    
                }catch (InputMismatchException e ){
                    System.out.println("Valor no valido, por favor introduzca una opción valida (entre 1 y 5)");
                    continua = true;
                } catch (IOException ex){
                    System.out.println("Error: "+ ex);
                    continua = true;
                }
                }while (continua == true);
                
                numJugadores--;
                
            }
    }
}
        
        
        
        
        
    
    

