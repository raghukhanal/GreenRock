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
 * File: InputLayer
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
public class InputLayer implements Serializable {

    /**
     *
     */
    protected ArrayList<InputNeuron> neuronList;

    /**
     *
     */
    protected double alpha = 0.2;

    /**
     *
     */
    protected String name;

    InputLayer(int numNeurons) {
        this.neuronList = this.createNeurons(numNeurons);
    }

    InputLayer(int numNeurons, String layerID) {
        this.neuronList = this.createNeurons(numNeurons, layerID);
        this.name = layerID;
    }

    InputLayer(int numNeurons, String id, double alpha) {
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
    public ArrayList<InputNeuron> getNeurons() {
        return neuronList;
    }

    /**
     *
     * @param Neurons
     */
    public void setNeurons(ArrayList<InputNeuron> Neurons) {
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
     * @param numNeurons
     * @return
     */
    public ArrayList<InputNeuron> createNeurons(int numNeurons) {
        ArrayList<InputNeuron> myNeurons = new ArrayList<>();
        InputNeuron tempNeuron;

        for (int i = 0; i < numNeurons; i++) {
            tempNeuron = new InputNeuron(i);
            if (this.alpha != -1) {
                tempNeuron.setAlpha(this.alpha);
            }
            else {
                tempNeuron.setAlpha(this.alpha);
            }
            tempNeuron.setTheta(0);
            myNeurons.add(tempNeuron);
        }
        return (myNeurons);
    }

    /**
     *
     * @param numNeurons
     * @param id
     * @return
     */
    public ArrayList<InputNeuron> createNeurons(int numNeurons, String id) {
        ArrayList<InputNeuron> myNeurons = new ArrayList<>();
        InputNeuron tempNeuron;
        this.name = id;

        for (int i = 0; i < numNeurons; i++) {
            tempNeuron = new InputNeuron(String.valueOf(i));

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
     * take in the input values, and set the input neurons
     *
     * @param inputVals
     */
    public void fireNeurons(double[] inputVals) {
        for (int i = 0; i < this.neuronList.size(); i++) {
            this.neuronList.get(i).setNetInput(inputVals[i]);
            this.neuronList.get(i).fire();
        }
    }

    /**
     *
     * @param nextLayer
     */
    public void connectLayer(HiddenLayer nextLayer) {
        InputEdge tempEdge;
        for (int i = 0; i < this.neuronList.size(); i++) {
            if (this.neuronList.get(i).getOutEdges().size() > 0) {
                throw new RuntimeException(
                        "Trying to connect neuron that already has out going connections!");
            }
            for (int j = 0; j < nextLayer.getNeurons().size(); j++) {
                tempEdge = new InputEdge();
                tempEdge.setName(
                        this.name + this.neuronList.get(i).getName() + "E" + j);
                tempEdge.setWeight(this.assignWeight());
                tempEdge.setFrom(this.neuronList.get(i));
                tempEdge.setTo(nextLayer.getNeurons().get(j));
                this.neuronList.get(i).getOutEdges().add(tempEdge);
                nextLayer.getNeurons().get(j).getInEdges().add(tempEdge);
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
