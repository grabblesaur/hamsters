package bogdanovbayar.mojohamster.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import bogdanovbayar.mojohamster.R;
import bogdanovbayar.mojohamster.base.BaseActivity;
import bogdanovbayar.mojohamster.data.model.Hamster;
import bogdanovbayar.mojohamster.di.Application;
import butterknife.BindView;

public class MainActivity extends BaseActivity implements MainContract.MainView {

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

    private void initViews() {
        mAdapter = new HamsterAdapter(new ArrayList<>(0), hamster -> {
            Toast.makeText(this, hamster.getTitle(), Toast.LENGTH_SHORT).show();
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getData();
    }


    @Override
    public void showData(List<Hamster> hamsterList) {
        mAdapter.replace(hamsterList);
    }
}
