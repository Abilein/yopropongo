package com.xiberty.propongo.councils.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.fragments.ToolbarBaseFragment;
import com.xiberty.propongo.contrib.utils.UIUtils;
import com.xiberty.propongo.councils.adapters.DirectiveAdapter;
import com.xiberty.propongo.councils.models.DirectiveItem;
import com.xiberty.propongo.database.Council;
import com.xiberty.propongo.database.CouncilMan;

import java.util.ArrayList;
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


        return rootView;
    }

    private void setDirective(Council selectedCouncil) {
        DirectiveAdapter adapter = new DirectiveAdapter(getActivity().getApplicationContext(),
                makedirective(selectedCouncil));
        if (adapter.getCount() == 0) {
            placeholder.setVisibility(View.VISIBLE);
            placeholderText.setText("NO EXISTE DIRECTIVA");
            listView.setVisibility(View.GONE);
        } else {
            listView.setAdapter(adapter);
        }
    }

    private ArrayList<DirectiveItem> makedirective(Council selectedCouncil) {
        ArrayList<DirectiveItem> directive = new ArrayList<>();
        if (selectedCouncil.president != 0) {
            CouncilMan president = getCouncilMan(selectedCouncil.president);
            if (president != null)
                directive.add(new DirectiveItem(president, "presidente"));
        }
        if (selectedCouncil.vice_president != 0) {
            CouncilMan vicepresident = getCouncilMan(selectedCouncil.vice_president);
            if (vicepresident != null)
                directive.add(new DirectiveItem(vicepresident, "Vice Presidente"));
        }
        if (selectedCouncil.secretary != 0) {
            CouncilMan secretary = getCouncilMan(selectedCouncil.secretary);
            if (secretary != null)
                directive.add(new DirectiveItem(secretary, "Secretario"));
        }

        return directive;
    }

    private CouncilMan getCouncilMan(int ID) {
        List<CouncilMan> councilMen = Store.getCouncilman(context);
        for (CouncilMan councilMan : councilMen) {
            if (councilMan.getId() == ID)
                return councilMan;
        }
        return null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
