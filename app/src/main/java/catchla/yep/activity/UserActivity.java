/*
 * Copyright (c) 2015. Catch Inc,
 */

package catchla.yep.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;

import com.bluelinelabs.logansquare.LoganSquare;

import java.io.IOException;

import catchla.yep.Constants;
import catchla.yep.R;
import catchla.yep.fragment.UserFragment;
import catchla.yep.graphic.ActionBarColorDrawable;
import catchla.yep.model.User;
import catchla.yep.util.MathUtils;
import catchla.yep.util.ThemeUtils;
import catchla.yep.util.Utils;
import catchla.yep.view.HeaderDrawerLayout;
import catchla.yep.view.TintedStatusFrameLayout;
import catchla.yep.view.iface.IExtendedView;

public class UserActivity extends SwipeBackContentActivity implements Constants,
        HeaderDrawerLayout.DrawerCallback, IExtendedView.OnFitSystemWindowsListener {

    private TintedStatusFrameLayout mMainContent;
    private ActionBarDrawable mActionBarBackground;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mMainContent = (TintedStatusFrameLayout) findViewById(R.id.main_content);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final User currentUser;
        final Intent intent = getIntent();
        final Bundle fragmentArgs = new Bundle();
        if (intent.hasExtra(EXTRA_USER)) {
            final String value = intent.getStringExtra(EXTRA_USER);
            fragmentArgs.putString(EXTRA_USER, value);
            try {
                currentUser = LoganSquare.parse(value, User.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            currentUser = Utils.getCurrentAccountUser(this);
        }

        if (currentUser == null) {
            finish();
            return;
        }

        final ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        final int primaryColor = ThemeUtils.getColorFromAttribute(this, R.attr.colorPrimary, 0);

        final Drawable shadow = ResourcesCompat.getDrawable(getResources(), R.drawable.shadow_user_banner_action_bar, null);

        mActionBarBackground = new ActionBarDrawable(shadow);
        actionBar.setBackgroundDrawable(mActionBarBackground);
        setContentView(R.layout.activity_user);

        mMainContent.setDrawShadow(true);
        mMainContent.setDrawColor(true);
        mMainContent.setOnFitSystemWindowsListener(this);

        mMainContent.setShadowColor(0xA0000000);
        mActionBarBackground.setColor(primaryColor);
        mMainContent.setColor(primaryColor);

        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.main_content, Fragment.instantiate(this, UserFragment.class.getName(), fragmentArgs));
        ft.commit();

        topChanged(0);

        setTitle(currentUser.getNickname());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings: {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean canScroll(float dy) {
        return false;
    }

    @Override
    public void cancelTouch() {

    }

    @Override
    public void fling(float velocity) {

    }

    @Override
    public boolean isScrollContent(float x, float y) {
        return false;
    }

    @Override
    public void scrollBy(float dy) {

    }

    @Override
    public boolean shouldLayoutHeaderBottom() {
        return false;
    }

    @Override
    public void topChanged(int offset) {
        if (mActionBarBackground == null || mMainContent == null) return;
        final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (fragment == null) {
            mActionBarBackground.setFactor(0);
            mMainContent.setFactor(0);
            return;
        }
        final int paddingTop = ((UserFragment) fragment).getHeaderPaddingTop();
        final int spaceHeight = ((UserFragment) fragment).getHeaderSpaceHeight();
        if (spaceHeight == 0) {
            mActionBarBackground.setFactor(0);
            mMainContent.setFactor(0);
            return;
        }
        final float factor = (paddingTop - offset) / (float) spaceHeight;
        mActionBarBackground.setFactor(factor);
        mMainContent.setFactor(factor);
    }

    @Override
    public void onFitSystemWindows(Rect insets) {
        final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_content);
        if (fragment instanceof IExtendedView.OnFitSystemWindowsListener) {
            ((IExtendedView.OnFitSystemWindowsListener) fragment).onFitSystemWindows(insets);
        }
    }

    @Override
    protected boolean isTintBarEnabled() {
        return false;
    }

    private static class ActionBarDrawable extends LayerDrawable {

        private final Drawable mShadowDrawable;
        private final ColorDrawable mColorDrawable;

        private float mFactor;
        private int mColor;
        private int mAlpha;
        private float mOutlineAlphaFactor;

        public ActionBarDrawable(Drawable shadow) {
            super(new Drawable[]{shadow, ActionBarColorDrawable.create(true)});
            mShadowDrawable = getDrawable(0);
            mColorDrawable = (ColorDrawable) getDrawable(1);
            setAlpha(0xFF);
            setOutlineAlphaFactor(1);
        }

        public int getColor() {
            return mColor;
        }

        public void setColor(int color) {
            mColor = color;
            mColorDrawable.setColor(color);
            setFactor(mFactor);
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void getOutline(Outline outline) {
            mColorDrawable.getOutline(outline);
            outline.setAlpha(mFactor * mOutlineAlphaFactor * 0.99f);
        }

        @Override
        public void setAlpha(int alpha) {
            mAlpha = alpha;
            setFactor(mFactor);
        }

        @Override
        public int getIntrinsicWidth() {
            return mColorDrawable.getIntrinsicWidth();
        }

        @Override
        public int getIntrinsicHeight() {
            return mColorDrawable.getIntrinsicHeight();
        }

        public void setFactor(float f) {
            mFactor = f;
            mShadowDrawable.setAlpha(Math.round(mAlpha * MathUtils.clamp(1 - f, 0, 1)));
            final boolean hasColor = mColor != 0;
            mColorDrawable.setAlpha(hasColor ? Math.round(mAlpha * MathUtils.clamp(f, 0, 1)) : 0);
        }

        public void setOutlineAlphaFactor(float f) {
            mOutlineAlphaFactor = f;
            invalidateSelf();
        }

    }

}
