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

public class AddUserActivity extends AppCompatActivity {

    @BindView(R.id.edit_phone)
    EditText phoneEdit;

    @BindView(R.id.button_submit)
    Button submitButton;

    private String tourId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);

        tourId = getIntent().getStringExtra("tour_id");

        ButterKnife.bind(this);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = phoneEdit.getText().toString();

                Services.getInstance().getTourService().addUserToTour(tourId, user).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });

            }
        });

    }
}
