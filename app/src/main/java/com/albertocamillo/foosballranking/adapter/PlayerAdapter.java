package com.albertocamillo.foosballranking.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.albertocamillo.foosballranking.R;
import com.albertocamillo.foosballranking.model.Player;

import java.util.ArrayList;

/**
 * Created by Alberto Camillo on 14/02/2017.
 */

public class PlayerAdapter extends ArrayAdapter<Player> {
    public PlayerAdapter(Context context, ArrayList<Player> players) {
        super(context, 0, players);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Player player = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_player, parent, false);
            viewHolder.fullName = (TextView) convertView.findViewById(R.id.tvP4);
            viewHolder.emailAddress = (TextView) convertView.findViewById(R.id.tvP2);
            convertView.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.fullName.setText(player.getFullName());
        viewHolder.fullName.setTypeface(null, Typeface.BOLD);
        viewHolder.emailAddress.setText(player.getEmailAddress());
        return convertView;
    }

    private static class ViewHolder {
        TextView fullName;
        TextView emailAddress;
    }
}