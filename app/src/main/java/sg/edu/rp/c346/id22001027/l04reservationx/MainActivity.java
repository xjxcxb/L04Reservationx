package sg.edu.rp.c346.id22001027.l04reservationx;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;



public class MainActivity extends AppCompatActivity {
    EditText nameEditText, mobileEditText;
    NumberPicker groupSizePicker;
    DatePicker datePicker;
    TimePicker timePicker;
    RadioGroup smokingRadioGroup;
    RadioButton smokingRadio, nonSmokingRadio;
    Button confirmButton, resetButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        mobileEditText = (EditText) findViewById(R.id.mobileEditText);
        groupSizePicker = (NumberPicker) findViewById(R.id.groupSizePicker);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        smokingRadioGroup = (RadioGroup) findViewById(R.id.smokingRadioGroup);
        smokingRadio = (RadioButton) findViewById(R.id.smokingRadio);
        nonSmokingRadio = (RadioButton) findViewById(R.id.nonSmokingRadio);
        confirmButton = (Button) findViewById(R.id.confirmButton);
        resetButton = (Button) findViewById(R.id.resetButton);
        // Initialize date and time TextViews
        TextView dateTextView = findViewById(R.id.dateTextView);
        TextView timeTextView = findViewById(R.id.timeTextView);


        // Set default date and time
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2020);
        calendar.set(Calendar.MONTH, Calendar.JUNE);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 19);
        calendar.set(Calendar.MINUTE, 30);
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
        timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
        timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));

        // Set up group size picker
        String[] displayedValues = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        int maxValue = displayedValues.length - 1;
        groupSizePicker.setMinValue(0);
        groupSizePicker.setMaxValue(maxValue);
        groupSizePicker.setDisplayedValues(displayedValues);

        // Set up confirm button listener
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get reservation details from input views
                String name = nameEditText.getText().toString().trim();
                String mobileNumber = mobileEditText.getText().toString().trim();
                int groupSize = groupSizePicker.getValue();
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();
                int hour;
                int minute;
                if (android.os.Build.VERSION.SDK_INT >= 23) {
                    hour = timePicker.getHour();
                    minute = timePicker.getMinute();
                } else {
                    hour = timePicker.getCurrentHour();
                    minute = timePicker.getCurrentMinute();
                }
                String smokingArea = smokingRadio.isChecked() ? "Smoking Area" : "Non-Smoking Area";

                // Validate input fields
                if (name.isEmpty() || mobileNumber.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter your name and mobile number.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Construct confirmation message
                // Format date and time
                Calendar reservationTime = Calendar.getInstance();
                reservationTime.set(year, month, dayOfMonth, hour, minute);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
                String formattedDate = dateFormat.format(reservationTime.getTime());
                String formattedTime = timeFormat.format(reservationTime.getTime());

                // Construct confirmation message
                String confirmationMessage = "Name: " + name + "\n"
                        + "Mobile Number: " + mobileNumber + "\n"
                        + "Group Size: " + (groupSize + 1) + "\n"
                        + "Date: " + formattedDate + "\n"
                        + "Time: " + formattedTime + "\n"
                        + "Smoking Area: " + smokingArea;
                // Display reservation details
                TextView reservationDetailsTextView = findViewById(R.id.reservationDetailsTextView);
                reservationDetailsTextView.setVisibility(View.VISIBLE);

                TextView nameTextView = findViewById(R.id.nameTextView);
                nameTextView.setText("Name: " + name);

                TextView mobileNumberTextView = findViewById(R.id.mobileNumberTextView);
                mobileNumberTextView.setText("Mobile Number: " + mobileNumber);

                TextView groupSizeTextView = findViewById(R.id.groupSizeTextView);
                groupSizeTextView.setText("Group Size: " + (groupSize + 1));

                dateTextView.setText("Date: " + formattedDate);

                timeTextView.setText("Time: " + formattedTime);

                TextView smokingAreaTextView = findViewById(R.id.smokingAreaTextView);
                smokingAreaTextView.setText("Smoking area: " + smokingArea);

                        // Display confirmation message
                Toast.makeText(MainActivity.this, confirmationMessage, Toast.LENGTH_LONG).show();
            }
        });

        // Set up reset button listener
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Reset input views
                nameEditText.setText("");
                mobileEditText.setText("");
                groupSizePicker.setValue(0);
                Calendar calendar = Calendar.getInstance();
                datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), null);
                timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
                timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
                smokingRadioGroup.check(R.id.nonSmokingRadio);
            }
        });
    }
}