package com.albertocamillo.foosballranking.fragment;

/**
 * Created by Alberto Camillo on 13/02/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.albertocamillo.foosballranking.R;
import com.albertocamillo.foosballranking.db.DatabaseHandler;
import com.albertocamillo.foosballranking.model.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

public class StatsFragment extends Fragment{

    private Spinner mSpinnerP1;
    private Spinner mSpinnerP2;
    private ArrayList<Player> listOfplayers;
    private TextView mTvStats1;
    private TextView mTvStats2;
    private TextView mTvStatsText;
    private DatabaseHandler db;

    public StatsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_stats, container, false);

        mSpinnerP1 = (Spinner) view.findViewById(R.id.player1_stats_spinner);
        mSpinnerP2 = (Spinner) view.findViewById(R.id.player2_stats_spinner);
        mTvStats1  = (TextView) view.findViewById(R.id.stats1_text);
        mTvStats2  = (TextView) view.findViewById(R.id.stats2_text);
        mTvStatsText  = (TextView) view.findViewById(R.id.player_stats_text);

        db = new DatabaseHandler(getActivity());

        mSpinnerP1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                loadSpinners();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        mSpinnerP2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                loadSpinners();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return view;
    }

    private void loadSpinners(){
        int player1Id  = ((Player)mSpinnerP1.getSelectedItem()).getId();
        int player2Id  = ((Player)mSpinnerP2.getSelectedItem()).getId();

        loadPlayerStats(player1Id);
        loadPlayerVsPlayerStats(player1Id,player2Id);
    }

    private void loadPlayersList(){
        listOfplayers = (ArrayList<Player>) db.getAllPlayers();

        if(null==listOfplayers || listOfplayers.size()<1){
            mSpinnerP1.setVisibility(View.GONE);
            mSpinnerP2.setVisibility(View.GONE);
            mTvStats1.setText(getString(R.string.no_stats));
            mTvStats2.setVisibility(View.GONE);
            mTvStatsText.setVisibility(View.GONE);
        }else if(listOfplayers.size()==1){
            mSpinnerP2.setVisibility(View.GONE);
            mTvStats2.setVisibility(View.GONE);
        }

        ArrayAdapter<Player> spinnerAdapter = new ArrayAdapter<Player>(getActivity(), android.R.layout.simple_spinner_item, listOfplayers);
        mSpinnerP1.setAdapter(spinnerAdapter);
        mSpinnerP2.setAdapter(spinnerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadPlayersList();
    }

    private void loadPlayerStats(int player1ID){
        NumberFormat formatter = new DecimalFormat("#0.00");

        int wins = db.getWonMatchesByPlayer(player1ID);
        int lost = db.getLostMatchesByPlayer(player1ID);
        double perc = ((double)wins / (double)(wins + lost)) *100.00;

        Player p1 = db.getPlayerById(player1ID);

        mTvStats1.setText(p1.getFullName()+" has won "+wins+" games and lost "+lost+" games. (" + formatter.format(perc) +"%)");
    }

    private void loadPlayerVsPlayerStats(int player1ID,int player2ID){
        NumberFormat formatter = new DecimalFormat("#0.00");

        int wins = db.getMatchesPlayer1WinsVsPlayer2(player1ID,player2ID);
        int lost = db.getMatchesPlayer1LosesVsPlayer2(player1ID,player2ID);

        double perc = ((double)wins / (double)(wins + lost)) *100.00;

        if (Double.isNaN(perc)) {
            perc = 0;
        }

        Player p1 = db.getPlayerById(player1ID);
        Player p2 = db.getPlayerById(player2ID);

        if(player1ID == player2ID){
            mTvStats2.setText(getString(R.string.choose_different_players));
        }else{
            mTvStats2.setText(p1.getFullName()+" has won "+wins+" games and lost "+lost+" games versus "+p2.getFullName()+". (" + formatter.format(perc) +"%)");

        }

    }
}