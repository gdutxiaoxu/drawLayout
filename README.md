# 使用ViewDragHelper打造属于自己的DragLayout（抽屉开关 ）



---
**DrawLayout这个自定义的空间很常见，qq，网易新闻，知乎等等，都有这种效果，那这种效果是怎样实现的呢？本篇博客将带你来怎样实现它。**


**转载请注明[原博客地址：](http://blog.csdn.net/gdutxiaoxu/article/details/51935896)  http://blog.csdn.net/gdutxiaoxu/article/details/51935896** 

### 废话不多说，先来看一下 效果
![](http://ww3.sinaimg.cn/large/9fe4afa0gw1f5w113v6btg208w0futsy.gif)

## 首先我们先来看一下我们要怎样使用它
**其实只需要两个 步骤，使用起来 非常方便**
### 1.在XML文件
DragLayout至少要有两个孩子，且都是 ViewGroup或者ViewGroup的实现类
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
### 在代码中若想为其设置监听器,
分别可以监听打开的 时候，关闭的时候，拖动的时候，可以在里面做相应的处理，同时我还加入了 自定义属性可以通过    app:range="480"或者setRange（）方法，即可设置打开抽屉的范围。
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

---

## 实现方式
可以参考我的这篇博客[**使用ViewDragHelper打造属于自己的DragLayout（抽屉开关 ）**](http://blog.csdn.net/gdutxiaoxu/article/details/51935896) 
