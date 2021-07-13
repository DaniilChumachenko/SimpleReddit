package com.chumachenko.simpleReddit.data.db

import com.chumachenko.simpleReddit.data.db.realmModel.RedditItemRealm
import com.chumachenko.simpleReddit.data.repository.model.RedditItem
import com.google.gson.Gson
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import io.realm.Case
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Inject

class RedditLocalSource @Inject constructor(val gson: Gson) :
    RedditDataSource<RedditItemRealm, RedditItem> {

    private val realmConfiguration: RealmConfiguration? = Realm.getDefaultConfiguration()
    private val realmEntity: Class<RedditItemRealm> = RedditItemRealm::class.java

    override fun search(search: String, isFollowing: Boolean): Maybe<List<RedditItem>> {
        val realm = Realm.getInstance(realmConfiguration!!)
        val managedEntities: List<RedditItemRealm> = realm.where(realmEntity)
            .equalTo("isFollowing", isFollowing)
            .and()
            .contains("name", search, Case.INSENSITIVE)
            .limit(20)
            .findAll()

        val unmanagedEntities = realm.copyFromRealm(managedEntities)

        realm.close()

        return if (unmanagedEntities.isEmpty()) Maybe.empty() else Maybe.just(unmanagedEntities)
            .map { items ->
                val list: ArrayList<RedditItem> = ArrayList()
                items.forEach { item -> list.add(item.toRedditItem()) }
                list
            }
    }

    override fun findById(id: String?): Maybe<RedditItem> {
        val realm: Realm = Realm.getInstance(realmConfiguration!!)

        val managedModel: RedditItemRealm = realm.where(realmEntity)
            .equalTo("id", id)
            .findFirst()
            ?: return Maybe.empty()

        val unmanagedModel: RedditItemRealm = realm.copyFromRealm(managedModel)

        realm.close()

        return Maybe.just(unmanagedModel)
            .subscribeOn(Schedulers.io())
            .map { item ->
                item.toRedditItem()
            }
    }

    override fun findByIdSync(id: String): RedditItem? {
        val realm: Realm = Realm.getInstance(realmConfiguration!!)

        val managedModel: RedditItemRealm = realm.where(realmEntity)
            .equalTo("id", id)
            .findFirst()
            ?: return null

        val unmanagedModel: RedditItemRealm = realm.copyFromRealm(managedModel)

        realm.close()

        return unmanagedModel.toRedditItem()
    }

    override fun findByIdRealmObject(itemId: String): RedditItemRealm? {
        val realm: Realm = Realm.getInstance(realmConfiguration!!)

        val managedModel: RedditItemRealm? = realm.where(realmEntity)
            .equalTo("id", itemId)
            .findFirst()
        var model: RedditItemRealm? = null
        managedModel?.let {
            model = realm.copyFromRealm(it)
        }
        realm.close()
        return model
    }


    override fun findByIds(ids: List<String>?): Maybe<List<RedditItem>> {
        val realm = Realm.getInstance(realmConfiguration!!)
        realm.refresh()

        val managedModels: List<RedditItemRealm>? = realm.where(realmEntity)
            .`in`("id", ids?.toTypedArray())
            .findAll()

        val unmanagedModels = realm.copyFromRealm(managedModels!!)

        realm.close()

        return if (unmanagedModels.isEmpty()) {
            Maybe.empty()
        } else {

            Maybe.just(unmanagedModels)
                .map { items ->
                    val list: ArrayList<RedditItem> = ArrayList()
                    items.forEach { item -> list.add(item.toRedditItem()) }
                    list
                }
        }
    }

    override fun addOrReplace(model: RedditItemRealm): Maybe<RedditItem> {
        try {
            Realm.getInstance(realmConfiguration!!).use { realmInstance ->
                realmInstance.executeTransaction { realm: Realm ->
                    realm.createOrUpdateObjectFromJson(
                        realmEntity,
                        gson.toJson(model)
                    )
                }
                realmInstance.close()
                return Maybe.just(model.toRedditItem())
            }
        } catch (error: Exception) {
            error.printStackTrace()
            return Maybe.error(error)
        }
    }

    override fun addOrReplace(models: ArrayList<RedditItemRealm>?): Maybe<ArrayList<RedditItem>> {
        try {
            Realm.getInstance(realmConfiguration!!).use { realmInstance ->
                realmInstance.executeTransaction { realm: Realm ->
                    realm.createOrUpdateAllFromJson(
                        realmEntity,
                        gson.toJson(models)
                    )
                }
                realmInstance.close()
                return Maybe.just(models ?: ArrayList()).map { items ->
                    val list: ArrayList<RedditItem> = ArrayList()
                    items.forEach { item -> list.add(item.toRedditItem()) }
                    list
                }
            }
        } catch (error: Exception) {
            error.printStackTrace()
            return Maybe.error(error)
        }
    }

    override fun deleteAll(): Completable {
        try {
            Realm.getInstance(realmConfiguration!!).use { realmInstance ->
                realmInstance.executeTransaction { realm: Realm ->
                    realm.where(realmEntity).findAll().deleteAllFromRealm()
                }
            }
        } catch (e: Error) {
            e.printStackTrace()
            return Completable.error(e)
        }
        return Completable.defer { Completable.complete() }
    }

    override fun delete(models: ArrayList<RedditItemRealm>?): Completable {
        try {
            Realm.getInstance(realmConfiguration!!).use { realmInstance ->
                realmInstance.executeTransactionAsync { realm: Realm ->
                    realm.where(realmEntity)
                        .`in`("id", getIds(models ?: ArrayList()))
                        .findAll()
                        .deleteAllFromRealm()
                }
            }
        } catch (e: Error) {
            e.printStackTrace()
            return Completable.error(e)
        }
        return Completable.defer { Completable.complete() }
    }

    override fun delete(id: String): Completable {
        try {
            Realm.getInstance(realmConfiguration!!).use { realmInstance ->
                realmInstance.executeTransaction { realm: Realm ->
                    realm.where(realmEntity)
                        .equalTo("id", id)
                        .findAll()
                        .deleteAllFromRealm()
                }
            }
        } catch (e: Error) {
            e.printStackTrace()
            return Completable.error(e)
        }
        return Completable.defer { Completable.complete() }
    }

    override fun deleteSync(id: String) {
        try {
            Realm.getInstance(realmConfiguration!!).use { realmInstance ->
                realmInstance.executeTransaction { realm: Realm ->
                    realm.where(realmEntity)
                        .equalTo("id", id)
                        .findAll()
                        .deleteAllFromRealm()
                }
            }
        } catch (e: Error) {
            e.printStackTrace()
        }
    }

    private fun getIds(models: List<RedditItemRealm>): Array<String> {
        return models.map { it.id }.toTypedArray()
    }

    override fun addOrReplaceSync(model: RedditItemRealm): RedditItem {
        try {
            Realm.getInstance(realmConfiguration!!).use { realmInstance ->
                realmInstance.executeTransaction { realm: Realm ->
                    realm.createOrUpdateObjectFromJson(
                        realmEntity,
                        gson.toJson(model)
                    )
                }
                realmInstance.close()
                return model.toRedditItem()
            }
        } catch (error: Exception) {
            error.printStackTrace()
            throw error
        }
    }

    override fun findAll(): Maybe<ArrayList<RedditItem>> {
        val realm = Realm.getInstance(realmConfiguration!!)
        realm.refresh()

        val managedModels: List<RedditItemRealm>? = realm.where(realmEntity)
            .findAll()

        val unmanagedModels = realm.copyFromRealm(managedModels!!)

        realm.close()

        return if (unmanagedModels.isEmpty()) {
            Maybe.empty()
        } else {

            Maybe.just(unmanagedModels)
                .map { items ->
                    val list: ArrayList<RedditItem> = ArrayList()
                    items.forEach { item -> list.add(item.toRedditItem()) }
                    list
                }
        }
    }

}