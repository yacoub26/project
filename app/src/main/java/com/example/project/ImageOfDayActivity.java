package com.example.project;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.database.EntityClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Objects;

public class ImageOfDayActivity extends AppCompatActivity {


    TextView btnPickDate, tvLink;
    String date = "";
    Button btnSearch;
    ProgressDialog progressDialog;
    ImageView btnFav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_of_day);

        btnPickDate = findViewById(R.id.btnPickDate);

        btnSearch = findViewById(R.id.btnSearch);
        tvLink = findViewById(R.id.tvLink);
        btnFav = findViewById(R.id.btnFav);

        btnPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ImageOfDayActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        date = (i + "-" + (i1 + 1) + "-" + i2);
                        btnPickDate.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Objects.equals(date, "")) {
                    String url = "https://api.nasa.gov/planetary/apod?api_key=DgPLcIlnmN0Cwrzcg3e9NraFaYLIDI68Ysc6Zh3d&date=" + date;
                    new MyAsyncTasks(url).execute();
                } else {
                    Toast.makeText(ImageOfDayActivity.this, "Please Select Date", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EntityClass entityClass = new EntityClass();
                entityClass.date=date;
                entityClass.url=tvLink.getText().toString();
                MainActivity.db.savedFilesDao().insertAll(entityClass);
                Toast.makeText(ImageOfDayActivity.this, "Image saved", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class MyAsyncTasks extends AsyncTask<String, String, String> {

        String apiUrl = "";
        public MyAsyncTasks(String url) {
            apiUrl=url;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(ImageOfDayActivity.this);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            String current = "";
            try {
                URL url;
                HttpURLConnection urlConnection = null;
                try {
                    url = new URL(apiUrl);

                    urlConnection = (HttpURLConnection) url
                            .openConnection();

                    InputStream in = urlConnection.getInputStream();

                    InputStreamReader isw = new InputStreamReader(in);

                    int data = isw.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isw.read();
                        System.out.print(current);

                    }
                    return current;

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            try {
                JSONObject result = new JSONObject(s);

                String url = result.getString("hdurl");
                String date = result.getString("date");
                btnFav.setVisibility(View.VISIBLE);
                tvLink.setVisibility(View.VISIBLE);
                tvLink.setText(url);

            } catch (JSONException e) {
                Toast.makeText(ImageOfDayActivity.this, "No result Found", Toast.LENGTH_SHORT).show();
                tvLink.setVisibility(View.GONE);
                btnFav.setVisibility(View.GONE);
                e.printStackTrace();
            }


        }

    }
}