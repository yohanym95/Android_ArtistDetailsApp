package com.example.yohan.firebaserealtimetest;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class trackList extends ArrayAdapter<Track> {

    private Activity context;
    ArrayList<Track> traclList;

    public trackList(Activity context, ArrayList<Track> tracks){
        super(context,R.layout.track_list,tracks);

        this.context = context;
        this.traclList = tracks;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,@NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.track_list,null,true);

        TextView textViewName = listViewItem.findViewById(R.id.tvTrackName);
        TextView textViewRating = listViewItem.findViewById(R.id.tvRating);

        Track track = traclList.get(position);

        textViewName.setText(track.getTrackName());
        textViewRating.setText(String.valueOf(track.getTrackRating()));

        return listViewItem;
    }
}
