package com.example.consumirjson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private static final String URL = "https://jsonplaceholder.typicode.com/posts/";
    private List<HashMap<String, String>> listaLivros;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaLivros = new ArrayList<>();
        lv = findViewById(R.id.listView);

        ObtencaoDeLivros odc = new ObtencaoDeLivros();
        odc.execute();
    }
    private class ObtencaoDeLivros extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(getApplicationContext(), "Download JSON" ,Toast.LENGTH_LONG ).show();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            Auxiliar aux = new Auxiliar();
            String jsonStr = aux.conectar(URL);

            if(jsonStr != null){
                try {
                    //JSONObject jsonObject = new JSONObject(jsonStr);
                    //JSONArray jsonArray = jsonObject.getJSONArray("contacts");
                    JSONArray jsonArray = new JSONArray(jsonStr.toString());
                    for(int i =0 ; i<jsonArray.length(); i++){
                        JSONObject c = jsonArray.getJSONObject(i);
                        String userId = c.getString("userId");
                        String id = c.getString("id");
                        String title = c.getString("title");
                        String body = c.getString("body");

                        HashMap<String, String> livro = new HashMap<>();
                        livro.put("id", id);
                        livro.put("title", title);
                        livro.put("body", body);

                        listaLivros.add(livro);
                    }//for
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }//if
            return null;
        }//method

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ListAdapter adapter = new SimpleAdapter(MainActivity.this,
                    listaLivros,
                    R.layout.item_lista,
                    new String[]{"id","title","body"},
                    new int[]{R.id.textViewNome, R.id.textViewTitle, R.id.textViewBody});

            lv.setAdapter(adapter);

        }//method
    }//inner class
}
