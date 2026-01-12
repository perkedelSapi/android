# UAS

## Nama: Albhani Fadillah Haryady
## NIM: 312410130
## Kelas: TI 24 A1

#  Aplikasi Pajakin

# PajakIn

Sebuah aplikasi Android yang menghadirkan pengalaman perhitungan pajak yang personal dan mendunia. Tidak hanya mengenal budaya Indonesia dari Sabang sampai Merauke, PajakIn kini juga memahami keragaman sapaan dari seluruh penjuru dunia. Dari "Selamat datang" di Jakarta hingga "Hello" di New York, dari "Bonjour" di Paris hingga "ようこそ" di Tokyo - setiap pembukaan aplikasi adalah percakapan pertama yang menghargai keberadaan Anda di mana pun Anda berada.

## Fitur
- **Splash Screen dengan Deteksi Lokasi**: Aplikasi menampilkan splash screen yang akan menampilkan sapaan berbeda berdasarkan lokasi pengguna (berdasarkan koordinat GPS).
- **Kalkulator Pajak**: Fitur utama aplikasi yang memungkinkan pengguna menghitung pajak berdasarkan inputan tertentu.

## Cakupan Bahasa
### 🇮🇩 Indonesia (Detail Bahasa Daerah)
- Sumatra: Aceh, Batak (Horas), Minang (Salam Datuak), Melayu, Palembang (Sugê rawuh)

- Jawa: Sunda (Wilujeng sumping), Jawa (Sugeng rawuh), Madura (Salamet tangghâ')

- Bali & Nusa Tenggara: Bali (Rahajeng rauh), Sasak (Sampun rauh), NTT (Mau mai)

- Kalimantan: Dayak (Lompok andau), Banjar, Melayu Kalimantan

- Sulawesi: Manado (Salama' datang), Bugis/Makassar, Gorontalo (U'otalo)

- Maluku: Beta ale sio, Jaga-jaga ale

- Papua: Selamat datang

### Negara Lain (Bahasa Nasional)
- Asia: Malaysia (Selamat datang), Thailand (ยินดีต้อนรับ), Jepang (ようこそ), Korea (환영합니다), dll.

- Eropa: Inggris (Welcome), Perancis (Bienvenue), Jerman (Willkommen), Italia (Benvenuto), dll.

- Amerika: USA/Canada (Welcome), Meksiko (Bienvenido), Brasil (Bem-vindo), dll.

- Afrika: Swahili (Karibu), Arab (أهلا وسهلا), Inggris, Perancis

- Timur Tengah: Arab (أهلا وسهلا), Persia (خوش آمدید), Turki (Hoş geldiniz)
## Struktur Direktori

```plaintext
PajakIn/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/pajak/in/
│   │   │   │   ├── SplashActivity.java    # Splash screen dengan deteksi lokasi
│   │   │   │   └── MainActivity.java      # Kalkulator pajak utama
│   │   │   ├── res/
│   │   │   │   ├── layout/
│   │   │   │   │   ├── activity_splash.xml  # Layout untuk splash screen
│   │   │   │   │   └── activity_main.xml    # Layout untuk kalkulator pajak
│   │   │   │   ├── drawable/                # Folder untuk gambar dan ikon
│   │   │   │   └── values/                  # Folder untuk values dan styles
│   │   │   └── AndroidManifest.xml          # File manifest aplikasi
│   │   └── test/
│   └── build.gradle                          # File konfigurasi gradle untuk aplikasi
└── README.md                                 # File ini
````
```
Bahasa Daerah Khas
Daerah	Greeting	Contoh Kota
Aceh	Sëlamat datang	Banda Aceh, Lhokseumawe
Batak	Horas	Medan, Pematangsiantar
Minang	Salam Datuak	Padang, Bukittinggi
Sunda	Wilujeng sumping	Bandung, Tasikmalaya
Jawa	Sugeng rawuh	Yogyakarta, Semarang
Madura	Salamet tangghâ'	Pamekasan, Sumenep
Bali	Rahajeng rauh	Denpasar, Tabanan
Sasak	Sampun rauh	Mataram, Praya
Dayak	Lompok andau	Palangkaraya, Kasongan
Bugis	Salama' datang	Makassar, Parepare
```

## Cara Penggunaan
1. Splash Screen

- Buka aplikasi

- Izinkan permission lokasi (opsional)

- Akan muncul greeting sesuai lokasi Anda

- Otomatis pindah ke kalkulator setelah 2.5 detik

2. Kalkulator Pajak

- Masukkan harga di kolom input

- Format otomatis: 1000000 → 1.000.000

- Atur persentase pajak dengan slider

- Range: 0% sampai 50%

- Lihat hasil secara real-time:

- Jumlah pajak

- Total harga + pajak

## Permission
- Aplikasi membutuhkan permission lokasi untuk:

- Mendeteksi koordinat pengguna

- Menentukan greeting bahasa daerah yang sesuai

- Catatan: Jika permission ditolak, akan menggunakan "Selamat datang"