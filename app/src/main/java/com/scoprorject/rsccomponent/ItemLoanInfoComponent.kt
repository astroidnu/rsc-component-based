package com.scoprorject.rsccomponent

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.scoprorject.rsccomponent.core.ComponentEvent
import com.scoprorject.rsccomponent.core.EventBusFactory
import com.scoprorject.rsccomponent.core.UIComponent
import io.reactivex.Observable
import kotlinx.android.synthetic.main.item_two_line.view.*

/**
 * Created by ibnumuzzakkir on 22,October,2019
 */
@SuppressLint("CheckResult")
class ItemLoanInfoComponent (container: ViewGroup,private val bus: EventBusFactory) : UIComponent<ComponentItemLoan>{

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

    override fun getContainerId() = R.layout.common_item_loan_info

    override fun getUserInteractionEvents(): Observable<ComponentItemLoan> {
        return bus.getSafeManagedObservable(ComponentItemLoan::class.java)
    }
    init {
        container.addView(uiView)

        bus.getSafeManagedObservable(ComponentItemLoan.Input::class.java).subscribe { data ->
            val status = presenter.checkStatusPinjaman(data.isIndebt ?: false)
            tvStatusPinjaman.text = status
            tvPembayaran.text = data.pembayaran
            tvPeriodePinjaman.text = data.periodePinjaman
            tvJumlahPinjaman.text = data.jmlPinjaman
        }
        btnTabelCicilan.setOnClickListener {
            bus.emit(ComponentItemLoan::class.java, ComponentItemLoan.InstallmentTableOnClick)
        }


    }
}

sealed class ComponentItemLoan : ComponentEvent() {
    data class Input(var jmlPinjaman : String? ,
                     var periodePinjaman : String?,
                     var pembayaran : String?,
                     var isIndebt: Boolean?) : ComponentItemLoan()

    object InstallmentTableOnClick : ComponentItemLoan()
}