package com.xiberty.propongo.councils.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.councils.InboxDetailActivity;
import com.xiberty.propongo.councils.models.NewProposalResponse;
import com.xiberty.propongo.database.ProposalDB;
import com.xiberty.propongo.database.ProposalDB_Table;

import java.util.List;


public class InboxProposalsAdapter extends BaseAdapter {
    private Context context;
    private List<NewProposalResponse> items;
    private String TAG;

    public InboxProposalsAdapter(Context context, List<NewProposalResponse> items, String TAG) {
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
            rowView = inflater.inflate(R.layout.row_proposal, viewGroup, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.card = (CardView)  rowView.findViewById(R.id.card_view);
            viewHolder.cardTitle = (TextView) rowView.findViewById(R.id.lblTitle);
            viewHolder.cardSummary = (TextView) rowView.findViewById(R.id.lblSummary);
            viewHolder.cardRate = (TextView) rowView.findViewById(R.id.lblRate);
            viewHolder.cardView = (TextView) rowView.findViewById(R.id.lblViews);
            viewHolder.cardFiles = (TextView) rowView.findViewById(R.id.lblFiles);
            viewHolder.cardImageStatus = (ImageView) rowView.findViewById(R.id.image_status);

            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        final NewProposalResponse proposal = items.get(position);

        holder.cardTitle.setText(proposal.title);
        String textAbout = proposal.description;

        if (proposal.description.length()>40)
            textAbout = proposal.description.substring(0,40)+"...";

        holder.cardSummary.setText(textAbout);
        holder.cardImageStatus.setImageResource(R.drawable.inboox);

        ProposalDB proposalDB = SQLite.select().
                from(ProposalDB.class).
                where(ProposalDB_Table.id.is(proposal.id)).
                querySingle();
        holder.cardRate.setText(String.valueOf(proposalDB.average)+" punto(s)");
        holder.cardView.setText(String.valueOf(proposalDB.views)+ " vista(s)");
        holder.cardFiles.setText(String.valueOf(proposal.attachments.size())+" archivo(s)");

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Gson gson = new Gson();
                String proposalStr = gson.toJson(proposal);

                Intent intent = new Intent(context, InboxDetailActivity.class);
                intent.putExtra(Constants.KEY_PROPOSAL_ID, proposalStr);
                intent.putExtra(Constants.KEY_BASE_CLASS, TAG);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        return rowView;
    }

    class ViewHolder {
        CardView card;
        TextView cardTitle;
        TextView cardSummary;
        TextView cardRate;
        TextView cardView;
        TextView cardFiles;
        ImageView cardImageStatus;
    }

}