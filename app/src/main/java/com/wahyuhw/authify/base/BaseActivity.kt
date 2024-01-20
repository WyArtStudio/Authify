package com.wahyuhw.authify.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewbinding.ViewBinding
import com.wahyuhw.authify.R
import com.wahyuhw.authify.utils.showLoadingDialog

abstract class BaseActivity<T : ViewBinding> : AppCompatActivity() {
    private var mBinding: T? = null
    protected val binding get() = mBinding!!
    private lateinit var errorDialog: AlertDialog
    private lateinit var loadingDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = getViewBinding()
        setupDialog()
        setupContentView()
        setupIntent()
        setupUI()
        setupAction()
        setupProcess()
        setupObserver()
    }
    
    private fun setupDialog() {
        loadingDialog = ProgressDialog(this)
        errorDialog = AlertDialog.Builder(this).create()
    }
    
    private fun setupContentView() {
        setContentView(mBinding?.root)
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    abstract fun getViewBinding(): T

    abstract fun setupIntent()

    abstract fun setupUI()

    abstract fun setupAction()

    abstract fun setupProcess()

    abstract fun setupObserver()

    protected fun setErrorDialog(alertDialog: AlertDialog) {
        errorDialog = alertDialog
    }
    
    protected fun showErrorDialog(message: String, onRetry: (() -> Unit)? = null) {
        errorDialog.apply {
            this.setTitle(getString(R.string.label_error))
            setMessage(message)
            setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.action_retry)) { dialog, _ ->
                onRetry?.invoke()
                dialog.dismiss()
            }
            show()
        }
    }
    
    protected fun dismissLoading() {
        loadingDialog.dismiss()
    }
    
    protected fun showLoading(message: String, isCancelable: Boolean = false) {
        loadingDialog.showLoadingDialog(message, isCancelable)
    }

    protected fun navigateBack() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun onDestroy() {
        mBinding = null
        super.onDestroy()
    }
}