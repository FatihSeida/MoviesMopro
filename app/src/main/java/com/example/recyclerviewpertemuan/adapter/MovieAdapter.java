package com.example.recyclerviewpertemuan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recyclerviewpertemuan.R;
import com.example.recyclerviewpertemuan.model.MovieModel;
import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    List<MovieModel> listItem;
    TextView tvName,tvRating,tvJadwal;
    ImageView ivPoster;
    View view;

    private Context ctx;
    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onClick(int position);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mOnItemClickListener = mItemClickListener;
    }
    public MovieAdapter(Context ctx) {
        this.ctx = ctx;
        listItem = new ArrayList<>();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(View v) {
            super(v);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_movie, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        MovieModel item = listItem.get(position);
        tvName = holder.itemView.findViewById(R.id.txt_name_movie);
        tvJadwal = holder.itemView.findViewById(R.id.txt_jadwal);
        tvRating = holder.itemView.findViewById(R.id.txt_rating);
        ivPoster = holder.itemView.findViewById(R.id.img_movie);
        tvName.setText(item.getTitle());
        tvRating.setText(String.valueOf(item.getVoteAverage()));
        tvJadwal.setText(item.getReleaseDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onClick(position);
            }
        });
        Glide.with(ctx).load("https://image.tmdb.org/t/p/w200" +
                item.getPosterPath()).into(ivPoster);
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public void add(MovieModel item) {
        listItem.add(item);
        notifyItemInserted(listItem.size() + 1);
    }

    public void addAll(List<MovieModel> listItem) {
        for (MovieModel item : listItem) {
            add(item);
        }
    }

    public void removeAll() {
        listItem.clear();
        notifyDataSetChanged();
    }
    public void remove(int pos) {
        listItem.remove(pos);
        notifyDataSetChanged();
    }
    public void swap(List<MovieModel> datas) {
        if (datas == null || datas.size() == 0) listItem.clear();
        if (listItem != null && listItem.size() > 0)
            listItem.clear();
        listItem.addAll(datas);
        notifyDataSetChanged();
    }
    public MovieModel getItem(int pos) {
        return listItem.get(pos);
    }
    public void setFilter(List<MovieModel> list) {
        listItem = new ArrayList<>();
        listItem.addAll(list);
        notifyDataSetChanged();
    }

    public List<MovieModel> getListItem() {
        return listItem;
    }

}
