package com.codingblocks.noida.billsplit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.codingblocks.noida.billsplit.model.Tour;
import com.codingblocks.noida.billsplit.model.Transaction;
import com.codingblocks.noida.billsplit.util.Services;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTransactionActivity extends AppCompatActivity {


    @BindView(R.id.edit_note)
    EditText noteEdit;

    @BindView(R.id.spinner_payer)
    Spinner payerSpinner;

    @BindView(R.id.edit_amount)
    EditText amountEdit;

    @BindView(R.id.button_submit)
    Button submitButton;

    private String tourId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);

        ButterKnife.bind(this);

        this.tourId = getIntent().getStringExtra("tour_id");


        Services.getInstance().getTourService().getTour(tourId).enqueue(new Callback<Tour>() {
            @Override
            public void onResponse(Call<Tour> call, Response<Tour> response) {
                if(response.isSuccessful()){
                    final List<String> items = new ArrayList<>();
                    for (String item : response.body().users){
                        items.add(item);
                    }

                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(AddTransactionActivity.this,
                            android.R.layout.simple_spinner_item, items);
                    dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    payerSpinner.setAdapter(dataAdapter);
                }
            }

            @Override
            public void onFailure(Call<Tour> call, Throwable t) {

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String payer = payerSpinner.getSelectedItem().toString();
                String note = noteEdit.getText().toString();
                Integer amount = Integer.parseInt(amountEdit.getText().toString());

                Transaction transaction = new Transaction(payer, amount, note);

                Services.getInstance().getTourService().createTransaction(tourId, transaction).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()){
                            finish();
                        } else {
                            Toast.makeText(AddTransactionActivity.this, response.message(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Toast.makeText(AddTransactionActivity.this, "Bhadak", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });

    }
}
