package tech.test.surveys.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import com.squareup.otto.Subscribe;
import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.test.surveys.R;
import tech.test.surveys.adapter.SurveyScreenPagerAdapter;
import tech.test.surveys.dao.SurveyItemDao;
import tech.test.surveys.eventbus.BusEventSurveyDataLoaded;
import tech.test.surveys.eventbus.MainBus;
import tech.test.surveys.manager.SurveyListManager;
import tech.test.surveys.manager.http.HTTPManager;
import tech.test.surveys.util.Contextor;
import tech.test.surveys.view.UntouchableCirclePageIndicator;
import tech.test.surveys.view.VerticalViewPager;

/**
 * Created by GOLF on 7/16/2016.
 */
public class MainFragment extends Fragment {

    static int currentPagePosition = 0;

    VerticalViewPager viewPagerVertical;
    SurveyScreenPagerAdapter surveyViewPagerAdapter;
    UntouchableCirclePageIndicator pageIndicatorCircle;
    ImageButton ibRefresh;
    Button btnTakeSurvey;

    SurveyListManager surveyListManager;
    boolean isLoading = false;
    boolean isFirstTimeFetchData = true;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        surveyListManager = SurveyListManager.getInstance();

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);

        MainBus.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        currentPagePosition = viewPagerVertical.getCurrentItem();
    }

    @Override
    public void onResume() {
        super.onResume();
        viewPagerVertical.setCurrentItem(currentPagePosition);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainBus.getInstance().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBundle("surveyListManager", surveyListManager.onSaveInstanceState());
        outState.putBoolean("isLoading", isLoading);
        outState.putBoolean("isFirstTimeFetchData", isFirstTimeFetchData);
        if (viewPagerVertical != null) currentPagePosition = viewPagerVertical.getCurrentItem();
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        surveyListManager.onRestoreInstanceState(savedInstanceState.getBundle("surveyListManager"));
        isLoading = savedInstanceState.getBoolean("isLoading");
        isFirstTimeFetchData = savedInstanceState.getBoolean("isFirstTimeOpen");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initInstances(rootView, savedInstanceState);
        return rootView;
    }

    private void initInstances(View rootView, Bundle savedInstanceState) {

        viewPagerVertical = (VerticalViewPager) rootView.findViewById(R.id.viewPagerVertical);
        surveyViewPagerAdapter = new SurveyScreenPagerAdapter();
        viewPagerVertical.setAdapter(surveyViewPagerAdapter);

        pageIndicatorCircle = (UntouchableCirclePageIndicator) rootView.findViewById(R.id.pageIndicatorCircle);
        pageIndicatorCircle.setViewPager(viewPagerVertical);

        btnTakeSurvey = (Button) rootView.findViewById(R.id.btnTakeSurvey);
        btnTakeSurvey.setOnClickListener(takeSurveyOnclickListener);

        ibRefresh = (ImageButton) getActivity().findViewById(R.id.toolbar).findViewById(R.id.ibRefresh);
        ibRefresh.setImageResource(R.drawable.ic_refresh_white_36dp);
        ibRefresh.setOnClickListener(refreshOnClickListener);
        if (isLoading) startRefreshAnimation();

        if (savedInstanceState == null && isFirstTimeFetchData) {
            loadSurveysData();
            isFirstTimeFetchData = false;
        }

    }

    private void loadSurveysData() {
        startRefreshAnimation();

        Call<SurveyItemDao[]> call = HTTPManager.getInstance().getService().loadSurveyList("6eebeac3dd1dc9c97a06985b6480471211a777b39aa4d0e03747ce6acc4a3369");
        call.enqueue(new Callback<SurveyItemDao[]>() {
            @Override
            public void onResponse(Call<SurveyItemDao[]> call, Response<SurveyItemDao[]> response) {
                MainBus.getInstance().post(new BusEventSurveyDataLoaded(true, response));
            }

            @Override
            public void onFailure(Call<SurveyItemDao[]> call, Throwable t) {
                MainBus.getInstance().post(new BusEventSurveyDataLoaded(false, null));
                Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void startRefreshAnimation() {
        isLoading = true;
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        ibRefresh.startAnimation(anim);
        ibRefresh.setClickable(false);
    }

    private void stopRefreshAnimation() {
        isLoading = false;
        if (ibRefresh != null) {
            ibRefresh.clearAnimation();
            ibRefresh.setClickable(true);
        }
    }

    private View.OnClickListener refreshOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            loadSurveysData();
        }
    };

    private View.OnClickListener takeSurveyOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            Fragment showingFragment = fragmentManager.findFragmentById(R.id.contentContainer);
            if (!(showingFragment instanceof SecondFragment)) {
                fragmentManager.beginTransaction()
                        .replace(R.id.contentContainer, SecondFragment.newInstance())
                        .addToBackStack(null)
                        .commit();
            }
        }
    };

    @Subscribe
    public void BusEventSurveyDataLoaded(BusEventSurveyDataLoaded event) {
        stopRefreshAnimation();
        if (event.isSuccess()) {
            Response<SurveyItemDao[]> response = event.getResponse();
            if (response.isSuccessful()) {
                surveyListManager.setData(response.body());
                currentPagePosition = 0;
                if (viewPagerVertical != null) {
                    viewPagerVertical.getAdapter().notifyDataSetChanged();
                    if (surveyListManager.getData().length > 0) viewPagerVertical.setCurrentItem(0);
                }
                Toast.makeText(Contextor.getInstance().getContext(), "load success : " + surveyListManager.getData().length + " item.", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
