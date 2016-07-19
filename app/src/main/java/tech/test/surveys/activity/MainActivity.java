package tech.test.surveys.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import tech.test.surveys.R;
import tech.test.surveys.fragment.MainFragment;
import tech.test.surveys.fragment.SecondFragment;

public class MainActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initInstances();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.contentContainer, MainFragment.newInstance())
                    .commit();
        }

        showVisibleMenu();
    }

    private void initInstances()
    {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar !=null) {
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    @Override
    public void onBackStackChanged() {
        showVisibleMenu();
    }


    private void showVisibleMenu()
    {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.contentContainer);
        if(fragment instanceof MainFragment) {
            toolbar.findViewById(R.id.ibRefresh).setVisibility(View.VISIBLE);
            toolbar.findViewById(R.id.ibRefresh).setAlpha(1);
            toolbar.findViewById(R.id.ibBack).setVisibility(View.GONE);
        } else if(fragment instanceof SecondFragment) {
            toolbar.findViewById(R.id.ibRefresh).setVisibility(View.GONE);
            toolbar.findViewById(R.id.ibRefresh).setAlpha(0);
            toolbar.findViewById(R.id.ibBack).setVisibility(View.VISIBLE);
        }
    }
}
