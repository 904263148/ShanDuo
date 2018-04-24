package com.yapin.shanduo.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.yapin.shanduo.R;
import com.yapin.shanduo.widget.SquareImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 */
public class ShowPictureAdapter extends RecyclerView.Adapter<ShowPictureAdapter.ViewHolder> {
    private Context context;
    private Activity activity;
    private List<String> list;
    private int source = -1;

    public ShowPictureAdapter(Context context, Activity activity, List<String> list) {
        this.context = context;
        this.activity = activity;
        this.list = list;
    }

    public void setSource(int source) {
        this.source = source;
    }

    @Override
    public ShowPictureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_show_picture, parent, false));
    }

    @Override
    public void onBindViewHolder(ShowPictureAdapter.ViewHolder holder, int position) {
        if (source != -1 && position == 0) {
            Glide.with(activity).load(R.drawable.add_picture).into(holder.ivPicture);
            return;
        }
        Glide.with(activity).load(list.get(position)).into(holder.ivPicture);
//        GlideUtil.load(activity, Utils.getImagePath(list.get(position)), holder.ivPicture);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, int source);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_picture)
        SquareImageView ivPicture;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null)
                        listener.onItemClick(getLayoutPosition(), source);
                }
            });
            ButterKnife.bind(this, itemView);
        }

    }
}
