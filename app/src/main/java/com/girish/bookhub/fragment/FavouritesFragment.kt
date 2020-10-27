package com.girish.bookhub.fragment


import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room.databaseBuilder
import com.girish.bookhub.R
import com.girish.bookhub.adapter.FavouritesRecyclerAdapter
import com.girish.bookhub.database.BookDatabase
import com.girish.bookhub.database.BookEntity

class FavouritesFragment : Fragment() {

    lateinit var progressBarLayout: RelativeLayout
    lateinit var progressBar: ProgressBar
    lateinit var recyclerLayoutFav : RecyclerView
    lateinit var layoutManager : RecyclerView.LayoutManager
    lateinit var recyclerAdapter : FavouritesRecyclerAdapter
    var dbFavBookList = listOf<BookEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourites, container, false)

        recyclerLayoutFav = view.findViewById(R.id.recyclerLayoutFav)
        progressBarLayout = view.findViewById(R.id.progressBarLayout)
        progressBarLayout.visibility = View.VISIBLE
        layoutManager = GridLayoutManager(activity as Context, 2)
        dbFavBookList = RetriveFavourites(activity as Context).execute().get()

        if(activity != null)
        {
            progressBarLayout.visibility = View.GONE
            recyclerAdapter = FavouritesRecyclerAdapter(activity as Context,dbFavBookList)
            recyclerLayoutFav.layoutManager = layoutManager
        }


        return view
    }

    class RetriveFavourites(val context: Context) : AsyncTask<Void, Void, List<BookEntity>>() {


        override fun doInBackground(vararg p0: Void?): List<BookEntity> {

            val db = databaseBuilder(context, BookDatabase::class.java, "books-db").build()


            return db.bookDao().getAllBooks()
        }

    }


}

