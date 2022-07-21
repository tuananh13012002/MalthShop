package Slider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.example.malthshop.R;
import com.makeramen.roundedimageview.RoundedImageView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SubSliderItemAdapter extends RecyclerView.Adapter<SubSliderItemAdapter.SubSliderItemViewHolder> {
    private Context context;
    private ViewPager2 pager2;
    private List<SubSliderItem> subSliderItemList;

    public SubSliderItemAdapter(Context context,ViewPager2 pager2, List<SubSliderItem> subSliderItemList) {
        this.context = context;
        this.pager2 = pager2;
        this.subSliderItemList = subSliderItemList;
    }

    @NonNull
    @NotNull
    @Override
    public SubSliderItemViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new SubSliderItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub_slider, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SubSliderItemViewHolder holder, int position) {
        SubSliderItem subSliderItem = subSliderItemList.get(position);
        if(subSliderItem == null){
            return;
        }else{
            Glide.with(context).load(subSliderItem.getImg()).into(holder.imgSlider);
            if(position == subSliderItemList.size() - 2){
                pager2.post(runnable);
            }
        }
    }

    @Override
    public int getItemCount() {
        return subSliderItemList == null ? 0 : subSliderItemList.size();
    }

    public class SubSliderItemViewHolder extends RecyclerView.ViewHolder {

        private RoundedImageView imgSlider;

        public SubSliderItemViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            imgSlider = (RoundedImageView) itemView.findViewById(R.id.img_slider);
        }
    }
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            subSliderItemList.addAll(subSliderItemList);
            notifyDataSetChanged();
        }
    };
}
