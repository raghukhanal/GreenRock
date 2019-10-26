
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
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;


public class ANNClient {

    static Scanner in = new Scanner(System.in);
    static final int THRESHOLD = 100000;

    public static void main(String[] args) throws IOException {

        boolean trainingMode = false;
        NeuralNet ANN = new NeuralNet();
        while (true) {

            System.out.print(
                    "enter 1 for training mode, enter 2 for classification mode, enter something else to quit the program: ");
            String choice = in.next();
            if (choice.equals("1")) {
                try {
                    System.out.println("---- training mode ----");
                    trainingMode = true;
                    readInput(ANN);

                    System.out.print(
                            "Would you like to read weights from external file? Y/N ");
                    String yn = in.next();
                    if (yn.equalsIgnoreCase("Y")) {
                        readWeight(ANN);
                    }
                    displayANN(ANN);
                    runANN(ANN, ANN.getSse());
                    if (ANN.getEpoch() == -1) {
                        System.out.println(
                                "max number of epochs has been reach, unable to train, do you want to start over? ");
                        String respond = in.next();
                        if (!respond.equalsIgnoreCase("yes")) {
                            ANN.setEpoch(THRESHOLD);
                            writeOutputs(ANN);
                            writeObj(ANN);
                            System.out.println("Done.");
                        }
                    }
                    else {
                        writeOutputs(ANN);
                        writeObj(ANN);
                        System.out.println("Done.");
                    }

                } catch (FileNotFoundException ex) {
                    System.out.println("file not found, please try again. ");

                } catch (IOException ex) {
                    System.out.println("io excaption, please try again. ");
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex + "please try again. ");
                }

            }

            else if (choice.equals("2")) {
                try {
                    System.out.println("---- classification mode----");
                    if (trainingMode) {
                        readInput(ANN);
                        runANN(ANN, ANN.getSse());
                        writeOutputs(ANN);

                    }
                    else {
                        readInput(ANN);
                        readWeight(ANN);
                        runANN(ANN, ANN.getSse());
                        writeOutputs(ANN);

                    }
                    writeObj(ANN);
                    System.out.println("Done.");
                } catch (FileNotFoundException ex) {
                    System.out.println("file not found, please try again. ");
                } catch (IOException ex) {
                    System.out.println("io excaption, please try again. ");
                } catch (IllegalArgumentException ex) {
                    System.out.println(ex + "please try again. ");
                }

            }
            else {
                break;
            }

        }
        System.out.println("Goodbye.");

    }

    /**
     * train the ANN
     */
    public static void runANN(NeuralNet ann,
                              double sse) throws IOException {
        //Seed our random so that we can repeat our simulations!
        long randSeed = 10;

        Random rand = new Random(randSeed);
        int randNum;
        int i;
        //   /Users/qinchen/csci205_hw_sp19/src/hw02/ANNTrainingLog.csv
        System.out.print("enter the name of your ann: ");
        String name = in.next();
        System.out.print("enter the PATH of the training log file: ");
        String path = in.next();
        File outFile = new File(path);
        BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);

        writer.write(name + ",");
        writer.write(dateString);
        writer.newLine();
        writer.write("Network parameters and config info\n");
        writer.write("Edges between Input and Hidden Layer\n");
        for (InputNeuron n : ann.getInputLayer().neuronList) {
            for (InputEdge e : n.getOutEdges()) {
                writer.write(e.getName() + "," + e.getWeight() + "\n");

            }
        }
        writer.write("Edges between Hidden and Output Layer\n");
        for (HiddenNeuron n : ann.getHiddenLayer().neuronList) {
            for (OutputEdge e : n.getOutEdges()) {
                writer.write(e.getName() + "," + e.getWeight() + "\n");

            }
        }
        writer.newLine();
        writer.write("input\n");
        for (int k = 0; k < ann.getInputs().length; k++) {
            for (int g = 0; g < ann.getInputs()[0].length; g++) {
                writer.write(ann.getInputs()[k][g] + ",");
            }
            writer.newLine();
        }

        double startTime = System.nanoTime() / 1000000000.0;
        for (int j = 0; j < THRESHOLD; j++) {

            randNum = rand.nextInt(4);
            i = randNum;
            ann.setSse(0);
            //int length = ann.getInputs().length;
            while (i < randNum + 4) {
                ann.learnNet(i % 4); //ioline
                i++;
            }
            System.out.printf("Current SSE: %f\n", ann.getSse());
            ann.setTotalSSE(ann.getTotalSSE() + ann.getSse());
            dateString = formatter.format(currentTime);
            writer.write("EPOCH " + j + "," + dateString + "\n");
            writer.write("Edges between Input and Hidden Layer\n");
            for (InputNeuron n : ann.getInputLayer().neuronList) {
                for (InputEdge e : n.getOutEdges()) {
                    writer.write(e.getName() + "," + e.getWeight() + "\n");

                }
            }
            writer.newLine();
            writer.write("Edges between Hidden and Output Layer\n");
            for (HiddenNeuron n : ann.getHiddenLayer().neuronList) {
                for (OutputEdge e : n.getOutEdges()) {
                    writer.write(e.getName() + "," + e.getWeight() + "\n");

                }
            }
            writer.newLine();
            System.out.println("----");
            if (ann.getSse() < sse) {
                System.out.printf("Took %d Epochs to get a low enough SSE!\n", j);
                ann.setEpoch(j);
                break;
            }
        }
        double endTime = System.nanoTime() / 1000000000.0;
        double time = (endTime - startTime);
        ann.setTime(time);
        writer.write("Training Ended\n");
        writer.write(formatter.format(currentTime));
        writer.close();
        System.out.println();
    }

    /**
     * read weight from a file
     */
    private static void readWeight(NeuralNet ann) throws FileNotFoundException, IOException {

        Scanner in = new Scanner(System.in);
        System.out.print(
                "Enter the PATH name of the weight file: ");
        //   /Users/qinchen/csci205_hw_sp19/src/hw02/weight.csv
        String path = in.next();
        File weight = new File(path);
        BufferedReader br = null;

        br = new BufferedReader(new FileReader(weight));

        String line = "";
        String everyLine = "";
        int count = 0;

        List<String> allString = new ArrayList<>();

        while ((line = br.readLine()) != null) {
            everyLine = line;
            allString.add(everyLine);
            count++;
        }
        int numOfNeurons = ann.getInputLayer().getNeurons().size() + ann.getHiddenLayer().getNeurons().size();
        if (count != numOfNeurons) {
            throw new IllegalArgumentException(
                    "Number of weights does not match. ");
        }
        for (int i = 0; i < ann.getInputLayer().getNeurons().size(); i++) {
            InputNeuron n = ann.getInputLayer().getNeurons().get(i);
            String[] w = allString.get(i).split(",");
            if (n.getOutEdges().size() != w.length) {
                throw new IllegalArgumentException(
                        "Number of weights does not match. ");
            }
            for (int j = 0; j < w.length; j++) {
                n.getOutEdges().get(j).setWeight(Double.parseDouble(w[j]));
            }
        }
        for (int i = 0; i < ann.getHiddenLayer().getNeurons().size(); i++) {
            String[] w = allString.get(
                    i + ann.getInputLayer().getNeurons().size()).split(",");
            HiddenNeuron n = ann.getHiddenLayer().getNeurons().get(i);
            if (w.length != n.getOutEdges().size()) {
                throw new IllegalArgumentException(
                        "Number of weights does not match. ");
            }
            for (int j = 0; j < n.getOutEdges().size(); j++) {
                n.getOutEdges().get(j).setWeight(Double.parseDouble(w[j]));
            }

        }
        br.close();
        in.close();

    }

    /**
     * display weight and training parameters
     *
     * @author Zoe Chen
     */
    private static void displayANN(NeuralNet ann) {
        System.out.println("display the related ANN information");
        System.out.println(
                "diaplaying the initial weights of edges between input neurons and hidden neurons. ");
        for (InputNeuron n : ann.getInputLayer().getNeurons()) {
            for (InputEdge e : n.getOutEdges()) {
                System.out.println(e.name + " : " + e.getWeight());
            }
        }
        System.out.println(
                "diaplaying the initial weights of edges between hidden neurons and output neurons. ");
        for (HiddenNeuron n : ann.getHiddenLayer().getNeurons()) {
            for (OutputEdge e : n.getOutEdges()) {
                System.out.println(e.name + " : " + e.getWeight());
            }
        }
        System.out.println(
                "displaying the learning parameter used to train the network");
        for (InputNeuron n : ann.getInputLayer().getNeurons()) {
            System.out.println(n.name + " alpha : " + n.getAlpha());
            System.out.println(n.name + " theta: " + n.getTheta());
        }
        for (HiddenNeuron n : ann.getHiddenLayer().getNeurons()) {
            System.out.println(n.name + " alpha : " + n.getAlpha());
            System.out.println(n.name + " theta: " + n.getTheta());
        }
        for (OutputNeuron n : ann.getOutputLayer().getNeurons()) {
            System.out.println(n.name + " alpha : " + n.getAlpha());
            System.out.println(n.name + " theta: " + n.getTheta());
        }
        System.out.println("----done----");
        System.out.println();
    }

    /**
     * read inputs from user
     *
     * @author Zoe Chen
     */
    private static void readInput(NeuralNet ANN) throws FileNotFoundException, IOException {

        System.out.print("Number of input neurons: ");
        int inputNeuron = in.nextInt();
        System.out.print("Number of hidden neurons: ");
        int hiddenNeuron = in.nextInt();
        System.out.print("highest acceptable SSE: ");
        double sse = in.nextDouble();
        ANN.setSse(sse);
        System.out.print("Number of output neurons: ");
        int OutputNeuron = in.nextInt();
        ANN.constructNN(inputNeuron, hiddenNeuron, OutputNeuron);

        System.out.print("enter the PATH of training data file： ");

        String name = in.next();
        //  /Users/qinchen/csci205_hw_sp19/src/hw02/exampleAND.csv
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

    /**
     * write outputs to a file
     *
     * @author Zoe Chen
     */
    private static void writeOutputs(NeuralNet ANN) throws FileNotFoundException {
        PrintWriter out = null;

        System.out.print(
                "Enter the PATH name to store the results: ");
        String path = in.next();
        //   /Users/qinchen/csci205_hw_sp19/src/hw02/output
        out = new PrintWriter(path
        );
        String writeFiles = "";
        for (HiddenNeuron e : ANN.getHiddenLayer().getNeurons()) {
            for (InputEdge ie : e.getInEdges()) {
                writeFiles += ie.getName() + " : " + ie.getWeight() + "\n";
            }
            for (OutputEdge ed : e.getOutEdges()) {
                writeFiles += ed.getName() + " : " + ed.getWeight() + "\n";
            }
        }
        for (HiddenNeuron e : ANN.getHiddenLayer().getNeurons()) {
            writeFiles += e.getName() + " : " + e.getTheta() + "\n";;
        }
        for (OutputNeuron e : ANN.getOutputLayer().getNeurons()) {
            writeFiles += e.getName() + " : " + e.getTheta() + "\n";;
        }

        writeFiles = writeFiles + "number of epoch: " + ANN.getEpoch() + "\n";
        writeFiles += "time to complete training: " + ANN.getTime() + " seconds. \n";
        writeFiles += "average sse over the entire training data: " + ANN.getTotalSSE() / ANN.getEpoch();
        out.printf(writeFiles);
        out.close();

    }

    /**
     * write an object to the disk
     */
    public static void writeObj(NeuralNet ANN) throws FileNotFoundException, IOException {

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(
                new FileOutputStream("ANN.txt"));
        objectOutputStream.writeObject(ANN);
        objectOutputStream.close();

    }

    /**
     * read an object from the disk
     */
    public static void readObj(NeuralNet ANN) throws FileNotFoundException, IOException, ClassNotFoundException {

        ObjectInputStream objectInputStream = new ObjectInputStream(
                new FileInputStream("ANN.txt"));

        Object object = objectInputStream.readObject();
        ANN = (NeuralNet) object;
        objectInputStream.close();

    }

}
