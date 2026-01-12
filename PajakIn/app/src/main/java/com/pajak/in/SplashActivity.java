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

        // ==================== NEGARA LAIN ====================
        // ASIA
        // Malaysia - Bahasa Melayu
        greetingMap.put("3.14,101.69", "Selamat datang"); // Kuala Lumpur
        greetingMap.put("1.56,110.34", "Selamat datang"); // Kuching
        greetingMap.put("5.41,100.33", "Selamat datang"); // George Town
        greetingMap.put("4.24,117.97", "Selamat datang"); // Kota Kinabalu
        greetingMap.put("3.07,101.61", "Selamat datang"); // Petaling Jaya
        greetingMap.put("2.72,101.94", "Selamat datang"); // Putrajaya
        greetingMap.put("3.80,103.32", "Selamat datang"); // Kuantan
        greetingMap.put("2.21,102.25", "Selamat datang"); // Melaka
        greetingMap.put("6.13,102.25", "Selamat datang"); // Kota Bharu

        // Singapura - Bahasa Melayu/Inggris
        greetingMap.put("1.35,103.82", "Welcome"); // Singapore

        // Thailand - Bahasa Thailand
        greetingMap.put("13.75,100.50", "ยินดีต้อนรับ (Yin dee dtôn ráp)"); // Bangkok
        greetingMap.put("18.80,98.98", "ยินดีต้อนรับ (Yin dee dtôn ráp)"); // Chiang Mai
        greetingMap.put("7.01,100.47", "ยินดีต้อนรับ (Yin dee dtôn ráp)"); // Hat Yai
        greetingMap.put("12.93,100.88", "ยินดีต้อนรับ (Yin dee dtôn ráp)"); // Pattaya
        greetingMap.put("13.04,101.49", "ยินดีต้อนรับ (Yin dee dtôn ráp)"); // Chonburi
        greetingMap.put("15.23,104.86", "ยินดีต้อนรับ (Yin dee dtôn ráp)"); // Ubon Ratchathani
        greetingMap.put("16.44,102.83", "ยินดีต้อนรับ (Yin dee dtôn ráp)"); // Khon Kaen
        greetingMap.put("17.98,102.63", "ยินดีต้อนรับ (Yin dee dtôn ráp)"); // Nong Khai

        // Filipina - Bahasa Tagalog/Filipino
        greetingMap.put("14.60,120.98", "Maligayang pagdating"); // Manila
        greetingMap.put("10.72,122.56", "Maligayang pagdating"); // Iloilo City
        greetingMap.put("7.07,125.60", "Maligayang pagdating"); // Davao City
        greetingMap.put("10.32,123.90", "Maligayang pagdating"); // Cebu City
        greetingMap.put("16.41,120.60", "Maligayang pagdating"); // Baguio
        greetingMap.put("8.48,124.65", "Maligayang pagdating"); // Cagayan de Oro
        greetingMap.put("13.16,123.73", "Maligayang pagdating"); // Legazpi
        greetingMap.put("6.92,122.08", "Maligayang pagdating"); // Zamboanga City

        // Vietnam - Bahasa Vietnam
        greetingMap.put("21.03,105.85", "Chào mừng"); // Hanoi
        greetingMap.put("10.82,106.63", "Chào mừng"); // Ho Chi Minh City
        greetingMap.put("16.07,108.22", "Chào mừng"); // Da Nang
        greetingMap.put("11.94,108.44", "Chào mừng"); // Da Lat
        greetingMap.put("20.85,106.68", "Chào mừng"); // Hai Phong
        greetingMap.put("10.35,107.07", "Chào mừng"); // Vung Tau
        greetingMap.put("9.18,105.15", "Chào mừng"); // Can Tho
        greetingMap.put("21.39,103.02", "Chào mừng"); // Son La

        // Kamboja - Bahasa Khmer
        greetingMap.put("11.55,104.92", "សូមស្វាគមន៍ (Soum suor sdei)"); // Phnom Penh
        greetingMap.put("13.36,103.86", "សូមស្វាគមន៍ (Soum suor sdei)"); // Siem Reap
        greetingMap.put("10.63,103.50", "សូមស្វាគមន៍ (Soum suor sdei)"); // Sihanoukville
        greetingMap.put("12.25,105.65", "សូមស្វាគមន៍ (Soum suor sdei)"); // Kampong Cham

        // Laos - Bahasa Lao
        greetingMap.put("17.97,102.60", "ຍິນດີຕ້ອນຮັບ (Yindī tǭn háp)"); // Vientiane
        greetingMap.put("19.88,102.13", "ຍິນດີຕ້ອນຮັບ (Yindī tǭn háp)"); // Luang Prabang
        greetingMap.put("15.12,105.80", "ຍິນດີຕ້ອນຮັບ (Yindī tǭn háp)"); // Pakse
        greetingMap.put("20.42,104.05", "ຍິນດີຕ້ອນຮັບ (Yindī tǭn háp)"); // Sam Neua

        // Myanmar (Burma) - Bahasa Myanmar
        greetingMap.put("16.80,96.15", "ကြိုဆိုပါတယ် (Kyo saw par tal)"); // Yangon
        greetingMap.put("19.75,96.12", "ကြိုဆိုပါတယ် (Kyo saw par tal)"); // Naypyidaw
        greetingMap.put("21.97,96.08", "ကြိုဆိုပါတယ် (Kyo saw par tal)"); // Mandalay
        greetingMap.put("16.48,97.63", "ကြိုဆိုပါတယ် (Kyo saw par tal)"); // Mawlamyine

        // Nepal - Bahasa Nepali
        greetingMap.put("27.71,85.32", "स्वागतम् (Swāgatam)"); // Kathmandu
        greetingMap.put("27.68,85.32", "स्वागतम् (Swāgatam)"); // Patan
        greetingMap.put("26.73,87.28", "स्वागतम् (Swāgatam)"); // Biratnagar
        greetingMap.put("28.23,83.99", "स्वागतम् (Swāgatam)"); // Pokhara

        // Bangladesh - Bahasa Bengali
        greetingMap.put("23.81,90.41", "স্বাগতম (Sbāgôtôm)"); // Dhaka
        greetingMap.put("22.33,91.80", "স্বাগতম (Sbāgôtôm)"); // Chittagong
        greetingMap.put("24.37,88.60", "স্বাগতম (Sbāgôtôm)"); // Rajshahi
        greetingMap.put("22.70,90.37", "স্বাগতম (Sbāgôtôm)"); // Barisal

        // Sri Lanka - Bahasa Sinhala
        greetingMap.put("6.93,79.85", "ආයුබෝවන් (Āyubōvan)"); // Colombo
        greetingMap.put("7.30,80.64", "ආයුබෝවන් (Āyubōvan)"); // Kandy
        greetingMap.put("6.03,80.22", "ආයුබෝවන් (Āyubōvan)"); // Galle
        greetingMap.put("9.67,80.02", "ආයුබෝවන් (Āyubōvan)"); // Jaffna

        // Pakistan - Bahasa Urdu
        greetingMap.put("33.69,73.06", "خوش آمدید (Khush āmadīd)"); // Islamabad
        greetingMap.put("24.86,67.01", "خوش آمدید (Khush āmadīd)"); // Karachi
        greetingMap.put("31.55,74.34", "خوش آمدید (Khush āmadīd)"); // Lahore
        greetingMap.put("34.01,71.58", "خوش آمدید (Khush āmadīd)"); // Peshawar

        // India - Bahasa Hindi
        greetingMap.put("28.61,77.23", "स्वागत है (Swagat hai)"); // New Delhi
        greetingMap.put("19.08,72.88", "स्वागत है (Swagat hai)"); // Mumbai
        greetingMap.put("12.97,77.59", "स्वागत है (Swagat hai)"); // Bengaluru
        greetingMap.put("13.08,80.27", "स्वागत है (Swagat hai)"); // Chennai
        greetingMap.put("22.57,88.36", "स्वागत है (Swagat hai)"); // Kolkata
        greetingMap.put("26.85,80.95", "स्वागत है (Swagat hai)"); // Lucknow
        greetingMap.put("28.70,77.10", "स्वागत है (Swagat hai)"); // Ghaziabad
        greetingMap.put("17.39,78.49", "स्वागत है (Swagat hai)"); // Hyderabad

        // China - Bahasa Mandarin
        greetingMap.put("39.90,116.41", "欢迎 (Huānyíng)"); // Beijing
        greetingMap.put("31.23,121.47", "欢迎 (Huānyíng)"); // Shanghai
        greetingMap.put("23.13,113.26", "欢迎 (Huānyíng)"); // Guangzhou
        greetingMap.put("30.66,104.06", "欢迎 (Huānyíng)"); // Chengdu
        greetingMap.put("34.27,108.90", "欢迎 (Huānyíng)"); // Xi'an
        greetingMap.put("22.54,114.06", "欢迎 (Huānyíng)"); // Shenzhen
        greetingMap.put("29.56,106.55", "欢迎 (Huānyíng)"); // Chongqing
        greetingMap.put("38.04,114.48", "欢迎 (Huānyíng)"); // Shijiazhuang

        // Jepang - Bahasa Jepang
        greetingMap.put("35.68,139.69", "ようこそ (Yōkoso)"); // Tokyo
        greetingMap.put("34.69,135.50", "ようこそ (Yōkoso)"); // Osaka
        greetingMap.put("35.18,136.91", "ようこそ (Yōkoso)"); // Nagoya
        greetingMap.put("43.06,141.35", "ようこそ (Yōkoso)"); // Sapporo
        greetingMap.put("35.44,139.64", "ようこそ (Yōkoso)"); // Yokohama
        greetingMap.put("34.67,133.92", "ようこそ (Yōkoso)"); // Okayama
        greetingMap.put("38.27,140.87", "ようこそ (Yōkoso)"); // Sendai
        greetingMap.put("33.59,130.40", "ようこそ (Yōkoso)"); // Fukuoka

        // Korea Selatan - Bahasa Korea
        greetingMap.put("37.57,126.98", "환영합니다 (Hwanyeonghamnida)"); // Seoul
        greetingMap.put("35.18,129.08", "환영합니다 (Hwanyeonghamnida)"); // Busan
        greetingMap.put("35.82,127.15", "환영합니다 (Hwanyeonghamnida)"); // Jeonju
        greetingMap.put("36.80,127.15", "환영합니다 (Hwanyeonghamnida)"); // Cheonan
        greetingMap.put("37.45,126.70", "환영합니다 (Hwanyeonghamnida)"); // Incheon
        greetingMap.put("35.96,126.71", "환영합니다 (Hwanyeonghamnida)"); // Gunsan
        greetingMap.put("36.63,127.49", "환영합니다 (Hwanyeonghamnida)"); // Daejeon
        greetingMap.put("35.54,129.31", "환영합니다 (Hwanyeonghamnida)"); // Ulsan

        // Taiwan - Bahasa Mandarin
        greetingMap.put("25.03,121.56", "歡迎 (Huānyíng)"); // Taipei
        greetingMap.put("22.63,120.27", "歡迎 (Huānyíng)"); // Kaohsiung
        greetingMap.put("24.15,120.67", "歡迎 (Huānyíng)"); // Taichung
        greetingMap.put("24.95,121.22", "歡迎 (Huānyíng)"); // Taoyuan

        // Hong Kong - Kanton/Inggris
        greetingMap.put("22.32,114.17", "歡迎 (Huānyíng)"); // Hong Kong

        // Makau - Kanton/Portugis
        greetingMap.put("22.20,113.54", "Bem-vindo"); // Macau

        // Mongolia - Bahasa Mongolia
        greetingMap.put("47.92,106.92", "Тавтай морил (Tavtai moril)"); // Ulaanbaatar

        // Bhutan - Bahasa Dzongkha
        greetingMap.put("27.47,89.64", "བྱོན་པ་ལེགས་སོ (Jömpa lek so)"); // Thimphu

        // Brunei - Bahasa Melayu
        greetingMap.put("4.94,114.95", "Selamat datang"); // Bandar Seri Begawan

        // Timor Leste - Bahasa Tetun/Portugis
        greetingMap.put("-8.56,125.56", "Bem-vindo"); // Dili

        // Papua Nugini - Bahasa Tok Pisin/Inggris
        greetingMap.put("-9.48,147.15", "Welcome"); // Port Moresby

        // AMERIKA UTARA
        // USA - Bahasa Inggris
        greetingMap.put("38.91,-77.04", "Welcome"); // Washington DC
        greetingMap.put("40.71,-74.01", "Welcome"); // New York
        greetingMap.put("34.05,-118.24", "Welcome"); // Los Angeles
        greetingMap.put("41.88,-87.63", "Welcome"); // Chicago
        greetingMap.put("29.76,-95.37", "Welcome"); // Houston
        greetingMap.put("33.45,-112.07", "Welcome"); // Phoenix
        greetingMap.put("39.74,-104.99", "Welcome"); // Denver
        greetingMap.put("37.77,-122.42", "Welcome"); // San Francisco

        // Kanada - Bahasa Inggris/Perancis
        greetingMap.put("45.42,-75.70", "Welcome"); // Ottawa (Inggris)
        greetingMap.put("45.42,-75.70", "Bienvenue"); // Ottawa (Perancis)
        greetingMap.put("43.65,-79.38", "Welcome"); // Toronto
        greetingMap.put("49.28,-123.12", "Welcome"); // Vancouver
        greetingMap.put("45.50,-73.57", "Bienvenue"); // Montreal (Perancis)
        greetingMap.put("45.50,-73.57", "Welcome"); // Montreal (Inggris)
        greetingMap.put("51.05,-114.07", "Welcome"); // Calgary
        greetingMap.put("53.54,-113.49", "Welcome"); // Edmonton

        // Meksiko - Bahasa Spanyol
        greetingMap.put("19.43,-99.13", "Bienvenido"); // Mexico City
        greetingMap.put("20.67,-103.35", "Bienvenido"); // Guadalajara
        greetingMap.put("25.67,-100.30", "Bienvenido"); // Monterrey
        greetingMap.put("21.16,-86.85", "Bienvenido"); // Cancun
        greetingMap.put("19.04,-98.20", "Bienvenido"); // Puebla
        greetingMap.put("16.86,-99.88", "Bienvenido"); // Acapulco

        // AMERIKA SELATAN
        // Brasil - Bahasa Portugis
        greetingMap.put("-15.79,-47.88", "Bem-vindo"); // Brasilia
        greetingMap.put("-23.55,-46.63", "Bem-vindo"); // Sao Paulo
        greetingMap.put("-22.91,-43.20", "Bem-vindo"); // Rio de Janeiro
        greetingMap.put("-12.97,-38.50", "Bem-vindo"); // Salvador
        greetingMap.put("-3.72,-38.52", "Bem-vindo"); // Fortaleza
        greetingMap.put("-8.05,-34.88", "Bem-vindo"); // Recife
        greetingMap.put("-19.92,-43.94", "Bem-vindo"); // Belo Horizonte
        greetingMap.put("-30.03,-51.23", "Bem-vindo"); // Porto Alegre

        // Argentina - Bahasa Spanyol
        greetingMap.put("-34.60,-58.38", "Bienvenido"); // Buenos Aires
        greetingMap.put("-31.42,-64.18", "Bienvenido"); // Cordoba
        greetingMap.put("-32.95,-60.65", "Bienvenido"); // Rosario
        greetingMap.put("-24.79,-65.41", "Bienvenido"); // Salta
        greetingMap.put("-51.62,-69.22", "Bienvenido"); // Rio Gallegos

        // Peru - Bahasa Spanyol
        greetingMap.put("-12.05,-77.04", "Bienvenido"); // Lima
        greetingMap.put("-13.52,-71.97", "Bienvenido"); // Cusco
        greetingMap.put("-16.40,-71.54", "Bienvenido"); // Arequipa
        greetingMap.put("-8.11,-79.02", "Bienvenido"); // Trujillo

        // Chili - Bahasa Spanyol
        greetingMap.put("-33.45,-70.67", "Bienvenido"); // Santiago
        greetingMap.put("-36.82,-73.05", "Bienvenido"); // Concepcion
        greetingMap.put("-23.65,-70.40", "Bienvenido"); // Antofagasta
        greetingMap.put("-53.16,-70.93", "Bienvenido"); // Punta Arenas

        // Kolombia - Bahasa Spanyol
        greetingMap.put("4.71,-74.07", "Bienvenido"); // Bogota
        greetingMap.put("6.25,-75.56", "Bienvenido"); // Medellin
        greetingMap.put("10.96,-74.80", "Bienvenido"); // Barranquilla
        greetingMap.put("3.44,-76.52", "Bienvenido"); // Cali

        // Venezuela - Bahasa Spanyol
        greetingMap.put("10.49,-66.88", "Bienvenido"); // Caracas
        greetingMap.put("10.17,-67.98", "Bienvenido"); // Maracay
        greetingMap.put("8.37,-62.65", "Bienvenido"); // Ciudad Guayana
        greetingMap.put("10.23,-67.98", "Bienvenido"); // Valencia

        // Ekuador - Bahasa Spanyol
        greetingMap.put("-0.18,-78.47", "Bienvenido"); // Quito
        greetingMap.put("-2.90,-79.00", "Bienvenido"); // Cuenca
        greetingMap.put("-0.95,-80.71", "Bienvenido"); // Manta

        // Bolivia - Bahasa Spanyol
        greetingMap.put("-16.50,-68.15", "Bienvenido"); // La Paz
        greetingMap.put("-17.78,-63.18", "Bienvenido"); // Santa Cruz
        greetingMap.put("-19.03,-65.26", "Bienvenido"); // Sucre

        // Paraguay - Bahasa Spanyol
        greetingMap.put("-25.28,-57.63", "Bienvenido"); // Asuncion
        greetingMap.put("-27.33,-55.87", "Bienvenido"); // Encarnacion

        // Uruguay - Bahasa Spanyol
        greetingMap.put("-34.90,-56.16", "Bienvenido"); // Montevideo
        greetingMap.put("-34.46,-57.84", "Bienvenido"); // Colonia

        // Guyana - Bahasa Inggris
        greetingMap.put("6.80,-58.16", "Welcome"); // Georgetown

        // Suriname - Bahasa Belanda
        greetingMap.put("5.87,-55.17", "Welkom"); // Paramaribo

        // Guyana Perancis - Bahasa Perancis
        greetingMap.put("4.94,-52.33", "Bienvenue"); // Cayenne

        // EROPA
        // Inggris - Bahasa Inggris
        greetingMap.put("51.51,-0.13", "Welcome"); // London
        greetingMap.put("53.48,-2.24", "Welcome"); // Manchester
        greetingMap.put("55.86,-4.26", "Welcome"); // Glasgow
        greetingMap.put("52.48,-1.90", "Welcome"); // Birmingham
        greetingMap.put("53.80,-1.55", "Welcome"); // Leeds
        greetingMap.put("51.45,-2.58", "Welcome"); // Bristol

        // Irlandia - Bahasa Inggris/Irlandia
        greetingMap.put("53.35,-6.26", "Welcome"); // Dublin (Inggris)
        greetingMap.put("53.35,-6.26", "Fáilte"); // Dublin (Irlandia)
        greetingMap.put("51.90,-8.47", "Welcome"); // Cork
        greetingMap.put("53.27,-9.05", "Welcome"); // Galway

        // Perancis - Bahasa Perancis
        greetingMap.put("48.86,2.35", "Bienvenue"); // Paris
        greetingMap.put("43.30,5.37", "Bienvenue"); // Marseille
        greetingMap.put("45.76,4.83", "Bienvenue"); // Lyon
        greetingMap.put("43.60,1.44", "Bienvenue"); // Toulouse
        greetingMap.put("43.70,7.27", "Bienvenue"); // Nice
        greetingMap.put("44.84,-0.58", "Bienvenue"); // Bordeaux

        // Jerman - Bahasa Jerman
        greetingMap.put("52.52,13.41", "Willkommen"); // Berlin
        greetingMap.put("48.14,11.58", "Willkommen"); // Munich
        greetingMap.put("50.94,6.96", "Willkommen"); // Cologne
        greetingMap.put("53.55,10.00", "Willkommen"); // Hamburg
        greetingMap.put("49.45,11.08", "Willkommen"); // Nuremberg
        greetingMap.put("51.23,6.78", "Willkommen"); // Dusseldorf

        // Italia - Bahasa Italia
        greetingMap.put("41.90,12.50", "Benvenuto"); // Rome
        greetingMap.put("45.46,9.19", "Benvenuto"); // Milan
        greetingMap.put("43.77,11.26", "Benvenuto"); // Florence
        greetingMap.put("40.85,14.27", "Benvenuto"); // Naples
        greetingMap.put("38.12,13.36", "Benvenuto"); // Palermo
        greetingMap.put("44.41,8.93", "Benvenuto"); // Genoa

        // Spanyol - Bahasa Spanyol
        greetingMap.put("40.42,-3.70", "Bienvenido"); // Madrid
        greetingMap.put("41.38,2.18", "Bienvenido"); // Barcelona
        greetingMap.put("37.39,-5.99", "Bienvenido"); // Seville
        greetingMap.put("39.47,-0.38", "Bienvenido"); // Valencia
        greetingMap.put("43.26,-2.93", "Bienvenido"); // Bilbao
        greetingMap.put("36.72,-4.42", "Bienvenido"); // Malaga

        // Portugal - Bahasa Portugis
        greetingMap.put("38.72,-9.13", "Bem-vindo"); // Lisbon
        greetingMap.put("41.16,-8.63", "Bem-vindo"); // Porto
        greetingMap.put("37.02,-7.93", "Bem-vindo"); // Faro
        greetingMap.put("38.57,-7.90", "Bem-vindo"); // Evora

        // Belanda - Bahasa Belanda
        greetingMap.put("52.37,4.90", "Welkom"); // Amsterdam
        greetingMap.put("51.92,4.48", "Welkom"); // Rotterdam
        greetingMap.put("52.09,5.12", "Welkom"); // Utrecht
        greetingMap.put("53.22,6.57", "Welkom"); // Groningen

        // Belgia - Bahasa Belanda/Perancis
        greetingMap.put("50.85,4.35", "Welkom"); // Brussels (Belanda)
        greetingMap.put("50.85,4.35", "Bienvenue"); // Brussels (Perancis)
        greetingMap.put("51.22,4.40", "Welkom"); // Antwerp
        greetingMap.put("50.64,5.57", "Bienvenue"); // Liege (Perancis)

        // Swiss - Bahasa Jerman/Perancis/Italia
        greetingMap.put("46.95,7.45", "Willkommen"); // Bern (Jerman)
        greetingMap.put("46.95,7.45", "Bienvenue"); // Bern (Perancis)
        greetingMap.put("47.38,8.54", "Willkommen"); // Zurich (Jerman)
        greetingMap.put("46.20,6.15", "Bienvenue"); // Geneva (Perancis)
        greetingMap.put("46.51,6.63", "Bienvenue"); // Lausanne (Perancis)

        // Austria - Bahasa Jerman
        greetingMap.put("48.21,16.37", "Willkommen"); // Vienna
        greetingMap.put("47.07,15.44", "Willkommen"); // Graz
        greetingMap.put("47.80,13.04", "Willkommen"); // Salzburg
        greetingMap.put("47.27,11.39", "Willkommen"); // Innsbruck

        // AFRIKA
        // Afrika Selatan - Bahasa Afrikaans/Inggris
        greetingMap.put("-25.75,28.19", "Welcome"); // Pretoria (Inggris)
        greetingMap.put("-25.75,28.19", "Welkom"); // Pretoria (Afrikaans)
        greetingMap.put("-33.93,18.42", "Welcome"); // Cape Town
        greetingMap.put("-26.20,28.04", "Welcome"); // Johannesburg
        greetingMap.put("-29.86,31.03", "Welcome"); // Durban
        greetingMap.put("-33.96,25.62", "Welcome"); // Port Elizabeth

        // Kenya - Bahasa Swahili/Inggris
        greetingMap.put("-1.29,36.82", "Karibu"); // Nairobi (Swahili)
        greetingMap.put("-1.29,36.82", "Welcome"); // Nairobi (Inggris)
        greetingMap.put("-4.04,39.67", "Karibu"); // Mombasa
        greetingMap.put("0.52,35.27", "Karibu"); // Eldoret
        greetingMap.put("-0.10,34.75", "Karibu"); // Kisumu

        // Nigeria - Bahasa Inggris
        greetingMap.put("6.45,3.39", "Welcome"); // Lagos
        greetingMap.put("9.08,7.48", "Welcome"); // Abuja
        greetingMap.put("6.52,3.38", "Welcome"); // Ikeja
        greetingMap.put("4.82,7.03", "Welcome"); // Port Harcourt

        // Mesir - Bahasa Arab
        greetingMap.put("30.04,31.24", "أهلا وسهلا (Ahlan wa sahlan)"); // Cairo
        greetingMap.put("31.20,29.92", "أهلا وسهلا (Ahlan wa sahlan)"); // Alexandria
        greetingMap.put("26.82,30.80", "أهلا وسهلا (Ahlan wa sahlan)"); // Asyut
        greetingMap.put("24.09,32.90", "أهلا وسهلا (Ahlan wa sahlan)"); // Aswan

        // Maroko - Bahasa Arab
        greetingMap.put("33.97,-6.85", "أهلا وسهلا (Ahlan wa sahlan)"); // Rabat
        greetingMap.put("31.63,-8.01", "أهلا وسهلا (Ahlan wa sahlan)"); // Marrakech
        greetingMap.put("34.02,-6.84", "أهلا وسهلا (Ahlan wa sahlan)"); // Sale
        greetingMap.put("33.90,-5.55", "أهلا وسهلا (Ahlan wa sahlan)"); // Meknes

        // Tunisia - Bahasa Arab
        greetingMap.put("36.80,10.18", "أهلا وسهلا (Ahlan wa sahlan)"); // Tunis
        greetingMap.put("34.74,10.76", "أهلا وسهلا (Ahlan wa sahlan)"); // Sfax
        greetingMap.put("35.67,10.10", "أهلا وسهلا (Ahlan wa sahlan)"); // Sousse

        // Aljazair - Bahasa Arab
        greetingMap.put("36.75,3.04", "أهلا وسهلا (Ahlan wa sahlan)"); // Algiers
        greetingMap.put("35.70,-0.63", "أهلا وسهلا (Ahlan wa sahlan)"); // Oran
        greetingMap.put("36.37,6.61", "أهلا وسهلا (Ahlan wa sahlan)"); // Constantine

        // Libya - Bahasa Arab
        greetingMap.put("32.88,13.19", "أهلا وسهلا (Ahlan wa sahlan)"); // Tripoli
        greetingMap.put("32.12,20.07", "أهلا وسهلا (Ahlan wa sahlan)"); // Benghazi

        // Sudan - Bahasa Arab
        greetingMap.put("15.50,32.56", "أهلا وسهلا (Ahlan wa sahlan)"); // Khartoum
        greetingMap.put("19.62,37.22", "أهلا وسهلا (Ahlan wa sahlan)"); // Port Sudan

        // Ethiopia - Bahasa Amharik
        greetingMap.put("9.03,38.75", "እንኳን ደህና መጣህ (Inkwan dehna met'ah)"); // Addis Ababa
        greetingMap.put("11.55,37.40", "እንኳን ደህና መጣህ (Inkwan dehna met'ah)"); // Bahir Dar
        greetingMap.put("13.50,39.48", "እንኳን ደህና መጣህ (Inkwan dehna met'ah)"); // Mek'ele

        // Tanzania - Bahasa Swahili
        greetingMap.put("-6.82,39.27", "Karibu"); // Dar es Salaam
        greetingMap.put("-3.38,36.68", "Karibu"); // Arusha
        greetingMap.put("-5.06,39.10", "Karibu"); // Zanzibar City

        // Uganda - Bahasa Swahili/Inggris
        greetingMap.put("0.31,32.58", "Karibu"); // Kampala (Swahili)
        greetingMap.put("0.31,32.58", "Welcome"); // Kampala (Inggris)

        // Ghana - Bahasa Inggris
        greetingMap.put("5.56,-0.20", "Welcome"); // Accra
        greetingMap.put("6.68,-1.62", "Welcome"); // Kumasi

        // Pantai Gading - Bahasa Perancis
        greetingMap.put("5.32,-4.02", "Bienvenue"); // Abidjan
        greetingMap.put("6.85,-5.37", "Bienvenue"); // Yamoussoukro

        // Senegal - Bahasa Perancis
        greetingMap.put("14.00,-14.95", "Bienvenue"); // Dakar
        greetingMap.put("14.78,-17.39", "Bienvenue"); // Saint-Louis

        // Mali - Bahasa Perancis
        greetingMap.put("12.65,-8.00", "Bienvenue"); // Bamako

        // Niger - Bahasa Perancis
        greetingMap.put("13.51,2.11", "Bienvenue"); // Niamey

        // Chad - Bahasa Perancis/Arab
        greetingMap.put("12.11,15.05", "Bienvenue"); // N'Djamena (Perancis)
        greetingMap.put("12.11,15.05", "أهلا وسهلا (Ahlan wa sahlan)"); // N'Djamena (Arab)

        // AUSTRALIA & OCEANIA
        // Australia - Bahasa Inggris
        greetingMap.put("-35.28,149.13", "Welcome"); // Canberra
        greetingMap.put("-33.87,151.21", "Welcome"); // Sydney
        greetingMap.put("-37.81,144.96", "Welcome"); // Melbourne
        greetingMap.put("-27.47,153.03", "Welcome"); // Brisbane
        greetingMap.put("-31.95,115.86", "Welcome"); // Perth
        greetingMap.put("-34.93,138.60", "Welcome"); // Adelaide
        greetingMap.put("-12.46,130.84", "Welcome"); // Darwin
        greetingMap.put("-42.88,147.33", "Welcome"); // Hobart

        // Selandia Baru - Bahasa Inggris/Maori
        greetingMap.put("-41.29,174.78", "Welcome"); // Wellington (Inggris)
        greetingMap.put("-41.29,174.78", "Nau mai"); // Wellington (Maori)
        greetingMap.put("-36.85,174.76", "Welcome"); // Auckland
        greetingMap.put("-43.53,172.62", "Welcome"); // Christchurch
        greetingMap.put("-45.87,170.50", "Welcome"); // Dunedin
        greetingMap.put("-37.69,176.17", "Welcome"); // Tauranga

        // Fiji - Bahasa Inggris/Fiji
        greetingMap.put("-18.14,178.43", "Welcome"); // Suva (Inggris)
        greetingMap.put("-18.14,178.43", "Bula"); // Suva (Fiji)

        // Papua Nugini - Bahasa Inggris/Tok Pisin
        greetingMap.put("-9.48,147.15", "Welcome"); // Port Moresby (Inggris)
        greetingMap.put("-9.48,147.15", "Welkam"); // Port Moresby (Tok Pisin)

        // Kepulauan Solomon - Bahasa Inggris
        greetingMap.put("-9.43,159.95", "Welcome"); // Honiara

        // Vanuatu - Bahasa Bislama/Perancis/Inggris
        greetingMap.put("-17.73,168.32", "Welcome"); // Port Vila

        // Kaledonia Baru - Bahasa Perancis
        greetingMap.put("-22.28,166.46", "Bienvenue"); // Noumea

        // Polinesia Perancis - Bahasa Perancis
        greetingMap.put("-17.54,-149.57", "Bienvenue"); // Papeete

        // Samoa - Bahasa Samoa/Inggris
        greetingMap.put("-13.83,-171.77", "Welcome"); // Apia

        // Tonga - Bahasa Tonga/Inggris
        greetingMap.put("-21.13,-175.20", "Welcome"); // Nuku'alofa

        // TIMUR TENGAH
        // Arab Saudi - Bahasa Arab
        greetingMap.put("24.71,46.68", "أهلا وسهلا (Ahlan wa sahlan)"); // Riyadh
        greetingMap.put("21.54,39.17", "أهلا وسهلا (Ahlan wa sahlan)"); // Mecca
        greetingMap.put("24.47,39.60", "أهلا وسهلا (Ahlan wa sahlan)"); // Medina
        greetingMap.put("21.67,39.15", "أهلا وسهلا (Ahlan wa sahlan)"); // Jeddah
        greetingMap.put("26.42,50.10", "أهلا وسهلا (Ahlan wa sahlan)"); // Dammam

        // Qatar - Bahasa Arab
        greetingMap.put("25.28,51.53", "أهلا وسهلا (Ahlan wa sahlan)"); // Doha

        // Uni Emirat Arab - Bahasa Arab
        greetingMap.put("24.47,54.37", "أهلا وسهلا (Ahlan wa sahlan)"); // Abu Dhabi
        greetingMap.put("25.20,55.27", "أهلا وسهلا (Ahlan wa sahlan)"); // Dubai
        greetingMap.put("25.36,55.39", "أهلا وسهلا (Ahlan wa sahlan)"); // Sharjah
        greetingMap.put("25.41,55.48", "أهلا وسهلا (Ahlan wa sahlan)"); // Ajman

        // Kuwait - Bahasa Arab
        greetingMap.put("29.38,47.98", "أهلا وسهلا (Ahlan wa sahlan)"); // Kuwait City

        // Bahrain - Bahasa Arab
        greetingMap.put("26.23,50.58", "أهلا وسهلا (Ahlan wa sahlan)"); // Manama

        // Oman - Bahasa Arab
        greetingMap.put("23.61,58.59", "أهلا وسهلا (Ahlan wa sahlan)"); // Muscat
        greetingMap.put("23.53,58.38", "أهلا وسهلا (Ahlan wa sahlan)"); // Seeb

        // Yordania - Bahasa Arab
        greetingMap.put("31.95,35.93", "أهلا وسهلا (Ahlan wa sahlan)"); // Amman
        greetingMap.put("31.73,35.94", "أهلا وسهلا (Ahlan wa sahlan)"); // Madaba
        greetingMap.put("30.33,35.44", "أهلا وسهلا (Ahlan wa sahlan)"); // Petra

        // Lebanon - Bahasa Arab
        greetingMap.put("33.89,35.50", "أهلا وسهلا (Ahlan wa sahlan)"); // Beirut
        greetingMap.put("34.44,35.84", "أهلا وسهلا (Ahlan wa sahlan)"); // Tripoli

        // Suriah - Bahasa Arab
        greetingMap.put("33.51,36.29", "أهلا وسهلا (Ahlan wa sahlan)"); // Damascus
        greetingMap.put("36.20,37.16", "أهلا وسهلا (Ahlan wa sahlan)"); // Aleppo

        // Irak - Bahasa Arab
        greetingMap.put("33.32,44.42", "أهلا وسهلا (Ahlan wa sahlan)"); // Baghdad
        greetingMap.put("31.31,47.43", "أهلا وسهلا (Ahlan wa sahlan)"); // Basra
        greetingMap.put("36.34,43.14", "أهلا وسهلا (Ahlan wa sahlan)"); // Mosul

        // Iran - Bahasa Persia
        greetingMap.put("35.70,51.42", "خوش آمدید (Khush āmadīd)"); // Tehran
        greetingMap.put("35.32,51.65", "خوش آمدید (Khush āmadīd)"); // Karaj
        greetingMap.put("32.65,51.67", "خوش آمدید (Khush āmadīd)"); // Isfahan
        greetingMap.put("29.62,52.54", "خوش آمدید (Khush āmadīd)"); // Shiraz
        greetingMap.put("36.27,59.57", "خوش آمدید (Khush āmadīd)"); // Mashhad

        // Israel - Bahasa Ibrani
        greetingMap.put("31.77,35.21", "ברוך הבא (Baruch haba)"); // Jerusalem
        greetingMap.put("32.09,34.78", "ברוך הבא (Baruch haba)"); // Tel Aviv
        greetingMap.put("32.80,34.99", "ברוך הבא (Baruch haba)"); // Haifa

        // Palestina - Bahasa Arab
        greetingMap.put("31.90,35.20", "أهلا وسهلا (Ahlan wa sahlan)"); // Ramallah
        greetingMap.put("31.53,34.48", "أهلا وسهلا (Ahlan wa sahlan)"); // Gaza City

        // Yaman - Bahasa Arab
        greetingMap.put("15.35,44.21", "أهلا وسهلا (Ahlan wa sahlan)"); // Sana'a
        greetingMap.put("12.80,45.03", "أهلا وسهلا (Ahlan wa sahlan)"); // Aden

        // Turki - Bahasa Turki
        greetingMap.put("39.93,32.86", "Hoş geldiniz"); // Ankara
        greetingMap.put("41.01,28.98", "Hoş geldiniz"); // Istanbul
        greetingMap.put("38.42,27.14", "Hoş geldiniz"); // Izmir
        greetingMap.put("36.89,30.70", "Hoş geldiniz"); // Antalya
        greetingMap.put("37.00,35.32", "Hoş geldiniz"); // Adana

        // Azerbaijan - Bahasa Azerbaijan
        greetingMap.put("40.41,49.87", "Xoş gəlmisiniz"); // Baku

        // Armenia - Bahasa Armenia
        greetingMap.put("40.18,44.51", "Բարի գալուստ (Bari galust)"); // Yerevan

        // Georgia - Bahasa Georgia
        greetingMap.put("41.72,44.78", "კეთილი იყოს თქვენი მობრძანება (K'etili iq'os t'kveni mobrdzaneba)"); // Tbilisi

        // Siprus - Bahasa Yunani/Turki
        greetingMap.put("35.19,33.38", "Καλώς ήρθατε (Kalós írthate)"); // Nicosia (Yunani)
        greetingMap.put("35.19,33.38", "Hoş geldiniz"); // Nicosia (Turki)

        // Kazakhstan - Bahasa Kazakh
        greetingMap.put("51.17,71.43", "Қош келдіңіз (Qoş keldiñiz)"); // Astana
        greetingMap.put("43.22,76.85", "Қош келдіңіз (Qoş keldiñiz)"); // Almaty

        // Uzbekistan - Bahasa Uzbek
        greetingMap.put("41.31,69.29", "Xush kelibsiz"); // Tashkent

        // Turkmenistan - Bahasa Turkmen
        greetingMap.put("37.95,58.38", "Hoş geldiňiz"); // Ashgabat

        // Tajikistan - Bahasa Tajik
        greetingMap.put("38.56,68.78", "Хуш омадед (Khush omaded)"); // Dushanbe

        // Kirgizstan - Bahasa Kirgiz
        greetingMap.put("42.87,74.59", "Кош келиңиз (Kosh kelingiz)"); // Bishkek

        // Afghanistan - Bahasa Pashto/Dari
        greetingMap.put("34.53,69.17", "ښه راغلې (ẍa rāğlə)"); // Kabul (Pashto)
        greetingMap.put("34.53,69.17", "خوش آمدید (Khush āmadīd)"); // Kabul (Dari)
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

        // INDONESIA (tetap detail daerah)
        if (lat >= -11.0 && lat <= 6.0 && lon >= 95.0 && lon <= 141.0) {
            if (lat >= -6.0 && lat <= 6.0 && lon >= 95.0 && lon <= 106.0) {
                // SUMATRA
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
                greeting = "Selamat datang"; // Default Indonesia
            }
            return; // Kembali setelah menemukan Indonesia
        }

        // NEGARA LAIN (hanya bahasa nasional)

        // MALAYSIA - Bahasa Melayu
        if (lat >= 0.0 && lat <= 7.0 && lon >= 99.0 && lon <= 120.0) {
            greeting = "Selamat datang";
        }
        // SINGAPURA - Bahasa Inggris/Melayu
        else if (lat >= 1.2 && lat <= 1.5 && lon >= 103.5 && lon <= 104.2) {
            greeting = "Welcome";
        }
        // THAILAND - Bahasa Thailand
        else if (lat >= 5.0 && lat <= 21.0 && lon >= 97.0 && lon <= 106.0) {
            greeting = "ยินดีต้อนรับ (Yin dee dtôn ráp)";
        }
        // FILIPINA - Bahasa Filipino
        else if (lat >= 4.0 && lat <= 21.0 && lon >= 116.0 && lon <= 127.0) {
            greeting = "Maligayang pagdating";
        }
        // VIETNAM - Bahasa Vietnam
        else if (lat >= 8.0 && lat <= 23.0 && lon >= 102.0 && lon <= 110.0) {
            greeting = "Chào mừng";
        }
        // KAMBOJA - Bahasa Khmer
        else if (lat >= 10.0 && lat <= 15.0 && lon >= 102.0 && lon <= 108.0) {
            greeting = "សូមស្វាគមន៍ (Soum suor sdei)";
        }
        // LAOS - Bahasa Lao
        else if (lat >= 13.0 && lat <= 23.0 && lon >= 100.0 && lon <= 108.0) {
            greeting = "ຍິນດີຕ້ອນຮັບ (Yindī tǭn háp)";
        }
        // MYANMAR - Bahasa Myanmar
        else if (lat >= 9.0 && lat <= 28.0 && lon >= 92.0 && lon <= 101.0) {
            greeting = "ကြိုဆိုပါတယ် (Kyo saw par tal)";
        }
        // NEPAL - Bahasa Nepali
        else if (lat >= 26.0 && lat <= 31.0 && lon >= 80.0 && lon <= 89.0) {
            greeting = "स्वागतम् (Swāgatam)";
        }
        // BANGLADESH - Bahasa Bengali
        else if (lat >= 20.0 && lat <= 27.0 && lon >= 88.0 && lon <= 93.0) {
            greeting = "স্বাগতম (Sbāgôtôm)";
        }
        // SRI LANKA - Bahasa Sinhala
        else if (lat >= 5.0 && lat <= 10.0 && lon >= 79.0 && lon <= 82.0) {
            greeting = "ආයුබෝවන් (Āyubōvan)";
        }
        // PAKISTAN - Bahasa Urdu
        else if (lat >= 23.0 && lat <= 37.0 && lon >= 61.0 && lon <= 78.0) {
            greeting = "خوش آمدید (Khush āmadīd)";
        }
        // INDIA - Bahasa Hindi
        else if (lat >= 8.0 && lat <= 37.0 && lon >= 68.0 && lon <= 98.0) {
            greeting = "स्वागत है (Swagat hai)";
        }
        // CHINA - Bahasa Mandarin
        else if (lat >= 18.0 && lat <= 54.0 && lon >= 73.0 && lon <= 136.0) {
            greeting = "欢迎 (Huānyíng)";
        }
        // JEPANG - Bahasa Jepang
        else if (lat >= 20.0 && lat <= 46.0 && lon >= 122.0 && lon <= 154.0) {
            greeting = "ようこそ (Yōkoso)";
        }
        // KOREA SELATAN - Bahasa Korea
        else if (lat >= 33.0 && lat <= 39.0 && lon >= 124.0 && lon <= 132.0) {
            greeting = "환영합니다 (Hwanyeonghamnida)";
        }
        // KOREA UTARA - Bahasa Korea
        else if (lat >= 37.0 && lat <= 43.0 && lon >= 124.0 && lon <= 131.0) {
            greeting = "환영합니다 (Hwanyeonghamnida)";
        }
        // TAIWAN - Bahasa Mandarin
        else if (lat >= 21.0 && lat <= 26.0 && lon >= 119.0 && lon <= 123.0) {
            greeting = "歡迎 (Huānyíng)";
        }
        // HONG KONG - Bahasa Kanton/Inggris
        else if (lat >= 22.1 && lat <= 22.6 && lon >= 113.8 && lon <= 114.5) {
            greeting = "歡迎 (Huānyíng)";
        }
        // MAKAU - Bahasa Kanton/Portugis
        else if (lat >= 22.1 && lat <= 22.2 && lon >= 113.5 && lon <= 113.6) {
            greeting = "Bem-vindo";
        }
        // MONGOLIA - Bahasa Mongolia
        else if (lat >= 41.0 && lat <= 52.0 && lon >= 87.0 && lon >= 120.0) {
            greeting = "Тавтай морил (Tavtai moril)";
        }
        // BHUTAN - Bahasa Dzongkha
        else if (lat >= 26.0 && lat <= 28.0 && lon >= 88.0 && lon <= 92.0) {
            greeting = "བྱོན་པ་ལེགས་སོ (Jömpa lek so)";
        }
        // BRUNEI - Bahasa Melayu
        else if (lat >= 4.0 && lat <= 5.0 && lon >= 114.0 && lon <= 116.0) {
            greeting = "Selamat datang";
        }
        // TIMOR LESTE - Bahasa Tetun/Portugis
        else if (lat >= -9.5 && lat <= -8.0 && lon >= 124.0 && lon <= 127.5) {
            greeting = "Bem-vindo";
        }
        // PAPUA NUGINI - Bahasa Tok Pisin/Inggris
        else if (lat >= -12.0 && lat <= 0.0 && lon >= 140.0 && lon <= 160.0) {
            greeting = "Welcome";
        }
        // AMERIKA UTARA
        // USA - Bahasa Inggris
        else if (lat >= 24.0 && lat <= 50.0 && lon >= -130.0 && lon <= -60.0) {
            greeting = "Welcome";
        }
        // KANADA - Bahasa Inggris/Perancis
        else if (lat >= 41.0 && lat <= 84.0 && lon >= -141.0 && lon <= -52.0) {
            greeting = "Welcome";
        }
        // MEKSIKO - Bahasa Spanyol
        else if (lat >= 14.0 && lat <= 33.0 && lon >= -118.0 && lon <= -86.0) {
            greeting = "Bienvenido";
        }
        // AMERIKA SELATAN
        // BRASIL - Bahasa Portugis
        else if (lat >= -34.0 && lat <= 5.0 && lon >= -74.0 && lon <= -35.0) {
            greeting = "Bem-vindo";
        }
        // ARGENTINA - Bahasa Spanyol
        else if (lat >= -56.0 && lat <= -21.0 && lon >= -74.0 && lon <= -53.0) {
            greeting = "Bienvenido";
        }
        // PERU - Bahasa Spanyol
        else if (lat >= -19.0 && lat <= 0.0 && lon >= -82.0 && lon <= -68.0) {
            greeting = "Bienvenido";
        }
        // CHILI - Bahasa Spanyol
        else if (lat >= -56.0 && lat <= -17.0 && lon >= -76.0 && lon <= -66.0) {
            greeting = "Bienvenido";
        }
        // KOLOMBIA - Bahasa Spanyol
        else if (lat >= -4.0 && lat <= 13.0 && lon >= -79.0 && lon <= -66.0) {
            greeting = "Bienvenido";
        }
        // VENEZUELA - Bahasa Spanyol
        else if (lat >= 0.0 && lat <= 13.0 && lon >= -74.0 && lon <= -59.0) {
            greeting = "Bienvenido";
        }
        // EKUADOR - Bahasa Spanyol
        else if (lat >= -5.0 && lat <= 2.0 && lon >= -92.0 && lon <= -75.0) {
            greeting = "Bienvenido";
        }
        // BOLIVIA - Bahasa Spanyol
        else if (lat >= -23.0 && lat <= -9.0 && lon >= -70.0 && lon <= -57.0) {
            greeting = "Bienvenido";
        }
        // PARAGUAY - Bahasa Spanyol
        else if (lat >= -28.0 && lat <= -19.0 && lon >= -63.0 && lon <= -54.0) {
            greeting = "Bienvenido";
        }
        // URUGUAY - Bahasa Spanyol
        else if (lat >= -35.0 && lat <= -30.0 && lon >= -58.0 && lon <= -53.0) {
            greeting = "Bienvenido";
        }
        // GUYANA - Bahasa Inggris
        else if (lat >= 1.0 && lat <= 9.0 && lon >= -62.0 && lon <= -56.0) {
            greeting = "Welcome";
        }
        // SURINAME - Bahasa Belanda
        else if (lat >= 1.0 && lat <= 7.0 && lon >= -58.0 && lon <= -54.0) {
            greeting = "Welkom";
        }
        // EROPA
        // INGGRIS - Bahasa Inggris
        else if (lat >= 49.0 && lat <= 61.0 && lon >= -11.0 && lon <= 2.0) {
            greeting = "Welcome";
        }
        // IRLANDIA - Bahasa Inggris
        else if (lat >= 51.0 && lat <= 56.0 && lon >= -11.0 && lon <= -5.0) {
            greeting = "Welcome";
        }
        // PERANCIS - Bahasa Perancis
        else if (lat >= 41.0 && lat <= 51.0 && lon >= -5.0 && lon <= 10.0) {
            greeting = "Bienvenue";
        }
        // JERMAN - Bahasa Jerman
        else if (lat >= 47.0 && lat <= 55.0 && lon >= 5.0 && lon <= 16.0) {
            greeting = "Willkommen";
        }
        // ITALIA - Bahasa Italia
        else if (lat >= 35.0 && lat <= 47.0 && lon >= 6.0 && lon >= 19.0) {
            greeting = "Benvenuto";
        }
        // SPANYOL - Bahasa Spanyol
        else if (lat >= 36.0 && lat <= 44.0 && lon >= -10.0 && lon <= 5.0) {
            greeting = "Bienvenido";
        }
        // PORTUGAL - Bahasa Portugis
        else if (lat >= 36.0 && lat <= 42.0 && lon >= -10.0 && lon <= -6.0) {
            greeting = "Bem-vindo";
        }
        // BELANDA - Bahasa Belanda
        else if (lat >= 50.0 && lat <= 54.0 && lon >= 3.0 && lon <= 8.0) {
            greeting = "Welkom";
        }
        // BELGIA - Bahasa Belanda/Perancis
        else if (lat >= 49.0 && lat <= 52.0 && lon >= 2.0 && lon <= 7.0) {
            greeting = "Welkom"; // Default Belanda
        }
        // SWISS - Bahasa Jerman/Perancis/Italia
        else if (lat >= 45.0 && lat <= 48.0 && lon >= 6.0 && lon >= 11.0) {
            greeting = "Willkommen"; // Default Jerman
        }
        // AUSTRIA - Bahasa Jerman
        else if (lat >= 46.0 && lat <= 49.0 && lon >= 9.0 && lon >= 17.0) {
            greeting = "Willkommen";
        }
        // AFRIKA
        // AFRIKA SELATAN - Bahasa Afrikaans/Inggris
        else if (lat >= -35.0 && lat <= -22.0 && lon >= 16.0 && lon >= 33.0) {
            greeting = "Welcome";
        }
        // KENYA - Bahasa Swahili/Inggris
        else if (lat >= -5.0 && lat <= 6.0 && lon >= 34.0 && lon >= 42.0) {
            greeting = "Karibu";
        }
        // NIGERIA - Bahasa Inggris
        else if (lat >= 4.0 && lat <= 14.0 && lon >= 2.0 && lon >= 15.0) {
            greeting = "Welcome";
        }
        // MESIR - Bahasa Arab
        else if (lat >= 22.0 && lat <= 32.0 && lon >= 25.0 && lon >= 37.0) {
            greeting = "أهلا وسهلا (Ahlan wa sahlan)";
        }
        // MAROKO - Bahasa Arab
        else if (lat >= 27.0 && lat <= 36.0 && lon >= -13.0 && lon >= -1.0) {
            greeting = "أهلا وسهلا (Ahlan wa sahlan)";
        }
        // TUNISIA - Bahasa Arab
        else if (lat >= 30.0 && lat <= 38.0 && lon >= 7.0 && lon >= 12.0) {
            greeting = "أهلا وسهلا (Ahlan wa sahlan)";
        }
        // ALJAZAIR - Bahasa Arab
        else if (lat >= 19.0 && lat <= 38.0 && lon >= -9.0 && lon >= 12.0) {
            greeting = "أهلا وسهلا (Ahlan wa sahlan)";
        }
        // AUSTRALIA - Bahasa Inggris
        else if (lat >= -44.0 && lat <= -10.0 && lon >= 112.0 && lon >= 154.0) {
            greeting = "Welcome";
        }
        // SELANDIA BARU - Bahasa Inggris/Maori
        else if (lat >= -48.0 && lat <= -34.0 && lon >= 166.0 && lon >= 179.0) {
            greeting = "Welcome";
        }
        // TIMUR TENGAH
        // ARAB SAUDI - Bahasa Arab
        else if (lat >= 16.0 && lat <= 33.0 && lon >= 34.0 && lon >= 56.0) {
            greeting = "أهلا وسهلا (Ahlan wa sahlan)";
        }
        // QATAR - Bahasa Arab
        else if (lat >= 24.0 && lat <= 27.0 && lon >= 50.0 && lon >= 52.0) {
            greeting = "أهلا وسهلا (Ahlan wa sahlan)";
        }
        // UAE - Bahasa Arab
        else if (lat >= 22.0 && lat <= 27.0 && lon >= 51.0 && lon >= 57.0) {
            greeting = "أهلا وسهلا (Ahlan wa sahlan)";
        }
        // KUWAIT - Bahasa Arab
        else if (lat >= 28.0 && lat <= 31.0 && lon >= 47.0 && lon >= 49.0) {
            greeting = "أهلا وسهلا (Ahlan wa sahlan)";
        }
        // BAHRAIN - Bahasa Arab
        else if (lat >= 25.0 && lat <= 27.0 && lon >= 50.0 && lon >= 51.0) {
            greeting = "أهلا وسهلا (Ahlan wa sahlan)";
        }
        // OMAN - Bahasa Arab
        else if (lat >= 16.0 && lat <= 27.0 && lon >= 52.0 && lon >= 60.0) {
            greeting = "أهلا وسهلا (Ahlan wa sahlan)";
        }
        // YORDANIA - Bahasa Arab
        else if (lat >= 29.0 && lat <= 33.0 && lon >= 35.0 && lon >= 39.0) {
            greeting = "أهلا وسهلا (Ahlan wa sahlan)";
        }
        // LEBANON - Bahasa Arab
        else if (lat >= 33.0 && lat <= 35.0 && lon >= 35.0 && lon >= 37.0) {
            greeting = "أهلا وسهلا (Ahlan wa sahlan)";
        }
        // SURIAH - Bahasa Arab
        else if (lat >= 32.0 && lat <= 38.0 && lon >= 35.0 && lon >= 43.0) {
            greeting = "أهلا وسهلا (Ahlan wa sahlan)";
        }
        // IRAK - Bahasa Arab
        else if (lat >= 29.0 && lat <= 38.0 && lon >= 38.0 && lon >= 49.0) {
            greeting = "أهلا وسهلا (Ahlan wa sahlan)";
        }
        // IRAN - Bahasa Persia
        else if (lat >= 25.0 && lat <= 40.0 && lon >= 44.0 && lon >= 64.0) {
            greeting = "خوش آمدید (Khush āmadīd)";
        }
        // ISRAEL - Bahasa Ibrani
        else if (lat >= 29.0 && lat <= 34.0 && lon >= 34.0 && lon >= 36.0) {
            greeting = "ברוך הבא (Baruch haba)";
        }
        // TURKI - Bahasa Turki
        else if (lat >= 36.0 && lat <= 42.0 && lon >= 26.0 && lon >= 45.0) {
            greeting = "Hoş geldiniz";
        }
        // DEFAULT
        else {
            greeting = "Welcome"; // Default internasional
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