//package com.example.workingwithstorage.livedata
//
//import android.content.SharedPreferences
//import kotlinx.coroutines.InternalCoroutinesApi
//
//@InternalCoroutinesApi
//class LiveSharedPreferences constructor(private val preferences: SharedPreferences) {
//
//   private val publisher = PublishSubject.create<String>()
//    private val listener =
//        SharedPreferences.OnSharedPreferenceChangeListener { _, key -> publisher.onNext(key) }
//
//    private val updates = publisher.doOnSubscribe {
//        preferences.registerOnSharedPreferenceChangeListener(listener)
//    }.doOnDispose {
//        if (!publisher.hasObservers())
//            preferences.unregisterOnSharedPreferenceChangeListener(listener)
//    }
//}