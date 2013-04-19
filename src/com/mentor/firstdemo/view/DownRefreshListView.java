package com.mentor.firstdemo.view;

import java.util.Date;

import com.mentor.firstdemo.R;
import com.mentor.firstdemo.util.DateUtil;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
public class DownRefreshListView extends ListView  { 

	private int state;
	private final static int RELEASE_To_REFRESH = 0;
	private final static int PULL_To_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private final static int LOADING = 4;
	private final static int RATIO = 3;
	private LinearLayout headView;
	private int headContentWidth;
	private int headContentHeight;
	private TextView tipsTextview;
	private TextView lastUpdatedTextView;
	private ImageView arrowImageView;
	private ProgressBar progressBar;
	private RotateAnimation animation;
	private RotateAnimation reverseAnimation;
	private boolean isRecored;
	private int startY;
	private int firstItemIndex;
	private boolean isBack;
	private boolean isRefreshable;
	private Context context;
	private OnDownRefreshListener listener;
	public DownRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}
	public DownRefreshListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context=context;
		init();
	}
	
	public void setOnDownRefreshListener(OnDownRefreshListener listener){
		this.listener=listener;
	}

	private void init() {

		this.setFadingEdgeLength(0);
		Context con=context;
	    this.setCacheColorHint(0xfff4f4f4);
	    this.setDivider(con.getResources().getDrawable(R.drawable.downrefresh_horizontal_line));
		this.setDividerHeight(1);
	    
		headView =(LinearLayout) View.inflate(con, R.layout.downrefresh_head, null);

		arrowImageView = (ImageView) headView.findViewById(R.id.head_arrowImageView);
		arrowImageView.setMinimumWidth(70);
		arrowImageView.setMinimumHeight(50);
		progressBar = (ProgressBar) headView.findViewById(R.id.head_progressBar);
		tipsTextview = (TextView) headView.findViewById(R.id.head_tipsTextView);
		lastUpdatedTextView = (TextView) headView.findViewById(R.id.head_lastUpdatedTextView);

		measureView(headView);
		headContentHeight = headView.getMeasuredHeight();
		headContentWidth = headView.getMeasuredWidth();

		headView.setPadding(0, -1 * headContentHeight, 0, 0);
		headView.invalidate();

		Log.v("size", "width:" + headContentWidth + " height:"
				+ headContentHeight);

		this.addHeaderView(headView, null, false);
		animation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		animation.setInterpolator(new LinearInterpolator());
		animation.setDuration(250);
		animation.setFillAfter(true);

		reverseAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		reverseAnimation.setInterpolator(new LinearInterpolator());
		reverseAnimation.setDuration(200);
		reverseAnimation.setFillAfter(true);

		state = DONE;
		isRefreshable = true;
		
		this.setOnScrollListener(ssListScroll);
		this.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				if (isRefreshable) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						if (firstItemIndex == 0 && !isRecored) {
							isRecored = true;
							startY = (int) event.getY();
						}
						break;
					case MotionEvent.ACTION_UP:
						if (state != REFRESHING && state != LOADING) {
							if (state == DONE) {
							}
							if (state == PULL_To_REFRESH) {
								state = DONE;
								changeHeaderViewByState();
							}
							if (state == RELEASE_To_REFRESH) {
								state = REFRESHING;
								changeHeaderViewByState();
								listener.onRefresh();
							}
						}
						isRecored = false;
						isBack = false;
						break;

					case MotionEvent.ACTION_MOVE:
						int tempY = (int) event.getY();

						if (!isRecored && firstItemIndex == 0) {
							isRecored = true;
							startY = tempY;
						}

						if (state != REFRESHING && isRecored
								&& state != LOADING) {

							if (state == RELEASE_To_REFRESH) {

								DownRefreshListView.this.setSelection(0);
								if (((tempY - startY) / RATIO < headContentHeight)
										&& (tempY - startY) > 0) {
									state = PULL_To_REFRESH;
									changeHeaderViewByState();
								} else if (tempY - startY <= 0) {
									state = DONE;
									changeHeaderViewByState();
								}
							}

							if (state == PULL_To_REFRESH) {
								DownRefreshListView.this.setSelection(0);
								if ((tempY - startY) / RATIO >= headContentHeight) {
									state = RELEASE_To_REFRESH;
									isBack = true;
									changeHeaderViewByState();
								} else if (tempY - startY <= 0) {
									state = DONE;
									changeHeaderViewByState();
								}
							}

							if (state == DONE) {
								if (tempY - startY > 0) {
									state = PULL_To_REFRESH;
									changeHeaderViewByState();
								}
							}

							if (state == PULL_To_REFRESH) {
								headView.setPadding(0, -1 * headContentHeight
										+ (tempY - startY) / RATIO, 0, 0);

							}

							if (state == RELEASE_To_REFRESH) {
								headView.setPadding(0, (tempY - startY) / RATIO
										- headContentHeight, 0, 0);
							}

						}

						break;
					}
				}

				return false;
			}
		});
	}

	private void changeHeaderViewByState() {
		switch (state) {
		case RELEASE_To_REFRESH:
			arrowImageView.setVisibility(View.VISIBLE);
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);

			arrowImageView.clearAnimation();
			arrowImageView.startAnimation(animation);

			tipsTextview.setText("松开刷新");
			//isXiala=true;
			break;
		case PULL_To_REFRESH:
			progressBar.setVisibility(View.GONE);
			tipsTextview.setVisibility(View.VISIBLE);
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.VISIBLE);

			if (isBack) {
				isBack = false;
				arrowImageView.clearAnimation();
				arrowImageView.startAnimation(reverseAnimation);

				tipsTextview.setText("下拉刷新");
			} else {
				tipsTextview.setText("下拉刷新");
			}
			break;

		case REFRESHING:

			headView.setPadding(0, 0, 0, 0);

			progressBar.setVisibility(View.VISIBLE);
			arrowImageView.clearAnimation();
			arrowImageView.setVisibility(View.GONE);
			tipsTextview.setText("正在刷新...");
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			
			break;
		case DONE:
			headView.setPadding(0, -1 * headContentHeight, 0, 0);

			progressBar.setVisibility(View.GONE);
			arrowImageView.clearAnimation();
			arrowImageView.setImageResource(R.drawable.downrefresh_arrow);
			tipsTextview.setText("完毕");
			lastUpdatedTextView.setVisibility(View.VISIBLE);
			//isXiala=false;
			break;
		}
	}
	public void onRefreshComplete() {
		state = DONE;
		String dateStr=DateUtil.getFormatDateString(new Date(), DateUtil.SHORT_DATE_DAY_FORMAT);
		lastUpdatedTextView.setText("更新于:" + dateStr);
		changeHeaderViewByState();
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}
	/**
	 * ListView滚动用于记录firstItemIndex
	 */
	AbsListView.OnScrollListener ssListScroll = new OnScrollListener() {
		public void onScrollStateChanged(AbsListView abslistview,
				int scrollState) {
		}

		public void onScroll(AbsListView abslistview, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {
			// 这一句用于下拉刷新
			firstItemIndex = firstVisibleItem;
		}
	};

}
