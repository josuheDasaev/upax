package com.dasaevcompany.upax.utilities

interface CallBack<T> {
    fun onSuccess(result: T?)
    fun onFailed (exception: Exception?) }