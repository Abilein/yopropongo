package com.xiberty.propongo.councils.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.utils.FileDownloader;
import com.xiberty.propongo.contrib.views.XTextView;
import com.xiberty.propongo.database.AttachmentDB;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by growcallisaya on 5/7/17.
 */

public class AttachmentAdapter extends RecyclerView.Adapter<AttachmentAdapter.RecyclerViewHolder> {
    private List<AttachmentDB> attachs;
    private Context context;


    public AttachmentAdapter(Context context, List<AttachmentDB> attachs) {
        this.attachs = attachs;
        this.context = context;
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_attach, null);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        RecyclerViewHolder viewHolder = new RecyclerViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, final int position) {
        final AttachmentDB attachment = attachs.get(position);
        Log.e("FILENAME",attachment.file);
        final String typeOfFile = viewTypeOfFile(attachment.file);
        switch (typeOfFile){
            case "TXT":
                holder.mType.setImageResource(R.drawable.file_txt);
                break;
            case "DOC":
                holder.mType.setImageResource(R.drawable.file_doc);
                break;
            case "PDF":
                holder.mType.setImageResource(R.drawable.file_pdf);
                break;
            default:
                holder.mType.setImageResource(R.drawable.file_otro);
                break;
        }

        holder.mText.setText(attachment.name);
        holder.txtDwload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent target = new Intent(Intent.ACTION_VIEW);
                String file_url = attachs.get(position).file;
                Uri uri = Uri.parse(file_url);
                target.setDataAndType(uri,"application/*");
                target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                try{
                    context.startActivity(target);
                }catch (Exception e){
                    Log.e("WARNING","No Application found, so lets Download:");

                    Toast.makeText(context, "Descargando...", Toast.LENGTH_SHORT).show();
                    String [] campos = file_url.split("/");
                    String file_name = "yopropongo_"+campos[campos.length-1];

                    new DownloadFile().execute(file_url,file_name);

                    String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                    Toast.makeText(context, "Descargado en: "+ extStorageDirectory+"/yopropongo/", Toast.LENGTH_SHORT).show();
                    }
                }
        });

    }

    private String viewTypeOfFile(String nameOfFile) {
        String [] fileExtension = nameOfFile.split("\\.");
        switch (fileExtension[fileExtension.length-1]){
            case "txt": return "TXT";
            case "doc": return "DOC";
            case "docx": return "DOC";
            case "pdf": return "PDF";
            default:return "OTHER";
        }
    }

    @Override
    public int getItemCount() {
        return attachs.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private final String TAG = RecyclerViewHolder.class.getSimpleName();
        protected ImageView mType;
        protected XTextView mText;
        protected TextView txtDwload;

        public RecyclerViewHolder(View view) {
            super(view);
            this.mType = (ImageView) view.findViewById(R.id.attachment_type);
            this.mText = (XTextView) view.findViewById(R.id.attachment_name);
            this.txtDwload = (TextView) view.findViewById(R.id.attachment_download);
        }


    }

    private class DownloadFile extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            String fileUrl = strings[0];   // -> "http://{domain}/media/council/2017/07/03/file_name.pdf"
            String fileName = strings[1];  // -> file_name.pdf
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            File folder = new File(extStorageDirectory, "yopropongo");
            folder.mkdir();

            File pdfFile = new File(folder, fileName);

            try{
                pdfFile.createNewFile();
            }catch (IOException e){
                e.printStackTrace();
            }
            FileDownloader.downloadFile(fileUrl, pdfFile);
            return null;
        }
    }
}
