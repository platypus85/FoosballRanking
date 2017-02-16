package com.albertocamillo.foosballranking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.albertocamillo.foosballranking.R;
import com.albertocamillo.foosballranking.model.Match;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alberto Camillo on 14/02/2017.
 */

public class MatchAdapter extends ArrayAdapter<Match> {

    private String pattern = "dd/MM/yyyy HH:mm";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(pattern);

    public MatchAdapter(Context context, ArrayList<Match> matches) {
        super(context, 0, matches);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Match match = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_match, parent, false);
            viewHolder.tvPlayer1 = (TextView) convertView.findViewById(R.id.tvP1);
            viewHolder.tvPlayer2 = (TextView) convertView.findViewById(R.id.tvP2);
            viewHolder.tvPlayer3 = (TextView) convertView.findViewById(R.id.tvP3);
            viewHolder.tvPlayer4 = (TextView) convertView.findViewById(R.id.tvP4);
            viewHolder.tvScore = (TextView) convertView.findViewById(R.id.tvScore);
            viewHolder.tvCreationDate = (TextView) convertView.findViewById(R.id.tvDate);

            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(null!=match.getPlayerOne()){
            viewHolder.tvPlayer1.setText(match.getPlayerOne().getFullName());
            viewHolder.tvPlayer1.setVisibility(View.VISIBLE);
        }else{
            viewHolder.tvPlayer1.setVisibility(View.GONE);
        }

        if(null!=match.getPlayerTwo()){
            viewHolder.tvPlayer2.setText(match.getPlayerTwo().getFullName());
            viewHolder.tvPlayer2.setVisibility(View.VISIBLE);

        }else{
            viewHolder.tvPlayer2.setVisibility(View.GONE);
        }

        if(null!=match.getPlayerThree()){
            viewHolder.tvPlayer3.setText(match.getPlayerThree().getFullName());
            viewHolder.tvPlayer3.setVisibility(View.VISIBLE);
        }else{
            viewHolder.tvPlayer3.setVisibility(View.GONE);
        }

        if(null!=match.getPlayerFour()){
            viewHolder.tvPlayer4.setText(match.getPlayerFour().getFullName());
            viewHolder.tvPlayer4.setVisibility(View.VISIBLE);
        }else{
            viewHolder.tvPlayer4.setVisibility(View.GONE);
        }

        viewHolder.tvScore.setText(match.getScoreTeam1() + " - " +match.getScoreTeam2());
        viewHolder.tvCreationDate.setText(dateFormatter.format(new Date(match.getCreationDate())).toString());
        return convertView;
    }

    private static class ViewHolder {
        TextView tvPlayer1;
        TextView tvPlayer2;
        TextView tvPlayer3;
        TextView tvPlayer4;
        TextView tvScore;
        TextView tvCreationDate;
    }
}