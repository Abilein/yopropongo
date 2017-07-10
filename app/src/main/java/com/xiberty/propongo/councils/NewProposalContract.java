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
    }

    public interface Presenter{
        void createProposal(Context c, String title, String summary, int for_councilman, int id_council, File file);
    }
}
