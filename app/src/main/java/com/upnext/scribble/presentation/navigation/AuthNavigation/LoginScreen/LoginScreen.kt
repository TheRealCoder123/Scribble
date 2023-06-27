package com.upnext.scribble.presentation.navigation.AuthNavigation.LoginScreen

import android.app.Activity.RESULT_OK
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.upnext.scribble.R
import com.upnext.scribble.domain.model.LoginUserParams
import com.upnext.scribble.presentation.global_components.LoadingDialog
import com.upnext.scribble.presentation.navigation.AuthNavigation.AuthViewModel
import com.upnext.scribble.presentation.navigation.AuthNavigation.GoogleSignIn.GoogleAuthUIClient
import com.upnext.scribble.presentation.navigation.AuthNavigation.Routes.AuthNavRoutes
import com.upnext.scribble.presentation.navigation.AuthNavigation.components.AuthTextField
import com.upnext.scribble.presentation.navigation.AuthNavigation.components.GreenAuthButton
import com.upnext.scribble.presentation.navigation.AuthNavigation.components.SignUpWithGoogleButton
import com.upnext.scribble.presentation.navigation.MainNavigation.Routes.MainGraphRoutes
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun LoginScreen(
    authVm: AuthViewModel,
    navController: NavController
) {


    val state = authVm.loginState.value
    val googleSignInState = authVm.googleSignInState.collectAsState().value
    val firestoreGoogleSignInState = authVm.firestoreGoogleSignInState.value

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val isLoginButtonEnabled = remember {
        mutableStateOf(false)
    }

    var loadingDialogState by rememberSaveable {
        mutableStateOf(false)
    }

    val googleAuthUIClient = GoogleAuthUIClient(context, Identity.getSignInClient(context))


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult(),
        onResult = { result ->
            if (result.resultCode == RESULT_OK) {
                scope.launch {
                    val signInResult = googleAuthUIClient.signInWithIntent(
                        intent = result.data ?: return@launch
                    )
                    authVm.onSignInResult(signInResult)
                    loadingDialogState = true
                }
            }
        }
    )

    LaunchedEffect(key1 = firestoreGoogleSignInState.error){
        if (firestoreGoogleSignInState.error.isNotBlank()){
            Toast.makeText(context, firestoreGoogleSignInState.error, Toast.LENGTH_SHORT).show()
            loadingDialogState = false
        }
    }

    LaunchedEffect(key1 = firestoreGoogleSignInState.success){
        if (firestoreGoogleSignInState.success.isNotBlank()){
            Toast.makeText(context, firestoreGoogleSignInState.success, Toast.LENGTH_SHORT).show()
            loadingDialogState = false
            navController.popBackStack(MainGraphRoutes.AuthRoute.route, inclusive = true)
            navController.navigate(MainGraphRoutes.HomeRoute.route)
            authVm.resetLoginState()
            authVm.resetGoogleSignInState()
        }
    }

    LaunchedEffect(key1 = googleSignInState.isSignInSuccessful){
        if (googleSignInState.isSignInSuccessful){
            googleSignInState.data?.let { user->
                authVm.setDataToFirestoreGoogleSignIn(user)
            }
        }
    }

    LaunchedEffect(key1 = googleSignInState.signInError){
        googleSignInState.signInError?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            authVm.resetGoogleSignInState()
        }
    }

    LaunchedEffect(key1 = authVm.loginEmailTextState.value, key2 = authVm.loginPasswordTextState.value){
        isLoginButtonEnabled.value = authVm.loginEmailTextState.value.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(authVm.loginEmailTextState.value).matches() &&
                authVm.loginPasswordTextState.value.isNotBlank()
    }

    LaunchedEffect(key1 = state.loading){
        loadingDialogState = state.loading
    }

    LaunchedEffect(key1 = state.error){
        if (state.error.isNotBlank()){
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
            authVm.resetLoginState()
        }
    }

    LaunchedEffect(key1 = state.success){
        if (state.success.isNotBlank()){
            Toast.makeText(context, state.success, Toast.LENGTH_SHORT).show()
            navController.popBackStack(MainGraphRoutes.AuthRoute.route, inclusive = true)
            navController.navigate(MainGraphRoutes.HomeRoute.route)
            authVm.resetGoogleSignInState()
            authVm.resetLoginState()
        }
    }

    LoadingDialog(isDialogOpen = loadingDialogState)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        item {

            Spacer(modifier = Modifier.height(45.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.scribble_logo),
                    contentDescription = stringResource(R.string.logo),
                )
                Spacer(modifier = Modifier.width(ScribbleTheme.spaces.large))
                Text(
                    text = stringResource(id = R.string.app_name),
                    color = ScribbleTheme.colors.text,
                    fontSize = 25.sp
                )
            }

            Spacer(modifier = Modifier.height(25.dp))


            Text(
                text = stringResource(R.string.organize_without_limits),
                color = Color.Gray,
                fontSize = 18.sp
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ){
                AuthTextField(
                    value = authVm.loginEmailTextState.value,
                    onValueChange = { authVm.loginEmailTextState.value = it },
                    label = stringResource(R.string.email),
                    inputType = KeyboardType.Email
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ){
                Column {
                    AuthTextField(
                        value = authVm.loginPasswordTextState.value,
                        onValueChange = { authVm.loginPasswordTextState.value = it },
                        label = stringResource(R.string.password),
                        inputType = KeyboardType.Password
                    )
                    Text(
                        text = stringResource(R.string.forgot_password),
                        color = ScribbleTheme.colors.text,
                        modifier = Modifier.clickable {
                            navController.navigate(AuthNavRoutes.ForgotPasswordScreen.route)
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(100.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ){
                GreenAuthButton(text = stringResource(id = R.string.cont), isEnabled = isLoginButtonEnabled.value) {
                    LoginUserParams(
                        authVm.loginEmailTextState.value,
                        authVm.loginPasswordTextState.value
                    ).let {params ->
                        authVm.login(params)
                    }
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(
                text = stringResource(R.string.or),
                color = ScribbleTheme.colors.text
            )

            Spacer(modifier = Modifier.height(15.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ){
                SignUpWithGoogleButton {
                    scope.launch {
                        val signInIntentSender = googleAuthUIClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntentSender ?: return@launch
                            ).build()
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            color = ScribbleTheme.colors.text
                        )
                    ){
                        append("New to Scribble? ")
                    }
                    withStyle(
                        SpanStyle(
                            color = Color.Gray
                        )
                    ){
                        append("Sign Up")
                    }
                },
                modifier = Modifier.clickable {
                    navController.navigate(AuthNavRoutes.SignUpRoute.route)
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun LoginScreenPreview() {
    LoginScreen(hiltViewModel(), rememberNavController())
}