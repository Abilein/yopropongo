package com.xiberty.propongo.council.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xiberty.propongo.R;

import java.util.ArrayList;


public class DirectiveFragment  extends Fragment {
    private static final String TAG = DirectiveFragment.class.getSimpleName();


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_directive, container, false);
        Context context = getActivity().getBaseContext();

        if (rootView != null && getArguments()!=null) {

            Bundle args = getArguments();

//            ArrayList<Integer> position = args.getIntegerArrayList("position");
//            ArrayList<String> label = args.getStringArrayList("label");

//            if( position != null && label !=null && position.size() == label.size() ){
                //TODO Create Directive to set in the List
//                DirectiveAdapter adapter = new DirectiveAdapter(getActivity().getApplicationContext(),
//                        CouncilMan.makeDirective(context, position, label));
//                ListView listView = (ListView) rootView.findViewById(R.id.listView);
//                listView.setAdapter(adapter);
//            }
        }
        return rootView;
    }

}
