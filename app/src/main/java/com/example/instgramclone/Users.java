package com.example.instgramclone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class Users extends Fragment implements AdapterView.OnItemClickListener {


        ListView listView ;
        ArrayList<String> arrayListParseUsers;
        ArrayAdapter arrayAdapter ;

    public Users() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        listView = view.findViewById(R.id.listView);
        arrayListParseUsers = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arrayListParseUsers);
        listView.setOnItemClickListener(this);


        ParseQuery <ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e==null)
                {
                    if (objects.size()>0)
                    {
                        for (ParseUser users:objects)
                        {
                            arrayListParseUsers.add(users.getUsername());


                        }
                        listView.setAdapter(arrayAdapter);

                    }

                }
            }
        });


        return view ;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        Intent intent = new Intent(getContext(),Users.class);
        intent.putExtra("username",arrayListParseUsers.get(i));
        startActivity(intent);

    }
}
