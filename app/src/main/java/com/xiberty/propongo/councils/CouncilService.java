package com.xiberty.propongo.councils;

import com.xiberty.propongo.Constants;
import com.xiberty.propongo.database.Comment;
import com.xiberty.propongo.database.Commission;
import com.xiberty.propongo.database.Council;
import com.xiberty.propongo.database.CouncilMan;
import com.xiberty.propongo.database.Proposal;

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
import retrofit2.http.Query;


public interface CouncilService {

    @GET(Constants.COUNCIL_ENDPOINT)
    Call<List<Council>> getCouncils();

    @GET(Constants.COMISSIONS_ENDPOINT)
    Call<List<Commission>> getCommissions(@Path("pk") String pk);

    @GET(Constants.COUNCILMEN_ENDPOINT)
    Call<List<CouncilMan>> getCouncilMan(@Path("pk") String pk);

    @GET(Constants.COUNCILMEN_INBOX_ENDPOINT)
    Call<Proposal> getCouncilMenInbox();

    @GET(Constants.PROPOSALS_ENDPOINT)
    Call<List<Proposal>> getProposal(@Path("pk") String pk);

    @Multipart
    @POST(Constants.PROPOSALS_ENDPOINT)
    Call<Proposal> createProposal(@Part("title") RequestBody title,
                                  @Part("summary") RequestBody summary,
                                  @Part("for_councilman") RequestBody for_councilman,
                                  @Part MultipartBody.Part attached_file);


    @GET(Constants.PROPOSAL_ROOT_ENDPOINT+"{pk}/up/")
    Call<Proposal> activeProposal(@Path("pk") String pk);

    @GET(Constants.PROPOSAL_ROOT_ENDPOINT+"{pk}/halt/")
    Call<Proposal> deleteProposal(@Path("pk") String pk);

    @GET(Constants.PROPOSAL_ROOT_ENDPOINT+"{pk}/comments/")
    Call<List<Comment>> getProposalComments(@Path("pk") String pk);

    @FormUrlEncoded
    @POST(Constants.PROPOSAL_ROOT_ENDPOINT+"{pk}/rate")
    Call<Proposal> rateProposal(@Field("pk") String pk,
                                @Field("rating") String rating);

    @FormUrlEncoded
    @POST(Constants.PROPOSAL_ROOT_ENDPOINT+"{pk}/comment")
    Call<Proposal> commentProposal(@Field("pk") String pk,
                                   @Field("comment") String comment);

    @GET(Constants.CONTACTS_ENDPOINT)
    Call<Proposal> getContacts();

    @POST(Constants.CONTACTS_ENDPOINT)
    Call<Proposal> createContact(@Field("name") String name,
                                 @Field("email") String email,
                                 @Field("message") String message);

}