package tech.test.surveys.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import tech.test.surveys.dao.SurveyItemDao;
import tech.test.surveys.util.Contextor;

/**
 * Created by GOLF on 7/20/2016.
 */
public class SurveyListManager {

    public static final int MAX_CACHE_SURVEY_ITEM = 10;

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
        loadCache();
    }

    public void setData(SurveyItemDao[] data) {
        this.data = data;
        saveCache();
    }

    public SurveyItemDao[] getData() {
        return data;
    }

    private void saveCache() {
        if (data == null)
            return;

        int cachedDaoCount = Math.min(MAX_CACHE_SURVEY_ITEM, data.length);
        SurveyItemDao[] cacheDao = new SurveyItemDao[cachedDaoCount];
        System.arraycopy(data,0,cacheDao,0,cachedDaoCount);

        String json = new Gson().toJson(cacheDao);

        SharedPreferences prefs = mContext.getSharedPreferences("surveys", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("json", json);
        editor.apply();
    }

    private void loadCache() {
        SharedPreferences prefs = mContext.getSharedPreferences("surveys", Context.MODE_PRIVATE);
        String json = prefs.getString("json", null);

        if (json == null)
            return;
        data = new Gson().fromJson(json, SurveyItemDao[].class);
    }
}