package bogdanovbayar.mojohamster.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import bogdanovbayar.mojohamster.R;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment
    implements BaseView {

    protected abstract void initViews();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        initViews();
    }

    public void hideKeyboard() {
        if (getActivity() != null && getActivity().getCurrentFocus() != null
                && getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) != null
                && getActivity().getCurrentFocus().getWindowToken() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void setTitle(String title) {
        if (getActivity() != null)
            ((BaseActivity) getActivity()).getSupportActionBar().setTitle(title);
    }

    public void replaceFragment(int idContainer, Fragment fragment) {
        if (getActivity() != null) {
            getActivity()
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(idContainer, fragment, fragment.getClass().getName().toString())
                    .commit();
        }
    }

    public void replaceFragmentWithBackstack(int idContainer, Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
                    .replace(idContainer, fragment, fragment.getClass().getName().toString())
                    .addToBackStack(fragment.getClass().getName().toString())
                    .commit();
        }
    }

    public void addFragmentWithBackstack(int idContainer, Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations(R.anim.slide_in_up, R.anim.slide_in_up, R.anim.slide_out_bottom, R.anim.slide_out_bottom)
                    .add(idContainer, fragment, fragment.getClass().getName().toString())
                    .addToBackStack(fragment.getClass().getName().toString())
                    .commit();
        }
    }

    public boolean isOnline() {
        return getActivity() != null && ((BaseActivity) getActivity()).isOnline();
    }

    @Override
    public void showError(String errorMessage) {
        if (getActivity() != null) {
            Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
