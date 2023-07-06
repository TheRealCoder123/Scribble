package com.upnext.scribble.presentation.navigation.HomeNavigation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upnext.scribble.common.Resource
import com.upnext.scribble.data.remote.Workspace
import com.upnext.scribble.data.repository.IBoardRepository
import com.upnext.scribble.data.repository.IUserRepository
import com.upnext.scribble.data.repository.IWorkspaceRepository
import com.upnext.scribble.domain.model.HomeWorkspaceSections
import com.upnext.scribble.domain.use_case.auth_use_cases.GetSignedInUserUIDUseCase
import com.upnext.scribble.domain.use_case.board_use_cases.GetBoardsByAllWorkspacesUseCase
import com.upnext.scribble.presentation.navigation.HomeNavigation.States.BoardsState
import com.upnext.scribble.presentation.navigation.HomeNavigation.States.HomeScreenDisplayState
import com.upnext.scribble.presentation.navigation.HomeNavigation.States.HomeWorkspaceSectionsState
import com.upnext.scribble.presentation.navigation.HomeNavigation.States.UserState
import com.upnext.scribble.presentation.navigation.HomeNavigation.States.WorkspacesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: IUserRepository,
    private val workspaceRepository: IWorkspaceRepository,
    private val boardRepository: IBoardRepository,
    private val uid: GetSignedInUserUIDUseCase,
    private val getBoardsByAllWorkspacesUseCase: GetBoardsByAllWorkspacesUseCase,
) : ViewModel() {


    private val _userState = mutableStateOf(UserState())
    val userState : State<UserState> = _userState

    private val _workspaceState = mutableStateOf(WorkspacesState())
    val workspaceState : State<WorkspacesState> = _workspaceState

    private val _boardsState = mutableStateOf(BoardsState())
    val boardsState : State<BoardsState> = _boardsState

    private val _homeWorkspaceSections = mutableStateOf(HomeWorkspaceSectionsState())
    val homeWorkspaceSections : State<HomeWorkspaceSectionsState> = _homeWorkspaceSections

    val dataDisplayState = mutableStateOf<HomeScreenDisplayState>(HomeScreenDisplayState.ALL_BOARDS)
    val selectedWorkspace = mutableStateOf<Workspace?>(null)

    init {
        getUserData()
        getWorkspaces()
    }

    private fun getUserData() = viewModelScope.launch {
        uid()?.let { uid ->
            userRepository.getUserData(uid).collectLatest {result->
                when(result){
                    is Resource.Error -> _userState.value = UserState(error = result.message ?: "Unknown error")
                    is Resource.Loading -> _userState.value = UserState(loading = true)
                    is Resource.Success -> _userState.value = UserState(user = result.data)
                }
            }
        }
    }

    fun getBoards() = viewModelScope.launch {
        selectedWorkspace.value?.let { workspace ->
            boardRepository.getBoardsByWorkspace(workspace.workspaceId).collectLatest { result ->
                when(result){
                    is Resource.Error -> _boardsState.value = BoardsState(error = result.message ?: "Unknown error")
                    is Resource.Loading -> _boardsState.value = BoardsState(loading = true)
                    is Resource.Success -> {
                        _boardsState.value = BoardsState(boards = result.data ?: emptyList())
                        _homeWorkspaceSections.value = HomeWorkspaceSectionsState()
                    }
                }
            }
        }
    }

    fun getBoardsByAllWorkspaces() = viewModelScope.launch {
        boardRepository.getAllBoards(_workspaceState.value.data).collectLatest{ result ->
            when(result){
                is Resource.Error -> {
                    Log.e("error in get boards", "${result.message}")
                }
                is Resource.Loading -> {

                }
                is Resource.Success -> {
                    _homeWorkspaceSections.value = HomeWorkspaceSectionsState(result.data ?: emptyList())
                    _boardsState.value = BoardsState()
                }
            }
        }
    }



    private fun getWorkspaces() = viewModelScope.launch {
        workspaceRepository.getWorkspaces().collectLatest {result->
            when(result){
                is Resource.Error -> _workspaceState.value = WorkspacesState(error = result.message ?: "Unknown error")
                is Resource.Loading -> _workspaceState.value = WorkspacesState(loading = true)
                is Resource.Success -> {
                    _workspaceState.value = WorkspacesState(data = result.data ?: emptyList())
                }
            }
        }
    }


}