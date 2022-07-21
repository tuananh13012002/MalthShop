package AdapterLaptop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malthshop.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import ModelLaptop.Brand;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.BrandViewHolder> {
    private Context context;
    private List<Brand> mListBrand;

    public BrandAdapter(Context context, List<Brand> mListBrand) {
        this.context = context;
        this.mListBrand = mListBrand;
    }

    @NonNull

    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_rv_brand, parent, false);
        return new BrandViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        Brand product = mListBrand.get(position);
        Picasso.get().load(product.getBrandPicture())
                .placeholder(R.drawable.laptop)
                .error(R.drawable.ic_home)
                .into(holder.imgBrand);
    }

    @Override
    public int getItemCount() {
        return mListBrand.size();
    }



    public class BrandViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameLaptop2;
        private TextView txtGiaLaptop2;
        private ImageView imgBrand,imgBrand2;

        public BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameLaptop2 = (TextView) itemView.findViewById(R.id.txtNameLaptop2);
            txtGiaLaptop2 = (TextView) itemView.findViewById(R.id.txtGiaLaptop2);
            imgBrand = (ImageView) itemView.findViewById(R.id.imgBrand);


        }
    }
}
