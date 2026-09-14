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
public class Instructor {

    public static final String fileName = "instructor.txt";
    public static final long TAMAÑO_REGISTRO = (30 * 2) + 8 + (30 * 2) + (30 * 2) + 4 + 1;

    private String nombre;
    private long cedula;
    private String telefono;
    private String especialidad;
    private int sesionesRealizadas;
    private boolean estado;

    public Instructor(String nombre, long cedula, String telefono, String especialidad, int sesionesRealizadas, boolean estado) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.sesionesRealizadas = sesionesRealizadas;
        this.estado = estado;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public int getSesionesRealizadas() {
        return sesionesRealizadas;
    }

    public void setSesionesRealizadas(int sesionesRealizadas) {
        this.sesionesRealizadas = sesionesRealizadas;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public static void main(String[] args) {
        System.out.println("HolaS");
    }

    public void escribirInstructor(RandomAccessFile raf) {
        try {
            raf.writeChars(String.format("%-30.30s", this.nombre));
            raf.writeLong(this.cedula);
            raf.writeChars(String.format("%-30.30s", this.telefono));
            raf.writeChars(String.format("%-30.30s", this.especialidad));
            raf.writeInt(this.sesionesRealizadas);
            raf.writeBoolean(true);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Instructor leerInstructor(RandomAccessFile raf, long posicion) {
        Instructor I = null;
        try {
            raf.seek(posicion);
            String nombre = leerCadenaFija(raf, 30);
            long cedula = raf.readLong();
            String telefono = leerCadenaFija(raf, 30);
            String especialidad = leerCadenaFija(raf, 30);
            int sesionesRealizadas = raf.readInt();
            boolean estado = raf.readBoolean();
            I = new Instructor(nombre, cedula, telefono, especialidad, sesionesRealizadas, estado);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return I;
    }

    private static String leerCadenaFija(RandomAccessFile raf, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }

}
