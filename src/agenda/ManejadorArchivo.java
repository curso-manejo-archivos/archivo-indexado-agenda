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
 *
 * @author Dhaby Xiloj <dhabyx@gmail.com>
 */
class ManejadorArchivo {
    public static void escribirRegistro(Registro r, RandomAccessFile f) throws IOException {
        byte []longitudNombre = {(byte) r.getNombres().length()};
        byte []longitudApellido = {(byte) r.getApellidos().length()};
        byte []longitudDireccion = {(byte) r.getDireccion().length()};
        
        f.write(longitudNombre);
        f.writeBytes(r.getNombres());
        f.write(longitudApellido);
        f.writeBytes(r.getApellidos());
        f.writeBytes(String.format("%8s", r.getTelefono()));
        f.write(longitudDireccion);
        f.writeBytes(r.getDireccion());
    }
    
    public static Registro leerRegistro(RandomAccessFile f) throws IOException, ArchivoNoValidoException {
        Registro r;
        
        // se separa la información por campo, ésto puede realizarse luego por medio
        // de un ciclo for o while.
        
        // nombre
        byte []nombre = null;
        int longitudNombre;
        longitudNombre = f.readByte() & 0xFF;
        if (longitudNombre > 0) {
            nombre = new byte[longitudNombre];
            f.read(nombre);
        } else 
            throw new ArchivoNoValidoException("Estructura de registro inválida");
        
        // apellido
        byte []apellido = null;
        int longitudApellido;
        longitudApellido = f.readByte() & 0xFF;
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
        longitudDireccion = f.readByte() & 0xFF;
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
    
    public static void escribirReferenciaIndice(Referencia r, RandomAccessFile f) throws IOException{
        byte []longitudApellido = {(byte) r.getApellido().length()};
        
        f.write(Conversion.deLongA3Bytes(r.getPosicion()));
        f.write(longitudApellido);
        f.writeBytes(r.getApellido());
    }
    
    public static Referencia leerReferenciaIndice(RandomAccessFile f) throws IOException {
        Referencia r;
        byte []apellido;        
        byte []posicion = new byte[3];
        
        f.read(posicion);
        int longitudApellido;
        longitudApellido = f.readByte() & 0xFF;
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
