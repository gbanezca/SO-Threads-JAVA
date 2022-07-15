/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso;

import Ventana.Interfaz;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;
import javax.swing.JOptionPane;

public class Control {
    /*Clase control, nuestro "main",por asi decirlo,
     el que controla todo detras de la UI*/  
   
    
    Archivo archivo;//variable para la clase que maneja el cargado de los datos el txt    
    Sucursal sucursal; //variable para la clase donde se van a cargar los datos
                       //extraidos en la clase Archivo
    Cliente cliente;//El cliente
    Jefe jefe;//Jefe que solo modifica las horas   
    Gerente gerente; //Gerente que resetea contadores

  
      
    static int minuto;//Lo que vale un minuto de acuerdo a lo establecido por el txt    
    static int carDisp;//carritos disponibles    
    static int cajDisp; //cajas disponibles 
    static int maxCar, maxCaj;
    int total; //contador de total
    int numClientes = 0; // identificador de clientes
    
    Semaphore semC; // semaforo contador de carritos disponibles
    Semaphore numCarritos; // semaforo contador de carritos existentes
    Semaphore semCl; // semaforo contador de carritos existentes
    Semaphore semE; // semaforo contador de estantes existentes, PD: amen que no hay que borrar estantes
    Semaphore semP; // semaforo contador de cajeros existentes
    Semaphore semG; // semaforo contador de gaancias
    
    ArrayList<Carrito> Carritos = new ArrayList<Carrito>(); // array de hilos de carritos
    ArrayList<Cliente> Clientes; // array de hilos de clientes
    ArrayList<Estante> Estantes; // array de hilos de estantes
    ArrayList<Cajero> Cajeros; // array de hilos de cajeros
    ArrayList<Semaphore> mutexEstantes; // array de semaforos de exclusion mutua por estante
    ArrayList<Semaphore> productosEstantes; // array de semaforos contadores de productos por estante
    ArrayList<Semaphore> mutexEmpleados; // array de semaforos de exclusion de estantes para los empleados
    ArrayList<Semaphore> mutexCajeros; // array de semaforos de exclusion mutua por Cajeros
    ArrayList<Semaphore> productosCajeros; // array de semaforos contadores de productos por cajero
    Interfaz interfaz;

    public Control() throws InterruptedException  {
        //El archivo que va a cargar los datos para la sucursal
        this.archivo =  new Archivo();
        
        //La sucursal que va a indicar cuanto es en segundos una hora en el programa
        //los carritos iniciales, la max cantidad de carritos, etc...
        this.sucursal = this.archivo.cargarData();
        
        //se crea el jefe y gerente que van a manejar los contadores globales
        this.jefe =  new Jefe();
        this.gerente = new Gerente(this.jefe); 
        
        
        this.semC = new Semaphore(0, true);
        
        //Si surge la pregunta de porque se usa el nro maximo de carritos/cajas
        //es porque se van a adquirir los permisos de acuerdo a cuantos
        //carritos cajas hay inicialmente
        this.numCarritos = new Semaphore(0, true);
        
        //El semaforo que maneja cuantos clientes hay dentro del supermercado
        this.semCl = new Semaphore(0, true);
        
        //El semaforo que maneja cuantos estantes hay en el supermercado
        this.semE = new Semaphore(0, true);
        
        //El semaforo que maneja cuantas cajas van a haber en operacion
        this.semP = new Semaphore(0, true);
        this.semG = new Semaphore(0, true);
        
        this.Clientes = new ArrayList<Cliente>();
        this.Estantes = new ArrayList<>();
        this.Cajeros = new ArrayList<>();
        this.mutexEstantes = new ArrayList<>();
        this.productosEstantes = new ArrayList<>();
        this.mutexEmpleados = new ArrayList<>();
        this.mutexCajeros =new ArrayList<>();
        this.productosCajeros = new ArrayList<>();
        
        
        //la formula para calcular cuanto es un minuto en segundos
        minuto = this.sucursal.getTiempo() / 60 * 1000;
    }
    
    
    
//    public Control() throws InterruptedException {
//        //se crea el jefe y gerente que van a manejar los contadores globales
//        //...aunque no hay una variable ganancias y lo mostramos directamente en 
//        //Ventana.Interfaz
//        this.jefe = new Jefe();
//        this.gerente = new Gerente(this.jefe);
//        //El archivo que va a cargar los datos para la sucursal
//        this.archivo = new Archivo();
//        //La sucursal que va a indicar cuanto es en segundos una hora en el programa
//        //los carritos iniciales, la max cantidad de carritos, etc...
//        this.sucursal = this.archivo.cargarData();
//        //la formula para calcular cuanto es un minuto en segundos
//        minuto = this.sucursal.getTiempo() / 60 * 1000;
//        //El array en donde van a estar los estantes creados
//        //su tamaño se determina de lo extraido del txt
//        maxCar = this.sucursal.getNroEstantesMax();
//        maxCaj = this.sucursal.getNroCajasMax();
//        this.estantes = new Estante[this.sucursal.getNroEstantesMax()];
//        //El semaforo que maneja cuantos clientes hay dentro del supermercado
//        this.sClientes = new Semaphore(this.sucursal.getNroCarritosMax(), true);
//        //El semaforo que maneja cuantas cajas van a haber en operacion
//        this.sCajeros = new Semaphore(this.sucursal.getNroCajasMax(), true);
//        //Si surge la pregunta de porque se usa el nro maximo de carritos/cajas
//        //es porque se van a adquirir los permisos de acuerdo a cuantos
//        //carritos cajas hay inicialmente
//        this.numCarritos = new Semaphore(0, true);
//        this.sEstantes = new Semaphore(0, true);
//        this.sCajeros = new Semaphore(0, true);
//        this.total = 0;
//        this.Clientes = new ArrayList<>();
//        this.Estantes = new ArrayList<>();
//        this.Cajeros = new ArrayList<>();
//        this.mutexEstantes = new ArrayList<>();
//        this.productosEstantes = new ArrayList<>();
//        this.mutexEmpleados = new ArrayList<>();
//        this.mutexCajeros = new ArrayList<>();
//        this.productosCajeros = new ArrayList<>();
//
//        
//    }

    /**
     * Comienza el programa de verdad crea los clientes y los empleados que
     * manejan los estantes. tambien de inicio a los threads de los
     * clientes,empleados,jefe y gerente
     */
    
    
//    //Se crean los estantes y se guardan directamente en el array
//        for (int i = 0; i < this.sucursal.getEstantesIni(); i++) {
//            this.estantes[i] = new Estante(this.sucursal.getCapEstantes());
//        }
//        //Esta el la parte que se dijo en donde se adquieren los permisos
//        //de acuerdo al nro de carritos iniciales
//        carDisp = this.sucursal.getNroCarritosMax() - this.sucursal.getCarritosIni();
//        this.sClientes.acquire(carDisp);
//        cajDisp = this.sucursal.getNroCajasMax() - this.sucursal.getCajasIni();
//        this.sCajeros.acquire(cajDisp);

        //Verificaciones
        //System.out.println("Carritos disponibles " + this.sClientes.availablePermits());
        //System.out.println("Cajas abiertos " + this.sCajeros.availablePermits());
        
    public void iniciar() {
        
        //Se inicia al jefe y al gerente (tiempo global)
        this.jefe.start();
        this.gerente.start();
        
        //Aunque no es lo correcto no me gusta comenzar con el cliente 0
    
        // inicializamos los hilos de carritos
        for (int i = 0; i < sucursal.getCarritosIni(); i++) {
            Carritos.add(new Carrito(i, semC, numCarritos));
            Carritos.get(i).start();
            //Carritos.get(i).setName("Carrito" + Integer.toString(i));
        }
        
        //Se crean e inician los estantes cada uno atendido por un empleado
        for (int i = 0; i < sucursal.getEstantesIni(); i++) {
//            this.empleado = new Empleado(this.estantes[i]);
//            this.empleado.start();
            
            mutexEstantes.add(new Semaphore(0, true));
            productosEstantes.add(new Semaphore(0, true));
            mutexEmpleados.add(new Semaphore(0));
            Estantes.add(new Estante(i, mutexEstantes, productosEstantes, mutexEmpleados, semE));
            Estantes.get(i).start();
            //Estantes.get(i).setName("Estante" + Integer.toString(i));            
        }
        
        // iniciamos los hilos de cajeros       
        for (int i = 0; i < sucursal.getCajasIni(); i++){
            mutexCajeros.add(new Semaphore(0, true));
            productosCajeros.add(new Semaphore(0, true));
            Cajeros.add(new Cajero(i, semCl, semC, semP, semG, mutexCajeros, productosCajeros, total));
            Cajeros.get(i).start();
            //Cajeros.get(i).setName("Cajero" + Integer.toString(i));
        }
        
        //loop infinito que crea los clientes
        while (true) {
            cliente = new Cliente(numClientes,semCl,semC, semP ,semE, 
                    semG, mutexEstantes, mutexCajeros, productosEstantes, 
                    productosCajeros, mutexEmpleados, Cajeros);
            Clientes.add(cliente);
            Clientes.get(numClientes).start();
            numClientes++;
            //updateInterfaz();
            
            try {
                Thread.sleep((minuto * 5));
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, "InterruptedException control",
                        "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            //pense que seria bueno poner un garbage collector aqui...
            System.gc();
        }
            
    }
    

    /**
     * Metodo para actualizar el contador de clientes afuera y cajas operativas
     * en la UI
     */
    //Originalmente pense que este metodo actualizaria mas contadores
    /*public void updateInterfaz() {
        Interfaz.clAf
                .setText(Integer.toString(this.semCl.getQueueLength()));
        Interfaz.cajOp
                .setText(Integer.toString(this.semP.availablePermits()));
    }*/

    //getters and setters
    public Archivo getArchivo() {
        return archivo;
    }

    public void setArchivo(Archivo archivo) {
        this.archivo = archivo;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Jefe getJefe() {
        return jefe;
    }

    public void setJefe(Jefe jefe) {
        this.jefe = jefe;
    }

    public Gerente getGerente() {
        return gerente;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }

    public static int getMinuto() {
        return minuto;
    }

    public static void setMinuto(int minuto) {
        Control.minuto = minuto;
    }

    public static int getCarDisp() {
        return carDisp;
    }

    public static void setCarDisp(int carDisp) {
        Control.carDisp = carDisp;
    }

    public static int getCajDisp() {
        return cajDisp;
    }

    public static void setCajDisp(int cajDisp) {
        Control.cajDisp = cajDisp;
    }

    public static int getMaxCar() {
        return maxCar;
    }

    public static void setMaxCar(int maxCar) {
        Control.maxCar = maxCar;
    }

    public static int getMaxCaj() {
        return maxCaj;
    }

    public static void setMaxCaj(int maxCaj) {
        Control.maxCaj = maxCaj;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getNumClientes() {
        return numClientes;
    }

    public void setNumClientes(int numClientes) {
        this.numClientes = numClientes;
    }

    public Semaphore getSemC() {
        return semC;
    }

    public void setSemC(Semaphore semC) {
        this.semC = semC;
    }

    public Semaphore getNumCarritos() {
        return numCarritos;
    }

    public void setNumCarritos(Semaphore numCarritos) {
        this.numCarritos = numCarritos;
    }

    public Semaphore getSemCl() {
        return semCl;
    }

    public void setSemCl(Semaphore semCl) {
        this.semCl = semCl;
    }

    public Semaphore getSemE() {
        return semE;
    }

    public void setSemE(Semaphore semE) {
        this.semE = semE;
    }

    public Semaphore getSemP() {
        return semP;
    }

    public void setSemP(Semaphore semP) {
        this.semP = semP;
    }

    public Semaphore getSemG() {
        return semG;
    }

    public void setSemG(Semaphore semG) {
        this.semG = semG;
    }

    public ArrayList<Carrito> getCarritos() {
        return Carritos;
    }

    public void setCarritos(ArrayList<Carrito> Carritos) {
        this.Carritos = Carritos;
    }

    public ArrayList<Cliente> getClientes() {
        return Clientes;
    }

    public void setClientes(ArrayList<Cliente> Clientes) {
        this.Clientes = Clientes;
    }

    public ArrayList<Estante> getEstantes() {
        return Estantes;
    }

    public void setEstantes(ArrayList<Estante> Estantes) {
        this.Estantes = Estantes;
    }

    public ArrayList<Cajero> getCajeros() {
        return Cajeros;
    }

    public void setCajeros(ArrayList<Cajero> Cajeros) {
        this.Cajeros = Cajeros;
    }

    public ArrayList<Semaphore> getMutexEstantes() {
        return mutexEstantes;
    }

    public void setMutexEstantes(ArrayList<Semaphore> mutexEstantes) {
        this.mutexEstantes = mutexEstantes;
    }

    public ArrayList<Semaphore> getProductosEstantes() {
        return productosEstantes;
    }

    public void setProductosEstantes(ArrayList<Semaphore> productosEstantes) {
        this.productosEstantes = productosEstantes;
    }

    public ArrayList<Semaphore> getMutexEmpleados() {
        return mutexEmpleados;
    }

    public void setMutexEmpleados(ArrayList<Semaphore> mutexEmpleados) {
        this.mutexEmpleados = mutexEmpleados;
    }

    public ArrayList<Semaphore> getMutexCajeros() {
        return mutexCajeros;
    }

    public void setMutexCajeros(ArrayList<Semaphore> mutexCajeros) {
        this.mutexCajeros = mutexCajeros;
    }

    public ArrayList<Semaphore> getProductosCajeros() {
        return productosCajeros;
    }

    public void setProductosCajeros(ArrayList<Semaphore> productosCajeros) {
        this.productosCajeros = productosCajeros;
    }

    public Interfaz getInterfaz() {
        return interfaz;
    }

    public void setInterfaz(Interfaz interfaz) {
        this.interfaz = interfaz;
    }

   
}
