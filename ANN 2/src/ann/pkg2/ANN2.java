/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ann.pkg2;

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
public class ANN2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        System.out.println(" ANN 2");
        double alpha = 0.01;
        int hn = 5; //hidden neuron
        double[] y = new double[hn]; // summing function neuron
        double y2; //hasil summing function output
        double[] yy = new double[hn]; //fungsi aktifasi
        double[] b = new double[hn]; //bias hidden
        double output = 0; //output 
        double boutput; //bias output
        int baris = 1210;
        int kolom = 7;
        double error=0;
        double error2 = 0;
        double MSE;
        double [][] data = new double [kolom][baris];
        String[][] train = new String[kolom][baris];
        int maxEpoch = 10;
        int epoch = 0;
        double x1, x2, x3, x4, x5, x6, target; //buat input summing function    
        double d2, db2;
        double[] d1 = new double[hn]; //menghitung d1
        
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
            Logger.getLogger(ANN2.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BiffException ex) {
            Logger.getLogger(ANN2.class.getName()).log(Level.SEVERE, null, ex);
        }

        //preprocessing
        for (int i = 0; i < kolom; i++) {
            for (int j = 0; j < baris; j++) {
                if (train[0][j].equals("vhigh")) {
                    train[0][j] = "1";
                }
                if (train[0][j].equals("high")) {
                    train[0][j] = "0.75";
                }
                if (train[0][j].equals("med")) {
                    train[0][j] = "0.50";
                }
                if (train[0][j].equals("low")) {
                    train[0][j] = "0.25";
                }
                if (train[1][j].equals("vhigh")) {
                    train[1][j] = "1.0";
                }
                if (train[1][j].equals("high")) {
                    train[1][j] = "0.75";
                }
                if (train[1][j].equals("med")) {
                    train[1][j] = "0.50";
                }
                if (train[1][j].equals("low")) {
                    train[1][j] = "0.25";
                }
                if (train[2][j].equals("2")) {
                    train[2][j] = "0.25";
                }
                if (train[2][j].equals("3")) {
                    train[2][j] = "0.50";
                }
                if (train[2][j].equals("4")) {
                    train[2][j] = "0.75";
                }
                if (train[2][j].equals("5more")) {
                    train[2][j] = "1.0";
                }
                if (train[3][j].equals("2")) {
                    train[3][j] = "0.33";
                }
                if (train[3][j].equals("4")) {
                    train[3][j] = "0.66";
                }
                if (train[3][j].equals("more")) {
                    train[3][j] = "1.0";
                }
                if (train[4][j].equals("small")) {
                    train[4][j] = "0.33";
                }
                if (train[4][j].equals("med")) {
                    train[4][j] = "0.66";
                }
                if (train[4][j].equals("big")) {
                    train[4][j] = "1.0";
                }
                if (train[5][j].equals("low")) {
                    train[5][j] = "0.33";
                }
                if (train[5][j].equals("med")) {
                    train[5][j] = "0.66";
                }
                if (train[5][j].equals("high")) {
                    train[5][j] = "1";
                }
                if (train[6][j].equals("unacc")) {
                    train[6][j] = "0.25";
                }
                if (train[6][j].equals("acc")) {
                    train[6][j] = "0.50";
                }
                if (train[6][j].equals("good")) {
                    train[6][j] = "0.75";
                }
                if (train[6][j].equals("vgood")) {
                    train[6][j] = "1.0";
                }
            }
        }

        //pindah dari string ke  double
        for (int i = 0; i < kolom; i++) {
            for (int j = 0; j < baris; j++) {
                data[i][j] = Double.parseDouble(train[i][j]);
            }
        }
        
        // output 
//        for (int i = 0; i < baris; i++) {
//            for (int j = 0; j < kolom; j++) {
//                  System.out.print(data[j][i]+" ");
//            }
//             System.out.println(" ");
//        }
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
       
        
        while(epoch < 100){
             
            //ambil data
            for (int i = 0; i < baris; i++) { //baris
                x1=data[0][i];
                x2=data[1][i];
                x3=data[2][i];
                x4=data[3][i];
                x5=data[4][i];
                x6=data[5][i];
                target=data[6][i];
                
                //hitung maju
                double temp = 0;
                for (int j = 0; j < hn; j++) {
                   y[j]=x1*w1[j]+x2*w2[j]+x3*w3[j]+x4*w4[j]+x5*w5[j]+x6*w6[j]+b[j]; //summing function hidden
                   yy[j]=1/(1+(Math.exp(-y[j]))); //aktifasi sigmoid hidden
                   temp = yy[j] * Woutput[j] + temp;  //summing ke output 
                }
                
                y2 = temp + boutput; //summing function output
                output=1/(1+(Math.exp(-y2))); //aktifasi sigmoid output
                
                error = target - output;
                error2= error2 + Math.pow(error, 2); //total error kuadrat
                //System.out.println(i+" "+error2 );
                //hitung mundur 
                
                for (int j = 0; j < hn; j++) { //hidden dw, bias , woutput , bias output
                    dw1[j]=error*(1-(Math.pow(output, 2))) * Woutput[j] * (1-(Math.pow(yy[j],2)))*x1*alpha; // error * output^2 * bobot hidden ke ouput neuron i * output hidden neuron ke i ^ 2 * input * alpha
                    dw2[j]=error*(1-(Math.pow(output, 2))) * Woutput[j] * (1-(Math.pow(yy[j],2)))*x2*alpha;
                    dw3[j]=error*(1-(Math.pow(output, 2))) * Woutput[j] * (1-(Math.pow(yy[j],2)))*x3*alpha;
                    dw4[j]=error*(1-(Math.pow(output, 2))) * Woutput[j] * (1-(Math.pow(yy[j],2)))*x4*alpha;
                    dw5[j]=error*(1-(Math.pow(output, 2))) * Woutput[j] * (1-(Math.pow(yy[j],2)))*x5*alpha;
                    dw6[j]=error*(1-(Math.pow(output, 2))) * Woutput[j] * (1-(Math.pow(yy[j],2)))*x6*alpha;
                    dwoutput[j]=error*(1-(Math.pow(output, 2)))*Woutput[j]*alpha;
                    db[j]=error*(1-(Math.pow(output, 2))) * Woutput[j] * yy[j]*alpha;
//                    
                    
//                    dw1[j]=error*Math.pow((1-output),2) * Woutput[j] * Math.pow((1-yy[j]),2)*x1*alpha; // error * output^2 * bobot hidden ke ouput neuron i * output hidden neuron ke i ^ 2 * input * alpha
//                    dw2[j]=error*Math.pow((1-output),2) * Woutput[j] * Math.pow((1-yy[j]),2)*x2*alpha;
//                    dw3[j]=error*Math.pow((1-output),2) * Woutput[j] * Math.pow((1-yy[j]),2)*x3*alpha;
//                    dw4[j]=error*Math.pow((1-output),2) * Woutput[j] * Math.pow((1-yy[j]),2)*x4*alpha;
//                    dw5[j]=error*Math.pow((1-output),2) * Woutput[j] * Math.pow((1-yy[j]),2)*x5*alpha;
//                    dw6[j]=error*Math.pow((1-output),2) * Woutput[j] * Math.pow((1-yy[j]),2)*x6*alpha;
//                    dwoutput[j]=error*Math.pow((1-output),2)*Woutput[j]*alpha;
//                    db[j]=error*Math.pow((1-output),2) * Woutput[j] * yy[j]*alpha;
                }
               
                     db2 = error*(1-(Math.pow(output, 2)))*alpha;
                
                
                //update bobot
                
                for (int j = 0; j < hn; j++) {
                    w1[j]=dw1[j]+w1[j];
                    w2[j]=dw2[j]+w2[j];
                    w3[j]=dw3[j]+w3[j];
                    w4[j]=dw4[j]+w4[j];
                    w5[j]=dw1[j]+w5[j];
                    w6[j]=dw1[j]+w6[j];
                    Woutput[j]=dwoutput[j]+Woutput[j];
                    b[j]=db[j]+b[j];
                    
                }
                
                boutput= db2 + boutput;
            }
            
            MSE = error2/baris;
            System.out.println((epoch+1)+" MSE : "+MSE);
            epoch ++;
           
            
        }
        
        
        
    }
    
}
