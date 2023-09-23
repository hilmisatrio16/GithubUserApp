package com.hilmisatrio.githubuserapp.ui.detail

import androidx.lifecycle.LiveData
import com.hilmisatrio.githubuserapp.data.local.entity.UserFavorite
import com.hilmisatrio.githubuserapp.data.repository.UserRepository
import com.hilmisatrio.githubuserapp.data.Result
import com.hilmisatrio.githubuserapp.data.remote.response.ResponseDetailUser
import com.hilmisatrio.githubuserapp.data.remote.response.ResponseFollow
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class DetailViewModelTest {

    private lateinit var detailViewModel: DetailViewModel
    private lateinit var userRepository: UserRepository

    @Before
    fun initial() {
        userRepository = Mockito.mock(UserRepository::class.java)
        detailViewModel = DetailViewModel(userRepository)
    }

    @Test
    fun getDetailTest() {
        val responDetail = mockk<LiveData<Result<ResponseDetailUser>>>()
        `when`(userRepository.getDetail("hilmi")).thenReturn(responDetail)
        val resultDetail = detailViewModel.getDetail("hilmi")
        verify(userRepository).getDetail("hilmi")
        assertEquals(responDetail, resultDetail)
    }

    @Test
    fun getListFollowersTest() {
        val responFollowers = mockk<LiveData<Result<ResponseFollow>>>()
        `when`(userRepository.getFollowers("hilmi")).thenReturn(responFollowers)
        val resultFollowers = detailViewModel.getListFollowers("hilmi")
        verify(userRepository).getFollowers("hilmi")
        assertEquals(responFollowers, resultFollowers)
    }

    @Test
    fun getListFollowingTest() {
        val responFollowing = mockk<LiveData<Result<ResponseFollow>>>()
        `when`(userRepository.getFollowing("hilmi")).thenReturn(responFollowing)
        val resultFollowing = detailViewModel.getListFollowing("hilmi")
        verify(userRepository).getFollowing("hilmi")
        assertEquals(responFollowing, resultFollowing)
    }

    @Test
    fun checkFavoriteUserTest() {
        val responCheckFavorite = mockk<LiveData<UserFavorite>>()
        `when`(userRepository.checkFavorite("hilmi")).thenReturn(responCheckFavorite)
        val resultCheckFavorite = detailViewModel.checkFavoriteUser("hilmi")
        verify(userRepository).checkFavorite("hilmi")
        assertEquals(responCheckFavorite, resultCheckFavorite)
    }

}