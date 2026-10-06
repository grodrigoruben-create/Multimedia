package com.example.multimedia;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Variables para el manejo de Audio
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Vincular componentes del XML
        Button btnPlayAudio = findViewById(R.id.btnPlayAudio);
        Button btnPauseAudio = findViewById(R.id.btnPauseAudio);
        Button btnPlayVideo = findViewById(R.id.btnPlayVideo);
        VideoView videoView = findViewById(R.id.videoView);

        // 2. Configurar el reproductor de Audio (Desde la carpeta res/raw)
        // Nota: Reemplaza "mi_audio" por el nombre real de tu archivo MP3
        mediaPlayer = MediaPlayer.create(this, R.raw.billie_jean);

        // Listener para reproducir audio
        btnPlayAudio.setOnClickListener(v -> {
            if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                Toast.makeText(MainActivity.this, "Reproduciendo audio", Toast.LENGTH_SHORT).show();
            }
        });

        // Listener para pausar audio
        btnPauseAudio.setOnClickListener(v -> {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                Toast.makeText(MainActivity.this, "Audio pausado", Toast.LENGTH_SHORT).show();
            }
        });

        // 3. Configurar la reproducción de Video (Vía streaming por Internet)
        btnPlayVideo.setOnClickListener(v -> {
            // URL pública de un video MP4 de prueba (puedes cambiarla por la tuya)
            // Reemplaza tu string videoUrl por este:
            String videoUrl = "https://drive.google.com/file/d/1he1I047IkkcCfQSRVs3czGg4J_PEvcr3/view?usp=drive_open";
            Uri videoUri = Uri.parse(videoUrl);

            videoView.setVideoURI(videoUri);

            // Agregar barra de controles nativa (Play, Pausa, Adelantar, Retroceder)
            MediaController mediaController = new MediaController(this);
            mediaController.setAnchorView(videoView);
            videoView.setMediaController(mediaController);

            // Iniciar la reproducción automáticamente al cargar
            videoView.setOnPreparedListener(mp -> {
                videoView.start();
                Toast.makeText(MainActivity.this, "Reproduciendo video", Toast.LENGTH_SHORT).show();
            });

            // Control de errores (por ejemplo, si se cae el Internet)
            videoView.setOnErrorListener((mp, what, extra) -> {
                Toast.makeText(MainActivity.this, "Error al cargar el video", Toast.LENGTH_SHORT).show();
                return true;
            });
        });
    }

    // BUENA PRÁCTICA: Liberar la memoria del audio si el usuario cierra la aplicación
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}