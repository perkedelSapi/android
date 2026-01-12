package com.pajak.in;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private EditText editTextHarga;
    private SeekBar seekBarPajak;
    private TextView textPersentase, textJumlahPajak, textTotal, textTitle;

    // Untuk formatting
    private DecimalFormat formatter;
    private String currentText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi formatter
        formatter = new DecimalFormat("#,###");
        formatter.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.US));

        // Hubungkan dengan layout
        editTextHarga = findViewById(R.id.editTextHarga);
        seekBarPajak = findViewById(R.id.seekBarPajak);
        textPersentase = findViewById(R.id.textPersentase);
        textJumlahPajak = findViewById(R.id.textJumlahPajak);
        textTotal = findViewById(R.id.textTotal);
        textTitle = findViewById(R.id.textTitle);

        // ===== SET GRADIEN TEXT =====
        applyTextGradient();

        // ===== SET DEFAULT =====
        textPersentase.setText("0%");
        seekBarPajak.setProgress(0);
        editTextHarga.setHint("0");
        editTextHarga.setText("");
        updateDisplay();

        // ===== TEXT WATCHER YANG SIMPLE =====
        editTextHarga.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                // Hindari infinite loop
                if (currentText.equals(s.toString())) {
                    return;
                }

                // Hapus semua titik untuk mendapatkan angka murni
                String cleanString = s.toString().replaceAll("[.,]", "");

                // Jika kosong, set ke hint "0"
                if (cleanString.isEmpty()) {
                    currentText = "";
                    editTextHarga.removeTextChangedListener(this);
                    editTextHarga.setText("");
                    editTextHarga.setHint("0");
                    editTextHarga.addTextChangedListener(this);
                    updateDisplay();
                    return;
                }

                // Jika hanya "0", biarkan saja
                if (cleanString.equals("0")) {
                    currentText = "0";
                    editTextHarga.removeTextChangedListener(this);
                    editTextHarga.setText("0");
                    editTextHarga.setSelection(1);
                    editTextHarga.addTextChangedListener(this);
                    updateDisplay();
                    return;
                }

                // Hapus leading zeros
                cleanString = cleanString.replaceFirst("^0+(?!$)", "");

                try {
                    // Parse ke long
                    long parsedValue = Long.parseLong(cleanString);

                    // Format dengan separator ribuan
                    String formatted = formatter.format(parsedValue);

                    // Update jika berbeda
                    if (!s.toString().equals(formatted)) {
                        currentText = formatted;
                        editTextHarga.removeTextChangedListener(this);
                        editTextHarga.setText(formatted);
                        editTextHarga.setSelection(formatted.length());
                        editTextHarga.addTextChangedListener(this);
                    } else {
                        currentText = s.toString();
                    }

                } catch (NumberFormatException e) {
                    // Jika angka terlalu besar, reset
                    currentText = "";
                    editTextHarga.removeTextChangedListener(this);
                    editTextHarga.setText("");
                    editTextHarga.setHint("0");
                    editTextHarga.addTextChangedListener(this);
                }

                updateDisplay();
            }
        });

        // ===== LISTENER UNTUK SEEKBAR =====
        seekBarPajak.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textPersentase.setText(progress + "%");
                updateDisplay();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        // Fokus ke EditText
        editTextHarga.requestFocus();
    }

    // ===== METHOD UNTUK GRADIEN TEXT =====
    private void applyTextGradient() {
        textTitle.post(new Runnable() {
            @Override
            public void run() {
                Shader textShader = new LinearGradient(
                        0, 0, textTitle.getWidth(), textTitle.getHeight(),
                        new int[]{0xFF1E90FF, 0xFF9B59B6, 0xFFE74C3C},
                        null, Shader.TileMode.CLAMP
                );
                textTitle.getPaint().setShader(textShader);
                textTitle.invalidate();
            }
        });
    }

    // ===== METHOD UNTUK KONVERSI STRING KE ANGKA =====
    private long parseInputToLong(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }

        try {
            // Hapus semua titik
            String clean = input.replaceAll("[.,]", "");
            if (clean.isEmpty()) {
                return 0;
            }
            return Long.parseLong(clean);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // ===== METHOD UNTUK UPDATE DISPLAY =====
    private void updateDisplay() {
        try {
            // Ambil dan parse input
            String inputStr = editTextHarga.getText().toString();
            long harga = parseInputToLong(inputStr);

            // Jika kosong dan hint "0", anggap 0
            if (inputStr.isEmpty() && editTextHarga.getHint().toString().equals("0")) {
                harga = 0;
            }

            // Ambil persentase
            int persentase = seekBarPajak.getProgress();

            // Hitung pajak
            long pajak = (long) (harga * (persentase / 100.0));

            // Hitung total
            long total = harga + pajak;

            // Format output
            String pajakFormatted = pajak == 0 ? "0" : formatter.format(pajak);
            String totalFormatted = total == 0 ? "0" : formatter.format(total);

            // Update UI
            textJumlahPajak.setText(pajakFormatted);
            textTotal.setText(totalFormatted);

        } catch (Exception e) {
            textJumlahPajak.setText("0");
            textTotal.setText("0");
        }
    }
}