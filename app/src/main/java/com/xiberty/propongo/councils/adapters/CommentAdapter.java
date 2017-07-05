package com.xiberty.propongo.councils.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.utils.UIUtils;
import com.xiberty.propongo.councils.ProposalDetailActivity;
import com.xiberty.propongo.database.Comment;
import com.xiberty.propongo.database.Commission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by growcallisaya on 5/7/17.
 */

public class CommentAdapter  extends BaseAdapter{
    private Context context;
    private List<Comment> items;

    public CommentAdapter(Context context, List<Comment> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.get(position).id;
    }

    @Override
    public View getView(int position, View rowView, ViewGroup viewGroup) {

        if (rowView ==null){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.row_comment, viewGroup, false);

            ViewHolder holder = new ViewHolder();
            holder.content = (TextView) rowView.findViewById(R.id.lblComment);
            holder.date = (TextView) rowView.findViewById(R.id.lblDate);
            holder.fullname = (TextView) rowView.findViewById(R.id.lblFullName);
            holder.avatar = (ImageView) rowView.findViewById(R.id.imgAvatar);
            rowView.setTag(holder);
        }
        ViewHolder holder = (ViewHolder) rowView.getTag();

        final Comment comment = items.get(position);
        holder.fullname.setText(comment.full_name);
        holder.content.setText(comment.content);
        holder.date.setText(UIUtils.convertToDate(comment.date));
        if (comment.avatar.length()>0)
            Glide.with(context).load(comment.avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.avatar);
        else
            Glide.with(context).load(R.drawable.avatar).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.avatar);

        return rowView;

    }

    class ViewHolder {
        TextView content;
        TextView date;
        TextView fullname;
        ImageView avatar;

    }
}
