/* *****************************************
 * CSCI205 - Software Engineering and Design
 *
 *
 * Name: Zoe Chen, Horio Hu
 * Date: 3/6/2019
 * Time:
 *
 * Project:
 * Package: hw01
 * File: NeuralNet
 * Description:
 *
 * ****************************************
 */
package ann;

import java.io.Serializable;


public class NeuralNet implements Serializable {

    private InputLayer inputLayer;
    private HiddenLayer hiddenLayer;
    private OutputLayer outputLayer;
    private double[][] inputs;
    private double[][] targetOutputs;
    private double sse = 0; // sum of the squared error
    private double[] errors;
    private int epoch = -1;
    private double time;
    private double totalSSE = 0;

    /**
     * Construct a new Neural Network based on number of input, hidden, and
     * output nodes
     *
     * @param numInput - Number of input nodes
     * @param numHidden - Number of hidden Nodes
     * @param numOutput - Number of Output Nodes
     */
    public void constructNN(int numInput, int numHidden, int numOutput) {
        this.inputLayer = new InputLayer(numInput, "I1-");
        this.hiddenLayer = new HiddenLayer(numHidden, "H1-");
        this.outputLayer = new OutputLayer(numOutput, "O1-");
        this.inputLayer.connectLayer(this.hiddenLayer);
        this.hiddenLayer.connectLayer(this.outputLayer);
    }

    /**
     * Makes our Neural Net learn by firing neurons (forward) and then changing
     * weights according to error (back-propagation)
     *
     * @param ioLine - Specifies line in array that will be used for
     * input/output
     */
    public void learnNet(int ioLine) {
        //Fire out neurons

        this.getInputLayer().fireNeurons(this.getInputs()[ioLine]);

        this.hiddenLayer.fireNeurons();

        double[] targetOutput;
        targetOutput = this.getTargetOutputs()[ioLine];
        this.outputLayer.setTargetOutput(targetOutput);

        //Calculated weight deltas based on error
        this.outputLayer.calculateWeightDeltas();
        this.hiddenLayer.calculateWeightDeltas();

        //We only assume one output node when calculating our SSE
        this.sse = (this.getSse()
                    + this.outputLayer.getOutputErrors()[0]
                      * this.outputLayer.getOutputErrors()[0]);

        //Now use deltas to learn
        this.outputLayer.learn();
        this.hiddenLayer.learn();
    }

    /**
     *
     * @param hiddenLayer
     */
    public void setHiddenLayer(HiddenLayer hiddenLayer) {
        this.hiddenLayer = hiddenLayer;
    }

    /**
     *
     * @return
     */
    public HiddenLayer getHiddenLayer() {
        return hiddenLayer;
    }

    /**
     *
     * @return
     */
    public InputLayer getInputLayer() {
        return inputLayer;
    }

    /**
     *
     * @return
     */
    public OutputLayer getOutputLayer() {
        return outputLayer;
    }

    /**
     *
     * @return
     */
    public double[][] getInputs() {
        return inputs;
    }

    /**
     *
     * @return
     */
    public double[][] getTargetOutputs() {
        return targetOutputs;
    }

    /**
     *
     * @return
     */
    public double[] getErrors() {
        return errors;
    }

    /**
     *
     * @param inputLayer
     */
    public void setInputLayer(InputLayer inputLayer) {
        this.inputLayer = inputLayer;
    }

    /**
     *
     * @param outputLayer
     */
    public void setOutputLayer(OutputLayer outputLayer) {
        this.outputLayer = outputLayer;
    }

    /**
     *
     * @return Sum Squared Error
     */
    public double getSse() {
        return sse;
    }

    /**
     *
     * @param sse - Sum of Squared Error
     */
    public void setSse(double sse) {
        this.sse = sse;
    }

    /**
     *
     * @param targetOutputs
     */
    public void setTargetOutputs(double[][] targetOutputs) {
        this.targetOutputs = targetOutputs;
    }

    /**
     *
     * @param errors
     */
    public void setErrors(double[] errors) {
        this.errors = errors;
    }

    /**
     *
     * @param inputs
     */
    public void setInputs(double[][] inputs) {
        this.inputs = inputs;
    }

    public void setEpoch(int e) {
        this.epoch = e;
    }

    public int getEpoch() {
        return this.epoch;
    }

    public void setTime(double e) {
        this.time = e;
    }

    public double getTime() {
        return this.time;
    }

    public void setTotalSSE(double e) {
        this.totalSSE = e;
    }

    public double getTotalSSE() {
        return this.totalSSE;
    }

}
