/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso;

import Ventana.Interfaz;
import javax.swing.JOptionPane;

public class Jefe extends Thread {
    int horas = 0;
    boolean firstloop = true;
    //Se me olvido porque no hice un constructor
    
    
    public void run() {
        //sout de verificacion
        //System.out.println("JEFE ACTIVO");
        //trycatch obligatorio
        try {
            //loop infinito
            while (true) {
                //pasa una "hora"
                if(firstloop==true){
                    Thread.sleep((Control.minuto * 60));
                    firstloop=false;
                //No se si esta bien pero si se toma en cuenta que el tipo
                //tarda min y medio en cambiar el contador no se
                //deberia restar de la hora que pasa?
                }else Thread.sleep((Control.minuto * 60)-(Control.minuto + Control.minuto/2));
                
                //verificacion
                //System.out.println("Jefe dice: actualizando horas");
                //el minuto y medio que tarda en modificar el contador
                Thread.sleep((Control.minuto + Control.minuto / 2));
                this.horas++;
                
                //se actualiza el contador en la UI
                Interfaz.horas.setText(Integer.toString(this.horas));
                //mas verificacion
                //System.out.println("Jefe dice: horas trabajadas " + this.horas);
            } 
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "InterruptedException jefe", 
                    "ERROR", JOptionPane.ERROR_MESSAGE);
          
        } 
    }
  
    //getters y setters
  public int getHoras() {
    return this.horas;
  }
  
  public void setHoras(int horas) {
    this.horas = horas;
  }
}
