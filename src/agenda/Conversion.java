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
 *
 * @author Dhaby Xiloj <dhabyx@gmail.com>
 */
public class Conversion {

    public static int de3BytesAInt(byte[] b) {
        int resultado;
        resultado = ((int)b[0] & 0xFF) << 16 | ((int)b[1] & 0xFF) << 8 | ((int)b[2] & 0xFF);
        return resultado;
    }

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
