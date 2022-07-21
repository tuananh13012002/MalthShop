package com.example.malthshop.SlideShow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.malthshop.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PhoToViewPagerAdapter extends PagerAdapter {

    private List<Photo> list;
    private ImageView imgPhoto;
    private TextView textView,text1,text2,text3;

    public PhoToViewPagerAdapter(List<Photo> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_photo, container, false);
        imgPhoto = view.findViewById(R.id.img_photo);
        textView=view.findViewById(R.id.textView);
        text1=view.findViewById(R.id.text1);
        text2=view.findViewById(R.id.text2);
        text3=view.findViewById(R.id.text3);
        Photo photo=list.get(position);
        imgPhoto.setImageResource(photo.getResourceID());
        textView.setText(photo.getText());
        text1.setText(photo.getText1());
        text2.setText(photo.getText2());
        text3.setText(photo.getText3());

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
