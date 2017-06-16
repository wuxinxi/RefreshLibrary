# RefreshLibrary
## 1.依赖方式</br>
Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```java
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```
dependencies {
	        compile 'com.github.wuxinxi:RefreshLibrary:1.0.1'
	}
```
## 2.功能
```
1. 下拉刷新、滑动到底部自动加载下页数据；
2. 具备item点击和长按事件；
3. 网络错误加载失败点击Footer重新请求数据；
4. 可以动态为FooterView赋予不同状态（加载中、加载失败、滑到最底等）；
```
## 3.使用方式 </br>
```
<android.support.v4.widget.SwipeRefreshLayout
        android:id="@+id/refresh"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <com.wxx.refreshlibrary.recyclerView.TRecyclerView
            android:id="@+id/recyclerView"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </android.support.v4.widget.SwipeRefreshLayout>
    
```
Adapter继承BaseAdapter</br>
### 1.添加数据<br>
```
 MyAdapter myAdapter = new MyAdapter(R.layout.view_itre,getData());
 TRecyclerViewAdapter tAdapter = new TRecyclerViewAdapter(myAdapter);
 recyclerView.setAdapter(tAdapter);
 
```
### 2.上拉刷新（实现接口）</br>
```
OnLoadMoreListener
```
### 3.点击事件、长按事件、滑动监听（实现接口）</br>
```
OnItemClickListener、OnItemLongClickListener、LScrollListener
```
### 4.设置foot文字提示、文字颜色
```
recyclerView.setFooterViewHint(String loading, String noMore, String noNetWork )
recyclerView.setFooterViewColor(int indicatorColor, int hintColor, int backgroundColor)

```
### 5.加载完处理
```
tAdapter.addAll(list);
recyclerView.refreshComplete(REQUEST_COUNT);// REQUEST_COUNT为每页加载数量

```
### 6.网络错误处理
```
recyclerView.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                @Override
                public void reload() {
                    requestData();
                }
            });
```

## 感谢
LRecyclerView(https://github.com/jdsjlzx/LRecyclerView)

