package scroller_v1;

public class randomNumGenorator {
	
	double getRandomNumber(int low, int high) {
		int range = high - low + 1;
		return Math.random() * range + low;
	}
}
