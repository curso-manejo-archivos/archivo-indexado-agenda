# Estructura del archivo

## Estructura general del archivo

Campo       | Tipo          | Tamaño (Bytes)
------------| ------------- | --------------
Marca de archivo | Caracter | 3
Localización de Indice | Entero | 3
Registros de datos | Variable | n
Indice | Variable | n

### Estructura de cada Registro

Campo | Tipo | Tamaño (Bytes)
------| -----| --------------
Longitud de Nombre | Entero | 1
Nombre | Caracter | n
Longitud de Apellidos | Entero | 1
Apellidos | Caracter | n
Número de teléfono | Caracter | 8
Longitud de Dirección | Entero | 1
Dirección | Caracter | n

## Estructura de Indice

Campo | Tipo | Tamaño (Bytes)
------| -----| --------------
Cantidad de registros | Entero | 3
Referencia | Variable | n


### Estructura de cada Referencia de Indice

Campo | Tipo | Tamaño (Bytes)
------| -----| --------------
Posición | Entero | 3
Longitud de Apellido | Entero | 1
Apellido | Caracter | n


# Estructura de un archivo sin registros

Cuando se crea un archivo vacío, el archivo contendrá la siguiente información pasada a notación hexadecimal:

61 67 64 00 00 06 00 00 00

Los primeros tres Bytes corresponden a la marca de inicio "agd"

Los siguientes tres Bytes indican en qué posición se encuentra el índice, en éste caso es "00 00 06" que significa que se encuentra en la posición 6 del archivo.

Al mover el puntero del archivo a la posición 6 leemos tres bytes y obtendremos el número 0, lo que nos indica que no tiene ningún dato.
