package com.example.qthjen.listvideoyoutube;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends BaseAdapter {

    Context context;
    int layout;
    List<Video> list;

    public VideoAdapter(Context context, int layout, List<Video> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder {

        ImageView ivImage;
        TextView tvTitle;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder = new ViewHolder();

        if ( view == null) {

            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layout, null);
            holder.ivImage = (ImageView) view.findViewById(R.id.ivImage);
            holder.tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            view.setTag(holder);

        } else {

            holder = (ViewHolder) view.getTag();
        }

        holder.tvTitle.setText(list.get(i).getTitle());
        Picasso.with(context).load(list.get(i).getImage()).into(holder.ivImage);

        return view;
    }

}
