package com.stone.wanandroid

import android.content.Intent
import android.support.v4.app.FragmentTransaction
import com.blankj.utilcode.util.LogUtils
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.stone.common.base.BaseActivity
import com.stone.common.util.StatusBarUtil
import com.stone.wanandroid.bean.LoginBean
import com.stone.wanandroid.fragment.CategoryFragment
import com.stone.wanandroid.bean.TabEntity
import com.stone.wanandroid.bean.event.LoginEvent
import com.stone.wanandroid.bean.event.LogoutEvent
import com.stone.wanandroid.constant.CommonConstant
import com.stone.wanandroid.fragment.HomeFragment
import com.stone.wanandroid.fragment.MineFragment
import com.stone.wanandroid.fragment.ProjectFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.Subscribe

class MainActivity : BaseActivity(), OnTabSelectListener {
    companion object {
        const val FRAGMENT_NONE = -1
        const val FRAGMENT_HOME = 0
        const val FRAGMENT_PROJECT = 1
        const val FRAGMENT_CATEGORY = 2
        const val FRAGMENT_MINE = 3
    }

    private val mTitles = arrayOf("首页", "项目", "体系", "我的")
    private val mSelectIcons = arrayOf(
        R.drawable.icon_home_select,
        R.drawable.icon_project_select,
        R.drawable.icon_category_select,
        R.drawable.icon_mine_select
    )
    private val mUnSelectIcons = arrayOf(
        R.drawable.icon_home_unselect,
        R.drawable.icon_project_unselect,
        R.drawable.icon_category_unselect,
        R.drawable.icon_mine_unselect
    )

    private var mHomeFragment: HomeFragment? = null
    private var mProjectFragment: ProjectFragment? = null
    private var mCategoryFragment: CategoryFragment? = null
    private var mMineFragment: MineFragment? = null

    private val mTabEntities = ArrayList<CustomTabEntity>()
    private var mIndex: Int = 0

    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initData() {
    }

    override fun initView() {
        if (intent.hasExtra(CommonConstant.MAIN_ACTIVITY_POSITION)) {
            mIndex = intent.getIntExtra(CommonConstant.MAIN_ACTIVITY_POSITION, 0)
        }

        StatusBarUtil.setRootViewFitsSystemWindows(this, false)
        StatusBarUtil.setStatusBarDarkTheme(this, false)

        (0 until mTitles.size).mapTo(mTabEntities) {
            TabEntity(
                mTitles[it],
                mSelectIcons[it],
                mUnSelectIcons[it]
            )
        }

        tb_main_activity.setTabData(mTabEntities)
        tb_main_activity.setOnTabSelectListener(this)
        tb_main_activity.currentTab = mIndex

        LogUtils.d("initView-----------$mIndex")
        selectFragment(mIndex)
    }

    override fun start() {
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        mIndex = intent.getIntExtra(CommonConstant.MAIN_ACTIVITY_POSITION, 0)
        LogUtils.d("onNewIntent-----------$mIndex")
        selectFragment(mIndex)
    }


    override fun onTabSelect(position: Int) {
        selectFragment(position)
    }

    override fun onTabReselect(position: Int) {
    }

    private fun selectFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragment(transaction)
        when (position) {
            FRAGMENT_HOME -> {
                StatusBarUtil.setStatusBarDarkTheme(this, HomeFragment.isStatusBarDark)
                mHomeFragment?.let {
                    transaction.show(it)
                } ?: HomeFragment.getInstance(mTitles[position]).let {
                    mHomeFragment = it
                    transaction.add(R.id.fl_container_main_activity, it, "home")
                }
            }
            FRAGMENT_PROJECT -> {
                StatusBarUtil.setStatusBarDarkTheme(this, true)
                mProjectFragment?.let {
                    transaction.show(it)
                } ?: ProjectFragment.getInstance(mTitles[position]).let {
                    mProjectFragment = it
                    transaction.add(R.id.fl_container_main_activity, it, "project")
                }
            }
            FRAGMENT_CATEGORY -> {
                StatusBarUtil.setStatusBarDarkTheme(this, true)
                mCategoryFragment?.let {
                    transaction.show(it)
                } ?: CategoryFragment.getInstance(mTitles[position]).let {
                    mCategoryFragment = it
                    transaction.add(R.id.fl_container_main_activity, it, "category")
                }
            }
            FRAGMENT_MINE -> {
                StatusBarUtil.setStatusBarDarkTheme(this, true)

                mMineFragment?.let {
                    transaction.show(it)
                } ?: MineFragment.getInstance(mTitles[position]).let {
                    mMineFragment = it
                    transaction.add(R.id.fl_container_main_activity, it, "mine")
                }
            }
            else -> {

            }
        }

        mIndex = position
        tb_main_activity.currentTab = mIndex
        transaction.commitAllowingStateLoss()
    }

    private fun hideFragment(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mProjectFragment?.let { transaction.hide(it) }
        mCategoryFragment?.let { transaction.hide(it) }
        mMineFragment?.let { transaction.hide(it) }
    }

    override fun isRegisterEventBus(): Boolean {
        return true
    }

    @Subscribe
    fun logoutEvent(event: LogoutEvent) {
        LogUtils.d("logoutEvent-----------")
        mHomeFragment?.let { it.lazyLoad() }
        mProjectFragment?.let { it.lazyLoad() }
        mCategoryFragment?.let { it.lazyLoad() }
        mMineFragment?.let { it.lazyLoad() }
    }

    @Subscribe
    fun loginEvent(event: LoginEvent) {
        LogUtils.d("loginEvent-----------")
        mHomeFragment?.let { it.lazyLoad() }
        mProjectFragment?.let { it.lazyLoad() }
        mCategoryFragment?.let { it.lazyLoad() }
        mMineFragment?.let { it.lazyLoad() }
    }
}
