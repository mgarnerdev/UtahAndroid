package com.utahandroid.april.activities;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;

import com.utahandroid.april.R;
import com.utahandroid.april.adapters.MemberListAdapter;
import com.utahandroid.april.utils.ConnectionUtils;
import com.utahandroid.april.utils.Utils;

@SuppressWarnings("unused")
public class MemberListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, MemberListAdapter.OnLoadListener {

    private MemberListAdapter mMemberListAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RelativeLayout mRlDimmingOverlay;
    private AlphaAnimation mDimmingOverlayFadeOutAnim;
    private AlphaAnimation mDimmingOverlayFadeInAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Slide(Gravity.START));
            getWindow().setExitTransition(new Slide(Gravity.START));
        }
        setContentView(R.layout.activity_member_list);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.member_list_swipe_refresh_layout);
        assert mSwipeRefreshLayout != null;
        mSwipeRefreshLayout.setOnRefreshListener(this);
        RecyclerView rvMemberList = (RecyclerView) findViewById(R.id.member_list_rv_list);
        mMemberListAdapter = new MemberListAdapter(this);
        mMemberListAdapter.setLoadListener(this);

        mRlDimmingOverlay = (RelativeLayout) findViewById(R.id.member_list_rl_dimming_loading_overlay);
        mDimmingOverlayFadeOutAnim = Utils.setupFadeAnimation(300, 1.0f, 0.0f, mRlDimmingOverlay);
        mDimmingOverlayFadeInAnim = Utils.setupFadeAnimation(300, 0.0f, 1.0f, mRlDimmingOverlay);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        assert rvMemberList != null;
        rvMemberList.setHasFixedSize(true);
        rvMemberList.setLayoutManager(layoutManager);
        rvMemberList.setAdapter(mMemberListAdapter);
        mSwipeRefreshLayout.setRefreshing(true);
        if (ConnectionUtils.hasInternet(this)) {
            mMemberListAdapter.getMembers();
        } else {
            Snackbar.make(mSwipeRefreshLayout, "Sorry, you don't have internet right now. Please connect and try again.", Snackbar.LENGTH_LONG).setAction("Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mMemberListAdapter != null) {
                        mMemberListAdapter.getMembers();
                    }
                }
            }).show();
        }
    }

    /**
     * Show progress bar
     */
    private void showDimmingOverlay() {
        if (mRlDimmingOverlay != null && mDimmingOverlayFadeInAnim != null) {
            mRlDimmingOverlay.startAnimation(mDimmingOverlayFadeInAnim);
        }
    }

    /**
     * Hide progress bar
     */
    private void hideDimmingOverlay() {
        if (mRlDimmingOverlay != null && mDimmingOverlayFadeOutAnim != null) {
            mRlDimmingOverlay.startAnimation(mDimmingOverlayFadeOutAnim);
        }
    }

    /**
     * Called when the user swipes down at the top.
     */
    @Override
    public void onRefresh() {
        if (mMemberListAdapter != null) {
            mMemberListAdapter.clearAdapter();
            mMemberListAdapter.getMembers();
        }
    }

    @Override
    public void onMembersLoaded(int numLoaded) {
        hideDimmingOverlay();
    }

    @Override
    public void onMembersLoadedFailure() {
        Snackbar.make(mSwipeRefreshLayout, "Sorry, we were unable to load any members at the moment. Please try again later.", Snackbar.LENGTH_LONG).setAction("Try Again", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMemberListAdapter != null) {
                    mMemberListAdapter.getMembers();
                }
            }
        }).show();
    }

    @Override
    public void onLoadStarted() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void onLoadFinished() {
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    /**
     * Clean up custom interfaces to prevent possible memory leaks.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMemberListAdapter != null) {
            mMemberListAdapter.setLoadListener(null);
            mMemberListAdapter.clearAdapter();
            mMemberListAdapter.destroyAdapter();
        }
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.setOnRefreshListener(null);
        }
    }
}
