package fr.zzi.activity;

import android.app.Activity;
import android.os.Bundle;
import fr.zzi.artifact.R;

public class GameActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
    }
}
