/* *****************************************
* CSCI205 - Software Engineering and Design
* Spring 2019
*
* Name: Zoe Chen, Horio Hu
* Date: Mar 14, 2019
* Time: 11:42:44 PM
*
* Project: csci205_hw_sp19
* Package: hw02
* File: Neuron
* Description:
*
* ****************************************
 */
package ann;

import java.io.Serializable;

/**
 * abstract class Neuron can be extended by all input neurons, hidden neurons
 * and output neurons
 *
 * @author qinchen
 */
public abstract class Neuron implements Serializable {

    String name = "N";
    double alpha;
    double netInput;
    double theta;
    final static long RANDSEED = 1;
    final static double DEFAULTALPHA = 0.2;

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
     * @return
     */
    public double getAlpha() {
        return alpha;
    }

    /**
     *
     * @param alpha
     */
    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

}
