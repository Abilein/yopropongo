package com.xiberty.propongo.councils;

import android.graphics.Color;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.Space;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.contrib.utils.UIUtils;
import com.xiberty.propongo.contrib.views.AppBarStateChangeListener;
import com.xiberty.propongo.councils.adapters.SectionsPagerAdapter;
import com.xiberty.propongo.councils.fragments.BiographyFragment;
import com.xiberty.propongo.councils.fragments.CouncilProposalsFragment;
import com.xiberty.propongo.councils.fragments.DirectiveFragment;
import com.xiberty.propongo.database.CouncilMan;
import com.xiberty.propongo.database.Macrodistrict;

import java.util.List;


public class CouncilManDetailActivity extends AppCompatActivity {

    private final static float EXPAND_AVATAR_SIZE_DP = 80f;
    private final static float COLLAPSED_AVATAR_SIZE_DP = 32f;

    private AppBarLayout mAppBarLayout;
    private ImageView mAvatarImageView,mSpace,mFlag;
    private TextView mToolbarTextView, mTitleTextView,mEmail,nNameFlag,mNameMacro;
    private Toolbar mToolBar;
    private TabLayout tabs;
    private ViewPager pager;


    private AppBarStateChangeListener mAppBarStateChangeListener;

    private int[] mAvatarPoint = new int[2], mSpacePoint = new int[2], mToolbarTextPoint =
            new int[2], mTitleTextViewPoint = new int[2];
    private float mTitleTextSize;

    private CouncilMan councilmanSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_council_man_detail);

        Bundle bundle = getIntent().getExtras();
        int CouncilManId = bundle.getInt(Constants.KEY_COUNCILMAN_ID);

        councilmanSelected = CouncilMan.getCouncilman(this,CouncilManId);

        findViews();
        setUpViews();
        fetchAvatar();

    }

    private void findViews() {
        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAvatarImageView = (ImageView) findViewById(R.id.imageView_avatar);
        mTitleTextView = (TextView) findViewById(R.id.textView_title);
        mToolBar = (Toolbar) findViewById(R.id.toolbar);
        mEmail= (TextView) findViewById(R.id.textView_email);

        mToolbarTextView = (TextView) findViewById(R.id.toolbar_title);
        mSpace = (ImageView) findViewById(R.id.toolbar_image);
        mFlag = (ImageView) findViewById(R.id.imgFlag);
        nNameFlag= (TextView) findViewById(R.id.nameFlag);
        mNameMacro= (TextView) findViewById(R.id.nameMacro);

        tabs= (TabLayout) findViewById(R.id.tabs);
        tabs.addTab(tabs.newTab().setText("BIOGRAFIA"));
        tabs.addTab(tabs.newTab().setText("PROPUESTAS"));
        tabs.setBackgroundColor(Color.BLACK);
        tabs.setTabTextColors(Color.WHITE,Color.YELLOW);
        pager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(pager);
        tabs.setupWithViewPager(pager);


    }

    private void setUpViews() {
        mTitleTextSize = mTitleTextView.getTextSize();
        setUpToolbar();
        setUpAmazingAvatar();
    }

    private void setUpToolbar() {
        setSupportActionBar(mToolBar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
    private void setUpAmazingAvatar() {
        mAppBarStateChangeListener = new AppBarStateChangeListener() {

            @Override
            public void onStateChanged(AppBarLayout appBarLayout,
                                       AppBarStateChangeListener.State state) {

                if (state==State.IDLE || state==State.EXPANDED){
                    mSpace.setVisibility(View.GONE);
//                    mSpace.animate().alpha(3.0f);
                    mToolbarTextView.setVisibility(View.GONE);
                }else{
                    mSpace.setVisibility(View.VISIBLE);
//                    mSpace.animate().alpha(0.0f);
                    mToolbarTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onOffsetChanged(AppBarStateChangeListener.State state, float offset) {
                    translationView(offset);
            }
        };
        mAppBarLayout.addOnOffsetChangedListener(mAppBarStateChangeListener);
    }
    private void translationView(float offset) {
        float xOffset = -(mAvatarPoint[0] - mSpacePoint[0]) * offset;
        float yOffset = -(mAvatarPoint[1] - mSpacePoint[1]) * offset;
        float xTitleOffset = -(mTitleTextViewPoint[0] - mToolbarTextPoint[0]) * offset;
        float yTitleOffset = -(mTitleTextViewPoint[1] - mToolbarTextPoint[1]) * offset;
        int newSize = UIUtils.convertDpToPixelSize(
                EXPAND_AVATAR_SIZE_DP - (EXPAND_AVATAR_SIZE_DP - COLLAPSED_AVATAR_SIZE_DP) * offset,
                CouncilManDetailActivity
                        .this);
        float newTextSize =
                mTitleTextSize - (mTitleTextSize - mToolbarTextView.getTextSize()) * offset;
        mAvatarImageView.getLayoutParams().width = newSize;
        mAvatarImageView.getLayoutParams().height = newSize;
        mAvatarImageView.setTranslationX(xOffset);
        mAvatarImageView.setTranslationY(yOffset);
        mTitleTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, newTextSize);
        mTitleTextView.setTranslationX(xTitleOffset);
        mTitleTextView.setTranslationY(yTitleOffset);



    }


    private void fetchAvatar() {

        if (councilmanSelected!=null) {
            if (councilmanSelected.avatar !=null){
                Glide.with(this)
                        .load(councilmanSelected.avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mAvatarImageView);
                Glide.with(this)
                        .load(councilmanSelected.avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mSpace);
            }else{
                Glide.with(this)
                        .load(R.drawable.avatar)
                        .into(mAvatarImageView);
            }

            String full_name = councilmanSelected.first_name + " " + councilmanSelected.last_name;
            mTitleTextView.setText(full_name);
            mToolbarTextView.setText(full_name);
            mEmail.setText(councilmanSelected.email);
            Glide.with(this).load(councilmanSelected.getFlag(this)).into(mFlag);
            nNameFlag.setText(councilmanSelected.getFlagName());
            Macrodistrict macrodistrict = councilmanSelected.macrodistrict;
            mNameMacro.setText(macrodistrict.getName());
        }else{
            /**Set Default Content**/
            mTitleTextView.setText("Consejal");
            Glide.with(this).load(R.drawable.code).into(mFlag);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        BiographyFragment biographyFragment = new BiographyFragment();
        CouncilProposalsFragment councilProposalsFragment = new CouncilProposalsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("ID",councilmanSelected.id);

        biographyFragment.setArguments(bundle);
        councilProposalsFragment.setArguments(bundle);

        adapter.addFragment(biographyFragment, "BIOGRAFIA");
        adapter.addFragment(councilProposalsFragment, "PROPUESTAS");
        viewPager.setAdapter(adapter);
    }
}
