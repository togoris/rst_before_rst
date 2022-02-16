package scroller_v1;

import java.util.List;

import javafx.scene.shape.Rectangle;

public abstract class bot {
int x;
int y;
int botWidth = 5;
int botHeight = 5;
public bot(int x, int y) {
	super();
	this.x = x;
	this.y = y;
}
public abstract void move(long dif,List<Platforms> platforms, Rectangle player1);	
public Rectangle getRect() {
	
	return new Rectangle(x,y,botWidth,botHeight);
	
	
}
}
