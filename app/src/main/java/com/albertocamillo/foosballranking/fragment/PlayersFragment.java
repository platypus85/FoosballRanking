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
import com.albertocamillo.foosballranking.activity.NewPlayerActivity;
import com.albertocamillo.foosballranking.adapter.PlayerAdapter;
import com.albertocamillo.foosballranking.db.DatabaseHandler;
import com.albertocamillo.foosballranking.model.Player;

import java.util.ArrayList;

public class PlayersFragment extends Fragment{

    private FloatingActionButton mNewPlayerFAB;
    private FloatingActionButton mRefreshPlayersFAB;
    private ListView mListViewPlayers;
    private PlayerAdapter mPlayersAdapter;

    public PlayersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_players, container, false);

        mListViewPlayers = (ListView) view.findViewById(R.id.list_players);
        mListViewPlayers.setEmptyView(view.findViewById(R.id.emptyElement));
        mNewPlayerFAB = (FloatingActionButton) view.findViewById(R.id.fab_players);
        mNewPlayerFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NewPlayerActivity.class);
                startActivity(i);
            }
        });
        mRefreshPlayersFAB = (FloatingActionButton) view.findViewById(R.id.fab_refresh_players);
        mRefreshPlayersFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadPlayersList();
            }
        });


        return view;
    }

    private void loadPlayersList(){
        DatabaseHandler db = new DatabaseHandler(getActivity());
        ArrayList<Player> arrayOfPlayers = (ArrayList<Player>) db.getAllPlayers();
        mPlayersAdapter = new PlayerAdapter(getActivity(), arrayOfPlayers);
        mListViewPlayers.setAdapter(mPlayersAdapter);
        mPlayersAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPlayersList();
    }
}