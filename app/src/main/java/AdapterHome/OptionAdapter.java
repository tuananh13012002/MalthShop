package AdapterHome;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.malthshop.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import ModelHome.Option;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {
    private Context mContext;
    private List<Option> mListOption;

    public OptionAdapter(Context mContext, List<Option> mListOption) {
        this.mContext = mContext;
        this.mListOption = mListOption;
    }

    @NonNull
    @NotNull
    @Override
    public OptionViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_option, parent, false);
        return new OptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull OptionViewHolder holder, int position) {
        Option option = mListOption.get(position);
        if(option == null){
            return;
        }else{
            holder.imgOption.setImageResource(option.getIcon());
            holder.tvItemOption.setText(option.getText());
        }
    }

    @Override
    public int getItemCount() {
        return mListOption != null ? mListOption.size() : 0;
    }

    public class OptionViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgOption;
        private TextView tvItemOption;

        public OptionViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(View itemView) {
            imgOption = (ImageView) itemView.findViewById(R.id.img_option);
            tvItemOption = (TextView) itemView.findViewById(R.id.tv_item_option);
        }
    }
}
