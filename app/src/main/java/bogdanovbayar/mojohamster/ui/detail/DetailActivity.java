package bogdanovbayar.mojohamster.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import bogdanovbayar.mojohamster.R;
import bogdanovbayar.mojohamster.base.BaseActivity;
import bogdanovbayar.mojohamster.base.GlideApp;
import bogdanovbayar.mojohamster.data.model.Hamster;
import butterknife.BindView;

public class DetailActivity extends BaseActivity {

    public static final String EXTRA_HAMSTER = "extra-hamster";

    @BindView(R.id.detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.detail_image)
    ImageView mImageView;
    @BindView(R.id.detail_title)
    TextView mTitleTextView;
    @BindView(R.id.detail_desc)
    TextView mDescTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
    }

    private void initViews() {
        setUpToolbarWithoutFragmentsWithWhiteButton(mToolbar);
        Hamster hamster = (Hamster) getIntent().getSerializableExtra(EXTRA_HAMSTER);
        if (hamster != null) {
            GlideApp.with(this)
                    .load(hamster.getImageUrl())
                    .into(mImageView);
            mTitleTextView.setText(hamster.getTitle());
            mDescTextView.setText(hamster.getDescription());
        }
    }
}
