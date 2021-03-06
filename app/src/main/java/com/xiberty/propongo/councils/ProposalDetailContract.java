package com.xiberty.propongo.councils;

import android.content.Context;

import com.xiberty.propongo.councils.models.DetailResponse;
import com.xiberty.propongo.database.Comment;

import java.util.List;

/**
 * Created by growcallisaya on 5/7/17.
 */

public class ProposalDetailContract {

    public interface Presenter{
        void getComments(Context context, String id);
        void getViews(Context context, String proposalId);

        void setComment(Context context,String id, String comment);
        void rateProposal(Context context, String proposalId, int average);
    }

    public interface View{
        void showComments(List<Comment> comments);
        void showErrorComments (String error);
        void updateRating(float average);

        void errorRating(DetailResponse body);

        void showDetailResponse(String detail);

        void showErrorToMakeComment(String error
        );

        void updateViewers(int view);
    }
}
