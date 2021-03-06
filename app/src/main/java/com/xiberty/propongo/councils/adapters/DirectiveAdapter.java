package com.xiberty.propongo.councils.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.views.XTextView;
import com.xiberty.propongo.contrib.views.XTextViewBold;
import com.xiberty.propongo.councils.CouncilManDetailActivity;
import com.xiberty.propongo.councils.models.DirectiveItem;
import com.xiberty.propongo.database.CouncilMan;

import java.util.ArrayList;



public class DirectiveAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<DirectiveItem> items;
    private String TAG;

    public DirectiveAdapter(Context context, ArrayList<DirectiveItem> items,String TAG) {
        this.context = context;
        this.items = items;
        this.TAG = TAG;
    }

    @Override
    public int getCount() {
//        return 0;
        if(items!=null)
            return items.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.indexOf(items.get(position));
    }

    @Override
    public View getView(final int position, View rowView, ViewGroup viewGroup) {

        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_directive, viewGroup, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.fullName = (XTextViewBold) rowView.findViewById(R.id.lblFullName);
            viewHolder.position = (XTextView) rowView.findViewById(R.id.lblPosition);
            viewHolder.avatar = (ImageView) rowView.findViewById(R.id.imgAvatar);
            viewHolder.flag = (ImageView) rowView.findViewById(R.id.imgFlag);

            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        final CouncilMan councilMan = items.get(position).councilMan;
        final String pos = items.get(position).position;

        holder.fullName.setText(councilMan.first_name + " " + councilMan.last_name);
        holder.position.setText(pos);
        holder.flag.setImageResource(councilMan.getFlag(context));
        if(councilMan.avatar.length()>5)
            Glide.with(context)
                    .load(councilMan.avatar)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.avatar);
        else
            Glide.with(context)
                    .load(R.drawable.avatar)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.avatar);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, CouncilManDetailActivity.class);
                intent.putExtra(Constants.KEY_COUNCILMAN_ID, councilMan.id);
                intent.putExtra(Constants.KEY_BASE_CLASS, TAG);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return rowView;
    }

    class ViewHolder {
        XTextViewBold fullName;
        XTextView position;
        ImageView avatar;
        ImageView flag;
    }

}