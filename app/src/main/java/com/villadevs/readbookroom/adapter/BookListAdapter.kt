package com.villadevs.readbookroom.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.villadevs.readbookroom.databinding.BookListItemBinding
import com.villadevs.readbookroom.data.Book

class BookListAdapter(val onItemClicked: (Book) -> Unit) :
    ListAdapter<Book, BookListAdapter.BookViewHolder>(DiffCallback) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val viewHolder = BookViewHolder(BookListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

        /* viewHolder.itemView.setOnClickListener {
             val position = viewHolder.adapterPosition
             onItemClicked(getItem(position))
         }*/
        return viewHolder
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val currentBook = getItem(position)
        holder.bind(currentBook)

        holder.itemView.setOnClickListener {
            onItemClicked(currentBook)
        }
    }


    class BookViewHolder(private var binding: BookListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(book: Book) {
            /* binding.tvItemName.text = Book.itemName
             binding.tvItemPrice.text = Book.getFormattedPrice()
             binding.tvItemQuantity.text = Book.quantityInStock.toString()*/

            binding.apply {
                tvTitle.text = book.bookTitle
                tvAuthor.text = book.bookAuthor
                //tvItemQuantity.text = Book.quantityInStock.toString()
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Book>() {
            override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem.bookId == newItem.bookId
                //return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
                return oldItem == newItem
                //return oldItem.title == newItem.title
            }
        }
    }

}