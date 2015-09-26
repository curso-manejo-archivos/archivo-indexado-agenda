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
public class ManejadorArchivo {
    public static void escribirRegistro(Registro r, RandomAccessFile f) throws IOException {
        byte []longitudNombre = {(byte) r.getNombres().length()};
        byte []longitudApellido = {(byte) r.getApellidos().length()};
        byte []longitudDireccion = {(byte) r.getDireccion().length()};
        
        f.write(longitudNombre);
        f.writeBytes(r.getNombres());
        f.write(longitudApellido);
        f.writeBytes(r.getApellidos());
        f.writeBytes(r.getTelefono());
        f.write(longitudDireccion);
        f.writeBytes(r.getDireccion());
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
