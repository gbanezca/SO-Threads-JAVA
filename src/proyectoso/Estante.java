/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso;

import static java.lang.Math.random;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jesus Barrios
 */
public class Estante extends Thread {

    //Variables
    //ints para darle precio a los productos y cuantos productos
    //se va a agarrar

    int randomInt, randomCost;

    ArrayList<Semaphore> mutexEstantes;
    ArrayList<Semaphore> productosEstantes;
    ArrayList<Semaphore> mutexEmpleados;

    //numero de productos en el estante y su capacidad
    //en un comienzo tienen el mismo valor
    //private int nroProd;
    int id;
    //su semaforo
    private Semaphore semE;
    boolean existo = true;
    
    //el cliente es null porque bueno...el cliente que accede al
    //estante cambia cada rato
    public Estante(int id, ArrayList<Semaphore> mutexEstantes, 
            ArrayList<Semaphore> productosEstantes, 
            ArrayList<Semaphore> mutexEmpleados, Semaphore semE) {
        this.id = id;
        this.mutexEstantes = mutexEstantes;
        this.productosEstantes = productosEstantes;
        this.mutexEmpleados = mutexEmpleados;
        this.semE = semE;
    }

    

   

   

    /**
     * Metodo para obtener productos del estante
     *
     * @param cliente thread que va a usar el estante
     * @throws InterruptedException
     */
    public void Existo(){
      existo = false;
    }
    
    @Override
    public void run() {
        /*El cliente puede(decidir)agarrar productos(el gafo igual puede decidir
         agarrar 0 productos), si no hay productos ve al "else" de este "if" 
         para mas detalles*/
        System.out.println("New estante nro: "+ id);
        semE.release();
        productosEstantes.get(id).release(10);
        mutexEstantes.get(id).release();
        mutexEmpleados.get(id).release();
        boolean empleadoTrabaja = false;
        
        // loop infinito revisando estatus del estante
        while(existo){
            
            try {
                Thread.sleep(50);
                mutexEstantes.get(id).acquire(); // reserva el estante mientras lo examina
                
                if (productosEstantes.get(id).availablePermits() <= 7 && !empleadoTrabaja) { // si hay muy pocos productos y ningun emplado en camino
                    
                    mutexEstantes.get(id).release(); // libera el estante
                    empleadoTrabaja = true; // envia al empleado
                    System.out.println("Empleado va al estante " + Integer.toString(id));
                    Thread.sleep(Control.minuto*4); // tiempo que al empleado la toma llegar
                    mutexEmpleados.get(id).acquire(); // reserva el estante para el empleado
                    
                }else if (productosEstantes.get(id).availablePermits() <= 7) { // si el empleado ya llego
                    
                    System.out.println("Empleado rellena el estante " + Integer.toString(id));
                    Thread.sleep(Control.minuto*1); // tiempo que trabaja el empleado en rellenar
                    productosEstantes.get(id).release(3); // 3 productos se añaden al  estante
                    mutexEmpleados.get(id).release(); // libera el estante para el empleado
                    mutexEstantes.get(id).release(); // libera el estante
                    empleadoTrabaja = false; // empleado termino
                    
                }else{ // si hay suficientes productos
                    
                    mutexEstantes.get(id).release(); // libera el estante
                    Thread.sleep(Control.minuto);
                    
                }                    
                
            } catch (InterruptedException ex) {
            }            
            
        }

    }
}

//        
//        if(nroProd!=0){
//            //se le asigna al cliente el semaforo de este estante
//            //cliente.setSemE(semS);
//            //Se le pide permiso al semaforo para acceder al estante
//            //cliente.getSemE().acquire();
//            mutexEstantes.get(id).acquire(); // reserva el estante mientras lo examina
//                
//            
//            
////            System.out.println("-----------------");
////            System.out.println(cliente.getNro()+" dice: Estoy en el estante");
//
//            //el tiempo que usa el cliente para decidir que va a agarrar
//            cliente.sleep(Control.minuto);
//            
//            //Un switch dependiendo del nro de productos que hay en el estante
//            switch(nroProd){
//                //no voy a explicar los 2 casos, hacen lo mismo, 
//                //solo que el case 1 hace un random entra 0...vea el caso default
//                case 1:
//                    randomInt = (int)(2 * Math.random());
//                    cliente.setNroItems(cliente.getNroItems()+randomInt);
//                    
//                    //System.out.println(cliente.getNro()+" dice: agarre "+randomInt+" productos");
//                    
//                    nroProd = nroProd - randomInt;
//                    if(randomInt!=0){
//                        randomCost= 1 + ((int)(10 * Math.random()));
//                        cliente.setTotal(cliente.getTotal()+randomCost);
//                    }
//                    break;
//
//                default:
//                    //Se escoge cuandos productos va a agarrar el cliente
//                    randomInt = (int)(3 * Math.random());
//                    /*se modifica el nro de prod que el cliente tiene
//                    ahora que escribo esto lo del nro de prod que el cliente
//                    tiene es inutil...¿tiene algo que ver con el enunciado
//                    del proyecto?*/
//                    cliente.setNroItems(cliente.getNroItems()+randomInt);
//                    
//                    //sout de verificacion
//                    //System.out.println(cliente.getNro()+" dice: agarre "+randomInt+" productos");
//                    
//                    /*Aqui si es importante el randomInt porque se le resta al
//                    total de productos que le quedan al estante despues de que
//                    el cliente agarro*/
//                    nroProd = nroProd - randomInt;
//                    //Si el cliente agarro productos aqui se le asigna el 
//                    //precio a c/producto que agarro el cliente
//                    
//                    if(randomInt!=0){
//                        for (int i = 0; i < randomInt; i++) {
//                            //Se inventa el precio
//                            randomCost= 1 + ((int)(10 * Math.random()));
//                            //Se suma al total que tiene que pagar el cliente
//                            //y por lo tanto lo que iria al contador global
//                            cliente.setTotal(cliente.getTotal()+randomCost);
//                        }
//                    }
//                    break;
//            }
//            //verificacion
////            System.out.println("-----------------");
////            System.out.println("PRODUCTOS RESTANTES:"+nroProd);
//            
//            //el cliente deja ir el semaforo
//            cliente.getSemE().release();
//            
//            
//        } else {
//            //Si el estante esta vacio se entra en un loop infinito hasta
//            //que el empleado rellene el estante,luego de eso se vuelve a llamar
//            //a la funcion
//            //System.out.println(cliente.getNro()+" dice: Estante vacio,esperare");
//            while(nroProd==0){}
//            get(cliente);
//        }
//        
//    }
//    
//    
//    /*Este deberia ser el put de productor consumidor, por ahora..o para el
//    momento de la entrega este proceso raro el cual estoy seguro no aplica 
//    productor consumidor*/
//    /**
//     * Metodo para reponer productos del estante
//     * @param empleado thread que va a usar el estante
//     * @throws InterruptedException 
//     */
//    void put (Empleado empleado) throws InterruptedException {
//        //Si el empleado no tiene semaforo asignado, se le asigna el semaforo
//        //de su respectivo estante...me da miedo cambiar esto
//        if(empleado.getSem()==null){
//            empleado.setSem(semS);
//        }
//        
//        //si el numero de productos es lo suficientemente pequeño como
//        //para el empleado vaya a buscar una caja se ejecuta esto
//        if(nroProd<=(cap-3)){
//            //verificando que el thread empleado funcione
//            //System.out.p rintln("Empleado dice: Oh no el estante tiene que ser repuesto");
//            
//            //buscando la caja...
//            Thread.sleep(Control.minuto*4);
//            //se pide permiso para usar el estante
//            empleado.getSem().acquire();
//            
//            //El minuto en el que se repone el estante+verificacion por cout
//            //System.out.println("Empleado dice: reponiendo estante");
//            empleado.sleep(Control.minuto);
//            
//            //Se repone el estante
//            //System.out.println("Empleado dice: Estante repuesto con 3 productos");
//            nroProd = nroProd + 3;
//            
//            //verificacion+se suelta el permiso para usar el semaforo 
//            //del estante...me sigo preguntando si eso esta bien dicho
//            //System.out.println("PRODUCTOS RESTANTES: "+nroProd);
//            empleado.getSem().release();
//        } 
//        
//    }
//
//    
//    
//    
//    //getters y setters
//    
//    public int getNroProd() {
//        return nroProd;
//    }
//
//    public void setNroProd(int nroProd) {
//        this.nroProd = nroProd;
//    }
//
//    public Semaphore getSemS() {
//        return semS;
//    }
//
//    public void setSemS(Semaphore semS) {
//        this.semS = semS;
//    }
//    

