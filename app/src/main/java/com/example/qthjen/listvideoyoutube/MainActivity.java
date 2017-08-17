package com.example.qthjen.listvideoyoutube;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String API_KEY = "AIzaSyAPEmmIpFUqOtq3_N8He1EW-TFnako074c";
    String ID_PLAYLIST = "PLyoQp87sZD25PS0wAOI5zoTSnKfxzobgV";
    String urlJson = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=" + ID_PLAYLIST + "&key=" + API_KEY + "&maxResults=50";

    ListView lv;
    ArrayList<Video> arrayVideo;
    VideoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = (ListView) findViewById(R.id.lv);
        arrayVideo = new ArrayList<Video>();
        adapter = new VideoAdapter(MainActivity.this, R.layout.row_listvideo, arrayVideo);

        lv.setAdapter(adapter);

        GetJson(urlJson);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(MainActivity.this, PlayVideo.class);
                intent.putExtra("idVideoYt", arrayVideo.get(i).getId());
                startActivity(intent);
            }
        });

    }

    public void GetJson(String url) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonItems = response.getJSONArray("items");

                    String title = "";
                    String url = "";
                    String id = "";

                    for ( int i = 0; i < jsonItems.length(); i++) {

                        JSONObject item = jsonItems.getJSONObject(i);

                        JSONObject jsonSippet = item.getJSONObject("snippet");

                        title = jsonSippet.getString("title");
                        JSONObject jsonThumbnail = jsonSippet.getJSONObject("thumbnails");

                        JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");

                        url = jsonMedium.getString("url");

                        JSONObject jsonResultId = jsonSippet.getJSONObject("resourceId");
                        id = jsonResultId.getString("videoId");

                        arrayVideo.add(new Video(title, url, id));
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(jsonObjectRequest);

    }

}