package com.lgmshare.component.app;

import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;

import com.lgmshare.component.app.FrameApplication;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<FrameApplication> {

    public ApplicationTest() {
        super(FrameApplication.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testPreconditions() {
    }

    /**
     * Test basic startup/shutdown of Application
     */
    @MediumTest
    public void testSimpleCreate() {
        createApplication();
    }
}