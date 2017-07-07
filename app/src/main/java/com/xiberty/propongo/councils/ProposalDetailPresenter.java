package com.xiberty.propongo.councils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.councils.models.RateResponse;
import com.xiberty.propongo.database.Comment;
import com.xiberty.propongo.database.Proposal;
import com.xiberty.propongo.database.ProposalDB;
import com.xiberty.propongo.database.ProposalDB_Table;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by growcallisaya on 5/7/17.
 */

public class ProposalDetailPresenter implements ProposalDetailContract.Presenter {

    private static final String TAG = ProposalDetailPresenter.class.getSimpleName();
    private ProposalDetailActivity mView;
    private CouncilService ccService;

    public ProposalDetailPresenter(ProposalDetailActivity mView, CouncilService ccService) {
        this.mView = mView;
        this.ccService = ccService;
    }

    @Override
    public void getComments(final Context context, String id) {
        Call<List<Comment>> proposalCall = ccService.getProposalComments(id);
        proposalCall.enqueue( new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if (response.isSuccessful()){
                    List<Comment> comments = response.body();
                    mView.showComments(comments);
                }else{
                    Log.e(TAG,"OMG! Failed 'cause "+response.body());
                    Log.e(TAG,"OMG!  CODE "+response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                    Log.e(TAG,"OMG! Failed 'cause "+t.getMessage());
            }
        });
    }

    @Override
    public void setComment(Context context, String id, String comment) {
        Call<Proposal> proposalCall = ccService.commentProposal(id,comment);
    }

    @Override
    public void rateProposal(Context context, String proposalId, final String average) {
        int averageInt = (int)Double.parseDouble(average);
        Call<RateResponse> proposalCall = ccService.rateProposal(proposalId,averageInt);
        proposalCall.enqueue(new Callback<RateResponse>() {
            @Override
            public void onResponse(Call<RateResponse> call, Response<RateResponse> response) {
                if (response.isSuccessful()){
                    RateResponse rateResponse = response.body();
                    if (rateResponse.detail.equals("Ranking actualizado!")){
                        updateDatabase(average);
                        mView.updateRating(average);
                    }
                }else {
                        mView.errorRating(response.body());
                    Log.e(TAG, "OMG! No rating 'cause"+ response.body());
                }
            }

            @Override
            public void onFailure(Call<RateResponse> call, Throwable t) {
                Log.e(TAG, "OMG! Rating was not successfull");
            }
        });

    }

    private void updateDatabase(String rate) {
        ProposalDB proposal = new ProposalDB();
        proposal.setAverage(Double.parseDouble(rate));
    }
}
