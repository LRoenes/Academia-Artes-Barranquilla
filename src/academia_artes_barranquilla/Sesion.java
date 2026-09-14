/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academia_artes_barranquilla;

import static academia_artes_barranquilla.Instructor.fileName;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author Luis
 */
public class Sesion {

    public static final String fileName = "sesiones.txt";
    public static final long TAMAÑO_REGISTRO = 
    8 +           // codigoSesion (long)
    8 +           // instructorCedula (long)
    (30 * 2) +    // nombreInstructor
    8 +           // aprendizCedula (long)
    (30 * 2) +    // nombreAprendiz
    (30 * 2) +    // especialidad
    (30 * 2);     // fecha
    private long codigoSesion;
    private String nombreAprendiz;
    private long aprendizCedula;
    private String especialidad;
    private long instructorCedula;
    private String nombreInstructor;
    private String fecha;

    public Sesion(long codigoSesion, String nombreAprendiz, long aprendizCedula, String especialidad, long instructorCedula, String nombreInstructor, String fecha) {
        this.codigoSesion = codigoSesion;
        this.nombreAprendiz = nombreAprendiz;
        this.aprendizCedula = aprendizCedula;
        this.especialidad = especialidad;
        this.instructorCedula = instructorCedula;
        this.nombreInstructor = nombreInstructor;
        this.fecha = fecha;
    }

    public long getCodigoSesion() {
        return codigoSesion;
    }

    public void setCodigoSesion(long codigoSesion) {
        this.codigoSesion = codigoSesion;
    }

    public String getNombreAprendiz() {
        return nombreAprendiz;
    }

    public void setNombreAprendiz(String nombreAprendiz) {
        this.nombreAprendiz = nombreAprendiz;
    }

    public long getAprendizCedula() {
        return aprendizCedula;
    }

    public void setAprendizCedula(long aprendizCedula) {
        this.aprendizCedula = aprendizCedula;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public long getInstructorCedula() {
        return instructorCedula;
    }

    public void setInstructorCedula(long instructorCedula) {
        this.instructorCedula = instructorCedula;
    }

    public String getNombreInstructor() {
        return nombreInstructor;
    }

    public void setNombreInstructor(String nombreInstructor) {
        this.nombreInstructor = nombreInstructor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public static void main(String[] args) {

    }

    public void escribirSesion(RandomAccessFile raf) {
        try {
            raf.seek(raf.length());
            raf.writeLong(this.codigoSesion);
            raf.writeLong(this.instructorCedula);
            raf.writeChars(String.format("%-30.30s", this.nombreInstructor));
            raf.writeLong(this.aprendizCedula);
            raf.writeChars(String.format("%-30.30s", this.nombreAprendiz));
            raf.writeChars(String.format("%-30.30s", this.especialidad));
            raf.writeChars(String.format("%-30.30s", this.fecha));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Sesion leerSesion(RandomAccessFile raf, long posicion) {
        Sesion s = null;
        try {
            raf.seek(posicion);
            long codigoSesion = raf.readLong();
            long instructorCedula = raf.readLong();
            String nombreInstructor = leerCadenaFija(raf, 30);
            long aprendizCedula = raf.readLong();
            String nombreAprendiz = leerCadenaFija(raf, 30);
            String especialidad = leerCadenaFija(raf, 30);
            String fecha = leerCadenaFija(raf, 30);

            s = new Sesion(codigoSesion, nombreAprendiz, aprendizCedula, especialidad, instructorCedula, nombreInstructor, fecha);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return s;
    }

    private static String leerCadenaFija(RandomAccessFile raf, int longitud) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            sb.append(raf.readChar());
        }
        return sb.toString().trim();
    }

}
