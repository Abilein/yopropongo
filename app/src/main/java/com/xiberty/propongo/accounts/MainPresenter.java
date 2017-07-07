package com.xiberty.propongo.accounts;

import android.content.Context;
import android.util.Log;

import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.Excepts;
import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.OAuthCollection;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.councils.CouncilService;
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
    public void getCouncils(final Context context) {

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
                Log.e("MainPresenter",t.getCause()+"" );
            }
        });
    }

    @Override
    public void getCouncilmen(final Context context) {
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
    public void getProposals(final Context context) {
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



    @Override
    public void getCommissions(final Context context) {
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
