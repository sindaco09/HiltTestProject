package com.indaco.hilttestproject

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/*
 * This class is used to host Hilt Fragments that require a Hilt Activity with annotation
 * @AndroidEntryPoint. Currently this has to be done either in debug library or the main code base
 * it is better to leave the main codebase alone
 */
@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity()