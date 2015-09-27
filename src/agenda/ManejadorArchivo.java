/*
 * Copyright (C) 2015 Dhaby Xiloj <dhabyx@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package agenda;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Administra el almacenamiento y lectura de información.
 * 
 * La clase no maneja la dirección donde se almacena la información, solamente 
 * provee los métodos necesarios para accedera a los datos.
 * 
 * Todos los métodos en ésta clase dependen de que el objeto {@code RandomAccesFile}
 * ya esté posicionado donde deba guardar o leer información.
 * 
 * @author Dhaby Xiloj <dhabyx@gmail.com>
 */
class ManejadorArchivo {
    /**
     * Escribe un registro {@code r} en un archivo {@code f}.
     * 
     * El archivo ya debe de encontrarse en la posición donde se escribirán los datos.
     * 
     * La estructura es la de un registro, se puede ver la documentación en el
     * archivo doc/Estructura.md del proyecto.
     * 
     * @param r Objeto {@link Registro} a almacenar
     * @param f Archivo de acceso aleatorio donde se almacenará la información
     * @throws IOException 
     */
    public static void escribirRegistro(Registro r, RandomAccessFile f) throws IOException {
        byte []longitudNombre = {(byte) r.getNombres().length()};
        byte []longitudApellido = {(byte) r.getApellidos().length()};
        byte []longitudDireccion = {(byte) r.getDireccion().length()};
        
        f.write(longitudNombre);
        f.writeBytes(r.getNombres());
        f.write(longitudApellido);
        f.writeBytes(r.getApellidos());
        f.writeBytes(String.format("%8s", r.getTelefono()).replace(' ', '0'));
        f.write(longitudDireccion);
        f.writeBytes(r.getDireccion());
    }
    
    /**
     * Lee un registro de un archivo {@code f}
     * 
     * El archivo debe de estar posicionado donde se desea leer la información.
     * 
     * La estructura es la de un registro, se puede ver la documentación en el
     * archivo doc/Estructura.md del proyecto.
     * 
     * @param f Archivo de donde se obtendrá la información
     * @return Registro con la información obtenida del archivo
     * @throws IOException
     * @throws ArchivoNoValidoException en caso de encontrar un error de lectura
     */
    public static Registro leerRegistro(RandomAccessFile f) throws IOException, ArchivoNoValidoException {
        Registro r;
        
        // se separa la información por campo, ésto puede realizarse luego por medio
        // de un ciclo for o while. Se deja aislado para comprenderlo mejor.
        
        // nombre
        byte []nombre = null;
        int longitudNombre;
        longitudNombre = Conversion.unsignedByteAInt(f.readByte());
        if (longitudNombre > 0) {
            nombre = new byte[longitudNombre];
            f.read(nombre);
        } else 
            throw new ArchivoNoValidoException("Estructura de registro inválida");
        
        // apellido
        byte []apellido = null;
        int longitudApellido;
        longitudApellido = Conversion.unsignedByteAInt(f.readByte());
        if (longitudApellido > 0) {
            apellido = new byte[longitudApellido];
            f.read(apellido);
        } else 
            throw new ArchivoNoValidoException("Estructura de registro inválida");
        
        // telefono
        // es el único elemento distinto al resto de campos
        byte []telefono = new byte[8];
        f.read(telefono);
        
        // dirección
        byte []direccion = null;
        int longitudDireccion;
        longitudDireccion = Conversion.unsignedByteAInt(f.readByte());
        if (longitudDireccion > 0) {
            direccion = new byte[longitudDireccion];
            f.read(direccion);
        } else 
            throw new ArchivoNoValidoException("Estructura de registro inválida");
               
        r = new Registro(
                new String(nombre), 
                new String(apellido), 
                new String(telefono),
                new String(direccion));
        return r;
    }
    
    /**
     * Guarda la información de una sola referencia {@code r} en un archivo {@code f}.
     * 
     * El archivo ya debe de encontrarse en la posición donde se almacenará 
     * la referencia
     * 
     * La estructura es la de una referencia, se puede ver la documentación en el
     * archivo doc/Estructura.md del proyecto.
     * 
     * @param r Referencia de índice
     * @param f Archivo donde se almacenará la información
     * @throws IOException 
     */
    public static void escribirReferenciaIndice(Referencia r, RandomAccessFile f) throws IOException{
        byte []longitudApellido = {(byte) r.getApellido().length()};
        
        f.write(Conversion.deLongA3Bytes(r.getPosicion()));
        f.write(longitudApellido);
        f.writeBytes(r.getApellido());
    }
    
    /**
     * Obtiene una sola referencia de un archivo {@code f}.
     * 
     * El archivo ya debe de encontrarse en la posición donde se desea leer una
     * referencia.
     * 
     * La estructura es la de una referencia, se puede ver la documentación en el
     * archivo doc/Estructura.md del proyecto.
     * 
     * @param f Archivo de donde se obtiene la información
     * @return Referencia
     * @throws IOException
     * @throws ArrayIndexOutOfBoundsException 
     */
    public static Referencia leerReferenciaIndice(RandomAccessFile f) throws IOException, ArrayIndexOutOfBoundsException {
        Referencia r;
        byte []apellido;        
        byte []posicion = new byte[3];
        
        f.read(posicion);
        int longitudApellido;
        
        // se le aplica un AND lógico para evitar tomar el signo.
        // vea la documentación de la clase Conversion
        longitudApellido = Conversion.unsignedByteAInt(f.readByte());
        if (longitudApellido > 0) {
            apellido = new byte[longitudApellido];
            f.read(apellido);
        }
        else
            throw new IOException("La estructura del archivo esta dañada o no es un archivo de agenda");
        r = new Referencia(new String(apellido), Conversion.de3BytesAInt(posicion));
        return r;
    }
}
