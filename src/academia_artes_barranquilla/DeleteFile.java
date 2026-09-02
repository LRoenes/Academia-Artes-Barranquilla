/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package academia_artes_barranquilla;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class DeleteFile {

    private static final int RECORD = 44;

    public static void main(String[] args) {
        RandomAccessFile file;
        String name = null, surname = null;
        int age = 0;

        try {
            file = new RandomAccessFile(new File("prueba.txt"), "rw");
            Scanner sc = new Scanner(System.in);
            long fileSize = file.length();
            file.seek(0);
            long numRecords = fileSize / RECORD;
            boolean check = true;

            while (check == true) {
                System.out.println("Which person's name do you want to change?");
                String search = sc.nextLine();

                for (int j = 0; j < numRecords; j++) {
                    name = file.readUTF();
                    for (int i = 0; i < 20 - name.length(); i++) {
                        file.readByte();
                    }
                    surname = file.readUTF();
                    for (int i = 0; i < 20 - surname.length(); i++) {
                        file.readByte();
                    }
                    age = file.readInt();

                    if (search.equalsIgnoreCase(name)) {

                        System.out.println("Name : " + name + " Surname : " + surname + " Age: " + age);
                        System.out.println("Are you sure you want to delete?");
                        search = sc.nextLine().toLowerCase();
                        if (search.equalsIgnoreCase("yes")) {
                            file.seek(j * RECORD);
                            file.setLength(fileSize - 48);
                        }
                    }

                }

                System.out.println("Do you wish to change another name?");
                search = sc.nextLine();
                if (search.equalsIgnoreCase("no")) {
                    check = false;
                }

            }
            file.close();

        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Error al leer el archivo");
        }
    }
}
