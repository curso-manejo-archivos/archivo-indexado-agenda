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
 * Clase que permite el almacenamiento de un solo Registro de datos en la Agenda.
 * 
 * La estructura de cada registro se puede entender mejor consultando la documentación
 * en el archivo doc/Estructura.md del proyecto.
 * 
 * 
 * @author Dhaby Xiloj <dhabyx@gmail.com>
 */
public class Registro {
    private String nombres;
    private String apellidos;
    private String telefono;
    private String direccion;
    
    /**
     * Constructor base, solamente inicia los datos a cadenas vacías.
     * 
     * Ultil solamente cuando se desea crear un registro para búsquedas o
     * creación de listas limpias.
     */
    public Registro() {
        this.nombres = new String();
        this.apellidos = new String();
        this.telefono = new String();
        this.direccion = new String();
    }
    
    /**
     * Permite la creación de un registro con todos los datos necesarios,
     * 
     * El número de teléfono siempre se convertirá a 8 caracteres, si tiene
     * mas se eliminan, si tiene menos números se rellena con 0's al momento
     * de almacenarse.
     * 
     * Las cadenas de texto se permiten hasta una longitud de 255 caracteres,
     * aunque actualmente no se valida su longitud.
     * 
     * @param nombres de la persona
     * @param apellidos de la persona
     * @param telefono de 8 caracteres
     * @param direccion de la persona
     */
    public Registro(String nombres, String apellidos, String telefono, String direccion) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = (telefono.length()<=8)?telefono:telefono.substring(0, 8);
        this.direccion = direccion;
    }
    
    /**
     *
     * @return
     */
    public String getNombres() {
        return nombres;
    }

    /**
     *
     * @param nombres
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     *
     * @return
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     *
     * @param apellidos
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     *
     * @return
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     *
     * @param telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = (telefono.length()<=8)?telefono:telefono.substring(0, 8);
    }

    /**
     *
     * @return
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     *
     * @param direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Metodo implementado para cuando se utilice una escritura rápida del contenido
     * del registro.
     * @return 
     */
    @Override
    public String toString() {
        return "Nombres: "+nombres+
                "\nApellidos: "+apellidos+
                "\nTelefono: "+telefono+
                "\nDirección: "+direccion;
    }
    
    
}
