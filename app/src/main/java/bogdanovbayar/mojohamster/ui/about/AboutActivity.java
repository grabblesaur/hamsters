package bogdanovbayar.mojohamster.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import bogdanovbayar.mojohamster.R;
import bogdanovbayar.mojohamster.base.BaseActivity;
import butterknife.BindView;

public class AboutActivity extends BaseActivity {

    @BindView(R.id.about_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.about_hh_ref)
    Button mHhRefButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initViews();
    }

    private void initViews() {
        setUpToolbarWithoutFragmentsWithWhiteButton(mToolbar, true);
        mToolbar.setTitle(R.string.about);
        mHhRefButton.setOnClickListener(v -> {
            String url = "https://ulan-ude.hh.ru/applicant/resumes/view?resume=248badb2ff03f0ed320039ed1f34375669497a";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }
}
