package com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upnext.scribble.common.Resource
import com.upnext.scribble.data.remote.Workspace
import com.upnext.scribble.data.repository.IWorkspaceRepository
import com.upnext.scribble.domain.model.CreateWorkspaceParams
import com.upnext.scribble.domain.repository.WorkspaceRepository
import com.upnext.scribble.presentation.navigation.HomeNavigation.CreateNewWorkspaceNavigation.CreateWorkspaceScreen.CreateWorkspaceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateWorkspaceViewModel @Inject constructor(
    private val workspaceRepository: IWorkspaceRepository
) : ViewModel() {

    val nameTextFieldState = mutableStateOf("")
    val workspaceImageUri = mutableStateOf<Uri?>(null)

    val createWorkspaceParamsState = mutableStateOf(CreateWorkspaceParams("",""))

    private val _createWorkspaceState = mutableStateOf(CreateWorkspaceState())
    val createWorkspaceState : State<CreateWorkspaceState> = _createWorkspaceState

    fun createWorkspace(params: CreateWorkspaceParams) = viewModelScope.launch {
        workspaceRepository.createWorkspace(params).collectLatest { result ->
            when(result){
                is Resource.Error -> _createWorkspaceState.value = CreateWorkspaceState(error = result.message ?: "Unknown Error")
                is Resource.Loading -> _createWorkspaceState.value = CreateWorkspaceState(loading = true)
                is Resource.Success -> _createWorkspaceState.value = CreateWorkspaceState(success = result.data ?: "")
            }
        }
    }

}