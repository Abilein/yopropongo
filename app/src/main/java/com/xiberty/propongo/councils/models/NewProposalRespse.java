package com.xiberty.propongo.councils.models;

import com.google.gson.annotations.SerializedName;
import com.xiberty.propongo.database.Attachment;
import com.xiberty.propongo.database.Council;

import java.util.List;

/**
 * Created by growcallisaya on 8/7/17.
 */

public class NewProposalRespse {
    @SerializedName("id") public int id;
    @SerializedName("title") public String title;
    @SerializedName("description") public String description;
    @SerializedName("excerpt") public String excerpt;
    @SerializedName("attachments") public List<Attachment> attachments;
    @SerializedName("type") public String type;
    @SerializedName("status") public String status;
    @SerializedName("council") public Council council;
    @SerializedName("proposer") public ProposerResponse proposer;
    @SerializedName("receivers") public List<ReceiverResponse> receiver;
}
