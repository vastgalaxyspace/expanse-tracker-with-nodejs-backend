package com.example.jforce;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class addexpnese extends AppCompatActivity {
    private EditText titleEditText, amountEditText, dateEditText, descriptionEditText;
    private Button datePickButton, saveExpenseToServer;
    private Exapi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addexpnese);


        titleEditText = findViewById(R.id.expenseName);
        amountEditText = findViewById(R.id.amount);
        dateEditText = findViewById(R.id.date);
        descriptionEditText = findViewById(R.id.description);
        datePickButton = findViewById(R.id.idBtnPickDate);
        saveExpenseToServer = findViewById(R.id.addExpenseButton);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Exapi.class);


        datePickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });


        saveExpenseToServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {
                    saveExpenseToServer();
                }
            }
        });
    }

    private boolean validateInputs() {
        String title = titleEditText.getText().toString().trim();
        String amountStr = amountEditText.getText().toString().trim();
        String date = dateEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "title required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (amountStr.isEmpty()) {
            Toast.makeText(this, "amount required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (date.isEmpty()) {
            Toast.makeText(this, "date required", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Integer.parseInt(amountStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "valid number", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void saveExpenseToServer() {
        String title = titleEditText.getText().toString();
        int amount = Integer.parseInt(amountEditText.getText().toString());
        String date = dateEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        Expanse expanse = new Expanse(title, amount, date, description);

        Call<Void> call = api.saveexpense(expanse);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {

                    Intent intent=new Intent(addexpnese.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {
                    String errorMessage = "Failed " + response.message();
                    Toast.makeText(addexpnese.this, errorMessage, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                addexpnese.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
                        dateEditText.setText(String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay));
                    }
                },
                year, month, day
        );

        datePickerDialog.show();
    }
}
