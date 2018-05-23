package com.codingblocks.noida.billsplit.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codingblocks.noida.billsplit.AddTransactionActivity;
import com.codingblocks.noida.billsplit.R;
import com.codingblocks.noida.billsplit.fragment.TransactionsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionsActivity extends AppCompatActivity {

    @BindView(R.id.fab) FloatingActionButton fab;

    TransactionsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);

        ButterKnife.bind(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddTransactionActivity.class);
                intent.putExtras(getIntent().getExtras());
                startActivityForResult(intent, 12);
            }
        });

        fragment = new TransactionsFragment();
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fragment.reset();
    }
}
