package fr.zzi.engine;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import fr.zzi.objects.ObjectsHandler;

public class GameEngine {
	ObjectsHandler objectsHandler;

	private Paint whitePaint;

	public void init(Context context, int sw, int sh) {
		whitePaint = new Paint();
		whitePaint.setColor(Color.WHITE);
		whitePaint.setStyle(Style.FILL);

		objectsHandler = new ObjectsHandler(context, sw, sh);
	}

	public void update() {
		if (objectsHandler.getCore().getLife() > 0) {
			objectsHandler.update();
		}
	}

	public void draw(Canvas canvas) {
		try {
			canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), whitePaint);

			if (objectsHandler.getCore().getLife() > 0) {
				// normal game
				objectsHandler.draw(canvas);
			} else {
				// game over
				objectsHandler.drawGameOver(canvas);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
