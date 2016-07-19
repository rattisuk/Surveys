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
import tech.test.surveys.manager.http.HTTPManager;
import tech.test.surveys.util.Contextor;
import tech.test.surveys.view.VerticalViewPager;

/**
 * Created by GOLF on 7/16/2016.
 */
public class MainFragment extends Fragment {

    VerticalViewPager viewPagerVertical;
    SurveyScreenPagerAdapter surveyViewPagerAdapter;
    CirclePageIndicator pageIndicatorCircle;
    ImageButton ibRefresh;
    Button btnTakeSurvey;

    SurveyItemDao[] daos = null;
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

        if (savedInstanceState != null)
            onRestoreInstanceState(savedInstanceState);

        MainBus.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainBus.getInstance().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray("daos", daos);
        outState.putBoolean("isFirstTimeFetchData", isFirstTimeFetchData);
    }

    private void onRestoreInstanceState(Bundle savedInstanceState) {
        daos = (SurveyItemDao[]) savedInstanceState.getParcelableArray("daos");
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
        surveyViewPagerAdapter.setDaos(daos);
        viewPagerVertical.setAdapter(surveyViewPagerAdapter);

        pageIndicatorCircle = (CirclePageIndicator) rootView.findViewById(R.id.pageIndicatorCircle);
        pageIndicatorCircle.setViewPager(viewPagerVertical);
        //TODO: handle on touch/swipe inside indicator

        btnTakeSurvey = (Button) rootView.findViewById(R.id.btnTakeSurvey);
        btnTakeSurvey.setOnClickListener(takeSurveyOnclickListener);

        ibRefresh = (ImageButton) getActivity().findViewById(R.id.toolbar).findViewById(R.id.ibRefresh);
        ibRefresh.setImageResource(R.drawable.ic_refresh_white_36dp);
        ibRefresh.setOnClickListener(refreshOnClickListener);

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
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        ibRefresh.startAnimation(anim);
        ibRefresh.setClickable(false);
    }

    private void stopRefreshAnimation() {
        ibRefresh.clearAnimation();
        ibRefresh.setClickable(true);
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
                daos = response.body();
                if (surveyViewPagerAdapter != null) surveyViewPagerAdapter.setDaos(daos);
                if (viewPagerVertical != null) {
                    viewPagerVertical.getAdapter().notifyDataSetChanged();
                    if (daos.length > 0) viewPagerVertical.setCurrentItem(0);
                }
                Toast.makeText(Contextor.getInstance().getContext(), "load success : " + daos.length + " item.", Toast.LENGTH_SHORT).show();
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
