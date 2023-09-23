package com.hilmisatrio.githubuserapp.ui.favorite

import androidx.lifecycle.LiveData
import com.hilmisatrio.githubuserapp.data.local.entity.UserFavorite
import com.hilmisatrio.githubuserapp.data.repository.UserRepository
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class FavoriteViewModelTest {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var userRepository: UserRepository

    @Before
    fun initial() {
        userRepository = Mockito.mock(UserRepository::class.java)
        favoriteViewModel = FavoriteViewModel(userRepository)
    }

    @Test
    fun getUsersFavoriteTest() {
        val respon = mockk<LiveData<List<UserFavorite>>>()
        `when`(userRepository.getFavorit()).thenReturn(respon)
        val result = favoriteViewModel.getUsersFavorite()
        verify(userRepository).getFavorit()
        assertEquals(respon, result)
    }
}