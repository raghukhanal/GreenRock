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
 * File: Edge
 * Description:
 *
 * ****************************************
 */
package ann;

/**
 * the edge between hidden layer and output layer
 *
 * @author cld028
 */
public class OutputEdge extends Edge {

    private OutputNeuron to;
    private HiddenNeuron from;

    OutputEdge() {
        this.weight = 0;
    }

    OutputEdge(double weight, String Name) {
        this.weight = weight;
    }

    /**
     *
     * @return
     */
    public HiddenNeuron getFrom() {
        return from;
    }

    /**
     *
     * @param from
     */
    public void setFrom(HiddenNeuron from) {
        this.from = from;
    }

    /**
     *
     * @return
     */
    public OutputNeuron getTo() {
        return to;
    }

    /**
     *
     * @param to
     */
    public void setTo(OutputNeuron to) {
        this.to = to;
    }

}
