package com.codingblocks.noida.billsplit.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codingblocks.noida.billsplit.R;
import com.codingblocks.noida.billsplit.fragment.OwesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OwesActivity extends AppCompatActivity {
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);

        ButterKnife.bind(this);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OwesActivity.this, "blah", Toast.LENGTH_SHORT).show();
            }
        });

        Fragment fragment = new OwesFragment();
        fragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
    }
}
