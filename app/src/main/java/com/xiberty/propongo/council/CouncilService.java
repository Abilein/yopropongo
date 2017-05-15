package com.xiberty.propongo.council;

import com.xiberty.propongo.Constants;
import com.xiberty.propongo.council.responses.Commission;
import com.xiberty.propongo.council.responses.Council;
import com.xiberty.propongo.council.responses.CouncilMan;
import com.xiberty.propongo.council.responses.Proposal;
import com.xiberty.propongo.credentials.responses.UserProfile;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by growcallisaya on 2/5/17.
 */

public interface CouncilService {

    @GET(Constants.COUNCIL_ENDPOINT)
    Call<List<Council>> getCouncils();

    @GET(Constants.COMISSIONS_ENDPOINT)
    Call<Commission> getCommissions();

    @GET(Constants.COUNCILMEN_ENDPOINT)
    Call<CouncilMan> getCouncilMan();

    @GET(Constants.COUNCILMEN_INBOX_ENDPOINT)
    Call<Proposal> getCouncilMenInbox();

    @GET(Constants.PROPOSALS_ENDPOINT)
    Call<Proposal> getProposal();

    @Multipart
    @POST(Constants.PROPOSALS_ENDPOINT)
    Call<Proposal> createProposal(@Part("title") RequestBody title,
                                  @Part("summary") RequestBody summary,
                                  @Part("for_councilman") RequestBody for_councilman,
                                  @Part MultipartBody.Part attached_file);


    @GET(Constants.PROPOSALS_ENDPOINT+"/{pk}/up")
    Call<Proposal> activeProposal(@Path("pk") String pk);

    @GET(Constants.PROPOSALS_ENDPOINT+"/{pk}/halt")
    Call<Proposal> deleteProposal(@Path("pk") String pk);

    @GET(Constants.PROPOSALS_ENDPOINT+"/{pk}/comments")
    Call<Proposal> getProposalComments(@Path("pk") String pk);

    @FormUrlEncoded
    @POST(Constants.PROPOSALS_ENDPOINT+"/{pk}/rate")
    Call<Proposal> rateProposal(@Field("pk") String pk,
                                @Field("rating") String rating);

    @FormUrlEncoded
    @POST(Constants.PROPOSALS_ENDPOINT+"/{pk}/comment")
    Call<Proposal> commentProposal(@Field("pk") String pk,
                                   @Field("comment") String comment);

    @GET(Constants.CONTACTS_ENDPOINT)
    Call<Proposal> getContacts();

    @POST(Constants.CONTACTS_ENDPOINT)
    Call<Proposal> createContact(@Field("name") String name,
                                 @Field("email") String email,
                                 @Field("message") String message);

}
