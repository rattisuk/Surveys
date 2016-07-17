package tech.test.surveys.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.viewpagerindicator.CirclePageIndicator;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.test.surveys.R;
import tech.test.surveys.adapter.SurveyScreenPagerAdapter;
import tech.test.surveys.dao.SurveyItemDao;
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
        surveyViewPagerAdapter = new SurveyScreenPagerAdapter();
        viewPagerVertical.setAdapter(surveyViewPagerAdapter);

        pageIndicatorCircle = (CirclePageIndicator) rootView.findViewById(R.id.pageIndicatorCircle);
        pageIndicatorCircle.setViewPager(viewPagerVertical);
        //TODO: handle on touch/swipe inside indicator

        loadSurveysData();

    }

    private void loadSurveysData() {
        Call<SurveyItemDao[]> call = HTTPManager.getInstance().getService().loadSurveyList("6eebeac3dd1dc9c97a06985b6480471211a777b39aa4d0e03747ce6acc4a3369");
        call.enqueue(new Callback<SurveyItemDao[]>() {
            @Override
            public void onResponse(Call<SurveyItemDao[]> call, Response<SurveyItemDao[]> response) {
                if (response.isSuccessful()) {
                    SurveyItemDao[] daos = response.body();
                    surveyViewPagerAdapter.setDaos(daos);
                    viewPagerVertical.getAdapter().notifyDataSetChanged();
                    Toast.makeText(Contextor.getInstance().getContext(), "load success : " + daos.length + " item.", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        Toast.makeText(Contextor.getInstance().getContext(), response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onFailure(Call<SurveyItemDao[]> call, Throwable t) {
                Toast.makeText(Contextor.getInstance().getContext(), t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
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
