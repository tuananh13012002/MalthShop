package Slider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.malthshop.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImageAdapter extends PagerAdapter {
    private Context mContext;
    private List<Image> mListImage;
    private ImageView imgItemSlide;

    public ImageAdapter(Context mContext, List<Image> mListImage) {
        this.mContext = mContext;
        this.mListImage = mListImage;
    }

    @Override
    public int getCount() {
        return mListImage != null ? mListImage.size() : 0;
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_slider_home_main, container, false);
        initView(view);

        Image image = mListImage.get(position);
        if(image != null){
            Glide.with(mContext).load(image.getImg()).into(imgItemSlide);
        }

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == object;
    }

    private void initView(View view) {
        imgItemSlide = (ImageView) view.findViewById(R.id.img_item_slide);
    }
}
