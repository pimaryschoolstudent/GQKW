package com.example.myapplication.Utils;

import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class RxBus {
    var map = HashMap<Any,CompositeDisposable>()
    var mBus :Subject<Any> = PublishSubject.create<Any>().toSerialized()
    companion object{
        val util:RxBus by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { RxBus() }

        fun <T>handler():ObservableTransformer<T,T>{
            return ObservableTransformer {
                it.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            }
        }
    }

    fun register(any: Any, disposable1: Disposable){
       var compositeDisposable =  map[any]
        if (compositeDisposable == null){
            compositeDisposable = CompositeDisposable()
        }
        compositeDisposable.add(disposable1)
        map[any] = compositeDisposable
    }

    fun unregister(disposable: Any){
        var compositeDisposable = map[disposable]
        if (compositeDisposable!=null){
            compositeDisposable.clear()
            map.remove(disposable)
        }
    }

    inline fun <reified T:Any>observe():Observable<T>{
        return mBus.ofType(T::class.java)
    }

    fun post(ob:Any){
        mBus.onNext(ob)
    }
}

fun Disposable.registerInBus(disposable: Any){
    RxBus.util.register(disposable,this)
}
