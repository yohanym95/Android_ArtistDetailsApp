package com.example.yohan.firebaserealtimetest;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String ARTIST_NAME = "artistname";
    public static final String ARTIST_ID = "artistid";
    EditText etName;
    Button btnAdd;
    Spinner spGenreics;
    private DatabaseReference mDatabase;
    ListView listView;
    ArrayList<Artists> artistsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.etName);
        btnAdd = findViewById(R.id.btnadd);
        spGenreics = findViewById(R.id.spinner);
        listView = findViewById(R.id.listVoew);


        mDatabase = FirebaseDatabase.getInstance().getReference("Artist");

        artistsList = new ArrayList<>();


        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addArtists();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Artists artists = artistsList.get(position);
                Intent intent = new Intent(MainActivity.this,AddTrackActivity.class);

                intent.putExtra(ARTIST_ID,artists.artistId);
                intent.putExtra(ARTIST_NAME,artists.artistName);

                startActivity(intent);


            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Artists artists = artistsList.get(position);
                showUpdateDialog(artists.getArtistId(),artists.getArtistName());

                return false;
            }
        });
    }
    @Override //
    protected void onStart() {
        super.onStart();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                artistsList.clear();
                for(DataSnapshot artistSnapshot : dataSnapshot.getChildren()){
                    Artists artists = artistSnapshot.getValue(Artists.class);
                    artistsList.add(artists);
                }

                //artist adapter class
                ArtistList adapter = new ArtistList(MainActivity.this, artistsList);
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });
    }

    //update dialog
    private void showUpdateDialog(final String artistID, final String artistName){

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog,null);
        dialogBuilder.setView(dialogView);

        final  EditText editTextname = dialogView.findViewById(R.id.etupdatename);
      //  final TextView textViewname = dialogView.findViewById(R.id.tvupdateName);

        final Button btnUpdate = dialogView.findViewById(R.id.btnupdate);
        final Spinner spGenric = dialogView.findViewById(R.id.spGenericU);
        final Button btnDelete = dialogView.findViewById(R.id.btnDelete);


        dialogBuilder.setTitle("Updating Artist : "+artistName);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editTextname.getText().toString().trim();
                String genre = spGenric.getSelectedItem().toString();

                if(TextUtils.isEmpty(name)){
                    editTextname.setError("Name Required");

                    return;
                }
                updateArtists(artistID,name,genre);


                alertDialog.dismiss();



            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteArtist(artistID);

            }
        });



    }

    //update
    private boolean updateArtists(String id,String name, String genre){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Artist").child(id);
        Artists artists = new Artists(id,name,genre);
        databaseReference.setValue(artists);
        Toast.makeText(this,"Artist Updated Successfully", Toast.LENGTH_LONG).show();

        return true;
    }

    //delete
    private void deleteArtist(String id){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Artist").child(id);
        DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Tracks").child(id);
        databaseReference.removeValue();
        databaseReference1.removeValue();

        Toast.makeText(this,"Artist deleted !",Toast.LENGTH_LONG).show();



    }

   //save data on firebase
    public void addArtists(){
        String name = etName.getText().toString().trim();
        String generic = spGenreics.getSelectedItem().toString();

        if (!TextUtils.isEmpty(name)) {
        String id =  mDatabase.push().getKey();

        Artists artists = new Artists(id,name,generic);

        mDatabase.child(id).setValue(artists);

        Toast.makeText(this,"Artist added",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(MainActivity.this,"You should enter the name",Toast.LENGTH_LONG).show();

        }

    }
}
