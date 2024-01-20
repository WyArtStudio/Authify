package com.wahyuhw.authify.utils

import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.text.style.TypefaceSpan
import android.view.View
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.bold
import androidx.core.text.inSpans
import com.wahyuhw.authify.R

class ClickActionSpan(
	private val context: Context,
	private val clickAction: () -> Unit
) : ClickableSpan() {
	override fun onClick(widget: View) {
		widget.cancelPendingInputEvents()
		clickAction.invoke()
	}
	
	override fun updateDrawState(ds: TextPaint) {
		super.updateDrawState(ds)
		ds.color = context.getCompatColor(R.color.colorWhiteGray)
		ds.isUnderlineText = false
	}
}

fun SpannableStringBuilder.semiBoldSpan(
	context: Context,
	text: String,
	clickAction: () -> Unit,
	@FontRes fontResourceId: Int = R.font.poppins_semibold,
): SpannableStringBuilder {
	return if (Build.VERSION.SDK_INT >= 28) {
		val myTypeface = Typeface.create(
			ResourcesCompat.getFont(context, fontResourceId), Typeface.BOLD
		)
		inSpans(TypefaceSpan(myTypeface)) {
			append(text, ClickActionSpan(context) {
				clickAction.invoke()
			}, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
		}
	} else {
		bold {
			append(text, ClickActionSpan(context) {
				clickAction.invoke()
			}, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
		}
	}
}