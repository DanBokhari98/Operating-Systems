import java.util.LinkedList;
import java.util.Queue;

public class Ranger implements Runnable {
	
	private static int counter = 0;
	private final int maxCount = 3;
	static Queue<FisherMan> fishingHole = new LinkedList<>();
	private boolean isServing = false;
	
	public synchronized void pickFisherMan(FisherMan man){
		man.setTurn(true);
		counter++;
	}
	
	public void doNothing() {}
	
	public static synchronized void stopFishing(FisherMan man) {
		man.setTurn(false);
		counter--;
	}
	
	@Override
	public void run() {
			if(!fishingHole.isEmpty()) {
				try {
						FisherMan f = fishingHole.remove();
						while(MainThread.enterCs[f.getFishermanNumber()-1] && !isServing) {}
						isServing = true;
						pickFisherMan(f);
						//Critical section
						System.out.println("Ranger selected Fisher man " + f.getFishermanNumber() + " to fish");
						while(counter == 3) {}
						isServing = false;
						System.out.println("Ranger removed Fisher man " + f.getFishermanNumber() + " from the fishing hole");
					} catch(Exception e) {}
			}
	}

}
