package AdapterSearch;

import android.content.Context;
import android.content.Intent;
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

import ModelLaptop.TypeProduct;
import Search.EarphoneActivity;
import Search.ElectronnicActivity;
import Search.LaptopActivity;
import Search.MouseActivity;
import Search.PhoneActivity;

public class TypeRecyclerViewAdapter extends RecyclerView.Adapter<TypeRecyclerViewAdapter.TypeViewHolder> {
    private Context context;
    private List<TypeProduct> list;

    public TypeRecyclerViewAdapter(Context context, List<TypeProduct> list) {
        this.context = context;
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    public TypeViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_type, parent, false);
        return new TypeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TypeRecyclerViewAdapter.TypeViewHolder holder, int position) {
        TypeProduct typeProduct=list.get(position);
        holder.tvName.setText(typeProduct.getNameProduct());
        Picasso.get().load(typeProduct.getImageProduct())
                .placeholder(R.drawable.ic_baseline_home_24)
                .error(R.drawable.ic_baseline_error_24)
                .into(holder.img);
        holder.cvContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position==0){
                    context.startActivity(new Intent(context.getApplicationContext(), PhoneActivity.class));
                }else if (position==1){
                    context.startActivity(new Intent(context.getApplicationContext(), LaptopActivity.class));
                }else if (position==2){
                    context.startActivity(new Intent(context.getApplicationContext(), ElectronnicActivity.class));
                }else if (position==3){
                    context.startActivity(new Intent(context.getApplicationContext(), MouseActivity.class));
                }else if (position==4){
                    context.startActivity(new Intent(context.getApplicationContext(), EarphoneActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class TypeViewHolder extends RecyclerView.ViewHolder {
        private CardView cvContainer;
        private TextView tvName;
        private ImageView img;
        public TypeViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cvContainer = itemView.findViewById(R.id.cv_container);
            tvName = itemView.findViewById(R.id.tv_name);
            img = itemView.findViewById(R.id.img);
        }
    }
}
