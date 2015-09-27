# Estructura del archivo

## Estructura general del archivo

La estructura general del archivo se ve de la siguiente manera:

Campo                  | Tipo          | Tamaño (Bytes)
-----------------------| ------------- | --------------
Marca de archivo       | Caracter      | 3
Localización de Indice | Entero        | 3
Registros de datos     | Variable      | n
Indice                 | Variable      | n

* **Marca de archivo**: siempre almacenará los caracteres "agd" al inicio del archivo.
* **Localización de Indice**: contiene un valor entero sin signo de 3 Bytes que indica el desplazamiento (offset) donde inicia la estructura del índice. El desplazamiento siempre se mide desde el inicio del archivo.
* **Registros de datos**: En ésta sección se almacenan los registros de cada contacto.
* **Indice**: Al final del archivo se coloca la estructura del índice.

El tamaño *n* que se ve en la tabla, indica que no se sabe el tamaño que pueda tener, ya que éstas secciones del archivo son variables, según la cantidad de datos que se almacenen.

### Estructura de cada Registro

La estructura de cada registro (contacto) que se almacena en el documento tiene la siguiente estructura:

Campo                   | Tipo          | Tamaño (Bytes)
------------------------| ------------- | --------------
Longitud de Nombres     | Entero        | 1
Nombres                 | Caracter      | n
Longitud de Apellidos   | Entero        | 1
Apellidos               | Caracter      | n
Número de teléfono      | Caracter      | 8
Longitud de Dirección   | Entero        | 1
Dirección               | Caracter      | n

Al haber varios contactos, éstos se guardan secuencialmente, uno tras otro.

Los campos que inician con la palabra **Longitud** almacenan la longitud del campo que le sigue, así por ejemplo si el campo **Nombre** tiene asignado el valor "Pedro" el campo **Longitud de Nombre** deberá tener el valor de *5*.

La función de los campos que inician con **Longitud** es indicarnos cuantos caracteres tienen los campos de caracteres que le sigen. Al ser te tamaño de 1 Byte, solo se permitirán cadenas de un máximo de 255 caracteres.

El campo **Número de teléfono** siempre tendrá una longitud de 8 caracteres, si un número tiene menos números, se debe rellenar con 0's a la izquierda.

## Estructura de Indice

El índice esta compuesto por dos campos, el primero **Cantidad de registros**, el cual es un numero entero sin signo de 3 Bytes que almacena la cantidad de registros que están guardados en el índice. Sabiendo que el índice almacena la misma cantidad de campos que los registros de datos, este campo también se utiliza para saber cuantos contactos hay almacenados en el archivo.

La sección de **Referencia**, contiene una lista de referencias que apuntan a su respectivo registro de contacto.

Campo                   | Tipo          | Tamaño (Bytes)
------------------------| ------------- | --------------
Cantidad de registros   | Entero        | 3
Referencia              | Variable      | n


### Estructura de cada Referencia de Indice

Cada **Referencia** indicada en la sección anterior tiene ésta estructura:

Campo                   | Tipo          | Tamaño (Bytes)
------------------------| ------------- | --------------
Posición                | Entero        | 3
Longitud de Apellido    | Entero        | 1
Apellido                | Caracter      | n

El campo **Posición** almacena la posición donde se encuentra el registro con la información del contacto con el primer apellido igual al almacenado en el campo **Apellido**.

La *posición* siempre es medida desde el inicio del archivo.


# Estructura de un archivo sin registros

Cuando se crea un archivo vacío, el archivo contendrá la siguiente información pasada a notación hexadecimal:

61 67 64 00 00 06 00 00 00

Los primeros tres Bytes corresponden a la marca de inicio "agd"

Los siguientes tres Bytes indican en qué posición se encuentra el índice, en éste caso es "00 00 06" que significa que se encuentra en la posición 6 del archivo.

Al mover el puntero del archivo a la posición 6 leemos tres bytes y obtendremos el número 0, lo que nos indica que no tiene ningún dato.
