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
public class Jugadores extends GestionJugadores{
    
    private String nombre;
    private int puntosPartida;
    private int puntosTotal;
    private boolean humano;
    private boolean exists = false;
    //private String expresion="^Cpu\\d*$";

    public Jugadores() {
        this.exists = false;
    }

    
    
    

    public Jugadores(String usuario) {
        String expresion="^Cpu\\d*$";
        this.nombre = usuario;
        this.puntosPartida = 0;
        this.puntosTotal = 0;
        this.humano = true;
        this.exists = true;
        
        if (usuario == null){
            System.out.println("no noooombre");
            this.exists = false;
        }
        
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPuntosPartida() {
        return puntosPartida;
    }

    public void setPuntosPartida(int puntosPartida) {
        this.puntosPartida = puntosPartida;
    }

    public int getPuntosTotal() {
        return puntosTotal;
    }

    public void setPuntosTotal(int puntosTotal) {
        this.puntosTotal = puntosTotal;
    }

    public boolean isHumano() {
        return humano;
    }

    public boolean isExists() {
        return exists;
    }
    

    public void setHumano(boolean humano) {
        this.humano = humano;
        //System.out.println("hemos cambiado a cpu");
    }

    @Override
    public String toString() {
        return nombre + " " + "0" + " " + puntosTotal;
    }
    
    
    
    
    
        

}
