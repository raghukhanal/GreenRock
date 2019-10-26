/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2016
 *
 * Name: Zoe Chen, Horio Hu
 * Date: 3/6/2019
 * Time:
 *
 * Project: ANN
 * Package: hw01
 * File: OutputLayer
 * Description:
 *
 * ****************************************
 */
package ann;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author cld028
 */
public class OutputLayer implements Serializable {

    /**
     *
     *
     */
    protected ArrayList<OutputNeuron> neuronList;
    private double momentum = 0.5;

    /**
     *
     */
    protected double alpha = 0.2;

    /**
     *
     */
    protected String name;

    private double[] targetOutput;
    private double[] outputErrors;

    OutputLayer(int numNeurons) {
        this.neuronList = this.createNeurons(numNeurons);
    }

    OutputLayer(int numNeurons, String layerID) {
        this.neuronList = this.createNeurons(numNeurons, layerID);
        this.name = layerID;
    }

    OutputLayer(int numNeurons, String id, double alpha) {
        this.neuronList = this.createNeurons(numNeurons, id);
        this.name = id;
        this.alpha = alpha;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @param alpha
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    /**
     *
     * @return
     */
    public ArrayList<OutputNeuron> getNeurons() {
        return neuronList;
    }

    /**
     *
     * @param Neurons
     */
    public void setNeurons(ArrayList<OutputNeuron> Neurons) {
        this.neuronList = Neurons;
    }

    /**
     *
     * @return
     */
    public double getAlpha() {
        return (this.alpha);
    }

    /**
     *
     * @return
     */
    public double[] getTargetOutput() {
        return targetOutput;
    }

    /**
     *
     * @param targetOutput
     */
    public void setTargetOutput(double[] targetOutput) {
        this.targetOutput = targetOutput;
    }

    /**
     *
     * @return
     */
    public double[] getOutputErrors() {
        return outputErrors;
    }

    /**
     *
     * @param outputErrors
     */
    public void setOutputErrors(double[] outputErrors) {
        this.outputErrors = outputErrors;
    }

    /**
     *
     * @param numNeurons
     * @return
     */
    public ArrayList<OutputNeuron> createNeurons(int numNeurons) {
        ArrayList<OutputNeuron> myNeurons = new ArrayList<>();
        OutputNeuron tempNeuron;
        for (int i = 0; i < numNeurons; i++) {
            tempNeuron = new OutputNeuron(i);
            if (this.alpha != -1) {
                tempNeuron.setAlpha(this.alpha);
            }
            else {
                tempNeuron.setAlpha(this.alpha);
            }
            myNeurons.add(tempNeuron);
        }
        return (myNeurons);
    }

    /**
     *
     * @param numNeurons
     * @param layerID
     * @return
     */
    public ArrayList<OutputNeuron> createNeurons(int numNeurons, String layerID) {
        ArrayList<OutputNeuron> myNeurons = new ArrayList<>();
        OutputNeuron tempNeuron;
        for (int i = 0; i < numNeurons; i++) {
            tempNeuron = new OutputNeuron(layerID + String.valueOf(i));
            if (this.alpha != -1) {
                tempNeuron.setAlpha(this.alpha);
            }
            else {
                tempNeuron.setAlpha(this.alpha);
            }
            myNeurons.add(tempNeuron);
        }
        return (myNeurons);
    }

    /**
     *
     * @param nextLayer
     */
    public void connectLayer(OutputLayer nextLayer) {
        throw new UnsupportedOperationException(
                "Output layer shouldn't be connecting!");
    }

    /**
     *
     */
    public void calculateWeightDeltas() {

        if (this.targetOutput.length != this.neuronList.size()) {
            System.out.println("target output" + this.targetOutput.length);
            System.out.println("neruon list" + this.neuronList.size());
            throw new RuntimeException(
                    "Target output should match number of output neurons!");
        }
        this.calculateErrors();
        this.calculateDeltas(this.neuronList, this.outputErrors);
    }

    /**
     *
     */
    public void learn() {
        this.learn(this.neuronList, this.outputErrors);
    }

    /**
     * calculate error between target output and real output E(t)
     */
    private void calculateErrors() {
        this.outputErrors = new double[this.targetOutput.length];
        double neuronOutput = 0;
        for (int i = 0; i < this.targetOutput.length; i++) {
            OutputNeuron currNeuron = this.neuronList.get(i);
            neuronOutput = currNeuron.getNetInput();
            neuronOutput = currNeuron.calcOutput(
                    neuronOutput - currNeuron.getTheta());
            this.outputErrors[i] = this.targetOutput[i] - neuronOutput;
        }
    }

    /**
     * logistic equation
     *
     * @param netInput
     * @return
     */
    public double calcOutput(double netInput) {
        double outputVal = 1 / (1 + Math.exp(-netInput));
        return outputVal;
    }

    /**
     * calculate derivate of output
     *
     * @param netInput
     * @return
     */
    public double calcDerivOutput(double netInput) {
        double outputVal = calcOutput(netInput);

        //the simplified derivative of our logistic eqn - y' = y*(1-y)
        return (outputVal * (1 - outputVal));
    }

    private void setWeightDeltas(ArrayList<OutputEdge> oldWeights, double error,
                                 double alpha) {
        double weightCorrection = 0;
        double neuronOut = 0;
        for (OutputEdge edge : oldWeights) {
            neuronOut = edge.getFrom().calcOutput(
                    edge.getFrom().getNetInput() - edge.getFrom().getTheta());
            edge.setErrorGradient(error);
            weightCorrection = alpha * neuronOut * error + edge.previousWeight * momentum;
            edge.prevWeightDelta = edge.getWeight();
            edge.setWeightDelta(weightCorrection);
        }
    }

    /**
     *
     * @param neuronList
     * @param errors
     */
    public void calculateDeltas(ArrayList<OutputNeuron> neuronList,
                                double[] errors) {
        double errorGrad = 0;
        double neuronNet = 0;
        double neuronOut = 0;
        double deltaTheta = 0;
        for (int i = 0; i < neuronList.size(); i++) {
            OutputNeuron currNeuron = neuronList.get(i);
            neuronNet = currNeuron.getNetInput();
            neuronOut = currNeuron.calcOutput(neuronNet - currNeuron.getTheta());
            errorGrad = neuronOut * (1 - neuronOut) * errors[i]; //small delta
            this.setWeightDeltas(currNeuron.getInEdges(), errorGrad,
                                 neuronList.get(i).getAlpha());
            deltaTheta = currNeuron.getAlpha() * -1 * errorGrad;
            currNeuron.setTheta(deltaTheta + currNeuron.getTheta());
            //Reset the net input
            currNeuron.setNetInput(0);
            currNeuron.setErrorGradient(errorGrad);
        }
    }

    /**
     * This function update the weights for in-edges connected to neurons
     *
     * @param neurons (output)
     * @param errors
     */
    public void learn(ArrayList<OutputNeuron> neurons, double[] errors) {
        for (int i = 0; i < neurons.size(); i++) {
            OutputNeuron currNeuron = neurons.get(i);
            double output = currNeuron.calcOutput(
                    currNeuron.getNetInput() - currNeuron.getTheta());
            for (OutputEdge e : currNeuron.getInEdges()) {
                double newWeight = e.getWeight() + e.getWeightDelta();
                e.setWeight(newWeight);
            }
        }

    }

    /**
     * assign weight [-0.5,0.5]
     *
     * @return
     */
    public double assignWeight() {
        Random rnd = new Random();
        boolean pos = rnd.nextBoolean();
        double w;
        while (true) {
            w = rnd.nextDouble();
            if (w <= 0.5) {
                break;
            }
        }
        if (pos) {
            return w;
        }
        else {
            return -w;
        }
    }

}
