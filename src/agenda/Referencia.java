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
class Referencia implements Comparable<Referencia> {
    private String apellido;
    private long posicion;
    
    public Referencia(String apellido, long posicion) throws NullPointerException{
       this.apellido = obtenerPrimerApellido(apellido);
       this.posicion = posicion;
    }

    public String getApellido() {
        return apellido;
    }
    
    public static String obtenerPrimerApellido(String s) throws NullPointerException{
        if (!s.isEmpty()) {
            String unApellido= s.split(" ", 2)[0];
            if (!unApellido.isEmpty())
               return unApellido;
            else
               throw new NullPointerException("No existe ningún apellido");
        }
        else
            throw new NullPointerException("No existe ningún apellido");
    }

    public void setApellido(String apellido) throws NullPointerException{
        this.apellido=obtenerPrimerApellido(apellido);
    }

    public long getPosicion() {
        return posicion;
    }

    public void setPosicion(long posicion) {
        this.posicion = posicion;
    }

    @Override
    public int compareTo(Referencia o) {
        return this.apellido.compareTo(o.getApellido());
    }
    
    
}
