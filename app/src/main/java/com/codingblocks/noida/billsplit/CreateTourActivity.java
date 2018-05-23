package com.codingblocks.noida.billsplit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.codingblocks.noida.billsplit.model.Tour;
import com.codingblocks.noida.billsplit.util.Preferences;
import com.codingblocks.noida.billsplit.util.Services;

import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CreateTourActivity extends AppCompatActivity {

    @BindView(R.id.edit_name)
    EditText nameEdit;

    @BindView(R.id.button_submit)
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);


        ButterKnife.bind(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = UUID.randomUUID().toString();
                String name = nameEdit.getText().toString();
                Tour tour = new Tour(id, name);
                tour.users.add(Preferences.of(getApplicationContext()).username().get());

                Services.getInstance().getTourService().createTour(tour).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(CreateTourActivity.this, "Tour Created", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(CreateTourActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(CreateTourActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
