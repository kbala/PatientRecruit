package com.oneworldinc.android.patientrecruit;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {

    EditText firstNameEditView, lastNameEditView, emailIdEditView, cityEditView, zipEditView;
    Spinner stateSpinner, specialtySpinner;
    Button next;
    String firstName, lastName, emilId, city, state, zip, specialty;
    String[] stateDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View view = getLayoutInflater().inflate(R.layout.action_bar_view, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        TextView titleView = (TextView) view.findViewById(R.id.title_text);
        titleView.setText(R.string.actionBar_title);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setElevation(0);
            actionBar.setCustomView(view);
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));
            actionBar.setDisplayShowCustomEnabled(true);
        }

        firstNameEditView = (EditText) findViewById(R.id.firstName);
        lastNameEditView = (EditText) findViewById(R.id.lastName);
        emailIdEditView = (EditText) findViewById(R.id.email);
        cityEditView = (EditText) findViewById(R.id.city);
        zipEditView = (EditText) findViewById(R.id.zip);
        next = (Button) findViewById(R.id.next);

        stateSpinner = (Spinner) findViewById(R.id.state);

//Edit Text view click Time only open the keyboard
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(firstNameEditView.getWindowToken(), 0);
        mgr.hideSoftInputFromWindow(lastNameEditView.getWindowToken(), 0);
        mgr.hideSoftInputFromWindow(emailIdEditView.getWindowToken(), 0);
        mgr.hideSoftInputFromWindow(cityEditView.getWindowToken(), 0);
        mgr.hideSoftInputFromWindow(zipEditView.getWindowToken(), 0);
        mgr.showSoftInput(firstNameEditView, InputMethodManager.SHOW_IMPLICIT);
        mgr.showSoftInput(lastNameEditView, InputMethodManager.SHOW_IMPLICIT);
        mgr.showSoftInput(emailIdEditView, InputMethodManager.SHOW_IMPLICIT);
        mgr.showSoftInput(cityEditView, InputMethodManager.SHOW_IMPLICIT);
        mgr.showSoftInput(zipEditView, InputMethodManager.SHOW_IMPLICIT);


        boolean tabletSize = getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {
            stateDetail = new String[]{"AZ", "NY", "AL", "AK", "AR", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "HI",
                    "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH",
                    "NJ", "NM", "NC", "ND", "OH", "OK", "OR", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};

        } else {
            specialtySpinner = (Spinner) findViewById(R.id.specialty);
            String[] specialtyItem = new String[]{"Pulmonologist", "Cardiologist", "Hematologist", "Rheumatologist", "Infectious Disease", "Other"};
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, specialtyItem);
            specialtySpinner.setAdapter(adapter);
            stateDetail = new String[]{"NY", "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DC", "DE", "FL", "GA", "HI",
                    "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH",
                    "NJ", "NM", "NC", "ND", "OH", "OK", "OR", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
        }


        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, stateDetail);//State = AZ(tab)

        stateSpinner.setAdapter(stateAdapter);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateRegisterInfo();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.profile) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //register view validate
    public void validateRegisterInfo() {

        firstNameEditView.setError(null);
        lastNameEditView.setError(null);
        emailIdEditView.setError(null);
        cityEditView.setError(null);
        zipEditView.setError(null);

        firstName = firstNameEditView.getText().toString();
        lastName = lastNameEditView.getText().toString();
        emilId = emailIdEditView.getText().toString();
        city = cityEditView.getText().toString();
        zip = zipEditView.getText().toString();
        state = stateSpinner.getSelectedItem().toString();
        try {
            specialty = specialtySpinner.getSelectedItem().toString();
        } catch (Exception e) {
            e.printStackTrace();

        }

        boolean cancel = false;
        View focusView = null;

        try {
            if (specialty.equals("Specialty")) {
                TextView specialtyTextView = (TextView) specialtySpinner.getSelectedView();
                focusView = specialtyTextView;
                specialtyTextView.setError("");
//            specialtyTextView.setTextColor(Color.RED);
                specialtyTextView.setText("Specialty");
                cancel = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(zip)) {
            zipEditView.setError(getString(R.string.error_field_required));
            focusView = zipEditView;
            cancel = true;
        }
        if (state.equals("State")) {
            TextView stateTextView = (TextView) stateSpinner.getSelectedView();
            focusView = stateTextView;
            stateTextView.setError("");
//            stateTextView.setTextColor(Color.RED);
            stateTextView.setText("State");
            cancel = true;
        }
        if (TextUtils.isEmpty(city)) {
            cityEditView.setError(getString(R.string.error_field_required));
            focusView = cityEditView;
            cancel = true;
        }
        if (TextUtils.isEmpty(emilId)) {
            emailIdEditView.setError(getString(R.string.error_field_required));
            focusView = emailIdEditView;
            cancel = true;
        } else if (!isEmailValid(emilId)) {
            emailIdEditView.setError("This email address is invalid");
            focusView = emailIdEditView;
            cancel = true;
        }
        if (TextUtils.isEmpty(lastName)) {
            lastNameEditView.setError(getString(R.string.error_field_required));
            focusView = lastNameEditView;
            cancel = true;
        }
        if (TextUtils.isEmpty(firstName)) {
            firstNameEditView.setError(getString(R.string.error_field_required));
            focusView = firstNameEditView;
            cancel = true;
        }

        //cancel = false;

        if (cancel) {
            focusView.requestFocus();
        } else {
            Intent intent = new Intent(MainActivity.this, TrialActivity.class);
            finish();
            startActivity(intent);
        }
    }

    //mail id validate
    private boolean isEmailValid(String email) {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        return matcher.matches();
    }
}
