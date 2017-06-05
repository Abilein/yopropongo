package com.xiberty.propongo.councils.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.fragments.ToolbarBaseFragment;
import com.xiberty.propongo.contrib.utils.UIUtils;

import butterknife.ButterKnife;


public class DirectiveFragment extends ToolbarBaseFragment implements DirectiveContract.View{
    private static final String TAG = DirectiveFragment.class.getSimpleName();
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
        rootView= inflater.inflate(R.layout.fragment_menu, container, false);
        this.savedInstanceState = savedInstanceState;

        context = rootView.getContext();
        ButterKnife.bind(this, rootView);

        setHeader(rootView, getString(R.string.menu_directive).toUpperCase(), "CONCEJO MUNICIPAL DE LA PAZ");


//        CouncilService service = WS.makeService(CouncilService.class, Store.getCredential(context));
        return rootView;
    }


}
