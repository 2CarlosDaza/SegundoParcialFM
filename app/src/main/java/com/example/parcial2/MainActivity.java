package com.example.parcial2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.parcial2.Repository.TrackRepository;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private androidx.recyclerview.widget.RecyclerView RecyclerView;



    private RequestQueue queue;
    private ArrayList<Track> tracks;

    private RecyclerView lista;
    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista= findViewById(R.id.listaSong);
        lista.setLayoutManager(new LinearLayoutManager(this));
        tracks =new ArrayList<Track>();



        if(getIntent() != null){
            Track track = new Track();
            track.name = getIntent().getStringExtra("name");
            track.artist = getIntent().getStringExtra("artist");
            track.duration = getIntent().getStringExtra("duration");
            //tracks.add(track);
            TrackRepository.add(track);
        }




        queue = Volley.newRequestQueue(this);
        GetVolley();
        concatenar();
        //ShowToast();
        //setAdapter();
    }
    private void concatenar(){
        ArrayList<Track> tracksAux=TrackRepository.getOwnTrack();
        for(Track e: tracksAux){
            tracks.add(e);
        }
    }

    private void setAdapter(){

    }

    private void ShowToast(){
        for(int i = 0; i< tracks.size(); i++){
            Toast.makeText(MainActivity.this, tracks.get(i).name,Toast.LENGTH_LONG).show();
        }
    }


    private void GetVolley(){
        String URL = "https://ws.audioscrobbler.com/2.0/?method=chart.gettoptracks&api_key=b284db959637031077380e7e2c6f2775&format=json";
        JsonObjectRequest request
                = new JsonObjectRequest(Request.Method.GET,URL, (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("tracks");
                            JSONArray jsonArray = jsonObject.getJSONArray("track");
                            Track track;
                            for(int i=0;i<jsonArray.length(); i++){
                                track =new Track();
                                JSONObject JSONItem = jsonArray.getJSONObject(i);
                                track.name =JSONItem.getString("name");
                                track.duration =JSONItem.getString("duration");
                                JSONObject jsonArtist=JSONItem.getJSONObject("artist");
                                track.artist =jsonArtist.getString("name");

                                //track.name=name;
                                //track.duration=duration;
                                //track.artist=artist;

                                tracks.add(track);
                              //  Toast.makeText(MainActivity.this,name,Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter = new Adapter(tracks);
                        lista.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //code in error
            }
        });
        this.queue.add(request);
    }

    public void openActivityAdd(View view) {
        Intent intent= new Intent(this,addActivity.class);
        startActivity(intent);
    }

}
