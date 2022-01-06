package scroller_v1;

import simpleIO.Console;


// this is going to be the random one
public class bot1 extends bot {

	public bot1(int x, int y) {
		super(x, y);
		
	}
	int moved = 0;
	int movetime;
	int low = 1;
	int high = 5;
	@Override
	public void move() {
		if(moved == 1) {
			getRandomNumber(low,high);
			
		}
		
	}
	private void getRandomNumber(int low, int high) {
		int range = high - low + 1;
		movetime = (int) (Math.random() * range + low);
	}
	public void checktime() {
		
	}
	public void setTime() {
		
	}
}
