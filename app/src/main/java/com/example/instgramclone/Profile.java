package com.example.instgramclone;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import androidx.fragment.app.Fragment;


public class Profile extends Fragment {

    EditText nameEditText , bioEditText , professionEditText , hobbiesEditText , sportsEditText ;
    Button updateInfo ;

    public Profile() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        nameEditText = view.findViewById(R.id.nameEditText);
        bioEditText = view.findViewById(R.id.bioEditText);
        professionEditText = view.findViewById(R.id.professionEditText);
        hobbiesEditText = view.findViewById(R.id.hobbiesEditText);
        sportsEditText = view.findViewById(R.id.favouriteSportEditText);

        updateInfo = view.findViewById(R.id.updateInfo);
        final ParseUser parseUser = ParseUser.getCurrentUser();


        if (parseUser.get("profileName")==null && parseUser.get("bio")==null && parseUser.get("profession")==null
        && parseUser.get("hobbies")==null &&parseUser.get("sports")==null )
        {
            nameEditText.setText( "");
            bioEditText.setText("");
            professionEditText.setText( "");
            hobbiesEditText.setText( "");
            sportsEditText.setText("");

        }
        else {
            nameEditText.setText(parseUser.get("profileName") + "");
            bioEditText.setText(parseUser.get("bio") + "");
            professionEditText.setText(parseUser.get("profession") + "");
            hobbiesEditText.setText(parseUser.get("hobbies") + "");
            sportsEditText.setText(parseUser.get("sports") + "");
        }


        updateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                parseUser.put("profileName",nameEditText.getText().toString());
                parseUser.put("bio",bioEditText.getText().toString());
                parseUser.put("profession",professionEditText.getText().toString());
                parseUser.put("hobbies",hobbiesEditText.getText().toString());
                parseUser.put("sports",sportsEditText.getText().toString());
                final ProgressDialog progressDialog =new  ProgressDialog(getContext());
                progressDialog.setTitle("Update Info");
                progressDialog.show();
                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null )
                        {

                        }
                        else
                        {
                            Toast.makeText(getContext()," Something wrong",Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                });

            }
        });


        return  view ;
    }
}
