package com.sevenpeakssoftware.cars.ui

import android.text.format.DateFormat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sevenpeakssoftware.cars.data.Article
import com.sevenpeakssoftware.cars.repository.Resource
import org.threeten.bp.*
import org.threeten.bp.format.DateTimeFormatter
import androidx.palette.graphics.Palette
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition


@BindingAdapter("app:items")
fun setItems(listView: RecyclerView, resouce: Resource<List<Article>>?) {
    resouce?.let {
        if (resouce.data?.isNotEmpty()!!) {
            (listView.adapter as ArticlesAdapter).submitList(resouce.data)
        }
    }
}

@BindingAdapter("app:loadImage", "app:imageUrl")
fun loadImageWithGlide(imageView:ImageView, container:View, imageUrl:String) {
    Glide.with(container.context)
        .asBitmap()
        .load(imageUrl)
        .into(imageView)
    }


@BindingAdapter("app:dateTime")
fun setDateTime(textView: TextView, datetime: String) {
    val fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm").withZone(ZoneOffset.UTC)
    val dateTemporal = fmt.parse(datetime)

    val zonedDateTime = ZonedDateTime.from(dateTemporal)
    val nowZoneDateTime = ZonedDateTime.now(ZoneOffset.UTC)
    val showYear = nowZoneDateTime.year != zonedDateTime.year

    val timeInstant = Instant.from(dateTemporal)
    val fmtOut = DateTimeFormatter.ofPattern(
        "dd MMMM" +
                "${if (showYear) " yyyy" else ""}" +
                "${if (DateFormat.is24HourFormat(textView.context)) ", HH:mm" else ", hh:mm a"}")
        .withZone(ZoneId.systemDefault())

    textView.setText(fmtOut.format(timeInstant))
}





