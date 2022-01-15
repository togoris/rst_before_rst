package scroller_v1;

import javafx.scene.shape.Rectangle;

public class Platforms {
	int x;
	int y;
	int stage;
	public Platforms(int x, int y, int stage) {
		super();
		this.x = x;
		this.y = y;
		this.stage = stage;
		
	}public Rectangle getRect() {
		int platformWidth = 60;
		int platformHeight = 10;
		if(stage==2) {
			platformWidth +=40;
			platformHeight +=40;
		}
		return new Rectangle(x, y, platformWidth, platformHeight);
 
	}
}