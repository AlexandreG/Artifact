package fr.zzi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import fr.zzi.artifact.R;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

	}

	public void onClickGame(View v) {
		// start game
		Intent intent = new Intent(this, GameActivity.class);
		startActivity(intent);
	}
}
