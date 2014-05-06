package br.com.game.clavesgame;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class PointsActivity extends Activity implements OnClickListener{

	private Button btn_replay;
	private Button btn_hall;
	private Button btn_finish;
	private TextView txt_nome;
	private TextView txt_claves;
	private TextView txt_notas;
	private TextView txt_pontos;
	private TextView txt_tempo;
	String nome;
	int tempo;
	int claves;
	int notas;
	int pontos;
	PointsDAO pt;
	
	public static Points points;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_points);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	
		Bundle ext = getIntent().getExtras();
		this.nome = ext.getString("nome");
		this.tempo = ext.getInt("tempo");
		this.claves = ext.getInt("claves");
		this.notas = ext.getInt("notas");
		this.pontos = ext.getInt("pontos");
		
		points = new Points(nome, claves, notas, pontos, tempo);
		
		btn_replay = (Button) findViewById(R.id.points_play);
		btn_replay.setOnClickListener((android.view.View.OnClickListener)this);

		btn_hall = (Button) findViewById(R.id.points_hall);
		btn_hall.setOnClickListener((android.view.View.OnClickListener)this);

		btn_finish = (Button) findViewById(R.id.points_finish);
		btn_finish.setOnClickListener((android.view.View.OnClickListener)this);

		txt_nome = (TextView) findViewById(R.id.pt_nome);
		txt_nome.setText(points.getNome());

		txt_claves = (TextView) findViewById(R.id.pt_claves);
		txt_claves.setText(String.valueOf(points.getClaves()));

		txt_notas = (TextView) findViewById(R.id.pt_notas);
		txt_notas.setText(String.valueOf(points.getNotas()));

		txt_pontos = (TextView) findViewById(R.id.pt_pontos);
		txt_pontos.setText(String.valueOf(points.getPontos()));

		txt_tempo = (TextView) findViewById(R.id.pt_tempo);
		txt_tempo.setText(String.valueOf(points.getTempo()) + " min");
		
		pt = new PointsDAO(this);		
	}

	@Override
	public void onClick(View v) {
		
		int id = v.getId();
		Intent intent = new Intent();
		switch (id) {
		case R.id.points_play:				
			intent.setClass(v.getContext(), InitGameActivity.class);
			break;
		case R.id.points_hall:				
			intent.setClass(v.getContext(), HallActivity.class);
			break;
		case R.id.points_finish:				
			intent.setClass(v.getContext(), MainActivity.class);
			break;
		}
		pt.insert(points);
		startActivity(intent);
		finish();

	}

}