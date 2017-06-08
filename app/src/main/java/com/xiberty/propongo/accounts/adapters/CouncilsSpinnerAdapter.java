package com.xiberty.propongo.accounts.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiberty.propongo.R;
import com.xiberty.propongo.database.Council;

import java.util.ArrayList;

public class CouncilsSpinnerAdapter extends BaseAdapter {

    public static final String TAG = CouncilsSpinnerAdapter.class.getSimpleName();

    private ArrayList<Council> councils;
    private Context context;

    public CouncilsSpinnerAdapter(@NonNull Context context, ArrayList<Council> councils) {
        this.councils = councils;
        this.context = context;
    }

    @Override
    public int getCount() {
        return councils.size();
    }

    @Override
    public Council getItem(int position) {
        return councils.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        Council council = getItem(position);

        ViewHolder viewHolder;
        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.item_council, parent, false);

            viewHolder.name = (TextView) convertView.findViewById(R.id.lblName);
            viewHolder.logo = (ImageView) convertView.findViewById(R.id.imgLogo);

            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(council.name());

        Glide.with(context)
            .load(council.logo())
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(viewHolder.logo);

        return convertView;
    }


    private class ViewHolder {
        TextView name;
        ImageView logo;
    }
}