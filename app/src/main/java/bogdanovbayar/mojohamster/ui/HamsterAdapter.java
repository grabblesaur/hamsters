package bogdanovbayar.mojohamster.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import bogdanovbayar.mojohamster.R;
import bogdanovbayar.mojohamster.base.GlideApp;
import bogdanovbayar.mojohamster.data.model.Hamster;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HamsterAdapter
        extends RecyclerView.Adapter<HamsterAdapter.HamsterViewHolder>
        implements Filterable {

    private ViewGroup mViewGroup;

    public interface HamsterAdapterListener {
        void onItemClicked(Hamster hamster);
        void onSearch(boolean b);
    }

    private List<Hamster> mHamsterList;
    private List<Hamster> mHamsterFilteredList;
    private HamsterAdapterListener mListener;

    public HamsterAdapter(List<Hamster> hamsterList, HamsterAdapterListener listener) {
        mHamsterList = hamsterList;
        mHamsterFilteredList = hamsterList;
        mListener = listener;
    }

    public void replace(List<Hamster> hamsterList) {
        mHamsterFilteredList = hamsterList;
        mHamsterList = hamsterList;
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mHamsterFilteredList = mHamsterList;
                } else {
                    List<Hamster> filteredList = new ArrayList<>();
                    for (Hamster hamster : mHamsterList) {
                        if (hamster.getTitle().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(hamster);
                        }
                    }
                    mHamsterFilteredList = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mHamsterFilteredList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mHamsterFilteredList = (List<Hamster>) filterResults.values;
                if (mListener != null) {
                    if (mHamsterFilteredList.isEmpty()) {
                        mListener.onSearch(false);
                    } else {
                        mListener.onSearch(true);
                        notifyDataSetChanged();
                    }
                }
            }
        };
    }

    @NonNull
    @Override
    public HamsterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_hamster, viewGroup, false);
        mViewGroup = viewGroup;
        return new HamsterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HamsterViewHolder hamsterViewHolder, int i) {
        hamsterViewHolder.onBind(mHamsterFilteredList.get(hamsterViewHolder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return mHamsterFilteredList.size();
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

            GlideApp.with(mViewGroup)
                    .load(hamster.getImageUrl())
                    .into(mImageView);
        }
    }
}
