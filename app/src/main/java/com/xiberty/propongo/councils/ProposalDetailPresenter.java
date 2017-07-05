package com.xiberty.propongo.councils;

import android.content.Context;
import android.util.Log;

import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.database.Comment;

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
//                    FormattedResp error = ParserError.parse(response);
//                    String errorMessage = MessageManager.getMessage(context, error.code());
//                    mView.showErrorComments(errorMessage);
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
}
