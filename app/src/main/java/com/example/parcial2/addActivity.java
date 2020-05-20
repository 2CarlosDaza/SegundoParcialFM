package com.example.parcial2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class addActivity extends AppCompatActivity {

    TextView nameTV;
    TextView artistTV;
    TextView durationTV;
    EditText editText;
    private Button buttonSearch;
    private Button buttonAdd;
    Track track;
    boolean avaible;
    private RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        queue = Volley.newRequestQueue(this);
        setParameters();

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getVolley();
            }
        });
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTrack();
            }
        });


    }
    private void setParameters(){
        track =new Track();
        avaible=true;
        nameTV=findViewById(R.id.nameTV);
        artistTV=findViewById(R.id.artistTV);
        durationTV=findViewById(R.id.durationTV);
        editText=findViewById(R.id.searchEdit);
        buttonSearch=findViewById(R.id.buttonSearch);
        buttonAdd=findViewById(R.id.buttonAdd);
    }

    public void add(View view) {

    }

    private void addTrack(){
        validate();
    }

    private void validate(){
        if(avaible==true){
            backToMain();
        }
    }

    private void backToMain(){
        Intent intent = new Intent(addActivity.this, MainActivity.class);
        intent.putExtra("name", nameTV.getText());
        intent.putExtra("artist", artistTV.getText());
        intent.putExtra("duration","0");//toca devolver 0 porque en esa busqueda no aparece la duración
        startActivity(intent);
    }


    private void getVolley(){
        String searchEdit = editText.getText().toString();
        String URL = "https://ws.audioscrobbler.com/2.0/?method=track.search&track="+searchEdit+"&api_key=b284db959637031077380e7e2c6f2775&format=json";

        JsonObjectRequest request
                = new JsonObjectRequest(Request.Method.GET, URL, (String) null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("results");
                            JSONObject jsonObject2 = jsonObject.getJSONObject("trackmatches");
                            JSONArray jsonArray=jsonObject2.getJSONArray("track");
                            JSONObject JSONItem = jsonArray.optJSONObject(0);
                            track.name =JSONItem.getString("name");
                            track.duration ="0";
                            track.artist =JSONItem.getString("artist");
                            nameTV.setText(track.name);
                            artistTV.setText(track.artist);
                            durationTV.setText(track.duration);
                            avaible=true;

                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        this.queue.add(request);
    }

}
