package br.com.game.clavesgame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

public class InstrActivity extends Activity implements OnClickListener {

	private Button init_game_activity;
	private Button rtn_activity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_instr);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		init_game_activity = (Button) findViewById(R.id.instr_play);
		init_game_activity.setOnClickListener((android.view.View.OnClickListener)this);

		rtn_activity = (Button) findViewById(R.id.instr_return);
		rtn_activity.setOnClickListener((android.view.View.OnClickListener)this);

	}

	// função vazia para desabilitar o botão Back do dispositivo, não fechar o app ao querer voltar a tela
	public void onBackPressed(){}

	@Override
	public void onClick(View v) {
		int resId = v.getId();
		Intent intent = new Intent();
		if(resId == R.id.instr_play)
			intent.setClass(v.getContext(), InitGameActivity.class);
		else if(resId == R.id.instr_return)
			intent.setClass(v.getContext(), MainActivity.class);
		startActivity(intent);
		finish();

	}

}
