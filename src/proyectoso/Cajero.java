/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso;

import Ventana.Interfaz;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Ismenia Luces
 */
public class Cajero extends Thread {

    int total;
    Control control;
    int id; // identificador 
    int tiempoPagando; // tiempo en caja
    int contE = 0; // cajero actual en el que se ubica el cliente
    int numCajeros; // numero total de cajeros
    Semaphore semCl; // semaforo de clientes
    Semaphore semC; // semafoto de carritos
    Semaphore semP; // semaforo de cajeros
    Semaphore semG; // semaforo de Ganancias
    ArrayList<Semaphore> mutexCajeros;
    ArrayList<Semaphore> productosCajeros;
    int numProductos;
    int aComprar;
    boolean despedir = true;
    boolean finCompra = false;
    Interfaz interfaz;

    public Cajero(int id, Semaphore semCl, 
            Semaphore semC, Semaphore semP,Semaphore semG, 
            ArrayList<Semaphore> mutexCajeros, 
            ArrayList<Semaphore> productosCajeros, int total) { // constructor
        this.id = id;
        this.semCl = semCl;
        this.semC = semC;
        this.semP= semP;
        this.semG = semG;
        this.mutexCajeros = mutexCajeros;
        this.productosCajeros = productosCajeros;
        this.total = total;
    }

    public void despedir() {
        despedir = false;
    }

    public void setFinCompra(boolean finCompra) {
        this.finCompra = finCompra;
    }

    public void run() {

        System.out.println("Cajero " + Integer.toString(id) + " LISTO");
        semP.release();
        mutexCajeros.get(id).release();
        productosCajeros.get(id).release();

        try {

            while (despedir) { 
                
                interfaz.contCajas.setText(Integer.toString(semP.availablePermits()));
                if (mutexCajeros.get(id).availablePermits() == 0) { // comprobacion para cliente en cajero
                    if (!productosCajeros.get(id).tryAcquire()) { // comprobacion producto en cajero para procesar, reserva su espacio                  
                        System.out.println("Cajero " + Integer.toString(id) + " se encuentra procesando un producto.");
                        Thread.sleep(Control.minuto * 60); // tiempo procesar producto
                        productosCajeros.get(id).release(); // libera espacio del producto en el cajero
                    } else {
                        productosCajeros.get(id).release(); // libera espacio del producto en el cajero
                    }

                } else if (finCompra) {
                    Thread.sleep(Control.minuto);
                    
                    //Se modifica el contador de ganancias en la interfaz
                    interfaz.ganancias.setText(Integer.toString(semG.availablePermits()));
                    finCompra = false;
                }else{
                Thread.sleep(100);
            }
                
            }

        } catch (InterruptedException e) {
        }

    }
}
