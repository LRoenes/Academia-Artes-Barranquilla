/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academia_artes_barranquilla;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Luis
 */
public class Aprendiz {

    public static final String fileName = "aprendiz.txt";
    public static final long TAMAÑO_REGISTRO = (30 * 2) + 8 + 4 * ((30 * 2) + 4) + 1;
    String[] especialidad = new String[4];
    int[] sesionesRealizadas = new int[4];
    private String nombre;
    private long cedula;
    private boolean estado;

    public Aprendiz() {
        for (int i = 0; i < 4; i++) {
            especialidad[i] = "";
            sesionesRealizadas[i] = 0;
        }
    }

    public Aprendiz(String nombre, long cedula, String[] especialidad, int[] sesionesRealizadas, boolean estado) {
        this();
        this.nombre = nombre;
        this.cedula = cedula;
        this.estado = estado;

        for (int i = 0; i < especialidad.length && i < 4; i++) {
            this.especialidad[i] = especialidad[i];
            this.sesionesRealizadas[i] = sesionesRealizadas[i];
        }
    }

    public static void main(String[] args) {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public long getCedula() {
        return cedula;
    }

    public void setCedula(long cedula) {
        this.cedula = cedula;
    }

    public String[] getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String[] especialidad) {
        this.especialidad = especialidad;
    }

    public int[] getSesionesRealizadas() {
        return sesionesRealizadas;
    }

    public void setSesionesRealizadas(int[] sesionesRealizadas) {
        this.sesionesRealizadas = sesionesRealizadas;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public void escribirAprendiz(RandomAccessFile raf) {
        try {
            raf.writeChars(String.format("%-30.30s", this.nombre));
            raf.writeLong(this.cedula);
            for (int i = 0; i < 4; i++) {
                raf.writeChars(String.format("%-30.30s", this.especialidad[i]));
            }
            for (int i = 0; i < 4; i++) {
                raf.writeInt(this.sesionesRealizadas[i]);
            }
            raf.writeBoolean(true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Aprendiz leerAprendiz(RandomAccessFile raf, long posicion) {
        Aprendiz a = null;
        try {
            raf.seek(posicion);
            String nombre = leerCadenaFija(raf, 30);
            long cedula = raf.readLong();
            String[] especialidad = new String[4];

            for (int i = 0; i < 4; i++) {
                especialidad[i] = leerCadenaFija(raf, 30);
            }

            int[] sesionesRealizadas = new int[4];
            for (int i = 0; i < 4; i++) {
                sesionesRealizadas[i] = raf.readInt();
            }
            boolean estado = raf.readBoolean();
            a = new Aprendiz(nombre, cedula, especialidad, sesionesRealizadas, estado);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    private static String leerCadenaFija(RandomAccessFile raf, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }

    public int buscarSlotEspecialidad(String especialidad) {
        for (int i = 0; i < 4; i++) {
            if (this.especialidad[i].trim().equalsIgnoreCase(especialidad)) {
                return i;
            }
        }
        return -1; // no la tiene todavía
    }

    public int buscarSlotLibre() {
        for (int i = 0; i < 4; i++) {
            if (this.especialidad[i].trim().isEmpty()) {
                return i;
            }
        }
        return -1; // ya tiene las 4 especialidades ocupadas
    }

    public int getContador(int slot) {
        if (slot < 0 || slot >= 4) {
            return 0;
        }
        return this.sesionesRealizadas[slot];
    }

    public void incrementarContador(int slot, String especialidad) {
        if (this.especialidad[slot].trim().isEmpty()) {
            this.especialidad[slot] = especialidad; // primera vez que usa esta especialidad
        }
        this.sesionesRealizadas[slot]++;
    }
}
