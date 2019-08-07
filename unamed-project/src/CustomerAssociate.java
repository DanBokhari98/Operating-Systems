public class CustomerAssociate implements Runnable {
	
	private static int num_ca = 0;
	final double costPerPound = 0.75f;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public double calculateCost(int weight) {
		return weight * 0.75f;
	}
	
}
