/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso;

import Ventana.Interfaz;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Jesus Barrios
 */
public class ProyectoSO {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
  
        /*BIENVENIDO AL MAIN, para mas info de como funciona este programa
        ir primero a Control, despues de eso se convierte en ir clase
        por clase viendo los comentarios*/
        
        /*Ok, como funciona este proyecto: se comienza en el constructor
        de la clase control en donde se llama a la clase Archivo para
        que cargue los datos del txt y "cree" la sucursal, por crear
        me refiero a lo que es asignar carritos iniciales, el max de carritos
        etc. A partir de lo cargado en archivo y guardado en sucursal
        se instancian(es asi? llevo tiempo sin usar terminologia de programacion) 
        los semaforos y los estantes iniciales. Todo esto es lo que ocurre
        cuando se instancia el Control app*/
        Control app = new Control();
        new Interfaz(app).setVisible(true);
        /*Ahora en iniciar es donde de verdad comienza el asunto porque se
        dan inicio a todos los threads que va a manejar este programa, como
        los clientes y los empleados que vigilan los estantes, mencionaria
        a los threads de Jefe y Gerente pero solo estan encargados de manejar
        los contadores "horas" y "ganancias" asi que no hacen mucho*/
        app.iniciar();
        /*En Control a traves del semaforo sClientes es que se maneja cuantos
        clientes hay dentro del local en cualquier momento dado, porque bueno..
        si un cliente agarra un carrito es porque esta dentro del supermercado.
        Luego de obtener el permiso de sClientes el cliente recorre los estantes
        (ver clase Estante para ver como funcionan los estantes, y los recorre
        con un ciclo iterativo, los estantes estan guardados en un array) y finalmente
        llega a caja, las cajas son manejadas por el semaforo sCajeros y luego
        de que obtiene el permiso para ese semaforo el cliente "paga", libera
        un permiso en el semaforo sCajeros y va a dejar su carrito, y por decir
        dejar su carrito me refiero a que libera un permiso del semaforo sClientes
        
        Ahora, como funcionan los estantes? cada estante tiene su propio semaforo
        por lo que los clientes y el empleado asignado van a tener que compartir
        ese semaforo para agarrar productos o reponerlos. 
        Donde estan los estantes? los estantes estan el el array de Estante [] 
        estantes en la clase Control.
        Porque un array? bueno, se penso la idea de unar un arraylist pero como
        solo se pueden agregar estantes y se sabe el numero maximo de estantes,
        porque no? en mi opinion es mas facil manejarlo asi..a pesar de que
        uso ciclos iterativos para insertar estantes nuevos(ver Interfaz-boton 
        plusEstante para mas detalles sobre insertar nuevos estantes)...*/
        
        /*Y por ultimo los empleados, su unica utilidad es la de reponer los
        estantes, hay tantos empleados como estantes haya, al menos asi es en este
        programa, no se toman en cuenta los de la caja. Tienen un estante asignado
        que se les pasa por constructor y "utilizan" un metodo, el metodo put de
        la clase Estante, que sirve para reponer 3 productos del estante
        
        Para mas detalle de como funciona cada clase ver c/clase individualmente*/
        
        
        
                
    }
    
}
