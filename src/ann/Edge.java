
package ann;

import java.io.Serializable;

/**
 * abstract class Edge can be extended by the input edge and output edge
 *
 * @author qinchen
 */
public abstract class Edge implements Serializable {

    double weight;
    double errorGradient = 0;
    double weightDelta = 0;
    double prevWeightDelta = 0;
    String name;
    double previousWeight = 0;

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public double getPrevWeightDelta() {
        return prevWeightDelta;
    }


    public void setPrevWeightDelta(double prevWeightDelta) {
        this.prevWeightDelta = prevWeightDelta;
    }


    public double getWeightDelta() {
        return weightDelta;
    }


    public void setWeightDelta(double weightDelta) {
        this.weightDelta = weightDelta;
    }


    public double getErrorGradient() {
        return errorGradient;
    }

  
    public void setErrorGradient(double errorGradient) {
        this.errorGradient = errorGradient;
    }

    public double getWeight() {
        return weight;
    }

    
    public void setWeight(double weight) {
        this.weight = weight;
    }

}
