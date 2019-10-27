package entity;
import ann.TrainedANN;
import ann.NeuralNet;

public class ANN {
	
	public static NeuralNet ann = TrainedANN().getANN();
	
	public ANN(){
		
	}
	
	public double eval(double PE,double PB) {
		
		double one = 0;
		
		one+=ann.getInputLayer().getNeurons().get(0).getOutEdges().get(0).getWeight()*PE;
		one+=ann.getInputLayer().getNeurons().get(1).getOutEdges().get(0).getWeight()*PB;
		one-=ann.getHiddenLayer().getNeurons().get(0).getAlpha();
		
		double two =0;
		
		two+=ann.getInputLayer().getNeurons().get(0).getOutEdges().get(1).getWeight()*PE;
		two+=ann.getInputLayer().getNeurons().get(1).getOutEdges().get(1).getWeight()*PB;
		two-=ann.getHiddenLayer().getNeurons().get(1).getAlpha();
		
		double three = 0;
		
		three+=ann.getInputLayer().getNeurons().get(0).getOutEdges().get(2).getWeight()*PE;
		three+=ann.getInputLayer().getNeurons().get(1).getOutEdges().get(2).getWeight()*PB;
		three-=ann.getHiddenLayer().getNeurons().get(2).getAlpha();
		
		
		double res =0;
		res+=one*ann.getHiddenLayer().getNeurons().get(0).getInEdges().get(0).getWeight();
		res+=one*ann.getHiddenLayer().getNeurons().get(1).getInEdges().get(0).getWeight();
		res+=one*ann.getHiddenLayer().getNeurons().get(2).getInEdges().get(0).getWeight();
		res = calculateOutput(res);
		System.out.println(one+" "+two+" "+three+" "+res);
		return res;
		
		
		
	}
	private static TrainedANN TrainedANN() {
		// TODO Auto-generated method stub
		return null;
	}
//	public static void main(String[] args) {
//		eval(26.55,10.13);
//		eval(4.23,0.38);
//		eval(20.94,11.58);
//	}
	public static double calculateOutput(double netInput) {
        return 1.5 / (1+Math.exp(-netInput));
		//return 3 / (1 + Math.exp(-2 * netInput)) - 1;
		//return netInput;
    }
}
