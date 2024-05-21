package com.wceng.poems.ui.detail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.wceng.poems.R
import com.wceng.poems.databinding.ActivityPoemDetailBinding
import com.wceng.poems.logic.model.Poem
import com.wceng.poems.logic.model.Poet
import java.util.Locale


class PoemDetailActivity : AppCompatActivity() {

    companion object {
        fun actionShowDetail(context: Context, poemId: Long, poetName: String) {
            val intent = Intent(context, PoemDetailActivity::class.java)
            intent.putExtra("poemId", poemId)
            intent.putExtra("poetName", poetName)
            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityPoemDetailBinding
    private lateinit var tts: TextToSpeech
    private var ttsSupport = false

    private val viewModel by lazy {
        ViewModelProvider(this, PoemDetailViewModelFactory())[PoemDetailViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPoemDetailBinding.inflate(layoutInflater)
        window.statusBarColor = Color.TRANSPARENT
        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Glide.with(this).load(R.drawable.poem_place_holder).into(binding.poemImage)

        initTts()

        binding.poemMainLayout.audioIv.setOnClickListener {
            if (tts.isSpeaking) {
                tts.stop()
            } else {
                speak(viewModel.getPoemSpeakText())
            }
        }

        viewModel.poemLiveData.observe(this) { poem ->
            fillPoem(poem)
        }

        viewModel.poetLiveData.observe(this) { poet ->
            fillPoet(poet)
        }

        viewModel.queryPoem(intent.getLongExtra("poemId", -1))
        viewModel.queryPoet(intent.getStringExtra("poetName") ?: "")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("SetTextI18n")
    private fun fillPoem(poem: Poem) {

        if (poem.content.isNotEmpty()) {
            binding.poemMainLayout.root.visibility = View.VISIBLE
            binding.poemMainLayout.poemTitleTv.text = poem.title
            binding.poemMainLayout.poemDynastyPoetTv.text = "${poem.dynasty} · ${poem.poet}"
            binding.poemMainLayout.poemContentTv.text = poem.content

            if (poem.collected) {
                binding.poemMainLayout.collectionIv.setImageResource(R.drawable.baseline_star_24)
            } else {
                binding.poemMainLayout.collectionIv.setImageResource(R.drawable.baseline_star_border_24)
            }

            binding.poemMainLayout.collectionIv.setOnClickListener {
                poem.collected = !poem.collected
                viewModel.updatePoem(poem)
            }
        }

        if (poem.translation.isNotEmpty()) {
            binding.poemTranslationLayout.root.visibility = View.VISIBLE
            binding.poemTranslationLayout.poemTranslation.text = poem.translation
        }

        if (poem.annotation.isNotEmpty()) {
            binding.poemAnnotationLayout.root.visibility = View.VISIBLE
            binding.poemAnnotationLayout.poemAnnotation.text = poem.annotation
        }

        if (poem.background.isNotEmpty()) {
            binding.poemBackgroundLayout.root.visibility = View.VISIBLE
            binding.poemBackgroundLayout.poemBackground.text = poem.background
        }

        if (poem.appreciation.isNotEmpty()) {
            binding.poemAppreciationLayout.root.visibility = View.VISIBLE
            binding.poemAppreciationLayout.poemAppreciation.text = poem.appreciation
        }

    }

    private fun fillPoet(poet: Poet?) {
        poet?.let {
            binding.poetDetailLayout.root.visibility = View.VISIBLE
            binding.poetDetailLayout.poetName.text = poet.name
            Glide.with(this)
                .load(poet.imgUrl)
                .error(R.drawable.baseline_broken_image_24)
                .into(binding.poetDetailLayout.poetIcon)
            binding.poetDetailLayout.poetDes.text = poet.des
        }

    }

    private fun speak(s: String) {
        if (ttsSupport) {
            tts.speak(s, TextToSpeech.QUEUE_FLUSH, null, null)
        } else {
            Toast.makeText(
                this@PoemDetailActivity,
                R.string.not_support_chinese_volume,
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun initTts() {
        tts = TextToSpeech(this) { status ->
            if (status != TextToSpeech.ERROR) {
                val result: Int = tts.setLanguage(Locale.CHINA)
                ttsSupport =
                    !(result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED)
            }
        }
    }

    override fun onDestroy() {
        tts.stop()
        tts.shutdown()
        super.onDestroy()
    }


}
