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

import java.util.Objects;

/**
 * Clase utilizada para almacenar datos que irán en el índice, cada campo almacenado
 * en el índice se considera una referencia.
 * 
 * La estructura la puede encontrar en el archivo doc/Estructura.md en la carpeta
 * del proyecto.
 * 
 * @author Dhaby Xiloj <dhabyx@gmail.com>
 */
public class Referencia implements Comparable<Referencia> {
    private String apellido;
    private long posicion;
    
    /**
     * Constructor sobrecargado, utilizado generalmente para la realización de
     * búsqedas.
     * 
     * @param apellido
     */
    public Referencia(String apellido) {
        this(apellido,0l);
    }
    
    /**
     * Constructor principal, debe de recibir siempre un apellido y la posicoón
     * donde se encuentra el registro.
     * 
     * Las referencias deben de ir creandose en cuanto el programador sepa
     * en que parte ha quedado un registro.
     * 
     * El desplazamiento {@code posicion} debe ser desde el inicio del archivo.
     * 
     * @param apellido de la persona
     * @param posicion desplazamiento del registro.
     * @throws NullPointerException
     */
    public Referencia(String apellido, long posicion) throws NullPointerException{
       this.apellido = obtenerPrimerApellido(apellido);
       this.posicion = posicion;
    }

    /**
     * Devuelve el apellido
     * @return apellido
     */
    public String getApellido() {
        return apellido;
    }
    
    /**
     * Metodo para filtrar solamente un apellido de una cadena
     * 
     * En ocasiones se guardarán mas de un apellido, por lo que éste método
     * se asegura de obtener solo uno, separando los apellidos por espacio y
     * obteniendo solamente el primero.
     * 
     * @param s cadena con los apellidos
     * @return un solo apellido
     * @throws NullPointerException
     */
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

    /**
     * Almacena un apellido, en caso de querer cambiar el contenido del mismo.
     * 
     * @param apellido
     * @throws NullPointerException
     */
    public void setApellido(String apellido) throws NullPointerException{
        this.apellido=obtenerPrimerApellido(apellido);
    }

    /**
     * Obtiene la posición donde se almacena el registro con los datos.
     * 
     * El desplazamiento siempre es medido desde el inicio del archivo.
     * @return desplazamiento
     */
    public long getPosicion() {
        return posicion;
    }

    /**
     * Almacena la posición del registro con los datos
     * 
     * El desplazamiento debe ser siempre desde el inicio del archivo.
     * 
     * @param posicion
     */
    public void setPosicion(long posicion) {
        this.posicion = posicion;
    }

    /**
     * Método sobre-escrito para permitir realizar comparaciones entre los datos
     * y de ésta manera poder utilizar la búsqueda implementada en un ArrayList
     * 
     * @see java.util.ArrayList#indexOf(java.lang.Object) 
     * 
     * @param o clase Referencia a utilizar para comparar.
     * @return 
     */
    @Override
    public int compareTo(Referencia o) {
        return this.apellido.compareTo(o.getApellido());
    }

    /**
     * Metodo sobre-escrito para permitir verificar que elemento es mas pequeño o 
     * grande que otro, generalmente utilizado en los métodos de ordenamiento
     * de ArrayLists.
     * 
     * @see java.util.ArrayList#sort(java.util.Comparator) 
     * @param o Objeto a comparar
     * @return -1 si es menor; 0 si son iguales; 1 si es mayor.
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Referencia) {
            return this.apellido.equals(((Referencia)o).getApellido());
        }            
        else if (o instanceof String) {
            return this.apellido.equals((String)o);
        }
        else
            return false;
    }
    
    /**
     * Método utilizado en algunos casos para crear campos hash e identificar
     * a cada elemento, utilizado en algunos casos para ordenar y para búsquedas.
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode(this.apellido);
        return hash;
    }
}
