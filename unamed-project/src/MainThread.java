import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.Timer;

//CANNOT USE NOTIFY(), NOTIFYALL(), or WAIT() in this project. 

public class MainThread extends Thread {
	
	private static long time = System.currentTimeMillis();
	private Queue<Thread> fishing = new LinkedList<>();
	private static Thread[] fishermenArray;
	private static boolean boatingTrip = false;
	private static boolean allBigFish = false;
	public static boolean [] enterCs;
	
	public static void main(String[] args) {
		BeginTravel();
		fishermenArray = new Thread[6];
		enterCs = new boolean[6];
		for(int i = 0; i < 6; i++){
			Thread t = new FisherMan(i + 1);
			fishermenArray[i] = t;
			enterCs[i] = false;
			t.start();
		}
		
		//This code isn't working yet.
		while(true) {
			boolean flag = true;
			for(int i = 0; i < 6; i++) {
				if(!((FisherMan)fishermenArray[i]).caughtBigOne) { 
					flag = false;
					break;
				} 
			}
			if(flag) { 
				allBigFish = true;
				break;
			}
		}
	//	if(allBigFish == true) sendThemBack();
	//  Pointless code
	//	boatingTrip = true;
	}
	
//First problem Getting the boat to travel
	public static void BeginTravel() {
		System.out.println("\tBeginning Journey to Morrowind" +
		"\n\tAll aboard the boat!\n");
		boatTrip();
		System.out.println("\nArrived at Morrowind: Greetings! \n");
	}

	public static void boatTrip() {
		int count = 5;
		while(count >= 0)	{
			try { Thread.sleep(1000);}
			catch(Exception e) {e.printStackTrace();}
			if(count > 0) {
				System.out.println("" + count-- + " Seconds before reaching destination...");
				if(count == 0) { 
						try{Thread.sleep(1000);}catch(Exception e) {e.printStackTrace();}
						break; 
					} 
			}
		}
	}
	
	public void addQueue(FisherMan m) {
		fishing.add(m);
	}
	
	@Override
	public void run() {
	}
	
	public void msg(String s) {
	}
	
}
