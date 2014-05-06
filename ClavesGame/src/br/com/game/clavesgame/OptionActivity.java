package br.com.game.clavesgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;

public class OptionActivity extends Activity implements OnClickListener {

	private Button rtn_activity; //botão VOLTAR
	private Button init_game_activity; //botão JOGAR

	private CheckBox cb_sound_v; //Som quando acerta a nota
	private CheckBox cb_sound_f; //Som quando erra a nota
	private CheckBox cb_clave_sol; //ve se clave de sol foi selecionada
	private CheckBox cb_clave_fa; //ve se clave de fá foi selecionada
	private CheckBox cb_clave_do3; //ve se clave de do3 foi selecionada
	private CheckBox cb_clave_do4; //ve se clave de do4 foi selecionada

	private RadioButton rb_time_1;
	private RadioButton rb_time_3;
	private RadioButton rb_time_5;
	private RadioButton rb_sys_sj;
	private RadioButton rb_sys_en;
	private RadioButton rb_sys_nt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_option);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		rtn_activity = (Button) findViewById(R.id.option_return);
		rtn_activity.setOnClickListener((android.view.View.OnClickListener)this);	

		init_game_activity = (Button) findViewById(R.id.option_play);
		init_game_activity.setOnClickListener((android.view.View.OnClickListener)this);

		rb_time_1 = (RadioButton) findViewById(R.id.op_bt_time_1);
		rb_time_3 = (RadioButton) findViewById(R.id.op_bt_time_3);
		rb_time_5 = (RadioButton) findViewById(R.id.op_bt_time_5);

		rb_sys_sj = (RadioButton) findViewById(R.id.op_rb_sy_sf);
		rb_sys_en = (RadioButton) findViewById(R.id.op_rb_sy_en);
		rb_sys_nt = (RadioButton) findViewById(R.id.op_rb_sy_nt);

		cb_sound_v = (CheckBox) findViewById(R.id.op_cb_snd_act);
		cb_sound_f = (CheckBox) findViewById(R.id.op_cb_snd_err);

		cb_clave_sol = (CheckBox) findViewById(R.id.op_cb_cl_sol);
		cb_clave_fa = (CheckBox) findViewById(R.id.op_cb_cl_fa);
		cb_clave_do3 = (CheckBox) findViewById(R.id.op_cb_cl_do3);
		cb_clave_do4 = (CheckBox) findViewById(R.id.op_cb_cl_do4);

		recoverPreferences();

	}

	// função vazia para desabilitar o botão Back do dispositivo, não fechar o app ao querer voltar a tela
	public void onBackPressed(){}

	private void savePreferences(){
		boolean clave = false;
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);

		SharedPreferences.Editor out = pref.edit();
		out.clear();
		// preferencia de tempo
		if(rb_time_1.isChecked())
			out.putInt("time", 1);
		else
			if(rb_time_3.isChecked())
				out.putInt("time", 3);
			else
				if(rb_time_5.isChecked())
					out.putInt("time", 5);

		// as preferencias de som podem ser alteradas para se tocar quando acerta / erra a nota
		if(cb_sound_f.isChecked())
			out.putInt("somF", 1); // 1 de Verdadeiro, habilita o som quando erra a nota
		if(cb_sound_v.isChecked())
			out.putInt("somV", 1); // 1 de Verdadeiro, habilita o som quando acerta a nota

		// preferencia de sistemas de claves (aqui terá um bug, porque eu só quero o normal, nada de tipo inglês)
		if(rb_sys_sj.isChecked())
			out.putInt("system", 0);
		else
			if(rb_sys_en.isChecked())
				out.putInt("system", 1);
			else
				if(rb_sys_nt.isChecked())
					out.putInt("system", 2);
		
		// as preferencias de clave são utilizadas para definir as claves que o usuário queira usar
		if(cb_clave_sol.isChecked()){
			out.putInt("claveS", 1); // 1 de Verdadeiro, habilita clave de sol
			clave = true;
		}
		if(cb_clave_fa.isChecked()){
			out.putInt("claveF", 1); // 1 de Verdadeiro, habilita clave de fá
			clave = true;
		}
		if(cb_clave_do3.isChecked()){
			out.putInt("claveD3", 1); // 1 de Verdadeiro, habilita clave de dó3
			clave = true;
		}
		if(cb_clave_do4.isChecked()){
			out.putInt("claveD4", 1); // 1 de Verdadeiro, habilita clave de dó4
			clave = true;
		}
		
		if(!clave){
			out.putInt("claveS", 1); // caso Sol e Fá forem desabilitados, habilita apenas a clave de Sol
		}
		// dá o commit (\o/)
		out.commit();
	}

	private void recoverPreferences(){
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		int time = pref.getInt("time", 1);
		int sndF = pref.getInt("somF", 0);
		int sndV = pref.getInt("somV", 0);
		int syst = pref.getInt("system", 0);
		int clsl = pref.getInt("claveS", 0);
		int clfa = pref.getInt("claveF", 0);
		int cld3 = pref.getInt("claveD3", 0);
		int cld4 = pref.getInt("claveD4", 0);
		boolean clave = false;
		
		if(time == 1)
			rb_time_1.setChecked(true);
		else
			if(time == 3)
				rb_time_3.setChecked(true);
			else
				if(time == 5)
					rb_time_5.setChecked(true);
				else
					rb_time_1.setChecked(true);

		if(sndF == 1)
			cb_sound_f.setChecked(true);
		if(sndV == 1)
			cb_sound_v.setChecked(true);


		if(syst == 0)
			rb_sys_sj.setChecked(true);
		else
			if(syst == 1)
				rb_sys_en.setChecked(true);
			else
				if(syst == 2)
					rb_sys_nt.setChecked(true);
			else
				rb_sys_sj.setChecked(true);

		if(clsl == 1){
			cb_clave_sol.setChecked(true);
			clave = true;
		}
		if(clfa == 1){
			cb_clave_fa.setChecked(true);
			clave = true;
		}
		if(cld3 == 1){
			cb_clave_do3.setChecked(true);
			clave = true;
		}			
		if(cld4 == 1){
			cb_clave_do4.setChecked(true);
			clave = true;
		}
		if(!clave)
			cb_clave_sol.setChecked(true);
		

	}

	@Override
	public void onClick(View v) {
		int resId = v.getId();
		Intent intent = new Intent();
		if(resId == R.id.option_play){
			intent.setClass(v.getContext(), InitGameActivity.class);
			savePreferences();
		}
		else if(resId == R.id.option_return)
			intent.setClass(v.getContext(), MainActivity.class);
		startActivity(intent);
		finish();

	}

}