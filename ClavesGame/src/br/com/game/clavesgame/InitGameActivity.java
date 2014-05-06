package br.com.game.clavesgame;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class InitGameActivity extends Activity {

	private AutoCompleteTextView init_game_name;
	private Button rtn_activity;
	private Button game_start;
	private PointsDAO pt;
	private List<Points> name_list;
	private List<String> nomes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_init_game);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		pt = new PointsDAO(this);
		init_game_name = (AutoCompleteTextView) findViewById(R.id.init_game_name);
		nomes = new ArrayList<String>();

		try{
			name_list = pt.getAll();
			Points pts;
			for (Iterator<Points> iter = name_list.iterator(); iter.hasNext(); ){
				pts = iter.next();
				if(!nomes.contains(pts.getNome()))
					nomes.add(pts.getNome());
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nomes);
			init_game_name.setAdapter(adapter);
		}catch(Exception e){
			e.printStackTrace();
		}

		init_game_name.setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if(keyCode == KeyEvent.KEYCODE_ENTER ){
					if(init_game_name.getText().toString().length() > 3){
						String nome = init_game_name.getText().toString();
						Intent intent = new Intent();
						intent.setClass(v.getContext(), GameActivity.class);
						intent.putExtra("nome", nome);
						startActivity(intent);
						finish();
					}else{
						AlertDialog.Builder mensagem = new AlertDialog.Builder(InitGameActivity.this);
						mensagem.setMessage("Digite um nome válido!");
						mensagem.setNeutralButton("OK", null);
						mensagem.show();
					}
					return true;
				}
				return false;
			}
		});	

		game_start = (Button) findViewById(R.id.init_game_play);
		game_start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				if(init_game_name.getText().toString().length() > 3){
					String nome = init_game_name.getText().toString();
					intent.setClass(v.getContext(), GameActivity.class);
					intent.putExtra("nome", nome);	
					startActivity(intent);  
					finish();
				}else{
					AlertDialog.Builder mensagem = new AlertDialog.Builder(InitGameActivity.this);
					mensagem.setMessage("Digite um nome válido!");
					mensagem.setNeutralButton("OK", null);
					mensagem.show();
				}			
			}
		});

		rtn_activity = (Button) findViewById(R.id.init_game_return);
		rtn_activity.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(v.getContext(), MainActivity.class);
				startActivity(intent);  
				finish();

			}
		});	

	}

	public void onBackPressed(){
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);  
		finish();
	}
}