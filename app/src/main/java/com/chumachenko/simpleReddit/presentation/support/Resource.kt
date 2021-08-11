package com.chumachenko.simpleReddit.presentation.support

class Resource<T>(
    val status: Status,
    var data: T?,
    val throwable: Throwable?
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Resource<*>

        if (status != other.status) return false
        if (data != other.data) return false
        if (throwable != other.throwable) return false

        return true
    }

    override fun hashCode(): Int {
        var result = status.hashCode()
        result = 31 * result + (data?.hashCode() ?: 0)
        result = 31 * result + (throwable?.hashCode() ?: 0)
        return result
    }



    fun isSuccess(): Boolean {
        return this.status == Status.SUCCESS
    }

    fun isLoading(): Boolean {
        return this.status == Status.LOADING
    }

    fun isError() : Boolean{
        return this.status == Status.ERROR
    }

    override fun toString(): String {
        return "Resource(status=$status, data=$data, throwable=$throwable)"
    }

    companion object {

        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(data: T? = null, throwable: Throwable? = null): Resource<T> {
            return Resource(Status.ERROR, data, throwable)
        }

        fun <T> loading(): Resource<T> {
            return Resource(Status.LOADING, null, null)
        }
    }
}