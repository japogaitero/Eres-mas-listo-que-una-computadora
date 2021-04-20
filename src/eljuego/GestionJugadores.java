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
import java.util.InputMismatchException;
import java.util.Scanner;


/**
 *
 * @author Victor Alvarez <japogaitero at gmail.com>
 */
public class GestionJugadores extends Partida {
    
    
    
    public static void menuJugadores() throws IOException, MiExcepcion{
        ArrayList<String> nombres = new ArrayList<>();
        String nombre;
        int opcion;
        boolean continua;
        Jugadores jugador;
        Scanner teclado = new Scanner (System.in);
        
        do{
            
            System.out.println("Seleccione un número del siguiente menu:\n");
            System.out.println( "1. Ver jugadores \n2. Añadir jugador \n3. Eliminar jugador \n4. Volver " );
            
            try{
                continua = false;
                teclado = new Scanner (System.in);
                opcion = teclado.nextInt();
                switch(opcion) {
                    case 1:
                        verJugadores();
                        
                        continua = true;
                        break;
                    case 2:
                        jugador = nuevoJugador();
                        System.out.println("Se ha creado el jugado " + jugador.getNombre());
                        continua = true;
                        break;
                    case 3:
                        eliminarJugador ();
                        continua = true;
                        break;
                    case 4:
                        teclado.nextLine();
                        continua = false;
                        break;
                        
                    default:
                        System.out.println("Valor no valido, por favor introduzca una opción valida (entre 1 y 4)");
                        continua = true;
                        
                }
                
            }catch (InputMismatchException e ){
                System.out.println("Valor no valido, por favor introduzca una opción valida (entre 1 y 4)");
                continua = true;
            } catch (IOException ex){
                System.out.println("Error: "+ ex);
                continua = true;
            }
            
            
        }while (continua);
        
    }
    
    public static void verJugadores () throws IOException{
        
        String nombre;
        String[] parts;
        ArrayList<String> nombres = new ArrayList<>();
        
        try{
            nombres = ElJuego.lineaDatosJugador();
            
            if(nombres.isEmpty()){
                System.out.println("Por ahora no hay jugadores. Prueba a introducir uno");
            }else{
                System.out.println("Estos son los jugadores actuales");
                for (String i : nombres){
                    parts =i.split(" ");
                    nombre = parts[0];
                    System.out.println(nombre);
                }
            }
            System.out.println("");
            
            
        }catch (ArrayIndexOutOfBoundsException e ){
            System.out.println("Error: "+ e);
        }
    }
    
    public static Jugadores nuevoJugador () throws IOException, MiExcepcion{
        String nombre;
        String expresion="^Cpu\\d*$";
        Jugadores nuevo = new Jugadores();
        boolean newUsuario;
        ArrayList<String> archivoJugadores = new ArrayList<>();
        ArrayList<Jugadores> arrayJugadoresExistentes = new ArrayList<>();
        Scanner teclado = new Scanner (System.in);
        System.out.println("Que usuario deseas crear? ");
        
        do{
            try{
                newUsuario = true;
                
                archivoJugadores = ElJuego.lineaDatosJugador();
                arrayJugadoresExistentes = ElJuego.jugadoresEnArchivo(archivoJugadores);
                
                nombre = teclado.nextLine().toLowerCase();
                if( !nombre.isEmpty()){
                    nombre =  nombre.toUpperCase().charAt(0) + nombre.substring(1, nombre.length()).toLowerCase();
                    if (nombre.contains(" ")) {
                        throw new MiExcepcion(222);
                    }else if (nombre.matches(expresion) || nombre.equalsIgnoreCase ("cpu")){
                        throw new MiExcepcion(333);
                    }
                    for (Jugadores i : arrayJugadoresExistentes){ // Se comprueba que el nuevo nombre no esta ya en el arrayList
                        if (i.getNombre().equalsIgnoreCase(nombre)){
                            System.out.println("Este nombre de usuario ya existe. Prueba con otro nombre o pulse Enter para volver ");
                            newUsuario = false;
                            break;
                        }
                    }
                }
                if (newUsuario == true || nombre.isEmpty()){
                    nuevo = new Jugadores (nombre);
                    ElJuego.escriboFichero(nombre, archivoJugadores);
                }
                
            }catch (MiExcepcion ex){
                System.out.println(ex.getMessage());
                newUsuario = false;
            }catch (IOException d ){
                System.out.println("Error: "+ d);
                System.out.println("Que usuario deseas crear? ");
                newUsuario = false;
            }
        }while (newUsuario == false);
        
        return nuevo;
    }
    
    private static void eliminarJugador () throws IOException{
        Scanner teclado = new Scanner (System.in);
        String nombre;
        ArrayList<String> archivoJugadores = new ArrayList<>();
        ArrayList<Jugadores> arrayJugadoresExistentes = new ArrayList<>();
        boolean usuarioExiste;
        
        System.out.println("Que usuario deseas eliminar? ");
        
        do{
            try{
                usuarioExiste = false;
                
                archivoJugadores = ElJuego.lineaDatosJugador();
                arrayJugadoresExistentes = ElJuego.jugadoresEnArchivo(archivoJugadores);
                
                nombre = teclado.nextLine().toLowerCase();
                if( !nombre.isEmpty()){
                    nombre =  nombre.toUpperCase().charAt(0) + nombre.substring(1, nombre.length()).toLowerCase();
                    
                    for (int i = 0 ; i < arrayJugadoresExistentes.size() ; i++){ // Se comprueba que el nuevo nombre no esta ya en el arrayList
                        String nom = (arrayJugadoresExistentes.get(i)).getNombre();
                        if (nom.equalsIgnoreCase(nombre)){
                            System.out.println("Se ha borrado el usuario " + nombre);
                            arrayJugadoresExistentes.remove(i);
                            archivoJugadores.remove(i);
                            usuarioExiste = true;
                            break;
                        }
                    }
                }else{
                    System.out.println("El jugador que quieres eliminar no existe.Prueba con otro nombre o pulse Enter para volver");
                    
                }
                if (usuarioExiste == true || nombre.isEmpty()){
                    ElJuego.escriboFichero("", archivoJugadores);
                    usuarioExiste = true;
                }
                
                
                
            }catch (IOException d ){
                System.out.println("Error: "+ d);
                System.out.println("Que usuario deseas eliminar? ");
                usuarioExiste = false;
            }
            
        }while (usuarioExiste == false);
    }
    
    public static Jugadores jugadorExistente () throws FileNotFoundException, MiExcepcion{
        Scanner teclado = new Scanner (System.in);
        String nombre = "";
        String opcion;
        String expresion="^Cpu\\d*$";
        boolean newUsuario;
        boolean jugadorExiste;
        Jugadores jugadorExistente = new Jugadores ();
        ArrayList<String> archivoJugadores = new ArrayList<>();
        ArrayList<Jugadores> arrayJugadoresExistentes = new ArrayList<>();
        
        System.out.println("Con que usuario quieres jugar?");
        
        do{
            try{
                jugadorExiste = false;
                newUsuario = false;
                
                archivoJugadores = ElJuego.lineaDatosJugador();
                arrayJugadoresExistentes = ElJuego.jugadoresEnArchivo(archivoJugadores);
                
                nombre = teclado.nextLine().toLowerCase();
                if( !nombre.isEmpty()){
                    nombre =  nombre.toUpperCase().charAt(0) + nombre.substring(1, nombre.length()).toLowerCase();
                    
                    if (nombre.contains(" ")) {
                        throw new MiExcepcion(222);
                    }else if (nombre.matches(expresion) || nombre.equalsIgnoreCase ("cpu")){//
                        throw new MiExcepcion(333);
                    }
                    for (Jugadores i : arrayJugadoresExistentes){ // Se comprueba que el nuevo nombre no esta ya en el arrayList
                        if (i.getNombre().equalsIgnoreCase(nombre) ){
                            jugadorExiste = true;
                            break;
                        }
                    }
                    
                    if (jugadorExiste == true){
                        jugadorExistente = new Jugadores (nombre);
                        
                    }else if (jugadorExiste == false || arrayJugadoresExistentes.isEmpty()) {
                        System.out.println("Este nombre de usuario no existe en el archivo. Desea crearlo? Escriba SI/NO");
                        do {
                            opcion = teclado.next();
                            if ("si".equalsIgnoreCase(opcion)){
                                newUsuario = true;
                            }else if ("no".equalsIgnoreCase(opcion)){
                                jugadorExiste = true;
                                System.out.println("Eliga nueva opcion");
                                
                            }else{
                                System.out.println("Escriba simplemente \"si\" o \"no\"");
                            }
                        }while (!"no".equalsIgnoreCase(opcion) && !"si".equalsIgnoreCase(opcion));
                    }
                    
                    if (newUsuario == true){
                        jugadorExistente = new Jugadores (nombre);
                        ElJuego.escriboFichero(nombre, archivoJugadores);
                        jugadorExiste = true;
                    }
                }else{
                    System.out.println("Con que usuario quieres jugar?");
                }
            }catch (IOException e ){
                System.out.println("Error: "+ e);
                System.out.println("Con que usuario quieres jugar?");
                jugadorExiste = false;
                
            }catch (MiExcepcion ex){
                System.out.println(ex.getMessage());
                jugadorExiste = false;
            }
        }while (jugadorExiste == false);
        return jugadorExistente;
    }
    
    public static Jugadores nuevoCPU () throws IOException{
        String nombre = "Cpu";
        int numCpu;
        ArrayList<String> arrayCpu = new ArrayList<>();
        ArrayList<String> archivoJugadores = new ArrayList<>();
        ArrayList<Jugadores> arrayJugadoresExistentes = new ArrayList<>();
        Jugadores nuevo = new Jugadores ();
        
        try{
            archivoJugadores = ElJuego.lineaDatosJugador();
            arrayJugadoresExistentes = ElJuego.jugadoresEnArchivo(archivoJugadores);
            
            for (Jugadores i : arrayJugadoresExistentes){ // Se comprueba si hay algun Cpu* en el arraList
                //if (i.matches(expresion)){
                if (!i.isHumano()){
                    //System.out.println("hay un ordenadooooor");
                    arrayCpu.add("");
                }
            }
            numCpu = arrayCpu.size() + 1;
            nombre = nombre + numCpu;
            nuevo = new Jugadores (nombre);
            nuevo.setHumano(false);
            
            ElJuego.escriboFichero(nombre, archivoJugadores);
            
        }catch (FileNotFoundException e ){
            System.out.println("Error: "+ e);
        }
        return nuevo;
    }
}
