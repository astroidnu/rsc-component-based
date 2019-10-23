package com.scoprorject.rsccomponent

/**
 * Created by ibnumuzzakkir on 22,October,2019
 */

class ItemLoanInfo {
    interface Presenter {
        fun checkStatusPinjaman(boolean: Boolean): String
    }

}

class ItemLoanInfoPresenter : ItemLoanInfo.Presenter {
    override fun checkStatusPinjaman(boolean: Boolean): String {
        return if(boolean) {
            "Lancar"
        } else {
            "Menunggak"
        }
    }

}