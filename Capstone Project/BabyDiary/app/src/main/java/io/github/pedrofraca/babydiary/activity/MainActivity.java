package io.github.pedrofraca.babydiary.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import io.github.pedrofraca.babydiary.R;
import io.github.pedrofraca.babydiary.provider.baby.BabyColumns;
import io.github.pedrofraca.babydiary.provider.baby.BabyContentValues;
import io.github.pedrofraca.babydiary.provider.baby.BabyCursor;
import io.github.pedrofraca.babydiary.provider.baby.BabySelection;
import io.github.pedrofraca.babydiary.provider.baby.Gender;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Button mDatePicker;
    private TextInputEditText mBabyName;
    private Spinner mGenderSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BabyCursor allBabies = new BabySelection().query(this);
        if (allBabies != null && allBabies.moveToFirst()) {
            startActivity(TimelineActivity.newIntent(MainActivity.this));
            finish();
        }

        mDatePicker = (Button) findViewById(R.id.datePicker);
        mBabyName = (TextInputEditText) findViewById(R.id.baby_name);
        mGenderSpinner = (Spinner) findViewById(R.id.spinner_gender);

        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date = new Date(System.currentTimeMillis());
                Calendar calendar = GregorianCalendar.getInstance();
                calendar.setTime(date);
                DatePickerDialog birthDatePicker = new DatePickerDialog(MainActivity.this,
                        MainActivity.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                birthDatePicker.getDatePicker().setSpinnersShown(true);
                birthDatePicker.show();
            }
        });

        SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.date_format));
        mDatePicker.setText(sdf.format(new Date(System.currentTimeMillis())));

        Spinner spinner = (Spinner) findViewById(R.id.spinner_gender);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        FloatingActionButton saveFab = (FloatingActionButton) findViewById(R.id.save_fab);
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBabyName.getText().toString().isEmpty()){
                    mBabyName.setError(getString(R.string.time_to_decide_baby_time));
                } else {
                    try {
                        SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.date_format));
                        Date parsedDate = sdf.parse(mDatePicker.getText().toString());
                        BabyContentValues baby = new BabyContentValues();
                        baby.putDateOfBirth(parsedDate);
                        baby.putName(mBabyName.getText().toString());
                        baby.putGender(Gender.valueOf(mGenderSpinner.getSelectedItem().toString().toUpperCase()));
                        getContentResolver().insert(BabyColumns.CONTENT_URI,baby.values());
                        startActivity(TimelineActivity.newIntent(MainActivity.this));
                        finish();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        final Toolbar toolbar = (Toolbar) findViewById(R.id.MyToolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.date_format));
        try {
            Date parsedDate = sdf.parse(day+"-"+(month+1)+"-"+year);
            mDatePicker.setText(sdf.format(parsedDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
