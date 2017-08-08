package com.xiberty.propongo.councils;

import android.content.Context;


import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.councils.models.NewProposalRespse;
import com.xiberty.propongo.database.CouncilMan;
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
    public void createProposal(final Context context, String title, String description, String councilmen, int id_council, File file) {


        RequestBody titlePart = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody descriptionPart = RequestBody.create(MediaType.parse("text/plain"), description);
        RequestBody councilmenPart = RequestBody.create(MediaType.parse("text/plain"), councilmen);
        RequestBody councilPart= RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_council));
        final RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("attached_file", file.getName(), reqFile);

        Call<List<NewProposalRespse>> proposalCall = mService.createProposal(titlePart,descriptionPart,councilmenPart,councilPart,filePart, "Bearer "+ Store.getAccessToken(context));
        proposalCall.enqueue(new Callback<List<NewProposalRespse>>() {
            @Override
            public void onResponse(Call<List<NewProposalRespse>> call, Response<List<NewProposalRespse>> response) {
                if (response.isSuccessful()){
//                    List<NewProposalRespse> proposals = response.body();
                    mView.showSuccessUploadProposal();
                }else{
                    mView.showErrorUploadProposal("1 "+ response.body());
                }

            }

            @Override
            public void onFailure(Call<List<NewProposalRespse>> call, Throwable t) {
                mView.showErrorUploadProposal("2 "+t.getMessage());
            }
        });
    }

}
