package tech.test.surveys.manager;

import android.content.Context;

import tech.test.surveys.dao.SurveyItemDao;
import tech.test.surveys.util.Contextor;

/**
 * Created by GOLF on 7/20/2016.
 */
public class SurveyListManager {

    private static SurveyListManager instance;

    public static SurveyListManager getInstance() {
        if (instance == null)
            instance = new SurveyListManager();
        return instance;
    }

    private Context mContext;
    private SurveyItemDao[] data;

    private SurveyListManager() {
        mContext = Contextor.getInstance().getContext();
    }

    public void setData(SurveyItemDao[] data) {
        this.data = data;
    }

    public SurveyItemDao[] getData() {
        return data;
    }
}
