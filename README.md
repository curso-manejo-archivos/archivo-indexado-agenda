# Archivos Indexados (Agenda)

Esta es un ejemplo básico de cómo crear y obtener información de un archivo indexado básico.

La programación se realiza de una manera simple, de tal manera que el estudiante pueda comprender cómo se realiza un almacenamiento y una lectura de datos.

La estructura basica del proyecto puede ser utilizada para crear archivos multillave o incluss archivos inversos.

Por motivos de aprendizaje se trabajará con datos muy básicos, dejando al estudiante una idea básica de cómo se almacenan éste tipo de archivos, por lo que no se implementan mecanismos para detectar apellidos repetidos, eliminar datos o actualizar información.

Los datos que serán almacenados son:

* Nombres
* Apellidos
* Número de Teléfono (8 dígitos)
* Dirección

La indexación se realiza mediante un solo Apellido, por lo que las búsquedas solo se realizan por éste último, el cual será el campo que se almacenará como índice del archivo.

# Documentación

La documentación completa de cómo está estructurado el archivo de salida se puede encontrar en los siguientes enlaces:

* [Estructura](doc/Estructura.md)

# Licencia

Copyright (C) 2015 Dhaby Xiloj <dhabyx@gmail.com>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
