package com.xiberty.propongo.councils;

import android.content.Context;

import java.io.File;

/**
 * Created by growcallisaya on 8/7/17.
 */

public class NewProposalContract {

    public interface View{

        void showSuccessUploadProposal();
        void showErrorUploadProposal(String error);

        void hideProgress();
        void showProgress();
    }

    public interface Presenter{
        void createProposal(Context c, String title, String desc, String councilmen, int id_council, File file);
    }
}
