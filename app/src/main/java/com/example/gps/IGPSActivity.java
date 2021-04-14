package com.example.gps;

public interface IGPSActivity {
    int REQUEST_CODE = 400;
    void moveCamera(); // Recentrer autour de la carte et zoom autrour de la position GPS
}
