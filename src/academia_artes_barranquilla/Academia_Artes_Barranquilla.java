/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package academia_artes_barranquilla;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author Luis
 */
public class Academia_Artes_Barranquilla {

    /**
     * @param args the command line arguments
     */
    private static final String DATA_FILE = "productos.dat";
    private static final String INDEX_FILE = "productos.idx";
    private static final int RECORD_SIZE = 4 + 40 + 8; // ID(4) + Nombre(40) + Precio(8) = 52 bytes

    public static void main(String[] args) {

    }
}