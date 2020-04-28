package com.example.instgramclone;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;



public class Share extends Fragment implements View.OnClickListener {

    ImageView shareImageView ;
    Button shareButton ;
    EditText shareEditText ;
    Bitmap bitmap ;


    public Share() {
        // Required empty public constructor
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_share, container, false);

        shareImageView = view.findViewById(R.id.shareImageView);
        shareEditText = view.findViewById(R.id.shareEditText);
        shareButton = view.findViewById(R.id.shareButton);

        shareButton.setOnClickListener(Share.this);
        shareImageView.setOnClickListener(Share.this);
        return  view;

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.shareImageView:

                if (Build.VERSION.SDK_INT>=23&& ActivityCompat.checkSelfPermission(getContext(), Manifest.permission
                .READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED)
                {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                }

                break;
            case R.id.shareButton:

                if (bitmap!=null)
                {
                    if (shareEditText.getText().toString().equals(""))
                    {
                        Toast.makeText(getContext(),"Error You Must Write A Caption",Toast.LENGTH_SHORT).show();
                    }
                    else {


                       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                       bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);

                       byte[] bytes = byteArrayOutputStream.toByteArray();

                        ParseFile parseFile =new ParseFile("img.png",bytes);

                        ParseObject parseObject = new ParseObject("photos");
                        parseObject.put("pictures",parseFile);
                        parseObject.put("des",shareEditText.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());

                        final ProgressDialog progressDialog = new ProgressDialog(getContext());
                        progressDialog.setTitle("Loading ..");
                        progressDialog.show();

                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e ==null)
                                {
                                    Toast.makeText(getContext(),"Done",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(getContext(),e.getMessage().toString(),Toast.LENGTH_SHORT).show();

                                }
                                progressDialog.dismiss();
                            }
                        });

                    }


                }




        }

    }

    private void getChosenImage() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1000){
        if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){

            getChosenImage();
                }
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==2000)
        {

            if (resultCode== Activity.RESULT_OK)
            {
                try {


                Uri uri = data.getData();
                 bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),uri);

                shareImageView.setImageBitmap(bitmap);


            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            }

        }
    }
}
