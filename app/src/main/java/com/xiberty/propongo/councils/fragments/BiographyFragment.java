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
import android.widget.TextView;
import com.xiberty.propongo.R;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.database.CouncilMan;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BiographyFragment extends Fragment {
    private static final String TAG = BiographyFragment.class.getSimpleName();
    CouncilService mService;
    InboxPresenter presenter;
    View rootView = null;
    @BindView(R.id.textView_Bio)
    TextView textViewBio;

    Context context;

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
        rootView = inflater.inflate(R.layout.fragment_biography, container, false);
        ButterKnife.bind(this, rootView);

        context = rootView.getContext();
        Bundle bundle = getArguments();
        if (!bundle.isEmpty()){
            String about = bundle.getString("About");
            textViewBio.setText(about);
        }
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
