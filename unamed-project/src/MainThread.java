import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

import javax.swing.Timer;

//CANNOT USE NOTIFY(), NOTIFYALL(), or WAIT() in this project. 

public class MainThread extends Thread {
	
	private static long time = System.currentTimeMillis();
	private Queue<Thread> fishing = new LinkedList<>();
	private static Thread[] fishermenArray;
	private static boolean boatingTrip = false;
	private static boolean allBigFish = false;
	public static boolean [] enterCs;
	private static int num_threads; 
	static Scanner in = new Scanner(System.in);
	
	public static void main(String[] args) {
		intializeSim();
		BeginTravel();
		fishermenArray = new Thread[num_threads];
		enterCs = new boolean[num_threads];
		for(int i = 0; i < num_threads; i++){
			Thread t = new FisherMan(i + 1);
			fishermenArray[i] = t;
			enterCs[i] = false;
			t.start();
		}
	}
	
	public static void intializeSim() {
		System.out.println("pick a number of fisherman 2-6 preferably");
		int x = in.nextInt();
		while(x < 2 || x > 6){
			System.out.println("Incorrect number of fisherman, please choose 2-6");
			x = in.nextInt();
		}
		num_threads = x;
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
