package com.example.kdvhesaplama;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText editTutar,editKDV;
    private Button buttonYuzde1,buttonYuzde8,buttonYuzde18,buttonBaslik,buttonHesapla;
    private TextView textViewKdvDahilorHaric,textViewIslemTutari,textViewKdvTutari,textViewToplamTutar;
    private RadioGroup radioGroup;
    private boolean kdvdahil=true;
    private double tutar=0;
    private double kdv=0;

    private TextWatcher editTutardegisimleri=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            try {
                tutar=Double.parseDouble(charSequence.toString());
            }catch (NumberFormatException e){
                tutar=0.0;
            }
            guncelle();


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher editKDVdegisimleri = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
            try {
                kdv=Double.parseDouble(charSequence.toString());
            }catch (NumberFormatException e){
                kdv=0.0;
            }
            guncelle();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private RadioGroup.OnCheckedChangeListener radioGroupdegisimleri=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int i) {
            if (i==R.id.radioButtonKdvDahil){
                kdvdahil=true;
            }
            else if (i==R.id.radioButtonKdvHaric){
                kdvdahil=false;
            }
            guncelle();

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTutar = findViewById(R.id.editTutar);
        editKDV=findViewById(R.id.editKDV);
        buttonBaslik=findViewById(R.id.buttonBaslik);
        buttonHesapla=findViewById(R.id.buttonHesapla);
        buttonYuzde1=findViewById(R.id.buttonYuzde1);
        buttonYuzde8=findViewById(R.id.buttonYuzde8);
        buttonYuzde18=findViewById(R.id.buttonYuzde18);
        textViewIslemTutari=findViewById(R.id.textViewIslemTutari);
        textViewKdvDahilorHaric=findViewById(R.id.textViewKdvDahilorHaric);
        textViewToplamTutar=findViewById(R.id.textViewToplamTutar);
        textViewKdvTutari=findViewById(R.id.textViewKdvTutari);
        radioGroup=findViewById(R.id.radioGroup);

        editTutar.addTextChangedListener(editTutardegisimleri);
        editKDV.addTextChangedListener(editKDVdegisimleri);
        radioGroup.setOnCheckedChangeListener(radioGroupdegisimleri);

        buttonYuzde1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editKDV.setText(String.valueOf(1));
            }
        });
        buttonYuzde8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editKDV.setText(String.valueOf(8));
            }
        });
        buttonYuzde18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editKDV.setText(String.valueOf(18));
            }
        });
        guncelle();



    }
    public void guncelle(){
        buttonHesapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

                DecimalFormat formatter = new DecimalFormat("###,###,##");

                double kdvdahilIslemTutari= tutar / (1+kdv/100);
                double kdvdahilKdvTutari= tutar - kdvdahilIslemTutari;

                double kdvharicKdvsi= tutar * (kdv/100);
                double kdvharicToplamtutar= tutar + kdvharicKdvsi;



                if (kdvdahil){
                    textViewKdvDahilorHaric.setText("### KDV DAHİL ###");
                    textViewKdvDahilorHaric.setTextColor(Color.BLACK);
                    textViewKdvDahilorHaric.setBackgroundResource(R.color.yesil);
                    textViewKdvDahilorHaric.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);

                    textViewToplamTutar.setText(formatter.format(tutar));
                    textViewIslemTutari.setText(formatter.format(kdvdahilIslemTutari));
                    textViewKdvTutari.setText(formatter.format(kdvdahilKdvTutari));


                }
                else {
                    textViewKdvDahilorHaric.setText("### KDV HARİC ###");
                    textViewKdvDahilorHaric.setTextColor(Color.BLACK);
                    textViewKdvDahilorHaric.setBackgroundResource(R.color.kirmizi);
                    textViewKdvDahilorHaric.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);

                    textViewIslemTutari.setText(formatter.format(tutar));
                    textViewKdvTutari.setText(formatter.format(kdvharicKdvsi));
                    textViewToplamTutar.setText(formatter.format(kdvharicToplamtutar));






                }

            }
        });
    }
}