package tech.test.surveys.callback;

import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tech.test.surveys.dao.SurveyItemDao;

/**
 * Created by GOLF on 7/18/2016.
 */
public class SurveyItemDaosCallback implements Callback<SurveyItemDao[]> {
    private static final String TAG = "SurveyItemDaosCallback";

    @Override
    public void onResponse(Call<SurveyItemDao[]> call, Response<SurveyItemDao[]> response) {
        if (response.isSuccessful()) {
            SurveyItemDao[] dao = response.body();
            Log.d(TAG, "load success : " + dao.length + " item.");
        } else {
            try {
                Log.d(TAG, response.errorBody().string());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    public void onFailure(Call<SurveyItemDao[]> call, Throwable t) {
        Log.d(TAG, t.toString());
    }
}
