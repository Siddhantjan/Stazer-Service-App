package stazer.user.androidstazerserviceapp.AllRatesCard.RefrigeratorRateCard;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import stazer.user.androidstazerserviceapp.Model.RateModel;
import stazer.user.androidstazerserviceapp.R;

public class RefrigeratorRateCardAdapter extends FirebaseRecyclerAdapter<RateModel, RefrigeratorRateCardAdapter.FridgeRateCardViewHolder> {

    public RefrigeratorRateCardAdapter(@NonNull FirebaseRecyclerOptions<RateModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull FridgeRateCardViewHolder holder, int position, @NonNull RateModel model) {
        holder.mSparePart.setText(model.getSparePartName());
        holder.mSparePartCost.setText(model.getPartCost());
        holder.mLabourCost.setText(model.getLabourCost());

    }

    @NonNull
    @Override
    public FridgeRateCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row_rate_view, parent, false);
        return new FridgeRateCardViewHolder(view);
    }

    public static class FridgeRateCardViewHolder extends RecyclerView.ViewHolder {
        TextView mSparePart, mSparePartCost, mLabourCost;
        ImageView mEdit, mDelete;

        public FridgeRateCardViewHolder(@NonNull View itemView) {
            super(itemView);
            mSparePart = itemView.findViewById(R.id.partName);
            mSparePartCost = itemView.findViewById(R.id.partCost);
            mLabourCost = itemView.findViewById(R.id.labourCost);
        }
    }
}
