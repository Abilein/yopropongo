package com.xiberty.propongo.commission.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.commission.CommissionDetailActivity;
import com.xiberty.propongo.db.Commission;

import java.util.List;

/**
 * Created by growcallisaya on 15/5/17.
 */

public class CommissionAdapter extends BaseAdapter {
    private Context context;
    private List<Commission> items;

    public CommissionAdapter(Context context, List<Commission> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
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
        return items.get(position).id();
    }

    @Override
    public View getView(int position, View rowView, ViewGroup parent) {
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.row_commission, parent, false);

            CommissionAdapter.ViewHolder viewHolder = new CommissionAdapter.ViewHolder();
            viewHolder.imgCover= (ImageView) rowView.findViewById(R.id.imgCover);
            viewHolder.lblName = (TextView) rowView.findViewById(R.id.lblName);
            rowView.setTag(viewHolder);
            //TODO get Commission
//            final Commission commission = Commission.fromCursor(cursor);

            final Commission commission = items.get(position);
//            viewHolder.lblName.setText(commission.name.toUpperCase());
            viewHolder.lblName.setText(commission.name().toUpperCase());
            if(commission.cover() != null && commission.cover().length() > 5)
                Glide.with(context)
                        .load(commission.cover())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewHolder.imgCover);
            rowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, CommissionDetailActivity.class);
                    intent.putExtra(Constants.KEY_COMMISSION_ID, commission.id());
                    context.startActivity(intent);
                }
            });

        }
        return rowView;
    }

    public class ViewHolder {
        ImageView imgCover;
        TextView lblName;
    }
}
