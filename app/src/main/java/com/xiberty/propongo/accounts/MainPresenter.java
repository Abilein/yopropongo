package com.xiberty.propongo.accounts;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.Excepts;
import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.OAuthCollection;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.councils.models.Commission_Proposal;
import com.xiberty.propongo.councils.models.Councilman_Proposal;
import com.xiberty.propongo.credentials.CredentialService;
import com.xiberty.propongo.database.Attachment;
import com.xiberty.propongo.database.AttachmentDB;
import com.xiberty.propongo.database.Commission;
import com.xiberty.propongo.database.Council;
import com.xiberty.propongo.database.CouncilMan;
import com.xiberty.propongo.database.Proposal;
import com.xiberty.propongo.database.ProposalDB;

import java.util.List;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainPresenter implements MainContract.Presenter {

    private MainContract.View mView;
    private AccountService aService;
    private CredentialService cService;
    private CouncilService ccService;
    private int counter = 0;

    public MainPresenter(MainContract.View mView, AccountService aService, CredentialService cService, CouncilService ccService) {
        this.mView = mView;
        this.aService = aService;
        this.cService = cService;
        this.ccService = ccService;
    }

    @Override
    public void logout(final Context context) {
        if(Store.isLoggedIn(context)) {


            Call<ResponseBody> tokenResponseCall = cService.logout(
                    Constants.CLIENT_ID, Constants.CLIENT_SECRET, Store.getAccessToken(context));

            tokenResponseCall.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    mView.hideProgress();
                    if (response.isSuccessful()) {
                        Store.removeCredential(context);
                        mView.logoutSuccess();
                    } else {
                        FormattedResp error = ParserError.parse(response);
                        String errorMessage = MessageManager.getMessage(context, error.code());
                        mView.showError(errorMessage);
                        if(error.code().equals(MessageManager.INVALID_ACCESS_TOKEN)) {
                            Store.removeCredential(context);
                            mView.logoutSuccess();
                        }
                    }
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mView.hideProgress();

                    if(Excepts.isConnectionFailure(t)) {

                        // TODO Construir un servicio que elimine los AcessToken PASADOS

                        OAuthCollection oauthCollection = Store.getOAuthCollection(context);
                        oauthCollection.credentials().add(Store.getAccessToken(context));
                        Store.saveOAuthCollection(context, oauthCollection);

                        String errorMessage = MessageManager.getMessage(context, MessageManager.WITHOUT_CONNECTION);
                        Store.removeCredential(context);
                        mView.showError(errorMessage);
                        mView.logoutSuccess();

                    }

                    // TODO VALIDAR CUANDO EN EL SERVER SALE UN ERROR 500, 403
                }
            });

        }
    }

    @Override
    public void saveCouncils(final Context context) {
        mView.showProgress();
        Call<List<Council>> councilsCall = ccService.getCouncils();
        councilsCall.enqueue(new Callback<List<Council>>() {
            @Override
            public void onResponse(Call<List<Council>> call, Response<List<Council>> response) {
                if (response.isSuccessful()) {
                    mView.hideProgress();
                    List<Council> councils = response.body();
                    mView.showCouncils(councils);
                } else {
                    FormattedResp error = ParserError.parse(response);
                    String errorMessage = MessageManager.getMessage(context, error.code());
                    mView.showError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<Council>> call, Throwable t) {
                mView.hideProgress();
                Log.e("MainPresenter",t.getCause()+"");
            }
        });
    }

    @Override
    public void saveCouncilmen(final Context context) {
        Council defaultCouncil = Store.getDefaultCouncil(context);
        Call<List<CouncilMan>> councilmenCall = ccService.getCouncilMan(defaultCouncil.id()+"");
        councilmenCall.enqueue(new Callback<List<CouncilMan>>() {
            @Override
            public void onResponse(Call<List<CouncilMan>> call, Response<List<CouncilMan>> response) {
                if (response.isSuccessful()){
                    Store.saveCouncilman(context,response.body());
                }else{
                    FormattedResp error = ParserError.parse(response);
                    String errorMessage = MessageManager.getMessage(context, error.code());
                    mView.showError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<CouncilMan>> call, Throwable t) {
                Log.e("MainPresenter",t.getCause()+"" );
            }
        });

    }

    @Override
    public void saveProposals(final Context context) {
        Council defaultCouncil = Store.getDefaultCouncil(context);
        String id = String.valueOf(defaultCouncil.id);
        Call<List<Proposal>> proposalCall = ccService.getProposal(id);
        proposalCall.enqueue(new Callback<List<Proposal>>() {
            @Override
            public void onResponse(Call<List<Proposal>> call, Response<List<Proposal>> response) {
                if (response.isSuccessful()){
                    /**
                     * Save in the Database
                     **/

                    Log.e("MainPresenter","Proposal Database in progress.....");
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



    @Override
    public void saveCommissions(final Context context) {
        Council defaultCouncil = Store.getDefaultCouncil(context);
        Call<List<Commission>> commissionCall = ccService.getCommissions(defaultCouncil.id()+"");
        commissionCall.enqueue(new Callback<List<Commission>>() {
            @Override
            public void onResponse(Call<List<Commission>> call, Response<List<Commission>> response) {
                if (response.isSuccessful()){
                    Store.saveCommissions(context,response.body());
                }else{
                    FormattedResp error = ParserError.parse(response);
                    String errorMessage = MessageManager.getMessage(context, error.code());
                    mView.showError(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<Commission>> call, Throwable t) {
                Log.e("MainPresenter.class",String.valueOf(t.getCause()));
            }
        });
    }


}
