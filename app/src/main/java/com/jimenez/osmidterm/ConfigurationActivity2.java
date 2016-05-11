package com.jimenez.osmidterm;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ConfigurationActivity2 extends AppCompatActivity {

    private int pageSize,listSize;
    private ArrayList<String> reference = new ArrayList<>();
    private String pass;

    private TextView txtregerence;
    private TextView btnA;
    private TextView btnB;
    private TextView btnC;
    private TextView btnD;
    private TextView btnE;
    private TextView btnF;
    private TextView btnG;
    private TextView btnOk;
    private ImageView btnback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtregerence= (TextView) findViewById(R.id.txtReferences);
        btnA= (TextView)findViewById(R.id.btnA);
        btnB= (TextView)findViewById(R.id.btnB);
        btnC= (TextView)findViewById(R.id.btnC);
        btnD= (TextView)findViewById(R.id.btnD);
        btnE= (TextView)findViewById(R.id.btnE);
        btnF= (TextView)findViewById(R.id.btnF);
        btnG= (TextView)findViewById(R.id.btnG);
        btnOk= (TextView)findViewById(R.id.btnOk);
        btnback= (ImageView) findViewById(R.id.btnBack);

        Intent in= getIntent();
        pageSize= in.getIntExtra("PAGES",0);


        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(reference.size()>=pageSize)
                {
                    Intent intent= new Intent(ConfigurationActivity2.this,Simulation.class);
                    intent.putExtra("RESOURCE",pass);
                    intent.putExtra("PAGES",pageSize);
                    startActivity(intent);

                }
                else
                {
                    Snackbar.make(v, "Reference size must be greater than the number of pages ^_^", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reference.size()>0) {
                    reference.remove(reference.size() - 1);
                    txtregerence.setText(printList(""));
                }
                else
                {
                    Snackbar.make(v, "Reference is already empty", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.add("A");
                txtregerence.setText(printList("A"));
            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.add("B");
                txtregerence.setText(printList("B"));
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.add("C");
                txtregerence.setText(printList("C"));
            }
        });

        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.add("D");
                txtregerence.setText(printList("D"));
            }
        });

        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.add("E");
                txtregerence.setText(printList("E"));
            }
        });

        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.add("F");
                txtregerence.setText(printList("F"));
            }
        });

        btnG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.add("G");
                txtregerence.setText(printList("G"));
            }
        });


    }


    public String printList(String s)
    {
        String newst="";
        for(int a=0;a<reference.size();a++)
        {
            if(a==0)
            {
                newst=reference.get(a);
            }
            else
            {
                newst = newst + "," + reference.get(a);
            }
        }
        pass=newst;
        return newst;
    }


    ////////// PRINTERS ///////////////////////////////////////////

    public void showToast(String msg) {
        Toast.makeText(ConfigurationActivity2.this, msg, Toast.LENGTH_LONG).show();
    }

    //////////////////////////////////////////////////////////////

}
