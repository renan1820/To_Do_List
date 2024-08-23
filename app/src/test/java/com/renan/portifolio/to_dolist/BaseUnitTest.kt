package com.renan.portifolio.to_dolist

import com.renan.portifolio.to_dolist.ui.login.viewmodel.LoginViewModel
import io.mockk.every
import io.mockk.mockkObject
import org.junit.Test

import org.junit.Assert.*
import org.koin.test.KoinTest

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
open class BaseUnitTest : KoinTest{

    init {
        mockkObject(LoginViewModel())
        every {
        }

    }

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}