package AdapterLaptop;

import android.content.Context;
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

import java.text.DecimalFormat;
import java.util.List;

import InterfaceForHomeManager.OnEventShowProduct;
import ModelHome.Product;

public class EarPhoneAdapter extends RecyclerView.Adapter<EarPhoneAdapter.EarPhoneViewHolder> {
    private Context context;
    private List<Product> mListEarPhone;
    private OnEventShowProduct onEventShowProduct;

    public EarPhoneAdapter(Context context, List<Product> mListEarPhone, OnEventShowProduct onEventShowProduct) {
        this.context = context;
        this.mListEarPhone = mListEarPhone;
        this.onEventShowProduct = onEventShowProduct;
    }

    @NonNull

    @Override
    public EarPhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_rv_earphone, parent, false);
        return new EarPhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarPhoneViewHolder holder, int position) {
        Product product = mListEarPhone.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPriceEarPhone.setText(decimalFormat.format(product.getPrice())+"đ");
        holder.txtNameEarPhone.setText(product.getProductName());
        Picasso.get().load(product.getImgProduct())
                .placeholder(R.drawable.laptop)
                .error(R.drawable.ic_home)
                .into(holder.imgEarPhone);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onEventShowProduct.onClickShowProduct(product);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mListEarPhone.size();
    }



    public class EarPhoneViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameEarPhone;
        private TextView txtPriceEarPhone;
        private ImageView imgEarPhone,imgFree;
        private CardView cardView;

        public EarPhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cvEarPhone);
            txtNameEarPhone = (TextView) itemView.findViewById(R.id.txtNameEarPhone);
            txtPriceEarPhone = (TextView) itemView.findViewById(R.id.txtPriceEarPhone);
            imgEarPhone = (ImageView) itemView.findViewById(R.id.imgEarPhone);
        }
    }
}
