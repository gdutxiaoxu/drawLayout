# drawLayout
仿qq，网易新闻的自定义抽屉开关。


**DrawLayout这个自定义的空间很常见，qq，网易新闻，知乎等等，都有这种效果，那这种效果是怎样实现的呢？本篇博客将带你来怎样实现它。**

### 废话 不多说，先来看一下 效果
![](http://ww3.sinaimg.cn/large/9fe4afa0gw1f5w113v6btg208w0futsy.gif)

## 首先 我们先来看一下我们要怎样使用它
**其实只需要两个 步骤，使用起来 非常方便**
### 1.在XML文件
DragLayout至少要有两个 ViewGroup的孩子
```xml
<com.xujun.drawerLayout.drag.DragLayout
    android:id="@+id/dl"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/bg"
    app:range="480"
    >

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:paddingBottom="50dp"
        android:paddingLeft="10dp"
        android:paddingRight="50dp"
        android:paddingTop="50dp">

        <ImageView
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:src="@drawable/head"/>

        <ListView
            android:id="@+id/lv_left"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        </ListView>
    </LinearLayout>

    <com.xujun.drawerLayout.drag.MyLinearLayout
        android:id="@+id/mll"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#ffffff"
        android:orientation="vertical">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="50dp"
            android:background="#18B6EF"
            android:gravity="center_vertical">

            <ImageView
                android:id="@+id/iv_header"
                android:layout_width="30dp"
                android:layout_height="30dp"
                android:layout_marginLeft="15dp"
                android:src="@drawable/head"/>

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_centerHorizontal="true"
                android:text="Header"/>
        </RelativeLayout>

        <ListView
            android:id="@+id/lv_main"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        </ListView>
    </com.xujun.drawerLayout.drag.MyLinearLayout>

</com.xujun.drawerLayout.drag.DragLayout>
```
### 在 代码中若想为其设置监听器,
分别可以监听打开的 时候，关闭的时候，拖动的时候，可以在里面做相应的处理，同时我还加入了 自定义属性可以通过    app:range="480"或者setRange（）方法 设置打开 抽屉的 范围。
```java
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
                ObjectAnimator mAnim = ObjectAnimator.ofFloat(mHeaderImage, "translationX", 15.0f);
                mAnim.setInterpolator(new CycleInterpolator(4));
                mAnim.setDuration(500);
                mAnim.start();
            }
        });
```
