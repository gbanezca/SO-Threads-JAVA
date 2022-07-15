package proyectoso;

import Ventana.Interfaz;
import static Ventana.Interfaz.carDisp;
import static Ventana.Interfaz.contCajas;
import static Ventana.Interfaz.contCarritos;
import static Ventana.Interfaz.contEstante;
import static Ventana.Interfaz.totEmp;
import static Ventana.Interfaz.totEst;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

public class Cliente extends Thread {
    /*NOTA: todos los System.out.print en esta clase son para testing
     y debugging...usar la herramienta de debugging es fastidioso
     aunque super util*/

    int id; // identificador de cada cliente

    //Semáforos
    Semaphore semCl; //Semaforo de los clientes
    Semaphore semC; //Semáforo de los carritos
    Semaphore semP; //Semaforos de las cajas, es P de pago 
    Semaphore semE; //Semaforo para acceder a X estante
    Semaphore semG; //Semaforo para el dinero
    

    /*Cuantos items agarro el cliente de los estantes,
     sirve para calcular el tiempo que tarda el cliente
     en poner sus items del carrito en la caja para pagar*/
    int addItem = 0;
    int nroItems;
    int nroEstantes; // numero total de estantes
    int nroCajas; // numero total de cajas    
    
    int contE = 0; //Auxiliar que sirve para ver a que estante va X cliente
    int contP = 0; //Auxiliar que sirve para ver a que caja va X cliente

    int total;//El total que hay que pagar y que se va a sumar al cont global
    int randomInt;
    boolean comprando = true; // salir de fase estantes
    boolean pagando = true; // salir de fase cajeros

    ArrayList<Semaphore> mutexEstantes;
    ArrayList<Semaphore> mutexCajeros;
    ArrayList<Semaphore> productosEstantes;
    ArrayList<Semaphore> productosCajeros;
    ArrayList<Semaphore> mutexEmpleados;
    ArrayList<Cajero> Cajeros;

    //Nunca use esto, perdon, pense que seria util cuando lo vi en el ejemplo
    //boolean nuevo;
    Interfaz interfaz;

    public Cliente(int id, Semaphore semCl, Semaphore semC, Semaphore semP, 
            Semaphore semE, Semaphore semG, ArrayList<Semaphore> mutexEstantes, 
            ArrayList<Semaphore> mutexCajeros, ArrayList<Semaphore> productosEstantes, 
            ArrayList<Semaphore> productosCajeros, ArrayList<Semaphore> mutexEmpleados, 
            ArrayList<Cajero> Cajeros){
        this.id = id;
        this.semCl = semCl;
        this.semC = semC;
        this.semP = semP;
        this.semE = semE;
        this.semG = semG;
        this.mutexEstantes = mutexEstantes;
        this.mutexCajeros = mutexCajeros;
        this.productosEstantes = productosEstantes;
        this.productosCajeros = productosCajeros;
        this.mutexEmpleados = mutexEmpleados;
        this.Cajeros = Cajeros;
    }

    

    //Le pondira javadoc a esto pero igual lo que se usa es el metodo start()
    public void run() {
        
        interfaz.contEstante.setText(Integer.toString(semE.availablePermits()));
        interfaz.contCajas.setText(Integer.toString(mutexCajeros.size()));
        interfaz.totEst.setText(Integer.toString(semE.availablePermits()));
        interfaz.totEmp.setText(Integer.toString(semE.availablePermits()));
        interfaz.contCarritos.setText(Integer.toString(semC.availablePermits()));
        interfaz.carDisp.setText(Integer.toString(semC.availablePermits()));
        interfaz.cajOp.setText(Integer.toString(mutexCajeros.size()));  
        interfaz.clAf.setText(Integer.toString(semC.getQueueLength()));
        interfaz.contClientes.setText(Integer.toString(semC.getQueueLength()));
        
        
       
        
        System.out.println("Soy el cliente " + Integer.toString(id) + "llegando");
        semCl.release(); // señala cantidad de clientes

        try {

            //Si no hay carritos disponibles para entrar al super, avisa
            
            if (semC.availablePermits() < 0) {
                System.out.println("Cliente " + this.id + " dice: No hay carritos, voy a esperar");
            }

            /*Desde aqui hasta donde dice HASTA AQUI es para modificar los contadores
             de la interfaz*/
            //Se modifica el contador de clientes en la clase Interfaz en el paquete Ventana
            
            contCarritos.setText(Integer.toString(semC.availablePermits()));
            carDisp.setText(Integer.toString(semC.availablePermits()));
            
            semC.acquire(); //Cliente resarva un carrito

            //Se modifica el contador de clientes en el sistema en la clase Interfaz en el paquete Ventana
            interfaz.contClientesSis
                    .setText(Integer.toString(semC.availablePermits() + 1));
            interfaz.clSist
                    .setText(Integer.toString(semC.availablePermits() + 1));
            interfaz.contCarritos
                    .setText(Integer.toString(semC.availablePermits() - 1));
            interfaz.carDisp
                    .setText(Integer.toString(semC.availablePermits() - 1));
            //HASTA AQUI HASTA AQUI HASTA AQUI

            System.out.println(this.id + " dice: agarre un carrito");

            //Tomando en cuena que tarda 5 min en llegar a cualquier estante
            Thread.sleep((Control.minuto * 5));
            
            nroEstantes = semE.availablePermits();
            //El cliente recorre los estantes uno por uno
            for (int i = 0; i < nroEstantes; i++) {

                System.out.println("-----------------");
                System.out.println(this.id + " dice: voy al estante " + this.contE);
                mutexEmpleados.get(contE).acquire(); // revisa que no este esperando un empleado a entrar
                mutexEstantes.get(contE).acquire(); // reserva el estante
                mutexEmpleados.get(contE).release();
                randomInt = (int) (2 * Math.random());
                nroItems = productosEstantes.get(contE).availablePermits(); // get numero de productos en estante
                System.out.println("nroItems "+nroItems);

                if (nroItems >= randomInt) { // solo comprar si hay suficiente productos en el estante

                    System.out.println("El cliente " + Integer.toString(id) + " comprara " 
                            + Integer.toString(randomInt) + " productos en el estante " + Integer.toString(contE));
                    Thread.sleep(Control.minuto * 1); // pasa tiempo comprando
                    System.out.println("RANDOMINT: "+randomInt);
                    productosEstantes.get(contE).acquire(randomInt); // toma los productos
                    addItem = addItem + randomInt; // cuenta el numero total de productos en el carrito
                    total = total + (randomInt * (int) (10 * Math.random())); // genera precios de los productos y los cuenta
                    semG.release(total);
                    
                    System.out.println("El cliente " + Integer.toString(id) + " termino en el estante " + Integer.toString(contE) + ", quedan " + productosEstantes.get(contE).availablePermits() + " productos.");

                }
                mutexEstantes.get(contE).release(); // libera el estante                        
                Thread.sleep(Control.minuto * 5); // tiempo en llegar a un estante                  
                contE++; // incrementa el estante al cual ir
            }
            /*Creo que en el enunciado del proyecto nunca de hablo
             de cuanto tarda el cliente en llegar a caja y no queria que
             el cliente llegara de inmediato asi que asumo que tarda lo mismo
             que con lo que los estantes: 5 min en llegar a cualquier lado*/
            Thread.sleep((Control.minuto * 5));

            nroCajas = semP.availablePermits(); // actualiza numero total de cajeros
            //Pide permiso para usar el semaforo

            for (int j = 0; j < nroCajas; j++) { // itera en los cajeros disponibles

                if (mutexCajeros.get(j).tryAcquire()) { // si encuentra un cajero disponible, lo reserva  
                    System.out.println("-----------------");
                    System.out.println(this.id + " dice: Voy a caja");
                    System.out.println("-----------------");
                    contP = j; // guarda cajero que lo atendera
                    j = nroCajas; // asegura salir del loop
                } else if (j == (nroCajas - 1)) { // si no encuentra un cajero    
                    System.out.println(this.id + " dice: Hay gente pagando, voy a esperar");
                    nroCajas = semP.availablePermits(); // re-actualiza numero total de cajeros
                    j = 0; // reinicia las interaciones del loop
                }

            }

           
            System.out.println(this.id + " dice: estoy pagando mis " + this.addItem + " productos");
            System.out.println("-----------------");
            
            while (addItem != 0) { // mientras hayan productos en el carrito

                System.out.println("El cliente " + Integer.toString(id) + " le falta comprar " + Integer.toString(addItem) + " productos del carrito.");
                //Creo que esta formula esta bien, es el medio segundo que tarda
                //en poner todos sus objetos en el mostrador
                Thread.sleep(Control.minuto / 60 / 2); // pasa tiempo el cliente pone el producto en la caja  
                productosCajeros.get(contP).acquire(); // señala al cajero que hay un producto para procesar                    
                addItem--; // quita un producto del carrito                    

            }

            Cajeros.get(contP).setFinCompra(true);
            //System.out.println(this.nro + " dice: pague " + this.total + "$");
            System.out.println("El cliente " + Integer.toString(id) 
                    + " ha concluido su compra."+  " Dice: pague " + this.total + "$");
            mutexCajeros.get(contP).release(); // libera el cajero
            //Los 2 min que tarda el cliente en dejar el carrito
            Thread.sleep((Control.minuto * 2));
            semC.release(); // libera el carrito
            semCl.acquire(); // se va el cliente

//       
            
            
            //El cliente deja la caja
            semP.release();
            
            //Se modifican mas contadores en la interfaz
            Interfaz.clDes
                    .setText(Integer.toString(Integer.parseInt(Interfaz.clDes.getText()) + 1));

        System.out.println("-----------------");
        System.out.println(this.id + " dice: ya pague, voy a dejar el carrito");
        System.out.println("-----------------");
            //deja ir el carritos
            if (semC.availablePermits() <= Control.maxCar) {
                semC.release();
                //Se modifican mas contadores en la interfaz
                Interfaz.contCarritos
                        .setText(Integer.toString(Integer.parseInt(Interfaz.contCarritos.getText()) + 1));
                Interfaz.carDisp
                        .setText(Integer.toString(Integer.parseInt(Interfaz.carDisp.getText()) + 1));
            }
            Interfaz.contClientes
                    .setText(Integer.toString(Integer.parseInt(Interfaz.contClientes.getText()) - 1));

            //Verificaciones
        System.out.println(this.id + " dice: solte el carrito,adios");
        System.out.println("-----------------");
            //Esperamos a que el thread muera
            join();
        } catch (InterruptedException e) {
            JOptionPane.showMessageDialog(null, "InterruptedException cliente",
                    "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    

   
    
//Getters y setters
    
    
    public Semaphore getSemC() {
        return this.semC;
    }

    public void setSemC(Semaphore semC) {
        this.semC = semC;
    }

    public int getTotal() {
        return this.total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Semaphore getSemE() {
        return this.semE;
    }

    public void setSemE(Semaphore semE) {
        this.semE = semE;
    }

    public Semaphore getSemP() {
        return this.semP;
    }

    public void setSemP(Semaphore semP) {
        this.semP = semP;
    }

    public int getNroItems() {
        return this.nroItems;
    }

    public void setNroItems(int nroItems) {
        this.nroItems = nroItems;
    }
}
