/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package academia_artes_barranquilla;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Academia_Artes_Barranquilla {

    /**
     * @param args the command line arguments
     */
    private static final String DATA_FILE = "productos.dat";
    private static final String INDEX_FILE = "productos.idx";
    private static final int RECORD_SIZE = 4 + 40 + 8; // ID(4) + Nombre(40) + Precio(8) = 52 bytes

    public static void main(String[] args) {
                try {
            // 1. Escribir datos de prueba
            escribirProducto(101, "Laptop Asus         ", 850.50); 
            escribirProducto(105, "Raton Optico        ", 25.00);
            escribirProducto(102, "Monitor Gamer       ", 300.99);

            // 2. Cargar índices a Memoria (Simulación de índice denso)
            Map<Integer, Long> indices = cargarIndices();

            // 3. Buscar un producto usando el índice (Búsqueda Directa)
            int idABuscar = 101;
            if (indices.containsKey(idABuscar)) {
                long posicionByte = indices.get(idABuscar);
                buscarProducto(posicionByte);
            } else {
                System.out.println("Producto no encontrado.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /*
    public static void leerArchivoSequencial(Scanner sc, String file_name) {
        try {
            FileReader outfile = new FileReader(file_name + ".txt");
            BufferedReader BufferLectura = new BufferedReader(outfile);
            String line = null;

            while ((line = BufferLectura.readLine()) != null) {
                String columnas[] = line.split("\t");
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void escribirArchivoSequencial(Scanner sc, String file_name, String file_read_name) {
        try {
            FileWriter newFile = new FileWriter(file_name + ".txt");
            PrintWriter registro_sesiones = new PrintWriter(newFile);
            FileReader outfile = new FileReader(file_read_name + ".txt");
            BufferedReader BufferLectura = new BufferedReader(outfile);
            String line = null;

            while ((line = BufferLectura.readLine()) != null) {
                String columnas[] = line.split("\t");
                for (int i = 0; i < columnas.length; i++) {
                    registro_sesiones.println(columnas[i] + "\t");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    */
    
    public static void escribirProducto(int id, String nombre, double precio) throws IOException {
        try (RandomAccessFile df = new RandomAccessFile(DATA_FILE, "rw"); RandomAccessFile ifile = new RandomAccessFile(INDEX_FILE, "rw")) {

            // Ir al final del archivo de datos para añadir el nuevo registro
            long posicionByte = df.length();
            df.seek(posicionByte); // Mueve el cursor/pointer a una posicion exacta.

            // Escribir datos
            df.writeInt(id);
            df.writeChars(nombre.substring(0, 20)); // Forzar tamaño fijo de cadena
            df.writeDouble(precio);

            // Guardar en el índice: ID y la posición de byte correspondiente
            ifile.seek(ifile.length());
            ifile.writeInt(id);
            ifile.writeLong(posicionByte);
        }
    }

    // Carga las parejas (ID -> Posición Byte) en un Map para búsquedas instantáneas
    public static Map<Integer, Long> cargarIndices() throws IOException {
        Map<Integer, Long> mapaIndices = new HashMap<>(); //1. Se crea un HashMap
        try (RandomAccessFile ifile = new RandomAccessFile(INDEX_FILE, "r")) { //2. Creacion de un archivo indexado solo Lectura "r", si fuese leer y escribir "rw"
            while (ifile.getFilePointer() < ifile.length()) { //3. .getFilePointer()returns the current byte position (offset) where the next read or write operation will happen. .length() para un objeto de RandomAccessFile te da la cantidad de bytes en el archivo.
                int id = ifile.readInt(); //4. Leer 32 bits  
                long pos = ifile.readLong(); //5. Leer 64 bits
                mapaIndices.put(id, pos); //6. Registrar el indice con la posicion.
            }
        }
        return mapaIndices; //7. Retorna el HashMap
    }

    // Accede directamente al registro sin recorrer el archivo secuencialmente
    public static void buscarProducto(long posicionByte) throws IOException {
        try (RandomAccessFile df = new RandomAccessFile(DATA_FILE, "r")) {
            df.seek(posicionByte); // Saltamos directo a la posición obtenida por el índice

            int id = df.readInt();

            // Leer los 20 caracteres del nombre
            StringBuilder nombreBuilder = new StringBuilder();
            for (int i = 0; i < 20; i++) {
                nombreBuilder.append(df.readChar());
            }
            double precio = df.readDouble();

            System.out.println("--- PRODUCTO ENCONTRADO ---");
            System.out.println("ID: " + id);
            System.out.println("Nombre: " + nombreBuilder.toString().trim());
            System.out.println("Precio: $" + precio);
        }
    }
}
