package SlideShow;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;


import com.example.malthshop.MainActivity;
import com.example.malthshop.R;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;


public class SlideShowActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private CircleIndicator circle;
    private List<Photo> list;
    private Button btn_next;
    private CardView btn_getStart;
    int position =0;
    PhoToViewPagerAdapter adapter;
    Animation btnAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (restorePrefData()){
            Intent intent=new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }


        setContentView(R.layout.activity_slide_show);

        viewPager = findViewById(R.id.view_pager);
        circle = findViewById(R.id.circle);
        btn_next = findViewById(R.id.btn_next);
        btn_getStart=findViewById(R.id.btn_getStart);
        btnAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.btn_getstart);


        list = getListPhoto();
        adapter = new PhoToViewPagerAdapter(list);
        viewPager.setAdapter(adapter);
        circle.setViewPager(viewPager);
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position=viewPager.getCurrentItem();
                if (position<list.size()){
                    position++;
                    viewPager.setCurrentItem(position);
                }
                if (position==list.size()-1){
                    loadLastScreen();
                }
            }
        });
        btn_getStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                //also we need to save a boolean to storage so next time when the user run
                //we could know he is already checked the intro screen activity
                //i'm going user shared preferences to that process
                savePrefData();
                finish();
            }
        });
    }

    private boolean restorePrefData() {
        SharedPreferences pref=getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore=pref.getBoolean("isIntroOpened",false);
        return isIntroActivityOpenedBefore;
    }

    private void savePrefData(){
        SharedPreferences pref=getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);
        SharedPreferences.Editor editor=pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();
    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();

        list.add(new Photo("Chào mừng đến ", "Matlh Shop !", "", "Matlh Shop", R.drawable.logo_appp));
        list.add(new Photo("Mua sắm an toàn với ", "", "Matlh Shop Đảm Bảo", "", R.drawable.two));
        list.add(new Photo("Bật thông báo ", "Matlh Shop", "", "", R.drawable.three));

        return list;
    }
    private void loadLastScreen(){
        btn_next.setVisibility(View.INVISIBLE);
        btn_getStart.setVisibility(View.VISIBLE);
        circle.setVisibility(View.INVISIBLE);
        btn_getStart.setAnimation(btnAnim);
    }
}