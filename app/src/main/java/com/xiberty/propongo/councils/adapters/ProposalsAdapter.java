package com.xiberty.propongo.councils.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.councils.CommissionDetailActivity;
import com.xiberty.propongo.database.Attachment;
import com.xiberty.propongo.database.AttachmentDB;
import com.xiberty.propongo.database.AttachmentDB_Table;
import com.xiberty.propongo.database.Commission;
import com.xiberty.propongo.database.Proposal;
import com.xiberty.propongo.database.ProposalDB;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class ProposalsAdapter extends BaseAdapter {
    private static final String TAG = ProposalsAdapter.class.getSimpleName();
    private Context context;
    private List<ProposalDB> items;

    public ProposalsAdapter(Context context, List<ProposalDB> items) {
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
            rowView = inflater.inflate(R.layout.row_proposal, viewGroup, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.card = (CardView)  rowView.findViewById(R.id.card_view);
            viewHolder.cardTitle = (TextView) rowView.findViewById(R.id.lblTitle);
            viewHolder.cardSummary = (TextView) rowView.findViewById(R.id.lblSummary);
            viewHolder.cardRate = (TextView) rowView.findViewById(R.id.lblRate);
            viewHolder.cardView = (TextView) rowView.findViewById(R.id.lblViews);
            viewHolder.cardFiles = (TextView) rowView.findViewById(R.id.lblFiles);

            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        final ProposalDB proposal = items.get(position);

        holder.cardTitle.setText(proposal.title);
        String textAbout = proposal.summary.substring(0,30)+"...";
        holder.cardSummary.setText(textAbout);
        holder.cardView.setText(proposal.views+" vistas");
        holder.cardRate.setText(proposal.rate+" puntos");
        List<AttachmentDB> attachments = SQLite.select().
                from(AttachmentDB.class).
                where(AttachmentDB_Table.proposal.is(proposal.getId())).
                queryList();

        holder.cardFiles.setText(attachments.size()+" archivos");

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommissionDetailActivity.class);
                intent.putExtra(Constants.KEY_COUNCILMAN_ID, proposal.getId());
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
    }

}