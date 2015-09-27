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
public class Registro {
    private String nombres;
    private String apellidos;
    private String telefono;
    private String direccion;
    
    public Registro() {
        this.nombres = new String();
        this.apellidos = new String();
        this.telefono = new String();
        this.direccion = new String();
    }
    
    public Registro(String nombres, String apellidos, String telefono, String direccion) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.telefono = (telefono.length()<=8)?telefono:telefono.substring(0, 8);
        this.direccion = direccion;
    }
    
    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = (telefono.length()<=8)?telefono:telefono.substring(0, 8);
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Nombres: "+nombres+
                "\nApellidos: "+apellidos+
                "\nTelefono: "+telefono+
                "\nDirección: "+direccion;
    }
    
    
}
