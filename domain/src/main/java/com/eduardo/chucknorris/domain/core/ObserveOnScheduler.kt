package com.eduardo.chucknorris.domain.core

import io.reactivex.Scheduler

interface ObserveOnScheduler {
    val scheduler: Scheduler
}