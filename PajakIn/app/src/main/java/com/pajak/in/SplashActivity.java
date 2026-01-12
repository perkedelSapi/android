package com.pajak.in;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import java.util.HashMap;

public class SplashActivity extends AppCompatActivity implements LocationListener {

    private TextView textWelcome;
    private LocationManager locationManager;
    private static final int LOCATION_PERMISSION_REQUEST = 100;
    private String greeting = "Selamat datang";
    private boolean locationReceived = false;

    private HashMap<String, String> greetingMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        textWelcome = findViewById(R.id.textWelcome);
        textWelcome.setText("Mendeteksi lokasi...");

        initializeGreetingMap();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    LOCATION_PERMISSION_REQUEST);
        } else {
            startLocationDetection();
        }

        new Handler().postDelayed(() -> {
            if (!locationReceived) {
                updateGreetingMessage();
                proceedToMain();
            }
        }, 5000);
    }

    private void initializeGreetingMap() {
        // ==================== SUMATRA ====================
        // ACEH
        greetingMap.put("5.55,95.32", "Sëlamat datang"); // Banda Aceh
        greetingMap.put("5.18,97.14", "Sëlamat datang"); // Lhokseumawe
        greetingMap.put("4.78,97.00", "Sëlamat datang"); // Langsa
        greetingMap.put("5.09,97.14", "Sëlamat datang"); // Lhoksukon
        greetingMap.put("4.63,96.85", "Sëlamat datang"); // Calang
        greetingMap.put("4.45,97.97", "Sëlamat datang"); // Takengon
        greetingMap.put("3.78,96.83", "Sëlamat datang"); // Meulaboh
        greetingMap.put("2.33,97.80", "Sëlamat datang"); // Singkil

        // SUMATERA UTARA - BATAK
        greetingMap.put("3.58,98.67", "Horas"); // Medan
        greetingMap.put("2.96,99.06", "Horas"); // Pematangsiantar
        greetingMap.put("1.48,99.07", "Horas"); // Padangsidempuan
        greetingMap.put("2.33,99.07", "Horas"); // Sibolga
        greetingMap.put("2.07,98.93", "Horas"); // Balige
        greetingMap.put("2.38,99.13", "Horas"); // Tarutung
        greetingMap.put("2.78,99.08", "Horas"); // Siborong-borong
        greetingMap.put("1.13,97.62", "Ya'ahowu"); // Gunungsitoli (Nias)
        greetingMap.put("0.78,97.77", "Ya'ahowu"); // Teluk Dalam (Nias)

        // SUMATERA BARAT - MINANG
        greetingMap.put("-0.95,100.35", "Salam Datuak"); // Padang
        greetingMap.put("-0.31,100.37", "Salam Datuak"); // Bukittinggi
        greetingMap.put("-1.00,100.62", "Salam Datuak"); // Solok
        greetingMap.put("-0.92,100.42", "Salam Datuak"); // Padang Panjang
        greetingMap.put("-0.26,100.63", "Salam Datuak"); // Payakumbuh
        greetingMap.put("0.08,100.32", "Salam Datuak"); // Pariaman
        greetingMap.put("-1.38,98.95", "Salam Datuak"); // Tuapejat (Mentawai)

        // RIAU - MELAYU
        greetingMap.put("0.53,101.44", "Salam"); // Pekanbaru
        greetingMap.put("1.45,102.15", "Salam"); // Dumai
        greetingMap.put("0.42,101.44", "Salam"); // Siak
        greetingMap.put("-0.37,102.28", "Salam"); // Rengat
        greetingMap.put("0.62,101.75", "Salam"); // Bengkalis
        greetingMap.put("0.82,102.33", "Salam"); // Rokan Hilir

        // KEPULAUAN RIAU
        greetingMap.put("1.08,104.03", "Salam"); // Batam
        greetingMap.put("0.92,104.47", "Salam"); // Tanjungpinang
        greetingMap.put("0.80,104.53", "Salam"); // Tanjung Balai Karimun
        greetingMap.put("1.00,103.93", "Salam"); // Tanjung Uban

        // JAMBI
        greetingMap.put("-1.61,103.61", "Salam"); // Jambi
        greetingMap.put("-1.49,102.91", "Salam"); // Muaro Bungo
        greetingMap.put("-2.06,102.70", "Salam"); // Sarolangun
        greetingMap.put("-2.18,102.91", "Salam"); // Bangko
        greetingMap.put("-1.75,102.90", "Salam"); // Muara Tebo

        // SUMATERA SELATAN - PALEMBANG
        greetingMap.put("-2.99,104.75", "Sugê rawuh"); // Palembang
        greetingMap.put("-3.80,102.26", "Sugê rawuh"); // Prabumulih
        greetingMap.put("-4.02,103.80", "Sugê rawuh"); // Lubuklinggau
        greetingMap.put("-3.40,104.00", "Sugê rawuh"); // Lahat
        greetingMap.put("-3.58,103.13", "Sugê rawuh"); // Muara Enim

        // BENGKULU
        greetingMap.put("-3.79,102.26", "Selamet datang"); // Bengkulu
        greetingMap.put("-3.46,102.54", "Selamet datang"); // Curup
        greetingMap.put("-4.45,102.90", "Selamet datang"); // Manna
        greetingMap.put("-3.80,102.26", "Selamet datang"); // Arga Makmur
        greetingMap.put("-4.22,102.97", "Selamet datang"); // Kepahiang

        // LAMPUNG
        greetingMap.put("-5.43,105.26", "Sugê rawuh"); // Bandar Lampung
        greetingMap.put("-4.81,104.90", "Sugê rawuh"); // Metro
        greetingMap.put("-5.24,105.18", "Sugê rawuh"); // Kalianda
        greetingMap.put("-5.64,105.60", "Sugê rawuh"); // Kota Agung
        greetingMap.put("-5.07,105.33", "Sugê rawuh"); // Kotabumi
        greetingMap.put("-5.28,105.11", "Sugê rawuh"); // Gedong Tataan

        // BANGKA BELITUNG
        greetingMap.put("-2.13,106.11", "Salam"); // Pangkal Pinang
        greetingMap.put("-3.00,107.72", "Salam"); // Tanjung Pandan
        greetingMap.put("-1.85,105.45", "Salam"); // Sungailiat
        greetingMap.put("-2.50,106.05", "Salam"); // Koba
        greetingMap.put("-2.74,107.65", "Salam"); // Manggar
        greetingMap.put("-2.08,105.18", "Salam"); // Muntok
        greetingMap.put("-1.90,105.85", "Salam"); // Belinyu
        greetingMap.put("-2.52,106.42", "Salam"); // Toboali
        greetingMap.put("-3.45,107.72", "Salam"); // Kelapa Kampit
        greetingMap.put("-2.87,107.92", "Salam"); // Sijuk

        // ==================== JAWA ====================
        // BANTEN - SUNDA
        greetingMap.put("-6.12,106.15", "Wilujeng sumping"); // Serang
        greetingMap.put("-6.18,106.63", "Wilujeng sumping"); // Tangerang
        greetingMap.put("-6.31,106.10", "Wilujeng sumping"); // Cilegon
        greetingMap.put("-6.34,106.10", "Wilujeng sumping"); // Pandeglang
        greetingMap.put("-6.31,105.85", "Wilujeng sumping"); // Labuan

        // JAKARTA - SELAMAT DATANG (DIUBAH)
        greetingMap.put("-6.21,106.85", "Selamat datang"); // Jakarta Pusat
        greetingMap.put("-6.23,106.80", "Selamat datang"); // Jakarta Selatan
        greetingMap.put("-6.15,106.80", "Selamat datang"); // Jakarta Barat
        greetingMap.put("-6.18,106.83", "Selamat datang"); // Jakarta Timur
        greetingMap.put("-6.13,106.90", "Selamat datang"); // Jakarta Utara

        // JAWA BARAT - SUNDA
        greetingMap.put("-6.91,107.61", "Wilujeng sumping"); // Bandung
        greetingMap.put("-6.71,108.56", "Wilujeng sumping"); // Cirebon
        greetingMap.put("-6.36,107.39", "Wilujeng sumping"); // Bekasi
        greetingMap.put("-6.87,107.53", "Wilujeng sumping"); // Garut
        greetingMap.put("-7.33,108.22", "Wilujeng sumping"); // Tasikmalaya
        greetingMap.put("-6.70,108.55", "Wilujeng sumping"); // Kuningan
        greetingMap.put("-6.30,106.10", "Wilujeng sumping"); // Depok
        greetingMap.put("-6.56,107.43", "Wilujeng sumping"); // Purwakarta
        greetingMap.put("-6.42,107.18", "Wilujeng sumping"); // Karawang
        greetingMap.put("-7.35,108.21", "Wilujeng sumping"); // Ciamis

        // JAWA TENGAH - JAWA
        greetingMap.put("-6.99,110.42", "Sugeng rawuh"); // Semarang
        greetingMap.put("-7.80,110.36", "Sugeng rawuh"); // Yogyakarta
        greetingMap.put("-6.87,109.13", "Sugeng rawuh"); // Tegal
        greetingMap.put("-7.43,109.24", "Sugeng rawuh"); // Purwokerto
        greetingMap.put("-6.87,109.67", "Sugeng rawuh"); // Pekalongan
        greetingMap.put("-7.04,110.42", "Sugeng rawuh"); // Salatiga
        greetingMap.put("-6.98,110.41", "Sugeng rawuh"); // Demak
        greetingMap.put("-6.90,109.75", "Sugeng rawuh"); // Pemalang
        greetingMap.put("-6.48,110.70", "Sugeng rawuh"); // Jepara
        greetingMap.put("-6.87,111.07", "Sugeng rawuh"); // Rembang

        // JAWA TIMUR - JAWA
        greetingMap.put("-7.25,112.74", "Sugeng rawuh"); // Surabaya
        greetingMap.put("-7.97,112.63", "Sugeng rawuh"); // Malang
        greetingMap.put("-8.17,113.70", "Sugeng rawuh"); // Jember
        greetingMap.put("-7.64,111.52", "Sugeng rawuh"); // Madiun
        greetingMap.put("-7.15,111.88", "Sugeng rawuh"); // Bojonegoro
        greetingMap.put("-7.16,112.65", "Sugeng rawuh"); // Sidoarjo
        greetingMap.put("-7.63,112.00", "Sugeng rawuh"); // Nganjuk
        greetingMap.put("-7.85,111.96", "Sugeng rawuh"); // Ponorogo
        greetingMap.put("-7.15,112.73", "Sugeng rawuh"); // Gresik
        greetingMap.put("-7.98,112.63", "Sugeng rawuh"); // Batu

        // MADURA
        greetingMap.put("-7.15,113.47", "Salamet tangghâ'"); // Pamekasan
        greetingMap.put("-6.90,113.10", "Salamet tangghâ'"); // Sumenep
        greetingMap.put("-7.04,113.93", "Salamet tangghâ'"); // Bangkalan
        greetingMap.put("-7.16,113.48", "Salamet tangghâ'"); // Sampang

        // ==================== BALI & NUSA TENGGARA ====================
        // BALI
        greetingMap.put("-8.41,115.19", "Rahajeng rauh"); // Denpasar
        greetingMap.put("-8.50,115.27", "Rahajeng rauh"); // Badung
        greetingMap.put("-8.54,115.13", "Rahajeng rauh"); // Tabanan
        greetingMap.put("-8.11,115.08", "Rahajeng rauh"); // Singaraja
        greetingMap.put("-8.45,115.26", "Rahajeng rauh"); // Gianyar
        greetingMap.put("-8.40,115.62", "Rahajeng rauh"); // Amlapura

        // NUSA TENGGARA BARAT - SASAK
        greetingMap.put("-8.58,116.13", "Sampun rauh"); // Mataram
        greetingMap.put("-8.53,116.82", "Sampun rauh"); // Sumbawa Besar
        greetingMap.put("-8.45,117.42", "Sampun rauh"); // Bima
        greetingMap.put("-8.73,116.28", "Sampun rauh"); // Praya
        greetingMap.put("-8.57,116.63", "Sampun rauh"); // Gerung

        // NUSA TENGGARA TIMUR
        greetingMap.put("-10.17,123.60", "Mau mai"); // Kupang (Timor)
        greetingMap.put("-8.64,122.24", "Lero walu"); // Ende (Flores)
        greetingMap.put("-8.82,120.97", "Lero walu"); // Ruteng (Manggarai)
        greetingMap.put("-9.66,120.26", "Lero walu"); // Waingapu (Sumba)
        greetingMap.put("-8.62,122.21", "Lero walu"); // Maumere
        greetingMap.put("-8.53,119.87", "Lero walu"); // Labuan Bajo

        // ==================== KALIMANTAN ====================
        // KALIMANTAN BARAT
        greetingMap.put("-0.02,109.34", "Salamat datang"); // Pontianak
        greetingMap.put("0.03,109.34", "Salamat datang"); // Singkawang
        greetingMap.put("-0.03,111.50", "Salamat datang"); // Sintang
        greetingMap.put("0.82,112.93", "Salamat datang"); // Putussibau
        greetingMap.put("0.87,109.48", "Salamat datang"); // Sambas
        greetingMap.put("0.02,109.32", "Salamat datang"); // Mempawah
        greetingMap.put("-0.03,109.34", "Salamat datang"); // Ngabang
        greetingMap.put("0.42,109.95", "Salamat datang"); // Sanggau

        // KALIMANTAN TENGAH - DAYAK
        greetingMap.put("-2.21,113.92", "Lompok andau"); // Palangkaraya
        greetingMap.put("-1.85,113.75", "Lompok andau"); // Sampit
        greetingMap.put("-1.23,113.88", "Lompok andau"); // Pangkalan Bun
        greetingMap.put("-2.20,113.84", "Lompok andau"); // Kasongan
        greetingMap.put("-1.03,114.93", "Lompok andau"); // Kuala Kurun
        greetingMap.put("-0.62,114.67", "Lompok andau"); // Puruk Cahu
        greetingMap.put("-1.83,113.73", "Lompok andau"); // Kuala Pembuang

        // KALIMANTAN SELATAN - BANJAR
        greetingMap.put("-3.32,114.59", "Salamat datang"); // Banjarmasin
        greetingMap.put("-3.31,114.85", "Salamat datang"); // Banjarbaru
        greetingMap.put("-2.68,115.17", "Salamat datang"); // Barabai
        greetingMap.put("-2.43,115.50", "Salamat datang"); // Amuntai
        greetingMap.put("-3.00,115.03", "Salamat datang"); // Kandangan
        greetingMap.put("-2.75,115.25", "Salamat datang"); // Tanjung
        greetingMap.put("-3.42,114.85", "Salamat datang"); // Martapura
        greetingMap.put("-2.97,115.27", "Salamat datang"); // Rantau

        // KALIMANTAN TIMUR
        greetingMap.put("-0.50,117.15", "Salamat datang"); // Samarinda
        greetingMap.put("1.24,116.83", "Salamat datang"); // Balikpapan
        greetingMap.put("0.52,117.13", "Salamat datang"); // Bontang
        greetingMap.put("-1.85,116.17", "Salamat datang"); // Tenggarong
        greetingMap.put("0.12,117.47", "Salamat datang"); // Sangatta
        greetingMap.put("-0.30,117.10", "Salamat datang"); // Loa Janan
        greetingMap.put("-1.25,116.88", "Salamat datang"); // Muara Badak

        // KALIMANTAN UTARA
        greetingMap.put("3.33,117.58", "Salamat datang"); // Tarakan
        greetingMap.put("2.88,117.37", "Salamat datang"); // Tanjung Selor
        greetingMap.put("4.13,117.67", "Salamat datang"); // Nunukan
        greetingMap.put("3.90,117.58", "Salamat datang"); // Sebatik
        greetingMap.put("3.55,117.53", "Salamat datang"); // Malinau

        // ==================== SULAWESI ====================
        // SULAWESI UTARA - MINAHASA/MANADO
        greetingMap.put("1.49,124.84", "Salama' datang"); // Manado
        greetingMap.put("1.45,125.18", "Salama' datang"); // Bitung
        greetingMap.put("0.68,124.31", "Salama' datang"); // Kotamobagu
        greetingMap.put("4.78,127.13", "Salama' datang"); // Tahuna (Sangihe)
        greetingMap.put("3.68,125.50", "Salama' datang"); // Melonguane (Talaud)
        greetingMap.put("1.33,124.92", "Salama' datang"); // Tomohon
        greetingMap.put("0.90,124.78", "Salama' datang"); // Amurang

        // GORONTALO
        greetingMap.put("0.54,123.06", "U'otalo"); // Gorontalo
        greetingMap.put("0.70,122.43", "U'otalo"); // Limboto
        greetingMap.put("0.55,123.08", "U'otalo"); // Telaga

        // SULAWESI TENGAH - KAILI
        greetingMap.put("-0.90,119.87", "Salama datang"); // Palu
        greetingMap.put("-1.00,120.50", "Salama datang"); // Poso
        greetingMap.put("-0.88,119.87", "Salama datang"); // Donggala
        greetingMap.put("-0.53,119.85", "Salama datang"); // Parigi
        greetingMap.put("-1.38,120.75", "Salama datang"); // Tentena
        greetingMap.put("-1.40,120.77", "Salama datang"); // Ampana

        // SULAWESI SELATAN - BUGIS/MAKASSAR
        greetingMap.put("-5.16,119.43", "Salama' datang"); // Makassar
        greetingMap.put("-4.02,119.63", "Salama' datang"); // Parepare
        greetingMap.put("-4.77,119.50", "Salama' datang"); // Maros
        greetingMap.put("-5.13,119.41", "Salama' datang"); // Gowa
        greetingMap.put("-4.80,119.55", "Salama' datang"); // Pangkajene
        greetingMap.put("-3.98,120.20", "Salama' datang"); // Palopo
        greetingMap.put("-4.54,119.98", "Salama' datang"); // Sinjai
        greetingMap.put("-4.10,119.85", "Salama' datang"); // Barru
        greetingMap.put("-3.94,119.78", "Salama' datang"); // Soppeng
        greetingMap.put("-5.20,119.45", "Salama' datang"); // Takalar

        // SULAWESI TENGGARA
        greetingMap.put("-4.00,122.52", "Salama datang"); // Kendari
        greetingMap.put("-3.86,122.22", "Salama datang"); // Baubau
        greetingMap.put("-4.13,122.53", "Salama datang"); // Wakatobi
        greetingMap.put("-5.47,122.60", "Salama datang"); // Raha (Muna)
        greetingMap.put("-4.08,122.52", "Salama datang"); // Unaaha
        greetingMap.put("-3.93,122.50", "Salama datang"); // Kolaka

        // SULAWESI BARAT - MANDAR
        greetingMap.put("-2.68,118.89", "Salama' datang"); // Mamuju
        greetingMap.put("-3.35,119.38", "Salama' datang"); // Majene
        greetingMap.put("-3.40,119.33", "Salama' datang"); // Polewali
        greetingMap.put("-3.10,119.03", "Salama' datang"); // Mamasa
        greetingMap.put("-2.70,118.88", "Salama' datang"); // Mamuju Tengah

        // ==================== MALUKU ====================
        // MALUKU
        greetingMap.put("-3.70,128.17", "Beta ale sio"); // Ambon
        greetingMap.put("-3.31,127.10", "Beta ale sio"); // Masohi
        greetingMap.put("-5.63,132.75", "Beta ale sio"); // Tual (Kei)
        greetingMap.put("-3.32,130.72", "Beta ale sio"); // Dobo (Aru)
        greetingMap.put("-5.57,134.28", "Beta ale sio"); // Saumlaki (Tanimbar)
        greetingMap.put("-3.10,129.48", "Beta ale sio"); // Namlea (Buru)

        // MALUKU UTARA
        greetingMap.put("0.68,127.40", "Jaga-jaga ale"); // Ternate
        greetingMap.put("1.63,125.07", "Jaga-jaga ale"); // Tidore
        greetingMap.put("1.87,127.83", "Jaga-jaga ale"); // Tobelo
        greetingMap.put("0.73,127.57", "Jaga-jaga ale"); // Sofifi
        greetingMap.put("0.90,127.33", "Jaga-jaga ale"); // Jailolo

        // ==================== PAPUA ====================
        // PAPUA
        greetingMap.put("-2.53,140.72", "Selamat datang"); // Jayapura
        greetingMap.put("-4.08,138.95", "Selamat datang"); // Wamena
        greetingMap.put("-3.72,136.17", "Selamat datang"); // Nabire
        greetingMap.put("-6.13,141.00", "Selamat datang"); // Merauke
        greetingMap.put("-4.54,136.88", "Selamat datang"); // Timika
        greetingMap.put("-3.37,135.50", "Selamat datang"); // Enarotali
        greetingMap.put("-1.87,138.75", "Selamat datang"); // Sarmi
        greetingMap.put("-2.58,140.52", "Selamat datang"); // Sentani
        greetingMap.put("-3.90,139.63", "Selamat datang"); // Oksibil
        greetingMap.put("-2.75,140.70", "Selamat datang"); // Abepura

        // PAPUA BARAT & DAERAH
        greetingMap.put("-0.86,134.08", "Selamat datang"); // Manokwari
        greetingMap.put("-1.88,132.25", "Selamat datang"); // Sorong
        greetingMap.put("-0.93,134.05", "Selamat datang"); // Ransiki
        greetingMap.put("-1.17,131.35", "Selamat datang"); // Sorong Kota
        greetingMap.put("-0.78,133.08", "Selamat datang"); // Bintuni
        greetingMap.put("-1.48,134.12", "Selamat datang"); // Wasior
        greetingMap.put("-0.90,134.03", "Selamat datang"); // Kebar
        greetingMap.put("-2.08,133.58", "Selamat datang"); // Teminabuan
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationDetection();
            } else {
                proceedToMain();
            }
        }
    }

    private void startLocationDetection() {
        try {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestSingleUpdate(LocationManager.GPS_PROVIDER, this, null);
                }
            } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null);
                }
            } else {
                proceedToMain();
            }
        } catch (Exception e) {
            proceedToMain();
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null && !locationReceived) {
            locationReceived = true;

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            findGreetingForLocation(latitude, longitude);

            updateGreetingMessage();
            proceedToMain();
        }
    }

    private void findGreetingForLocation(double lat, double lon) {
        String nearestKey = null;
        double minDistance = Double.MAX_VALUE;

        for (String key : greetingMap.keySet()) {
            String[] coords = key.split(",");
            double mapLat = Double.parseDouble(coords[0]);
            double mapLon = Double.parseDouble(coords[1]);

            double distance = Math.sqrt(
                    Math.pow(lat - mapLat, 2) +
                            Math.pow(lon - mapLon, 2)
            );

            if (distance < minDistance) {
                minDistance = distance;
                nearestKey = key;
            }
        }

        if (nearestKey != null && minDistance < 1.5) {
            greeting = greetingMap.get(nearestKey);
        } else {
            setGreetingByRegion(lat, lon);
        }
    }

    private void setGreetingByRegion(double lat, double lon) {
        // Tambahkan kondisi khusus untuk Jakarta
        if (lat >= -6.3 && lat <= -6.0 && lon >= 106.7 && lon <= 107.0) {
            greeting = "Selamat datang"; // Jakarta
            return;
        }

        // SUMATRA
        if (lat >= -6.0 && lat <= 6.0 && lon >= 95.0 && lon <= 106.0) {
            if (lat >= 2.0 && lat <= 4.0 && lon >= 98.0 && lon <= 100.0) {
                greeting = "Horas"; // Batak
            } else if (lat >= -1.5 && lat <= 1.0 && lon >= 100.0 && lon <= 102.0) {
                greeting = "Salam Datuak"; // Minang
            } else if (lat >= -6.0 && lat <= 2.0 && lon >= 104.0 && lon <= 106.0) {
                greeting = "Sugê rawuh"; // Palembang/Lampung
            } else if (lat >= -4.0 && lat <= -1.0 && lon >= 105.0 && lon <= 108.0) {
                greeting = "Salam"; // Bangka Belitung
            } else {
                greeting = "Salam"; // Melayu umum
            }
        }
        // JAWA
        else if (lat >= -9.0 && lat <= -6.0 && lon >= 105.0 && lon <= 115.0) {
            if (lon >= 105.0 && lon <= 108.0) {
                greeting = "Wilujeng sumping"; // Sunda
            } else if (lon >= 113.0 && lon <= 114.0) {
                greeting = "Salamet tangghâ'"; // Madura
            } else {
                greeting = "Sugeng rawuh"; // Jawa
            }
        }
        // BALI & NUSA TENGGARA
        else if (lat >= -11.0 && lat <= -8.0 && lon >= 115.0 && lon <= 125.0) {
            if (lat >= -8.8 && lat <= -8.0) {
                greeting = "Rahajeng rauh"; // Bali
            } else if (lat >= -9.0 && lat <= -8.0) {
                greeting = "Sampun rauh"; // Sasak (Lombok)
            } else {
                greeting = "Mau mai"; // NTT
            }
        }
        // KALIMANTAN
        else if (lat >= -4.0 && lat <= 7.0 && lon >= 108.0 && lon <= 119.0) {
            if (lat >= -3.5 && lat <= -2.0 && lon >= 114.0 && lon <= 115.5) {
                greeting = "Salamat datang"; // Banjar
            } else if (lat >= -3.0 && lat <= 0.0 && lon >= 110.0 && lon <= 113.0) {
                greeting = "Lompok andau"; // Dayak
            } else {
                greeting = "Salamat datang"; // Kalimantan umum
            }
        }
        // SULAWESI
        else if (lat >= -6.0 && lat <= 2.0 && lon >= 118.0 && lon <= 125.0) {
            if (lat >= 0.5 && lat <= 2.0) {
                greeting = "Salama' datang"; // Manado
            } else if (lat >= -1.5 && lat <= 0.0) {
                greeting = "U'otalo"; // Gorontalo
            } else if (lat >= -5.5 && lat <= -4.0) {
                greeting = "Salama' datang"; // Bugis/Makassar
            } else if (lat >= -3.5 && lat <= -2.5) {
                greeting = "Salama' datang"; // Mandar
            } else {
                greeting = "Salama datang"; // Sulawesi umum
            }
        }
        // MALUKU
        else if (lat >= -8.0 && lat <= 2.0 && lon >= 125.0 && lon <= 135.0) {
            if (lat >= -3.0 && lat <= 1.0) {
                greeting = "Jaga-jaga ale"; // Maluku Utara
            } else {
                greeting = "Beta ale sio"; // Maluku
            }
        }
        // PAPUA
        else if (lat >= -10.0 && lat <= -1.0 && lon >= 130.0 && lon <= 141.0) {
            greeting = "Selamat datang"; // Papua
        }
        else {
            greeting = "Selamat datang"; // Default
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}

    private void updateGreetingMessage() {
        runOnUiThread(() -> {
            textWelcome.setText(greeting);
        });
    }

    private void proceedToMain() {
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }, 2500);
    }
}