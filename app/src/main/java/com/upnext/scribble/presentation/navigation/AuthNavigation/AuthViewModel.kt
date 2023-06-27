package com.upnext.scribble.presentation.navigation.AuthNavigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.upnext.scribble.common.Resource
import com.upnext.scribble.data.remote.User
import com.upnext.scribble.data.repository.IAuthRepository
import com.upnext.scribble.domain.model.LoginUserParams
import com.upnext.scribble.domain.model.SignUpUserParams
import com.upnext.scribble.domain.repository.AuthRepository
import com.upnext.scribble.presentation.navigation.AuthNavigation.GoogleSignIn.GoogleSignInState
import com.upnext.scribble.presentation.navigation.AuthNavigation.GoogleSignIn.SignInResult
import com.upnext.scribble.presentation.navigation.AuthNavigation.States.ForgotPasswordState
import com.upnext.scribble.presentation.navigation.AuthNavigation.States.LoginState
import com.upnext.scribble.presentation.navigation.AuthNavigation.States.SignUpState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: IAuthRepository
) : ViewModel() {

    val loginEmailTextState = mutableStateOf("")
    val loginPasswordTextState = mutableStateOf("")

    val signUpEmailTextState = mutableStateOf("")
    val signUpPasswordTextState = mutableStateOf("")
    val signUpConfirmPasswordTextState = mutableStateOf("")

    val forgotPasswordEmailTextState = mutableStateOf("")


    private val _loginState = mutableStateOf(LoginState())
    val loginState : State<LoginState> = _loginState

    private val _signUpState = mutableStateOf(SignUpState())
    val signUpState : State<SignUpState> = _signUpState

    private val _forgotPasswordState = mutableStateOf(ForgotPasswordState())
    val forgotPasswordState : State<ForgotPasswordState> = _forgotPasswordState

    private val _firestoreGoogleSignInState = mutableStateOf(LoginState())
    val firestoreGoogleSignInState : State<LoginState> = _firestoreGoogleSignInState

    private val _googleSignInState = MutableStateFlow(GoogleSignInState())
    val googleSignInState  = _googleSignInState.asStateFlow()

    fun setDataToFirestoreGoogleSignIn(user: User) = viewModelScope.launch {
        authRepository.signInWithGoogleUserData(user).collectLatest {
            when(it){
                is Resource.Error -> _firestoreGoogleSignInState.value = LoginState(error = it.message ?: "Unknown Error")
                is Resource.Loading -> _firestoreGoogleSignInState.value = LoginState(loading = true)
                is Resource.Success -> _firestoreGoogleSignInState.value = LoginState(success = it.data ?: "aaa")
            }
        }
    }

    fun onSignInResult(result: SignInResult) {
        _googleSignInState.update {it.copy(
            data = result.data,
            isSignInSuccessful = result.data != null,
            signInError = result.errorMessage
        )}
    }

    fun resetGoogleSignInState() {
        _googleSignInState.update { GoogleSignInState() }
    }


    fun login(params: LoginUserParams) = viewModelScope.launch {
        authRepository.loginUser(params).collectLatest {
            when(it){
                is Resource.Error -> _loginState.value = LoginState(error = it.message ?: "Unknown error")
                is Resource.Loading -> _loginState.value = LoginState(loading = true)
                is Resource.Success -> _loginState.value = LoginState(success = it.data ?: "")
            }
        }
    }

    fun  resetLoginState() {
        _loginState.value = LoginState()
    }

    fun signUp(params: SignUpUserParams) = viewModelScope.launch {
        authRepository.signUpUser(params).collectLatest {
            when(it){
                is Resource.Error -> _signUpState.value = SignUpState(error = it.message ?: "Unknown error")
                is Resource.Loading -> _signUpState.value = SignUpState(loading = true)
                is Resource.Success -> _signUpState.value = SignUpState(success = it.data ?: "")
            }
        }
    }

    fun  resetSignUpState() {
        _signUpState.value = SignUpState()
    }

    fun forgotPassword(email: String) = viewModelScope.launch {
        authRepository.forgotPassword(email).collectLatest {
            when(it){
                is Resource.Error -> _forgotPasswordState.value = ForgotPasswordState(error = it.message ?: "Unknown error")
                is Resource.Loading -> _forgotPasswordState.value = ForgotPasswordState(loading = true)
                is Resource.Success -> _forgotPasswordState.value = ForgotPasswordState(success = it.data ?: "")
            }
        }
    }

    fun  resetForgotPasswordState() {
        _forgotPasswordState.value = ForgotPasswordState()
    }


}