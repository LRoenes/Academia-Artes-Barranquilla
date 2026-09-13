/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academia_artes_barranquilla;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.RandomAccess;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class WriteFile {

    public static void main(String[] args) throws IOException {
        try {
            RandomAccessFile raf = new RandomAccessFile("example.txt", "rw");
            Scanner sc = new Scanner(System.in);
            String input = "";
            
            
            System.out.println("Deseas agregar un registro?");
            input = sc.nextLine();
            raf.seek(raf.length()); //Pointer al ultimo registro para no sobreescribir
            
            while (input.equalsIgnoreCase("si")) {
                System.out.println("Nombre para registrar: ");
                input = sc.nextLine();
                raf.writeUTF(String.format("%-30.30s", input));
                System.out.println("Edad para registrar: ");
                input = sc.nextLine();
                raf.writeInt(Integer.parseInt(input));
                System.out.println("Peso para registrar: ");
                input = sc.nextLine();
                raf.writeDouble(Double.parseDouble(input));
                raf.writeBoolean(true);

                System.out.println("Deseas agregar un registro?");
                input = sc.nextLine();
            }
            long salto = (2 + 30) + Integer.BYTES + Double.BYTES + 1;
            int registro = 1;

            System.out.println("Solo el registro " + registro);
            raf.seek(salto * (registro - 1)); //Pointer al registro para buscar

            System.out.println(raf.readUTF());
            System.out.println(raf.readInt() + "");
            System.out.println(raf.readDouble());
            

        } catch (FileNotFoundException ex) {
            System.getLogger(WriteFile.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
