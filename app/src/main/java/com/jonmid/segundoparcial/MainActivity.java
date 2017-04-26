package com.jonmid.segundoparcial;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jonmid.segundoparcial.Adapters.UserAdapter;
import com.jonmid.segundoparcial.Models.User;
import com.jonmid.segundoparcial.Parser.Json;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar loader;
    RecyclerView myRecycler;
    List<User> myUser;
    UserAdapter myAdapter;
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boton = (Button) findViewById(R.id.boton);
        loader = (ProgressBar) findViewById(R.id.loader);
        myRecycler = (RecyclerView) findViewById(R.id.myRecycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myRecycler.setLayoutManager(linearLayoutManager);
    }

    // Metodo para validar la conexion a internet
    public Boolean isOnLine(){
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connec.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

    public void cargarDatos(){
        // Crear un objeto de tipo "PostAdapter" y retorna el item de mi layout (item.xml)
        myAdapter = new UserAdapter(getApplicationContext(), myUser);
        // inyectar el item en mi RecyclerView
        myRecycler.setAdapter(myAdapter);
    }

    // Medodo para manejar el evento del item del menu
    public void onClickButton(View view){
        if (isOnLine()){
            MyTask task = new MyTask();
            task.execute("https://jsonplaceholder.typicode.com/users");
        }else {
            Toast.makeText(this, "Sin conexión", Toast.LENGTH_SHORT).show();
        }
    }

    // Tarea asincrona para obtener los datos desde internet
    private class MyTask extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            loader.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(String... params) {

            String content = null;
            try {
                content = HttpManager.getData(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onProgressUpdate(String... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);
            try {
                myUser = Json.parserJsonUser(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cargarDatos();
            loader.setVisibility(View.GONE);
        }
    }

    // Metodo para para inicializar el menu en el Toolbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //onClickButton();
        return super.onOptionsItemSelected(item);
    }
}
