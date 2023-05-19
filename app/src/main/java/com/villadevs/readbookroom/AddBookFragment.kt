package com.villadevs.readbookroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.villadevs.readbookroom.data.Book
import com.villadevs.readbookroom.databinding.FragmentAddBookBinding
import com.villadevs.readbookroom.viewmodel.BookViewModel
import com.villadevs.readbookroom.viewmodel.BookViewModelFactory


private const val ARG_PARAM1 = "param1"


class AddBookFragment : Fragment() {

    private val viewModel: BookViewModel by activityViewModels {
        BookViewModelFactory((activity?.application as BookApplication).database.bookDao())
    }

    private var _binding: FragmentAddBookBinding? = null
    private val binding get() = _binding!!

    lateinit var book: Book

    private val args: AddBookFragmentArgs by navArgs()

    private var param1: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAddBookBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookId = args.id

        if (bookId > 0) {

            //Observe a Forageable that is retrieved by id, set the forageable variable, and call the bindBook method
            viewModel.retrieveBook(bookId).observe(viewLifecycleOwner) { selectedBook ->
                book = selectedBook
                bindBook(book)
            }

            binding.btDelete.visibility = View.VISIBLE
            binding.btDelete.setOnClickListener {
                deleteBook(book)
            }
        } else {
            binding.btSave.setOnClickListener {
                addBook()
            }
        }

    }

    private fun deleteBook(book: Book) {
        viewModel.deleteBook(book)
        findNavController().navigate(R.id.action_addBookFragment_to_bookListFragment)
    }

    private fun addBook() {
        if (isValidEntry()) {
            viewModel.addBook(
                binding.etTitle.text.toString(),
                binding.etAuthor.text.toString(),
                binding.cbRead.isChecked,
                binding.etNotes.text.toString()
            )
            findNavController().navigate(
                R.id.action_addBookFragment_to_bookListFragment
            )
        }
    }

    private fun updateBook() {
        if (isValidEntry()) {
            viewModel.updateBook(
                bookId = args.id,
                bookTitle = binding.etTitle.text.toString(),
                bookAuthor = binding.etAuthor.text.toString(),
                bookRead= binding.cbRead.isChecked,
                bookNotes = binding.etNotes.text.toString()
            )
            findNavController().navigate(
                R.id.action_addBookFragment_to_bookListFragment
            )
        }
    }

    private fun bindBook(book: Book) {
        binding.apply{
            etTitle.setText(book.bookTitle, TextView.BufferType.SPANNABLE)
            etAuthor.setText(book.bookAuthor, TextView.BufferType.SPANNABLE)
            cbRead.isChecked = book.bookRead
            etNotes.setText(book.bookNotes, TextView.BufferType.SPANNABLE)
            btSave.setOnClickListener {
                updateBook()
            }
        }

    }

    private fun isValidEntry() = viewModel.isValidEntry(
        binding.etTitle.text.toString(),
        binding.etAuthor.text.toString()
    )


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}