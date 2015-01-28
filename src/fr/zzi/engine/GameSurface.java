package fr.zzi.engine;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

	private GameEngine gameEngine;
	private SurfaceHolder surfaceHolder;
	private Context context;
	private GameThread gameThread;

	public GameSurface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
		init();
	}

	public GameSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	void init() {
		SurfaceHolder surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);

		final WindowManager w = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		final Display d = w.getDefaultDisplay();
		final DisplayMetrics dm = new DisplayMetrics();
		d.getMetrics(dm);
		gameEngine = new GameEngine();
		gameEngine.init(context, dm.widthPixels, dm.heightPixels);

		gameThread = new GameThread(surfaceHolder, context, new Handler(),
				gameEngine);
		setFocusable(true);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
		boolean retry = true;
		gameThread.state = GameThread.PAUSED;
		while (retry) {
			try {
				gameThread.join();
				retry = false;
			} catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		if (gameThread.state == GameThread.PAUSED) {
			gameThread = new GameThread(getHolder(), context, new Handler(),
					gameEngine);
			gameThread.start();
		} else {
			gameThread.start();
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			// deal event
			gameEngine.objectsHandler.tapEvent();
			return true;
		}
		return false;

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}
}
