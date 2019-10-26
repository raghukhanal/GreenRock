/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2019
 *
 * Name: Zoe Chen, Horio Hu
 * Date:
 * Time:
 *
 * Project:
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
public class InputNeuron extends Neuron {

    private ArrayList<InputEdge> outEdgeList;

    public final static double DEFAULTTHETA = 0.1;

    public ArrayList<InputEdge> getOutEdges() {
        return outEdgeList;
    }

    /**
     *
     * @param outEdges
     */
    public void setOutEdges(ArrayList<InputEdge> outEdges) {
        this.outEdgeList = outEdges;
    }

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

    InputNeuron(int idNum) {
        this.outEdgeList = new ArrayList<InputEdge>();
        this.alpha = InputNeuron.DEFAULTALPHA;
        this.assignTheta();
        this.name += String.valueOf(idNum);
    }

    InputNeuron(String id) {

        this.outEdgeList = new ArrayList<InputEdge>();
        this.alpha = InputNeuron.DEFAULTALPHA;
        this.assignTheta();
        this.name += id;
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

    public void fire() {
        double output = 0;
        output = calcOutput(this.netInput - this.theta);
        double edgeOutput = 0;
        double currNet = 0;
        for (InputEdge edge : this.outEdgeList) {
            currNet = edge.getTo().getNetInput(); //current net value of the hidden neurons

            edgeOutput = (output * edge.getWeight());
            edge.getTo().setNetInput(currNet + edgeOutput);
        }

    }

    /**
     *
     * @param netInput
     * @return
     */
    public double calcOutput(double netInput) {
        return netInput;
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
}
