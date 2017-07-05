package com.xiberty.propongo.councils;

import android.content.Context;

import com.xiberty.propongo.database.Comment;

import java.util.List;

/**
 * Created by growcallisaya on 5/7/17.
 */

public class ProposalDetailContract {

    public interface Presenter{
        void getComments(Context context, String id);
    }

    public interface View{
        void showComments(List<Comment> comments);
        void showErrorComments (String error);
    }
}
