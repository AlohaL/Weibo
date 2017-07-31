package com.sina.weibo.sdk.demo.main;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.sina.weibo.sdk.demo.BaseApplication;
import com.sina.weibo.sdk.demo.Constants;
import com.sina.weibo.sdk.demo.R;
import com.sina.weibo.sdk.demo.adapter.MiddlePageAdapter;
import com.sina.weibo.sdk.demo.bean.FeedList;
import com.sina.weibo.sdk.demo.bean.FeedListBean;
import com.sina.weibo.sdk.demo.bean.PhotoBean;
import com.sina.weibo.sdk.demo.net.SinaRetrofitUtil;
import com.sina.weibo.sdk.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Steve on 2017/4/15.
 */

public class HomePageFragment extends Fragment {

    protected Context mContext = null;

    private PtrFrameLayout ptrFrameLayout;
    private List<FeedListBean> lists;
    private MiddlePageAdapter adapter;
    private LoadMoreListViewContainer mLoadMoreListViewContainer;
    private int start = 0;
    private int count = 15;
    private int mCurrentPage = 1;

    public static HomePageFragment newInstance() {
        HomePageFragment fragment = new HomePageFragment();
        return fragment;
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //设置刷新完毕，取消刷新动画
            ptrFrameLayout.refreshComplete();
            adapter.notifyDataSetChanged();
        }
    };


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_home_page, container, false);
        return layout;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //TODO
        //填充view的内容
        ptrFrameLayout = (PtrFrameLayout) view.findViewById(R.id.ptr_frame);
        ListView listView = (ListView) view.findViewById(R.id.pull_listview);
        initData();

        //下拉刷新
        //1.默认经典头布局
//        PtrClassicDefaultHeader defaultHeader = new PtrClassicDefaultHeader(this);
//        给Ptr添加头布局
//        ptrFrameLayout.setHeaderView(defaultHeader);
//        使头布局的状态和刷新状态同步
//        ptrFrameLayout.addPtrUIHandler(defaultHeader);
        //2.模仿MD风格的头布局
//        MaterialHeader materialHeader = new MaterialHeader(this);
        //设置MD风格动画的颜色
//        materialHeader.setColorSchemeColors(new int[]{Color.RED, Color.GREEN, Color.BLUE});
//        ptrFrameLayout.setHeaderView(materialHeader);
//        ptrFrameLayout.addPtrUIHandler(materialHeader);
        //3.闪动文字的头布局
        StoreHouseHeader storeHouseHeader = new StoreHouseHeader(mContext);
        //设置加载数据时显示的字符串，文本只支持0-9，英文字符
        storeHouseHeader.initWithString("Loading...");
        //设置头布局的背景颜色
        storeHouseHeader.setBackgroundColor(Color.WHITE);
        //设置文本的颜色
        storeHouseHeader.setTextColor(Color.BLACK);
//        storeHouseHeader.setDropHeight(300);
        ptrFrameLayout.setHeaderView(storeHouseHeader);
        ptrFrameLayout.addPtrUIHandler(storeHouseHeader);
//        adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, list);
        adapter = new MiddlePageAdapter(mContext, lists);
        listView.setAdapter(adapter);
        //设置下拉刷新监听
        ptrFrameLayout.setPtrHandler(new PtrDefaultHandler() {
            //在这里进行数据加载
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mCurrentPage = 1;
                lists.clear();
                Call<FeedList> callTimeline = SinaRetrofitUtil.getService().getTimeline(
                        BaseApplication.getmAccessToken().getToken(), mCurrentPage, Constants.PAGECOUNT);

                System.out.println("哈哈"+BaseApplication.getmAccessToken().getToken());
                callTimeline.enqueue(new Callback<FeedList>() {
                    @Override
                    public void onResponse(Call<FeedList> call, Response<FeedList> response) {
                        if (response.body() == null) {
                            System.out.println("网络请求失败");
                            return;
                        }
                        for (FeedListBean bean : response.body().getStatuses()) {
                            System.out.println("创建时间"+bean.getCreated_at());
                            System.out.println("文本"+bean.getText());
                            System.out.println("图片"+bean.getPic_urls());
                            System.out.println("文本长度"+bean.getTextLength());

                            lists.add(bean);
                        }
                        ptrFrameLayout.refreshComplete();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<FeedList> call, Throwable t) {
                        LogUtil.d("d:", t.getLocalizedMessage());
                    }
                });
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "position:" + position, Toast.LENGTH_SHORT).show();
            }
        });
//        ptrFrameLayout.setPtrHandler(new PtrHandler() {
        //返回控件是否可以执行下拉刷新操作
//            @Override
//            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
//                return true;
//            }
//
//            @Override
//            public void onRefreshBegin(PtrFrameLayout frame) {
//
//            }
//        });


        //上拉加载更多
        mLoadMoreListViewContainer = (LoadMoreListViewContainer) view.findViewById(R.id.load_more_list_view_container);
        mLoadMoreListViewContainer.setAutoLoadMore(true);//设置是否自动加载更多
        mLoadMoreListViewContainer.useDefaultHeader();
        //5.添加加载更多的事件监听
        mLoadMoreListViewContainer.setLoadMoreHandler(new LoadMoreHandler() {
            @Override
            public void onLoadMore(LoadMoreContainer loadMoreContainer) {
                //模拟加载更多的业务处理
                mLoadMoreListViewContainer.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCurrentPage += 1;
                        Call<FeedList> callTimeline = SinaRetrofitUtil.getService().getTimeline(BaseApplication.getmAccessToken().getToken(), mCurrentPage, Constants.PAGECOUNT);
                        callTimeline.enqueue(new Callback<FeedList>() {
                            @Override
                            public void onResponse(Call<FeedList> call, Response<FeedList> response) {
                                if (response.body() == null)
                                    return;
                                for (FeedListBean bean : response.body().getStatuses()) {
                                    lists.add(bean);
                                }
                                mLoadMoreListViewContainer.loadMoreFinish(false, true);
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<FeedList> call, Throwable t) {
                                LogUtil.d("d:", t.getLocalizedMessage());
                            }
                        });
                    }
                }, 1000);
            }
        });
    }

    private void initData() {
        lists = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            FeedListBean listBean = new FeedListBean();
            List<PhotoBean> photoList = new ArrayList<>();
            if (i % 2 == 0) {
                PhotoBean photoBean = new PhotoBean();
                photoList.add(photoBean);
            }
            listBean.setPic_urls(photoList);
            lists.add(listBean);
        }
    }
}