package com.xiberty.propongo.councils.fragments;

import android.content.Context;
import android.util.Log;

import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.councils.models.Commission_Proposal;
import com.xiberty.propongo.councils.models.Councilman_Proposal;
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
             if(response.isSuccessful()){
                /**
                 * Save in the Database
                 **/

                Log.e("MainPresenter","Updating Database .....");
                List<Proposal> proposals = response.body();
                for (Proposal proposal : proposals){
                    ProposalDB proposalDB = new ProposalDB();
                    proposalDB.id = proposal.getId();
                    proposalDB.title = proposal.getTitle();
                    proposalDB.description = proposal.getDescription();
                    proposalDB.excerpt = proposal.getExcerpt();
                    proposalDB.views = proposal.getViews();
                    proposalDB.average = proposal.getAverage();
                    proposalDB.rate = proposal.getRate();
                    proposalDB.type = proposal.getType();
                    proposalDB.status = proposal.getStatus();
                    proposalDB.datetime= proposal.getDatetime();

                    //Commissions IDs (Example. 1,9)
                    List<Commission_Proposal> commission_proposals = proposal.getCommissions();
                    String id_Commissions="";
                    for ( Commission_Proposal commission_proposal : commission_proposals)
                        id_Commissions+= commission_proposal.id+",";
                    if (id_Commissions.length()>0)
                        proposalDB.commissions = id_Commissions.substring(0, id_Commissions.length()-1);

                    //Councilmen IDs (Example. 11,2)
                    List<Councilman_Proposal> councilman_proposals = proposal.getCouncilmen();
                    String id_Councilmen="";
                    if (councilman_proposals.size()>0) {
                        for (Councilman_Proposal councilman_proposal : councilman_proposals)
                            id_Councilmen += councilman_proposal.id + ",";
                        proposalDB.councilmen = id_Councilmen.substring(0, id_Councilmen.length() - 1);
                    }
                    //Council ID (Example. 2)
                    proposalDB.council = proposal.getCouncil().getId();
                    proposalDB.save();

                    //Attachments of a Council
                    List<Attachment> attachments = proposal.getAttachments();
                    for (Attachment attachment: attachments){
                        AttachmentDB attachmentDB = new AttachmentDB();
                        attachmentDB.id = attachment.getId();
                        attachmentDB.name = attachment.getName();
                        attachmentDB.file = attachment.getFile();
                        attachmentDB.proposal = proposal.getId();
                        attachmentDB.save();
                    }
                    mView.setRefreshigFalse();
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
