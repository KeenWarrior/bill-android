package com.codingblocks.noida.billsplit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codingblocks.noida.billsplit.R;
import com.codingblocks.noida.billsplit.model.Tour;
import com.codingblocks.noida.billsplit.model.Transaction;
import com.codingblocks.noida.billsplit.util.Preferences;
import com.codingblocks.noida.billsplit.util.Services;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TourDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.edit_name)
    EditText nameEdit;
    @BindView(R.id.button_submit)
    Button submitButton;
    @BindView(R.id.button_users)
    Button usersButton;
    @BindView(R.id.button_transactions)
    Button transactionsButton;
    @BindView(R.id.button_balances)
    Button balancesButton;

    private Boolean update;

    private String tourId;
    private String tourName;

    private Tour tour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        tourId = intent.getStringExtra("tour_id");
        tourName = intent.getStringExtra("tour_name");

        submitButton.setText("Update");
        fillDetail(tourId, tourName);

        submitButton.setOnClickListener(this);
        usersButton.setOnClickListener(this);
        balancesButton.setOnClickListener(this);
        transactionsButton.setOnClickListener(this);
    }

    private void fillDetail(String tourId, String tourName) {

        nameEdit.setText(tourName);

        Services.getInstance().getTourService().getTour(tourId).enqueue(new Callback<Tour>() {
            @Override
            public void onResponse(Call<Tour> call, Response<Tour> response) {
                if (response.isSuccessful()) {
                    tour = response.body();
                }
            }

            @Override
            public void onFailure(Call<Tour> call, Throwable t) {

            }
        });
    }

    public void submit() {

        tour.name = nameEdit.getText().toString();
        Services.getInstance().getTourService().updateTour(tourId, tour).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Tour Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Internet Error", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onClick(View v) {


        Bundle bundle = new Bundle();
        bundle.putString("tour_id", tourId);
        bundle.putString("tour_name", tourName);

        switch (v.getId()) {
            case R.id.button_submit:
                submit();
                break;

            case R.id.button_users:
                Intent users_intent = new Intent(getApplicationContext(), UsersActivity.class);
                users_intent.putExtras(bundle);
                startActivityForResult(users_intent, 12);
                break;

            case R.id.button_transactions:
                Intent transactions_intent = new Intent(getApplicationContext(), TransactionsActivity.class);
                transactions_intent.putExtras(bundle);
                startActivityForResult(transactions_intent, 12);
                break;

            case R.id.button_balances:
                Intent balances_intent = new Intent(getApplicationContext(), OwesActivity.class);
                balances_intent.putExtras(bundle);
                startActivityForResult(balances_intent, 12);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}


//else {
//        Tour tour = new Tour();
//        tour.id = UUID.randomUUID().toString();
//        tour.name = nameEdit.getText().toString();
//        tour.users.add(Preferences.of(getApplicationContext()).username().get());
//
//        Services.getInstance().getTourService().createTour(tour).enqueue(new Callback<Void>() {
//@Override
//public void onResponse(Call<Void> call, Response<Void> response) {
//        if(response.isSuccessful()){
//        Toast.makeText(getApplicationContext(), "Tour Created", Toast.LENGTH_SHORT).show();
//        } else {
//        Toast.makeText(getApplicationContext(), "Try Again", Toast.LENGTH_SHORT).show();
//        }
//        }
//
//@Override
//public void onFailure(Call<Void> call, Throwable t) {
//        Toast.makeText(getApplicationContext(), "Internet Error", Toast.LENGTH_SHORT).show();
//        }
//        });
//        }
