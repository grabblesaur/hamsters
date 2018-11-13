package bogdanovbayar.mojohamster.ui.detail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import bogdanovbayar.mojohamster.R;
import bogdanovbayar.mojohamster.base.BaseActivity;
import bogdanovbayar.mojohamster.base.GlideApp;
import bogdanovbayar.mojohamster.data.model.Hamster;
import butterknife.BindView;

public class DetailActivity extends BaseActivity {

    public static final String EXTRA_HAMSTER = "extra-hamster";
    private static final String TAG = DetailActivity.class.getSimpleName();

    @BindView(R.id.detail_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.detail_image)
    ImageView mImageView;
    @BindView(R.id.detail_title)
    TextView mTitleTextView;
    @BindView(R.id.detail_desc)
    TextView mDescTextView;

    private Hamster hamster;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                getImgCachePath(hamster.getImageUrl());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews() {
        setUpToolbarWithoutFragmentsWithWhiteButton(mToolbar, true);
        hamster = (Hamster) getIntent().getSerializableExtra(EXTRA_HAMSTER);
        if (hamster != null) {
            GlideApp.with(this)
                    .load(hamster.getImageUrl())
                    .into(mImageView);
            mTitleTextView.setText(hamster.getTitle());
            mDescTextView.setText(hamster.getDescription());
            mToolbar.setTitle(hamster.getTitle());
        }
    }

    private void getImgCachePath(String url) {
        GlideApp.with(this)
                .asBitmap()
                .load(url)
                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_TEXT, String.format("%s\n%s", hamster.getTitle(), hamster.getDescription()));
                        startActivity(Intent.createChooser(intent, "Поделитесь хомяком"));
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                })
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("image/*");
                        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        intent.putExtra(Intent.EXTRA_TEXT, String.format("%s\n%s", hamster.getTitle(), hamster.getDescription()));
                        intent.putExtra(Intent.EXTRA_STREAM, getImageUri(resource));
                        startActivity(Intent.createChooser(intent, "Поделитесь хомяком"));
                    }
                });
    }

    public Uri getImageUri(Bitmap inImage) {
        File filesDir = getFilesDir();
        File imageFile = new File(filesDir, hamster.getTitle() + ".jpeg");
        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            inImage.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }
        return FileProvider.getUriForFile(this, "com.mojohamster.fileprovider", imageFile);
    }
}
