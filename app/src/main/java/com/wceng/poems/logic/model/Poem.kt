package com.wceng.poems.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
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
    val labels: String
)
