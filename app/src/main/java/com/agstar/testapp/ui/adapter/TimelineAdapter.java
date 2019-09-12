package com.agstar.testapp.ui.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.agstar.testapp.R;
import com.agstar.testapp.api.RequestHandler;
import com.agstar.testapp.api.model.response.Post;
import com.agstar.testapp.utils.CircleTransform;
import com.agstar.testapp.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {
    ProgressDialog progressDialog;
    private List<Post> postList;
    private Activity mActivity;

    public TimelineAdapter(List<Post> postList, Activity mActivity) {
        this.postList = postList;
        this.mActivity = mActivity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.post_item_list_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        progressDialog = new ProgressDialog(mActivity);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Processing.....");
        Post post = postList.get(position);
        holder.titleTextView.setText(post.getPostTitle());
        holder.detailsTextView.setText(post.getPostDescription());
        holder.dateTextView.setText(CommonUtils.getDate(post.getTimestamp()));
        holder.commentsCountTextView.setText(post.getCommentCount() + "");
        holder.likeCounterTextView.setText(post.getLikesCount() + "");


        Picasso.get()
                .load(post.getUser().getUserImageUrl()).transform(new CircleTransform())
                .placeholder(R.mipmap.ic_launcher_round)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.authorImageView);

        Picasso.get().load(post.getPostImageUrl()).placeholder(R.drawable.ic_image_bg).error(R.drawable.ic_image_bg).into(holder.postImageView);


        holder.likeCounterTextView.setOnClickListener(v -> {
            if (!progressDialog.isShowing()) {
                progressDialog.show();
                RequestHandler.getInstance().updateLikeStatus(post.getPostId());
            }
            new CountDownTimer(3000, 1000) {

                public void onTick(long millisUntilFinished) {

                }

                public void onFinish() {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    holder.likeCounterTextView.setText(post.getLikesCount() + 1 + "");
                }
            }.start();

        });
    }


    @Override
    public int getItemCount() {
        return postList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView postImageView, authorImageView;
        public TextView titleTextView, detailsTextView, likeCounterTextView, commentsCountTextView, dateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.postImageView = itemView.findViewById(R.id.postImageView);
            this.authorImageView = itemView.findViewById(R.id.authorImageView);
            this.titleTextView = itemView.findViewById(R.id.titleTextView);
            this.detailsTextView = itemView.findViewById(R.id.detailsTextView);
            this.likeCounterTextView = itemView.findViewById(R.id.likeCounterTextView);
            this.commentsCountTextView = itemView.findViewById(R.id.commentsCountTextView);
            this.dateTextView = itemView.findViewById(R.id.dateTextView);
        }
    }
}  