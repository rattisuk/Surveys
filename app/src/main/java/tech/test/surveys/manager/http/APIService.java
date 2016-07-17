package tech.test.surveys.manager.http;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tech.test.surveys.dao.SurveyItemDao;

/**
 * Created by GOLF on 7/18/2016.
 */
public interface APIService {

    @GET("surveys.json")
    Call<SurveyItemDao[]> loadSurveyList(@Query("access_token") String access_token);
}
