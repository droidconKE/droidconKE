package droiddevelopers254.droidconke.utils.network

import droiddevelopers254.droidconke.utils.network.Status.ERROR
import droiddevelopers254.droidconke.utils.network.Status.LOADING
import droiddevelopers254.droidconke.utils.network.Status.SUCCESS

class Resource<T> private constructor(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(LOADING, data, null)
        }
    }

}
