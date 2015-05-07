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
        double alpha = 0.1;
        int hn = 2; //hidden neuron
        double[] y = new double[hn]; // summing function neuron
        double y2 ; //hasil summing function output
        double[] yy = new double[hn]; //fungsi aktifasi
        double[] b = new double[hn]; //bias hidden
        double output=0; //output 
        double boutput; //bias output
        int baris = 1210;
        int kolom = 7;
        double error;
        double error2=0;
        double MSE;
        int[][] data = new int[kolom][baris];
        String[][] train = new String[kolom][baris];
        int maxEpoch = 10;
        int epoch = 0;
        int x1, x2, x3, x4, x5, x6,target; //buat input summing function    
        double d2,db2;
        double [] d1 = new double[hn]; //menghitung d1
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



        //bias output
        

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

        //start hitung maju
        while (epoch < 100) {
            //tampung data ke variabel
            for (int i = 0; i < 6; i++) {
                x1 = data[0][i];
                x2 = data[1][i];
                x3 = data[2][i];
                x4 = data[3][i];
                x5 = data[4][i];
                x6 = data[5][i];
                target = data[6][i];
                
                //System.out.println(data[0][i] + " " + data[1][i] + " " + data[2][i] + " " + data[3][i]+ " " + data[4][i]+ " " + data[5][i]);
                //System.out.println(x1+ " " + x2 + " " + x3 + " " + x4+ " " + x5+ " " + x6);
                
                

                
                //hitung maju sebanyak jumlah neuron
                for (int j = 0; j < hn; j++) {
                 //hidden layer
//                    System.out.println(" intput ");
//                    System.out.println(" x1 : " +x1 + " x2 :  " +x2 + " x3 : "+x3 +" x4 : " +x4+" x5 : "+x5 +" x6 : " +x6  );
//                    System.out.println(" bobot ");
//                    System.out.println("1 : " +w1[j]+ " 2 : " + w2[j]+ " 3 : " +w3[j] + " 4 : "+ w4[j]+ " 5 : " + w5[j]+ " 6 : " + w6[j]);
                    y[j]=((x1*w1[j])+(x2*w2[j]+(x3*w3[j])+ (x4*w4[j]) + (x5*w5[j])+ (x6*w6[j]) +b[j])); //summing function
                    yy[j]=(1/(1+Math.exp(-(y[j]))));  //fungsi aktifasi
            //        System.out.println("iterasi ke "+(1+i)+" ativation function neuron ke "+(j+1)+" "+yy[j]);
                 }
                 //output layer 
                double temp=0;
                
                 for (int k = 0; k < hn; k++) {
                    temp=yy[k]*Woutput[k]+temp;
                }
         //       System.out.println(" hasil temp "+temp); 
                y2 = temp + boutput;
                output = (1/(1+Math.exp(-y2)));
//                System.out.println(" summing function output "+y2);
//                System.out.println(" fungsi aktifasi "+output);
                error = target-output;
                error2 = error2 + Math.pow(error, 2);
            //    System.out.println(" error " +error);
                
                //perhitungan mundur
                
                //hitung d2 (delta di output) 
                d2=(1-(Math.pow(output, 2)))*error;
            //    System.out.println(" d2 done ");
                
                //menghitung d1
                 for (int l = 0; l < hn; l++) {
                    d1[l]=(1-(Math.pow(yy[l], 2)))*(Woutput[l]*d2);
                }
              //   System.out.println(" d1 done ");
                
                 //menghitung bobot baru
                 for (int x = 0; x < hn; x++) {
                    dw1[x]=alpha*d1[x]*x1;
                    dw2[x]=alpha*d1[x]*x2;
                    dw3[x]=alpha*d1[x]*x3;
                    dw4[x]=alpha*d1[x]*x4;
                    dw5[x]=alpha*d1[x]*x5;
                    dw6[x]=alpha*d1[x]*x6;
                    dwoutput[x]=alpha*d2*yy[x];
                    db[x]=alpha*d1[x];
                }
                 db2=alpha*d2;
                 
                 //update bobot
                 
                 for (int w = 0; w < hn; w++) {
                    w1[w]=w1[w]+dw1[w];
                    w2[w]=w2[w]+dw2[w];
                    w3[w]=w3[w]+dw3[w];
                    w4[w]=w4[w]+dw4[w];
                    w5[w]=w5[w]+dw5[w];
                    w6[w]=w6[w]+dw6[w];
                    b[w]=b[w]+db[w];    
                }
                
                //update bias output 
                 output = output + db2;
                 
            }
            

            MSE=output/baris;
            System.out.println((epoch+1)+ "   MSE : "+MSE );
            epoch++;
        }

    }

}


