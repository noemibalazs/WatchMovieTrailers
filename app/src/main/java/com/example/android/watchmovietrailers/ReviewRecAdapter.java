package com.example.android.watchmovietrailers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ReviewRecAdapter extends RecyclerView.Adapter<ReviewRecAdapter.ReviewViewHolder> {

    private Context mContext;
    private List<Review> mReviews;

    public ReviewRecAdapter(Context context){
        mContext= context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(mContext).inflate(R.layout.list_item_review, parent, false);
        return new ReviewViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = mReviews.get(position);
        String author= review.getAuthor();
        String comment = review.getComments();
        holder.mAuthor.setText(author);
        holder.mComment.setText(comment);
    }

    @Override
    public int getItemCount() {
        if (mReviews == null){
            return 0;
        }
        return mReviews.size();
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{
       private TextView mAuthor;
       private TextView mComment;


        private ReviewViewHolder(View itemView) {
            super(itemView);
            mAuthor = itemView.findViewById(R.id.tv_author);
            mComment=itemView.findViewById(R.id.tv_comment);
        }
    }

    public void bindReviewList(List<Review> reviewList){
        mReviews = reviewList;
        notifyDataSetChanged();
    }
}
