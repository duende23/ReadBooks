package com.villadevs.readbookroom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.villadevs.readbookroom.data.Book
import com.villadevs.readbookroom.databinding.FragmentBookDetailBinding
import com.villadevs.readbookroom.databinding.FragmentBookListBinding
import com.villadevs.readbookroom.viewmodel.BookViewModel
import com.villadevs.readbookroom.viewmodel.BookViewModelFactory


private const val ARG_PARAM1 = "param1"


class BookDetailFragment : Fragment() {

    private val viewModel: BookViewModel by activityViewModels {
        BookViewModelFactory((activity?.application as BookApplication).database.bookDao())
    }

    private var _binding: FragmentBookDetailBinding? = null
    private val binding get() = _binding!!

    lateinit var book: Book

    private val args: BookDetailFragmentArgs by navArgs()

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
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookId = args.id

        viewModel.retrieveBook(bookId).observe(viewLifecycleOwner) { selectedBook->
            book = selectedBook
            bindBook(book)
        }


    }

    private fun bindBook(book: Book) {
        binding.apply {
            tvTitleDetails.text = book.bookTitle
            tvAuthorDetails.text = book.bookAuthor
            tvNoteDetails.text = book.bookNotes
            if (book.bookRead) {
                tvBookReadDetails.text = getString(R.string.yes_read)
            } else {
                tvBookReadDetails.text = getString(R.string.no_read)
            }
            fabEditBook.setOnClickListener {
                val action = BookDetailFragmentDirections.actionBookDetailFragmentToAddBookFragment2(book.bookId)
                findNavController().navigate(action)
            }

            /*    location.setOnClickListener {
                    launchMap()
                }*/
        }
    }

   /* private fun launchMap() {
        val address = forageable.address.let {
            it.replace(", ", ",")
            it.replace(". ", " ")
            it.replace(" ", "+")
        }
        val gmmIntentUri = Uri.parse("geo:0,0?q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        startActivity(mapIntent)
    }*/


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}