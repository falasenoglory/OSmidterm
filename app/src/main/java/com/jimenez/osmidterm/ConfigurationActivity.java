package com.jimenez.osmidterm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ConfigurationActivity extends AppCompatActivity {

    private ImageView btnPlus;
    private ImageView btnMinus;
    private TextView txtPages;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i= Integer.parseInt(txtPages.getText().toString());
                if(i==0)
                {Snackbar.make(view, "Input cannot be zero", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();}
                else
                {
                    Intent intent = new Intent(ConfigurationActivity.this, ConfigurationActivity2.class);
                    intent.putExtra("PAGES",i);
                    startActivity(intent);

                }
            }
        });


        btnPlus= (ImageView) findViewById(R.id.btnAdd);
        btnMinus= (ImageView) findViewById(R.id.btnMinus);
        txtPages= (TextView) findViewById(R.id.txtHitRate);

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i= Integer.parseInt(txtPages.getText().toString());
                if(i<7)
                    i++;
                txtPages.setText(i+"");
            }
        });

        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i= Integer.parseInt(txtPages.getText().toString());
                if(i>0)
                    i--;
                txtPages.setText(i+"");
            }
        });

    }

}
