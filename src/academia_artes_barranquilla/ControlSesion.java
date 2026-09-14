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
public class ControlSesion {

    private RandomAccessFile raf;
    private HashMap<Long, Long> indice; // clave = codigoSesion, valor = posición
    private static final String fileName = "sesiones.txt";

    private ControlInstructor controlInstructor;
    private ControlAprendiz controlAprendiz;

    public ControlSesion(ControlInstructor controlInstructor, ControlAprendiz controlAprendiz) throws IOException {
        this.raf = new RandomAccessFile(fileName, "rw");
        this.indice = new HashMap<>();
        this.controlInstructor = controlInstructor;
        this.controlAprendiz = controlAprendiz;
        construirIndice();
    }

    private void construirIndice() throws IOException {
        long total = raf.length() / Sesion.TAMAÑO_REGISTRO;
        for (long i = 0; i < total; i++) {
            long posicion = i * Sesion.TAMAÑO_REGISTRO;
            Sesion s = Sesion.leerSesion(raf, posicion);
            if (s != null) {
                indice.put(s.getCodigoSesion(), posicion);
            }
        }
    }

    public void crear(Sesion s) throws IOException {
        s.escribirSesion(raf);
        long posicion = raf.length() - Sesion.TAMAÑO_REGISTRO;
        indice.put(s.getCodigoSesion(), posicion);
    }

    public Sesion buscarPorCodigo(long codigo) {
        Long posicion = indice.get(codigo);
        if (posicion == null) {
            return null;
        }
        return Sesion.leerSesion(raf, posicion);
    }

    public ArrayList<Sesion> listarTodas() {
        ArrayList<Sesion> lista = new ArrayList<>();
        for (Long posicion : indice.values()) {
            Sesion s = Sesion.leerSesion(raf, posicion);
            lista.add(s);
        }
        return lista;
    }

    public void cerrar() throws IOException {
        raf.close();
    }

    public boolean crearSesion(long cedulaAprendiz, long cedulaInstructor, String especialidad, String fecha) throws IOException {
        Instructor inst = controlInstructor.buscarPorCedula(cedulaInstructor);
        Aprendiz apr = controlAprendiz.buscarPorCedula(cedulaAprendiz);

        if (inst == null) {
            System.out.println("Instructor no encontrado.");
            return false;
        }
        if (apr == null) {
            System.out.println("Aprendiz no encontrado.");
            return false;
        }

        int slot = apr.buscarSlotEspecialidad(especialidad);
        if (slot == -1) {
            slot = apr.buscarSlotLibre();
            if (slot == -1) {
                System.out.println("El aprendiz ya tiene 4 especialidades distintas.");
                return false;
            }
        }

        if (apr.getContador(slot) >= 4) {
            System.out.println("Ya alcanzó el máximo de sesiones en " + especialidad + " este mes.");
            return false;
        }

        long nuevoCodigo = (raf.length() / Sesion.TAMAÑO_REGISTRO) + 1;
        Sesion s = new Sesion(nuevoCodigo, apr.getNombre(), apr.getCedula(), especialidad,
                inst.getCedula(), inst.getNombre(), fecha);
        crear(s);

        apr.incrementarContador(slot, especialidad);
        controlAprendiz.actualizar(apr);
        inst.setSesionesRealizadas(inst.getSesionesRealizadas() + 1);
        controlInstructor.actualizar(inst);
        return true;
    }
}
