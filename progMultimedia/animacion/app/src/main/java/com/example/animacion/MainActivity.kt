package com.example.animacion

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.ImageView
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var videoView: VideoView
    private lateinit var moveButton: Button
    private lateinit var soundButton: Button
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        videoView = findViewById(R.id.videoView)
        moveButton = findViewById(R.id.moveButton)
        soundButton = findViewById(R.id.soundButton)

        // Inicializar el reproductor de sonido
        mediaPlayer = MediaPlayer.create(this, R.raw.sound)

        moveButton.setOnClickListener { moveImage() }
        soundButton.setOnClickListener { playSoundAndShowVideo() }
    }

    private fun moveImage() {
        val animation = TranslateAnimation(0f, 500f, 0f, 500f)
        animation.duration = 1000
        animation.fillAfter = true
        imageView.startAnimation(animation)
    }

    private fun playSoundAndShowVideo() {
        // Reproducir sonido
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        }

        // Ocultar la imagen y mostrar el video
        imageView.visibility = ImageView.GONE
        videoView.visibility = VideoView.VISIBLE

        // Configurar la ruta del video
        val videoUri = Uri.parse("android.resource://$packageName/${R.raw.video}")
        videoView.setVideoURI(videoUri)

        // Iniciar el video
        videoView.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}
