package com.codingblocks.noida.billsplit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codingblocks.noida.billsplit.R;
import com.codingblocks.noida.billsplit.model.Transaction;
import com.codingblocks.noida.billsplit.model.Transaction;
import com.codingblocks.noida.billsplit.util.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TransactionRecyclerViewAdapter extends RecyclerView.Adapter<TransactionRecyclerViewAdapter.ViewHolder> {

    public List<Transaction> mValues;

    private String tourId;

    public TransactionRecyclerViewAdapter(List<Transaction> items, String tourId) {
        this.mValues = items;
        this.tourId = tourId;
        update();

    }

    public void update(){
        Services.getInstance().getTourService().getTransactions(tourId).enqueue(new Callback<List<Transaction>>() {
            @Override
            public void onResponse(Call<List<Transaction>> call, Response<List<Transaction>> response) {
                if (response.isSuccessful()){
                    mValues = response.body();
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Transaction>> call, Throwable t) {

            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_transaction, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mPayerView.setText(mValues.get(position).payer);
        //holder.mAmountView.setText(mValues.get(position).amount);
        holder.mNoteView.setText(mValues.get(position).note);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mPayerView;
        public final TextView mNoteView;
        public final TextView mAmountView;
        public Transaction mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPayerView = (TextView) view.findViewById(R.id.text_payer);
            mNoteView = (TextView) view.findViewById(R.id.text_note);
            mAmountView = (TextView) view.findViewById(R.id.text_amount);
        }
 }
}
