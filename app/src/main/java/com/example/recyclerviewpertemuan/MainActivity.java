package com.example.recyclerviewpertemuan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.recyclerviewpertemuan.adapter.MovieAdapter;
import com.example.recyclerviewpertemuan.model.MovieModel;
import com.example.recyclerviewpertemuan.model.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import static com.example.recyclerviewpertemuan.Constant.GET_NOW_PLAYING;

public class MainActivity extends AppCompatActivity {
    MovieAdapter adapter;
    RecyclerView rv;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        initRecyclerView();
    }
    public void initRecyclerView(){
        adapter = new MovieAdapter(this);
        loadItem();
        rv = findViewById(R.id.rv_movie);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setItemAnimator(new DefaultItemAnimator());
        rv.setNestedScrollingEnabled(false);
        rv.hasFixedSize();
        rv.setAdapter(adapter);
        adapter.setOnItemClickListener(new MovieAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent i = new Intent(MainActivity.this,
                        DetailActivity.class);
                i.putExtra("name",adapter.getItem(position).getTitle());
                i.putExtra("rating",adapter.getItem(position).getVoteAverage());
                i.putExtra("jadwal",adapter.getItem(position).getReleaseDate());
                i.putExtra("deskripsi",adapter.getItem(position).getOverview());
                startActivity(i);
            }
        });
    }
    public void loadItem(){
    //show progress dialog
        progressDialog.setMessage("Please Wait..");
        progressDialog.show();
        AndroidNetworking.get(GET_NOW_PLAYING).build().getAsObject(MovieResponse.class, new ParsedRequestListener() {
            @Override
            public void onResponse(Object response) {
                if(response instanceof MovieResponse){
                    //disable progress dialog
                    progressDialog.dismiss();
                    //null data check
                    if (((MovieResponse) response).getResults() != null &&
                            ((MovieResponse) response).getResults().size() > 0){
                                adapter.addAll(((MovieResponse) response).getResults());
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Failed to fetch data",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
