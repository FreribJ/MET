package com.example.met;

import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.met.dataObjects.Weather;
import com.example.met.databinding.ActivityMainBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    public static DatabaseHelper db;
    String user = "";
    private AppBarConfiguration appBarConfiguration;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        DatabaseHelper db = new DatabaseHelper(this);

        com.example.met.databinding.ActivityMainBinding binding =
                ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //Überprüfen ob schon ein Benutzer erstellt worden ist und dann halt den direkt weiter
        // skippen

        if (db.getUser() == null) {
            navController.navigate(R.id.userCreationFragment);
        }
        locationManager = getSystemService(LocationManager.class);
        initLocationListener();
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
        if (id == R.id.action_editUser) {
            Navigation.findNavController(this, R.id.nav_host_fragment_content_main).navigate(R.id.userCreationFragment);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this,
                R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration) || super.onSupportNavigateUp();
    }

    private void initLocationListener() {
        LocationListener locationListener = (location) -> {
            new Thread(() -> {
                try {
                    getWeather(location.getLatitude(), location.getLongitude());
                } catch (IOException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        };
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 1,
                locationListener);
    }

    private void getWeather(double lat, double lon) throws IOException, JSONException {
        String apiKey = "98a1fcd8f66ee01cf44ecc347b0b12db";
        HttpURLConnection connection =
                (HttpURLConnection) new URL("https://api.openweathermap" + ".org/data/2" + ".5" +
                        "/weather?lat=" + lat + "&lon=" + lon + "&appid=" + apiKey).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        if (connection.getResponseCode() == 200) {
            StringBuilder response = new StringBuilder();
            new BufferedReader(new InputStreamReader(connection.getInputStream())).lines().forEach(response::append);
            connection.disconnect();
            JSONObject jsonObject = new JSONObject(response.toString());
            Weather weather = new ViewModelProvider(this).get(Weather.class);
            if (jsonObject.has("name")) {
                String name = jsonObject.getString("name");
                runOnUiThread(() -> weather.getName().setValue(name));
            }
            if (jsonObject.has("main")) {
                JSONObject main = jsonObject.getJSONObject("main");
                if (main.has("temp")) {
                    double temp = main.getDouble("temp");
                    runOnUiThread(() -> weather.getTemp().setValue(temp));
                }
                if (main.has("feels_like")) {
                    double feelsLike = main.getDouble("feels_like");
                    runOnUiThread(() -> weather.getFeelsLike().setValue(feelsLike));
                }
                if (main.has("icon")) {
                    String icon = main.getString("icon");
                    runOnUiThread(() -> weather.getIcon().setValue(icon));
                }
            }
            if (jsonObject.has("clouds")) {
                JSONObject clouds = jsonObject.getJSONObject("clouds");
                if (clouds.has("all")) {
                    String all = clouds.getString("all");
                    runOnUiThread(() -> weather.getClouds().setValue(Double.parseDouble(all)));
                }
            }
        } else {
            Log.d("WeatherAPICall", "Error: " + connection.getResponseCode());
        }
    }

    public void test() {

    }
}