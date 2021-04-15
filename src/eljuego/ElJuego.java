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
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Victor Alvarez <japogaitero at gmail.com>
 */
public class ElJuego {
    
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, Exception {
        int opcion;
        Scanner teclado = new Scanner (System.in);
        boolean continua;
        
        System.out.println("Bienvenido al juego \"Eres más listo que una computadora?\" \n");
        
        do{
            
            System.out.println("Seleccione un número del siguiente menu:\n");
            System.out.println( "1. Jugar partida \n2. Ver ránking \n3. Ver histórico \n4. Menú jugadores \n5. Salir del juego ");
            
            try{
                continua = false;
                teclado = new Scanner (System.in);
                opcion = teclado.nextInt();
                switch(opcion) {
                    case 1:
                        Partida aJugar = new Partida();
                        teclado.nextLine();
                        aJugar.menu();
                        continua = true;
                        break;
                    case 2:
                        // code block
                        break;
                    case 3:
                        // code block
                        break;
                    case 4:
                        GestionJugadores gestion = new GestionJugadores ();
                        teclado.nextLine();
                        gestion.menuJugadores();
                        continua = true;
                        break;
                    case 5:
                        System.out.println("Nos vemos en la próxima partida. Chaoooo!!");
                        continua = false;
                        break;
                    default:
                        System.out.println("Valor no válido, por favor introduzca una opción válida (entre 1 y 5)");
                        continua = true;
                        
                }
                
            }catch (InputMismatchException e ){
                System.out.println("Valor no válido, por favor introduzca una opción válida (entre 1 y 5)");
                
                continua = true;
            }
        }while (continua);
        
        
        
        
    }
    
    
    
    public static int aleatorio (int a, int b){  // devuelve un valor aleatorio entre a y b ambos incluidos
        
        Random r = new Random();
        int aleatorio = r.nextInt(b)+a;
        
        return aleatorio;
    }
    
    public static ArrayList<String> lineaDatosJugador(){
        
        String linea;
        ArrayList<String> lineaDatosJugador = new ArrayList<>();
        
        try{
            File lectura1 = new File ("src/eljuego/Jugadores.txt");
            Scanner archivoLeo = new Scanner(lectura1);
            /* Se llena dos arrayList, uno con los nombres que comparará con el posible nuevo nombre
            y otro con todos los datos para volver a escribir el archivo despues*/
            
            while (archivoLeo.hasNext()){
                linea = archivoLeo.nextLine();
                if (!linea.isEmpty()){
                    lineaDatosJugador.add(linea);
                }
            }
            archivoLeo.close();
        }catch (IOException e ){
            System.out.println("Error: "+ e);
        }
        return lineaDatosJugador;
    }
    
    public static ArrayList<Jugadores> jugadoresEnArchivo (ArrayList <String> lineaDatosJugador){
        
        String[] parts;
        String nombreJugador;
        int puntosPartida;
        int puntosTotal;
        
        ArrayList <Jugadores> arrayJugadores = new ArrayList <> ();
        try{
            for (String i : lineaDatosJugador){
                parts =i.split(" ");
                nombreJugador = parts[0].toLowerCase();
                nombreJugador =  nombreJugador.toUpperCase().charAt(0) + nombreJugador.substring(1, nombreJugador.length()).toLowerCase();
                puntosPartida = Integer.valueOf(parts[1]);
                puntosTotal = Integer.valueOf(parts[2]);
                
                Jugadores nuevo = new Jugadores(nombreJugador);
                nuevo.setPuntosPartida(1);
                nuevo.setPuntosTotal(2);
                arrayJugadores.add(nuevo);
            }
        }catch (ArrayIndexOutOfBoundsException e ){
            System.out.println("Error: "+ e);
        }
        return arrayJugadores;
        
    }
    
    public static void EscriboFichero (String nuevoJugador, ArrayList <String> lineaDatosJugador) throws IOException{
        boolean p= true;
        
        try{
            
            File f = new File ("src/eljuego/Jugadores.txt");
            FileWriter escribo = new FileWriter (f);
            if (!nuevoJugador.isEmpty()){
                Jugadores nuevo = new Jugadores (nuevoJugador);
                escribo.write ("" + nuevoJugador + " 0 0 \n");
            }
            
            for (String i : lineaDatosJugador){// Se vuelve a escribir el archivo Jugadores.txt con lo que contenia enteriormente
                escribo.write ("" + i + "\n");
            }
            escribo.close();
            
        }catch (IOException e ){
            System.out.println("Error: "+ e);
        }
        
        
        
    }
}
