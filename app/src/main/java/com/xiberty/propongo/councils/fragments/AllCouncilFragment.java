package com.xiberty.propongo.councils.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.contrib.fragments.ToolbarBaseFragment;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.councils.adapters.DirectiveAdapter;
import com.xiberty.propongo.councils.models.DirectiveItem;
import com.xiberty.propongo.database.Council;
import com.xiberty.propongo.database.CouncilMan;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by growcallisaya on 10/10/17.
 */

public class AllCouncilFragment extends ToolbarBaseFragment implements AllCouncilContract.View  {

    private static final String TAG = AllCouncilFragment.class.getSimpleName();

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.placeholder_text)
    TextView placeholderText;
    @BindView(R.id.placeholder)
    LinearLayout placeholder;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    AllCouncilPresenter presenter;

    public Bundle savedInstanceState;
    public View rootView;
    public Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        this.savedInstanceState = savedInstanceState;

        context = rootView.getContext();
        ButterKnife.bind(this, rootView);

        //Default Council
        Council selectedCouncil = Store.getDefaultCouncil(context);
        setHeader(rootView, getString(R.string.menu_councils).toUpperCase(), selectedCouncil.name());

        //Setting the Directive
        setAllCouncilMen(selectedCouncil);
        CouncilService service = WS.makeService(CouncilService.class);
        presenter = new AllCouncilPresenter(this, service);


        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshAllCouncilMen();
            }
        });
        return rootView;
    }


    private void refreshAllCouncilMen() {
        presenter.getCouncils(context);
    }


    private void setAllCouncilMen(Council selectedCouncil) {

        ArrayList<CouncilMan> councilMen = Store.getCouncilman(context);

        ArrayList<DirectiveItem> councilMenItems = new ArrayList<>();
        for (CouncilMan councilMan: councilMen){
            councilMenItems.add(new DirectiveItem(councilMan,"CONSEJAL"));
        }
        DirectiveAdapter adapter = new DirectiveAdapter(context,
                councilMenItems, TAG);

        if (adapter.getCount() == 0) {
            placeholder.setVisibility(View.VISIBLE);
            placeholderText.setText("NO EXISTE DIRECTIVA");
            listView.setVisibility(View.GONE);
        } else {
            listView.setAdapter(adapter);
        }
    }
    @Override
    public void updateCouncils(List<Council> councils) {
        Store.saveCouncils(context,councils);

        for (Council council: councils) {
            if (council.name().equals(Store.getDefaultCouncil(context).name)){
                Store.setDefaultCouncil(context, council);
                Log.e("MESSAGE", "Nuevo consejo es "+council.name());
            }
        }
        setAllCouncilMen(Store.getDefaultCouncil(context));
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(context, "Error al Cargar Councils", Toast.LENGTH_SHORT).show();
        swipeContainer.setRefreshing(false);
    }
}
