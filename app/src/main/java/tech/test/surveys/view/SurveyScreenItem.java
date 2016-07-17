package tech.test.surveys.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import tech.test.surveys.R;

/**
 * Created by GOLF on 7/17/2016.
 */
public class SurveyScreenItem extends RelativeLayout {

    public SurveyScreenItem(Context context) {
        super(context);
        initInflate();
        initInstances();
    }

    public SurveyScreenItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    public SurveyScreenItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initInflate();
        initInstances();
        initWithAttrs(attrs);
    }

    private void initInflate() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.survey_screen_item, this);
    }

    private void initInstances() {
    }

    private void initWithAttrs(AttributeSet attrs) {

    }
}
