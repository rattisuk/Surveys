package tech.test.surveys.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.viewpagerindicator.CirclePageIndicator;

import tech.test.surveys.R;
import tech.test.surveys.adapter.SurveyScreenPagerAdapter;
import tech.test.surveys.view.VerticalViewPager;

/**
 * Created by GOLF on 7/16/2016.
 */
public class MainFragment extends Fragment {

    VerticalViewPager viewPagerVertical;
    CirclePageIndicator pageIndicatorCircle;

    public MainFragment() {
        super();
    }

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView);
        return rootView;
    }

    private void initInstances(View rootView) {

        viewPagerVertical = (VerticalViewPager) rootView.findViewById(R.id.viewPagerVertical);
        viewPagerVertical.setAdapter(new SurveyScreenPagerAdapter());

        pageIndicatorCircle = (CirclePageIndicator) rootView.findViewById(R.id.pageIndicatorCircle);
        pageIndicatorCircle.setViewPager(viewPagerVertical);
        //TODO: handle on touch/swipe inside indicator
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}
