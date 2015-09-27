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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Dhaby Xiloj <dhabyx@gmail.com>
 */
public class Agenda {
    
    /**
     *
     */
    public Archivo archivo;
    private static final String nombreArchivo ="test.data";
    
    public Agenda() throws IOException, FileNotFoundException, ArchivoNoValidoException {
        archivo = new Archivo(nombreArchivo);
    }
    
    public void agregarDatos() throws IOException{
        archivo.agregarRegistro(
                new Registro("Juan Ernesto",
                "Fuentes Fulano",
                "12314",
                "Ciudad"));
        archivo.agregarRegistro(
                new Registro("Juan Fulano",
                "Mendez Fulano",
                "23423",
                "Ciudad"));
        archivo.agregarRegistro(
                new Registro("Pedro",
                "Fulano Ramos",
                "34234",
                "Ciudad"));
        archivo.agregarRegistro(
                new Registro("Luis",
                "Gutierrez",
                "432422132132",
                "Ciudad"));
        archivo.escribirDatos();
    }
    
    /**
     *
     * @throws java.io.IOException
     */
    public void imprimirIndice() throws IOException {
        archivo.leerIndice();
        System.out.println();
        System.out.println("Indice encontrado:");
        System.out.println("Apellido \t Posición en archivo");
        archivo.getIndice().stream().forEach((Referencia r) -> {
            System.out.println(String.format("%10s", r.getApellido())+" \t "+
                    String.format("0x%2s", Integer.toHexString((int) r.getPosicion())).replace(' ', '0'));
        });
    }

    public void buscar(String nombre) throws IOException, ArchivoNoValidoException{
        Registro persona = archivo.buscarPersona(nombre);
        System.out.println();
        if (persona == null) {
            System.out.println("Apellido de persona no encontrada");
            return;
        }
        System.out.println("Persona buscada");
        System.out.println(persona);
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Agenda a = new Agenda();
            a.agregarDatos();                       
            a.imprimirIndice();
            a.buscar("Mendez");
        } catch (IOException | ArchivoNoValidoException ex) {
            Logger.getLogger(Agenda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}