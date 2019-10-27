package ann;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TrainedANN {
	 static final int THRESHOLD = 100000;
	 private static NeuralNet ANN=new NeuralNet();;
	 private static double sse;
	 
	 public TrainedANN() {
		
		 
	 }
	 public static void main(String[] args) throws IOException {
		train();
	 }
	 
	 public static void train() throws IOException{
		
//		    ANN.setSse(2.5);
//	        ANN.constructNN(2, 3, 1);
//	       
//	     
//	        BufferedReader br = null;
//
//	        try {
//				br = new BufferedReader(new FileReader("/Users/qinchen/Codes/GreenRock/src/ann/financeTrain.csv"));
//			} catch (FileNotFoundException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//
//	        String line = "";
//	        String everyLine = "";
//	        int count = 0;
//
//	        List<String> allString = new ArrayList<>();
//
//	        try {
//				while ((line = br.readLine()) != null) {
//				    everyLine = line;
//				    allString.add(everyLine);
//				    count++;
//				}
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//	        //clean if the first line is not input but title
//	        String firstLine = allString.get(0);
//	        String[] slist = firstLine.split(",");
//	        int start = 0;
//	        for (String s : slist) {
//	            try {
//	                Double.parseDouble(s);
//	            } catch (NumberFormatException ex) {
//	                start = 1;
//	            }
//	        }
//	        double[][] input = new double[count - start][2];
//	        double[][] output = new double[count - start][1];
//	        for (int i = start; i < count; i++) {
//	            String cur = allString.get(i);
//	            String[] val = cur.split(",");
//
//	            if (val.length != 2 + 1) {
//	                throw new IllegalArgumentException(
//	                        "numbers of neurons and input file does not match. Please try again. ");
//	            }
//	            for (int j = 0; j < 2; j++) {
//	                input[i - start][j] = Double.parseDouble(val[j]);
//	            }
//	            for (int j = 0; j < 1; j++) {
//	                output[i - start][j] = Double.parseDouble(val[j + 2]);
//	            }
//
//	            ANN.setInputs(input);
//	            ANN.setTargetOutputs(output);
//	            //read weight
//	            
//	            
//	            
//	            //train ann
//	            long randSeed = 10;
//
//	            Random rand = new Random(randSeed);
//	            int randNum;
//	            int w;
//	           
//	            File outFile = new File("/Users/qinchen/Codes/GreenRock/src/ann/financeLog.csv");
//	            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
//	            Date currentTime = new Date();
//	            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	            String dateString = formatter.format(currentTime);
//
//	           
//	            for (InputNeuron n : ANN.getInputLayer().neuronList) {
//	                for (InputEdge e : n.getOutEdges()) {
//	                    writer.write(e.getName() + "," + e.getWeight() + "\n");
//
//	                }
//	            }
//	            //writer.write("Edges between Hidden and Output Layer\n");
//	            for (HiddenNeuron n : ANN.getHiddenLayer().neuronList) {
//	                for (OutputEdge e : n.getOutEdges()) {
//	                    writer.write(e.getName() + "," + e.getWeight() + "\n");
//
//	                }
//	            }
//	            writer.newLine();
//	            writer.write("input\n");
//	            for (int k = 0; k < ANN.getInputs().length; k++) {
//	                for (int g = 0; g < ANN.getInputs()[0].length; g++) {
//	                    writer.write(ANN.getInputs()[k][g] + ",");
//	                }
//	                writer.newLine();
//	            }
//
//	            double startTime = System.nanoTime() / 1000000000.0;
//	            for (int j = 0; j < THRESHOLD; j++) {
//
//	                randNum = rand.nextInt(4);
//	                w = randNum;
//	                ANN.setSse(0);
//	                //int length = ann.getInputs().length;
//	                while (w < randNum + 4) {
//	                	ANN.learnNet(w % 4); //ioline
//	                    w++;
//	                }
//	                System.out.printf("Current SSE: %f\n", ANN.getSse());
//	                ANN.setTotalSSE(ANN.getTotalSSE() + ANN.getSse());
//	                dateString = formatter.format(currentTime);
//	                writer.write("EPOCH " + j + "," + dateString + "\n");
//	                writer.write("Edges between Input and Hidden Layer\n");
//	                for (InputNeuron n : ANN.getInputLayer().neuronList) {
//	                    for (InputEdge e : n.getOutEdges()) {
//	                        writer.write(e.getName() + "," + e.getWeight() + "\n");
//
//	                    }
//	                }
//	                writer.newLine();
//	                writer.write("Edges between Hidden and Output Layer\n");
//	                for (HiddenNeuron n : ANN.getHiddenLayer().neuronList) {
//	                    for (OutputEdge e : n.getOutEdges()) {
//	                        writer.write(e.getName() + "," + e.getWeight() + "\n");
//
//	                    }
//	                }
//	                writer.newLine();
//	                System.out.println("----");
//	                if (ANN.getSse() < sse) {
//	                    System.out.printf("Took %d Epochs to get a low enough SSE!\n", j);
//	                    ANN.setEpoch(j);
//	                    break;
//	                }
//	            }
//	           
//	            writer.close();
//	            System.out.print("done");
	            
	            
//	        }
		 readDate();
	 
	       
		 
	 }
	 private static void readDate() {
		 
	 }
}
