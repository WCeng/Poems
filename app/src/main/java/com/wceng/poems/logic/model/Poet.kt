package com.wceng.poems.logic.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Poet(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val dynasty: String,
    val des: String
)
