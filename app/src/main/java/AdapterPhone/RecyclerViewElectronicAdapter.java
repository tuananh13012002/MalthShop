package AdapterPhone;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malthshop.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import InterfaceForHomeManager.OnEventShowProduct;
import ModelHome.Product;

public class RecyclerViewElectronicAdapter extends RecyclerView.Adapter<RecyclerViewElectronicAdapter.ElectronicViewHolder> {
    private Context context;
    private List<Product> list;
    private OnEventShowProduct onEventShowProduct;

    public RecyclerViewElectronicAdapter(Context context, List<Product> list, OnEventShowProduct onEventShowProduct) {
        this.context = context;
        this.list = list;
        this.onEventShowProduct = onEventShowProduct;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public ElectronicViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_linh_kien, parent, false);
        return new ElectronicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ElectronicViewHolder holder, int position) {
        Product electronic= list.get(position);
        holder.tvNameElectronic.setText(electronic.getProductName());
        holder.tvPrice.setText(electronic.getPrice()+"");
        Picasso.get().load(electronic.getImgProduct())
                .placeholder(R.drawable.ic_baseline_home_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(holder.imgElectronic);
        holder.cvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEventShowProduct.onClickShowProduct(electronic);
            }
        });
    }
    private final int limit = 2;
    @Override
    public int getItemCount() {
        if(list.size() > limit){
            return limit;
        }else {
            return list.size();
        }
    }




    public class ElectronicViewHolder extends RecyclerView.ViewHolder {
        private CardView cvContainer;
        private ImageView imgElectronic;
        private TextView tvNameElectronic;
        private TextView tvPrice;
        public ElectronicViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cvContainer = itemView.findViewById(R.id.cv_container);
            imgElectronic = itemView.findViewById(R.id.img_electronic);
            tvNameElectronic = itemView.findViewById(R.id.tv_nameElectronic);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }
}
