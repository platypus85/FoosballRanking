package com.albertocamillo.foosballranking.fragment;

/**
 * Created by Alberto Camillo on 13/02/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.albertocamillo.foosballranking.R;
import com.albertocamillo.foosballranking.activity.NewMatchActivity;
import com.albertocamillo.foosballranking.adapter.MatchAdapter;
import com.albertocamillo.foosballranking.db.DatabaseHandler;
import com.albertocamillo.foosballranking.model.Match;

import java.util.ArrayList;

public class MatchesFragment extends Fragment{
    private FloatingActionButton mNewMatchFAB;
    private ListView mListViewMatches;
    private MatchAdapter mMatchesAdapter;

    public MatchesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_matches, container, false);
        mListViewMatches = (ListView) view.findViewById(R.id.list_matches);
        mListViewMatches.setEmptyView(view.findViewById(R.id.emptyElementMatches));

        mNewMatchFAB = (FloatingActionButton) view.findViewById(R.id.fab_matches);
        mNewMatchFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NewMatchActivity.class);
                startActivity(i);
            }
        });
        return view;
    }
    private void loadMatchesList(){
        DatabaseHandler db = new DatabaseHandler(getActivity());
        ArrayList<Match> arrayOfMatches = (ArrayList<Match>) db.getAllmatches();
        mMatchesAdapter = new MatchAdapter(getActivity(), arrayOfMatches);
        mListViewMatches.setAdapter(mMatchesAdapter);
        mMatchesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadMatchesList();
    }
}