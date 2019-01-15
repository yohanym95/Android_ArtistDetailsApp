package com.example.yohan.firebaserealtimetest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AddTrackActivity extends AppCompatActivity {

    TextView tvArtistName;
    EditText etTrack;
    Button btnAddTrack;
    ListView listViewTracks;
    SeekBar seekBar;
    private DatabaseReference databaseReference;

    ArrayList<Track> TrackList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_track);

        tvArtistName = findViewById(R.id.tvArtistName);
        etTrack =  findViewById(R.id.etTrackName);
        btnAddTrack = findViewById(R.id.btnadd);
        listViewTracks = findViewById(R.id.listView);
        seekBar = findViewById(R.id.seekBarRating);


        Intent i = getIntent();

        String name = i.getStringExtra(MainActivity.ARTIST_NAME);
        String id = i.getStringExtra(MainActivity.ARTIST_ID);

        TrackList = new ArrayList<Track>();

        tvArtistName.setText(name);


        databaseReference = FirebaseDatabase.getInstance().getReference("Tracks").child(id);

        btnAddTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrack();

            }
        });
    }

    //list view for song
    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                TrackList.clear();
                for(DataSnapshot trackSnapshot : dataSnapshot.getChildren()){
                    Track track = trackSnapshot.getValue(Track.class);
                    TrackList.add(track);
                }

                trackList adapter = new trackList(AddTrackActivity.this,TrackList );
                listViewTracks.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //save tracks
    public void saveTrack(){
        String name = etTrack.getText().toString().trim();
        int rating = seekBar.getProgress();

        if(!TextUtils.isEmpty(name)){
            String id = databaseReference.push().getKey();
            Track track = new Track(id,name,rating);

            databaseReference.child(id).setValue(track);
            Toast.makeText(this,"Track saved Successfully",Toast.LENGTH_LONG).show();


        }else{
            Toast.makeText(this,"Track name should not be empty",Toast.LENGTH_LONG).show();
        }

       // Track track = new Track()
    }


}
