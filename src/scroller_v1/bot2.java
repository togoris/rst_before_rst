package scroller_v1;

import java.util.List;
import javafx.scene.shape.Rectangle;


public class bot2 extends bot{
long timeTillMove = 3000;
long distanceToClosestPlat = Integer.MAX_VALUE;
int platx;
int platy;
int distanceToPlat;
Platforms whichPlat;
	public bot2(int x, int y) {
		super(x, y);
	
	}

	@Override
	public void move(long dif,List<Platforms> platforms,Rectangle player1) {
		timeTillMove -=dif;
		if(timeTillMove <=0) {
			for(Platforms A: platforms) {
				if((A.x-x<0 && player1.getX()-x<0) || (A.x-x>0 && player1.getX()-x>0)) {
					platx = A.x;
					platy = A.y;
					distanceToPlat = (platx*platx)+(platy*platy);
					if(distanceToClosestPlat>distanceToPlat) {
						distanceToClosestPlat = distanceToPlat;
						whichPlat = A;
							}
				}
					
			}
			x=whichPlat.x;
			y=whichPlat.y;
			
			
			
			
			
			timeTillMove = 3000;
		}
	}

}
