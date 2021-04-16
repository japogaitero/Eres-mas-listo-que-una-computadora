/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package eljuego;

import static eljuego.Partida.jugandoPartida;
import static eljuego.Partida.pedirJugadores;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeMap;

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
        
        Map <String, Integer> r = new HashMap();
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
                        menuPartida();
                        continua = true;
                        break;
                    case 2:
                        ranking();
                        
                        break;
                    case 3:
                        historico();
                        continua = true;
                        break;
                    case 4:
                        GestionJugadores gestion = new GestionJugadores ();
                        
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
    
    public static void menuPartida()throws Exception, IOException{
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
        
        ArrayList <Jugadores> jugadoresPartida = new ArrayList <>();
        jugadoresPartida = pedirJugadores(nuevaPartida.getnJugadores());
        nuevaPartida.setJugadoresPartida(jugadoresPartida);
        System.out.println("\nY los jugadores para esta partida son:");
        for (Jugadores i : nuevaPartida.getJugadoresPartida()){
            System.out.print(i.getNombre());
            System.out.println("\t");
        }
        System.out.println("Vamos a decidir aleatoriamente el orden de los jugadores...");
        
        Collections.shuffle(nuevaPartida.getJugadoresPartida());
        System.out.println("\nEl orden de los jugadores para responder es:");
        for (Jugadores i : nuevaPartida.getJugadoresPartida()){
            System.out.print(i.getNombre());
            System.out.println("\t");
        }
        ArrayList <Jugadores> jugadoresActualizados;
        jugadoresActualizados = jugandoPartida(nuevaPartida.getJugadoresPartida(), nuevaPartida.getnRondas());
        ArrayList <String> puntuacionesNuevas = new ArrayList();
        
        for (Jugadores i : jugadoresActualizados){
            puntuacionesNuevas.add(i.toString());
        }
        
        actualizoFichero(jugadoresActualizados);
    }
    
    public  static ArrayList<String> lineaDatosJugador(){
        
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
        String expresion="^Cpu\\d*$";
        int puntosPartida;
        int puntosTotal;
        
        
        ArrayList <Jugadores> arrayJugadores = new ArrayList <> ();
        
        if (!lineaDatosJugador.isEmpty()){
        try{
            for (String i : lineaDatosJugador){
                parts =i.split(" ");
                nombreJugador = parts[0].toLowerCase();
                nombreJugador =  nombreJugador.toUpperCase().charAt(0) + nombreJugador.substring(1, nombreJugador.length()).toLowerCase();
                puntosPartida = Integer.valueOf(parts[1]);
                puntosTotal = Integer.valueOf(parts[2]);
                
                
                Jugadores nuevo = new Jugadores(nombreJugador);
                nuevo.setPuntosPartida(puntosPartida);
                nuevo.setPuntosTotal(puntosTotal);
                if (nombreJugador.matches(expresion) || nombreJugador.equalsIgnoreCase ("Cpu") ){
                    nuevo.setHumano(false);                    
                }
                arrayJugadores.add(nuevo);
            }
        }catch (ArrayIndexOutOfBoundsException e ){
            System.out.println("Error: "+ e);
        }
        }
        return arrayJugadores;
        
    }
    
    public  static void escriboFichero (String nuevoJugador, ArrayList <String> lineaDatosJugador) throws IOException{
        File f = new File ("src/eljuego/Jugadores.txt");
        
        
        try{
            
            if (!lineaDatosJugador.isEmpty()){
            FileWriter escribo = new FileWriter (f);
            
            
            if (!nuevoJugador.isEmpty()){
                escribo.write ("" + nuevoJugador + " 0 0 \n");
            }
            
            for (String i : lineaDatosJugador){// Se vuelve a escribir el archivo Jugadores.txt con lo que contenia enteriormente
                escribo.write ("" + i + "\n");
            }
            
            escribo.close();
            }
        }catch (IOException e ){
            System.out.println("Error: "+ e);
        }
        
    }
    
    public static void actualizoFichero(ArrayList <Jugadores> puntuacionesNuevas) throws IOException{
        ArrayList<Jugadores> lineaDatosJugador = new ArrayList();
        ArrayList<String> archivoActualizado = new ArrayList();
        
        if (!puntuacionesNuevas.isEmpty()){
        
        try{
            lineaDatosJugador = jugadoresEnArchivo(lineaDatosJugador());
            File f = new File ("src/eljuego/Jugadores.txt");
            FileWriter escribo = new FileWriter (f);
            
            for (int i = 0 ; i < puntuacionesNuevas.size() ; i++){ // Se comprueba que el nuevo nombre no esta ya en el arrayList
                String jugadorActualizado = (puntuacionesNuevas.get(i)).getNombre();
                for (int j = 0 ; j < lineaDatosJugador.size() ; j++){
                    String jugadorArchivo = (lineaDatosJugador.get(j)).getNombre();
                    if (jugadorArchivo.equalsIgnoreCase(jugadorActualizado)){
                        System.out.println("Se va a actualizar los datos del nombre " + jugadorArchivo);
                        lineaDatosJugador.get(j).setPuntosTotal(puntuacionesNuevas.get(i).getPuntosTotal()+lineaDatosJugador.get(j).getPuntosTotal());
                        
                        
                    }
                }
            }
            for (Jugadores i : lineaDatosJugador){
                archivoActualizado.add(i.toString());
            }
            for (String i : archivoActualizado){
                escribo.write ("" + i + "\n");
            }
            escribo.close();
            
        }catch (IOException e ){
            System.out.println("Error: "+ e);
        }
        }
    }
    
    public static void historico(){
        
        
        String linea;
        ArrayList<String> historico = new ArrayList<>();
        
        try{
            File lectura1 = new File ("src/eljuego/historico.txt");
            Scanner archivoLeo = new Scanner(lectura1);
            
            
            while (archivoLeo.hasNext()){
                linea = archivoLeo.nextLine();
                if (!linea.isEmpty()){
                    historico.add(linea);
                }
            }
            archivoLeo.close();
            Collections.reverse(historico);
            
            for (String i : historico){
                System.out.println(i);
            }
            
            
        }catch (IOException e ){
            System.out.println("Error: "+ e);
        }
        
    }
    
    public static void actualizoHistorico(String resultados){
        
        String linea;
        ArrayList<String> historico = new ArrayList<>();
        
        try{
            File lectura1 = new File ("src/eljuego/historico.txt");
            Scanner archivoLeo = new Scanner(lectura1);           
            
            while (archivoLeo.hasNext()){
                linea = archivoLeo.nextLine();
                if (!linea.isEmpty()){
                    historico.add(linea);
                }
            }
            archivoLeo.close();
            //Collections.reverse(historico);
            FileWriter actualizoHistorico = new FileWriter(lectura1);
            
            actualizoHistorico.write ("" + resultados + "\n");
            
            for (String i : historico){// Se vuelve a escribir el archivo Jugadores.txt con lo que contenia enteriormente
                actualizoHistorico.write ("" + i + "\n");
            }
            actualizoHistorico.close();
            
        }catch (IOException e ){
            System.out.println("Error: "+ e);
        }
    }
    
    public static void ranking(){
        
        ArrayList <Jugadores> arrayJugadores = new ArrayList<>();
        Map <String, Integer > Ranking = new HashMap();
        arrayJugadores = jugadoresEnArchivo(lineaDatosJugador());
        String nombre;
        String expresion="^Cpu\\d*$";
        int puntuacion;
        
        for (Jugadores i :arrayJugadores ){
            if(!i.getNombre().matches(expresion)){
            nombre = i.getNombre();
            puntuacion = i.getPuntosTotal();
            Ranking.put(nombre, puntuacion);
            }
        }
        
        Map<String, Integer> ordenadoPorPuntos = Ranking.entrySet()
                .stream()
                .sorted((Map.Entry.<String, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        
        
        for (String i : ordenadoPorPuntos.keySet()) {
                System.out.println("Nombre: " + i + "\t\t\t Puntos totales: " + Ranking.get(i));
            }
        
        
        
    }
    
    
    
    
}
