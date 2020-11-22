package stazer.user.androidstazerserviceapp.BookingProcess.dateTimePicker;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

import stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity;

public class TimeFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        orderSchduleActivity orderSchduleActivity = (stazer.user.androidstazerserviceapp.BookingProcess.orderSchduleActivity) getActivity();
        orderSchduleActivity.takeTime(hourOfDay,minute);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int hrs = calendar.get(Calendar.HOUR_OF_DAY);
        int min = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(getActivity(),this,hrs,min,true);
    }
}
