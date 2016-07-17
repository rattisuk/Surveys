package tech.test.surveys.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import tech.test.surveys.R;

/**
 * Created by GOLF on 7/17/2016.
 */
public class SurveyScreenItem extends RelativeLayout {

    TextView tvTitle;
    TextView tvDescription;
    ImageView ivImg;

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
        tvTitle = (TextView) findViewById(R.id.tvTitle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);
        ivImg = (ImageView) findViewById(R.id.ivImg);
    }

    private void initWithAttrs(AttributeSet attrs) {

    }

    public void setTitleText(String text) {
        tvTitle.setText(text);
    }

    public void setDescriptionText(String text) {
        tvDescription.setText(text);
    }

    public void setImgUrl(String url) {
        //TODO: Load Image
    }
}
