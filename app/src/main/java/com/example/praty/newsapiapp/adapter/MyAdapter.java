package com.example.praty.newsapiapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.praty.newsapiapp.R;
import com.example.praty.newsapiapp.helper.ItemClickListener;
import com.example.praty.newsapiapp.model.News;

import java.util.List;


//adapter class for attaching the recyclerview with the online retrofit data
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context mContext;
    private List<News> mArticles;
    private ItemClickListener mListener;

    public MyAdapter(Context mContext, List<News> mArticles, ItemClickListener mListener) {
        this.mContext = mContext;
        this.mArticles = mArticles;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_cardview, parent, false);
        final ViewHolder holder=new ViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onItemClick(v, holder.getAdapterPosition());
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(mArticles.get(position).getTitle());
        holder.description.setText(mArticles.get(position).getDescription());

        String authorName=mArticles.get(position).getAuthor();
        if(authorName== null || authorName.equals(""))
            authorName="by Anonymous";
        else authorName="by "+ mArticles.get(position).getAuthor();
        holder.author.setText(authorName);

        String date=mArticles.get(position).getPublishedAt();
        date=date.substring(0,10);
        holder.timestamp.setText(date);

        Glide.with(mContext)
                .load(mArticles.get(position).getUrlToImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(1000,1000)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, description, author, timestamp;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            title=(TextView) itemView.findViewById(R.id.title);
            description=(TextView) itemView.findViewById(R.id.description);
            author=(TextView) itemView.findViewById(R.id.author);
            timestamp=(TextView) itemView.findViewById(R.id.timestamp);
            image=(ImageView) itemView.findViewById(R.id.image);
        }
    }
}
