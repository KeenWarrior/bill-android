package com.codingblocks.noida.billsplit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingblocks.noida.billsplit.R;
import com.codingblocks.noida.billsplit.model.Owe;
import com.codingblocks.noida.billsplit.util.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OweRecyclerViewAdapter extends RecyclerView.Adapter<OweRecyclerViewAdapter.ViewHolder> {

    private List<Owe> mValues;


    public OweRecyclerViewAdapter(List<Owe> items, String tour) {
        mValues = items;

        Services.getInstance().getTourService().getBalances(tour).enqueue(new Callback<List<Owe>>() {
            @Override
            public void onResponse(Call<List<Owe>> call, Response<List<Owe>> response) {
                if (response.isSuccessful()){
                    mValues = response.body();
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Owe>> call, Throwable t) {

            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_owe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mPayerView.setText("Payer : " + mValues.get(position).payer);
        holder.mReceiverView.setText("Amount : " + mValues.get(position).receiver);
        holder.mAmountView.setText("Receiver : " +mValues.get(position).amount);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPayerView;
        public final TextView mReceiverView;
        public final TextView mAmountView;
        public Owe mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPayerView = (TextView) view.findViewById(R.id.text_payer);
            mReceiverView = (TextView) view.findViewById(R.id.text_receiver);
            mAmountView = (TextView) view.findViewById(R.id.text_amount);
        }
    }
}
