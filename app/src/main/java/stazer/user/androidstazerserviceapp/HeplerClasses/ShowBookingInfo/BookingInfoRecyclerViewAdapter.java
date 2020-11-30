package stazer.user.androidstazerserviceapp.HeplerClasses.ShowBookingInfo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import stazer.user.androidstazerserviceapp.BookingInfo.FinalBookingPaymentActivity;
import stazer.user.androidstazerserviceapp.Model.FetchBookingDetailsModel;
import stazer.user.androidstazerserviceapp.R;

public class BookingInfoRecyclerViewAdapter extends RecyclerView.Adapter {
    List<FetchBookingDetailsModel> fetchBookingDetailsModelList;
    Context context;

    public BookingInfoRecyclerViewAdapter(List<FetchBookingDetailsModel> fetchBookingDetailsModelList,Context context) {
        this.fetchBookingDetailsModelList = fetchBookingDetailsModelList;
        this.context = context;
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
        bookingInfoViewHolder.serviceDate.setText(fetchBookingDetailsModel.getDate());
        bookingInfoViewHolder.serviceTime.setText(fetchBookingDetailsModel.getTime());

        bookingInfoViewHolder.relativeLayoutDetails.setOnClickListener(v -> {
            Intent newDetailIntent = new Intent(context, FinalBookingPaymentActivity.class);
            newDetailIntent.putExtra("Service",fetchBookingDetailsModel.getServiceType());
            context.startActivity(newDetailIntent);
        });

    }

    @Override
    public int getItemCount() {
        return fetchBookingDetailsModelList.size();
    }

    public static class BookingInfoViewHolder extends RecyclerView.ViewHolder {

        TextView serviceName, serviceCategory, serviceTime, serviceDate;
        RelativeLayout relativeLayoutDetails;

        public BookingInfoViewHolder(@NonNull View itemView) {
            super(itemView);
            //Hooks
            serviceName = itemView.findViewById(R.id.nameService);
            serviceCategory = itemView.findViewById(R.id.nameCategory);
            serviceDate = itemView.findViewById(R.id.serviceDate);

            serviceTime = itemView.findViewById(R.id.serviceTime);
            relativeLayoutDetails = itemView.findViewById(R.id.info_bookings);

        }
    }
}
