package com.eduardo.chucknorris.domain.core

import io.reactivex.Scheduler

interface ObserveOnScheduler {
    fun getScheduler(): Scheduler
}