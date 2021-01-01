package stazer.user.androidstazerserviceapp.AllRatesCard.Ac;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import stazer.user.androidstazerserviceapp.Model.RateModel;
import stazer.user.androidstazerserviceapp.R;

public class AcRateCardAdapter extends FirebaseRecyclerAdapter<RateModel, AcRateCardAdapter.ACRateCardViewHolder> {

    public AcRateCardAdapter(@NonNull FirebaseRecyclerOptions<RateModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ACRateCardViewHolder holder, int position, @NonNull RateModel model) {
        holder.mSparePart.setText(model.getSparePartName());
        holder.mSparePartCost.setText(model.getPartCost());
        holder.mLabourCost.setText(model.getLabourCost());
    }

    @NonNull
    @Override
    public ACRateCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_rate_view, parent, false);
        return new ACRateCardViewHolder(view);
    }

    public static class ACRateCardViewHolder extends RecyclerView.ViewHolder {
        TextView mSparePart, mSparePartCost, mLabourCost;

        public ACRateCardViewHolder(@NonNull View itemView) {
            super(itemView);
            mSparePart = itemView.findViewById(R.id.partName);
            mSparePartCost = itemView.findViewById(R.id.partCost);
            mLabourCost = itemView.findViewById(R.id.labourCost);
        }
    }
}
