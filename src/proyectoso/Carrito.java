/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso;

import Ventana.Interfaz;
import java.util.concurrent.Semaphore;

/**
 *
 * @author Ismenia Luces
 */
public class Carrito extends Thread  {
     int id; // identificador unico
    Semaphore semC;
    Semaphore numCarritos;
    boolean matar = true;
    Interfaz interfaz;
    
    public Carrito(int id, Semaphore semC, Semaphore numCarritos){ // constructor
        this.id = id;
        this.semC= semC;
        this.numCarritos = numCarritos;
    }
    
    public void matar(){
      matar = false;
  }
    
    // señala que hay un carrito disponible al crearse el hilo
    public void run(){        
 
            System.out.println("Carrito " + Integer.toString(id));
            semC.release();
            numCarritos.release();     
            interfaz.contClientesSis.setText(Integer.toString( numCarritos.availablePermits() - semC.availablePermits()));
            interfaz.clSist.setText(Integer.toString( numCarritos.availablePermits() - semC.availablePermits()));
            interfaz.totCar.setText(Integer.toString( numCarritos.availablePermits()));
         
            
        }
}
