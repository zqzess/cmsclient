package cms.app.cms.admin.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;

import android.os.Bundle;
import android.widget.TabHost;

import java.util.ArrayList;
import java.util.List;

import cms.app.R;
import cms.app.cms.admin.model.TabItem;

/**
 * @author ZQZESS
 * @date 2020/6/25-18:45
 * GitHub：
 * email：
 * description：
 */
public class AdminMain extends AppCompatActivity {
    public static final int TAB_APPROVAL = 0;
    public static final int TAB_SEARCH = 1;
    public static final int TAB_CLASS = 2;

    private List<TabItem> mFragmentList;

    private FragmentTabHost mFragmentTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        getSupportActionBar().hide();
        initTabItemData();
    }

    private void initTabItemData() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new TabItem(
                R.mipmap.ic_launcher_shenhenomal,
                R.mipmap.ic_launcher_shenhenselect,
                "审核",
                UserApprovalListFragment.class, R.color.courseTable3
        ));

        mFragmentList.add(new TabItem(
                R.mipmap.ic_launcher_chazhaonomal,
                R.mipmap.ic_launcher_chazhaoselect,
                "查找",
                AdminSearchFragment.class, R.color.courseTable3
        ));

        mFragmentList.add(new TabItem(
                R.mipmap.ic_launcher_classnomal,
                R.mipmap.ic_launcher_classselect,
                "班级",
                AdminManagerClassFragment.class, R.color.courseTable3
        ));

        mFragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        // 绑定 FragmentManager
        mFragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        // 删除分割线
//        mFragmentTabHost.getTabWidget().setDividerDrawable(null);

        for (int i = 0; i < mFragmentList.size(); i++) {
            TabItem tabItem = mFragmentList.get(i);
            // 创建 tab
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(
                    tabItem.getTabText()).
                    setIndicator(tabItem.getTabView(AdminMain.this));
            // 将创建的 tab 添加到底部 tab 栏中（ @android:id/tabs ）
            // 将 Fragment 添加到页面中（ @android:id/tabcontent ）
            mFragmentTabHost.addTab(tabSpec, tabItem.getFragmentClass(), null);
            // 底部 tab 栏设置背景图片
            //mFragmentTabHost.getTabWidget().setBackgroundResource(R.drawable.bottom_bar);
            mFragmentTabHost.getTabWidget().setBackgroundResource(R.color.courseTable6);

            // 默认选中第一个 tab
            if (i == 0) {
                tabItem.setChecked(true);
            } else {
                tabItem.setChecked(false);
            }
        }

        // 切换 tab 时，回调此方法
        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < mFragmentList.size(); i++) {
                    TabItem tabItem = mFragmentList.get(i);
                    // 通过 tag 检查用户点击的是哪个 tab
                    if (tabId.equals(tabItem.getTabText())) {
                        tabItem.setChecked(true);
                    } else {
                        tabItem.setChecked(false);
                    }
                }
            }
        });
    }
}
