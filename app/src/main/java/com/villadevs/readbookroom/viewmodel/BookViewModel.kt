package com.villadevs.readbookroom.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.villadevs.readbookroom.data.Book
import com.villadevs.readbookroom.data.BookDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BookViewModel(private val bookDao: BookDao) : ViewModel() {

    // TODO: create a property to set to a list of all forageables from the DAO
    //val allFruits: Flow<List<Fruit>> = fruitDao.getFruits()
    val allBooks: LiveData<List<Book>> = bookDao.getAllBooks().asLiveData()

    // TODO : create method that takes id: Long as a parameter and retrieve a Forageable from the database by id via the DAO.
    fun retrieveBook(id: Long): LiveData<Book> {
        return bookDao.getBook(id).asLiveData()
    }


    fun addBook(bookTitle: String, bookAuthor: String, bookRead:Boolean, bookNotes: String) {
        val book = Book(bookTitle = bookTitle, bookAuthor = bookAuthor, bookRead = bookRead, bookNotes = bookNotes)
        viewModelScope.launch(Dispatchers.IO) {
            bookDao.insert(book)
        }

    }

    fun updateBook(bookId: Long, bookTitle: String, bookAuthor: String, bookRead:Boolean, bookNotes: String) {
        val book = Book(bookId = bookId, bookTitle = bookTitle, bookAuthor = bookAuthor, bookRead = bookRead, bookNotes = bookNotes)
        viewModelScope.launch(Dispatchers.IO) {
            bookDao.update(book)
        }
    }


    fun deleteBook(book: Book) {
        viewModelScope.launch(Dispatchers.IO) {
            bookDao.delete(book)
        }
    }

    fun isValidEntry(bookTitle: String, bookAuthor: String): Boolean {
        return bookTitle.isNotBlank() && bookAuthor.isNotBlank()
    }
}
