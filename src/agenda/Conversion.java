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

/**
 * Clase de apoyo para conversiones comunes de datos obtenidos con cantidades de
 * bits que no maneja por defecto java.
 * 
 * @author Dhaby Xiloj <dhabyx@gmail.com>
 */
public class Conversion {
    /**
     * Convierte un byte sin signo a un entero con signo de java.
     * 
     * Al asignar un byte a un entero, java lo toma siempre como un byte con signto,
     * por lo que si el bit mas significativo está a uno, significará que es negativo,
     * por ello se debe de aplicar un AND lógico al dato de tipo Byte para que lo convierta
     * a entero antes de aplicarle la regla del signo.
     * 
     * @param b byte sin signo a convertir
     * @return entero con signo
     */
    public static int unsignedByteAInt(byte b){
        return b & 0xFF;
    }

    /**
     * Convierte un array de tres bytes a un número entero básico.
     * 
     * Si el array es mayor a 3 bytes, solo convertirá los primeros 3 bytes.
     * 
     * El numero se convierte por medio de corrimientos de bits y operaciones OR
     * lógicas.
     * 
     * @param b array de byte, debe de ser de longitud 3.
     * @return numero entero
     */
    public static int de3BytesAInt(byte[] b) throws ArrayIndexOutOfBoundsException {
        if (b.length < 3)
            throw new ArrayIndexOutOfBoundsException("El número de dígitos debe ser igual a 3");
        int resultado;
        // A la conversión se le realiza una operación AND para mantener el signo del byte.
        resultado = unsignedByteAInt(b[0]) << 16 | unsignedByteAInt(b[1]) << 8 | unsignedByteAInt(b[2]);
        return resultado;
    }

    /**
     * Convierte un numero Long en un arreglo de 3 bytes.
     * 
     * Si el número excede la cantidad de bytes, solo se toman los bits 
     * correspondientes a los tres primero bytes, tomados desde la posición mas 
     * baja del número.
     * 
     * Las operaciones utilizadas son corrimientos de bits.
     * 
     * @param l numero a convertir
     * @return array de 3 bytes.
     */
    public static byte[] deLongA3Bytes(long l) {
        byte[] resultado = new byte[3];
        resultado[0] = (byte) (l >> 16);
        resultado[1] = (byte) (l >> 8);
        resultado[2] = (byte) (l);
        /* 
        System.out.println(String.format("0x%2s", Integer.toHexString(resultado[0])).replace(' ', '0'));
        System.out.println(String.format("0x%2s", Integer.toHexString(resultado[1])).replace(' ', '0'));
        System.out.println(String.format("0x%2s", Integer.toHexString(resultado[2])).replace(' ', '0'));
        */
        return resultado;
    }
    
    
}
