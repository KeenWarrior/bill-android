package com.codingblocks.noida.billsplit.service;

import com.codingblocks.noida.billsplit.model.Tour;
import com.codingblocks.noida.billsplit.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by hp on 5/15/2018.
 */
public interface UserService {

    @GET("/users/{id}")
    public Call<User> getUser(@Path("id") String id);

    @POST("/users/")
    public Call<Void> createUser(@Body User user);

    @PUT("/users/{id}")
    public Call<Void> updateUser(@Path("id") String id, @Body User user);

    @DELETE("/users/")
    public Call<Void> removeUser(@Path("id") String id);

    @GET("/users/{id}/tours/")
    public Call<List<Tour>> getToursForUser(@Path("id") String id);
}
