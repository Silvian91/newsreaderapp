package com.example.newsreaderapp.data.model

interface BaseMapper<FROM, TO> {
    fun map(from: FROM): TO
}