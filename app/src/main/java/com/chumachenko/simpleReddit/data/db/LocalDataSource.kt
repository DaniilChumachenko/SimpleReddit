package com.chumachenko.simpleReddit.data.db

import io.reactivex.Completable
import io.reactivex.Maybe

interface LocalDataSource<M, L> {
    fun findById(id: String?): Maybe<L>
    fun findByIds(ids: List<String>?): Maybe<List<L>>
    fun findAll(): Maybe<ArrayList<L>>
    fun addOrReplace(model: M): Maybe<L>
    fun addOrReplace(models: List<M>?): Maybe<List<L>>
    fun deleteAll(): Completable
    fun delete(models: List<M>?): Completable
    fun delete(id: String): Completable
}