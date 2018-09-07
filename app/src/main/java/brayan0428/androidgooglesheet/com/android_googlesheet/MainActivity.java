package brayan0428.androidgooglesheet.com.android_googlesheet;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import brayan0428.androidgooglesheet.com.android_googlesheet.Adapters.UsersAdapter;
import brayan0428.androidgooglesheet.com.android_googlesheet.POJOS.Users;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton addUser;
    RecyclerView rvUsers;
    ArrayList<Users> usersList;
    UsersAdapter usersAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvUsers = findViewById(R.id.rvUsers);
        addUser = findViewById(R.id.addUser);

        //Initialize the RecyclerView
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvUsers.setLayoutManager(linearLayoutManager);
        usersAdapter = new UsersAdapter(getApplicationContext(),usersList);
        rvUsers.setAdapter(usersAdapter);

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddUserActivity.class);
                startActivity(intent);
            }
        });
    }
}
