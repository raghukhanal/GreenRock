/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Spring 2019
 *
 * Name: Zoe Chen, Horio Hu
 * Date:
 * Time:
 *
 * Project:
 * Package: hw02
 * File: Edge
 * Description:
 *
 * ****************************************
 */
package ann;

/**
 * the edge between input layer and hidden layer
 *
 * @author cld028
 */
public class InputEdge extends Edge {

    private HiddenNeuron to;
    private InputNeuron from;

    /**
     *
     * @return
     */
    InputEdge() {
        this.weight = 0;
    }

    InputEdge(double weight, String Name) {
        this.weight = weight;
    }

    public InputNeuron getFrom() {
        return from;
    }

    /**
     *
     * @param from
     */
    public void setFrom(InputNeuron from) {
        this.from = from;
    }

    /**
     *
     * @return
     */
    public HiddenNeuron getTo() {
        return to;
    }

    /**
     *
     * @param to
     */
    public void setTo(HiddenNeuron to) {
        this.to = to;
    }

}
