import java.util.LinkedList;
import java.util.Queue;

public class CustomerAssociate implements Runnable {
	
	private static int num_ca = 0;
	final double costPerPound = 0.75f;
	static Queue<FisherMan> line = new LinkedList<>();
	
	public synchronized void timeForCustomer() {
		
	}
	
	@Override
	public void run() {
		// Basically do what i did with the ranger but differently
		try {
			
		} catch(Exception e) {}
	}
	
	public double cost(int weight) {
		return weight * 0.75f;
	}
	
	
}
