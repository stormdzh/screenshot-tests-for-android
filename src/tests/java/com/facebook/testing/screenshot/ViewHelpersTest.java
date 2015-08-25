/**
 * Copyright (c) 2014-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.testing.screenshot;

import android.test.InstrumentationTestCase;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.R;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Tests {@link ViewHelpers}
 */
public class ViewHelpersTest extends InstrumentationTestCase {
  private TextView mTextView;

  @Override
  public void setUp() throws Exception {
    super.setUp();
    mTextView = new TextView(getInstrumentation().getTargetContext());
    mTextView.setText("foobar");
  }

  public void testPreconditions() throws Throwable {
    assertThat(mTextView.getMeasuredHeight()).isEqualTo(0);
  }

  public void testMeasureWithoutHeight() throws Throwable {
    ViewHelpers.setupView(mTextView)
      .setExactWidthDp(100)
      .layout();

    assertThat(mTextView.getMeasuredHeight()).isGreaterThan(0);
  }

  public void testMeasureWithoutHeightPx() throws Throwable {
    ViewHelpers.setupView(mTextView)
      .setExactWidthPx(100)
      .layout();

    assertThat(mTextView.getMeasuredHeight()).isGreaterThan(0);
  }

  public void testMeasureForOnlyWidth() throws Throwable {
    ViewHelpers.setupView(mTextView)
      .setExactHeightPx(100)
      .layout();

    assertThat(mTextView.getMeasuredHeight()).isEqualTo(100);
    assertThat(mTextView.getMeasuredWidth()).isGreaterThan(0);
  }

  public void testBothWrapContent() throws Throwable {
    ViewHelpers.setupView(mTextView)
      .layout();

    assertThat(mTextView.getMeasuredHeight()).isGreaterThan(0);
    assertThat(mTextView.getMeasuredWidth()).isGreaterThan(0);
  }

  public void testHeightAndWidthCorrectlyPropagated() throws Throwable {
    ViewHelpers.setupView(mTextView)
      .setExactHeightDp(100)
      .setExactWidthDp(1000)
      .layout();

    assertThat(mTextView.getMeasuredWidth())
      .isGreaterThan(mTextView.getMeasuredHeight());
  }

  public void testListViewHeight() throws Throwable {
    ListView view = new ListView(getInstrumentation().getTargetContext());
    view.setDividerHeight(0);
    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
      getInstrumentation().getTargetContext(), R.layout.testing_simple_textview);
    view.setAdapter(adapter);

    for (int i = 0; i < 20; i++) {
      adapter.add("foo");
    }

    ViewHelpers.setupView(view)
      .guessListViewHeight()
      .setExactWidthDp(200)
      .layout();

    assertThat(view.getMeasuredHeight())
      .isGreaterThan(10);

    int oneHeight = view.getChildAt(0).getMeasuredHeight();
    assertThat(view.getMeasuredHeight()).isEqualTo(oneHeight * 20);
  }
}