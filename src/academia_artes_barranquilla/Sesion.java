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

    public static final String fileName = "";

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

    public void escribirSesion() {
        try (RandomAccessFile raf = new RandomAccessFile(fileName, "rw")) {
            raf.seek(raf.length());
            raf.writeLong(this.codigoSesion);
            raf.writeLong(this.instructorCedula);
            raf.writeUTF(String.format("%-30.30s", this.nombreInstructor));
            raf.writeLong(this.aprendizCedula);
            raf.writeUTF(String.format("%-30.30s", this.nombreAprendiz));
            raf.writeUTF(String.format("%-30.30s", this.especialidad));
            raf.writeUTF(String.format("%-30.30s", this.fecha));
            raf.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Sesion leerSesion(RandomAccessFile raf, long posicion) {
        Sesion s = null;
        try {
            raf.seek(posicion);
            long codigoSesion = raf.readLong();
            long cedulaInstructor = raf.readLong();
            String nombreInstructor = raf.readUTF().trim();
            Long cedulaAprendiz = raf.readLong();
            String nombreAprendiz = raf.readUTF().trim();
            String especialidad = raf.readUTF().trim();
            String fecha = raf.readUTF().trim();
            
            s = new Sesion(codigoSesion, nombreAprendiz, cedulaAprendiz, especialidad, cedulaInstructor, nombreInstructor, fecha);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return s;
    }

}
