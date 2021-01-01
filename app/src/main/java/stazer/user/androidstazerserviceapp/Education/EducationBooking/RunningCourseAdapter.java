package stazer.user.androidstazerserviceapp.Education.EducationBooking;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import stazer.user.androidstazerserviceapp.Model.CoursesBookingDetailsModel;
import stazer.user.androidstazerserviceapp.R;

public class RunningCourseAdapter extends FirebaseRecyclerAdapter<CoursesBookingDetailsModel, RunningCourseAdapter.RunningViewHolder> {

    public RunningCourseAdapter(@NonNull FirebaseRecyclerOptions<CoursesBookingDetailsModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull RunningViewHolder holder, int position, @NonNull CoursesBookingDetailsModel model) {
        holder.mStudentName.setText(model.getStudentName());
        holder.mClass.setText(model.getClassCategory());
        holder.mSubject.setText(model.getSubject());
        holder.mServiceAddress.setText(model.getServiceAddress());

    }

    @NonNull
    @Override
    public RunningViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complete_course_booking_cardview, parent, false);
        return new RunningViewHolder(view);
    }

    public static class RunningViewHolder extends RecyclerView.ViewHolder {
        TextView mStudentName, mClass, mSubject,mServiceAddress;

        public RunningViewHolder(@NonNull View itemView) {
            super(itemView);
            mStudentName = itemView.findViewById(R.id.nameStudent);
            mClass = itemView.findViewById(R.id.nameOfClass);
            mSubject = itemView.findViewById(R.id.subjects);
            mServiceAddress = itemView.findViewById(R.id.serviceAddress);
        }
    }
}
