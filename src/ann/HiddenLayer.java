
package ann;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;


public class HiddenLayer implements Serializable {


    protected ArrayList<HiddenNeuron> neuronList;
    private double momentum = 0.5;
 
    protected double alpha = 0.2;

 
    protected String name;

    private double[] outputErrors;

    HiddenLayer(int numNeurons) {
        this.neuronList = this.createNeurons(numNeurons);
    }

    HiddenLayer(int numNeurons, String layerID) {
        this.neuronList = this.createNeurons(numNeurons, layerID);
        this.name = layerID;
    }

    HiddenLayer(int numNeurons, String id, double alpha) {
        this.neuronList = this.createNeurons(numNeurons, id);
        this.name = id;
        this.alpha = alpha;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }


    public ArrayList<HiddenNeuron> getNeurons() {
        return neuronList;
    }


    public void setNeurons(ArrayList<HiddenNeuron> Neurons) {
        this.neuronList = Neurons;
    }


    public double getAlpha() {
        return (this.alpha);
    }


    public ArrayList<HiddenNeuron> createNeurons(int numNeurons) {
        ArrayList<HiddenNeuron> myNeurons = new ArrayList<>();
        HiddenNeuron tempNeuron;
        for (int i = 0; i < numNeurons; i++) {
            tempNeuron = new HiddenNeuron(i);
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


    public ArrayList<HiddenNeuron> createNeurons(int numNeurons, String layerID) {
        ArrayList<HiddenNeuron> myNeurons = new ArrayList<>();
        HiddenNeuron tempNeuron;
        for (int i = 0; i < numNeurons; i++) {
            tempNeuron = new HiddenNeuron(String.valueOf(i));
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


    public void connectLayer(OutputLayer nextLayer) {
        OutputEdge tempEdge;
        for (int i = 0; i < this.neuronList.size(); i++) {
            if (this.neuronList.get(i).getOutEdges().size() > 0) {
                throw new RuntimeException(
                        "Trying to connect neuron that already has out going connections!");
            }
            for (int j = 0; j < nextLayer.getNeurons().size(); j++) {
                tempEdge = new OutputEdge();
                tempEdge.setName(
                        this.name + this.neuronList.get(i).getName() + "E" + j);
                tempEdge.setWeight(this.assignWeight());
                tempEdge.setFrom(this.neuronList.get(i));
                this.neuronList.get(i).getOutEdges().add(tempEdge);
                tempEdge.setTo(nextLayer.getNeurons().get(j));
                nextLayer.getNeurons().get(j).getInEdges().add(tempEdge);
            }
        }
    }


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

    /**
     * fire every hidden neurons
     *
     */
    public void fireNeurons() {
        for (int i = 0; i < this.neuronList.size(); i++) {
            this.neuronList.get(i).fire();
        }
    }


    public void calculateWeightDeltas() {
        this.calculateErrors();
        this.calculateDeltas(this.neuronList, this.outputErrors);
    }


    public void learn() {
        this.learn(this.neuronList, this.outputErrors);
    }

    private void calculateErrors() {
        this.outputErrors = new double[this.neuronList.size()];
        double errorGradSum;
        HiddenNeuron currNeuron;
        for (int i = 0; i < this.neuronList.size(); i++) {
            errorGradSum = 0;
            currNeuron = this.neuronList.get(i);
            double output = currNeuron.calcOutput(
                    currNeuron.getNetInput() - currNeuron.getTheta());
            for (OutputEdge outEdge : currNeuron.getOutEdges()) {
                errorGradSum += (outEdge.getWeight()
                                 * outEdge.getTo().getErrorGradient());
            }
            this.outputErrors[i] = errorGradSum * (1 - output) * output;
        }
    }

    /**
     * set weight delta to the input edges
     */
    private void setWeightDeltas(ArrayList<InputEdge> oldWeights, double error,
                                 double alpha) {
        double weightCorrection = 0;
        double neuronOut = 0;
        for (InputEdge edge : oldWeights) {
            neuronOut = edge.getFrom().calcOutput(
                    edge.getFrom().getNetInput() - edge.getFrom().getTheta());
            edge.setErrorGradient(error);

            weightCorrection = alpha * neuronOut * error + edge.previousWeight * momentum;
            edge.prevWeightDelta = edge.getWeight();
            edge.setWeightDelta(weightCorrection);
        }
    }


    private void calculateDeltas(ArrayList<HiddenNeuron> neurons,
                                 double[] errors) {
        double errorGrad = 0;
        double neuronNet = 0;
        double neuronOut = 0;
        double newTheta = 0;
        for (int i = 0; i < neurons.size(); i++) {
            neuronNet = neurons.get(i).getNetInput();
            neuronOut = neurons.get(i).calcOutput(
                    neuronNet - neurons.get(i).getTheta());
            errorGrad = neuronOut * (1 - neuronOut) * errors[i];
            this.setWeightDeltas(neurons.get(i).getInEdges(), errorGrad,
                                 neurons.get(i).getAlpha());
            newTheta = neurons.get(i).getAlpha() * -1 * errorGrad;
            neurons.get(i).setTheta(newTheta + neurons.get(i).getTheta());
            //Reset the net input
            neurons.get(i).setNetInput(0);
        }
    }

    private void learn(ArrayList<HiddenNeuron> neurons, double[] errors) {
        for (int i = 0; i < neurons.size(); i++) {
            HiddenNeuron currNeuron = neurons.get(i);
            for (InputEdge e : currNeuron.getInEdges()) {
                double newWeight = e.getWeight() + e.getWeightDelta();
                e.setWeight(newWeight);
            }
        }
    }

}
