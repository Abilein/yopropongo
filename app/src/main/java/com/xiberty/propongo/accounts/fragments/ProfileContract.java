package com.xiberty.propongo.accounts.fragments;


import android.content.Context;

import com.xiberty.propongo.councils.models.ProposalResponse;

import java.util.List;

public class ProfileContract {

    public interface View {
        void getProfileError(String message);

        void showProposals(List<ProposalResponse> body);

        void showProgress();

        void hideProgress();

        void showError();
    }

    public interface Presenter {
        void getMyProposals(Context context);
    }
}
