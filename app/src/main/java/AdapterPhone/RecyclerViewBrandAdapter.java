package AdapterPhone;

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

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ModelPhone.Brand;

public class RecyclerViewBrandAdapter extends RecyclerView.Adapter<RecyclerViewBrandAdapter.THViewHolder> {
    private Context context;
    private List<Brand> list;


    public RecyclerViewBrandAdapter(Context context, List<Brand> list) {
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public THViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thuong_hieu, parent, false);
        return new THViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull THViewHolder holder, int position) {
        Brand brand=list.get(position);
        holder.tvNamePhone.setText(brand.getBrandName());
        Picasso.get().load(brand.getBrandPicture())
                .placeholder(R.drawable.ic_baseline_home_24)
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(holder.imgPhone);
        holder.cvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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


    public class THViewHolder extends RecyclerView.ViewHolder {
        private CardView cvContainer;
        private ImageView imgPhone;
        private TextView tvNamePhone;
        private TextView tvStatusPhone;
        public THViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cvContainer = itemView.findViewById(R.id.cv_container);
            imgPhone = itemView.findViewById(R.id.img_phone);
            tvNamePhone = itemView.findViewById(R.id.tv_namePhone);
            tvStatusPhone = itemView.findViewById(R.id.tv_statusPhone);
        }
    }
}
