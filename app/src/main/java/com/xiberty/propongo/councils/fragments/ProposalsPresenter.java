package com.xiberty.propongo.councils.fragments;

import android.content.Context;
import android.util.Log;

import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.database.Attachment;
import com.xiberty.propongo.database.AttachmentDB;
import com.xiberty.propongo.database.Council;
import com.xiberty.propongo.database.Proposal;
import com.xiberty.propongo.database.ProposalDB;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProposalsPresenter implements ProposalsContract.Presenter {

    String TAG = ProposalsPresenter.class.getSimpleName();
    private CouncilService service;
    private ProposalsFragment mView;

    public ProposalsPresenter(CouncilService service, ProposalsFragment view) {
        this.service = service;
        this.mView = view;
    }

    @Override
    public void getProposals(final Context context) {
        Council defaultCouncil = Store.getDefaultCouncil(context);
        String id = String.valueOf(defaultCouncil.id);
        Call<List<Proposal>> proposalCall = service.getProposal(id);
        proposalCall.enqueue(new Callback<List<Proposal>>() {
            @Override
            public void onResponse(Call<List<Proposal>> call, Response<List<Proposal>> response) {
                if (response.isSuccessful()){
                    /**
                     * Save in the Database
                     **/
                    Log.e("MainPreenter","Proposal Database in progress.....");
                    List<Proposal> proposals = response.body();
                    for (Proposal proposal : proposals){
                        ProposalDB proposalDB = new ProposalDB();
                        proposalDB.id = proposal.getId();
                        proposalDB.title = proposal.getTitle();
                        proposalDB.summary = proposal.getSummary();
                        proposalDB.commissions = proposal.getCommissions();
                        proposalDB.councilmen = proposal.getCouncilmen();
                        proposalDB.views = proposal.getViews();
                        proposalDB.average = proposal.getAverage();
                        proposalDB.creation_date= proposal.getCreation_date();
                        proposalDB.council = proposal.council;
                        proposalDB.save();

                        List<Attachment> attachments = proposal.getAttachments();
                        for (Attachment attachment: attachments){
                            AttachmentDB attachmentDB = new AttachmentDB();
                            attachmentDB.id = attachment.getId();
                            attachmentDB.name = attachment.getName();
                            attachmentDB.file = attachment.getFile();
                            attachmentDB.proposal = proposal.getId();
                            attachmentDB.save();
                        }
                    }
                }else{
                    FormattedResp error = ParserError.parse(response);
                    String errorMessage = MessageManager.getMessage(context, error.code());
                    mView.showError(errorMessage);
                }
            }



            @Override
            public void onFailure(Call<List<Proposal>> call, Throwable t) {
                Log.e("MainPreenter","WTF! Failed 'cause "+t.getCause());
            }
        });
    }

}
