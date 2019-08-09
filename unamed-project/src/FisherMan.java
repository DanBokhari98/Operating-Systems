import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class FisherMan extends Thread {
	ArrayList<Integer> bucket = new ArrayList<>();
	public boolean caughtBigOne = false;
	public boolean enoughFish = false;
	private int fisherManNum = 0;
	Random r = new Random();
	private int arr[] = new int[100];
	private double bankAccount = 0.00f;
	private final int travelCost = 250;
	public boolean myturn = false;
	public boolean shopping = false;
	public static Ranger ranger = new Ranger();
	public static CustomerAssociate associate = new CustomerAssociate();
	public boolean [] finished;
	public boolean allFinished = false;
	
	public FisherMan(int x){
		super();
		fisherManNum = x;
		finished = new boolean[6];
		fillArr();
	}
	
	//Populates the probability of catching a fish!
	public void fillArr() {
		for(int i = 0; i < 100; i++) {
			if(i <= 15) arr[i] = 0;
			else if(i <= 30 && i >= 16) arr[i] = 10;
			else if(i <= 50 && i >= 31) arr[i] = 20;
			else if(i <= 70 && i >= 51) arr[i] = 30;
			else if(i <= 90 && i >= 71) arr[i] = 40;
			else if(i <= 100 && i >= 91) arr[i] = 50;
			else if(i <= 100 && i >= 97) arr[i] = 200;
		}
	}
	
	//Specifies the fish type based on fish weight.
	private String fishType(int x) {
		if(arr[x] == 0) return "nothing";
		else if(arr[x] == 10) return "a small fish";
		else if(arr[x] == 20) return "a medium fish";
		else if(arr[x] == 30) return "a large fish";
		else if(arr[x] == 40) return "an extra large fish";
		else if(arr[x] == 50) return "a huge fish!";
		else if(arr[x] == 200) return "THE BIG ONE!";
		return "";
	}
	
	public void isSufficient(int sum) {
		if(sum >= 50) enoughFish = true; 
	}
	
	public int currentPoundsOfFish() {
		int sum = 0;
		for(Integer c : bucket) {
			sum += c;
		}
		return sum;
	}
	
	public synchronized boolean getTurn() { return myturn; }
	public synchronized void setTurn(boolean flag) { myturn = flag; }
	
	//Prints which fish was caught
	private void Reel(int fish) {
		if(arr[fish] == 250) {
			System.out.println("Fisherman " + fisherManNum + ": REELED IN THE " + fishType(fish));
			setBigOne();
		}else System.out.println("Fisherman " + fisherManNum + ": reeled in " + fishType(fish));
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
		if(arr[fish] <= 10) waitSleep(300);
		else if(arr[fish] == 20) waitSleep(600);
		else if(arr[fish] == 30) waitSleep(800);
		else if(arr[fish] == 40) waitSleep(900);
		else if(arr[fish] == 50) waitSleep(1000);
		else if(arr[fish] == 200) waitSleep(1200);
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
	
	public void journeyToMorrowind(){
		System.out.println("Fisherman " + fisherManNum + " is departing Breton Market for Marrowind");
		try {
			Thread.sleep(5000);
		}catch(Exception e){
			e.printStackTrace();
		}
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
		associate.run();
		while(true) {
			while(!myturn) { }
			while(myturn) {
				cast();
				lazyWait();
				isSufficient(currentPoundsOfFish());
				if(enoughFish) {
					Ranger.stopFishing(this);
					journeyToBreton();
					Ranger.fishingHole.add(this);
					myturn = false;
					shopping = true;
					}
				} 
				CustomerAssociate.line.add(this);
				while(!shopping) {}
				while(shopping) {
					System.out.println("Fisherman: " + fisherManNum + " entered the market");
					journeyToMorrowind();
					CustomerAssociate.queueCustomer(this);
					CustomerAssociate.dequeueCustomer(this);
					enoughFish = false;
					shopping = false;
					myturn = true;
				}
				if(bankAccount >= travelCost) {
					finished[fisherManNum - 1] = true;
					System.out.println("Fisherman " + fisherManNum + " is returning home and finished their trip");
					break;
				}
			}
		}
	
	public double getBankAccount() { return bankAccount; }
	
	public int getFishermanNumber() {
		return fisherManNum;
	}
	
	public synchronized void setStatus(boolean flag) {
		shopping = flag;
		shop();
	}
	
	//Only service first person in queue get brian's help
	public synchronized void shop() {
		try {
			while(!bucket.isEmpty()) {
				int tempFish = bucket.remove(0);
				if(tempFish == 10) {
					System.out.println("Sales Associate bought fisherman " + fisherManNum + " 10 pound fish");
					Thread.sleep(300);
					bankAccount += tempFish * 0.75f;
				}
				if(tempFish == 20) {
					System.out.println("Sales Associate bought fisherman " + fisherManNum + " 20 pound fish");
					Thread.sleep(600);
					bankAccount += tempFish * 0.75f;
				}
				if(tempFish == 30) {
					Thread.sleep(900);
					System.out.println("Sales Associate bought fisherman " + fisherManNum + " 30 pound fish");
					bankAccount += tempFish * 0.75f;
				}
				if(tempFish == 40) {
					Thread.sleep(1200);
					System.out.println("Sales Associate bought fisherman " + fisherManNum + " 40 pound fish");
					bankAccount += tempFish * 0.75f;
				}
				if(tempFish == 50) {
					Thread.sleep(1500);
					System.out.println("Sales Associate bought fisherman " + fisherManNum + " 50 pound fish");
					bankAccount += tempFish * 0.75f;
					}
				if(tempFish == 200) {
					Thread.sleep(2000);
					System.out.println("Sales Associate bought and stuffed fisherman " + fisherManNum + " 200 pound fish");
					bankAccount += tempFish * 0.75f;
					}
				if(bucket.isEmpty()) break;
				}
		} catch (InterruptedException e) {}
	}
	
	public void endTravel() {System.exit(0);}
	
	public boolean getStatus() { return shopping; }
	
	public String toString() {
		return "Fisherman " + fisherManNum;
	}
}
