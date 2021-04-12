/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package eljuego;

import java.io.FileNotFoundException;
import java.io.IOException;
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
    
}
