package com.xiberty.propongo.council.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.xiberty.propongo.R;
import com.xiberty.propongo.accounts.fragments.AboutFragment;
import com.xiberty.propongo.accounts.fragments.ProfileFragment;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.contrib.utils.UIUtils;
import com.xiberty.propongo.council.CouncilService;
import com.xiberty.propongo.council.adapters.PageAdapter;
import com.xiberty.propongo.council.responses.Council;
import com.xiberty.propongo.db.Council_T;
import com.xiberty.propongo.db.Council_T_Table;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by growcallisaya on 3/5/17.
 */

public class CouncilDetailFragment extends Fragment implements CouncilDetailContract.View{
    private static final String TAG = CouncilDetailFragment.class.getSimpleName();
    private SparseArray<UIUtils.PageItem> pages = new SparseArray<UIUtils.PageItem>();

    // pages positions
    public static int DIRECTIVE_FRAGMENT = 0;
    public static int COMMISSIONS_FRAGMENT = 1;

    public Context context;
    //References
    @BindView(R.id.toolbar) Toolbar toolbar;





    public CouncilDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    CouncilDetailPresenter presenter;
    Bundle savedInstanceState;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_council_detail, container, false);
        this.savedInstanceState = savedInstanceState;
        context = rootView.getContext();

        ButterKnife.bind(this, rootView);
        setToolbar();

        CouncilService service = WS.makeService(CouncilService.class, Store.getCredential(context));
        presenter = new CouncilDetailPresenter(this, service);
        presenter.getCouncils(context);
        return rootView;
    }

    private void setToolbar() {
        toolbar.setBackground(getResources().getDrawable(R.drawable.background_toolbar_invisible));
        toolbar.setVisibility(View.GONE);
        toolbar.setTitle("");
    }


    /**
     * Methods from CouncilDetailContract.View
     * **/
    @Override
    public void showCouncils(List<Council> councils) {



        //Creating Directive Fragment
        DirectiveFragment directiveFragment = new DirectiveFragment();

        if (savedInstanceState == null) {
            //SET TABS
            pages.append(CouncilDetailFragment.COMMISSIONS_FRAGMENT, new UIUtils.PageItem(
//                    new CommissionFragment(),
                    new ProfileFragment(),
                    getResources().getString(R.string.tab_commissions)));
            pages.append(CouncilDetailFragment.DIRECTIVE_FRAGMENT, new UIUtils.PageItem(
                    new AboutFragment(),
                    getResources().getString(R.string.tab_directive)));
            //ADAPTING TABS
            PageAdapter adapter = new PageAdapter(pages, getChildFragmentManager());
            PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) rootView.findViewById(R.id.tabs);
            ViewPager paginator = (ViewPager) rootView.findViewById(R.id.pager);
            paginator.setAdapter(adapter);
            tabs.setViewPager(paginator);
        }
        for (int i = 0; i< councils.size();i++){
            Council council= councils.get(i);
            Log.e("Council",council.getName());
        }

    }

    private Bundle makeDirectiveBundle(Council council){
        ArrayList<Integer> keys = new ArrayList<>();
        ArrayList<String> values = new ArrayList<>();
        if(council.president != 0){
            keys.add(council.president);
            values.add(getString(R.string.label_president));
        }
        if(council.vice_president != 0){
            values.add(getString(R.string.label_vice_president));
            keys.add(council.vice_president);
        }
        if(council.secretary != 0){
            keys.add(council.secretary);
            values.add(getString(R.string.label_secretary));
        }
        if(council.vocal_a != 0) {
            values.add(getString(R.string.label_vocal));
            keys.add(council.vocal_a);
        }

        if(council.vocal_b != 0){
            values.add(getString(R.string.label_vocal));
            keys.add(council.vocal_b);
        }
        Bundle bundle = new Bundle();
        bundle.putIntegerArrayList("position", keys);
        bundle.putStringArrayList("label", values);
        return bundle;


    }
    @Override
    public void errorLoadCouncil(String errorMessage) {
        Toast.makeText(context, "ERROR: "+errorMessage, Toast.LENGTH_SHORT).show();
    }
}
