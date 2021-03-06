package com.example.dell.alkitabanak;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


public class quiz_no4_daud extends ActionBarActivity implements RadioGroup.OnCheckedChangeListener{
    TextView tx_view_quiz4;
    RadioGroup rg;
    RadioButton rb1,rb2;
    SharedPreferences pref;
    MediaPlayer mediaPlayer,mediaPlayerSong;
    private final String KEY_SOAL_SATU_DAUD="key_soal_satu_daud";
    private final String KEY_SOAL_DUA_DAUD="key_soal_dua_daud";
    private final String KEY_SOAL_TIGA_DAUD="key_soal_tiga_daud";
    private final String KEY_SOAL_EMPAT_DAUD="key_soal_empat_daud";
    Bundle extras;
    int pilihan_nomor_satu_daud,pilihan_nomor_dua_daud,pilihan_nomor_tiga_daud;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_no4_daud);
        tx_view_quiz4=(TextView)findViewById(R.id.textViewQuizSoalEmpatDaud);
        rb1=(RadioButton)findViewById(R.id.radioButtonSoalEmpatADaud);
        rb2=(RadioButton)findViewById(R.id.radioButtonSoalEmpatBDaud);

        mediaPlayer=MediaPlayer.create(this,R.raw.page_flip);

        Typeface myTypeface = Typeface.createFromAsset(getAssets(),"josefin.ttf");
        tx_view_quiz4.setTypeface(myTypeface);
        rb1.setTypeface(myTypeface);
        rb2.setTypeface(myTypeface);

        extras=getIntent().getExtras();
        pilihan_nomor_satu_daud=extras.getInt("pilihan_nomor_satu_daud");
        pilihan_nomor_dua_daud=extras.getInt("pilihan_nomor_dua_daud");
        pilihan_nomor_tiga_daud=extras.getInt("pilihan_nomor_tiga_daud");

        rg=(RadioGroup)findViewById(R.id.radioGroupSoalEmpatDaud);
        pref= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mediaPlayer.start();

        showSavedPick();

        rg.setOnCheckedChangeListener(this);

        getSupportActionBar().hide();
    }

    public void onCheckedChanged(RadioGroup group, int checkedId){
        if(checkedId==R.id.radioButtonSoalEmpatADaud)
        {
            savedCheckedId(checkedId);
        }
        else if(checkedId==R.id.radioButtonSoalEmpatBDaud)
        {
            savedCheckedId(checkedId);
        }
    }

    public void showSavedPick(){
        int checked;
        checked=getChecked();
        rg.check(checked);
    }

    private int getChecked(){
        return pref.getInt(KEY_SOAL_EMPAT_DAUD,R.id.home);//DEFAULT VALUE
    }

    private void savedCheckedId(int pilihan){
        SharedPreferences.Editor editor= pref.edit();
        editor.putInt(KEY_SOAL_EMPAT_DAUD,pilihan);
        editor.commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayerSong.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayerSong=MediaPlayer.create(this,R.raw.quiz_theme_song);
        mediaPlayerSong.setLooping(true);
        mediaPlayerSong.start();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {//ERROR HANDLING untuk click HOME dan BACK
        if(keyCode==KeyEvent.KEYCODE_HOME){
            SharedPreferences.Editor editor=pref.edit();
            editor.remove(KEY_SOAL_SATU_DAUD);//untuk menghapus data yang ada di SharedPreference
            editor.remove(KEY_SOAL_DUA_DAUD);
            editor.remove(KEY_SOAL_TIGA_DAUD);
            editor.remove(KEY_SOAL_EMPAT_DAUD);
            editor.commit();

            Intent intention=new Intent(Intent.ACTION_MAIN);//buat close app nya!
            intention.addCategory(Intent.CATEGORY_HOME);
            intention.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mediaPlayer.release();
            startActivity(intention);
            finish();
            System.exit(0);
        }
        else if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent in = new Intent(getApplicationContext(),quiz_no3_daud.class);
            mediaPlayer.release();
            in.putExtra("pilihan_nomor_satu_daud", pilihan_nomor_satu_daud);//HARUS DIPAKE JUGA!
            in.putExtra("pilihan_nomor_dua_daud", pilihan_nomor_dua_daud);//HARUS DIPAKE JUGA!
            startActivity(in);
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClickLanjut(View v) {
        Intent intent = new Intent(getApplicationContext(), quiz_result_daud.class);//jangan lupa ganti!
        int checkedRadio = rg.getCheckedRadioButtonId();
        if(checkedRadio!=R.id.radioButtonSoalEmpatADaud && checkedRadio!=R.id.radioButtonSoalEmpatBDaud)
        {
            Toast.makeText(quiz_no4_daud.this, "Jawab Salah Satu", Toast.LENGTH_SHORT).show();
        }
        else {
            intent.putExtra("pilihan_nomor_empat_daud", checkedRadio);
            intent.putExtra("pilihan_nomor_satu_daud", pilihan_nomor_satu_daud);
            intent.putExtra("pilihan_nomor_dua_daud", pilihan_nomor_dua_daud);
            intent.putExtra("pilihan_nomor_tiga_daud", pilihan_nomor_tiga_daud);
            mediaPlayer.release();
            startActivity(intent);
        }
    }

    public void onClickKembali(View v){
        Intent intensi=new Intent(getApplicationContext(),quiz_no3_daud.class);
        intensi.putExtra("pilihan_nomor_satu_daud", pilihan_nomor_satu_daud);//HARUS DIPAKE JUGA!
        intensi.putExtra("pilihan_nomor_dua_daud", pilihan_nomor_dua_daud);//HARUS DIPAKE JUGA!
        mediaPlayer.release();
        startActivity(intensi);
    }
}
