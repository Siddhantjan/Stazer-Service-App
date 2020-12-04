package stazer.user.androidstazerserviceapp.tenderService;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import stazer.user.androidstazerserviceapp.R;

public class TenderActivity extends AppCompatActivity {

    private TextView mTenderType, mHeaderType, mDate, mTime, mBtnDate, mBtnTime;
    private EditText mName, mMobileNumber, mMail, mDescription;
    private Button mRequestTender;
    private String cTenderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tender);

        //Hooks
        mHeaderType = findViewById(R.id.tender_text);
        mTenderType = findViewById(R.id.tenderType);

        mBtnDate = findViewById(R.id.btn_Tender_date);
        mBtnTime = findViewById(R.id.btn_Tender_time);

        mDate = findViewById(R.id.text_tender_date);
        mTime = findViewById(R.id.text_tender_time);

        mName = findViewById(R.id.tenderPersonName);
        mMobileNumber = findViewById(R.id.tenderPersonContactNumber);
        mMail = findViewById(R.id.tenderPersonContactMail);
        mDescription = findViewById(R.id.describeTenderDetails);

        mRequestTender = findViewById(R.id.requestTender);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            //getData
            cTenderType = bundle.getString("Tender");
            //setData

            mHeaderType.setText(cTenderType);
            mTenderType.setText(cTenderType);
        }
    }
}