# Estructura inicial

Cuando se crea un archivo vacío, el archivo contendrá la siguiente información pasada a notación hexadecimal:

61 67 64 00 00 06 00

Los primeros tres Bytes corresponden a la marca de inicio "agd"

Los siguientes tres Bytes indican en qué posición se encuentra el índice, en éste caso es "00 00 06" que significa que se encuentra en la posición 6 del archivo.

Al mover el puntero del archivo a la posición 6 leemos un byte y obtendremos el número 0, lo que nos indica que no tiene ningún dato.
