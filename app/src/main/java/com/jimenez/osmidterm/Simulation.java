package com.jimenez.osmidterm;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jimenez.osmidterm.Models.LRU;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Simulation extends AppCompatActivity {


    private int pageSize,hitrate,pageFault;
    private TextView txtHitRate;
    private TextView txtPageFault;
    private TextView txtReference;
    private TextView txtContent;
    private TextView txtTime;

    private List<String> resourcesList= new ArrayList<>();
    private ArrayList<LRU> LRUList= new ArrayList<>();
    private String header,content;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        txtHitRate=(TextView)findViewById(R.id.txtHitRate);
        txtPageFault=(TextView)findViewById(R.id.txtPageFaults);
        txtReference=(TextView)findViewById(R.id.txtReferencelist);
        txtContent=(TextView) findViewById(R.id.txtContent);
        txtTime=(TextView) findViewById(R.id.txtTime);

        FloatingActionButton fabReplay = (FloatingActionButton) findViewById(R.id.fabReplay);

        /////////////////////////////////// GET DATA ////////////////////////////////////////////////////////////////////
        final Intent in= getIntent();
        pageSize= in.getIntExtra("PAGES",0);
        final String res=in.getStringExtra("RESOURCE");
        resourcesList = Arrays.asList(res.split("\\s*,\\s*"));

        txtReference.setText(printList());


        //////////////////////////////// ASYNCTASK SIMULATION ///////////////////////////////////////////////////////////
        FetchLRUTask task = new FetchLRUTask();
        task.execute();



        //////////////////////////////// PAGE HEADER INITIALIZATION /////////////////////////////////////////////////////
        header="    \t\tPAGES:\n Ref \t";
        for (int i=0;i<pageSize;i++)
        {
         header=header+"[ "+(i+1)+" ]   ";
        }

        txtContent.setText(header);


        ////////////////////////////////////// FLOATING ACTION BUTTONS //////////////////////////////////////////////////

        fabReplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Simulation.this,Simulation.class);
                intent.putExtra("RESOURCE",res);
                intent.putExtra("PAGES",pageSize);
                startActivity(intent);

            }
        });

        ///////////////////////////////////// END OF FLOATING ACTION BUTTONS ////////////////////////////////////////////

    }

        ///////////////////////////////////////// PRINTERS ///////////////////////////////////////////////////////

    public void showToast(String msg) {
        Toast.makeText(Simulation.this, msg, Toast.LENGTH_LONG).show();
    }

    public String printList()
    {
        String str="";
        for (int i=0;i<resourcesList.size();i++)
        {
            if(i==0)
            {
                str=resourcesList.get(i);
            }
            else {
                str = str +" "+ resourcesList.get(i);
            }
        }
        return str;
    }

    public String printLRUlist()
    {
        String line="";
        for (int i=0; i<LRUList.size();i++)
        {
            if(i==0)
            {
                line="  "+LRUList.get(i).getContains();
            }
            else{
                line= line+"       "+LRUList.get(i).getContains();
            }

        }
        return line;
    }


    ///////////////////////////////////// END OF PRINTERS ///////////////////////////////////////////////////////


    ///////////////////////////////////////// ASYNTASCTASK ///////////////////////////////////////////////////////////

    public class FetchLRUTask extends AsyncTask<Void,Integer, Void> {

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {
            for(int i=0;i<resourcesList.size();i++){
                try {
                    Thread.sleep(1000);
                    publishProgress(i); // Invokes onProgressUpdate()
                } catch (InterruptedException e) {
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int a=values[0],// kung unsa na nga index sa referenceList
                    least=0, // current least sa LRUlist
                    seen=0;

            ArrayList<Integer> equals= new ArrayList<>();

            /// 1. checks each tick tack of time
            String currentResource=resourcesList.get(a);

            txtHitRate=(TextView)findViewById(R.id.txtHitRate);
            txtPageFault=(TextView)findViewById(R.id.txtPageFaults);
            txtReference=(TextView)findViewById(R.id.txtReferencelist);
            txtContent=(TextView) findViewById(R.id.txtContent);
            txtTime=(TextView) findViewById(R.id.txtTime);

            txtTime.setText((a+1)+"");

            /// 2. checks naa naba daan sa LRUList ang currentResource

            String Ccontains;
            int CFIFO, Cused, sfifo;
            for(int b=0;b<LRUList.size();b++)
            {
                if(LRUList.get(b).getContains().equals(currentResource))
                {

                    Ccontains=LRUList.get(b).getContains();
                    CFIFO=LRUList.get(b).getFIFO();
                    Cused=LRUList.get(b).getUsed();

                    seen++;
                    // if naa na increase iyang "used"
                    LRUList.get(b).setUsed(Cused+1);
                    hitrate++;

                    // change ilang standing sa FIFO
                    for(int q=0;q<LRUList.size();q++)
                    {
                            sfifo=LRUList.get(q).getFIFO();
                            LRUList.get(q).setFIFO(sfifo-1);
                    }
                    LRUList.get(b).setFIFO(LRUList.size()-1);

                }
            }

            /// 3. Check if full naba ang LRUlist (equal na ang pagesize ug LRUlist size)

            //if naa pay available this ->
            if(LRUList.size()<pageSize)
            {
                     // and not yet in the list then add to the list
                if(seen==0)
                     {
                         /// Add to the LRUlist
                            LRUList.add(new LRU(currentResource,a,1));
                            pageFault++;
                     }


            }

            /// 4. If puno na ang LRUlist look for victim
            else
            if(seen==0)
            {
            /// 5. check unsay least recently use sa LRUlist
                for(int i=0; i<LRUList.size();i++)
                {
                    if(i==0)
                    {
                        least=0;
                    }
                    else
                    {
                        // get the least
                        if(LRUList.get(i).getUsed()<LRUList.get(least).getUsed())
                        {
                            least=i;
                        }
                        else {
                            ///  but if naay two ka least
                            if (LRUList.get(i).getUsed() == LRUList.get(least).getUsed()) {
                                /// 6. check of same paba ra ang naa sa least ug naa sa equal
                                if (equals.size() == 0) {
                                    equals.add(i);
                                } else {
                                    // if (equal pa ba ang naa sa least ug equal)
                                    if (LRUList.get(least).getUsed() == LRUList.get(equals.get(0)).getUsed()) {
                                        // if equal ra then add.
                                        equals.add(i);
                                    } else {
                                        // if not equal then clear the equal list and add another
                                        equals.clear();
                                        equals.add(i);
                                    }
                                }

                            }
                        }
                    }
                }

                /// 7. move their FIFO up
                for(int q=0;q<LRUList.size();q++)
                {
                    LRUList.get(q).setFIFO(LRUList.get(q).getFIFO()-1);
                }

                /// 8. Check if naa bay sulod ang EqualList
                if(equals.size()==0)
                {
                    // if none then the VICTIM is the LRUlist item in the "least" index


                    LRUList.set(least,new LRU(currentResource,LRUList.size()-1,1));
                    pageFault++;

                    for(int q=0;q<LRUList.size();q++)
                    {
                        LRUList.get(q).setUsed(1);
                    }

                }
                else
                {

                    // if naa then find the first who came sa list para ma ilisan
                    for(int i=0; i<LRUList.size();i++)
                    {
                        if (LRUList.get(i).getFIFO()==-1)
                        {
                            LRUList.set(i,new LRU(currentResource,LRUList.size()-1,1));
                            pageFault++;
                            for(int q=0;q<LRUList.size();q++)
                            {
                                LRUList.get(q).setUsed(1);
                            }
                        }
                    }
                }


            }



            if(a==0)
            {
                content= header+"\n  "+ currentResource+"\t\t"+printLRUlist();
                //content = content +"\n  "+ resourcesList.get(a)+"\t\t   " + resourcesList.get(a).toString();
            }
            else
            {
                content = content +"\n  "+currentResource+"\t\t"+printLRUlist();


            }

            txtPageFault.setText(pageFault+"");
            txtHitRate.setText(hitrate+"");
            txtContent.setText(content);

        }







    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////



}
