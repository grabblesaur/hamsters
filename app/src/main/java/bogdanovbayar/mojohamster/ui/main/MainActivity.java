package bogdanovbayar.mojohamster.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import bogdanovbayar.mojohamster.R;
import bogdanovbayar.mojohamster.base.BaseActivity;
import bogdanovbayar.mojohamster.data.model.Hamster;
import bogdanovbayar.mojohamster.di.Application;
import bogdanovbayar.mojohamster.ui.about.AboutActivity;
import bogdanovbayar.mojohamster.ui.detail.DetailActivity;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainContract.MainView {

    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.main_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.main_searchview)
    SearchView mSearchView;
    @BindView(R.id.main_recyclerview)
    RecyclerView mRecyclerView;

    @Inject
    MainPresenter mPresenter;

    private HamsterAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Application.getAppComponent().inject(this);
        mPresenter.attachView(this);
        initViews();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initViews() {
        setUpToolbarWithoutFragmentsWithWhiteButton(mToolbar, false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mAdapter = new HamsterAdapter(new ArrayList<>(0),
                new HamsterAdapter.HamsterAdapterListener() {
                    @Override
                    public void onItemClicked(Hamster hamster) {
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra(DetailActivity.EXTRA_HAMSTER, hamster);
                        startActivity(intent);
                    }

                    @Override
                    public void onSearch(boolean b) {
                        Log.i(TAG, "onSearch: " + b);
                    }
                });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });

        mPresenter.getData();
    }


    @Override
    public void showData(List<Hamster> hamsterList) {
        Collections.sort(hamsterList, new PinnedHamsterComparator());
        mAdapter.replace(hamsterList);
    }
}
