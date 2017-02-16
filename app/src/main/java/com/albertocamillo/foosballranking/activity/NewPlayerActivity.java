package com.albertocamillo.foosballranking.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.albertocamillo.foosballranking.R;
import com.albertocamillo.foosballranking.db.DatabaseHandler;
import com.albertocamillo.foosballranking.model.Player;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Alberto Camillo on 14/02/2017.
 */

public class NewPlayerActivity extends AppCompatActivity {

    private EditText mEditTextPlayerName;
    private EditText mEditTextEmail;
    private Button mButtonAddPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_player);
        mEditTextPlayerName = (EditText) findViewById(R.id.player_name);
        mEditTextEmail = (EditText) findViewById(R.id.player_email);
        mButtonAddPlayer = (Button) findViewById(R.id.add_player_button);

        mButtonAddPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fName = mEditTextPlayerName.getText().toString();
                String eAddress = mEditTextEmail.getText().toString();

                if (StringUtils.isBlank(fName) || StringUtils.isBlank(eAddress)) {
                    new AlertDialog.Builder(NewPlayerActivity.this)
                            .setTitle(getString(R.string.warning))
                            .setMessage(getString(R.string.insert_all_fields))
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            }).show();
                } else {
                    DatabaseHandler db = new DatabaseHandler(NewPlayerActivity.this);
                    Player newPlayer = new Player(fName, eAddress);

                    if(null!=db.getPlayerByEmailAddress(eAddress)){
                        new AlertDialog.Builder(NewPlayerActivity.this)
                                .setTitle(getString(R.string.error))
                                .setMessage(getString(R.string.player_exists))
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                }).show();
                    }else{
                        db.addPlayer(newPlayer);

                        new AlertDialog.Builder(NewPlayerActivity.this)
                                .setTitle(getString(R.string.player_added))
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        mEditTextPlayerName.setText("");
                                        mEditTextEmail.setText("");
                                        finish();
                                    }
                                }).show();
                    }

                }
            }
        });
    }
}
