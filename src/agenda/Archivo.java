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
 *
 * @author Dhaby Xiloj <dhabyx@gmail.com>
 */
public class Archivo {
    private final String nombreArchivo;
    private final String marcaDeInicio = "agd";
    private RandomAccessFile estructura;
    
    private final ArrayList<Registro> registros;
    
    private int numeroDeRegistros;
    private ArrayList<Referencia> indice;
    
    public Archivo(String ruta) throws IOException, FileNotFoundException, ArchivoValidoException {
        registros = new ArrayList();
        indice = new ArrayList();
        nombreArchivo = ruta;
        verificarArchivo();
    }
       
    private void verificarArchivo() throws FileNotFoundException, IOException, ArchivoValidoException{
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
                throw new ArchivoValidoException();
            }
        }
    }
    
     /**
     * Mueve el puntero del archivo a la posición donde se encuentre el índice
     * @return
     * @throws java.io.IOException
     */
    private void moverAIndice() throws IOException {
        // movemos el puntero hacia la posición donde está almacenada la posición
        // del índice en el archivo
        estructura.seek(3);
   
        byte []b = new byte[3];
        estructura.read(b);
        int posIndice = Conversion.de3BytesAInt(b);
        //System.out.println(String.format("0x%2s", Integer.toHexString(posIndice)).replace(' ', '0'));
        
        estructura.seek(posIndice);
    }
    
    /**
     * Devuelve la cantidad de registros que tiene el archivo
     * @return
     * @throws java.io.IOException
     */
    public int contarRegistros() throws IOException{
        moverAIndice();
        byte []b = new byte[3];
        estructura.read(b);
        return Conversion.de3BytesAInt(b);
    }
    
    public void leerIndice() throws IOException{
        int contador = contarRegistros();
        indice.clear();
        if (contador > 0)
            for (int i = 0; i < contador; i++ ) {
                indice.add(ManejadorArchivo.leerReferenciaIndice(estructura));
            }
    }
    
    public void imprimirIndice() {
        for (Referencia r: indice) {
            System.out.println(r.getApellido()+" - "+
                    String.format("0x%2s", Integer.toHexString((int) r.getPosicion())).replace(' ', '0'));
        }
    }
    
    public void agregarRegistro(Registro r){
        registros.add(r);
    }
    
    public void escribirDatos() throws IOException{
        // nos movemos hacia donde comienza la escritura de registros
        estructura.seek(6);
        
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
     *
     * @throws IOException
     */
    public void cerrar() throws IOException{
        estructura.close();
    }
    
    
}
