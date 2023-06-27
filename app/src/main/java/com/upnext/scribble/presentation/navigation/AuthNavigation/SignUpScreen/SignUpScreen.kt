package com.upnext.scribble.presentation.navigation.AuthNavigation.SignUpScreen

import android.util.Patterns
import android.widget.Toast
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.upnext.scribble.R
import com.upnext.scribble.domain.model.SignUpUserParams
import com.upnext.scribble.presentation.global_components.LoadingDialog
import com.upnext.scribble.presentation.navigation.AuthNavigation.AuthViewModel
import com.upnext.scribble.presentation.navigation.AuthNavigation.Routes.AuthNavRoutes
import com.upnext.scribble.presentation.navigation.AuthNavigation.components.AuthTextField
import com.upnext.scribble.presentation.navigation.AuthNavigation.components.GreenAuthButton
import com.upnext.scribble.presentation.navigation.AuthNavigation.components.SignUpWithGoogleButton
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme

@Composable
fun SignUpScreen(
    authVm: AuthViewModel,
    navController: NavController
) {

    val state = authVm.signUpState.value
    
    val context = LocalContext.current

    var loadingDialogState by rememberSaveable{
        mutableStateOf(false)
    }

    val isSignUpButtonEnabled = remember {
        mutableStateOf(false)
    }

    LoadingDialog(isDialogOpen = loadingDialogState)
    
    LaunchedEffect(
        key1 = authVm.signUpEmailTextState.value,
        key2 = authVm.signUpPasswordTextState.value,
        key3 = authVm.signUpConfirmPasswordTextState.value
    ){
        isSignUpButtonEnabled.value = authVm.signUpEmailTextState.value.isNotBlank() &&
                Patterns.EMAIL_ADDRESS.matcher(authVm.signUpEmailTextState.value).matches() &&
                authVm.signUpPasswordTextState.value.isNotBlank() &&
                authVm.signUpConfirmPasswordTextState.value.isNotBlank() &&
                authVm.signUpPasswordTextState.value == authVm.signUpConfirmPasswordTextState.value &&
                authVm.signUpPasswordTextState.value.length >= 6
    }

    LaunchedEffect(key1 = state.loading){
        loadingDialogState = state.loading
    }

    LaunchedEffect(key1 = state.error){
        if (state.error.isNotBlank()){
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(key1 = state.success){
        if (state.success.isNotBlank()){
            Toast.makeText(context, state.success, Toast.LENGTH_SHORT).show()
        }
    }


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {

            Spacer(modifier = Modifier.height(10.dp))

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
                text = stringResource(R.string.join_a_community_which_will_help_you_in_everyday_organizing),
                color = Color.Gray,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ){
                AuthTextField(
                    value = authVm.signUpEmailTextState.value,
                    onValueChange = { authVm.signUpEmailTextState.value = it },
                    label = stringResource(R.string.email),
                    inputType = KeyboardType.Email
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ){
                AuthTextField(
                    value = authVm.signUpPasswordTextState.value,
                    onValueChange = { authVm.signUpPasswordTextState.value = it },
                    label = stringResource(R.string.password),
                    inputType = KeyboardType.Password
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ){
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AuthTextField(
                        value = authVm.signUpConfirmPasswordTextState.value,
                        onValueChange = { authVm.signUpConfirmPasswordTextState.value = it },
                        label = stringResource(R.string.confirm_password),
                        inputType = KeyboardType.Password
                    )
                    Text(
                        text = "Password must be at least 6 characters long",
                        color = ScribbleTheme.colors.text
                    )
                }
            }


            Spacer(modifier = Modifier.height(100.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ){
                GreenAuthButton(text = stringResource(id = R.string.cont), isEnabled = isSignUpButtonEnabled.value) {
                    SignUpUserParams(
                        authVm.signUpEmailTextState.value,
                        authVm.signUpPasswordTextState.value,
                        false
                    ).let { signUpUserParams ->
                        authVm.signUp(signUpUserParams)
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
                        append("Already have an account? ")
                    }
                    withStyle(
                        SpanStyle(
                            color = Color.Gray
                        )
                    ){
                        append("Login")
                    }
                },
                modifier = Modifier.clickable {
                    navController.navigateUp()
                }
            )

            Spacer(modifier = Modifier.height(25.dp))

        }

    }

}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SignUpScreenPreview() {
    SignUpScreen(hiltViewModel(), rememberNavController())
}