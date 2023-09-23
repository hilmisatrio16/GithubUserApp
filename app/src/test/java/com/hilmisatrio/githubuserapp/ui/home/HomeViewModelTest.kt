package com.hilmisatrio.githubuserapp.ui.home

import androidx.lifecycle.LiveData
import com.hilmisatrio.githubuserapp.data.repository.UserRepository
import org.junit.Before
import com.hilmisatrio.githubuserapp.data.Result
import com.hilmisatrio.githubuserapp.data.remote.response.ResponseSearchUsers
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito.*

class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var userRepository: UserRepository

    @Before
    fun initial() {
        userRepository = mock(UserRepository::class.java)
        homeViewModel = HomeViewModel(userRepository)
    }

    @Test
    fun getUsers() {
        val respon = mockk<LiveData<Result<ResponseSearchUsers>>>()
        `when`(userRepository.searchUser("hilmi")).thenReturn(respon)
        val result = homeViewModel.getUsers("hilmi")
        verify(userRepository).searchUser("hilmi")
        assertEquals(respon, result)
    }
}