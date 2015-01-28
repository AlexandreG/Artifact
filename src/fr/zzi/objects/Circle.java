package fr.zzi.objects;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Circle {
	private double radius;
	private int col;
	private float x;
	private float y;
	private boolean killed;

	public Circle(double radius, int col, float x, float y) {
		super();
		this.radius = radius;
		this.col = col;
		this.x = x;
		this.y = y;
		this.killed = false;
	}

	public void move(float speed) {
		radius -= speed;
	}

	public void draw(Canvas canvas, Paint paint) {
		paint.setStyle(android.graphics.Paint.Style.FILL);
		paint.setColor(col);
		paint.setAntiAlias(true);
		canvas.drawCircle(x, y, (float) radius, paint);
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public void rebirth() {
		this.killed = false;
	}

	public void kill() {
		this.killed = true;
	}

	public boolean isKilled() {
		return killed;
	}
	
	public void setColor(int c){
		col = c;
	}
}
