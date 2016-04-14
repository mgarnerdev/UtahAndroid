package com.utahandroid.april.adapters;

import android.app.Activity;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utahandroid.april.BuildConfig;
import com.utahandroid.april.R;
import com.utahandroid.april.objects.Member;
import com.utahandroid.april.objects.MemberResponse;
import com.utahandroid.april.utils.MemberUtils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.Response;

/**
 * Created by mgarner on 4/11/2016.
 * This adapter contains the queries to retrieve member data and handles binding the data to the recycler view's view holder.
 */
@SuppressWarnings("unused")
public class MemberListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<Member> mMemberList = new ArrayList<>(25);
    private WeakReference<Activity> mActivity;
    private boolean mIsLoading = false;
    private OnLoadListener mLoadListener;
    private int mTotalMembers = 0;

    public MemberListAdapter(Activity activity) {
        mActivity = new WeakReference<>(activity);
    }

    public MemberListAdapter(Activity activity, ArrayList<Member> members) {
        mActivity = new WeakReference<>(activity);
        mMemberList = members;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(mActivity.get());
        View view = inflater.inflate(R.layout.list_item_member, parent, false);
        return new MemberViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MemberViewHolder) {
            bindMemberViewHolder((MemberViewHolder) holder, position);
        }
    }

    private void bindMemberViewHolder(MemberViewHolder holder, final int position) {
        final Member currentMember = mMemberList.get(position);
    }

    @Override
    public int getItemCount() {
        return mMemberList != null ? mMemberList.size() : 0;
    }

    /**
     * Ensure reference is destroyed.
     */
    public void destroyAdapter() {
        mActivity = null;
    }


    public void setMemberList(ArrayList<Member> memberList) {
        mMemberList = memberList;
        notifyDataSetChanged();
    }

    /**
     * Retrieve the first page of results from the members API.
     */
    public void getMembers() {
        Callback callback = new Callback() {
            Handler mainHandler = new Handler(mActivity.get().getMainLooper());

            @Override
            public void onFailure(Call call, final IOException e) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        mIsLoading = false;
                        if (BuildConfig.DEBUG) {
                            e.printStackTrace();
                        }
                        if (mLoadListener != null) {
                            mLoadListener.onMembersLoadedFailure();
                            mLoadListener.onLoadFinished();
                        }
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            mIsLoading = false;
                            if (!response.isSuccessful()) {
                                if (mLoadListener != null) {
                                    mLoadListener.onMembersLoadedFailure();
                                    mLoadListener.onLoadFinished();
                                }
                                throw new IOException("Unexpected code " + response);
                            }
                            MemberResponse memberResponse = MemberUtils.handleMemberResponse(response);
                            mTotalMembers = memberResponse.getTotalMembers();
                            setMemberList(memberResponse.getMembers());
                            if (mLoadListener != null) {
                                mLoadListener.onMembersLoaded(mMemberList.size());
                                mLoadListener.onLoadFinished();
                            }
                            if (BuildConfig.DEBUG) {
                                Headers responseHeaders = response.headers();
                                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                                    Log.d(MemberListAdapter.class.getSimpleName(), responseHeaders.name(i) + ": " + responseHeaders.value(i));
                                }
                                Log.d(MemberListAdapter.class.getSimpleName(), response.body().string());
                            }
                        } catch (IOException e) {
                            if (BuildConfig.DEBUG) {
                                e.printStackTrace();
                            }
                        }
                    }
                });

            }
        };
        if (!mIsLoading) {
            mIsLoading = true;
            if (mLoadListener != null) {
                mLoadListener.onLoadStarted();
            }
            MemberUtils.getMembers(callback);
        }
    }

    public void clearAdapter() {
        mMemberList.clear();
        notifyDataSetChanged();
    }

    /**
     * Sets a listener that can be used to know when results have been received.
     *
     * @param loadListener What's listening?
     */
    public void setLoadListener(OnLoadListener loadListener) {
        mLoadListener = loadListener;
    }

    public OnLoadListener getLoadListener() {
        return mLoadListener;
    }

    public class MemberViewHolder extends RecyclerView.ViewHolder {

        public MemberViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface OnLoadListener {
        void onMembersLoaded(int numLoaded);

        void onMembersLoadedFailure();

        void onLoadStarted();

        void onLoadFinished();
    }
}
