package com.example.android.watchmovietrailers;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class TrailerRecAdapter extends RecyclerView.Adapter<TrailerRecAdapter.TrailerViewHolder> {

    private Context mContext;
    private List<Trailer> mTrailer;

    public TrailerRecAdapter (Context context){
        mContext= context;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View root = LayoutInflater.from(mContext).inflate(R.layout.list_item_trailer, parent, false);
        return new TrailerViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
       Trailer trailer =  mTrailer.get(position);
       final String trailerUrl = trailer.getTrailerImage();
       holder.mImage.setImageResource(R.drawable.play);
       holder.mImage.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_VIEW);
               intent.setData(Uri.parse(trailerUrl));
               mContext.startActivity(intent);

           }
       });


    }

    @Override
    public int getItemCount() {
        if (mTrailer == null){
            return 0;
        }
        return mTrailer.size();
    }


    class TrailerViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImage;

        private TrailerViewHolder(View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.image_play);
        }
    }

    public void bindTrailerList(List<Trailer> trailerList){
        mTrailer = trailerList;
        notifyDataSetChanged();
    }
}
