package tech.test.surveys.manager;

import tech.test.surveys.dao.SurveyItemDao;

/**
 * Created by GOLF on 7/18/2016.
 */
public class SurveyItemsManager {

    private static SurveyItemsManager instance;

    public static SurveyItemsManager getInstance() {
        if(instance == null)
            instance =  new SurveyItemsManager();
        return instance;
    }

    private SurveyItemDao[] daos;

    public SurveyItemsManager() {}

    public SurveyItemDao[] getDaos() {
        return daos;
    }

    public void setDaos(SurveyItemDao[] daos) {
        this.daos = daos;
    }
}
