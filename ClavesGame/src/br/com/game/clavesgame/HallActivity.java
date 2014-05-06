package br.com.game.clavesgame;

import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HallActivity extends Activity implements OnClickListener{

	private Button init_game_activity;
	private Button rtn_activity;
	private int pos = 0;
	private PointsDAO pt;
	private List<Points> points;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hall);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		createTable();

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);		

		init_game_activity = (Button) findViewById(R.id.hall_play);
		init_game_activity.setOnClickListener((android.view.View.OnClickListener)this);

		rtn_activity = (Button) findViewById(R.id.hall_return);
		rtn_activity.setOnClickListener((android.view.View.OnClickListener)this);
	}

	public TableRow adcLinha(String pos, String nome, String nclaves, String ncertas, String pontos, String tempo){
		TableRow tr = new TableRow(this);
		tr.setPadding((int) width() / 65, (int) height() / 100, 0, 0);
		TextView tvPos = new TextView(this);
		tvPos.setTextSize(height() / 23);
		tvPos.setPadding(0, 0, (int) height() * 15 / 100, 0);
		tvPos.setText(pos);
		TextView tvNome = new TextView(this);
		tvNome.setTextSize(height() / 23);
		tvNome.setPadding(0, 0, (int) height() * 15 / 100, 0);
		tvNome.setText(nome);
		TextView tvClaves = new TextView(this);
		tvClaves.setTextSize(height() / 23);
		tvClaves.setPadding(0, 0, (int) height() * 15 / 100, 0);
		tvClaves.setText(nclaves);
		TextView tvNotas = new TextView(this);
		tvNotas.setTextSize(height() / 23);
		tvNotas.setPadding(0, 0, (int) height() * 15 / 100, 0);
		tvNotas.setText(ncertas);
		TextView tvPontos = new TextView(this);
		tvPontos.setTextSize(height() / 23);
		tvPontos.setPadding(0, 0, (int) height() * 15 / 100, 0);
		tvPontos.setText(pontos);
		TextView tvTempo = new TextView(this);
		tvTempo.setTextSize(height() / 23);
		tvTempo.setPadding(0, (int) width() / 100, (int) height() * 15 / 100, 0);
		tvTempo.setText(tempo);

		tr.addView(tvPos);
		tr.addView(tvNome);
		tr.addView(tvClaves);
		tr.addView(tvNotas);
		tr.addView(tvPontos);
		tr.addView(tvTempo);

		return tr;

	}

	public void createTable(){

		pt = new PointsDAO(this);

		TableLayout tl = new TableLayout(this);
		tl.setColumnCollapsed(2, true);
		tl.setPadding((int) width() /13, (int) (height() / 7.5), 0, 0);
		tl.addView(adcLinha("Posição","Nome", "Nº de Claves", "Acertos", "Pontuação", "Tempo"));

		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

		try{
			points = pt.getAll();
			Points pts;

			if(points.size() > 0){
				for(Iterator<Points> iter = points.iterator(); iter.hasNext(); ){
					pts = iter.next();
					if(pos < 5){
						tl.addView(adcLinha(String.valueOf(pos + 1)+"º",pts.getNome(), String.valueOf(pts.getClaves()), String.valueOf(pts.getNotas()), 
								String.valueOf(pts.getPontos()), String.valueOf(pts.getTempo())+" min"));
						pos++;
					}
				}
				addContentView(tl, params);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DisplayMetrics display(){
		Resources resources = this.getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		return metrics;
	}

	public float width(){		
		return display().widthPixels;
	}

	public float height(){
		return display().heightPixels;
	}

	// função vazia para desabilitar o botão Back do dispositivo, não fechar o 
	// app ao querer voltar a tela
	public void onBackPressed(){}

	@Override
	public void onClick(View v) {
		int resId = v.getId();
		Intent intent = new Intent();
		if(resId == R.id.hall_play)
			intent.setClass(v.getContext(), InitGameActivity.class);
		else if(resId == R.id.hall_return)
			intent.setClass(v.getContext(), MainActivity.class);
		startActivity(intent);
		finish();		
	}

}
