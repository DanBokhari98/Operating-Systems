import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

//CANNOT USE NOTIFY(), NOTIFYALL(), or WAIT() in this project. 

public class FisherMan extends Thread {
	/*
		CAN USE INTTERUPT(), isINTERRUPTED()
		getPRIORITY(), setPRIORITY(), and currentTHREAD() methods
		and YIELD() methods. ! NOT MANDATORY !
	 *  - Implement Ranger Thread to handle Fishermen (no clue yet)
	 */
	ArrayList<Integer> bucket = new ArrayList<>();
	public boolean caughtBigOne = false;
	private int fisherManNum = 0;
	Random r = new Random();
	private int arr[] = new int[100];
	private double bankAccount = 0.00f;
	private final int travelCost = 250;
	public boolean myturn = false;
	public boolean shopping = false;
	public static Ranger ranger = new Ranger();
	
	public FisherMan(int x){
		super();
		fisherManNum = x;
		fillArr();
	}
	
	public void fillArr() {
		for(int i = 0; i < 100; i++) {
			if(i <= 15) arr[i] = 0;
			else if(i <= 30 && i >= 16) arr[i] = 10;
			else if(i <= 50 && i >= 31) arr[i] = 20;
			else if(i <= 70 && i >= 51) arr[i] = 50;
			else if(i <= 90 && i >= 71) arr[i] = 100;
			else if(i <= 100 && i >= 91) arr[i] = 250;
		}
	}
	
	//Specifies the fish type based on fish weight.
	private String fishType(int x) {
		if(arr[x] == 0) return "nothing";
		else if(arr[x] == 10) return "a small fish";
		else if(arr[x] == 20) return "a medium fish";
		else if(arr[x] == 50) return "a large fish";
		else if(arr[x] == 100) return "an extra large fish";
		else if(arr[x] == 250) return "BIG ONE";
		return "";
	}
	
	public synchronized boolean getTurn() { return myturn; }
	public synchronized void setTurn(boolean flag) { myturn = flag; }
	
	//Prints which fish was caught
	private void Reel(int fish) {
		if(arr[fish] == 250) {
			System.out.println("Fisherman " + fisherManNum + " REELED IN THE " + fishType(fish));
			setBigOne();
		}else System.out.println("Fisherman " + fisherManNum + " reeled in " + fishType(fish));
	}
	
	//Creates the thread and prints when each fishermen starts fishing again.
	private void cast() {
		System.out.println("Fisherman " + fisherManNum + ": cast his rod ");
		try {
			Thread.sleep(200);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Uses smaller fish as bait to increase odds of catching larger fish
	public int getBait(){
		if(bucket.size() == 0) return 0;
		int max = 0;
		int temp = -1;
		for(int i = 0; i < bucket.size(); i++) {
			if(bucket.get(i) >= max && bucket.get(i) <= 20) { 
				max = bucket.get(i);
				temp = i;
			}
		}
		if(temp > -1) bucket.remove(temp);
		return max;
	}
	
	//This utilizes the bait to shift the odds of catching larger fish.
	public int nextFishChance(int b) {
		int fish = 0;
		if(b == 0) fish =  r.nextInt((99 - 0) + 1) + 0;
		else if(b == 10) fish =  r.nextInt((99 - 20) + 1) + 20;
		else if(b == 20) fish =  r.nextInt((99 - 40) + 1) + 40;
		return fish;
	}
	
	//Wait for fish to be caught
	//Every fish caught gets tossed into a fisherman's bucket.
	private void lazyWait() {
		int bait = getBait();
		int fish = nextFishChance(bait);
		if(arr[fish] <= 10) waitSleep(200);
		else if(arr[fish] == 20) waitSleep(400);
		else if(arr[fish] == 50) waitSleep(500);
		else if(arr[fish] == 100) waitSleep(600);
		else if(arr[fish] == 250) waitSleep(700);
		bucket.add(arr[fish]);
		Reel(fish);
	}
	
	//Instead of rewriting the thread, I created a method that generated the threads use.
	public void waitSleep(int x) {
		System.out.println("Fisherman " + fisherManNum + ": is waiting for fish to bite");
		try {
			Thread.sleep(x);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//Useless method
	public void msg (String s) {
		System.out.println("Fisherman " + fisherManNum + " " + s);
	}
	
	//This is dumb code
	private void setBigOne() {
		caughtBigOne = true;
	}
	
	//Code that gets the fisherman to and from Bretton
	public void journeyToBreton(){
		if(caughtBigOne) {
		System.out.println("Fisherman " + fisherManNum + " is departing Morrowind for Bretron");
			try {
				Thread.sleep(5000);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		System.out.println("Fisherman " + fisherManNum + " returned from Morrowind");
		
	}
	
	public void enqueueRanger(){
		Ranger.fishingHole.add(this);
	}
	
	//This is all the actions the fishermen do during their simulation.
	@Override 
	public void run(){
		Ranger.fishingHole.add(this);
		msg("Began Fishing");
		ranger.run();
		//while(true) {
		
		while(!myturn) { }
		
		while(myturn) {
			cast();
			lazyWait();
			if(caughtBigOne) {
				Ranger.stopFishing(this);
				journeyToBreton();
				Ranger.fishingHole.add(this);
				}
			}
		/*
		CustomerAssociate.line.add(this);
		while(!shopping) {}
		
		while(shopping) {	
		}
		*/
		//while market loop
		
		//While(true loop needed) Nested while loop 
	}
	public double getBankAccount() { return bankAccount; }
	public void transaction(double cost) { bankAccount = bankAccount + cost; }
	
	public int getFishermanNumber() {
		return fisherManNum;
	}
	public String toString() {
		return "Fisherman " + fisherManNum;
	}
}
