package brayan0428.androidgooglesheet.com.android_googlesheet;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import brayan0428.androidgooglesheet.com.android_googlesheet.Adapters.UsersAdapter;
import brayan0428.androidgooglesheet.com.android_googlesheet.Configuration.Variables;
import brayan0428.androidgooglesheet.com.android_googlesheet.POJOS.Users;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addUser;
    RecyclerView rvUsers;
    ArrayList<Users> usersList;
    UsersAdapter usersAdapter;
    RequestQueue request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvUsers = findViewById(R.id.rvUsers);
        addUser = findViewById(R.id.addUser);
        request = Volley.newRequestQueue(getApplicationContext());

        //Initialize the RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvUsers.setLayoutManager(linearLayoutManager);
        usersList = new ArrayList<>();
        LoadUsers();
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void LoadUsers(){
        try{
            final ProgressDialog progressDialog = ProgressDialog.show(this,"Consultando Usuarios","Espere un momento...",false,false);
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, Variables.URL_API,null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Users users;
                    JSONArray json = response.optJSONArray("records");
                    try{
                        for (int i=0;i< json.length();i++){
                            JSONObject jsonObject = null;
                            jsonObject = json.getJSONObject(i);
                            users = new Users(
                                jsonObject.optString("Id"), jsonObject.optString("Names"),jsonObject.optString("LastNames"),
                                    jsonObject.optString("Address"),
                                    jsonObject.optString("Email"), jsonObject.optString("Image")
                            );
                            usersList.add(users);
                        }
                        usersAdapter = new UsersAdapter(getApplicationContext(),usersList);
                        rvUsers.setAdapter(usersAdapter);
                    }catch (Exception e){

                    }
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    //Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
                }
            });
            request.add(getRequest);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }
}
