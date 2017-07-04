package com.xiberty.propongo.councils.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiberty.propongo.R;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.councils.adapters.DirectiveAdapter;
import com.xiberty.propongo.councils.models.DirectiveItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DirectiveCommissionFragment extends Fragment {
    InboxPresenter presenter;
    View rootView = null;
    Context context;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.placeholder_text)
    TextView placeholderText;
    @BindView(R.id.placeholder)
    LinearLayout placeholder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_council_proposals, container, false);
        ButterKnife.bind(this, rootView);

        context = rootView.getContext();
        Bundle bundle = getArguments();
        if (!bundle.isEmpty()) {
            Gson gson = new Gson();
            String directiveStr = bundle.getString("Directive");
            ArrayList<DirectiveItem> directiveItems = gson.fromJson(directiveStr, new TypeToken<ArrayList<DirectiveItem>>() {
            }.getType());
            setDirective(directiveItems);
        }

        return rootView;
    }

    private void setDirective(ArrayList<DirectiveItem> items) {
        DirectiveAdapter adapter = new DirectiveAdapter(context, items);
        if (adapter.getCount() == 0) {
            placeholder.setVisibility(View.VISIBLE);
            placeholderText.setText("NO EXISTE DIRECTIVA");
            listView.setVisibility(View.GONE);
        } else {
            listView.setAdapter(adapter);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
