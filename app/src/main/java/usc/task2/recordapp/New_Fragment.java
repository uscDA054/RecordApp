package usc.task2.recordapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.TimeZone;


public class New_Fragment extends Fragment {

    Button btnShare, btnShowMap, btnDate, btnPic;
    Uri imageUri;
    ImageView imageView;
    TextView txtDate,txtLoc,txtTitle,txtDetails,txtPlace;
    String stringLatitude;
    String stringLongitude;
    private SQLiteHelper db;
    // check if GPS enabled
    GPSTracker gpsTracker = new GPSTracker(getActivity());

    public New_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        txtDate = (TextView) getView().findViewById(R.id.txtDate);
        txtLoc = (TextView) getView().findViewById(R.id.txtLocation);
        txtTitle = (TextView) getView().findViewById(R.id.edTitle);
        txtPlace = (TextView) getView().findViewById(R.id.edPlace);
        txtDetails = (TextView) getView().findViewById(R.id.edPlace);
        imageView = (ImageView) getView().findViewById(R.id.imageView);
        btnDate = (Button) getView().findViewById(R.id.btnDate);
        btnPic = (Button) getView().findViewById(R.id.btnPicture);
        btnShare = (Button) getView().findViewById(R.id.btnShare);
        btnShowMap = (Button) getView().findViewById(R.id.btnShow);
        btnPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
            }
        });
        if (gpsTracker.getIsGPSTrackingEnabled()) {
            stringLatitude = String.valueOf(gpsTracker.latitude);
            stringLongitude = String.valueOf(gpsTracker.longitude);
            txtLoc.setText(stringLongitude + "  " + stringLatitude);
        }
        db = new SQLiteHelper(getActivity());
        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance(TimeZone.getDefault()); // Get current date

                // Create the DatePickerDialog instance
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(),
                        R.style.AppTheme, datePickerListener,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                datePicker.setCancelable(false);
                datePicker.setTitle("Select the date");
                datePicker.show();

            }
        });
        btnShowMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map_Fragment mapFragment = new Map_Fragment();
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                // FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.fragment_container, mapFragment); // f2_container is your FrameLayout container
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logs logs = new Logs(txtTitle.getText().toString(),txtPlace.getText().toString(),txtDetails.getText().toString(),txtDate.getText().toString(),stringLongitude,stringLatitude,"");

               //Save to DB
                db.insertLog(logs);

                Snackbar.make(v, "Item Saved!", Snackbar.LENGTH_LONG).show();
            }
        });

    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            String year1 = String.valueOf(selectedYear);
            String month1 = String.valueOf(selectedMonth + 1);
            String day1 = String.valueOf(selectedDay);
            txtDate.setText(day1 + "/" + month1 + "/" + year1);

        }
    };

    public void takePhoto() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bp = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bp);

    }


}
