package bogdanovbayar.mojohamster.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity
    implements BaseView {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }

    public void setUpToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            int backCount = getSupportFragmentManager().getBackStackEntryCount();
            if (backCount == 0) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                toolbar.setNavigationOnClickListener(v -> onBackPressed());
            }
        });
    }

    public void setUpToolbarWithoutFragments(Toolbar toolbarWithoutFragments) {
        setSupportActionBar(toolbarWithoutFragments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
        toolbarWithoutFragments.setNavigationOnClickListener(v -> onBackPressed());
    }

    public void setUpToolbarWithoutFragmentsWithWhiteButton(Toolbar toolbarWithoutFragments, boolean hasNavigation) {
        setSupportActionBar(toolbarWithoutFragments);
        getSupportActionBar().setDisplayHomeAsUpEnabled(hasNavigation);
        getSupportActionBar().setHomeButtonEnabled(hasNavigation);
        getSupportActionBar().setDisplayShowHomeEnabled(hasNavigation);
        getSupportActionBar().setTitle("");
        if (hasNavigation) {
            toolbarWithoutFragments.getNavigationIcon().setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_ATOP);
            toolbarWithoutFragments.setNavigationOnClickListener(v -> {
                onBackPressed();
            });
        }
    }

    public void hideKeyboard() {
        if (this.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void replaceFragment(int idContainer, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(idContainer, fragment, fragment.getClass().getName().toString()).commit();
    }

    public void replaceRootFragment(int idContainer, Fragment fragment) {
        if (getSupportFragmentManager().getBackStackEntryCount() != 0) {
            int id = getSupportFragmentManager().getBackStackEntryAt(0).getId();
            try {
                getSupportFragmentManager().popBackStackImmediate(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            } catch (IllegalStateException e) {
                return;
            }
        }
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(idContainer, fragment, fragment.getClass().getName().toString())
                .commit();
    }

    public void addFragment(int idContainer, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(idContainer, fragment, fragment.getClass().getName().toString()).addToBackStack(fragment.getClass().getName()).commit();
    }

    public void clearBackstack() {
        int stackSize = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < stackSize; i++) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void noInternetConnection() {
        Toast.makeText(this, "Отсутствует интернет соединение", Toast.LENGTH_SHORT).show();
    }

    public void showSoftKeyboard(View view) {
        Log.i(TAG, "setFocusOnEditText: ");
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
            Log.i(TAG, "hideSoftKeyboard: ");
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            getCurrentFocus().clearFocus();
        }
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
