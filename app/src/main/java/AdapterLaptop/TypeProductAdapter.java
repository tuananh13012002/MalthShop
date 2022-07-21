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

import ModelLaptop.TypeProduct;

public class TypeProductAdapter extends RecyclerView.Adapter<TypeProductAdapter.TypeProductViewHolder> {
    private List<TypeProduct> mListTypeProduct;
    private Context context;


    public TypeProductAdapter(List<TypeProduct> mListTypeProduct, Context context) {
        this.mListTypeProduct = mListTypeProduct;
        this.context = context;
    }

    @NonNull

    @Override
    public TypeProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getApplicationContext()).inflate(R.layout.item_rv_type, parent, false);
        return new TypeProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TypeProductViewHolder holder, int position) {
        TypeProduct typeProduct = mListTypeProduct.get(position);
        holder.txtTypeProduct.setText("Loại sản phẩm" + typeProduct.getNameProduct());
        Picasso.get().load(typeProduct.getImageProduct())
                .placeholder(R.drawable.laptop)
                .error(R.drawable.ic_home)
                .into(holder.imgTypeProduct);

    }

    @Override
    public int getItemCount() {
        return mListTypeProduct.size();
    }


    public class TypeProductViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTypeProduct;
        private ImageView imgTypeProduct;


        public TypeProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTypeProduct = (TextView) itemView.findViewById(R.id.txtTypeProduct);
            imgTypeProduct = itemView.findViewById(R.id.imgTyperoduct);

//
        }
    }
}
