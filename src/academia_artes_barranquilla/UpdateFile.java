/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academia_artes_barranquilla;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class UpdateFile {

    private static final long salto = (2 + 30) + Integer.BYTES + Double.BYTES + 1;
    private static final long nombreByte = 0;
    private static final long edadByte = 32;
    private static final long pesoByte = 36;
    private static final long estadoByte = 44;

    public static void main(String[] args) {

        try {
            RandomAccessFile raf = new RandomAccessFile("example.txt", "rw");
            long cantidadRegistros = raf.length() / salto;
            Scanner sc = new Scanner(System.in);
            String input = "";

            input = "si";
            raf.seek(0); //Pointer al ultimo registro para no sobreescribir

            while (input.equalsIgnoreCase("si")) {
                System.out.println("Cual usuario quieres actualizar?");
                input = sc.nextLine();

                for (int i = 1; i <= cantidadRegistros; i++) {

                    raf.seek(salto * (i - 1) + estadoByte);
                    boolean check = raf.readBoolean();// Activo o Inactivo
                    raf.seek(salto * (i - 1));
                    String nombre = raf.readUTF();

                    if (nombre.trim().equalsIgnoreCase(input.trim()) && check) {
                        System.out.println("Que deseas cambiar?");
                        input = sc.nextLine();
                        switch (input.trim()) {
                            case "nombre":
                                raf.seek(salto * (i - 1));
                                System.out.println("Cual sera su nuevo nombre?");
                                input = sc.nextLine();
                                raf.writeUTF(String.format("%-30.30s", input));
                                raf.seek(salto * (i - 1));
                                System.out.println("Nuevo nombre: " + raf.readUTF());
                                break;
                            case "edad":
                                raf.seek(salto * (i - 1) + edadByte);
                                System.out.println("Cual sera su nueva edad?");
                                input = sc.nextLine();
                                raf.writeInt(Integer.parseInt(input));
                                raf.seek(salto * (i - 1) + edadByte);
                                System.out.println("Nueva edad: " + raf.readInt());
                                break;
                            case "peso":
                                raf.seek(salto * (i - 1) + pesoByte);
                                System.out.println("Cual sera su nueva edad?");
                                input = sc.nextLine();
                                raf.writeDouble(Double.parseDouble(input));
                                raf.seek(salto * (i - 1) + pesoByte);
                                System.out.println("Nuevo peso: " + raf.readDouble());
                                break;
                        }
                        raf.seek(salto * (i - 1));
                        System.out.println(raf.readUTF());
                        System.out.println(raf.readInt() + "");
                        System.out.println(raf.readDouble());
                        break;
                    } else if (i == cantidadRegistros) {
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
