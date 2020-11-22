package stazer.user.androidstazerserviceapp.BookingProcess.dateTimePicker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;

public class DateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        orderSchduleActivity orderSchduleActivity = (stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity) getActivity();
        orderSchduleActivity.takeDate(year,month,dayOfMonth);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int days = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(),this,year,month,days);

    }
}
