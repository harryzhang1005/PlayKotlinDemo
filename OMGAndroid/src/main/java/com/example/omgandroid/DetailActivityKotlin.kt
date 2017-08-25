package com.example.omgandroid

/**
 * Created by hongfeizhang on 8/25/17.
 *
 * 1. Classes in Kotlin are declared using the keyword class -- just like in Java.
 * 2. The default visibility modifier in Kotlin is public.
 * 3. Classes and methods are final by default. You can declare them open if you want extensibility.
 *
 * Since Kotlin is Java interoperable, you can use existing Java frameworks and libs in your Kotlin code files.
 */

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.view.Menu
import android.widget.ImageView
import android.widget.ShareActionProvider
import com.squareup.picasso.Picasso

/**
 * Make the class a subclass of Activity.
 *
 * Note that you do this in Kotlin a little differently from how you do it in Java.
 * In Kotlin, you append :NameOfParentClass() to the subclass declaration.
 */
class DetailActivityKotlin: Activity() {

    private val kImageURLBase = "http://covers.openlibrary.org/b/id/"
    internal var mImageURL = ""
    internal var mShareActionProvider: ShareActionProvider? = null

    // Note: You can use Android Studio's code generation functionality to generate the onCreate method signature with `control + O`
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detail)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val imageView = findViewById(R.id.img_cover) as ImageView

        val coverId = this.intent.extras.getString("coverID")

        val len = coverId?.length ?: 0

        if (len > 0) {
            mImageURL = kImageURLBase + coverId + "-L.jpg"
            Picasso.with(this).load(mImageURL).placeholder(R.drawable.img_books_loading).into(imageView)
        }
    }

    private fun setShareIntent() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Book Recommendation!")
        shareIntent.putExtra(Intent.EXTRA_TEXT, mImageURL)

        mShareActionProvider?.setShareIntent(shareIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main, menu)

        val shareItem = menu?.findItem(R.id.menu_item_share)

        mShareActionProvider = shareItem?.actionProvider as ShareActionProvider

        setShareIntent()

        return true
    }

}
