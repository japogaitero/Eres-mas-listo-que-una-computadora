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
    
    
    
    public   void menuJugadores() throws IOException, MiExcepcion{
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
    
    public void verJugadores () throws IOException{
        
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
        
        Scanner teclado = new Scanner (System.in);
        String nombre = "";
        String expresion="^Cpu\\d*$";
        System.out.println("Que usuario deseas crear? ");
        ArrayList<String> archivoJugadores = new ArrayList<>();
        ArrayList<Jugadores> arrayJugadoresExistentes = new ArrayList<>();
        Jugadores nuevo = new Jugadores(); 
        
        boolean newUsuario = true;
        
        do{
            try{
                newUsuario = true;
                
                archivoJugadores = ElJuego.lineaDatosJugador();
                arrayJugadoresExistentes = ElJuego.jugadoresEnArchivo(archivoJugadores);
                
                nombre = teclado.nextLine().toLowerCase();
                nombre =  nombre.toUpperCase().charAt(0) + nombre.substring(1, nombre.length()).toLowerCase();
                if (nombre.contains(" ")) {
                    throw new MiExcepcion(222);
                }else if (nombre.matches(expresion) || nombre.equalsIgnoreCase ("cpu")){
                    throw new MiExcepcion(333);
                }
                
                
                for (Jugadores i : arrayJugadoresExistentes){ // Se comprueba que el nuevo nombre no esta ya en el arrayList
                    if (i.getNombre().equalsIgnoreCase(nombre)){
                        System.out.println("Este nombre de usuario ya existe. Prueba con otro nombre");
                        newUsuario = false;
                        break;
                    }
                }
                if (newUsuario == true){
                     nuevo = new Jugadores (nombre);
                    ElJuego.escriboFichero(nombre, archivoJugadores);
                }
                //ElJuego.escriboFichero("", archivoJugadores);
                
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
    
    
    private void eliminarJugador () throws IOException{
        
        Scanner teclado = new Scanner (System.in);
        String nombre = "";
        String nombreArchivo;
        String linea;
        String expresion="^Cpu\\d*$";
        System.out.println("Que usuario deseas eliminar? ");
        //ArrayList<String> arrayNombres = new ArrayList<>();
        ArrayList<String> archivoJugadores = new ArrayList<>();
        ArrayList<Jugadores> arrayJugadoresExistentes = new ArrayList<>();
        boolean usuarioExiste;
        
        do{
            try{
                
                usuarioExiste = false;
                archivoJugadores = ElJuego.lineaDatosJugador();
                arrayJugadoresExistentes = ElJuego.jugadoresEnArchivo(archivoJugadores);                
                nombre = teclado.nextLine().toLowerCase();
                if (nombre.contains(" ")) {
                    throw new MiExcepcion(222);
                }else if (nombre.matches(expresion)){
                    throw new MiExcepcion(333);
                }
                nombre =  nombre.toUpperCase().charAt(0) + nombre.substring(1, nombre.length()).toLowerCase();
                
                for (int i = 0 ; i < arrayJugadoresExistentes.size() ; i++){ // Se comprueba que el nuevo nombre no esta ya en el arrayList
                    String nom = (arrayJugadoresExistentes.get(i)).getNombre();
                    if (nom.equalsIgnoreCase(nombre)){
                        System.out.println("Se va a borrar el nombre " + nombre);                        
                        arrayJugadoresExistentes.remove(i);
                        archivoJugadores.remove(i);
                        usuarioExiste = true;
                        break;
                    }
                }
                    System.out.println("El jugador que quieres eliminar no existe.Prueba con otro nombre");
                    
                if (usuarioExiste == true){
                    System.out.println("toma mierdaaaaaaaaa");
                    ElJuego.escriboFichero("", archivoJugadores);
                }
                
            }catch (MiExcepcion ex){
                System.out.println(ex.getMessage());
                usuarioExiste = false;
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
        boolean jugadorExiste = false;
        Jugadores jugadorExistente = new Jugadores ();
        ArrayList<String> archivoJugadores = new ArrayList<>();
        ArrayList<Jugadores> arrayJugadoresExistentes = new ArrayList<>();
        
        System.out.println("Con que usuario quieres jugar?");
        
        
        do{
            try{
                jugadorExiste = false;
                
                archivoJugadores = ElJuego.lineaDatosJugador();
                arrayJugadoresExistentes = ElJuego.jugadoresEnArchivo(archivoJugadores);
                
                newUsuario = true;
                nombre = teclado.nextLine().toLowerCase();
                nombre =  nombre.toUpperCase().charAt(0) + nombre.substring(1, nombre.length()).toLowerCase();
                
                if (nombre.contains(" ")) {
                    throw new MiExcepcion(222);
                }else if (nombre.matches("expresion") ){//|| nombre.equalsIgnoreCase ("cpu")
                    throw new MiExcepcion(333);
                }
                
                
                for (Jugadores i : arrayJugadoresExistentes){ // Se comprueba que el nuevo nombre no esta ya en el arrayList
                    if (i.getNombre().equalsIgnoreCase(nombre) ){
                        System.out.println(nombre + " ha sido escogido para jugar");
                        jugadorExiste = true;
                        break;
                    }
                }
                
                if (jugadorExiste == true){
                    jugadorExistente = new Jugadores (nombre);
                    
                }else if (jugadorExiste == false || arrayJugadoresExistentes.isEmpty()) {
                    System.out.println("Este nombre de usuario no existe. Desea Crearlo? Escriba SI/NO");
                    do {
                        opcion = teclado.next();
                        if ("si".equalsIgnoreCase(opcion)){
                            newUsuario = true;
                        }else if ("no".equalsIgnoreCase(opcion)){
                            jugadorExiste = true;
                            nombre = "";
                            System.out.println("Eliga nueva opcion");
                        }else{
                            System.out.println("Escriba simplemente \"si\" o \"no\"");
                        }
                    }while (!"no".equalsIgnoreCase(opcion) && !"si".equalsIgnoreCase(opcion));
                }
                if (newUsuario == true){
                    System.out.println("se ha creado el nuevo");
                    jugadorExistente = new Jugadores (nombre);
                    ElJuego.escriboFichero(nombre, archivoJugadores);
                    jugadorExiste = true;
                }
                ElJuego.escriboFichero("", archivoJugadores);
                
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
        
        ;
        String nombreArchivo;
        String linea;
        int numCpu;
        
        ArrayList<String> arrayNombres = new ArrayList<>();
        ArrayList<String> arrayCpu = new ArrayList<>();
        ArrayList<String> escriboArchivoJugadores = new ArrayList<>();
        
        Scanner teclado = new Scanner (System.in);
        String nombre = "Cpu";
        String expresion="^Cpu\\d*$";
        System.out.println("Que usuario deseas crear? ");
        ArrayList<String> archivoJugadores = new ArrayList<>();
        ArrayList<Jugadores> arrayJugadoresExistentes = new ArrayList<>();
        Jugadores nuevo = new Jugadores ();
        boolean newUsuario = true;
        
        
            try{
                newUsuario = true;
                
                archivoJugadores = ElJuego.lineaDatosJugador();
                arrayJugadoresExistentes = ElJuego.jugadoresEnArchivo(archivoJugadores);
            
            for (Jugadores i : arrayJugadoresExistentes){ // Se comprueba si hay algun Cpu* en el arraList
                //if (i.matches(expresion)){
                if (!i.isHumano()){
                    System.out.println("hay un ordenadooooor");
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
