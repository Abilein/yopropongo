package com.xiberty.propongo.councils;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.councils.models.DetailResponse;
import com.xiberty.propongo.councils.models.ViewResponse;
import com.xiberty.propongo.database.Comment;
import com.xiberty.propongo.database.Proposal;
import com.xiberty.propongo.database.ProposalDB;

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
//        Call<List<Comment>> proposalCall = ccService.getProposalComments(id,"Bearer "+ Store.getAccessToken(context));
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
    public void getViews(Context context, String pk) {
        Call<ViewResponse> proposalCall = ccService.getView(pk);
        proposalCall.enqueue(new Callback<ViewResponse>() {
            @Override
            public void onResponse(Call<ViewResponse> call, Response<ViewResponse> response) {
                if (response.isSuccessful()){
                    ViewResponse viewResponse = response.body();
                    mView.updateViewers(viewResponse.views);
                }else{
                    Log.e(TAG,"OMG! Error viewers 'cause "+response.body());
                }

            }

            @Override
            public void onFailure(Call<ViewResponse> call, Throwable t) {
                Log.e(TAG,"OMG! Error viewers fatality 'cause "+t.getMessage());
            }
        });
    }

    @Override
    public void setComment(final Context context, final String id, String comment) {
        Call<DetailResponse> proposalCall = ccService.
                commentProposal(id,comment);
        proposalCall.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful()){
                    DetailResponse detailResponse = response.body();
                    mView.showDetailResponse("Comentario exitoso!");
                    getComments(context,id);
                }else{
                    mView.showErrorToMakeComment("Error al crear Comentario");
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void rateProposal(Context context, String proposalId, final String average) {
        int averageInt = (int)Double.parseDouble(average);
        Call<DetailResponse> proposalCall = ccService.rateProposal(proposalId,averageInt);
        proposalCall.enqueue(new Callback<DetailResponse>() {
            @Override
            public void onResponse(Call<DetailResponse> call, Response<DetailResponse> response) {
                if (response.isSuccessful()){
                    DetailResponse detailResponse = response.body();
                    updateDatabase(average);
                    mView.updateRating(average);
                }else {
                        mView.errorRating(response.body());
                    Log.e(TAG, "OMG! No rating 'cause"+ response.body());
                }
            }

            @Override
            public void onFailure(Call<DetailResponse> call, Throwable t) {
                Log.e(TAG, "OMG! Rating was not successfull");
            }
        });

    }

    private void updateDatabase(String rate) {
        ProposalDB proposal = new ProposalDB();
        proposal.setAverage(Double.parseDouble(rate));
    }


}
