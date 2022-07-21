package AutoSlideInPhone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.example.malthshop.R;
import com.example.malthshop.SlideShow.Photo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AutoSliderViewPagerAdapter extends PagerAdapter {

    private List<Photo> list;
    private ImageView img;
    private TextView textView,text1,text2,text3;

    public AutoSliderViewPagerAdapter(List<Photo> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_auto_slidershow, container, false);
        img = view.findViewById(R.id.img);
        Photo photo=list.get(position);
        img.setImageResource(photo.getResourceID());

        container.addView(view);
        
        return view;
    }

    @Override
    public int getCount() {
        if (list != null)
            return list.size();
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        container.removeView((View) object);
    }
    
}
