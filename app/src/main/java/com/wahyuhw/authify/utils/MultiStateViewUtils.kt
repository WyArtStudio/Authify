package com.wahyuhw.authify.utils

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.kennyc.view.MultiStateView
import com.wahyuhw.authify.R

fun MultiStateView.showEmptyList(title: String? = null, message: String? = null, imageResId: Int? = null) {
    this.viewState = MultiStateView.ViewState.EMPTY
    this.getView(MultiStateView.ViewState.EMPTY)?.let {
        val imgEmpty: ImageView = it.findViewById(R.id.imgEmpty)
        val tvEmptyTitle: TextView = it.findViewById(R.id.tvEmptyTitle)
        val tvEmptyMessage: TextView = it.findViewById(R.id.tvEmptyMessage)
        val btnRetry: Button = it.findViewById(R.id.btnRetry)

        btnRetry.gone()
        if (imageResId != null) {
            imgEmpty.setImageResource(imageResId)
        } else if (imgEmpty.drawable == null) {
            imgEmpty.gone()
        }
        title?.let { text -> tvEmptyTitle.text = text }
        message?.let { text ->
            if (text.isEmpty()) tvEmptyMessage.gone() else tvEmptyMessage.text = text
        }
    }
}

fun MultiStateView.showError(title: String? = null, message: String? = null, imageResId: Int? = null, onRetry: (() -> Unit)? = null) {
    this.viewState = MultiStateView.ViewState.ERROR
    this.getView(MultiStateView.ViewState.ERROR)?.let {
        val imgEmpty: ImageView = it.findViewById(R.id.imgEmpty)
        val tvEmptyTitle: TextView = it.findViewById(R.id.tvEmptyTitle)
        val tvEmptyMessage: TextView = it.findViewById(R.id.tvEmptyMessage)
        val btnRetry: Button = it.findViewById(R.id.btnRetry)

        if (imageResId != null) {
            imgEmpty.setImageResource(imageResId)
        } else if (imgEmpty.drawable == null) {
            imgEmpty.gone()
        }
        tvEmptyTitle.text =
            title ?: context.getString(R.string.label_error)
        message?.let { text ->
            if (text.isEmpty()) tvEmptyMessage.gone() else tvEmptyMessage.text = text
        }
        btnRetry.setOnClickListener {
            onRetry?.invoke()
        }
    }
}

fun MultiStateView.showContent() {
    this.viewState = MultiStateView.ViewState.CONTENT
}

fun MultiStateView.showLoading(){
    this.viewState = MultiStateView.ViewState.LOADING
}

fun MultiStateView.showErrorState(
    title: String?,
    message: String?,
    @DrawableRes imgResourceId: Int?,
    onRetry: (() -> Unit)?
) {
    this.viewState = MultiStateView.ViewState.ERROR
    this.showError(
        title = title,
        message = message,
        imageResId = imgResourceId,
        onRetry = { onRetry?.invoke() }
    )
}