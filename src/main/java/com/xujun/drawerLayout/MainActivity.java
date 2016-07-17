package com.xujun.drawerLayout;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.CycleInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.xujun.drawerLayout.drag.DragLayout;
import com.xujun.drawerLayout.drag.DragLayout.OnDragStatusChangeListener;
import com.xujun.drawerLayout.drag.MyLinearLayout;
import com.xujun.drawerLayout.util.Cheeses;
import com.xujun.drawerLayout.util.Utils;

import java.util.Random;

public class MainActivity extends Activity {

	private static final String TAG = "TAG";
    private ListView mLeftList;
    private ListView mMainList;
    private ImageView mHeaderImage;
    private MyLinearLayout mMyLinearLayout;
    private DragLayout mDragLayout;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

        initView();

        initListener();

        initData();

		
		
	}

    private void initView() {
        mLeftList = (ListView) findViewById(R.id.lv_left);
        mMainList = (ListView) findViewById(R.id.lv_main);
        mHeaderImage = (ImageView) findViewById(R.id.iv_header);
        mMyLinearLayout = (MyLinearLayout) findViewById(R.id.mll);

        // 查找Draglayout, 设置监听
        mDragLayout = (DragLayout) findViewById(R.id.dl);
    }

    private void initListener() {
        mDragLayout.setDragStatusListener(new OnDragStatusChangeListener() {

            @Override
            public void onOpen() {
                Utils.showToast(MainActivity.this, "onOpen");
                // 左面板ListView随机设置一个条目
                Random random = new Random();
                Log.i(TAG, "onOpen:=" +mDragLayout.getRange());
                int nextInt = random.nextInt(50);
                mLeftList.smoothScrollToPosition(nextInt);

            }

            @Override
            public void onDraging(float percent) {
                Log.d(TAG, "onDraging: " + percent);// 0 -> 1
                // 更新图标的透明度
                // 1.0 -> 0.0
                ViewHelper.setAlpha(mHeaderImage, 1 - percent);
            }

            @Override
            public void onClose() {
                Utils.showToast(MainActivity.this, "onClose");
                // 让图标晃动
//				mHeaderImage.setTranslationX(translationX)
                ObjectAnimator mAnim = ObjectAnimator.ofFloat(mHeaderImage, "translationX", 15.0f);
                mAnim.setInterpolator(new CycleInterpolator(4));
                mAnim.setDuration(500);
                mAnim.start();
            }
        });
    }

    private void initData() {

        mDragLayout.setRange(200);
        int range = mDragLayout.getRange();
        Log.i(TAG, "initData:=range" +range);

        // 设置引用
        mMyLinearLayout.setDraglayout(mDragLayout);
        mLeftList.setAdapter(new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, Cheeses.sCheeseStrings){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView mText = ((TextView)view);
                mText.setTextColor(Color.WHITE);
                return view;
            }
        });

        mMainList.setAdapter(new ArrayAdapter<String>(this,
               android.R.layout.simple_list_item_1, Cheeses.NAMES));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume:=" +mDragLayout.getRange());
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
