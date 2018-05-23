package com.codingblocks.noida.billsplit.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codingblocks.noida.billsplit.CreateTourActivity;
import com.codingblocks.noida.billsplit.activity.MainActivity;
import com.codingblocks.noida.billsplit.R;
import com.codingblocks.noida.billsplit.activity.TourDetailActivity;
import com.codingblocks.noida.billsplit.adapter.TourRecyclerViewAdapter;
import com.codingblocks.noida.billsplit.model.Tour;
import com.codingblocks.noida.billsplit.util.Preferences;
import com.codingblocks.noida.billsplit.util.Services;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ToursFragment extends Fragment implements Callback<List<Tour>>{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private TourRecyclerViewAdapter adapter;

    public List<Tour> tours = new ArrayList<>();
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    
    public ToursFragment() {

    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static ToursFragment newInstance(int columnCount) {
        ToursFragment fragment = new ToursFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Services.getInstance().getUserService().getToursForUser(Preferences.of(getActivity().getApplicationContext()).username().get()).enqueue(this);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        
        ((MainActivity)getActivity()).setFabClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), CreateTourActivity.class), 12);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            adapter = new TourRecyclerViewAdapter(tours, mListener);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResponse(Call<List<Tour>> call, Response<List<Tour>> response) {
        if (response.isSuccessful()){
            List<Tour> tours = response.body();
            adapter.mValues = tours;
            adapter.notifyDataSetChanged();
        }else {
            Toast.makeText(this.getActivity(), "some error in network", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onFailure(Call<List<Tour>> call, Throwable t) {
        Toast.makeText(this.getActivity(), "fucked up", Toast.LENGTH_SHORT).show();
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Tour item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            Services.getInstance().getUserService().getToursForUser(Preferences.of(getActivity().getApplicationContext()).username().get()).enqueue(this);
    }
}
