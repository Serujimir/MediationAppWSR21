package ru.serujimir.mediationapp.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import ru.serujimir.mediationapp.Parsing.UserPhoto;
import ru.serujimir.mediationapp.PhotosInterface;
import ru.serujimir.mediationapp.R;

public class UserPhotosAdapter extends RecyclerView.Adapter<UserPhotosAdapter.ViewHolder> {

    Context context;
    LayoutInflater layoutInflater;
    ArrayList<UserPhoto> userPhotoArrayList;

    PhotosInterface photosInterface;

    public UserPhotosAdapter(Context context, ArrayList<UserPhoto> userPhotoArrayList, PhotosInterface photosInterface) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.userPhotoArrayList = userPhotoArrayList;
        this.photosInterface = photosInterface;
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
        File in = null;
        try {
           in = new File(context.getFilesDir() + "/" + "images", userPhoto.getPhoto_address());
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        File finalIn = in;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.PhotoDialog);
                View view = layoutInflater.inflate(R.layout.photo_view, null, false);
                ImageView ivPhoto = view.findViewById(R.id.ivPhoto);
                Glide.with(context).load(finalIn).into(ivPhoto);

                Button btnPhotoClose = view.findViewById(R.id.btnPhotoClose);
                Button btnPhotoDelete = view.findViewById(R.id.btnPhotoDelete);
                builder.setView(view);

                Dialog dialog = builder.create();

                btnPhotoClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btnPhotoDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            finalIn.delete();
                            Log.d("TEST", "DELETE file name: " + finalIn.getName());
                            photosInterface.updatePhotosList();
                            dialog.dismiss();
                        } catch (Exception e) {
                            Toast.makeText(context,  e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                dialog.show();
            }
        });

        try {
            Date last_modified = new Date(in.lastModified());
            @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm ");
            Glide.with(context)
                    .load(in)
                    .error(R.drawable.wrdw)
                    .into(holder.ivPhoto);
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
            ivPhoto = itemView.findViewById(R.id.ivProfilePhotos);
            tvPhotoCreateDate = itemView.findViewById(R.id.tvPhotoCreateDate);
        }

    }
    public void update() {
        notifyDataSetChanged();
    }
}
