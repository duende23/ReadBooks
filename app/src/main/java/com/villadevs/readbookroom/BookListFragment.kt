package com.villadevs.readbookroom

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.villadevs.readbookroom.adapter.BookListAdapter
import com.villadevs.readbookroom.databinding.FragmentBookListBinding
import com.villadevs.readbookroom.viewmodel.BookViewModel
import com.villadevs.readbookroom.viewmodel.BookViewModelFactory

private const val ARG_PARAM1 = "param1"

class BookListFragment : Fragment() {

    private val viewModel: BookViewModel by activityViewModels {
        BookViewModelFactory((activity?.application as BookApplication).database.bookDao())
    }

    private var _binding: FragmentBookListBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentBookListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BookListAdapter { book ->
            val action =
                BookListFragmentDirections.actionBookListFragmentToBookDetailFragment(book.bookId)
            findNavController().navigate(action)
        }


        binding.apply {
            recyclerView.adapter = adapter
            fabAddForageable.setOnClickListener {
                findNavController().navigate(R.id.action_bookListFragment_to_addBookFragment)
            }
        }

        // TODO: observe the list of forageables from the view model and submit it the adapter
        viewModel.allBooks.observe(viewLifecycleOwner) { books ->
            books.let {
                adapter.submitList(it)
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}