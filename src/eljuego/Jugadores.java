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
    private boolean humano = false;

    public Jugadores(String nombre) {
        this.nombre = nombre;
        this.puntosPartida = 0;
        this.puntosTotal = 0;
        humano = true;
        
        
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

    public void setHumano(boolean humano) {
        this.humano = humano;
    }

    @Override
    public String toString() {
        return "Jugadores{" + "nombre=" + nombre + '}';
    }
    
    
    
    
    
        

}
