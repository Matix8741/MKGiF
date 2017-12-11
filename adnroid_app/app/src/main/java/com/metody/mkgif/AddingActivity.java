package com.metody.mkgif;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.icu.util.GregorianCalendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.Date;


public class AddingActivity extends AppCompatActivity {
    public Button button;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adding_layout);
        final Spinner spinner = findViewById(R.id.spinner);
        final Spinner spinnerStatus = findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.types, R.layout.spinner_layout);

        ArrayAdapter<CharSequence> adapterStatus = ArrayAdapter.createFromResource(this,
                R.array.status, R.layout.spinner_layout);
        adapter.setDropDownViewResource(R.layout.dropdown_spinner_layout);
        adapterStatus.setDropDownViewResource(R.layout.dropdown_spinner_layout);
        final ViewFlipper vf = findViewById(R.id.vf);
        final EditText[] creator = {null};
        final EditText[] createDate = {null};
        final EditText[] brand = {null};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        vf.setDisplayedChild(0);
                        creator[0] = findViewById(R.id.AuthorInput);
                        createDate[0] = findViewById(R.id.Book_DateInput);
                        brand[0] = findViewById(R.id.Book_BrandInput);
                        break;
                    case 1:
                        vf.setDisplayedChild(1);
                        creator[0] = findViewById(R.id.DirectorInput);
                        createDate[0] = findViewById(R.id.Movie_DateInput);
                        brand[0] = findViewById(R.id.Movie_BrandInput);
                        break;
                    case 2:
                        vf.setDisplayedChild(2);
                        creator[0] = findViewById(R.id.StudioInput);
                        createDate[0] = findViewById(R.id.Game_DateInput);
                        brand[0] = findViewById(R.id.Game_BrandInput);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        button = findViewById(R.id.DateButton);
        Date currentTime = Calendar.getInstance().getTime();
        button.setText(new SimpleDateFormat("yyyy-MM-dd").format(currentTime));
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinnerStatus.setAdapter(adapterStatus);
        spinnerStatus.setSelection(0);
        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RatingBar ratingBar = findViewById(R.id.rating);
                TextView textView = findViewById(R.id.addTextField);
                Intent intent = new Intent(getApplicationContext(), AddingActivity.class);
                intent.putExtra("text", textView.getText().toString());
                intent.putExtra("type", (String) spinner.getSelectedItem());
                intent.putExtra("date", button.getText());
                intent.putExtra("rating", ratingBar.getRating());
                intent.putExtra("status", (String) spinnerStatus.getSelectedItem());
                intent.putExtra("creator", creator[0].getText().toString());
                intent.putExtra("createDate", createDate[0].getText().toString());
                intent.putExtra("brand", brand[0].getText().toString());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Log.d("year", String.valueOf(year));
            GregorianCalendar gregorianCalendar =  new GregorianCalendar(year, month, day);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Log.d("date", dateFormat.format(gregorianCalendar));
            ((AddingActivity) getActivity()).button.setText(
                    dateFormat.format(gregorianCalendar));

        }
    }

    public void showDatePickerDialog(View view) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}