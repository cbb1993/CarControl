package com.base.event


class NetworkChangeEvent(var isConnected: Boolean)
class FragmentBackLast(var isBacked: Boolean)
class MusicFragmentBackLast(var isBacked: Boolean)
class LocationSearchEvent(var code :Int,var name:String,var address:String,var latitude:Double,var longitude:Double)
class FragmentBack()