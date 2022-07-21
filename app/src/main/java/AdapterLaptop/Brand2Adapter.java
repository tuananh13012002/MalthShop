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

import ModelLaptop.Brand2;

public class Brand2Adapter extends RecyclerView.Adapter<Brand2Adapter.Brand2ViewHolder> {
    private Context context;
    private List<Brand2> mListBrand2;

    public Brand2Adapter(Context context, List<Brand2> mListBrand2) {
        this.context = context;
        this.mListBrand2 = mListBrand2;
    }

    @NonNull

    @Override
    public Brand2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_rv_brand2, parent, false);
        return new Brand2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Brand2ViewHolder holder, int position) {
        Brand2 product = mListBrand2.get(position);
        Picasso.get().load(product.getBrandPicture())
                .placeholder(R.drawable.laptop)
                .error(R.drawable.ic_home)
                .into(holder.imgBrand2);
        


    }

    @Override
    public int getItemCount() {
        return mListBrand2.size();
    }



    public class Brand2ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameLaptop2;
        private TextView txtGiaLaptop2;
        private ImageView imgBrand2,imgBrand22;

        public Brand2ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameLaptop2 = (TextView) itemView.findViewById(R.id.txtNameLaptop2);
            txtGiaLaptop2 = (TextView) itemView.findViewById(R.id.txtGiaLaptop2);
            imgBrand2 = (ImageView) itemView.findViewById(R.id.imgBrand2);


        }
    }
}
