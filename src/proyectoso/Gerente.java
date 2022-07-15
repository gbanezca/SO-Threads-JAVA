/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso;

import Ventana.Interfaz;
import javax.swing.JOptionPane;

public class Gerente extends Thread {
  /*Clase gerente, su unica utilidad es "resetear" los contadores de horas
    y ganancias*/
    
    //Jefe con las horas a "resetear"
    Jefe jefe;

    /**
     * 
     * @param jefe 
     */
    Gerente(Jefe jefe) {
      this.jefe = jefe;
    }
  
  @Override
    public void run() {
        //sout de verificacion
        //System.out.println("GERENTE ACTIVO");
        //trycatch obligatorio 
        try {
            //loop infinito
            while (true) {
                //Cuando pasen 8 horas, se resetean los contadores
                if(this.jefe.getHoras() == 8) {
                    this.jefe.setHoras(0);
                    Interfaz.horas.setText("0");
                    //System.out.println("Gerente dice: reset a " + this.jefe.getHoras() + " horas");
                    Interfaz.ganancias.setText("0");
                } 
                //si se quita esto el programa va a dejar de ejecutar
                //este thread
                Thread.sleep(100);
            } 
        } catch (InterruptedException ex) {
            JOptionPane.showMessageDialog(null, "InterruptedException gerente", 
                    "ERROR", JOptionPane.ERROR_MESSAGE);

        } 
    }
}
