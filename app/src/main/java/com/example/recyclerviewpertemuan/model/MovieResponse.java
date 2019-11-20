package com.example.recyclerviewpertemuan.model;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class MovieResponse implements Serializable{
    @SerializedName("dates")
    private Dates dates;
    @SerializedName("page")
    private int page;
    @SerializedName("total_pages")
    private int totalPages;
    @SerializedName("results")
    private List<MovieModel> results;
    @SerializedName("total_results")
    private int totalResults;
    public Dates getDates(){
        return dates;
    }
    public int getPage(){
        return page;
    }
    public int getTotalPages(){
        return totalPages;
    }
    public List<MovieModel> getResults(){
        return results;
    }
    public int getTotalResults(){
        return totalResults;
    }
}
