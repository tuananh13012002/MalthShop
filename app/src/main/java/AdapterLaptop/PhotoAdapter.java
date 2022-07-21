package AdapterLaptop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.malthshop.R;

import java.util.List;

import ModelLaptop.Photo;

public class PhotoAdapter extends PagerAdapter {
    private Context mContext;
    private List<Photo> mListPhoto;

    public PhotoAdapter(Context mContext, List<Photo> mListPhoto) {
        this.mContext = mContext;
        this.mListPhoto = mListPhoto;
    }

    @Override
    public int getCount() {
        if(mListPhoto != null)
        {
            return mListPhoto.size();
        }
        return 0;
    }

    @NonNull

    @Override
    public Object instantiateItem(@NonNull  ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.slide_laptop,container,false);
        ImageView imgPhoto = view.findViewById(R.id.img_slide_laptop1);
        Photo photo = mListPhoto.get(position);
        if(photo !=null)
        {
            Glide.with(mContext).load(photo.getResourceID()).into(imgPhoto);
        }
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(@NonNull  View view, @NonNull  Object object) {
        return view== object;
    }

    @Override
    public void destroyItem(@NonNull  ViewGroup container, int position,  Object object) {
        container.removeView((View) object);
    }
}
