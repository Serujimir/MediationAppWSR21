package ru.serujimir.mediationapp.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.UnknownFormatConversionException;

import ru.serujimir.mediationapp.Parsing.UserPhoto;
import ru.serujimir.mediationapp.R;

public class UserPhotosAdapter extends RecyclerView.Adapter<UserPhotosAdapter.ViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<UserPhoto> userPhotoArrayList;

    public UserPhotosAdapter(Context context, ArrayList<UserPhoto> userPhotoArrayList) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.userPhotoArrayList = userPhotoArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserPhoto userPhoto = userPhotoArrayList.get(holder.getAdapterPosition());

        try {
            File imageFolder = new File(context.getFilesDir(), "images");
            File in = new File(imageFolder, userPhoto.getPhoto_address());
            Date last_modified = new Date(in.lastModified());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm ");

            FileInputStream fileInputStream = new FileInputStream(in);
            Bitmap photo = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
            holder.ivPhoto.setImageBitmap(photo);
            holder.tvPhotoCreateDate.setText(simpleDateFormat.format(last_modified));
        } catch (Exception e) {
            Log.d("TEST", e.getMessage());
            holder.ivPhoto.setImageDrawable(context.getDrawable(R.drawable.wrdw));
        }
    }

    @Override
    public int getItemCount() {
        return userPhotoArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView ivPhoto;
        final TextView tvPhotoCreateDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPhoto = itemView.findViewById(R.id.ivPhoto);
            tvPhotoCreateDate = itemView.findViewById(R.id.tvPhotoCreateDate);
        }
    }
    public void update() {
        notifyDataSetChanged();
    }
}
