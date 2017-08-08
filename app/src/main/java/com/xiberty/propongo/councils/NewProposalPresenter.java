package com.xiberty.propongo.councils;

import android.content.Context;


import com.facebook.stetho.inspector.protocol.module.Network;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.councils.models.NewProposalRespse;
import com.xiberty.propongo.councils.models.PersonResponse;
import com.xiberty.propongo.database.Commission;
import com.xiberty.propongo.database.CouncilMan;
import com.xiberty.propongo.database.Proposal;
import com.xiberty.propongo.database.ProposalDB;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by growcallisaya on 8/7/17.
 */

public class NewProposalPresenter implements NewProposalContract.Presenter {
    private NewProposalActivity mView;
    private CouncilService mService;
    public NewProposalPresenter(NewProposalActivity mView, CouncilService mService) {
        this.mView = mView;
        this.mService = mService;
    }

    @Override
    public void createProposal(final Context context, String title, String summary, int for_councilman, int id_council, File file) {
        RequestBody titlePart = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody summaryPart = RequestBody.create(MediaType.parse("text/plain"), summary);
        RequestBody for_councilmanPart = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(for_councilman));
        RequestBody councilPart= RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_council));
        final RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("attached_file", file.getName(), reqFile);

        Call<NewProposalRespse> proposalCall = mService.createProposal(titlePart,summaryPart,for_councilmanPart,councilPart,filePart);
        proposalCall.enqueue(new Callback<NewProposalRespse>() {
            @Override
            public void onResponse(Call<NewProposalRespse> call, Response<NewProposalRespse> response) {
                if (response.isSuccessful()){

                    /**Add to the DB**/
                    NewProposalRespse proposal = response.body();
                    ProposalDB proposalDB = new ProposalDB();
                    proposalDB.id = proposal.id;
                    proposalDB.title = proposal.title;
                    proposalDB.description = proposal.description;
//                    proposalDB.commissions = proposal.commission;
                    proposalDB.commissions = "1";
                    String councilManID = CouncilMan.getCouncilmanByName(context,proposal.receiver.full_name);
                    proposalDB.councilmen=councilManID;


                    proposalDB.views = 0;
                    proposalDB.average = 0;
//                    proposalDB.creation_date= proposal.getCreation_date();
                    proposalDB.datetime= "9999-09-09";
                    proposalDB.council = proposal.council.id;
                    proposalDB.save();

                    mView.showSuccessUploadProposal();
                }else{
                    mView.showErrorUploadProposal("1 "+ response.body());
                }

            }

            @Override
            public void onFailure(Call<NewProposalRespse> call, Throwable t) {
                mView.showErrorUploadProposal("2 "+t.getMessage());
            }
        });
    }

}
