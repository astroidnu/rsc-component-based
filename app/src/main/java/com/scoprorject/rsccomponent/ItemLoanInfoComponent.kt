package com.scoprorject.rsccomponent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_two_line.view.*

/**
 * Created by ibnumuzzakkir on 22,October,2019
 */

class ItemLoanInfoComponent (private val container: ViewGroup) {
    val compositeDisposable = CompositeDisposable()
    val btnTableOnClick: PublishSubject<Boolean> =   PublishSubject.create<Boolean>()
    val itemLoanInfoInput: PublishSubject<ComponentItemLoan.Input> =   PublishSubject.create<ComponentItemLoan.Input>()


    private val presenter: ItemLoanInfoPresenter by lazy {
        ItemLoanInfoPresenter()
    }

    private val uiView: View =
        LayoutInflater.from(container.context).inflate(R.layout.common_item_loan_info, container, false)

    val tvJumlahPinjaman = uiView.findViewById<TwoLineComponent>(R.id.jumlahPinjamanComponent).tv_subtitle
    val tvPeriodePinjaman = uiView.findViewById<TwoLineComponent>(R.id.periodePinjamanComponent).tv_subtitle
    val tvPembayaran = uiView.findViewById<TwoLineComponent>(R.id.pembayaranComponent).tv_subtitle
    val tvStatusPinjaman = uiView.findViewById<TwoLineComponent>(R.id.statusPinjamanComponent).tv_subtitle
    val btnTabelCicilan = uiView.findViewById<Button>(R.id.btnTabelCicilan)

    init {
        container.addView(uiView)

        btnTabelCicilan.setOnClickListener {
            btnTableOnClick.onNext(true)
        }

        compositeDisposable.add(
            itemLoanInfoInput.subscribe { data ->
                val status = presenter.checkStatusPinjaman(data.isIndebt ?: false)
                tvStatusPinjaman.text = status
                tvPembayaran.text = data.pembayaran
                tvPeriodePinjaman.text = data.periodePinjaman
                tvJumlahPinjaman.text = data.jmlPinjaman
            }
        )
    }

    fun onDetachView() {
        compositeDisposable.clear()
    }
}

sealed class ComponentItemLoan {
    data class Input(var jmlPinjaman : String? ,
                     var periodePinjaman : String?,
                     var pembayaran : String?,
                     var isIndebt: Boolean?)
}