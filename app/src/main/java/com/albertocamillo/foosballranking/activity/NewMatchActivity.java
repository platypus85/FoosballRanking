package com.albertocamillo.foosballranking.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.albertocamillo.foosballranking.R;
import com.albertocamillo.foosballranking.db.DatabaseHandler;
import com.albertocamillo.foosballranking.model.Match;
import com.albertocamillo.foosballranking.model.Player;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Created by Alberto Camillo on 14/02/2017.
 */

public class NewMatchActivity extends AppCompatActivity {


    private Button mButtonAddMatch;
    private Spinner mP1;
    private Spinner mP2;
    private Spinner mP3;
    private Spinner mP4;
    private RadioButton mbutton11;
    private RadioButton mbutton12;
    private RadioButton mbutton21;
    private RadioButton mbutton22;
    private EditText mEditTextScoreTeam1;
    private EditText mEditTextScoreTeam2;
    private boolean canAdd = false;
    private ArrayList<Player> listOfplayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_match);
        final DatabaseHandler db = new DatabaseHandler(NewMatchActivity.this);

        mP1 = (Spinner) findViewById(R.id.player1_spinner);
        mP2 = (Spinner) findViewById(R.id.player2_spinner);
        mP3 = (Spinner) findViewById(R.id.player3_spinner);
        mP4 = (Spinner) findViewById(R.id.player4_spinner);

        mbutton11 = (RadioButton) findViewById(R.id.radio1x1);
        mbutton11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUp1x1();
            }
        });
        mbutton12 = (RadioButton) findViewById(R.id.radio1x2);
        mbutton12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUp1x2();
            }
        });
        mbutton21 = (RadioButton) findViewById(R.id.radio2x1);
        mbutton21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUp2x1();
            }
        });
        mbutton22 = (RadioButton) findViewById(R.id.radio2x2);
        mbutton22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUp2x2();
            }
        });

        mEditTextScoreTeam1 = (EditText) findViewById(R.id.etTeam1Score);
        mEditTextScoreTeam2 = (EditText) findViewById(R.id.etTeam2Score);

        mButtonAddMatch = (Button) findViewById(R.id.add_match_button);
        mButtonAddMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Match match = new Match();

                if (mbutton11.isChecked()) {

                    match.setPlayerOne((Player) mP1.getSelectedItem());
                    match.setPlayerThree((Player) mP3.getSelectedItem());

                } else if (mbutton12.isChecked()) {
                    match.setPlayerOne((Player) mP1.getSelectedItem());
                    match.setPlayerThree((Player) mP3.getSelectedItem());
                    match.setPlayerFour((Player) mP4.getSelectedItem());

                } else if (mbutton21.isChecked()) {
                    match.setPlayerOne((Player) mP1.getSelectedItem());
                    match.setPlayerTwo((Player) mP2.getSelectedItem());
                    match.setPlayerThree((Player) mP3.getSelectedItem());

                } else if (mbutton22.isChecked()) {
                    match.setPlayerOne((Player) mP1.getSelectedItem());
                    match.setPlayerTwo((Player) mP2.getSelectedItem());
                    match.setPlayerThree((Player) mP3.getSelectedItem());
                    match.setPlayerFour((Player) mP4.getSelectedItem());

                }

                if (StringUtils.isNoneBlank(mEditTextScoreTeam1.getText().toString(), mEditTextScoreTeam2.getText().toString())) {
                    match.setScoreTeam1(Integer.valueOf(mEditTextScoreTeam1.getText().toString()));
                    match.setScoreTeam2(Integer.valueOf(mEditTextScoreTeam2.getText().toString()));
                    db.addMatch(match);

                    new AlertDialog.Builder(NewMatchActivity.this)
                            .setTitle(getString(R.string.match_added))
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    mEditTextScoreTeam1.setText("");
                                    mEditTextScoreTeam2.setText("");
                                    finish();
                                }
                            }).show();
                } else {
                    new AlertDialog.Builder(NewMatchActivity.this)
                            .setTitle(getString(R.string.warning))
                            .setMessage(getString(R.string.insert_all_scores))
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                }

            }
        });


    }

    private void setUp1x1() {
        mP1.setVisibility(View.VISIBLE);
        mP2.setVisibility(View.GONE);
        mP3.setVisibility(View.VISIBLE);
        mP4.setVisibility(View.GONE);
    }

    private void setUp1x2() {
        mP1.setVisibility(View.VISIBLE);
        mP2.setVisibility(View.GONE);
        mP3.setVisibility(View.VISIBLE);
        mP4.setVisibility(View.VISIBLE);
    }

    private void setUp2x1() {
        mP1.setVisibility(View.VISIBLE);
        mP2.setVisibility(View.VISIBLE);
        mP3.setVisibility(View.VISIBLE);
        mP4.setVisibility(View.GONE);
    }

    private void setUp2x2() {
        mP1.setVisibility(View.VISIBLE);
        mP2.setVisibility(View.VISIBLE);
        mP3.setVisibility(View.VISIBLE);
        mP4.setVisibility(View.VISIBLE);
    }

    private void loadPlayersList() {
        DatabaseHandler db = new DatabaseHandler(this);
        listOfplayers = (ArrayList<Player>) db.getAllPlayers();

        if (null == listOfplayers || listOfplayers.size() <= 1) {
            AlertDialog.Builder alert = new AlertDialog.Builder(NewMatchActivity.this);

            alert.setTitle(getString(R.string.warning))
                    .setMessage(getString(R.string.insert_two_players))
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .show();

        }

        ArrayAdapter<Player> spinnerAdapter = new ArrayAdapter<Player>(this, android.R.layout.simple_spinner_item, listOfplayers);
        mP1.setAdapter(spinnerAdapter);
        mP2.setAdapter(spinnerAdapter);
        mP3.setAdapter(spinnerAdapter);
        mP4.setAdapter(spinnerAdapter);


        if (listOfplayers.size() < 3) {
            mbutton21.setVisibility(View.GONE);
            mbutton12.setVisibility(View.GONE);
            mbutton22.setVisibility(View.GONE);
        }

        if (listOfplayers.size() < 4) {
            mbutton22.setVisibility(View.GONE);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPlayersList();
    }
}
