package com.xiberty.propongo.councils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.joaquimley.faboptions.FabOptions;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.accounts.MainActivity;
import com.xiberty.propongo.contrib.utils.UIUtils;
import com.xiberty.propongo.contrib.views.AppBarStateChangeListener;
import com.xiberty.propongo.contrib.views.XTextViewBold;
import com.xiberty.propongo.councils.adapters.SectionsPagerAdapter;
import com.xiberty.propongo.councils.fragments.AllCouncilFragment;
import com.xiberty.propongo.councils.fragments.BiographyFragment;
import com.xiberty.propongo.councils.fragments.DirectiveFragment;
import com.xiberty.propongo.councils.fragments.GeneralProposalsFragment;
import com.xiberty.propongo.database.CouncilMan;
import com.xiberty.propongo.database.Macrodistrict;
import com.xiberty.propongo.database.ProposalDB;
import com.xiberty.propongo.database.ProposalDB_Table;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;


public class CouncilManDetailActivity extends AppCompatActivity {

    private final static float EXPAND_AVATAR_SIZE_DP = 80f;
    private final static float COLLAPSED_AVATAR_SIZE_DP = 32f;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_image)
    ImageView mSpace;
    @BindView(R.id.toolbar_title)
    XTextViewBold mToolbarTextView;
    @BindView(R.id.textView_title)
    XTextViewBold mTitleTextView;
    @BindView(R.id.textView_email)
    TextView mEmail;
    @BindView(R.id.imageView_avatar)
    CircleImageView mAvatarImageView;
    @BindView(R.id.nameMacro)
    XTextViewBold mNameMacro;
    @BindView(R.id.imgFlag)
    ImageView mFlag;
    @BindView(R.id.nameFlag)
    XTextViewBold nNameFlag;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.pager)
    ViewPager pager;
    @BindView(R.id.scrollView)
    NestedScrollView scrollView;
    @BindView(R.id.fab_options)
    FabOptions fabOptions;


    private AppBarStateChangeListener mAppBarStateChangeListener;

    private int[] mAvatarPoint = new int[2], mSpacePoint = new int[2], mToolbarTextPoint =
            new int[2], mTitleTextViewPoint = new int[2];
    private float mTitleTextSize;

    private CouncilMan councilmanSelected;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_council_man_detail);
        context = this;
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        int CouncilManId = bundle.getInt(Constants.KEY_COUNCILMAN_ID);

        councilmanSelected = CouncilMan.getCouncilman(this, CouncilManId);

        setTabs();
        setUpViews();
        fetchAvatar();
        setupFab();

    }

    private void setupFab() {
        fabOptions.setButtonsMenu(R.menu.social_items);
        fabOptions.setBackgroundColor(R.color.white);
        fabOptions.setButtonColor(R.id.optionFacebook,R.color.colorFacebook);
        fabOptions.setButtonColor(R.id.optionTwitter,R.color.colorTwitter);
        fabOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = v.getId();
                Intent browserIntent;
                switch (id){
                    case R.id.optionFacebook:
                        if (councilmanSelected.facebook !=null){
                            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(councilmanSelected.facebook));
                            startActivity(browserIntent);
                        }else{
                            Toasty.custom(context,"El concejal no cuenta con Facebook",R.drawable.ic_facebook,Color.rgb(59,89,152),Toast.LENGTH_SHORT,true,true).show();
                        }
                        break;
                    case R.id.optionTwitter:
                        if (councilmanSelected.twitter !=null) {
                            browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(councilmanSelected.twitter));
                            startActivity(browserIntent);
                        }else{
                            Toasty.custom(context,"El concejal no cuenta con Twitter",R.drawable.ic_twitter,Color.rgb(29,202,255),Toast.LENGTH_SHORT,true,true).show();

                        }
                        break;
                }
            }
        });
    }

    private void setTabs() {
        tabs.setBackgroundColor(Color.rgb(46, 46, 46));
        tabs.setTabTextColors(Color.WHITE, Color.rgb(254, 190, 17));
        tabs.setSelectedTabIndicatorColor(Color.rgb(254, 190, 17));
        setupViewPager(pager);
        tabs.setupWithViewPager(pager);
    }

    private void setUpViews() {
        mTitleTextSize = mTitleTextView.getTextSize();
        setUpToolbar();
        setUpAmazingAvatar();
    }

    private void setUpToolbar() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void setUpAmazingAvatar() {
        mAppBarStateChangeListener = new AppBarStateChangeListener() {

            @Override
            public void onStateChanged(AppBarLayout appBarLayout,
                                       State state) {

                if (state == State.IDLE || state == State.EXPANDED) {
                    mSpace.setVisibility(View.GONE);
                    mToolbarTextView.setVisibility(View.GONE);
                } else {
                    mSpace.setVisibility(View.VISIBLE);
                    mToolbarTextView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onOffsetChanged(State state, float offset) {
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
        if (councilmanSelected != null) {
            if (councilmanSelected.avatar != null) {
                Glide.with(this)
                        .load(councilmanSelected.avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mAvatarImageView);
                Glide.with(this)
                        .load(councilmanSelected.avatar)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mSpace);
            } else {
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
            if (councilmanSelected.macrodistrict != null) {
                Macrodistrict macrodistrict = councilmanSelected.macrodistrict;
                mNameMacro.setText(macrodistrict.getName());
            }

        } else {
            /**Set Default Content**/
            mTitleTextView.setText("Concejal");
            Glide.with(this).load(R.drawable.code).into(mFlag);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        BiographyFragment biographyFragment = new BiographyFragment();
        GeneralProposalsFragment generalProposalsFragment = new GeneralProposalsFragment();
        List<ProposalDB> proposals = SQLite.select().
                from(ProposalDB.class).
                where(ProposalDB_Table.councilmen.is(String.valueOf(councilmanSelected.id))).
                and(ProposalDB_Table.status.is("ACCEPTED")).
                queryList();

        Gson gson = new Gson();
        String proposalStr = gson.toJson(proposals);

        Bundle bundle = new Bundle();
        bundle.putInt("ID", councilmanSelected.id);
        bundle.putString("Proposals", proposalStr);
        bundle.putString("About", councilmanSelected.bio);

        biographyFragment.setArguments(bundle);
        generalProposalsFragment.setArguments(bundle);

        adapter.addFragment(generalProposalsFragment, "PROPUESTAS");
        adapter.addFragment(biographyFragment, "BIOGRAFIA");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();

        Bundle bundle = getIntent().getExtras();
        String TAG = bundle.getString(Constants.KEY_BASE_CLASS);
        if (TAG.equals(AllCouncilFragment.class.getSimpleName())) {
            Intent intent = new Intent(CouncilManDetailActivity.this, MainActivity.class);
            intent.putExtra(Constants.MENU_STATE, 3);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else if (TAG.equals(DirectiveFragment.class.getSimpleName())) {
            Intent intent = new Intent(CouncilManDetailActivity.this, MainActivity.class);
            intent.putExtra(Constants.MENU_STATE, 4);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        } else {
            finish();
        }
        return true;
    }


}
