package com.example.instgramclone;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.tabs.TabLayout;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.viewpager.widget.ViewPager;

public class SocialMedia extends AppCompatActivity {

    public Toolbar toolbar;
    public ViewPager viewPager ;
    public TabLayout tapLayout ;
    public TapAdapter tapAdapter ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

        setTitle("Social Media");

        toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        viewPager = findViewById(R.id.viewPager);
        tapAdapter = new TapAdapter(getSupportFragmentManager());
        viewPager.setAdapter(tapAdapter);
        tapLayout = findViewById(R.id.tabLayout);
        tapLayout.setupWithViewPager(viewPager,true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId()==R.id.postImageItem)
        {

            if (Build.VERSION.SDK_INT>=23 && ActivityCompat.checkSelfPermission(SocialMedia.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            {

                requestPermissions(new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE},3000);
            }
            else {

                getChosenImage();
            }
        }
        else if (item.getItemId()==R.id.LogOutUserItem)
        {
            ParseUser.logOut();
            Intent intent = new Intent(SocialMedia.this , MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void getChosenImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,4000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==4000 &&resultCode==RESULT_OK &&data!=null){

            Uri uri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

            byte[] bytes = byteArrayOutputStream.toByteArray();


            ParseFile parseFile =new ParseFile("img.png",bytes);

            ParseObject parseObject = new ParseObject("photos");
            parseObject.put("pictures",parseFile);
            parseObject.put("username", ParseUser.getCurrentUser().getUsername());

            final ProgressDialog progressDialog = new ProgressDialog(SocialMedia.this);
            progressDialog.setTitle("Loading ..");
            progressDialog.show();

            parseObject.saveInBackground(new SaveCallback() {
                @Override
                public void done(ParseException e) {

                    if (e ==null)
                    {
                        Toast.makeText(SocialMedia.this,"Done",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(SocialMedia.this,"Error",Toast.LENGTH_SHORT).show();

                    }
                    progressDialog.dismiss();
                }
            });



        }
    }
}
