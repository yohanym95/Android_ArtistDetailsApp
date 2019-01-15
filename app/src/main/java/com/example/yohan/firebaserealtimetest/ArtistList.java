package com.example.yohan.firebaserealtimetest;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ArtistList extends ArrayAdapter<Artists> {
//custom adapter class for custom listview
    Activity context;
    ArrayList<Artists> list;

    public ArtistList(Activity context, ArrayList<Artists> list) {
        super(context, R.layout.custom_list,list);
        this.context = context;
        this.list = list;

    }


    @NonNull
    @Override
    public View getView(int position,@Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();

        View listViewItem = inflater.inflate(R.layout.custom_list,null,true);

        TextView tvName = listViewItem.findViewById(R.id.etartistName);
        TextView tvGeneric = listViewItem.findViewById(R.id.etartistGeneric);

        Artists artists = list.get(position);

        tvName.setText(artists.getArtistName());
        tvGeneric.setText(artists.getArtistGeneric());

        return listViewItem;


    }
}
