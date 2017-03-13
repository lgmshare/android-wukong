package com.lgmshare.base;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.view.View;

import com.lgmshare.base.ui.activity.MainActivity;


/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class MainActivityTest extends ActivityUnitTestCase<MainActivity> {

    private Intent mStartIntent;

    public MainActivityTest() {
        super(MainActivity.class);
    }


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mStartIntent = new Intent(Intent.ACTION_MAIN);
    }

    public void testPreconditions() {
        startActivity(mStartIntent, null, null);
        View view = getActivity().findViewById(R.id.realtabcontent);
        assertNotNull(view);
    }
}