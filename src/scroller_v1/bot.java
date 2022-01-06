package scroller_v1;

public abstract class bot {
int x;
int y;
public bot(int x, int y) {
	super();
	this.x = x;
	this.y = y;
}
public abstract void move();	

}
