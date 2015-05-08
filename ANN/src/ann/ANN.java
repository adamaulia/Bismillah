/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ann;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author Adam
 */
public class ANN {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // TODO code application logic here
        System.out.println(" ANN 1");
        double alpha = 0.1;
        int hn = 2; //hidden neuron
        double[] y = new double[hn]; // summing function neuron
        double y2; //hasil summing function output
        double[] yy = new double[hn]; //fungsi aktifasi
        double[] b = new double[hn]; //bias hidden
        double output = 0; //output 
        double boutput; //bias output
        int baris = 1210;
        int kolom = 7;
        double error;
        double error2 = 0;
        double MSE;
        int[][] data = new int[kolom][baris];
        String[][] train = new String[kolom][baris];
        int maxEpoch = 10;
        int epoch = 0;
        int x1, x2, x3, x4, x5, x6, target; //buat input summing function    
        double d2, db2;
        double[] d1 = new double[hn]; //menghitung d1
        //ambil data
        try {

            Workbook w = Workbook.getWorkbook(new File("D:\\don't open\\semester 6\\AI\\Tugasa besar 2\\Dataset1\\training1.xls")); //ambil data
            Sheet sh = w.getSheet(1);               //sheet kedua

            for (int i = 0; i < kolom; i++) {
                for (int j = 0; j < baris; j++) {
                    Cell c = sh.getCell(i, j);
                    String isi = c.getContents();
                    train[i][j] = isi;

                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ANN.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(ANN.class.getName()).log(Level.SEVERE, null, ex);
        }

        //preprocessing
        for (int i = 0; i < kolom; i++) {
            for (int j = 0; j < baris; j++) {
                if (train[0][j].equals("vhigh")) {
                    train[0][j] = "4";
                }
                if (train[0][j].equals("high")) {
                    train[0][j] = "3";
                }
                if (train[0][j].equals("med")) {
                    train[0][j] = "2";
                }
                if (train[0][j].equals("low")) {
                    train[0][j] = "1";
                }
                if (train[1][j].equals("vhigh")) {
                    train[1][j] = "4";
                }
                if (train[1][j].equals("high")) {
                    train[1][j] = "3";
                }
                if (train[1][j].equals("med")) {
                    train[1][j] = "2";
                }
                if (train[1][j].equals("low")) {
                    train[1][j] = "1";
                }
                if (train[2][j].equals("2")) {
                    train[2][j] = "2";
                }
                if (train[2][j].equals("3")) {
                    train[2][j] = "3";
                }
                if (train[2][j].equals("3")) {
                    train[2][j] = "3";
                }
                if (train[2][j].equals("4")) {
                    train[2][j] = "4";
                }
                if (train[2][j].equals("5more")) {
                    train[2][j] = "5";
                }
                if (train[2][j].equals("5more")) {
                    train[2][j] = "5";
                }
                if (train[3][j].equals("2")) {
                    train[3][j] = "2";
                }
                if (train[3][j].equals("4")) {
                    train[3][j] = "4";
                }
                if (train[3][j].equals("more")) {
                    train[3][j] = "5";
                }
                if (train[4][j].equals("small")) {
                    train[4][j] = "1";
                }
                if (train[4][j].equals("med")) {
                    train[4][j] = "2";
                }
                if (train[4][j].equals("big")) {
                    train[4][j] = "3";
                }
                if (train[5][j].equals("low")) {
                    train[5][j] = "1";
                }
                if (train[5][j].equals("med")) {
                    train[5][j] = "2";
                }
                if (train[5][j].equals("high")) {
                    train[5][j] = "3";
                }
                if (train[6][j].equals("unacc")) {
                    train[6][j] = "1";
                }
                if (train[6][j].equals("acc")) {
                    train[6][j] = "2";
                }
                if (train[6][j].equals("good")) {
                    train[6][j] = "3";
                }
                if (train[6][j].equals("vgood")) {
                    train[6][j] = "4";
                }
            }
        }

        //pindah dari string ke  double
        for (int i = 0; i < kolom; i++) {
            for (int j = 0; j < baris; j++) {
                data[i][j] = Integer.parseInt(train[i][j]);
            }
        }

        // output 
        for (int i = 0; i < baris; i++) {
            for (int j = 0; j < kolom; j++) {
                //  System.out.print(data[j][i]+" ");
            }
            // System.out.println(" ");
        }
        //bobot ke hidden layer
        double[] w1 = new double[hn];  //dari input 1
        double[] w2 = new double[hn];  //dari input 2
        double[] w3 = new double[hn];  //dari input 3
        double[] w4 = new double[hn];  //dari input 4 
        double[] w5 = new double[hn];  //dari input 5
        double[] w6 = new double[hn];  //dari input 6

        //delta bobot dan bias
        double[] dw1 = new double[hn];  //new bobot  1
        double[] dw2 = new double[hn];  //new bobot  2
        double[] dw3 = new double[hn];  //new bobot  3
        double[] dw4 = new double[hn];  //new bobot  4 
        double[] dw5 = new double[hn];  //new bobot  5
        double[] dw6 = new double[hn];  //new bobot  6
        double[] dwoutput = new double[hn];
        double[] db = new double[hn];

        //bobot ke output layer 
        double[] Woutput = new double[hn];

        //random bobot input , bobot output , bias input, bias output 
        Random r = new Random();
        for (int i = 0; i < hn; i++) {
            w1[i] = r.nextDouble();
            w2[i] = r.nextDouble();
            w3[i] = r.nextDouble();
            w4[i] = r.nextDouble();
            w5[i] = r.nextDouble();
            w6[i] = r.nextDouble();
            Woutput[i] = r.nextDouble();
            b[i] = r.nextDouble();
        }

        boutput = r.nextDouble();
        double temp = 0;
        //start ANN Backpro
        while (epoch < 1) {
            //tampung data ke variabel
            
            for (int i = 0; i < 3; i++) { //looping sebanyak baris data
                x1 = data[0][i];
                x2 = data[1][i];
                x3 = data[2][i];
                x4 = data[3][i];
                x5 = data[4][i];
                x6 = data[5][i];
                target = data[6][i];
                System.out.println("========================================================================================");
                //System.out.println(data[0][i] + " " + data[1][i] + " " + data[2][i] + " " + data[3][i]+ " " + data[4][i]+ " " + data[5][i]);
                //System.out.println(x1+ " " + x2 + " " + x3 + " " + x4+ " " + x5+ " " + x6);
                //hitung maju sebanyak jumlah neuron
                for (int j = 0; j < hn; j++) {
                    //hidden layer
                    System.out.println(" input ");
                    System.out.println(" x1 : " + x1 + " x2 :  " + x2 + " x3 : " + x3 + " x4 : " + x4 + " x5 : " + x5 + " x6 : " + x6);
                    System.out.println(" bobot ");
                    System.out.println("1 : " + w1[j] + " 2 : " + w2[j] + " 3 : " + w3[j] + " 4 : " + w4[j] + " 5 : " + w5[j] + " 6 : " + w6[j] + " wout " + Woutput[j]);
                    y[j] = ((x1 * w1[j]) + (x2 * w2[j] + (x3 * w3[j]) + (x4 * w4[j]) + (x5 * w5[j]) + (x6 * w6[j]) + b[j])); //summing function
                    yy[j] = (1 / (1 + Math.exp(-(y[j]))));  //fungsi aktifasi atau output
                    System.out.println("iterasi ke " + (1 + i) + " ativation function neuron ke " + (j + 1) + " " + yy[j]);
                    //temp = yy[j] * Woutput[j] + temp;
                }

                //output layer 
                for (int k = 0; k < hn; k++) {
                    temp = yy[k] * Woutput[k];  //buat nampung hasil 
                }
                //System.out.println(" hasil temp " + temp);
                y2 = temp + boutput;
                output = (1 / (1 + Math.exp(-y2)));
//                System.out.println(" summing function output "+y2);
//                System.out.println(" fungsi aktifasi "+output);

                error = target - output;
                System.out.println(" target : " + target + " error :  " + error + " output" + output);
                error2 = error2 + Math.pow(error, 2);
               // System.out.println(" error " + error2);

                //perhitungan mundur
                //hitung d2
                d2 = (1 - Math.pow(output, 2));

                //hitung d1
                for (int j = 0; j < hn; j++) {
                    d1[j] = (1 - Math.pow(yy[j], 2)) * (Woutput[j] * d2);
                }

                //hitung dw hidden output, db pada hidden output
                for (int j = 0; j < hn; j++) {
                    dw1[j] = alpha * d1[j] * x1;
                    dw2[j] = alpha * d1[j] * x2;
                    dw3[j] = alpha * d1[j] * x3;
                    dw4[j] = alpha * d1[j] * x4;
                    dw5[j] = alpha * d1[j] * x5;
                    dw6[j] = alpha * d1[j] * x6;
                    dwoutput[j] = alpha * d2 * yy[j];
                    db[j] = alpha * d1[j];
                }
                db2 = alpha * d2;
                
                System.out.println(" dw  ");
                for (int j = 0; j < hn; j++) {

                    System.out.println("1 : " + dw1[j] + " 2 : " + dw2[j] + " 3 : " + dw3[j] + " 4 : " + dw4[j] + " 5 : " + dw5[j] + " 6 : " + dw6[j] + " wout " + dwoutput[j]);
                }
                
                
                //update bobot hidden output, bias hidden output
                for (int j = 0; j < hn; j++) {
                    w1[j] = w1[j] + dw1[j];
                    w2[j] = w2[j] + dw2[j];
                    w3[j] = w3[j] + dw3[j];
                    w4[j] = w4[j] + dw4[j];
                    w5[j] = w5[j] + dw5[j];
                    w6[j] = w6[j] + dw6[j];
                    b[j] = b[j] + db[j];
                    Woutput[j] = dwoutput[j] + Woutput[j];
                }
                boutput = boutput + db2;

                System.out.println(" bobot baru ");
                for (int j = 0; j < hn; j++) {

                    System.out.println("1 : " + w1[j] + " 2 : " + w2[j] + " 3 : " + w3[j] + " 4 : " + w4[j] + " 5 : " + w5[j] + " 6 : " + w6[j] + " wout " + Woutput[j]);
                }

            }
            MSE = error2 / baris;
            System.out.println((epoch + 1) + "   MSE : " + MSE);
            epoch++;

        }

    }

}
