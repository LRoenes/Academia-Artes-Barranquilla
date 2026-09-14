/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academia_artes_barranquilla;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Luis
 */
public class ControlInstructor {

    private RandomAccessFile raf;
    private HashMap<Long, Long> indice;
    private static final String fileName = "";

    public void GestorInstructores() throws IOException {
        this.raf = new RandomAccessFile(fileName, "rw");
        this.indice = new HashMap<>();
        construirIndice();
    }

    private void construirIndice() throws IOException {
        long totalRegistros = raf.length() / Instructor.TAMAÑO_REGISTRO;

        for (long i = 0; i < totalRegistros; i++) {
            long posicion = i * Instructor.TAMAÑO_REGISTRO;
            Instructor instructor = Instructor.leerInstructor(raf, posicion);
            if (instructor != null) {
                indice.put(instructor.getCedula(), posicion);
            }
        }
    }

    public void crear(Instructor instructor) throws IOException {
        long posicion = raf.length(); // siempre al final
        raf.seek(posicion);
        instructor.escribirInstructor(raf);
        indice.put(instructor.getCedula(), posicion);
    }

    public Instructor buscarPorCedula(long cedula) throws IOException {
        Long posicion = indice.get(cedula);
        if (posicion == null) {
            return null; 
        }
        return Instructor.leerInstructor(raf, posicion);
    }

    public void actualizar(Instructor inst) throws IOException {
        Long posicion = indice.get(inst.getCedula());
        if (posicion == null) {
            System.out.println("No existe ese instructor.");
            return;
        }
        raf.seek(posicion);
        inst.escribirInstructor(raf); // sobreescribe TODO el registro en su misma posición
    }

    public void eliminar(long cedula) throws IOException {
        Instructor instructor = buscarPorCedula(cedula);
        if (instructor == null) {
            return;
        }
        instructor.setEstado(false);
        actualizar(instructor); // reutiliza actualizar(), no toca el índice (sigue existiendo, solo inactivo)
    }
    
       public void recuperar(long cedula) throws IOException {
        Instructor instructor = buscarPorCedula(cedula);
        if (instructor == null) {
            return;
        }
        instructor.setEstado(true);
        actualizar(instructor);
    }

    public ArrayList<Instructor> listarActivos() throws IOException {
        ArrayList<Instructor> lista = new ArrayList<>();
        for (Long posicion : indice.values()) {
            Instructor I = Instructor.leerInstructor(raf, posicion);
            if (I.isEstado()) {
                lista.add(I);
            }
        }
        return lista;
    }

    public void cerrar() throws IOException {
        raf.close();
    }
}
