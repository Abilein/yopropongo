package com.xiberty.propongo.councils.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.councils.CommissionDetailActivity;
import com.xiberty.propongo.councils.CouncilManDetailActivity;
import com.xiberty.propongo.councils.models.DirectiveItem;
import com.xiberty.propongo.database.Commission;
import com.xiberty.propongo.database.CouncilMan;

import java.util.ArrayList;


public class CommissionAdapter extends BaseAdapter {
    private static final String TAG = CommissionAdapter.class.getSimpleName();
    private Context context;
    private ArrayList<Commission> items;

    public CommissionAdapter(Context context, ArrayList<Commission> items) {
        this.context = context;
        this.items = items;
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
            rowView = inflater.inflate(R.layout.row_commission, viewGroup, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.cardName = (TextView) rowView.findViewById(R.id.txt_name);
            viewHolder.cardAbout = (TextView) rowView.findViewById(R.id.txt_about);
            viewHolder.cardImage = (ImageView) rowView.findViewById(R.id.imgCover);
            viewHolder.btnMore = (Button) rowView.findViewById(R.id.btn_more);
            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        final Commission commission = items.get(position);
        holder.cardName.setText(commission.name);
            String textAbout = commission.description.substring(0,70)+"...";
            holder.cardAbout.setText(textAbout);


        if(commission.cover!=null)
            Glide.with(context)
                    .load(commission.cover)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.cardImage);
        else
            Glide.with(context)
                    .load(R.drawable.avatar)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.cardImage);

        holder.btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommissionDetailActivity.class);
                intent.putExtra(Constants.KEY_COUNCILMAN_ID, commission.id);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return rowView;
    }

    class ViewHolder {
        TextView cardName;
        TextView cardAbout;
        ImageView cardImage;
        Button btnMore;
    }

}