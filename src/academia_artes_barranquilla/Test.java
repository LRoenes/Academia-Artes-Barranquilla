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
public class Test {

    public static void main(String[] args) throws IOException {
        ControlInstructor controlInstructor = new ControlInstructor();
        ControlAprendiz controlAprendiz = new ControlAprendiz();
        ControlSesion controlSesion = new ControlSesion(controlInstructor, controlAprendiz);

        // 2. Crear un instructor
        Instructor inst = new Instructor("Maria Torres", 111222333L, "3101112222", "Pintura", 0, true);
        controlInstructor.crear(inst);
        System.out.println("Instructor creado: " + inst.getNombre());

        // 3. Crear un aprendiz (arranca sin especialidades asignadas)
        String[] especialidadesVacias = new String[0];
        int[] contadoresVacios = new int[0];
        Aprendiz apr = new Aprendiz("Carlos Ruiz", 444555666L, especialidadesVacias, contadoresVacios, true);
        controlAprendiz.crear(apr);
        System.out.println("Aprendiz creado: " + apr.getNombre());

        // 4. Crear una sesión entre ambos
        boolean creada = controlSesion.crearSesion(444555666L, 111222333L, "Pintura", "13/09/2026");
        System.out.println("¿Sesión creada? " + creada);

        // 5. Verificar que la sesión quedó bien guardada
        Sesion s = controlSesion.buscarPorCodigo(1L);
        if (s != null) {
            System.out.println("Sesión leída -> Código: " + s.getCodigoSesion()
                    + " | Aprendiz: " + s.getNombreAprendiz()
                    + " | Instructor: " + s.getNombreInstructor()
                    + " | Especialidad: " + s.getEspecialidad()
                    + " | Fecha: " + s.getFecha());
        } else {
            System.out.println("No se encontró la sesión.");
        }

        // 6. Verificar que el contador del aprendiz subió a 1 en Pintura
        Aprendiz aprActualizado = controlAprendiz.buscarPorCedula(444555666L);
        int slot = aprActualizado.buscarSlotEspecialidad("Pintura");
        System.out.println("Contador de Pintura del aprendiz: " + aprActualizado.getContador(slot));

        // 7. Verificar que el contador de sesiones del instructor subió a 1
        Instructor instActualizado = controlInstructor.buscarPorCedula(111222333L);
        System.out.println("Sesiones del mes del instructor: " + instActualizado.getSesionesRealizadas());

        // 8. Probar el límite: crear 3 sesiones más (debería fallar en la 5ta, que excede el máximo de 4)
        for (int i = 0; i < 4; i++) {
            boolean ok = controlSesion.crearSesion(444555666L, 111222333L, "Pintura", "13/09/2026");
            System.out.println("Intento " + (i + 2) + " -> ¿creada? " + ok);
        }

        // Cerrar todo al final
        controlInstructor.cerrar();
        controlAprendiz.cerrar();
        controlSesion.cerrar();

    }
}

