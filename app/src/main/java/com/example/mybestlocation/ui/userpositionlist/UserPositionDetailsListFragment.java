package com.example.mybestlocation.ui.userpositionlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybestlocation.DatabaseHelper;
import com.example.mybestlocation.Position;
import com.example.mybestlocation.R;
import com.example.mybestlocation.ui.userpositionlist.adapter.PositionAdapter;

import java.util.ArrayList;
import java.util.List;

public class UserPositionDetailsListFragment extends Fragment {

    private RecyclerView positionsRecyclerView;
    private PositionAdapter positionAdapter;
    private List<Position> positionList = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    public UserPositionDetailsListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_position_details_list, container, false);
        // Initialize RecyclerView
        positionsRecyclerView = rootView.findViewById(R.id.positionsRecyclerView);
        positionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize DatabaseHelper
        databaseHelper = new DatabaseHelper(getContext());

        // Fetch the positions from the database and set the adapter
        fetchPositionsFromDatabase();

        return rootView;
    }

    private void fetchPositionsFromDatabase() {
        positionList.clear();
        // Fetch all positions from the database


        // Set the adapter to the RecyclerView
        positionAdapter = new PositionAdapter(getContext(), databaseHelper.getAllPositions());
        positionsRecyclerView.setAdapter(positionAdapter);
    }
}
