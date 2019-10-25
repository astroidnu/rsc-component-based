package com.scoprorject.rsccomponent

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.scoprorject.rsccomponent.core.EventBusFactory
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LifecycleOwner {
    private lateinit var lifecycleRegistry: LifecycleRegistry

    val data = ComponentItemLoan.Input(
        jmlPinjaman = "10 jt"
        , pembayaran = "1 dari 13",
        periodePinjaman = "3 Bulan",
        isIndebt = false
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleRegistry = LifecycleRegistry(this)
        lifecycleRegistry.currentState = Lifecycle.State.CREATED

        setContentView(R.layout.activity_main)

        initComponent(container)
    }

    @SuppressLint("CheckResult")
    private fun initComponent(rootViewContainer: ViewGroup) {
        ItemLoanInfoComponent(rootViewContainer, EventBusFactory.get(this))
            .getUserInteractionEvents()
            .subscribe {
                when (it) {
                    ComponentItemLoan.InstallmentTableOnClick -> {
                        Intent(this@MainActivity, InstallmentTableActivity::class.java).run {
                            startActivity(this)
                        }
                    }
                }
            }

        Observable.just(data)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                EventBusFactory.get(this)
                    .emit(ComponentItemLoan.Input::class.java, it)
            }.subscribe()
    }
}
