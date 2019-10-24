package com.scoprorject.rsccomponent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_two_line.view.*

/**
 * Created by ibnumuzzakkir on 22,October,2019
 */

class ItemLoanInfoComponent (container: ViewGroup) {
    val itemLoanInfoInput: PublishSubject<ComponentItemLoan.Input> =   PublishSubject.create<ComponentItemLoan.Input>()
    val btnTableCicilanOnClick: PublishSubject<ComponentItemLoan> =   PublishSubject.create<ComponentItemLoan>()


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
            btnTableCicilanOnClick.onNext(ComponentItemLoan.InstallmentTableOnClick)
        }

        itemLoanInfoInput.subscribe { data ->
            val status = presenter.checkStatusPinjaman(data.isIndebt ?: false)
            tvStatusPinjaman.text = status
            tvPembayaran.text = data.pembayaran
            tvPeriodePinjaman.text = data.periodePinjaman
            tvJumlahPinjaman.text = data.jmlPinjaman
        }
    }
}

sealed class ComponentItemLoan {
    data class Input(var jmlPinjaman : String? ,
                     var periodePinjaman : String?,
                     var pembayaran : String?,
                     var isIndebt: Boolean?)

    object InstallmentTableOnClick : ComponentItemLoan()
}