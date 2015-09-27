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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Esta clase se utiliza para cargar verificar y manipular información de
 * los archivos que contengan información en estructura de una agenda.
 * 
 * @author Dhaby Xiloj <dhabyx@gmail.com>
 */
public class Archivo {
    /**
     * Mantiene el nombre del archivo a lo largo de toda la clase
     */
    private final String nombreArchivo;
    
    /**
     * La marca de Inicio, es la marca que tendrán los archivos pertenecientes a 
     * esta aplicación, ésta marca debe ir al inicio del documento.
     * El método validarArchivo se encarga de verificar que el archivo cumpla
     * con los datos, de lo contrario no es considerado un documento válido
     * para ésta aplicación.
     */
    private final String marcaDeInicio = "agd";
    
    /**
     * Mantendrá la estructura interna del archivo, tanto para lectura como
     * para escritura.
     */    
    private RandomAccessFile estructura;
    
    /**
     * registros es una propiedad que mantendrá una lista de elementos de tipo
     * Registro, utilizada para mantener en memoria los datos que deben ser
     * almacenados en el archivo.
     * 
     * @see Registro
     */
    private final ArrayList<Registro> registros;
    
    /**
     * indice mantiene una lista de elementos de tipo Referencia, que permiten 
     * almacenar información que se utilizará para generar un índice de datos
     * contenidos en el documento.
     */
    private final ArrayList<Referencia> indice;
    
    /**
     * Almacena el número de registros que se van creando o leyendo de un 
     * documento
     */
    
    private int numeroDeRegistros;
    
    /**
     * El constructor de Archivo necesita el nombre del archivo, el cual
     * será utilizado para almacenar o cargar información.
     * 
     * @param ruta relativa o absoluta hacia el archivo
     * @throws IOException cuando encuentre un problema al abrir el archivo
     * @throws FileNotFoundException cuando no se encuentre el archivo a leer
     * @throws ArchivoNoValidoException cuando el archivo no tenga el formato adecuado
     */
    public Archivo(String ruta) throws IOException, FileNotFoundException, ArchivoNoValidoException {
        registros = new ArrayList();
        indice = new ArrayList();
        if (ruta.isEmpty())
            throw new IOException("No se puede crear un objeto Archivo sin un nombre de archivo");
        nombreArchivo = ruta;
        validarArchivo();
    }
       
    /**
     * Utilizada para validar la estructura del archivo que se intenta leer.
     * 
     * Si el archivo no existe, se crea un nuevo archivo con el nombre
     * contenido en la propiedad {@code nombreArchivo}.
     * 
     * Si el archivo existe, se verifica la marca inicial, si coincide el archivo
     * queda abierto para edición o lectura.
     * 
     * @return true si ha logrado abrirse adecuadamente
     * @throws FileNotFoundException No se puede localizar el nombre de archivo
     * @throws IOException Cuando se tiene problemas en almacenar o leer información
     * @throws ArchivoNoValidoException al detectar que el archivo no tiene el formato adecuado
     */
    public final boolean validarArchivo() throws FileNotFoundException, IOException, ArchivoNoValidoException{
        File archivo;
        archivo = new File(nombreArchivo);
        if (! archivo.exists()) {
            estructura = new RandomAccessFile(nombreArchivo, "rw");
            // marca inicial de archivo
            estructura.writeBytes(marcaDeInicio);
            // el índice comienza despues de colocar la localización del índice,
            // ésto es después de 3 bytes de la marca de inicio
            estructura.write(Conversion.deLongA3Bytes(estructura.getFilePointer()+3));
            byte []cantidadDatos = {0,0,0};
            estructura.write(cantidadDatos);
            
            // forzamos la escritura al archivo.
            estructura.getFD().sync();
        } 
        else {
            estructura = new RandomAccessFile(nombreArchivo, "rw");
            byte []marca = new byte[marcaDeInicio.length()];
            estructura.read(marca);
            if (! marcaDeInicio.equals(new String(marca))){
                throw new ArchivoNoValidoException();
            }
        }
        return true;
    }
    
     /**
     * Mueve el puntero del archivo a la posición donde se encuentre el índice
     * 
     * @return la posisición, en Bytes, donde se encuentra el índice
     * @throws java.io.IOException
     */
    private long moverAIndice() throws IOException {
        // movemos el puntero hacia la posición donde está almacenada la posición
        // del índice en el archivo
        estructura.seek(3);
   
        byte []b = new byte[3];
        estructura.read(b);
        int posIndice = Conversion.de3BytesAInt(b);
        //System.out.println(String.format("0x%2s", Integer.toHexString(posIndice)).replace(' ', '0'));
        
        estructura.seek(posIndice);
        return posIndice;
    }
    
    /**
     * Devuelve la cantidad de registros que tiene el archivo, leyendo el campo
     * asignado en el archivo para almacenar ésta información, localizado en la
     * sección del índice.
     * 
     * Al llamar a éste método, el puntero se deja siempre al inicio del índice,
     * por lo que cualquier lectura que se haga luego de éste metodo obtendrá los 
     * datos que se encuentran en el índice.
     * 
     * @return número de registros que contiene el archivo
     * @throws java.io.IOException en caso de no poder leer los datos
     */
    public int contarRegistros() throws IOException{
        moverAIndice();
        byte []b = new byte[3];
        estructura.read(b);
        return Conversion.de3BytesAInt(b);
    }
    
    /**
     * Agrega a la propiedad {@code indice} las referencias que tiene el índice
     * leyendo del archivo.
     * 
     * Este método hace uso del método {@link #contarRegistros() }, por lo que 
     * no hay necesidad de mover el puntero al inicio del índice y se procede a 
     * obtener la información mediante un método estático creado en la clase 
     * {@link ManejadorArchivo#leerReferenciaIndice(java.io.RandomAccessFile) }
     * 
     * Al ejecutar éste método, el puntero queda al final del archivo.
     * 
     * @see ManejadorArchivo
     * 
     * @throws IOException
     */
    public void leerIndice() throws IOException{
        int contador = contarRegistros();
        indice.clear();
        if (contador > 0)
            for (int i = 0; i < contador; i++ ) {
                indice.add(ManejadorArchivo.leerReferenciaIndice(estructura));
            }
    }
    
    public Registro buscarPersona(String apellido) throws IOException, ArchivoNoValidoException {
        Registro r = null;
        leerIndice();
        int indiceReferencia = indice.indexOf(new Referencia(apellido));
        if (indiceReferencia >= 0) {
            Referencia ref = indice.get(indiceReferencia);
            estructura.seek(ref.getPosicion());
            r = ManejadorArchivo.leerRegistro(estructura);
        }
        else
            System.out.println("no encontrado");
            
        return r;
    }
    
    /**
     * Método utilizado para agregar nuevos registros de datos de personas a la
     * agenda.
     * 
     * Este método no almacena la información en el archivo, solamente mantiene
     * los datos en el array {@code registros}. Para almacenar éstos datos se 
     * requiere el uso del método {@link #escribirDatos() }.
     * 
     * 
     * @param r un objeto de tipo Registro que será agregado
     */
    public void agregarRegistro(Registro r){
        registros.add(r);
    }
    
    /**
     * Escribe los datos al archivo, recorriendo los ArrayList de 
     * {@code estructura} e {@code indice}.
     * 
     * El método se asegura que el fin de archivo sea el adecuado, ya que
     * existe la posibilidad de que hayan menos datos y el archivo sea 
     * mas pequeño con los nuevos datos.
     * 
     * El tamaño del archivo se fija con el metodo 
     * {@link java.io.RandomAccessFile#setLength(long) }
     * 
     * El almacenado de los datos se hace mediante los métodos estáticos 
     * contenidos en la clase {@link ManejadorArchivo}
     * 
     * El método no considera edición de datos, solamente está creado para
     * reescribir el archivo por completo, almacenando la información en 
     * su debido lugar dentro de la estructura del archivo.
     * 
     * Cuando se realiza el almacenamiento se llena el array {@code indice} 
     * y así poder almacenarlo adecuadamente.
     * 
     * @see ManejadorArchivo
     * 
     * @throws IOException
     */
    public void escribirDatos() throws IOException{
        // nos movemos hacia donde comienza la escritura de registros
        estructura.seek(6);
        
        // nos aseguramos de que el índice se encuentre vacío
        indice.clear();
        
        for (Registro r: registros) {
            // se genera la entrada del índice
            indice.add(new Referencia(r.getApellidos(), estructura.getFilePointer()));
            // se escribe el registro al archivo
            ManejadorArchivo.escribirRegistro(r, estructura);
        }
        
        // ordenamos el índice
        indice.sort(null);
        
        // escribimos el índice
        
        // obtenemos la posicion donde quedará el indice:
        long posicionIndice = estructura.getFilePointer();
        
        // escribimos la cantidad de registros que se almacenaran:        
        estructura.write(Conversion.deLongA3Bytes(registros.size()));
        
        // luego se escribe cada uno de los datos del índice
        for (Referencia f: indice) {
            ManejadorArchivo.escribirReferenciaIndice(f, estructura);
        }
        
        // Nos aseguramos de colocar el fin de archivo luego de escribir el último
        // dato del índice:
        estructura.setLength(estructura.getFilePointer());
        
        // Se actualiza la posición del índice
        estructura.seek(3);
        estructura.write(Conversion.deLongA3Bytes(posicionIndice));
        
    }
    
    /**
     * Creada con el único proposito de llamar al método {@code close()} de 
     * la clase {@link java.io.RandomAccessFile}
     * 
     * @see RandomAccessFile#close() 
     * 
     * @throws IOException
     */
    public void cerrar() throws IOException{
        estructura.close();
    }

    /**
     * Devuelve la lista de información de personas que hay en memoria, no asi
     * en el archivo.
     * 
     * @return ArrayList de {@link Registro} 
     */
    public ArrayList<Registro> getRegistros() {
        return registros;
    }

    /**
     * Devuelver la lista de referencias que se encuentran en el índice, este dato
     * se llena cuando se almacena la informacioń con el método {@link #escribirDatos()}
     * o mediante el método {@link #leerIndice() }
     * 
     * @return ArrayList de {@link Referencia}
     * 
     * @see #leerIndice() 
     * @see #escribirDatos() 
     */
    public ArrayList<Referencia> getIndice() {
        return indice;
    }
    
}
