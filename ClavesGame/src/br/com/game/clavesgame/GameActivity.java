package br.com.game.clavesgame;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameActivity extends Activity implements OnClickListener{

	private TextView game_time;
	private TextView game_name;
	private TextView game_notas;
	private TextView game_erros;
	private TextView game_pontos;
	private ImageView img_clave;
	private ImageView img_nota;

	//opcoes de jogo
	private int time;
	private boolean clvS;
	private boolean clvF;
	private boolean clvD3;
	private boolean clvD4;
	private boolean somA;
	private boolean somE;
	private boolean solf;
	private boolean ingl;
	private boolean nots;

	// variáveis do jogo
	String name;
	int n_clv;
	int notas;
	int erros;
	int pontos;
	int nota_musica;

	MediaPlayer player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		recoverPreferences();

		notas = 0;
		erros = 0;
		pontos = 0;

		Bundle ext = getIntent().getExtras();
		name = ext.getString("nome").toString();

		game_time = (TextView) findViewById(R.id.game_time);

		game_name = (TextView) findViewById(R.id.game_name);
		game_name.setText(name);

		game_notas = (TextView) findViewById(R.id.game_notas);
		game_notas.setText("Notas: " + notas);
		
		game_erros = (TextView) findViewById(R.id.game_erros);
		game_erros.setText("Erros: " + erros);

		game_pontos = (TextView) findViewById(R.id.game_pontos);
		game_pontos.setText("Pontos: " + pontos);
		
		img_nota = (ImageView) findViewById(R.id.img_nota);
		img_nota.setImageResource(colocaImagemNota());
		
		img_clave = (ImageView) findViewById(R.id.img_clave);
		img_clave.setImageResource(colocaImagemClave(clvS, clvF, clvD3, clvD4));

		Button bt_do = (Button) findViewById(R.id.bt_do);
		Button bt_re = (Button) findViewById(R.id.bt_re);		
		Button bt_mi = (Button) findViewById(R.id.bt_mi);
		Button bt_fa = (Button) findViewById(R.id.bt_fa);
		Button bt_sol = (Button) findViewById(R.id.bt_sol);
		Button bt_la = (Button) findViewById(R.id.bt_la);
		Button bt_si = (Button) findViewById(R.id.bt_si);

		if(solf){
			bt_do.setText(" ");
			bt_re.setText(" ");
			bt_mi.setText(" ");
			bt_fa.setText(" ");
			bt_sol.setText(" ");
			bt_la.setText(" ");
			bt_si.setText(" ");
		}else if(ingl){
			bt_do.setText("C");
			bt_re.setText("D");
			bt_mi.setText("E");
			bt_fa.setText("F");
			bt_sol.setText("G");
			bt_la.setText("A");
			bt_si.setText("B");
		}
		else if(nots){
			bt_do.setText("DÓ");
			bt_re.setText("RÉ");
			bt_mi.setText("MI");
			bt_fa.setText("FÁ");
			bt_sol.setText("SOL");
			bt_la.setText("LÁ");
			bt_si.setText("SI");
		}

		bt_do.setOnClickListener((android.view.View.OnClickListener)this);
		bt_re.setOnClickListener((android.view.View.OnClickListener)this);
		bt_mi.setOnClickListener((android.view.View.OnClickListener)this);
		bt_fa.setOnClickListener((android.view.View.OnClickListener)this);
		bt_sol.setOnClickListener((android.view.View.OnClickListener)this);
		bt_la.setOnClickListener((android.view.View.OnClickListener)this);
		bt_si.setOnClickListener((android.view.View.OnClickListener)this);

		long tempoTotal = time * 60000;
		contagemRegressiva(tempoTotal);
	}

	@Override
	public void onClick(View v) {
		int resId = v.getId();
		int notaId = (Integer) img_nota.getTag();
		int claveId = (Integer) img_clave.getTag();
		verificaNota(claveId, notaId, resId);
		game_pontos.setText("Pontos: " + pontos);
		game_notas.setText("Notas: " + notas);
		game_erros.setText("Erros: " + erros);
		img_nota.setImageResource(colocaImagemNota());
		img_clave.setImageResource(colocaImagemClave(clvS, clvF, clvD3, clvD4));		
	}

	public int colocaImagemClave(boolean sol, boolean fa, boolean do3, boolean do4){
		int resId = 0;
		Random rand = new Random();
		int numClave = rand.nextInt(4);		
		String nomeClave;

		switch (numClave) {
		case 0:
			if(sol) nomeClave = "sol";
			else if(fa)	nomeClave = "fa";
			else if(do3) nomeClave = "do3";
			else nomeClave = "do4";
			break;
		case 1:
			if(fa) nomeClave = "fa";
			else if(do3) nomeClave = "do3";
			else if(do4) nomeClave = "do4";
			else nomeClave = "sol";
			break;
		case 2:
			if(do3)	nomeClave = "do3";
			else if(do4) nomeClave = "do4";
			else if(sol) nomeClave = "sol";
			else nomeClave = "fa";
			break;
		case 3:
			if(do4) nomeClave = "do4";
			else if(sol) nomeClave = "sol";
			else if(fa) nomeClave = "fa";
			else nomeClave = "do3";
			break;

		default:
			nomeClave = "sol";			
			break;
		}

		resId = getResources().getIdentifier(nomeClave, "drawable", getPackageName());
		img_clave.setTag(resId);
		return resId;
	}

	public int colocaImagemNota(){
		Random rand = new Random();
		int numNota = rand.nextInt(20);		
		String nomeNota = "n"+ numNota;
		int resId = getResources().getIdentifier(nomeNota, "drawable", getPackageName());
		img_nota.setTag(resId);
		return resId;
	}

	public void onBackPressed(){} // função vazia para desabilitar o botão Back do dispositivo	

	private void recoverPreferences(){
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		boolean clave = false;
		int time = pref.getInt("time", 1);
		int sndF = pref.getInt("somF", 0);
		int sndV = pref.getInt("somV", 0);
		int syst = pref.getInt("system", 0);
		int clsl = pref.getInt("claveS", 0);
		int clfa = pref.getInt("claveF", 0);
		int cld3 = pref.getInt("claveD3", 0);
		int cld4 = pref.getInt("claveD4", 0);

		switch (time) {
		case 1: this.time = 1; break;
		case 3: this.time = 3; break;
		case 5:	this.time = 5; break;
		default: this.time = 1; break;
		}

		if(sndF == 1) this.somE = true;
		if(sndV == 1) this.somA = true;

		if(syst == 0) this.solf = true;
		else if(syst == 1) this.ingl = true;
		else if(syst == 2) this.nots = true;
		else this.solf = true;

		if(clsl == 1) {this.clvS = true; this.n_clv++; clave = true;}
		if(clfa == 1) {this.clvF = true; this.n_clv++; clave = true;}
		if(cld3 == 1) {this.clvD3 = true; this.n_clv++; clave = true;}
		if(cld4 == 1) {this.clvD4 = true; this.n_clv++; clave = true;}
		if(!clave){this.clvS = true; this.n_clv++;}
		
	}

	public void contagemRegressiva(final long tempoTotal){
		new CountDownTimer(tempoTotal, 1000) {

			long t = tempoTotal - 5000;

			public void onTick(long tempoTotal) {
				game_time.setText("Tempo: " + tempoTotal / 1000 + " seg.");
				if(tempoTotal <= t){
					t = t - 5000;
				}
				game_pontos.setText("Pontos: " + pontos);
			}

			public void onFinish() {
				AlertDialog.Builder mensagem = new AlertDialog.Builder(GameActivity.this);
				mensagem.setTitle("Acabou o tempo!");
				mensagem.setMessage("O seu tempo acabou!!!\nClique em OK para ver sua Pontuação!");
				mensagem.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						Intent intent = new Intent();
						intent.setClass(GameActivity.this, PointsActivity.class);
						intent.putExtra("nome", name);
						intent.putExtra("tempo", time);
						intent.putExtra("claves", n_clv);
						intent.putExtra("notas", notas);
						intent.putExtra("pontos", pontos);
						startActivity(intent);  
						finish();
					}
				});
				mensagem.show();
			}
		}.start();
	}

	public void tocar(int rawId){
		player = MediaPlayer.create(this, rawId);
		player.start();
	}	

	public void pontuar(boolean certo, int resId){
		if(certo){
			pontos = pontos + 10;
			notas++;
			if(somA) tocar(resId);
		}else {pontos = (pontos > 0) ? pontos - 5 : pontos - 0; erros++; if(somE) tocar(R.raw.err);}
	}

	public void verNotaDo(int claveId, int notaId){
		boolean certo = false;
		if(claveId == R.drawable.sol && (notaId == R.drawable.n4 || notaId == R.drawable.n11 || notaId == R.drawable.n18)){certo = true;}
		else if(claveId == R.drawable.fa && (notaId == R.drawable.n2 || notaId == R.drawable.n9 || notaId == R.drawable.n16)){certo = true;}
		else if(claveId == R.drawable.do3 && (notaId == R.drawable.n3 || notaId == R.drawable.n10 || notaId == R.drawable.n17)){certo = true;}
		else if(claveId == R.drawable.do4 && (notaId == R.drawable.n5 || notaId == R.drawable.n12 || notaId == R.drawable.n19)){certo = true;}
		pontuar(certo, R.raw.ndo);
	}

	public void verNotaRe(int claveId, int notaId){
		boolean certo = false;
		if(claveId == R.drawable.sol && (notaId == R.drawable.n5 || notaId == R.drawable.n12 || notaId == R.drawable.n19)){certo = true;}
		else if(claveId == R.drawable.fa && (notaId == R.drawable.n3 || notaId == R.drawable.n10 || notaId == R.drawable.n17)){certo = true;}
		else if(claveId == R.drawable.do3 && (notaId == R.drawable.n4 || notaId == R.drawable.n11 || notaId == R.drawable.n18)){certo = true;}
		else if(claveId == R.drawable.do4 && (notaId == R.drawable.n6 || notaId == R.drawable.n13 || notaId == R.drawable.n20)){certo = true;}
		pontuar(certo, R.raw.nre);
	}

	public void verNotaMi(int claveId, int notaId){
		boolean certo = false;
		if(claveId == R.drawable.sol && (notaId == R.drawable.n6 || notaId == R.drawable.n13 || notaId == R.drawable.n20)){certo = true;}
		else if(claveId == R.drawable.fa && (notaId == R.drawable.n4 || notaId == R.drawable.n11 || notaId == R.drawable.n18)){certo = true;}
		else if(claveId == R.drawable.do3 && (notaId == R.drawable.n5 || notaId == R.drawable.n12 || notaId == R.drawable.n19)){certo = true;}
		else if(claveId == R.drawable.do4 && (notaId == R.drawable.n0 || notaId == R.drawable.n7 || notaId == R.drawable.n14)){certo = true;}
		pontuar(certo, R.raw.nmi);
	}

	public void verNotaFa(int claveId, int notaId){
		boolean certo = false;
		if(claveId == R.drawable.sol && (notaId == R.drawable.n0 || notaId == R.drawable.n7 || notaId == R.drawable.n14)){certo = true;}
		else if(claveId == R.drawable.fa && (notaId == R.drawable.n5 || notaId == R.drawable.n12 || notaId == R.drawable.n19)){certo = true;} 
		else if(claveId == R.drawable.do3 && (notaId == R.drawable.n6 || notaId == R.drawable.n13 || notaId == R.drawable.n20)){certo = true;}
		else if(claveId == R.drawable.do4 && (notaId == R.drawable.n1 || notaId == R.drawable.n8 || notaId == R.drawable.n15)){certo = true;}
		pontuar(certo, R.raw.nfa);
	}

	public void verNotaSol(int claveId, int notaId){
		boolean certo = false;
		if(claveId == R.drawable.sol && (notaId == R.drawable.n1 || notaId == R.drawable.n8 || notaId == R.drawable.n15)){certo = true;}
		else if(claveId == R.drawable.fa && (notaId == R.drawable.n6 || notaId == R.drawable.n13 || notaId == R.drawable.n20)){certo = true;}
		else if(claveId == R.drawable.do3 && (notaId == R.drawable.n0 || notaId == R.drawable.n7 || notaId == R.drawable.n14)){certo = true;}
		else if(claveId == R.drawable.do4 && (notaId == R.drawable.n2 || notaId == R.drawable.n9 || notaId == R.drawable.n16)){certo = true;}
		pontuar(certo, R.raw.nsol);
	}

	public void verNotaLa(int claveId, int notaId){
		boolean certo = false;
		if(claveId == R.drawable.sol && (notaId == R.drawable.n2 || notaId == R.drawable.n9 || notaId == R.drawable.n16)){certo = true;}
		else if(claveId == R.drawable.fa && (notaId == R.drawable.n0 || notaId == R.drawable.n7 || notaId == R.drawable.n14)){certo = true;}
		else if(claveId == R.drawable.do3 && (notaId == R.drawable.n1 || notaId == R.drawable.n8 || notaId == R.drawable.n15)){certo = true;}
		else if(claveId == R.drawable.do4 && (notaId == R.drawable.n3 || notaId == R.drawable.n10 || notaId == R.drawable.n17)){certo = true;}
		pontuar(certo, R.raw.nla);
	}

	public void verNotaSi(int claveId, int notaId){
		boolean certo = false;
		if(claveId == R.drawable.sol && (notaId == R.drawable.n3 || notaId == R.drawable.n10 || notaId == R.drawable.n17)){certo = true;}
		else if(claveId == R.drawable.fa && (notaId == R.drawable.n1 || notaId == R.drawable.n8 || notaId == R.drawable.n15)){certo = true;}
		else if(claveId == R.drawable.do3 && (notaId == R.drawable.n2 || notaId == R.drawable.n9 || notaId == R.drawable.n16)){certo = true;}
		else if(claveId == R.drawable.do4 && (notaId == R.drawable.n4 || notaId == R.drawable.n11 || notaId == R.drawable.n18)){certo = true;}
		pontuar(certo, R.raw.nsi);
	}

	public void verificaNota(int claveId, int notaId, int resId){
		switch (resId) {
		case R.id.bt_do: verNotaDo(claveId, notaId); break;
		case R.id.bt_re: verNotaRe(claveId, notaId); break;
		case R.id.bt_mi: verNotaMi(claveId, notaId); break;
		case R.id.bt_fa: verNotaFa(claveId, notaId); break;
		case R.id.bt_sol: verNotaSol(claveId, notaId); break;
		case R.id.bt_la: verNotaLa(claveId, notaId); break;
		case R.id.bt_si: verNotaSi(claveId, notaId); break;
		default: break;
		}
	}

}