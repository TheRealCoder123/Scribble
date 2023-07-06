package com.upnext.scribble.presentation.navigation.HomeNavigation.CreateBoardScreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upnext.scribble.common.Resource
import com.upnext.scribble.data.remote.Workspace
import com.upnext.scribble.data.repository.IBoardRepository
import com.upnext.scribble.data.repository.IWorkspaceRepository
import com.upnext.scribble.domain.model.CreateBoardParams
import com.upnext.scribble.presentation.navigation.HomeNavigation.States.WorkspacesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateBoardScreenViewModel @Inject constructor(
    private val workspaceRepository: IWorkspaceRepository,
    private val boardRepository: IBoardRepository
    ) : ViewModel() {

    val boardNameTextState = mutableStateOf("")

    val selectedWorkspace = mutableStateOf<Workspace?>(null)

    private val _workspaceState = mutableStateOf(WorkspacesState())
    val workspaceState : State<WorkspacesState> = _workspaceState

    private val _createBoardState = mutableStateOf(CreateBoardState())
    val createBoardState : State<CreateBoardState> = _createBoardState

    init {
        getWorkspaces()
    }

    fun createBoard(params: CreateBoardParams) = viewModelScope.launch {
        boardRepository.createBoard(params).collectLatest { response ->
            when(response){
                is Resource.Error -> _createBoardState.value = CreateBoardState(error = response.message ?: "Unknown Error")
                is Resource.Loading -> _createBoardState.value = CreateBoardState(loading = true)
                is Resource.Success -> _createBoardState.value = CreateBoardState(response.data ?: "")
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