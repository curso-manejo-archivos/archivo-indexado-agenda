# Archivos Indexados (Agenda)

Esta es un ejemplo básico de cómo crear y obtener información de un archivo indexado básico.

La estructura puede ser utilizada para crear archivos multillave o incluos archivos inversos.

Por motivos educacionales solamente se trabajará con datos muy básicos, dejando al estudiante la idea de cómo se almacenan éste tipo de archivos; los datos que se almacenaran son:

* Nombres
* Apellidos
* Número de Teléfono (8 dígitos)
* Dirección

La indexación se realiza mediante Apellidos.

## Estructura del archivo

### Estructura general del archivo

Campo       | Tipo          | Tamaño (Bytes)
------------| ------------- | --------------
Marca de archivo | Caracter | 3
Localización de Indice | Entero | 3
Registros de datos | Variable | n
Indice | Variable | n

### Estructura de cada Registros

Campo | Tipo | Tamaño (Bytes)
------| -----| --------------
Longitud de Nombre | Entero | 1
Nombre | Caracter | n
Longitud de Apellidos | Entero | 1
Apellidos | Caracter | n
Número de teléfono | Caracter | 8
Longitud de Dirección | Entero | 1
Dirección | Caracter | n

### Estructura de Indice

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
