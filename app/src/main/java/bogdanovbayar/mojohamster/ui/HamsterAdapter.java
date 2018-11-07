package bogdanovbayar.mojohamster.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import bogdanovbayar.mojohamster.R;
import bogdanovbayar.mojohamster.data.model.Hamster;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HamsterAdapter extends RecyclerView.Adapter<HamsterAdapter.HamsterViewHolder> {

    public interface HamsterAdapterListener {
        void onItemClicked(Hamster hamster);
    }

    private List<Hamster> mHamsterList;
    private HamsterAdapterListener mListener;

    @NonNull
    @Override
    public HamsterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_hamster, viewGroup, false);
        return new HamsterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HamsterViewHolder hamsterViewHolder, int i) {
        hamsterViewHolder.onBind(mHamsterList.get(i));
    }

    @Override
    public int getItemCount() {
        return mHamsterList.size();
    }

    public class HamsterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_parent_layout)
        LinearLayout mParentLayout;
        @BindView(R.id.item_image)
        ImageView mImageView;
        @BindView(R.id.item_title)
        TextView mTitleTextView;
        @BindView(R.id.item_desc)
        TextView mDescTextView;

        public HamsterViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void onBind(Hamster hamster) {
            mParentLayout.setOnClickListener(v -> {
                if (mListener != null) {
                    mListener.onItemClicked(hamster);
                }
            });
            mTitleTextView.setText(hamster.getTitle());
            mDescTextView.setText(hamster.getDescription());
        }
    }
}
