package AdapterLaptop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malthshop.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import InterfaceForHomeManager.OnEventShowProduct;
import ModelHome.Product;

public class ElectronicComponentsAdapter extends RecyclerView.Adapter<ElectronicComponentsAdapter.AccessoryViewHolder> {
    private Context context;
    private List<Product> mListElectronicComponents;
    private OnEventShowProduct onEventShowProduct;

    public ElectronicComponentsAdapter(Context context, List<Product> mListElectronicComponents, OnEventShowProduct onEventShowProduct) {
        this.context = context;
        this.mListElectronicComponents = mListElectronicComponents;
        this.onEventShowProduct = onEventShowProduct;
    }

    @NonNull

    @Override
    public AccessoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_rv_accessory, parent, false);
        return new AccessoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccessoryViewHolder holder, int position) {
        Product electronicComponents = mListElectronicComponents.get(position);
        holder.txtNameAccessory.setText(electronicComponents.getProductName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPriceAccessory.setText(decimalFormat.format(electronicComponents.getPrice())+"");
        Picasso.get().load(electronicComponents.getImgProduct())
                .placeholder(R.drawable.laptop)
                .error(R.drawable.ic_home)
                .into(holder.imgAccessory);
        holder.lvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEventShowProduct.onClickShowProduct(electronicComponents);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mListElectronicComponents.size();
    }



    public class AccessoryViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameAccessory;
        private TextView txtPriceAccessory;
        private ImageView imgAccessory;
        private CardView lvContainer;

        public AccessoryViewHolder(@NonNull View itemView) {
            super(itemView);
            lvContainer = (CardView) itemView.findViewById(R.id.lv_container);
            txtNameAccessory = (TextView) itemView.findViewById(R.id.txtNameAccessory);
            txtPriceAccessory = (TextView) itemView.findViewById(R.id.txtPriceAccessory);
            imgAccessory = (ImageView) itemView.findViewById(R.id.imgAccessory);


        }
    }
}
