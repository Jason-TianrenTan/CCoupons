package com.example.administrator.ccoupons.Search;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public abstract class EndlessOnScrollListener extends RecyclerView.OnScrollListener {


    private LinearLayoutManager mLinearLayoutManager;
    private int totalItemCount;
    private int lastVisibleItem;
    private int firstVisibleItem;
    private int previousTotal = 0;
    private boolean loading = true;

    public EndlessOnScrollListener(LinearLayoutManager linearLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager;
    }


    /**
     *
     * @param recyclerView
     * @param dx current X position
     * @param dy current Y position
     */
    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        totalItemCount = mLinearLayoutManager.getItemCount();
        lastVisibleItem = mLinearLayoutManager.findLastVisibleItemPosition();
        firstVisibleItem = mLinearLayoutManager.findFirstVisibleItemPosition();
        System.out.println("visibleItem = " + visibleItemCount + ", total = " + totalItemCount +
                ", firtVisible = " + firstVisibleItem);
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }
        if (!loading && totalItemCount <= (lastVisibleItem + 1)) {
            onLoadMore();
            loading = true;
        }
    }

    public abstract void onLoadMore();

}
