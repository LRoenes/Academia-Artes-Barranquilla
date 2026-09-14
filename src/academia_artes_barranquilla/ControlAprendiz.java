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
public class ControlAprendiz {

    private RandomAccessFile raf;
    private HashMap<Long, Long> indice;
    private static final String fileName = "aprendiz.txt";

    public ControlAprendiz() throws IOException {
        this.raf = new RandomAccessFile(fileName, "rw");
        this.indice = new HashMap<>();
        construirIndice();
    }

    private void construirIndice() throws IOException {
        long totalRegistros = raf.length() / Aprendiz.TAMAÑO_REGISTRO;

        for (long i = 0; i < totalRegistros; i++) {
            long posicion = i * Instructor.TAMAÑO_REGISTRO;
            Aprendiz aprendiz = Aprendiz.leerAprendiz(raf, posicion);
            if (aprendiz != null) {
                indice.put(aprendiz.getCedula(), posicion);
            }
        }
    }

    public void crear(Aprendiz aprendiz) throws IOException {
        long posicion = raf.length(); // siempre al final
        raf.seek(posicion);
        aprendiz.escribirAprendiz(raf);
        indice.put(aprendiz.getCedula(), posicion);
    }

    public Aprendiz buscarPorCedula(long cedula) throws IOException {
        Long posicion = indice.get(cedula);
        if (posicion == null) {
            return null;
        }
        return Aprendiz.leerAprendiz(raf, posicion);
    }

    public void actualizar(Aprendiz aprendiz) throws IOException {
        Long posicion = indice.get(aprendiz.getCedula());
        if (posicion == null) {
            System.out.println("No existe ese Aprendiz.");
            return;
        }
        raf.seek(posicion);
        aprendiz.escribirAprendiz(raf); // sobreescribe TODO el registro en su misma posición
    }

    public void eliminar(long cedula) throws IOException {
        Aprendiz aprendiz = buscarPorCedula(cedula);
        if (aprendiz == null) {
            return;
        }
        aprendiz.setEstado(false);
        actualizar(aprendiz); // reutiliza actualizar(), no toca el índice (sigue existiendo, solo inactivo)
    }

    public void recuperar(long cedula) throws IOException {
        Aprendiz aprendiz = buscarPorCedula(cedula);
        if (aprendiz == null) {
            return;
        }
        aprendiz.setEstado(true);
        actualizar(aprendiz);
    }

    public ArrayList<Aprendiz> listarActivos() throws IOException {
        ArrayList<Aprendiz> lista = new ArrayList<>();
        for (Long posicion : indice.values()) {
            Aprendiz a = Aprendiz.leerAprendiz(raf, posicion);
            if (a.isEstado()) {
                lista.add(a);
            }
        }
        return lista;
    }

    public void cerrar() throws IOException {
        raf.close();
    }
    
    public boolean incrementarSesion(long cedula, String especialidad) throws IOException {
        Aprendiz a = buscarPorCedula(cedula);
        if (a == null) {
            System.out.println("Aprendiz no encontrado.");
            return false;
        }

        int slot = a.buscarSlotEspecialidad(especialidad);
        if (slot == -1) {
            slot = a.buscarSlotLibre();
            if (slot == -1) {
                System.out.println("El aprendiz ya tiene 4 especialidades distintas.");
                return false;
            }
        }

        if (a.getContador(slot) >= 4) {
            System.out.println("Ya alcanzó el máximo de sesiones en esa especialidad.");
            return false;
        }

        a.incrementarContador(slot, especialidad); // asigna la especialidad si el slot estaba vacío, y suma 1
        actualizar(a);
        return true;
    }
}
