package com.example.admin.machinery_rview;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    AsyncHttpClient client;
    JSONObject jobj;
    JSONArray jarray;
    RequestParams params;
    RecyclerView recyclerView;
    VerticalAdapter adapter;
    LayoutInflater infater;
    LinearLayoutManager llmanager;

    ArrayList<String>idarray;
    ArrayList<String>namearray;
    ArrayList<String>typearray;
    ArrayList<String>desarray;
    ArrayList<String>useparray;
    ArrayList<String>pricearray;
    ArrayList<String>imagearray;

    String url="http://sicsglobal.co.in/agroapp/Json/Machinerys.aspx";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        client=new AsyncHttpClient();
        params=new RequestParams();

        recyclerView=findViewById(R.id.recyclerviewxml);

        idarray=new ArrayList<>();
        namearray=new ArrayList<>();
        typearray=new ArrayList<>();
        desarray=new ArrayList<>();
        useparray=new ArrayList<>();
        pricearray=new ArrayList<>();
        imagearray=new ArrayList<>();

        Log.e("value in","value out");

        client.get(url,params,new AsyncHttpResponseHandler(){

            @Override
            public void onSuccess(String content) {
                super.onSuccess(content);
                try {
                    Log.e("inn","out");
                    jobj=new JSONObject(content);
                    jarray=jobj.getJSONArray("Machinerys");
                    for (int i=0;i<jarray.length();i++){
                        JSONObject obj1=jarray.getJSONObject(i);
                        String machineid=obj1.getString("MachineId");
                        idarray.add("MachineId :"+machineid);
                        String name=obj1.getString("Name");
                        namearray.add("Name :"+name);
                        String type=obj1.getString("Type");
                        typearray.add("Type :"+type);
                        String des=obj1.getString("Description");
                        desarray.add("Description :"+des);
                        String usepro=obj1.getString("UsageProcedure");
                        useparray.add("UsageProcedure :"+usepro);
                        String price=obj1.getString("Price");
                        pricearray.add("Price :"+price);
                        String image=obj1.getString("Image");
                        imagearray.add(image);


//                        jobj=jarray.getJSONObject(i);
//                        idarray.add(jobj.getString("MachineId"));
//                        namearray.add(jobj.getString("Name"));
//                        typearray.add(jobj.getString("Type"));
//                        desarray.add(jobj.getString("Description"));
//                        useparray.add(jobj.getString("UsageProcedure"));
//                        pricearray.add(jobj.getString("Price"));
//                        imagearray.add(jobj.getString("Image"));

                        adapter=new VerticalAdapter(namearray);
                        llmanager=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);

                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(llmanager);


                    }
                } catch (Exception e){

                }
            }
        });



    }
    public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder>{
        private List<String>vlist;

        VerticalAdapter(List<String>vlist){
            this.vlist=namearray;
        }

        @NonNull
        @Override
        public VerticalAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

            View itemview=infater.from(viewGroup.getContext()).inflate(R.layout.machines,viewGroup,false);
            return new MyViewHolder(itemview);
        }

        @Override
        public void onBindViewHolder(@NonNull VerticalAdapter.MyViewHolder myViewHolder, int i) {
            myViewHolder.idr.setText(idarray.get(i));
            myViewHolder.namer.setText(namearray.get(i));
            myViewHolder.typer.setText(typearray.get(i));
            myViewHolder.desr.setText(desarray.get(i));
            myViewHolder.usepr.setText(useparray.get(i));
            myViewHolder.pricer.setText(pricearray.get(i));

            Picasso.with(MainActivity.this).load("http://sicsglobal.co.in/agroapp/Admin/VideoFiles/"+imagearray.get(i)).placeholder(R.drawable.load).error(R.drawable.load).into(myViewHolder.imager);

        }

        @Override
        public int getItemCount() {
            return namearray.size();
        }
        class MyViewHolder extends RecyclerView.ViewHolder{

           TextView idr,namer,typer,desr,usepr,pricer;
           ImageView imager;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                idr=itemView.findViewById(R.id.idxml);
                namer=itemView.findViewById(R.id.namexml);
                typer=itemView.findViewById(R.id.typexml);
                desr=itemView.findViewById(R.id.desxml);
                usepr=itemView.findViewById(R.id.usepxml);
                pricer=itemView.findViewById(R.id.textView7);

                imager=itemView.findViewById(R.id.imagexml);

            }
            }
        }






    }

