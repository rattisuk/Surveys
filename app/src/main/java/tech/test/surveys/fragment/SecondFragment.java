package tech.test.surveys.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import tech.test.surveys.R;

public class SecondFragment extends Fragment {

    ImageButton ibBack;

    public SecondFragment() {
        super();
    }

    public static SecondFragment newInstance() {
        SecondFragment fragment = new SecondFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_second, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {
        ibBack = (ImageButton) getActivity().findViewById(R.id.toolbar).findViewById(R.id.ibBack);
        ibBack.setImageResource(R.drawable.ic_arrow_back_white_36dp);
        ibBack.setOnClickListener(backOnClickListener);
    }

    View.OnClickListener backOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            getFragmentManager().popBackStack();
        }
    };
}
