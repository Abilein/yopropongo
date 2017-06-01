package com.xiberty.propongo.council.fragments;


import android.content.Context;
import android.util.Log;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;
import com.raizlabs.android.dbflow.structure.database.transaction.ProcessModelTransaction;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;
import com.xiberty.propongo.contrib.api.FormattedResp;
import com.xiberty.propongo.contrib.api.MessageManager;
import com.xiberty.propongo.contrib.api.ParserError;
import com.xiberty.propongo.council.CouncilService;
import com.xiberty.propongo.db.AppDatabase;
import com.xiberty.propongo.db.Commission;
import com.xiberty.propongo.db.Council;
import com.xiberty.propongo.db.CouncilMan;
import com.xiberty.propongo.db.Proposal;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CouncilDetailPresenter implements CouncilDetailContract.Presenter {
    private CouncilService mService;
    private CouncilDetailFragment mView;
    private CouncilDetailRepository mRepository;
    private String TAG = CouncilDetailPresenter.class.getSimpleName();

    public CouncilDetailPresenter(CouncilDetailFragment mView, CouncilService mService) {
        this.mView = mView;
        this.mService = mService;
    }


    @Override
    public void getCouncils(final Context context) {
        Call<List<Council>> respuesta = mService.getCouncils();
        respuesta.enqueue(new Callback<List<Council>>() {
            @Override
            public void onResponse(Call<List<Council>> call, Response<List<Council>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, response.message());

                    List<Council> councils = response.body();

                    /** Saving row with DBflow **/
                    FlowManager.getDatabase(AppDatabase.class)
                            .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                                    new ProcessModelTransaction.ProcessModel<Council>() {
                                        @Override
                                        public void processModel(Council council, DatabaseWrapper wrapper) {
                                            council.save();
                                        }
                                    }).addAll(councils).build())  // add elements (can also handle multiple)
                            .error(new Transaction.Error() {
                                @Override
                                public void onError(Transaction transaction, Throwable error) {

                                }
                            })
                            .success(new Transaction.Success() {
                                @Override
                                public void onSuccess(Transaction transaction) {

                                }
                            }).build().execute();
                    /** END Saving row with DBflow **/

                } else {
                    FormattedResp error = ParserError.parse(response);
                    String errorMessage = MessageManager.getMessage(context, error.code());
                    Log.e(TAG, errorMessage);
                    mView.errorLoadCouncil(errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<Council>> call, Throwable t) {
                 Log.e(TAG, t.getCause().getMessage());
                mView.errorLoadCouncil(t.getCause().getMessage());
            }
        });
    }

    @Override
    public void getCommissions(Context context) {
        Call<List<Commission>> call = mService.getCommissions();
        Callback<List<Commission>> callback = new Callback<List<Commission>>() {
            @Override
            public void onResponse(Call<List<Commission>> call, Response<List<Commission>> response) {
                if (response.isSuccessful()){
                    Log.e(TAG,response.body()+"");
                    List<Commission> commissions = response.body();

                    /** Saving row with DBflow **/
                    FlowManager.getDatabase(AppDatabase.class)
                            .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                                    new ProcessModelTransaction.ProcessModel<Commission>() {
                                        @Override
                                        public void processModel(Commission commission, DatabaseWrapper wrapper) {
                                            commission.save();
                                        }
                                    }).addAll(commissions).build())  // add elements (can also handle multiple)
                            .error(new Transaction.Error() {
                                @Override
                                public void onError(Transaction transaction, Throwable error) {

                                }
                            })
                            .success(new Transaction.Success() {
                                @Override
                                public void onSuccess(Transaction transaction) {

                                }
                            }).build().execute();

                    /** END Saving row with DBflow **/
                }else{
                    Log.e(TAG,response.message());
                }
            }
            @Override
            public void onFailure(Call<List<Commission>> call, Throwable t) {
                Log.e(TAG,t.getMessage());
            }
        };
        call.enqueue(callback);

    }

    @Override
    public void getProposals(Context context) {
        Call<List<Proposal>> call = mService.getProposal();
        Callback<List<Proposal>> callback = new Callback<List<Proposal>>() {
            @Override
            public void onResponse(Call<List<Proposal>> call, Response<List<Proposal>> response) {
                if (response.isSuccessful()){
                    Log.e(TAG,response.body()+"");
                    List<Proposal> proposals = response.body();

                    /** Saving row with DBflow **/
                    FlowManager.getDatabase(AppDatabase.class)
                            .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                                    new ProcessModelTransaction.ProcessModel<Proposal>() {
                                        @Override
                                        public void processModel(Proposal proposal, DatabaseWrapper wrapper) {
                                            proposal.save();
                                        }
                                    }).addAll(proposals).build())  // add elements (can also handle multiple)
                            .error(new Transaction.Error() {
                                @Override
                                public void onError(Transaction transaction, Throwable error) {

                                }
                            })
                            .success(new Transaction.Success() {
                                @Override
                                public void onSuccess(Transaction transaction) {

                                }
                            }).build().execute();

                    /** END Saving row with DBflow **/
                }else{
                    Log.e(TAG,response.message());
                }


            }

            @Override
            public void onFailure(Call<List<Proposal>> call, Throwable t) {

            }
        };
        call.enqueue(callback);

    }

    @Override
    public void getCouncilMen(Context context) {
        Call<List<CouncilMan>> call = mService.getCouncilMan();
        Callback<List<CouncilMan>> callback = new Callback<List<CouncilMan>>() {
            @Override
            public void onResponse(Call<List<CouncilMan>> call, Response<List<CouncilMan>> response) {
                if (response.isSuccessful()){
                    Log.e(TAG,response.body()+"");
                    List<CouncilMan> councilmen = response.body();
                    /** Saving row with DBflow **/
                    FlowManager.getDatabase(AppDatabase.class)
                            .beginTransactionAsync(new ProcessModelTransaction.Builder<>(
                                    new ProcessModelTransaction.ProcessModel<CouncilMan>() {
                                        @Override
                                        public void processModel(CouncilMan councilMan, DatabaseWrapper wrapper) {
                                            councilMan.save();
                                        }
                                    }).addAll(councilmen).build())  // add elements (can also handle multiple)
                            .error(new Transaction.Error() {
                                @Override
                                public void onError(Transaction transaction, Throwable error) {

                                }
                            })
                            .success(new Transaction.Success() {
                                @Override
                                public void onSuccess(Transaction transaction) {

                                }
                            }).build().execute();

                    /** END Saving row with DBflow **/
                }else{
                    Log.e(TAG,response.message());
                }
            }

            @Override
            public void onFailure(Call<List<CouncilMan>> call, Throwable t) {

            }
        };
        call.enqueue(callback);
    }
}
