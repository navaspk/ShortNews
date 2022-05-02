package com.sample.newsfeed.base

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.util.*

abstract class BaseActivity<VB : ViewBinding>(private val inflate: Inflate<VB>) :
    AppCompatActivity(), BaseInterface {

    //region VARIABLES

    private var _binding: ViewBinding? = null

    @Suppress("UNCHECKED_CAST")
    protected val viewDataBinding: VB
        get() = _binding as VB

    //endregion

    //region LIFECYCLE

    override fun attachBaseContext(base: Context?) {
        base?.resources?.configuration?.setLocale(Locale.getDefault())
        super.attachBaseContext(base)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = inflate.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        initUserInterface()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    //endregion

}
