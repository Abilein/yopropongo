package com.xiberty.propongo.councils.models;

import com.google.gson.annotations.SerializedName;
import com.xiberty.propongo.database.Attachment;
import com.xiberty.propongo.database.Council;

/**
 * Created by growcallisaya on 8/7/17.
 */

public class NewProposalRespse {

        @SerializedName("id") public int id;
        @SerializedName("title") public String title;
        @SerializedName("description") public String description;
        @SerializedName("excerpt") public String excerpt;
        @SerializedName("attachments") public Attachment attached_file;
        @SerializedName("proposer") public PersonResponse proposer;
        @SerializedName("receiver") public PersonResponse receiver;
        @SerializedName("council") public Council council;
        @SerializedName("type") public String type;
        @SerializedName("is_valid") public boolean is_valid;
}
