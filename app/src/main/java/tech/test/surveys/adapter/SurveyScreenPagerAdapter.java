package tech.test.surveys.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import tech.test.surveys.view.SurveyScreenItem;

/**
 * Created by GOLF on 7/17/2016.
 */
public class SurveyScreenPagerAdapter extends PagerAdapter {
    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View v = new SurveyScreenItem(container.getContext());
        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
