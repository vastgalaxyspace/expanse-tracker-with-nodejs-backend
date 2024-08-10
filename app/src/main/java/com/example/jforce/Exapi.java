package com.example.jforce;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
public interface Exapi {

    @POST ("/users")
    Call<Void> saveexpense(@Body Expanse expanse);

    @GET("/view")
    Call<List<Expanse>> getExpenses();

}
