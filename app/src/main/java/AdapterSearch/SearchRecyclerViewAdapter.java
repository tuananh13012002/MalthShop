package AdapterSearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malthshop.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import InterfaceForHomeManager.OnEventShowProduct;
import ModelHome.Product;

public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchViewHolder> implements Filterable {
    private Context context;
    private List<Product> currentList;
    private List<Product> foundProduct;
    private InterfaceForHomeManager.OnEventShowProduct OnEventShowProduct;


    public SearchRecyclerViewAdapter(Context context, List<Product> currentList,OnEventShowProduct onEventShowProduct) {
        this.context = context;
        this.currentList = currentList;
        this.foundProduct = currentList;
        this.OnEventShowProduct=onEventShowProduct;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_product, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchRecyclerViewAdapter.SearchViewHolder holder, int position) {
        Product product = currentList.get(position);
        holder.tvNamePhone.setText(product.getProductName());
        holder.tvPrice.setText("" + product.getPrice());
        if (product.getStatus() == 0) {
            holder.tvStatusPhone.setText("Còn hàng");
        }
        if (product.getStatus() != 0) {
            holder.tvStatusPhone.setText("Hết hàng");
        }
        Picasso.get().load(product.getImgProduct())
                .placeholder(R.drawable.ic_baseline_home_24)
                .error(R.drawable.ic_baseline_error_24)
                .into(holder.img);
        holder.cvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnEventShowProduct.onClickShowProduct(product);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (currentList!=null)
            return currentList.size();
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strSearch = charSequence.toString();
                if (strSearch.isEmpty()) {
                    currentList = foundProduct;
                } else {
                    List<Product> productList = new ArrayList<>();
                    for (Product product : foundProduct) {
                        if (product.getProductName().toLowerCase().contains(strSearch.toLowerCase())) {
                            productList.add(product);
                        }
                    }
                    currentList = productList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = currentList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                currentList = (List<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }


    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView tvNamePhone;
        private TextView tvPrice;
        private TextView tvStatusPhone;
        private CardView cvContainer;

        public SearchViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.img);
            tvNamePhone = itemView.findViewById(R.id.tv_namePhone);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvStatusPhone = itemView.findViewById(R.id.tv_statusPhone);
            cvContainer = itemView.findViewById(R.id.cv_container);
        }
    }

}
