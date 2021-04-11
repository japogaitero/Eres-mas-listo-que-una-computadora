/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eljuego;

/**
 * 
 * @author Victor Alvarez <japogaitero at gmail.com>
 */
public class MiExcepcion extends Exception{
     
    private int codigoError;
     
    public MiExcepcion(int codigoError){
        super();
        this.codigoError=codigoError;
    }
     
    @Override
    public String getMessage(){
         
        String mensaje="";
         
        switch(codigoError){
            case 14:
                mensaje="Error, debes escoger una opcion entre 1 y 4";
                break;
            case 222:
                mensaje="Error, el numero esta entre 11 y 20";
                break;
            case 333:
                mensaje="Error, el numero esta entre 21 y 30";
                break;
        }
         
        return mensaje;
         
    }
     
}
