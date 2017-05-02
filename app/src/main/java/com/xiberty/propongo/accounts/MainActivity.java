
package com.xiberty.propongo.accounts;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import com.xiberty.propongo.R;
import com.xiberty.propongo.accounts.fragments.AboutFragment;
import com.xiberty.propongo.accounts.fragments.ProfileFragment;
import com.xiberty.propongo.accounts.fragments.SettingsFragment;
import com.xiberty.propongo.contrib.Store;
import com.xiberty.propongo.contrib.api.WS;
import com.xiberty.propongo.credentials.CredentialService;
import com.xiberty.propongo.credentials.LoginActivity;
import com.xiberty.propongo.credentials.responses.UserProfile;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.mateware.snacky.Snacky;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    private static final String TAG = MainActivity.class.getSimpleName();
    AccountHeader accountHeader;

    public enum Menues {
        PROFILE(1001),
        SETTINGS(1002),
        ABOUT(1003),
        LOGOUT(1004);

        public int id;
        private Menues(int id) {
            this.id = id;
        }
        public long getID() {
            return (long) this.id;
        }
    }

    public Drawer drawer;
    MainContract.Presenter presenter;
    AccountService accountService;
    CredentialService credentialService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setToolbar();

        // TODO Validar si la sesion esta vigente
        accountService = WS.makeService(AccountService.class, Store.getCredential(this));
        credentialService = WS.makeService(CredentialService.class);
        presenter = new MainPresenter(this, accountService, credentialService);
        setUserProfile();
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

    private void showMenu(long identifier) {
        if(identifier==Menues.PROFILE.getID()){
            setContainer(new ProfileFragment());
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

        final IProfile userProfile = new ProfileDrawerItem()
                .withName(profile.fullName())
                .withEmail(profile.email());

        if(profile.photo() != null && profile.photo().length() > 0){
            userProfile.withIcon(profile.photo());
        } else {
            userProfile.withIcon(R.drawable.avatar);
        }


        accountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.coding)
                .addProfiles(userProfile)
                .build();


        PrimaryDrawerItem MenuProfile = new PrimaryDrawerItem()
                .withName(getString(R.string.menu_profile)).withIdentifier(Menues.PROFILE.id).withIcon(R.drawable.ic_profile);
        PrimaryDrawerItem MenuSettings = new PrimaryDrawerItem()
                .withName(getString(R.string.menu_settings)).withIdentifier(Menues.SETTINGS.id).withIcon(R.drawable.ic_settings);
        PrimaryDrawerItem MenuAbout = new PrimaryDrawerItem()
                .withName(getString(R.string.menu_about)).withIdentifier(Menues.ABOUT.id).withIcon(R.drawable.ic_about);
        PrimaryDrawerItem Menulogout = new PrimaryDrawerItem()
                .withName(getString(R.string.menu_logout)).withIdentifier(Menues.LOGOUT.id).withIcon(R.drawable.ic_logout);

        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withHeader(R.layout.drawer_header)
                .withAccountHeader(accountHeader)
                .addDrawerItems(MenuProfile, MenuSettings, MenuAbout, Menulogout)
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
        drawer.setSelection(Menues.PROFILE.getID());
        drawer.getRecyclerView().setVerticalScrollBarEnabled(false);
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
    public void logoutError(String message) {
        Snacky.builder()
                .setActivty(MainActivity.this)
                .setText(message)
                .setDuration(Snacky.LENGTH_SHORT)
                .error()
                .show();
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


}
