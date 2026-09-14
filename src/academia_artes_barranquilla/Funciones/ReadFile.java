/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academia_artes_barranquilla.Funciones;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class ReadFile {

    private static final long salto = (2 + 30) + Integer.BYTES + Double.BYTES + 1;

    public static void main(String[] args) {

        try {
            RandomAccessFile raf = new RandomAccessFile("example.txt", "r");
            long cantidadRegistros = raf.length() / salto;
            Scanner sc = new Scanner(System.in);
            String input = "";

            input = "si";
            raf.seek(0); //Pointer al ultimo registro para no sobreescribir

            while (input.equalsIgnoreCase("si")) {
                System.out.println("Cual usuario quieres buscar?");
                input = sc.nextLine();

                for (int i = 1; i <= cantidadRegistros; i++) {
                    
                    raf.seek(salto * (i - 1) + 44);
                    boolean check = raf.readBoolean();// Activo o Inactivo
                    raf.seek(salto * (i - 1));
                    
                    if (raf.readUTF().trim().equalsIgnoreCase(input.trim()) && check) {
                        raf.seek(salto * (i - 1));
                        System.out.println(raf.readUTF());
                        System.out.println(raf.readInt() + "");
                        System.out.println(raf.readDouble());
                        break;
                    } else if(i == cantidadRegistros){
                        System.out.println("No se encontro el usuario " + input + " en el sistema.");
                    }
                }
                System.out.println("Desea leer otro usuario?");
                input = sc.nextLine();
            }

        } catch (FileNotFoundException ex) {
            System.getLogger(WriteFile.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
