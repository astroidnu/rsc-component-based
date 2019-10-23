package com.scoprorject.rsccomponent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.reactivex.rxjava3.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val compositeDisposable = CompositeDisposable()

    lateinit var itemLoanInfoComponent: ItemLoanInfoComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val data = ComponentItemLoan.Input(
            jmlPinjaman = "10 jt"
            ,pembayaran = "1 dari 13",
            periodePinjaman = "3 Bulan",
            isIndebt = false)


        itemLoanInfoComponent = ItemLoanInfoComponent(container)

        itemLoanInfoComponent.itemLoanInfoInput.onNext(data)

        compositeDisposable.add(
            itemLoanInfoComponent.btnTableOnClick.subscribe {
                Toast.makeText(this@MainActivity, "Hello Bro", Toast.LENGTH_LONG).show()

            }
        )
    }

    override fun onDestroy() {
        itemLoanInfoComponent.onDetachView()
        super.onDestroy()
    }
}
