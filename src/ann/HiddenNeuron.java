
package ann;

import java.util.ArrayList;
import java.util.Random;


public class HiddenNeuron extends Neuron {

    private ArrayList<InputEdge> inEdgeList;
    private ArrayList<OutputEdge> outEdgeList;

    public ArrayList<InputEdge> getInEdges() {
        return inEdgeList;
    }


    public void setInEdges(ArrayList<InputEdge> inEdges) {
        this.inEdgeList = inEdges;
    }


    public ArrayList<OutputEdge> getOutEdges() {
        return outEdgeList;
    }


    public void setOutEdges(ArrayList<OutputEdge> outEdges) {
        this.outEdgeList = outEdges;
    }

    public double getNetInput() {
        return netInput;
    }


    public void setNetInput(double outVal) {
        this.netInput = outVal;
    }

    HiddenNeuron(int idNum) {
        this.inEdgeList = new ArrayList<InputEdge>();
        this.outEdgeList = new ArrayList<OutputEdge>();
        this.alpha = HiddenNeuron.DEFAULTALPHA;
        this.assignTheta();
        this.name += String.valueOf(idNum);
    }

    HiddenNeuron(String id) {
        this.inEdgeList = new ArrayList<InputEdge>();
        this.outEdgeList = new ArrayList<OutputEdge>();
        this.alpha = HiddenNeuron.DEFAULTALPHA;
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

    public void setInEdgeWeight(int index, double val) {
        inEdgeList.get(index).setWeight(val);
    }

    /**
     * calculate the output and send it into the output neuron
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
            edge.getTo().setNetInput(currNet + edgeOutput);
        }
    }

    /**
     * calculate the output using logistics equation
     *
     * @param netInput
     * @return
     */
    public double calcOutput(double netInput) {
        activationFunction f = new logistic();
        return f.calculateOutput(netInput);

    }

    /**
     * derivate of output value
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
