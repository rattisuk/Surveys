package tech.test.surveys.eventbus;

import retrofit2.Response;
import tech.test.surveys.dao.SurveyItemDao;

/**
 * Created by GOLF on 7/19/2016.
 */
public class BusEventSurveyDataLoaded {

    boolean isSuccess;
    Response<SurveyItemDao[]> response;
    public BusEventSurveyDataLoaded(boolean isSuccess, Response<SurveyItemDao[]> response) {
        this.isSuccess = isSuccess;
        this.response = response;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public Response<SurveyItemDao[]> getResponse() {
        return response;
    }
}
