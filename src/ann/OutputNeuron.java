/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2016
 *
 * Name: Zoe Chen, Horio Hu
 * Date: 3/6/2019
 * Time:
 *
 * Project: 205-FA16Class
 * Package: hw01
 * File: Neuron
 * Description:
 *
 * ****************************************
 */
package ann;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author cld028
 */
public class OutputNeuron extends Neuron {

    /**
     *
     */
    public final static double DEFAULTTHETA = 0.1;

    private ArrayList<OutputEdge> inEdgeList;
    private ArrayList<OutputEdge> outEdgeList;

    private double errorGradient;

    OutputNeuron(int idNum) {
        this.inEdgeList = new ArrayList<OutputEdge>();
        this.outEdgeList = new ArrayList<OutputEdge>();
        this.alpha = OutputNeuron.DEFAULTALPHA;
        this.assignTheta();
        this.name += String.valueOf(idNum);
    }

    OutputNeuron(String id) {
        this.inEdgeList = new ArrayList<OutputEdge>();
        this.outEdgeList = new ArrayList<OutputEdge>();
        this.alpha = OutputNeuron.DEFAULTALPHA;
        this.assignTheta();
        this.name += id;
    }

    /**
     *
     * @return
     */
    public double getTheta() {
        return theta;
    }

    /**
     *
     * @param theta
     */
    public void setTheta(double theta) {
        this.theta = theta;
    }

    public ArrayList<OutputEdge> getInEdges() {
        return inEdgeList;
    }

    /**
     *
     * @param inEdges
     */
    public void setInEdges(ArrayList<OutputEdge> inEdges) {
        this.inEdgeList = inEdges;
    }

    /**
     *
     * @return
     */
    public ArrayList<OutputEdge> getOutEdges() {
        return outEdgeList;
    }

    /**
     *
     * @param outEdges
     */
    public void setOutEdges(ArrayList<OutputEdge> outEdges) {
        this.outEdgeList = outEdges;
    }

    /**
     *
     * @return
     */
    public double getNetInput() {
        return netInput;
    }

    /**
     *
     * @param outVal
     */
    public void setNetInput(double outVal) {
        this.netInput = outVal;
    }

    private void assignTheta() {
        Random rnd = new Random();
        boolean pos = rnd.nextBoolean();
        double rndNum = rnd.nextDouble();
        if (pos) {
            this.theta = rndNum;
        }
        else {
            this.theta = (-rndNum);
        }

    }

    /**
     *
     * @param index
     * @param val
     */
    public void setInEdgeWeight(int index, double val) {
        inEdgeList.get(index).setWeight(val);
    }

    /**
     *
     */
    public void fire() {
        double output = 0;
        output = calcOutput(this.netInput - this.theta);
        double currNet = 0;
        double edgeOutput = 0;
        for (OutputEdge edge : this.outEdgeList) {
            currNet = edge.getTo().getNetInput();
            edgeOutput = (output * edge.getWeight());
            edge.getTo().setNetInput(
                    currNet + edgeOutput);
        }
    }

    /**
     * activation logistic function
     *
     * @param netInput
     * @return
     */
    public double calcOutput(double netInput) {
        activationFunction f = new logistic();
        return f.calculateOutput(netInput);
    }

    /**
     *
     * @param netInput
     * @return
     */
    public double calcDerivOutput(double netInput) {
        double outputVal = calcOutput(netInput);

        //the simplified derivative of our logistic eqn - y' = y*(1-y)
        return (outputVal * (1 - outputVal));
    }

    public void setErrorGradient(double er) {
        this.errorGradient = er;
    }

    public double getErrorGradient() {
        return this.errorGradient;
    }
}
