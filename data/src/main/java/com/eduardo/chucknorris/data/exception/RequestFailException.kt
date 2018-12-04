package com.eduardo.chucknorris.data.exception

class RequestFailException(val code: Int)
    : RuntimeException("Request fails with code $code")