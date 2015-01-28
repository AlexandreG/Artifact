package fr.zzi.objects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Core {
	protected float x;
	protected float y;
	protected double radius;

	protected int life;
	protected int hitzone; // the gap allowed to kill a circle

	protected int state; // 0: default, 1:success, 2:fail
	private long stateDuration; // kind of invulnerability frames
	private long lastStateTime; // moment the core changed of state

	// usefull only for the screenFeedback
	protected boolean tapped;
	private long tappedDuration;
	private long lastTappedTime;

	public Core(float x, float y, double radius) {
		super();
		this.x = x;
		this.y = y;
		this.radius = radius;

		this.life = 3;
		this.hitzone = (int) radius / 8;
		this.state = 0;
		this.stateDuration = 50;
		this.lastStateTime = 0;

		this.tapped = false;
		this.tappedDuration = 50;
		this.lastTappedTime = 0;
	}

	public void draw(Canvas canvas, Paint paint) {
		paint.setStyle(android.graphics.Paint.Style.STROKE);
		paint.setColor(-1);
		paint.setStrokeWidth(3F);
		paint.setAntiAlias(true);
		if (tapped) {
			paint.setColor(0xff0000ff);
			paint.setStrokeWidth(3F);
		}

		switch (state) {
		case 1:
			paint.setColor(Color.GREEN);
			paint.setStyle(android.graphics.Paint.Style.FILL);
			break;
		case 2:
			paint.setColor(Color.RED);
			paint.setStyle(android.graphics.Paint.Style.FILL);
			break;
		default:
			break;
		}
		canvas.drawCircle(x, y, (float) radius, paint);
	}

	public void updateState() {
		long now = System.currentTimeMillis();
		if (now - lastStateTime > stateDuration) {
			this.state = 0;
		}
		if (now - lastTappedTime > tappedDuration) {
			this.tapped = false;
		}
	}

	public double getRadius() {
		return radius;
	}

	public int getHitzone() {
		return hitzone;
	}

	public void hitted() {
		state = 2;
		--life;
		lastStateTime = System.currentTimeMillis();
	}

	public void tapEvent() {
		tapped = true;
		lastTappedTime = System.currentTimeMillis();
	}

	public void circleKilled() {
		state = 1;
		lastStateTime = System.currentTimeMillis();
	}

	public int getLife() {
		return life;
	}

}
