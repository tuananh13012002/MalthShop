package AdapterLaptop;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import SubFragmentLaptop.EarphoneFragment;
import SubFragmentLaptop.ElectronicComponentsFragment;
import SubFragmentLaptop.LaptopTablayoutFragment;
import SubFragmentLaptop.MouseFragment;


public class TabLayoutFragmentLaptopAdapter extends FragmentStatePagerAdapter {
    public TabLayoutFragmentLaptopAdapter(@NonNull  FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new LaptopTablayoutFragment();
            case 1:
                return new ElectronicComponentsFragment();
            case 2:
                return new MouseFragment();
            case 3:
                return new EarphoneFragment();
            default:
                return new  LaptopTablayoutFragment();

        }

    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position)
        {
            case 0:
                title = "Laptop";
                break;
            case 1:
                title = "Linh kiện";
                break;
            case 2:
                title = "Chuột";
                break;
            case 3:
                title = " Tai nghe";
                break;

        }
        return title;
    }
}
