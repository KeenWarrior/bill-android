package com.codingblocks.noida.billsplit.service;

import com.codingblocks.noida.billsplit.model.Owe;
import com.codingblocks.noida.billsplit.model.Tour;
import com.codingblocks.noida.billsplit.model.Transaction;
import com.codingblocks.noida.billsplit.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by hp on 5/15/2018.
 */
public interface TourService {

    @GET("/tours/{id}/")
    public Call<Tour> getTour(@Path("id") String id);

    @GET("/tours/{id}/balance/")
    public Call<List<Owe>> getBalances(@Path("id") String tourId);

    @POST("/tours/")
    public Call<Void> createTour(@Body Tour tour);

    @POST("/tours/{id}/transactions/")
    public Call<Void> createTransaction(@Path("id") String tourId, @Body Transaction transaction);

    @PUT("/tours/{id}/")
    public Call<Void> updateTour(@Path("id") String id, @Body Tour tour);

    @DELETE("/tours/{id}/")
    public Call<Void> removeTour(@Path("id") String id);

    @GET("/tours/{id}/users/")
    Call<List<User>> getUsersFromTour(@Path("id") String id);

    @GET("/tours/{id}/transactions/")
    Call<List<Transaction>> getTransactions(@Path("id") String id);

    @POST("/tours/{id}/users/")
    Call<Void> addUserToTour(@Path("id") String id, @Body String user);
}
