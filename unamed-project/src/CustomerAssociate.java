import java.util.LinkedList;
import java.util.Queue;

public class CustomerAssociate implements Runnable {
	
	private static int num_ca = 0;
	final double costPerPound = 0.75f;
	static Queue<FisherMan> line = new LinkedList<>();
	public boolean isServing;
	
	public static synchronized void queueCustomer(FisherMan man) {
		man.setStatus(true);
		line.add(man);
	}
	
	public static synchronized void dequeueCustomer(FisherMan man) {
		man.setStatus(false);
		line.remove(man);
	}
	
	@Override
	public void run() {
			if(!line.isEmpty()) {
				try {
					FisherMan f = line.remove();
					while(MainThread.enterCs[f.getFishermanNumber()-1] && !f.shopping) {}
					isServing = true;
					System.out.println("Customer selected Fisher man " + f.getFishermanNumber() + " to sell");
					f.shop();
					System.out.println("Customer finished serving Fisher man " + f.getFishermanNumber());
					isServing = false;
				} catch(Exception e) {}
			}
		}
	
	public double cost(int weight) {
		return weight * 0.75f;
	}	
	
}
