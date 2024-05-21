package com.wceng.poems.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.wceng.poems.util.Converters

@Entity
@TypeConverters(Converters::class)
data class Poem(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val dynasty: String,
    val poet: String,
    val content: String,
    val translation: String,
    val annotation: String,
    val background: String,
    val appreciation: String,
    val labels: List<String>,
    var collected: Boolean
)

