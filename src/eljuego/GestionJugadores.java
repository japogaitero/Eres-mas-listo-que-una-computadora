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
    private ArrayList<Jugadores> jugadores = new ArrayList<>();
    private ArrayList<Jugadores> jugadoresPartida = new ArrayList<>();
    
    
    public void menuJugadores() throws IOException{
        ArrayList<String> nombres = new ArrayList<>();
        
        int opcion;
        boolean continua;
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
                        nuevoJugador();
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
        
    }
    
    public void verJugadores () throws IOException{
        
        String nombre;
        ArrayList<String> nombres = new ArrayList<>();
        
        try{
            File lectura1 = new File ("src/eljuego/Jugadores.txt");
            Scanner archivoLeo = new Scanner(lectura1);
            
            while (archivoLeo.hasNext()){
                String[] parts = archivoLeo.nextLine().split(" ");
                nombre = parts[0].toLowerCase();
                nombre =  nombre.toUpperCase().charAt(0) + nombre.substring(1, nombre.length()).toLowerCase();
                nombres.add(nombre);
            }
            if(nombres.isEmpty()){
                System.out.println("Por ahora no hay jugadores. Prueba a introducir uno");
            }else{
                System.out.println("Estos son los jugadores actuales");
                for (String i : nombres){
                    System.out.println(i);
                }
            }
            System.out.println("");
            archivoLeo.close();
            
        }catch (IOException e ){
            System.out.println("Error: "+ e);
        }
    }
    
    public String nuevoJugador () throws IOException{
        
        Scanner teclado = new Scanner (System.in);
        String nombre = "";
        String nombreArchivo;
        String linea;
        System.out.println("Dime el nombre del nuevo jugador ");
        ArrayList<String> arrayNombres = new ArrayList<>();
        ArrayList<String> escribeTodo = new ArrayList<>();
        boolean newUsuario = true;
        try{
            
            /* Se llena dos arrayList, uno con los nombres que comparará con el posible nuevo nombre
            y otro con todos los datos para volver a escribir el archivo despues*/
            File lectura1 = new File ("src/eljuego/Jugadores.txt");
            Scanner archivoLeo = new Scanner(lectura1);
            
            while (archivoLeo.hasNext()){
                linea = archivoLeo.nextLine();
                String[] parts =linea.split(" ");
                escribeTodo.add(linea);
                nombreArchivo = parts[0].toLowerCase();
                nombreArchivo =  nombreArchivo.toUpperCase().charAt(0) + nombreArchivo.substring(1, nombreArchivo.length()).toLowerCase();
                arrayNombres.add(nombreArchivo);
            }
            do{                
                newUsuario = true;
                nombre = teclado.next().toLowerCase();
                nombre =  nombre.toUpperCase().charAt(0) + nombre.substring(1, nombre.length()).toLowerCase();
                
                for (String i : arrayNombres){ // Se comprueba que el nuevo nombre no esta ya en el arraList
                    if (nombre.equals(i)){
                        System.out.println("Este nombre de usuario ya existe");
                        System.out.println("Prueba con otro nombre");
                        //teclado.next();
                        newUsuario = false;
                        break;
                    }  
                }
                }while (newUsuario == false);
                FileWriter escribo = new FileWriter (lectura1);
                
                if(newUsuario || arrayNombres.isEmpty()){// Solo se añade nuevo usuario si el introducido no existia previamente o si es el primer Usuario
                        escribo.write ("" + nombre + "\n");
                        Jugadores nuevo = new Jugadores (nombre);
                        this.jugadores.add(nuevo);
                        System.out.println("Nuevo usuario creado");
                        newUsuario = true;
                    }
                for (String i : escribeTodo){// Se vuelve a escribir el archivo Jugadores.txt con lo que contenia enteriormente
                    escribo.write ("" + i + "\n");
                }
            
            escribo.close();
        }catch (IOException e ){
            System.out.println("Error: "+ e);
        }
        return nombre;
    }
    
    public void eliminarJugador () throws IOException{
        
        ArrayList<String> nombres = new ArrayList<>();
        
        try{
            File lectura1 = new File ("src/eljuego/Jugadores.txt");
            Scanner archivoLeo = new Scanner(lectura1);
            
            while (archivoLeo.hasNext()){
                nombres.add(archivoLeo.next().toLowerCase());
            }
            if(nombres.isEmpty()){
                System.out.println("Por ahora no hay jugadores. Prueba a introducir uno");
            }else{
                System.out.println("Estos son los jugadores actuales");
                for (String i : nombres){
                    System.out.println(i);
                }
            }
            System.out.println("");
            archivoLeo.close();
            
        }catch (IOException e ){
            System.out.println("Error: "+ e);
        }
    }
    
    public void comprobarJugadorHistorial (String usuario) throws FileNotFoundException{
        String nombre;
        boolean jugadorExiste = false;
        ArrayList<String> nombres = new ArrayList<>();
        this.jugadores = new ArrayList<>();
        this.jugadoresPartida = new ArrayList<>();
        
        try{
            File lectura1 = new File ("src/eljuego/Jugadores.txt");
            Scanner archivoLeo = new Scanner(lectura1);
            usuario = usuario.toLowerCase();
            usuario =  usuario.toUpperCase().charAt(0) + usuario.substring(1, usuario.length()).toLowerCase();
            
            while (archivoLeo.hasNext()){
                String[] parts = archivoLeo.nextLine().split(" ");
                nombre = parts[0].toLowerCase();
                nombre =  nombre.toUpperCase().charAt(0) + nombre.substring(1, nombre.length()).toLowerCase();
                nombres.add(nombre);
            }
            archivoLeo.close();
            if(nombres.isEmpty()){
                System.out.println("Este nombre de usuario no existe ----111111111----> Usuario " + usuario + " creado y listo para jugar");
                Jugadores nuevo = new Jugadores (usuario);
                this.jugadores.add(nuevo);
                this.jugadoresPartida.add(nuevo);
                jugadorExiste = true;
            }else{
                
                for (String i : nombres){ // Se comprueba si el  nombre  esta ya en el arraList
                    if (usuario.equals(i)){
                        System.out.println( usuario + " listo para jugar!!");
                        Jugadores nuevo = new Jugadores (usuario);
                        this.jugadores.add(nuevo);
                        this.jugadoresPartida.add(nuevo);
                        jugadorExiste = true;
                        break;
                    }
                }
                if (jugadorExiste == false) {
                    System.out.println("Este nombre de usuario no existe --------> Usuario " + usuario + " creado y listo para jugar");
                    Jugadores nuevo = new Jugadores (usuario);
                    this.jugadores.add(nuevo);
                    this.jugadoresPartida.add(nuevo);
                }
            }
            /*for ( Jugadores i : jugadoresPartida){
            if (jugadoresPartida.contains(i)){
            System.out.println("me cago en la putaaaa");
            }
            
            }*/
            
            
            System.out.println("");
            
            
        }catch (IOException e ){
            System.out.println("Error: "+ e);
        }
        
    }
    public void comprobarJugadorPartida (String usuario) {
        Partida nueva = new Partida();
        
        for ( Jugadores i : jugadoresPartida){
            if (jugadoresPartida.contains(i)){
                System.out.println("me cago en la putaaaa");
            }
            
        }
    }
}
