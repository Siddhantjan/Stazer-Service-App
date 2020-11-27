package stazer.user.androidstazerserviceapp.HeplerClasses.ShowBookingInfo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import stazer.user.androidstazerserviceapp.Model.FetchBookingDetailsModel;
import stazer.user.androidstazerserviceapp.R;

public class BookingInfoRecyclerViewAdapter extends RecyclerView.Adapter {
    List<FetchBookingDetailsModel> fetchBookingDetailsModelList;

    public BookingInfoRecyclerViewAdapter(List<FetchBookingDetailsModel> fetchBookingDetailsModelList) {
        this.fetchBookingDetailsModelList = fetchBookingDetailsModelList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complete_booking_information_card_view, parent, false);
        BookingInfoViewHolder bookingInfoViewHolder = new BookingInfoViewHolder(view);
        return bookingInfoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BookingInfoViewHolder bookingInfoViewHolder = (BookingInfoViewHolder)holder;
        FetchBookingDetailsModel fetchBookingDetailsModel = fetchBookingDetailsModelList.get(position);
        bookingInfoViewHolder.serviceName.setText(fetchBookingDetailsModel.getServiceType());
        bookingInfoViewHolder.serviceCategory.setText(fetchBookingDetailsModel.getServiceCategory());
        bookingInfoViewHolder.serviceStatus.setText(fetchBookingDetailsModel.getStatus());
        bookingInfoViewHolder.serviceDate.setText(fetchBookingDetailsModel.getDate());
        bookingInfoViewHolder.serviceTime.setText(fetchBookingDetailsModel.getTime());


    }

    @Override
    public int getItemCount() {
        return fetchBookingDetailsModelList.size();
    }

    public static class BookingInfoViewHolder extends RecyclerView.ViewHolder {

        TextView serviceName, serviceCategory, serviceTime, serviceDate, serviceStatus;

        public BookingInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            //Hooks
            serviceName = itemView.findViewById(R.id.nameService);
            serviceCategory = itemView.findViewById(R.id.nameCategory);
            serviceDate = itemView.findViewById(R.id.serviceDate);
            serviceStatus = itemView.findViewById(R.id.status);
            serviceTime = itemView.findViewById(R.id.serviceTime);

        }
    }
}
