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

    public static final String fileName = "";
    public static final long TAMAÑO_REGISTRO = (30 * 2) + 8 + 4 * ((30 * 2) + 4) + 1;
    
    private String nombre;
    private long cedula;
    private String[] especialidad = new String[4];
    private int[] sesionesRealizadas = new int[4];
    private boolean estado;

    public Aprendiz(String nombre, long cedula, String[] especialidad, int[] sesionesRealizadas, boolean estado) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.especialidad = especialidad;
        this.sesionesRealizadas = sesionesRealizadas;
        this.estado = estado;
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
            raf.seek(raf.length());
            raf.writeUTF(String.format("%-30.30s", this.nombre));
            raf.writeLong(this.cedula);
            for (int i = 0; i < 4; i++) {
                raf.writeUTF(String.format("%-30.30s", this.especialidad[i]));
            }
            for (int i = 0; i < 4; i++) {
                raf.writeInt(this.sesionesRealizadas[i]);
            }
            raf.writeBoolean(true);
            raf.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Aprendiz leerAprendiz(RandomAccessFile raf, long posicion) {
        Aprendiz a = null;
        try {
            raf.seek(posicion);
            String nombre = raf.readUTF().trim();
            long cedula = raf.readLong();
            String[] especialidad = new String[4];

            for (int i = 0; i < 4; i++) {
                especialidad[i] = raf.readUTF().trim();
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
}
