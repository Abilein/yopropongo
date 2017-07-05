package com.xiberty.propongo.councils.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xiberty.propongo.R;
import com.xiberty.propongo.councils.ProposalDetailActivity;
import com.xiberty.propongo.database.Attachment;
import com.xiberty.propongo.database.AttachmentDB;
import com.xiberty.propongo.database.Comment;

import java.util.List;

/**
 * Created by growcallisaya on 5/7/17.
 */

public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.CustomViewHolder> {
    private List<AttachmentDB> attachs;
    private Context context;

    public AttachmentAdapter(Context context, List<AttachmentDB> attachs) {
        this.attachs = attachs;
        this.context = context;
    }


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_attach, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        AttachmentDB attachment = attachs.get(position);
        holder.textView.setText(attachment.name);
    }

    @Override
    public int getItemCount() {
        return attachs.size();
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.attachment_name);
        }
    }
}
