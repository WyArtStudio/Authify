package com.wahyuhw.authify.utils

import android.app.ProgressDialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.method.LinkMovementMethod
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.text.buildSpannedString
import androidx.core.text.color
import androidx.core.text.toSpannable
import androidx.core.widget.doAfterTextChanged
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.wahyuhw.authify.R
import com.wahyuhw.authify.base.BaseAdapter

fun Context.showToast(message: String, isLengthShort: Boolean = true) {
	Toast.makeText(this, message, if (isLengthShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
}

fun View.visible() {
	visibility = View.VISIBLE
}

fun View.gone() {
	visibility = View.GONE
}
fun View.invisible() {
	visibility = View.INVISIBLE
}

fun <T>T.isNotNull(): Boolean = this != null

fun AppCompatActivity.setupToolbar(toolbar: Toolbar, title: String, showBack: Boolean = true, navigationBackListener: (() -> Unit)? = null) {
	setSupportActionBar(toolbar)
	supportActionBar?.apply {
		this.title = title
		toolbar.setBackgroundColor(getColor(R.color.colorBlack))
		toolbar.setTitleTextColor(getColor(R.color.colorWhite))
		setDisplayHomeAsUpEnabled(showBack)
	}
	toolbar.setNavigationOnClickListener { navigationBackListener?.invoke() }
}

fun Context.hideKeyboard(view: View){
	val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
	inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun <T>T?.orDefault(value: T): T = this ?: value

fun emptyString() = ""

fun String?.orEmpty() = this ?: ""

fun String?.orEmptyDash() = this ?: "-"

fun Int?.orZero() = this ?: 0

fun Boolean?.orFalse() = this ?: false

fun Long?.orZeroL() = this ?: 0L

fun Float?.orZeroF() = this ?: 0f

fun Double?.orZeroDouble() = this ?: 0.0

fun String?.orOne() = this ?: "1"

fun String?.orZero() = this ?: "0"

fun String.toBearerToken() = "Bearer $this"

fun EditText.onTextChange(onTextChange: (String) -> Unit) {
	this.doAfterTextChanged {
		onTextChange.invoke(it.toString())
	}
}

fun BaseAdapter<*, *, *>.clear() {
	this.submitList(emptyList())
}

fun TabLayout.onTabPositionSelected(action: (Int) -> Unit) {
	this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
		override fun onTabSelected(tab: TabLayout.Tab) {
			action.invoke(tab.position)
		}
		
		override fun onTabUnselected(tab: TabLayout.Tab) {
		}
		
		override fun onTabReselected(tab: TabLayout.Tab) {
		}
	})
}

fun EditText.onTextSubmit(action: (String) -> Unit) {
	this.setOnEditorActionListener { textView, actionId, _ ->
		if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEND) {
			action.invoke(textView.text.toString())
			return@setOnEditorActionListener true
		}
		false
	}
}

fun ImageView.loadImageUrl(
	imgUrl: String,
	@DrawableRes imgPlaceHolder: Int
) {
	Glide.with(this)
		.load(imgUrl)
		.placeholder(imgPlaceHolder)
		.into(this)
}

fun Context.getCompatColor(@ColorRes color: Int): Int {
	return ContextCompat.getColor(this, color)
}

fun Context.getCompatDrawable(@DrawableRes drawable: Int): Drawable? {
	return ContextCompat.getDrawable(this, drawable)
}

fun View.onlyVisibleIf(condition: Boolean) {
	this.apply { if (condition) visible() else gone() }
}

fun View.onlyGoneIf(condition: Boolean) {
	this.apply { if (condition) gone() else visible() }
}

fun debug(listener: () -> String) {
	Log.d("Debug", listener.invoke())
}

fun <T> addOrUpdateItems(oldList: MutableList<T>, newItems: List<T>, getId: (T) -> Int): MutableList<T> {
	val updatedList = oldList.toMutableList()
	for (newItem in newItems) {
		val existingItemIndex = updatedList.indexOfFirst { getId(it) == getId(newItem) }
		if (existingItemIndex != -1) {
			updatedList[existingItemIndex] = newItem
		} else {
			updatedList.add(newItem)
		}
	}
	return updatedList
}

fun TextView.setSpannableText(context: Context, normalString: String, highlightString: String, action: (() -> Unit)? = null) {
	this.apply {
		movementMethod = if (action != null) LinkMovementMethod.getInstance() else null
		text = buildSpannedString {
			append(normalString)
			append(context.getString(R.string.label_space))
			color(context.getCompatColor(R.color.colorBaseBlack)) {
				semiBoldSpan(
					context = context,
					text = highlightString,
					clickAction = {
						action?.invoke()
					}
				)}
		}.toSpannable()
	}
}

fun String.isValidEmail(): Boolean {
	val pattern = Patterns.EMAIL_ADDRESS
	return pattern.matcher(this).matches()
}

fun String.isValidPassword(): Boolean {
	return this.isNotEmpty()
}

fun ProgressDialog.showLoadingDialog(message: String, isCancelable: Boolean = false) {
	this.setTitle(message)
	this.setCancelable(isCancelable)
	this.show()
}

fun Context.showAlertDialog(
	title: String,
	message: String,
	onConfirmClickAction:() -> Unit
) {
	val alertDialogBuilder = AlertDialog.Builder(this)
	
	alertDialogBuilder.setTitle(title)
	alertDialogBuilder.setMessage(message)
	alertDialogBuilder.setPositiveButton("Yes") { dialog, which ->
		onConfirmClickAction.invoke()
		dialog.dismiss()
	}
	alertDialogBuilder.setNegativeButton("Cancel") { dialog, which ->
		dialog.dismiss()
	}
	
	val alertDialog = alertDialogBuilder.create()
	alertDialog.show()
}