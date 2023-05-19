package com.villadevs.readbookroom.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
data class Book(
    val id: Long = 0,
    val title: String,
    val author: String,
    //val read: Boolean,
    //val notes: String?
)*/

@Entity
data class Book(
    @PrimaryKey(autoGenerate = true) val bookId: Long = 0L,
    @ColumnInfo(name = "title") val bookTitle: String,
    @ColumnInfo(name = "author") val bookAuthor: String,
    @ColumnInfo(name = "read") val bookRead: Boolean,
    @ColumnInfo(name = "notes") val bookNotes: String?,
)
