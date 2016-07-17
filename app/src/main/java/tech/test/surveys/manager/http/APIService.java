package tech.test.surveys.manager.http;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by GOLF on 7/18/2016.
 */
public interface APIService {

    @POST("surveys.json")
    Call<Objects> loadSurveyList(@Query("access_token") String access_token);
}
