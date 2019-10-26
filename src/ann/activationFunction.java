
package ann;


public interface activationFunction {

    public double calculateOutput(double netInput);

}

class logistic implements activationFunction {

    @Override
    public double calculateOutput(double netInput) {
        return 1 / (1 + Math.exp(-netInput));
    }
}

class TanH implements activationFunction {

    @Override
    public double calculateOutput(double netInput) {
        return 2 / (1 + Math.exp(-2 * netInput)) - 1;
    }
}

class identity implements activationFunction {

    @Override
    public double calculateOutput(double netInput) {
        return netInput;
    }
}
