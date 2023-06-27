@file:OptIn(ExperimentalMaterial3Api::class)

package com.upnext.scribble.presentation.navigation.AuthNavigation.ForgotPassword

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.upnext.scribble.R
import com.upnext.scribble.presentation.global_components.LoadingDialog
import com.upnext.scribble.presentation.navigation.AuthNavigation.AuthViewModel
import com.upnext.scribble.presentation.navigation.AuthNavigation.Routes.AuthNavRoutes
import com.upnext.scribble.presentation.navigation.AuthNavigation.components.AuthTextField
import com.upnext.scribble.presentation.navigation.AuthNavigation.components.GreenAuthButton
import com.upnext.scribble.presentation.navigation.MainNavigation.Routes.MainGraphRoutes
import com.upnext.scribble.presentation.ui.theme.ScribbleTheme

@Composable
fun ForgotPasswordScreen(
    authVm: AuthViewModel,
    navController: NavHostController
) {

    val state = authVm.forgotPasswordState.value

    val context = LocalContext.current

    var isContinueButtonEnabled by remember {
        mutableStateOf(false)
    }

    var loadingDialogState by rememberSaveable {
        mutableStateOf(false)
    }

    LoadingDialog(isDialogOpen = loadingDialogState)

    LaunchedEffect(key1 = authVm.forgotPasswordEmailTextState.value){
        isContinueButtonEnabled = Patterns.EMAIL_ADDRESS.matcher(authVm.forgotPasswordEmailTextState.value).matches()
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
            navController.navigateUp()
        }
    }


    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {


            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.forgot_password_no_question_mark), color = ScribbleTheme.colors.text)
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = ScribbleTheme.colors.background
                ),
                navigationIcon = {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.go_back),
                        modifier = Modifier.clickable {
                            navController.navigateUp()
                        },
                        tint = ScribbleTheme.colors.text
                    )
                }
            )

            Spacer(modifier = Modifier.height(25.dp))


            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ){
                Column {
                    AuthTextField(
                        value = authVm.forgotPasswordEmailTextState.value,
                        onValueChange = { authVm.forgotPasswordEmailTextState.value = it },
                        label = stringResource(R.string.email),
                        inputType = KeyboardType.Email
                    )

                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ){
                GreenAuthButton(text = stringResource(id = R.string.cont), isEnabled = isContinueButtonEnabled) {
                    authVm.forgotPassword(authVm.forgotPasswordEmailTextState.value)
                }
            }


        }

    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen(hiltViewModel(), rememberNavController())
}