# UNIMET | Sistemas operativos | Proyecto 1

Ante la emergencia del COVID-19, la cadena de supermercados Gama Express ha
invertido en el desarrollo de un sistema que les permita simular la interacción de sus clientes
con los recursos disponibles en una de sus sucursales. Usted a sido designado para llevar acabo
dicha solicitud, la cual debe programarse en el lenguaje de Java. El programa debe contener el
uso de hilos, semáforos y las soluciones conocidas al problema del Productor/Consumidor y
Escritos/Lector.

El funcionamiento de la sucursal se entiende en 3 fases, llegada, proceso interno y salida.
Finalmente, la cadena Gama ha proporcionado una serie de valores y reglamentos
necesarios para poder llevar acabo el programa:

 Los clientes llegan cada cinco minutos al local.
 El número de carritos a disposición son 10, pero puede incrementar hasta 20.
 El número de cajas registradoras trabajando son 4, pero puede variar.
 El programa debe comenzar con un estante, pero con la posibilidad de agregar
hasta 3. No está permitido eliminar estantes.
 El programa debe comenzar con un empleado encargado del estante inicial y
nunca debe haber más empleados que número de estantes, ya que cada uno
será el responsable de su propio estante.
 Al agregarse un nuevo estante, se agrega automáticamente un empleado al
sistema encargado de suplirlo.
 Cada producto tiene un valor entre 1 y 10 dólares.
 Los estantes pueden albergar hasta 10 productos cada uno.

El programa deberá hacer uso de una interfaz gráfica que permita observar y controlar
el sistema. Se debe conocer en todo momento:
 El número de estantes.
 El número de clientes en el sistema.
 El número de cajas registradoras.
 El número de carritos disponibles.
 El número de clientes esperando afuera.
 El número de horas laborales cursadas.
 Las ganancias.

La simulación debe permitir, en tiempo de ejecución:
 Agregar estantes nuevos.
 Agregar o eliminar carritos.
 Contratar o despedir cajas registradoras.

Además, a través de un archivo (texto, objeto, csv o json), se le debe poder indicar al
programa los siguientes parámetros:

 Tiempo, en segundos, que dura una hora en el programa.
 Número de estantes iniciales.
 Número máximo de estantes permitidos.
 Capacidad máxima de los estantes.
 Número de cajas registradoras operando.
 Número máximo de cajas registradoras permitidas.
 Número de carritos iniciales.
 Número máximo de carritos.

Estudiantes: Pedro Barrios, Gabriela Banezca 
