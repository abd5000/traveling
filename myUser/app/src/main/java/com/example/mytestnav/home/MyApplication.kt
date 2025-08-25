package com.example.mytestnav.home

import android.app.Application
import android.content.Context

public class MyApplication:Application() {
  private lateinit var context: Context
   public override fun onCreate() {
       super.onCreate()
       context=applicationContext
   }

    public override fun getApplicationContext(): Context {
        return context
    }
}