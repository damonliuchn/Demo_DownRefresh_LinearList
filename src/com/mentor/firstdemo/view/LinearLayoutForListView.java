package me.ele.components;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Adapter;
import android.widget.LinearLayout;

public class LinearLayoutLikeListView extends LinearLayout {

    private Adapter adapter;
    private OnItemClickListener onItemClickListener = null;
    private int currentNum = -1;
    private boolean isFoot = false;
    private AdapterDataSetObserver mDataSetObserver;

    public LinearLayoutLikeListView(Context context) {
        super(context);
        init();
    }

    public LinearLayoutLikeListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init() {
        setOrientation(LinearLayout.VERTICAL);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setAdapter(Adapter adpater) {
        if (adpater == null) return;
        if (this.adapter != null && mDataSetObserver != null) {
            this.adapter.unregisterDataSetObserver(mDataSetObserver);
        }
        this.adapter = adpater;
        this.mDataSetObserver = new AdapterDataSetObserver();
        this.adapter.registerDataSetObserver(mDataSetObserver);
        bindLinearLayout();
    }

    /**
     * addview
     */
    public void bindLinearLayout() {
        int count = adapter.getCount();
        for (int i = currentNum + 1; i < count; i++) {
            final int a = i;
            View v = adapter.getView(i, null, null);
            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClick(v, a);
                }
            });
            v.setVisibility(View.VISIBLE);
            currentNum = i;
            addView(v, i);
        }
    }

    @Deprecated
    public void updateAllAdapter() {
        try {
            if (isFoot) {
                removeViews(0, getChildCount() - 1);
            } else {
                removeAllViews();
            }
            currentNum = -1;
            bindLinearLayout();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Adapter getAdpater() {
        return adapter;
    }

    public OnItemClickListener getItemOnclickListner() {
        return onItemClickListener;
    }

    /**
     * 目前只能添加一个footer
     *
     * @param footerView
     */
    public void addFooterView(LinearLayout footerView) {
        // TODO Auto-generated method stub
        if (!isFoot) {
            addView(footerView, currentNum + 1);
            isFoot = true;
        }
    }

    public void removeFooterView() {
        // TODO Auto-generated method stub
        if (isFoot) {
            removeViewAt(currentNum + 1);
            isFoot = false;
        }
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }

    class AdapterDataSetObserver extends DataSetObserver {
        @Override
        public void onChanged() {
            updateAllAdapter();
        }

        @Override
        public void onInvalidated() {
            updateAllAdapter();
        }
    }
}
