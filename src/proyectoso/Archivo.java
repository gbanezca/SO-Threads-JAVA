/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectoso;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Jesus Barrios
 */
public class Archivo {
    //Variables
    private String nombreArchivo;
    private String line;
    int aux, cont;
    int[]dataSuc;    
    private boolean check;
    
    //Las librerias que nos van a ayudar a acceder al archivo para
    //leerlo o reescribirlo
    FileReader fileReader;
    BufferedReader bufferedReader;
    FileWriter fileWriter;
    BufferedWriter bufferedWriter;
    Sucursal sucursal;
    
    /*Como lee el archivo el txt? facil, va linea por linea
    y si detecta que una linea comienza con "-", salta a la siguiente
    y agarra el dato que esta alli, ahora, no es que lee el nombre de
    lo que esta alli(ej: tiempo=70*) y se lo asigna a la
    variable correspondiente,nonono, el txt tiene que estar en un
    orden especifico para poder trabajar, de lo contrario no se puede estar
    seguro del funcionamiento del programa. A proposito el orden que debe 
    tener el archivo es:
        tiempo
        carritosIni
        estantesIni
        cajas
        maxCarritos
        maxEstantes
        maxCajas
        capEstantes
    Deberia poner algo en el readme de que si algo falla el usuario tiene que
    reescribir el txt con ese orden...o simplemente pongo una copia del settings
    en el readme*/
    
    
    
    /**
     * Metodo que carga los datos del archivo de texto de nombre
     * settings.txt
     * @return objeto Sucursal 
     */
    public Sucursal cargarData(){
        //El array donde vamos a guardas los datos modificables del programa
        //para despues pasarselos a la clase Sucursal
        dataSuc = new int [8];
        //el nombre del archivo txt, se le tiene que poner la terminacion
        nombreArchivo = "settings.txt";
        //auxiliar para ayudar a verificar que todo haya cargado bien
        cont = 0;
        //trycatch obligatorio cuando dse usa un fileReader/bufferedReader
        try {
            fileReader = new FileReader(nombreArchivo);
            bufferedReader = new BufferedReader(fileReader);
            //Revisa linea por linea hasta llegar hasta el fin
            while(!"fin".equals(line = bufferedReader.readLine())) {
                //Si la linea del txt empieza por -...
                if(line.startsWith("-")){
                    //..salta a la proxima linea para extrar uno de los datos
                    //a pasar a sucursal
                    line = bufferedReader.readLine();
                    //trycatch por si hay error
                    try {
                        //parseInt porse todo lo que se saca del txt es String
                        aux = Integer.parseInt
                            (line.substring(line.indexOf("=")+1,line.indexOf("*")));
                    } catch (Exception e) {
                        //Si algo falla se sale del while
                        break;
                    }
                    //Guardamos el dato extraido del txt en el array
                    dataSuc[cont] = aux;
                    //Sumamos a nuestro contador verificador
                    cont++;
                }
            }
            //Se cierra el bufferedReader
            bufferedReader.close();         
        }catch(FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No es posible abrir el archivo '" 
                    + nombreArchivo + "'", "NO ES POSIBLE ABRIR EL ARCHIVO", 
                    JOptionPane.ERROR_MESSAGE);
                           
        }catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo '" 
                    + nombreArchivo + "'", "NO SE PUDO LEER EL ARCHIVO", 
                    JOptionPane.ERROR_MESSAGE);
        }
        /*Y aqui la verificacion del contador
        si el contador esta en 8 todo bien, se sacaron todos los datos
        del txt sin problema. 
        En caso de haber algun problema creando la sucursal a partir de
        los datos del txt se crea una sucursal con datos por defecto*/
        if(cont==8 && verificacion(dataSuc)){
            //trycatch por si las moscas
            try {
                //System.out.println("ARCHIVO CARGADO CON EXITO");
                sucursal = new Sucursal(dataSuc[0],dataSuc[1], dataSuc[2], 
                    dataSuc[3], dataSuc[4], dataSuc[5], dataSuc[6], dataSuc[7]);
            } catch (Exception e) {
                //System.out.println("ERROR EN ARCHIVO, CARGANDO DEFAULT");
                JOptionPane.showMessageDialog(null, "Error al leer el archivo,cargando datos por defecto '" 
                    + nombreArchivo + "'", "NO SE PUDO LEER EL ARCHIVO", 
                    JOptionPane.ERROR_MESSAGE);
                sucursal = new Sucursal();
            }
        }else{
            //System.out.println("ERROR EN ARCHIVO, CARGANDO DEFAULT");
            JOptionPane.showMessageDialog(null, "Error al leer el archivo,cargando datos por defecto '" 
                    + nombreArchivo + "'", "NO SE PUDO LEER EL ARCHIVO", 
                    JOptionPane.ERROR_MESSAGE);
            sucursal = new Sucursal();
        }
            
        //Se retorna la sucursal con los datos a usar en Control
        
        return sucursal;
    }
    
    
    
    
    /*Esto en realidad no hace nada, lo tengo aqui por si
    se vuelve necesario usarlo, lo cual dudo*/
    /**
     * @deprecated 
     */
    public void escribirArchivo(){
        nombreArchivo = "settings.txt";
        try {
            fileWriter =new FileWriter(nombreArchivo,false);
            bufferedWriter = new BufferedWriter(fileWriter);
            
            
            
            
            bufferedWriter.close();
        }
       
        catch(FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "No es posible abrir el archivo '" 
                    + nombreArchivo + "'", "NO ES POSIBLE ABRIR EL ARCHIVO", 
                    JOptionPane.ERROR_MESSAGE);               
        }
        catch(IOException ex) {
            JOptionPane.showMessageDialog(null, "Error al leer/escribir el archivo '" 
                    + nombreArchivo + "'", "NO SE PUDO LEER EL ARCHIVO", JOptionPane.ERROR_MESSAGE);                
            
        }
        
    }
    
    
    public boolean verificacion(int [] data){
        check = true;
            for (int i = 0; i < data.length-1; i++) {
                if(data[i]<1){
                    check=false;
                    break;
                }
            }
            if(check!=false){
                if(data[data.length-1]<3)check = false;
                if(data[1]>data[4])check=false;
                if(data[2]>data[5])check=false;
                if(data[3]>data[6])check=false;
            }
        return check;
    }
    
    
}
