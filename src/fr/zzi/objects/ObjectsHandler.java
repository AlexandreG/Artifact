package fr.zzi.objects;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;

public class ObjectsHandler {
	private List<Circle> circles;
	private int circleGap;
	private int circlesNb;

	private Core core;
	private int level;

	private Paint paint;
	private Paint gameOverPaint;
	private Random random;

	private int score;
	private long lastTap; //prevent from touch spam
	private Rect gameOverRect;
	private int screenWidth;
	private int screenHeight;
	private int speed;

	public ObjectsHandler(Context ct,
			int sw, int sh) {
		this.screenWidth = sw;
		this.screenHeight = sh;
		
		this.score = 0;
		this.lastTap = System.currentTimeMillis();
		this.circlesNb = 15;
		this.circleGap = 150;
		this.paint = new Paint();
		initGameOverPaint();
		this.gameOverRect = new Rect();
		this.random = new Random();
		this.circles = new ArrayList<Circle>();

		double radius = sw;
		float x = sw / 2;
		float y = sh / 2;

		this.core = new Core(x, y, radius / 5);
		this.speed = 5;
		this.level = 1;

		//init circles
		int c = 0;
		for (int i = 0; i < circlesNb; i++) {
			c = Color.rgb(random.nextInt(255), random.nextInt(255),
					random.nextInt(255));

			if (!random.nextBoolean()) {
				radius += circleGap / 2;
			} else {
				radius += circleGap;
			}
			circles.add(new Circle(radius, c, x, y));
		}
	}

	public void update() {
		for(int i=0 ; i < circles.size() ; ++i){
			if (circles.get(i).getRadius() >= -100D) {
				circles.get(i).move(speed);
				if (circles.get(i).getRadius() < core.getRadius()
						- (double) core.getHitzone()
						&& !circles.get(i).isKilled()) {
					core.hitted();
					circles.get(i).kill();
				}
			}
		}
		
		//reload level
		if (circles.get(circles.size()-1).getRadius() <= -100D) {
			levelUp();
		}
		
		//update core
		core.updateState();
	}

	public void draw(Canvas canvas) {
		//the circles
		for (int i = circles.size() - 1; i >= 0; --i) {
			circles.get(i).draw(canvas, paint);
		}
		
		//the core
		core.draw(canvas, paint);
	}

	public Core getCore() {
		return core;
	}
	
	public void tapEvent() {
		//a tap only each 100ms
		if(System.currentTimeMillis() -lastTap <200){
			return;
		}
		lastTap = System.currentTimeMillis();
			
		core.tapEvent();
		
		
		for (Circle c : circles) {
			if(c.getRadius() >= core.getRadius()
					- (double) core.getHitzone()
					&& c.getRadius() <= core.getRadius()
							+ (double) core.getHitzone() && !c.isKilled()){
				core.circleKilled();
				c.kill();
				score = score + speed;
			}
		}
	}

	private void levelUp() {
		speed = 1 + speed;
		if (speed > 10) {
			speed = 10;
		}
		level = 1 + level;

		float radius = screenWidth;
		for (Circle c : circles) {
			c.setColor(Color.rgb(random.nextInt(255), random.nextInt(255),
					random.nextInt(255)));
			if (!random.nextBoolean()) {
				radius += circleGap / 2;
			} else {
				radius += circleGap;
			}
			c.setRadius(radius);
			c.rebirth();
		}
	}

	private void initGameOverPaint(){
		gameOverPaint = new Paint();
		gameOverPaint.setStyle(android.graphics.Paint.Style.FILL);
		gameOverPaint.setColor(Color.BLACK);
		gameOverPaint.setStrokeWidth(1.0F);
		gameOverPaint.setAntiAlias(true);
		gameOverPaint.setTextSize(screenWidth / 6);
		gameOverPaint.setTypeface(Typeface.create("Helvetica", 1));
	}
	
	public void drawGameOver(Canvas canvas) {
		String s = Integer.toString(score);
		gameOverPaint.getTextBounds(s, 0, s.length(), gameOverRect);
		canvas.drawText(s, screenWidth / 2 - (gameOverRect.right - gameOverRect.left) / 2,
				screenHeight / 2, gameOverPaint);
	}
}
