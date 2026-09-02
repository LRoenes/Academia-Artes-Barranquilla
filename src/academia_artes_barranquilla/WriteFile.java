/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academia_artes_barranquilla;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.RandomAccess;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class WriteFile {

    public static void main(String[] args) {
        RandomAccessFile file;
        Scanner sc = new Scanner(System.in);

        String name, surname;
        int age;

        System.out.println("Please input your name: ");
        name = sc.nextLine();
        System.out.println("Please input your surname: ");
        surname = sc.nextLine();
        System.out.println("Please input your age: ");
        age = sc.nextInt();

        try {
            file = new RandomAccessFile("prueba.txt", "rw");
            long fileSize = file.length();
            file.seek(fileSize);

            file.writeUTF(name);
            for (int i = 0; i < 20 - name.length(); i++) {
                file.writeByte(20);
            }
            file.writeUTF(surname);
            for (int i = 0; i < 20 - surname.length(); i++) {
                file.writeByte(20);
            }
            file.writeInt(age);
            
            file.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Error al escribir el archivo..");
        }
    }
}
