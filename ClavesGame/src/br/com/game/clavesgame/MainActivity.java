package br.com.game.clavesgame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

public class MainActivity extends Activity{

	private Button init_game_activity;
	private Button hall_activity;
	private Button instr_activity;
	private Button option_activity;
	private Button exit;
	private Intent intent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		intent = new Intent();

		init_game_activity = (Button) findViewById(R.id.main_play);
		init_game_activity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent.setClass(v.getContext(), InitGameActivity.class);
				startActivity(intent);
				finish();
			}
		});

		hall_activity = (Button) findViewById(R.id.main_hall);
		hall_activity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent.setClass(v.getContext(), HallActivity.class);
				startActivity(intent);
				finish();
			}
		});

		instr_activity = (Button) findViewById(R.id.main_instr);
		instr_activity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				intent.setClass(v.getContext(), InstrActivity.class);
				startActivity(intent);
				finish();
			}
		});

		option_activity = (Button) findViewById(R.id.main_option);
		option_activity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent.setClass(v.getContext(), OptionActivity.class);
				startActivity(intent);
				finish();
			}
		});

		exit = (Button) findViewById(R.id.main_exit);
		exit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				System.exit(0);
				finish();
			}
		});
	}
}
