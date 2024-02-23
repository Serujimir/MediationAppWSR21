package ru.serujimir.mediationapp;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.ActivityResultRegistry;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresExtension;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;

import ru.serujimir.mediationapp.Adapters.UserPhotosAdapter;
import ru.serujimir.mediationapp.Parsing.UserPhoto;

public class ProfileFragment extends Fragment {
    private String profileNickName, profileAvatar;

    View view;
    ImageView ivProfilePhoto;
    TextView tvProfileName;
    Button btnProfileAddImage;
    RecyclerView rvPhotos;
    UserPhotosAdapter userPhotosAdapter;

    int photos_count;

    ArrayList<UserPhoto> userPhotoArrayList;
    ActivityResultLauncher<String> iTakePhoto;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            profileNickName = getArguments().getString("nickName", "null");
            profileAvatar = getArguments().getString("avatar", "null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        init();

        iTakePhoto = registerForActivityResult(new ActivityResultContracts.GetContent(), new ActivityResultCallback<Uri>() {
            @Override
            public void onActivityResult(Uri o) {
                Bitmap bitmap;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), o);
                    savaImageToAppFolder(bitmap, "image_" + photos_count);
                } catch (IOException e) {
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;


    }

    public void init() {
        userPhotoArrayList = new ArrayList<>();

        rvPhotos = view.findViewById(R.id.rvPhotos);
        userPhotosAdapter = new UserPhotosAdapter(getContext(), userPhotoArrayList);
        rvPhotos.setAdapter(userPhotosAdapter);
        rvPhotos.setLayoutManager(new GridLayoutManager(getContext(), 2, LinearLayoutManager.VERTICAL, false));

        ivProfilePhoto = view.findViewById(R.id.ivProfilePhoto);
        tvProfileName = view.findViewById(R.id.tvProfileName);
        btnProfileAddImage = view.findViewById(R.id.btnProfileAddImage);
        btnProfileAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iTakePhoto.launch("image/*");
            }
        });

        Glide.with(getContext()).load(profileAvatar).into(ivProfilePhoto);
        tvProfileName.setText(profileNickName);

        getAllPhotos();
    }


    public void getAllPhotos() {
        userPhotoArrayList.clear();
        File appFolder = new File(getContext().getFilesDir(), "images");
        if (!appFolder.exists()) {
            appFolder.mkdir();
        }
        String[] photos = appFolder.list();
        photos_count = photos.length;

        for (int i = 0; i < photos_count; i++) {
            userPhotoArrayList.add(new UserPhoto(photos[i]));
        }
        userPhotosAdapter.update();
    }

    public void savaImageToAppFolder(Bitmap image, String imageName) throws IOException {
        try {
            File appFolder = new File(getContext().getFilesDir(), "images");
            if (!appFolder.exists()) {
                appFolder.mkdir();
            }

            File out = new File(appFolder, imageName + ".jpeg");
            FileOutputStream fileOutputStream = new FileOutputStream(out);
            image.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
            getAllPhotos();
        } catch (Exception e) {
            Toast.makeText(getContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}