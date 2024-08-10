package com.example.jforce;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private ExpanseAdapter expanseAdapter;
    private Exapi api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api=retrofit.create(Exapi.class);
        floatingActionButton=findViewById(R.id.add_task);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent save=new Intent(MainActivity.this,addexpnese.class);
                startActivity(save);
                finish();
            }
        });

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getexpanse();




    }

    private void getexpanse() {
        Call<List<Expanse>> call=api.getExpenses();
        call.enqueue(new Callback<List<Expanse>>() {
            @Override
            public void onResponse(Call<List<Expanse>> call, Response<List<Expanse>> response) {
                if(response.isSuccessful() ){
                    List<Expanse> expanses=response.body();
                    expanseAdapter=new ExpanseAdapter(expanses);
                    recyclerView.setAdapter(expanseAdapter);
                }
                else {
                    Toast.makeText(MainActivity.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();

                                  }
            }

            @Override
            public void onFailure(Call<List<Expanse>> call, Throwable t) {

            }
        });

    }


}