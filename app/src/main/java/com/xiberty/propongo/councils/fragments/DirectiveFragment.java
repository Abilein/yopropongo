package com.xiberty.propongo.councils.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiberty.propongo.R;
import com.xiberty.propongo.accounts.MainActivity;
import com.xiberty.propongo.accounts.MainPresenter;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.contrib.fragments.ToolbarBaseFragment;
import com.xiberty.propongo.contrib.utils.UIUtils;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.councils.adapters.DirectiveAdapter;
import com.xiberty.propongo.database.Council;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class DirectiveFragment extends ToolbarBaseFragment implements DirectiveContract.View {
    private static final String TAG = DirectiveFragment.class.getSimpleName();
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.placeholder_text)
    TextView placeholderText;
    @BindView(R.id.placeholder)
    LinearLayout placeholder;
    @BindView(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;
    private SparseArray<UIUtils.PageItem> pages = new SparseArray<UIUtils.PageItem>();

    // pages positions
    public static int DIRECTIVE_FRAGMENT = 0;
    public static int COMMISSIONS_FRAGMENT = 1;

    public Context context;

    //References
    public DirectiveFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    DirectivePresenter presenter;

    Bundle savedInstanceState;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        this.savedInstanceState = savedInstanceState;

        context = rootView.getContext();
        ButterKnife.bind(this, rootView);

        //Default Council
        Council selectedCouncil = Store.getDefaultCouncil(context);
        setHeader(rootView, getString(R.string.menu_directive).toUpperCase(), selectedCouncil.name());

        //Setting the Directive
        setDirective(selectedCouncil);
        CouncilService service = WS.makeService(CouncilService.class);
        presenter = new DirectivePresenter(this, service);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshDirective();
            }
        });
        return rootView;
    }

    private void refreshDirective() {
        presenter.getCouncils(context);
    }


    private void setDirective(Council selectedCouncil) {
        DirectiveAdapter adapter = new DirectiveAdapter(context,
                selectedCouncil.makedirective(context), TAG);
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
        setDirective(Store.getDefaultCouncil(context));
        swipeContainer.setRefreshing(false);
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(context, "Error al Cargar Councils", Toast.LENGTH_SHORT).show();
        swipeContainer.setRefreshing(false);
    }
}
