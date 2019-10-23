package com.scoprorject.rsccomponent.core

import io.reactivex.rxjava3.core.Observable


interface UIComponent<T> {
    fun getContainerId(): Int
    fun getUserInteractionEvents(): Observable<T>
}