
package com.xiberty.propongo.accounts;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.afollestad.materialdialogs.MaterialDialog;
import com.facebook.login.LoginManager;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.model.interfaces.Nameable;
import com.xiberty.propongo.Constants;
import com.xiberty.propongo.R;
import com.xiberty.propongo.accounts.forms.CouncilForm;
import com.xiberty.propongo.accounts.fragments.AboutFragment;
import com.xiberty.propongo.accounts.fragments.ProfileFragment;
import com.xiberty.propongo.accounts.fragments.SettingsFragment;
import com.xiberty.propongo.councils.CouncilService;
import com.xiberty.propongo.councils.fragments.CommissionsFragment;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.councils.fragments.DirectiveFragment;
import com.xiberty.propongo.councils.fragments.InboxFragment;
import com.xiberty.propongo.councils.fragments.ProposalsFragment;
import com.xiberty.propongo.credentials.CredentialService;
import com.xiberty.propongo.credentials.LoginActivity;
import com.xiberty.propongo.credentials.responses.UserProfile;
import com.xiberty.propongo.database.Council;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.mateware.snacky.Snacky;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private static final String TAG = MainActivity.class.getSimpleName();
    AccountHeader accountHeader;


    public enum Menues {
        INBOX(1001),
        PROFILE(1002),
        COUNCIL(1003),
        COMISSIONS(1004),
        PROPOSAL(1005),
        SETTINGS(1006),
        ABOUT(1007),
        LOGOUT(1008);

        public int id;
        private Menues(int id) {
            this.id = id;
        }
        public long getID() {
            return (long) this.id;
        }
    }

    public Drawer drawer;
    public MainContract.Presenter presenter;

    AccountService accountService;
    CredentialService credentialService;
    CouncilService councilService;

    CouncilForm councilForm;

    UserProfile profile;

    int state = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setToolbar();

        // Service for account
        accountService = WS.makeService(AccountService.class, Store.getCredential(this));
        credentialService = WS.makeService(CredentialService.class);
        councilService = WS.makeService(CouncilService.class, Store.getCredential(this));
        presenter = new MainPresenter(this, accountService, credentialService, councilService);

        // Service for Syncing
        setUserProfile();
        setCouncil();

    }



    @Override
    public void onResume(){
        super.onResume();
        setUserProfile();
    }

    public void setUserProfile(){
        UserProfile profile = Store.getProfile(this);
        setDrawer(profile);
    }

    public void  setCouncil(){
        boolean hasCouncil= false;
        if (Store.getDefaultCouncil(this)!=null) {
            hasCouncil= true;
        }
        if (!hasCouncil) {
            presenter.saveCouncils(getApplicationContext());
        } else {
//            showCouncils();
        }

    }

    private void showMenu(long identifier) {
        if(identifier==Menues.INBOX.getID()){
            setContainer(new InboxFragment());
            state=1;
        } else if(identifier==Menues.PROFILE.getID()){
            setContainer(new ProfileFragment());
            state=2;
        } else if(identifier==Menues.COUNCIL.getID()){
            setContainer(new DirectiveFragment());
            state=3;
        } else if(identifier==Menues.COMISSIONS.getID()){
            CommissionsFragment commissions = new CommissionsFragment();
            Bundle bcomm = new Bundle();
            bcomm.putBoolean(Constants.KEY_IS_ALONE, true);
            commissions.setArguments(bcomm);
            setContainer(commissions);
            state=4;
        } else if(identifier==Menues.PROPOSAL.getID()){
            setContainer(new ProposalsFragment());
            state=5;
        } else if(identifier==Menues.SETTINGS.getID()){
            setContainer(new SettingsFragment());
        } else if(identifier==Menues.ABOUT.getID()){
            setContainer(new AboutFragment());
        } else if(identifier==Menues.LOGOUT.getID()){
            toolbar.setTitle(""); toolbar.setSubtitle("");
            logout();
        }
    }

    public void setContainer(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
    }


    public void setToolbar() {
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }


    @Override
    public void setDrawer(UserProfile profile) {

        this.profile = profile;

        final ArrayList<Council> councils = Store.getCouncils(this);

        if (councils == null){
            IProfile userProfile = new ProfileDrawerItem()
                    .withName(profile.fullName())
                    .withEmail(profile.email());

            if(profile.photo() != null && profile.photo().length() > 0){
                userProfile.withIcon(profile.photo());
            } else {
                userProfile.withIcon(R.drawable.avatar);
            }

            accountHeader = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.header_cover)
                    .addProfiles(userProfile)
                    .build();
        }else{

            Council defaulCouncil = Store.getDefaultCouncil(this);
            presenter.saveCouncilmen(this);
            presenter.saveCommissions(this);
            presenter.saveProposals(this);

            AccountHeaderBuilder header = new AccountHeaderBuilder()
                    .withActivity(this)
                    .withHeaderBackground(R.drawable.header_cover);

            for (Council council : councils){
                if (!council.name().equals(defaulCouncil.name())){
                    IProfile councilProfile = new ProfileDrawerItem()
                            .withName(council.name())
                            .withEmail(council.department());
                    if (council.logo() !=null && council.logo().length()>0)
                        councilProfile.withIcon(council.logo());
                    else
                        councilProfile.withIcon(R.drawable.avatar);

                header.addProfiles(councilProfile);
                }
            }

            //Mark Council Profile that you've chosen

            IProfile selectedCouncilProfile = new ProfileDrawerItem()
                    .withName(defaulCouncil.name())
                    .withEmail(defaulCouncil.department());
            if (defaulCouncil.logo() !=null && defaulCouncil.logo().length()>0)
                selectedCouncilProfile.withIcon(defaulCouncil.logo());
            else
                selectedCouncilProfile.withIcon(R.drawable.avatar);

            header.addProfiles(selectedCouncilProfile);
            header.withOnAccountHeaderListener(new AccountHeader.OnAccountHeaderListener() {
                @Override
                public boolean onProfileChanged(View view, IProfile profile, boolean currentProfile) {
                    for (Council council: councils) {
                        if (council.name().equals(profile.getName().toString())) {
                            Store.setDefaultCouncil(view.getContext(), council);
                            presenter.saveProposals(view.getContext());
                        }
                    }
                    setDrawer(MainActivity.this.getProfile());
                    return false;
                }
            });
            accountHeader = header.build();
            accountHeader.setActiveProfile(selectedCouncilProfile);
        }

        //YoPropongo Menu Items
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/latoRegular.ttf");
        PrimaryDrawerItem MenuInbox = new PrimaryDrawerItem().withName("Buzon de Entrada").withTypeface(typeface).withIdentifier(Menues.INBOX.id).withIcon(R.drawable.ic_inbox);
        PrimaryDrawerItem MenuProfile = new PrimaryDrawerItem().withName("Perfil").withTypeface(typeface).withIdentifier(Menues.PROFILE.id).withIcon(R.drawable.ic_profile);
        PrimaryDrawerItem MenuCouncil = new PrimaryDrawerItem().withName("Directiva").withTypeface(typeface).withIdentifier(Menues.COUNCIL.id).withIcon(R.drawable.ic_council);
        PrimaryDrawerItem MenuComissions = new PrimaryDrawerItem().withName("Comisiones").withTypeface(typeface).withIdentifier(Menues.COMISSIONS.id).withIcon(R.drawable.ic_commissions);
        PrimaryDrawerItem MenuProposals = new PrimaryDrawerItem().withName("Propuestas").withTypeface(typeface).withIdentifier(Menues.PROPOSAL.id).withIcon(R.drawable.ic_proposals);
        PrimaryDrawerItem MenuAbout = new PrimaryDrawerItem().withName("Acerca de").withTypeface(typeface).withIdentifier(Menues.ABOUT.id).withIcon(R.drawable.ic_about);
        PrimaryDrawerItem Menulogout = new PrimaryDrawerItem().withName("Salir").withTypeface(typeface).withIdentifier(Menues.LOGOUT.id).withIcon(R.drawable.ic_logout);

        try{
            if (profile.is_councilman()) {
                drawer = new DrawerBuilder()
                        .withActivity(this)
                        .withToolbar(toolbar)
                        .withHeader(R.layout.drawer_header)
                        .withAccountHeader(accountHeader)
                        .addDrawerItems(MenuInbox ,MenuProfile,MenuCouncil ,MenuComissions , MenuProposals, MenuAbout, Menulogout)
                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(android.view.View view, int position, IDrawerItem drawerItem) {
                                if (drawerItem != null) {
                                    if (drawerItem instanceof Nameable) {
                                        toolbar.setTitle(((Nameable) drawerItem)
                                                .getName()
                                                .getText(MainActivity.this));
                                    }
                                    showMenu(drawerItem.getIdentifier());
                                }
                                return false;
                            }
                        })
                        .build();
                Bundle bundle = getIntent().getExtras();
                if (bundle != null){
                    switch (bundle.getInt(Constants.MENU_STATE)){
                        case 1:drawer.setSelection(Menues.INBOX.getID());break;
                        case 2:drawer.setSelection(Menues.PROFILE.getID());break;
                        case 3:drawer.setSelection(Menues.COUNCIL.getID());break;
                        case 4:drawer.setSelection(Menues.COMISSIONS.getID());break;
                        case 5:drawer.setSelection(Menues.PROPOSAL.getID());break;
                        default:drawer.setSelection(Menues.COMISSIONS.getID());break;
                    }
                }else{
                    drawer.setSelection(Menues.PROFILE.getID());
                }
                drawer.getRecyclerView().setVerticalScrollBarEnabled(false);
            }else{
                drawer = new DrawerBuilder()
                        .withActivity(this)
                        .withToolbar(toolbar)
                        .withHeader(R.layout.drawer_header)
                        .withAccountHeader(accountHeader)
                        .addDrawerItems(MenuProfile,MenuCouncil ,MenuComissions , MenuProposals, MenuAbout, Menulogout)
                        .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                            @Override
                            public boolean onItemClick(android.view.View view, int position, IDrawerItem drawerItem) {
                                if (drawerItem != null) {
                                    if (drawerItem instanceof Nameable) {
                                        toolbar.setTitle(((Nameable) drawerItem)
                                                .getName()
                                                .getText(MainActivity.this));
                                    }
                                    showMenu(drawerItem.getIdentifier());
                                }
                                return false;
                            }
                        })
                        .build();

                Bundle bundle = getIntent().getExtras();
                if (bundle != null){
                    switch (bundle.getInt(Constants.MENU_STATE)){
                        case 2:drawer.setSelection(Menues.PROFILE.getID());break;
                        case 3:drawer.setSelection(Menues.COUNCIL.getID());break;
                        case 4:drawer.setSelection(Menues.COMISSIONS.getID());break;
                        case 5:drawer.setSelection(Menues.PROPOSAL.getID());break;
                        default:drawer.setSelection(Menues.COMISSIONS.getID());break;
                    }
                }else{
                    drawer.setSelection(Menues.PROFILE.getID());
                }
                drawer.getRecyclerView().setVerticalScrollBarEnabled(false);
            }
        }catch (Exception e){
        }

    }

    public void logout() {
        new MaterialDialog.Builder(this)
                .title(R.string.title_exit)
                .content(R.string.dialog_exit_content)
                .positiveText(R.string.button_yes)
                .negativeText(R.string.button_no)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        LoginManager.getInstance().logOut();
                        presenter.logout(getApplicationContext());
                    }
                    @Override
                    public void onNegative(MaterialDialog dialog) {
                        dialog.onBackPressed();
                    }
                })
                .show();
    }

    @Override
    public void logoutSuccess() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }

    @Override
    public void showError(String message) {
        Snacky.builder()
                .setActivty(MainActivity.this)
                .setText(message)
                .setDuration(Snacky.LENGTH_SHORT)
                .error()
                .show();
    }

    @Override
    public void setCouncilinDrawer() {
        setDrawer(this.profile);
    }

    @Override
    public void showCouncils(List<Council> councils) {

        Store.saveCouncils(getBaseContext(), councils);

        View view = getLayoutInflater ().inflate (R.layout.sheet_select_council, null);
        BottomSheetDialog sheet = new BottomSheetDialog (
                MainActivity.this, R.style.Theme_Design_BottomSheetDialog);
        sheet.setCanceledOnTouchOutside(false);
        councilForm = new CouncilForm(this, view, sheet, councils);
        councilForm.show();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(android.view.View.GONE);
    }

    @Override
    public void onBackPressed() {
        logout();
    }

    public UserProfile getProfile() {
        return profile;
    }

}
