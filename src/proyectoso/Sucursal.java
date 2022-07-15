/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso;

/**
 *
 * @author Jesus Barrios
 */
public class Sucursal {
    /*Clase sucursal, aqui se mandan todos los datos extraidos en la
    clase Archivo, o en otras palabras, aqui terminan guardadas
    los parametros extraidos del txt*/
    private int tiempo, //el tiempo en segundos que dura una hora en el prog
            carritosIni, //numero de carritos iniciales
            cajasIni, //numero de cajas registradoras operando inicialmente
            estantesIni,//numero de estantes iniciales
            nroEstantesMin, //numero minimo de estantes, es 1 por si lo preguntas
            nroEstantesMax, //numero maximo de estantes
            capEstantes, //capacidad maxima de los estantes
            nroCajasMin, //numero minimo de cajas, nuevamente es 1...siempre
            nroCajasMax, /*numero maximo de cajas registradoras permitidas*/
            nroCarritosMin,//numero minimo de carritos, nuevamente 1
            nroCarritosMax;//numero maximo de carritos
    
   
    
    //Sucursal creada por datos pasados por parametro desde Archivo
    Sucursal(int tiempo,int carritosIni, int estantesIni, 
            int cajasIni,int nroCarritosMax,int nroEstantesMax,
            int nroCajasMax, int capEstantes){
        this.tiempo = tiempo;
        this.carritosIni = carritosIni;
        this.estantesIni = estantesIni;
        this.cajasIni = cajasIni;
        this.nroEstantesMin = 1;
        this.nroEstantesMax = nroEstantesMax;
        this.capEstantes = capEstantes;
        this.nroCajasMin = 1;
        this.nroCajasMax = nroCajasMax;
        this.nroCarritosMin = 1;
        this.nroCarritosMax = nroCarritosMax;
    }
    
    /*Sucursal con valores por defecto en caso de falla...decir eso no es
    redundante? osea decir valores por defecto en caso de falla no es redundante
    o soy yo?*/
    Sucursal(){
        this.tiempo = 60;
        this.carritosIni = 10;
        this.estantesIni = 1;
        this.cajasIni = 4;
        this.nroEstantesMin = 1;
        this.nroEstantesMax = 3;
        this.capEstantes = 10;
        this.nroCajasMin = 1;
        this.nroCajasMax = 7;
        this.nroCarritosMin = 1;
        this.nroCarritosMax = 20;
    }
    
    
    
    
    
    //getters y setters
    public int getTiempo() {
        return tiempo;
    }

    public int getCarritosIni() {
        return carritosIni;
    }

    public int getCajasIni() {
        return cajasIni;
    }

    public int getEstantesIni() {
        return estantesIni;
    }

    public int getNroEstantesMin() {
        return nroEstantesMin;
    }

    public int getNroEstantesMax() {
        return nroEstantesMax;
    }

    public int getCapEstantes() {
        return capEstantes;
    }

    public int getNroCajasMin() {
        return nroCajasMin;
    }

    public int getNroCajasMax() {
        return nroCajasMax;
    }

    public int getNroCarritosMin() {
        return nroCarritosMin;
    }

    public int getNroCarritosMax() {
        return nroCarritosMax;
    }
    
    
    
    
    
    
    
}
