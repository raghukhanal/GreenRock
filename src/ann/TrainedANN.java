package ann;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class TrainedANN {
	 static final int THRESHOLD = 100000;
	
	 private static double sse;
	 
	 private static  NeuralNet ANN;
	 
	 public TrainedANN(){
		 try {
			 ANN= readObj();
		 }catch(Exception e) {
			 ANN= new NeuralNet();
			 ANN = train(ANN);
			 try {
				writeObj(ANN);
			 } catch (Exception e1) {
				e1.printStackTrace();
			 }
		 }
		 
	 }
	 
	 
	 public static NeuralNet getANN() {
		 try {
			 return readObj();
		 }catch(Exception e) {
			 train(ANN);
			 try {
				writeObj(ANN);
			 } catch (Exception e1) {
				e1.printStackTrace();
			 }
		 }
		 return ANN;
	 }
	 
	 public static NeuralNet readObj() throws FileNotFoundException, IOException, ClassNotFoundException {

	        ObjectInputStream objectInputStream = new ObjectInputStream(
	                new FileInputStream("ANN.txt"));

	        Object object = objectInputStream.readObject();
	        ANN = (NeuralNet) object;
	        objectInputStream.close();
	        return ANN;

	 }
	 public static void writeObj(NeuralNet ANN) throws FileNotFoundException, IOException {

	        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
	                new FileOutputStream("ANN.txt"));
	        objectOutputStream.writeObject(ANN);
	        objectOutputStream.close();

	  }
	 
	
	 private static NeuralNet train(NeuralNet ANN){

		 try {
			readDate(ANN);
			runANN(ANN);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ANN;
		 
	 }
	 private static void runANN(NeuralNet ANN) throws IOException {
		 long randSeed = 10;

	        Random rand = new Random(randSeed);
	        int randNum;
	        int i;
	       
	        String path = "/Users/qinchen/Codes/GreenRock/src/ann/FinanceLog.csv";
	        File outFile = new File(path);
	        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
	        Date currentTime = new Date();
	        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String dateString = formatter.format(currentTime);

	       
	        writer.write(dateString);
	        writer.newLine();
	        writer.write("Network parameters and config info\n");
	        writer.write("Edges between Input and Hidden Layer\n");
	        for (InputNeuron n : ANN.getInputLayer().neuronList) {
	            for (InputEdge e : n.getOutEdges()) {
	                writer.write(e.getName() + "," + e.getWeight() + "\n");

	            }
	        }
	        writer.write("Edges between Hidden and Output Layer\n");
	        for (HiddenNeuron n : ANN.getHiddenLayer().neuronList) {
	            for (OutputEdge e : n.getOutEdges()) {
	                writer.write(e.getName() + "," + e.getWeight() + "\n");

	            }
	        }
	        writer.newLine();
	        writer.write("input\n");
	        for (int k = 0; k < ANN.getInputs().length; k++) {
	            for (int g = 0; g < ANN.getInputs()[0].length; g++) {
	                writer.write(ANN.getInputs()[k][g] + ",");
	            }
	            writer.newLine();
	        }

	        double startTime = System.nanoTime() / 1000000000.0;
	        for (int j = 0; j < THRESHOLD; j++) {

	            randNum = rand.nextInt(4);
	            i = randNum;
	            ANN.setSse(0);
	            //int length = ann.getInputs().length;
	            while (i < randNum + 4) {
	            	ANN.learnNet(i % 4); //ioline
	                i++;
	            }
	            System.out.printf("Current SSE: %f\n", ANN.getSse());
	            ANN.setTotalSSE(ANN.getTotalSSE() + ANN.getSse());
	            dateString = formatter.format(currentTime);
	            writer.write("EPOCH " + j + "," + dateString + "\n");
	            writer.write("Edges between Input and Hidden Layer\n");
	            for (InputNeuron n : ANN.getInputLayer().neuronList) {
	                for (InputEdge e : n.getOutEdges()) {
	                    writer.write(e.getName() + "," + e.getWeight() + "\n");

	                }
	            }
	            writer.newLine();
	            writer.write("Edges between Hidden and Output Layer\n");
	            for (HiddenNeuron n : ANN.getHiddenLayer().neuronList) {
	                for (OutputEdge e : n.getOutEdges()) {
	                    writer.write(e.getName() + "," + e.getWeight() + "\n");

	                }
	            }
	            writer.newLine();
	            System.out.println("----");
	            if (ANN.getSse() < sse) {
	                System.out.printf("Took %d Epochs to get a low enough SSE!\n", j);
	                ANN.setEpoch(j);
	                break;
	            }
	        }
	        double endTime = System.nanoTime() / 1000000000.0;
	        double time = (endTime - startTime);
	        ANN.setTime(time);
	        
	        writer.close();
	        System.out.println();
		
	}
	private static void readDate(NeuralNet ANN) throws IOException {

	        ANN.setSse(2.5);

	        ANN.constructNN(2, 3, 1);

	        //System.out.print("enter the PATH of training data file： ");

	        String name = "/Users/qinchen/Codes/GreenRock/src/ann/FinanceTrain.csv";
	        
	        File csv = new File(
	                name);
	        BufferedReader br = null;

	        br = new BufferedReader(new FileReader(csv));

	        String line = "";
	        String everyLine = "";
	        int count = 0;

	        List<String> allString = new ArrayList<>();

	        while ((line = br.readLine()) != null) {
	            everyLine = line;
	            allString.add(everyLine);
	            count++;
	        }

	        //clean if the first line is not input but title
	        String firstLine = allString.get(0);
	        String[] slist = firstLine.split(",");
	        int start = 0;
	        for (String s : slist) {
	            try {
	                Double.parseDouble(s);
	            } catch (NumberFormatException ex) {
	                start = 1;
	            }
	        }
	        int inputNeuron=2;
	        int OutputNeuron=1;
	        double[][] input = new double[count - start][inputNeuron];
	        double[][] output = new double[count - start][OutputNeuron];
	        for (int i = start; i < count; i++) {
	            String cur = allString.get(i);
	            String[] val = cur.split(",");

	            if (val.length != inputNeuron + OutputNeuron) {
	                throw new IllegalArgumentException(
	                        "numbers of neurons and input file does not match. Please try again. ");
	            }
	            for (int j = 0; j < inputNeuron; j++) {
	                input[i - start][j] = Double.parseDouble(val[j]);
	            }
	            for (int j = 0; j < OutputNeuron; j++) {
	                output[i - start][j] = Double.parseDouble(val[j + inputNeuron]);
	            }

	            ANN.setInputs(input);
	            ANN.setTargetOutputs(output);
	        }
	 }
	 
	 
}
