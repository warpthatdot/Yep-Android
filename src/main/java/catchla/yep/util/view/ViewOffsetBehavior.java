/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package catchla.yep.util.view;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.AccessorViewOffsetHelper;
import android.util.AttributeSet;
import android.view.View;

/**
 * Behavior will automatically sets up a {@link AccessorViewOffsetHelper} on a {@link View}.
 */
public class ViewOffsetBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {

    private AccessorViewOffsetHelper mViewOffsetHelper;

    private int mTempTopBottomOffset = 0;
    private int mTempLeftRightOffset = 0;

    public ViewOffsetBehavior() {
    }

    public ViewOffsetBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, V child, int layoutDirection) {
        // First let the parent lay it out
        parent.onLayoutChild(child, layoutDirection);

        onLayoutChildHelper(child);

        return true;
    }

    protected void onLayoutChildHelper(final V child) {
        if (mViewOffsetHelper == null) {
            mViewOffsetHelper = new AccessorViewOffsetHelper(child);
        }
        mViewOffsetHelper.onViewLayout();

        if (mTempTopBottomOffset != 0) {
            mViewOffsetHelper.setTopAndBottomOffset(mTempTopBottomOffset);
            mTempTopBottomOffset = 0;
        }
        if (mTempLeftRightOffset != 0) {
            mViewOffsetHelper.setLeftAndRightOffset(mTempLeftRightOffset);
            mTempLeftRightOffset = 0;
        }
    }

    public boolean setTopAndBottomOffset(int offset) {
        if (mViewOffsetHelper != null) {
            return mViewOffsetHelper.setTopAndBottomOffset(offset);
        } else {
            mTempTopBottomOffset = offset;
        }
        return false;
    }

    public boolean setLeftAndRightOffset(int offset) {
        if (mViewOffsetHelper != null) {
            return mViewOffsetHelper.setLeftAndRightOffset(offset);
        } else {
            mTempLeftRightOffset = offset;
        }
        return false;
    }

    public int getTopAndBottomOffset() {
        return mViewOffsetHelper != null ? mViewOffsetHelper.getTopAndBottomOffset() : 0;
    }

    public int getLeftAndRightOffset() {
        return mViewOffsetHelper != null ? mViewOffsetHelper.getLeftAndRightOffset() : 0;
    }
}