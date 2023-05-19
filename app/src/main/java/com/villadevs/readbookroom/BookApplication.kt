package com.villadevs.readbookroom

import android.app.Application
import com.villadevs.readbookroom.data.BookRoomDatabase

class BookApplication:Application() {
    val database: BookRoomDatabase by lazy { BookRoomDatabase.getDatabase(this) }
}