package com.example.smartfarmtool.Main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.smartfarmtool.Model.Articles;
import com.example.smartfarmtool.R;
import com.example.smartfarmtool.Utils;

import java.util.List;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    private List<Articles> articles;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public NewsAdapter(List<Articles> articles, Context context) {
        this.articles = articles;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.news_item,viewGroup,false);
        return new MyViewHolder(view,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder viewHolder, int i) {
        final MyViewHolder holder=viewHolder;
        Articles model=articles.get(i);
        RequestOptions requestOptions=new RequestOptions();
        requestOptions.placeholder(Utils.getRandomDrawbleColor());
        requestOptions.error(Utils.getRandomDrawbleColor());
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(model.getUrlToImage())
                .apply(requestOptions)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);

                        return false;
                    }
                }).transition(DrawableTransitionOptions.withCrossFade())
                .into(holder.imageView);


        holder.title.setText(model.getTitle());
        holder.desc.setText(model.getDesciption());

//        holder.source.setText(model.getSource().getName());
        holder.time.setText(String.format("⠢%s", Utils.DateToTimeFormat(model.getPublishedAt())));
        holder.published.setText(Utils.DateFormat(model.getPublishedAt()));
        holder.author.setText(model.getAuthor());



    }

    @Override
    public int getItemCount() {
        return articles.size();
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(View view, int  position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView title,desc,author,published,source,time;
        ImageView imageView;
        ProgressBar progressBar;
        OnItemClickListener onItemClickListener;

        public MyViewHolder(View itemView ,OnItemClickListener onItemClickListener) {
            super(itemView);

            itemView.setOnClickListener(this);
            time=itemView.findViewById(R.id.news_time);
            desc=itemView.findViewById(R.id.news_desc);
            author=itemView.findViewById(R.id.news_author);
            published=itemView.findViewById(R.id.news_publishedAt);
            source=itemView.findViewById(R.id.news_source);
            title=itemView.findViewById(R.id.news_title);
            imageView=itemView.findViewById(R.id.news_img);

            progressBar=itemView.findViewById(R.id.news_progress_bar);
            imageView=itemView.findViewById(R.id.news_img);
            this.onItemClickListener=onItemClickListener;


        }

        @Override
        public void onClick(View view) {
//                onItemClickListener.onItemClick(view,getAdapterPosition());
        }

    }

}
