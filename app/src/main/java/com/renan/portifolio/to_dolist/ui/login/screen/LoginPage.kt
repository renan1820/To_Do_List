package com.renan.portifolio.to_dolist.ui.login.screen

import com.renan.portifolio.to_dolist.util.composable.UserInputText
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.renan.portifolio.to_dolist.R
import com.renan.portifolio.to_dolist.model.User
import com.renan.portifolio.to_dolist.ui.login.state.LoginState
import com.renan.portifolio.to_dolist.ui.login.viewmodel.LoginViewModel
import com.renan.portifolio.to_dolist.ui.theme.nunitoFontFamily
import com.renan.portifolio.to_dolist.util.composable.CustomButtonAnimate
import com.renan.portifolio.to_dolist.util.composable.InputSelector
import com.renan.portifolio.to_dolist.util.composable.LoadingIndicator
import com.renan.portifolio.to_dolist.util.composable.RetryContent
import kotlin.math.abs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedLoginScreen(expanded: Boolean = false, navController: NavController?, loginViewModel: LoginViewModel = koinViewModel()) {
    val state: LoginState = loginViewModel.state

    val pagerState = rememberPagerState(pageCount = { 4 })
    var expandedLayout by remember { mutableStateOf(expanded) }
    var isLooping by remember { mutableStateOf(true) }
    var animateSize by remember { mutableStateOf(false) }
    val transitionState = remember { MutableTransitionState(false) }
    val coroutineScope = rememberCoroutineScope()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val offsetY = remember { Animatable(0f) }
    var isAnimatingLogo by remember { mutableStateOf(false) }

    val targetPagerHeight by remember {
        derivedStateOf {
            if (expandedLayout) {
                screenHeight * 0.6f // 70% of screen height
            } else {
                screenHeight + (screenHeight * 0.15f)// Full screen height
            }
        }
    }

    var currentInputSelectorEmail by rememberSaveable { mutableStateOf(InputSelector.NONE) }
    var currentInputSelectorPassword by rememberSaveable { mutableStateOf(InputSelector.NONE) }
    val dismissKeyboardEmail = { currentInputSelectorEmail = InputSelector.NONE }
    val dismissKeyboardPassword = { currentInputSelectorPassword = InputSelector.NONE }


    if (currentInputSelectorEmail != InputSelector.NONE) {
        BackHandler(onBack = dismissKeyboardEmail)
    }
    if (currentInputSelectorPassword != InputSelector.NONE) {
        BackHandler(onBack = dismissKeyboardPassword)
    }

    var textStateEmail by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var textStatePassword by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var textFieldFocusStateEmail by remember { mutableStateOf(false) }
    var textFieldFocusStatePassword by remember { mutableStateOf(false) }
    
    val pagerHeight by animateDpAsState(
        targetValue = targetPagerHeight, // Adjust sizes as needed
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing), label = ""
    )

    val compositionLogo by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.android_logo))
    val colorScheme = MaterialTheme.colorScheme

    val images = listOf(
        R.drawable.img_quarto,
        R.drawable.img_cozinha,
        R.drawable.img_escritorio,
        R.drawable.travel_1
    )

    var startPosition by remember { mutableStateOf(Offset.Zero) }
    var endPosition by remember { mutableStateOf(Offset.Zero) }

    LaunchedEffect(animateSize) {
        transitionState.targetState = animateSize
        if (animateSize) {
            delay(450)
            animateSize = false
        }
    }

    LaunchedEffect(isLooping) {
        if(isLooping){
            while (true) {
                yield()
                delay(3000)
                coroutineScope.launch {
                    val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                    pagerState.animateScrollToPage(
                        page = nextPage,
                        animationSpec = tween(
                            durationMillis = 1000,
                            easing = FastOutSlowInEasing
                        )
                    )
                }
            }
        }
    }


    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        Column {
            Box {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .height(pagerHeight)
                ) { page ->
                    val imageResId = images[page % images.size]
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = "Image ${page % images.size + 1}",
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                            .background(
                                Color.Blue,
                                shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)
                            ),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .height(targetPagerHeight)
                        .fillMaxWidth()
                        .pointerInput(Unit) {
                            detectDragGestures(
                                onDragStart = { offset ->
                                    startPosition = offset
                                },
                                onDrag = { change, dragAmount -> endPosition = change.position },
                                onDragEnd = {

                                    val verifyDragDirectionX = startPosition.x - endPosition.x
                                    val verifyDragDirectionY = startPosition.y - endPosition.y

                                    val movingInHorizontalPosition =
                                        abs(verifyDragDirectionX) >= abs(verifyDragDirectionY)
                                    if (movingInHorizontalPosition) {
                                        if (expandedLayout) {
                                            val nextPage: Int = if (verifyDragDirectionX > 0) {
                                                (pagerState.currentPage + 1) % pagerState.pageCount
                                            } else {
                                                (pagerState.currentPage - 1) % pagerState.pageCount
                                            }
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(nextPage)
                                            }
                                        }
                                    } else {
                                        expandedLayout = !expandedLayout
                                        isLooping = !isLooping

                                        if (!isAnimatingLogo) {
                                            isAnimatingLogo = true
                                            coroutineScope.animateVerticalMovementInfinite(
                                                offsetY = offsetY
                                            )
                                        }
                                    }
                                }
                            )
                        }
                ){
                    AnimatedVisibility(
                        visible = expandedLayout,
                        enter = expandVertically() + fadeIn(),
                        exit = shrinkVertically() + fadeOut()
                    ){
                        Text(text = "Voltar", color = Color.White)
                        Spacer(modifier = Modifier.height(32.dp))
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colorScheme.background)

            ) {
                AnimatedVisibility(
                    visible = expandedLayout,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut(),
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    BootomSheetPagerLogin(
                        onTextFieldFocusedEmail = { focused ->
                                if (focused) {
                                    currentInputSelectorEmail = InputSelector.NONE
                                }
                            textFieldFocusStateEmail = focused

                        },
                        onTextFieldFocusedPassword = { focused ->
                                if (focused) {
                                    currentInputSelectorPassword = InputSelector.NONE
                                }
                            textFieldFocusStatePassword = focused
                            },
                        textFieldValueEmail = textStateEmail,
                        onTextChangedEmail = { textStateEmail = it },
                        textFieldValuePassword = textStatePassword,
                        onTextChangedPassword = { textStatePassword = it },
                        keyboardShownEmail = currentInputSelectorEmail == InputSelector.NONE && textFieldFocusStateEmail,
                        keyboardShownPassword = currentInputSelectorPassword == InputSelector.NONE && textFieldFocusStatePassword,
                        focusStateEmail = textFieldFocusStateEmail,
                        focusStatePassword = textFieldFocusStatePassword,
                        compositionLogo = compositionLogo,
                        onClickStart = {
                            Log.i("aux", "textStateEmail: ${textStateEmail.text} : textStatePassword: ${textStatePassword.text}")


                            loginViewModel.login(textStateEmail.text, textStatePassword.text)

                            if(state.user?.password.equals("123")){
                                Log.i("aux", "password: ${state.user?.password}")
                            }

                        }
                    )
                }
            }
        }


        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            ScreenElementsLoginFullPager(
                screenHeight = screenHeight,
                boxMaxHeight = maxHeight,
                pagerHeight = pagerHeight,
                expandedLayout = expandedLayout,
                onClickStart ={
                    expandedLayout = !expandedLayout
                    isLooping = !isLooping

                    if (!isAnimatingLogo) {
                        isAnimatingLogo = true
                        coroutineScope.animateVerticalMovementInfinite(
                            offsetY = offsetY
                        )
                    }
                },
                onClickRegister ={},
                state = state,
                onRetry = { loginViewModel.login(textStateEmail.text, textStatePassword.text)}
            )
        }
    }
}
@Composable
fun ScreenElementsLoginFullPager(
    screenHeight: Dp,
    boxMaxHeight: Dp,
    pagerHeight: Dp,
    expandedLayout: Boolean = true,
    onClickStart: () -> Unit,
    onClickRegister: () -> Unit,
    state: LoginState,
    onRetry: () -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Spacer(modifier = Modifier.height(pagerHeight * 0.1f))
        Image(
            painter = painterResource(id = R.drawable.sua_logo),
            contentDescription = "Image B",
            modifier = Modifier
                .size(pagerHeight * 0.25f),
            contentScale = ContentScale.Fit
        )
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = boxMaxHeight * 0.1f, start = 30.dp, end = 30.dp)
        ) {
            Text("Bem vindo", fontSize = 32.sp, fontFamily = nunitoFontFamily, fontWeight = FontWeight.ExtraBold, color = Color.White)
            Spacer(modifier = Modifier.height(screenHeight * 0.01f)) // Responsive spacer
            Text("Descubra mais",fontFamily = nunitoFontFamily, fontWeight = FontWeight.Thin, fontSize = 16.sp, color = Color.White)
        }
    }
    AnimatedVisibility(
        visible = !expandedLayout,
        enter = fadeIn(),
        exit = fadeOut()
    ){
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier.fillMaxSize()
        ){
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(30.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                CustomButtonAnimate(
                    text = "Iníciar",
                    colorText = Color.White,
                    height = 48.dp,
                    weight = 156.dp,
                    colorStroke = Color.White,
                    onClick = onClickStart)

                CustomButtonAnimate(
                    text = "Registrar",
                    colorText = Color.White,
                    height = 48.dp,
                    weight = 156.dp,
                    colorStroke = Color.White,
                    animatedStroke = false,
                    onClick = onClickRegister)
            }
        }
    }

    LoadingIndicator(
        isLoading = state.isLoading,
        error = state.error,
        onRetry = onRetry
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BootomSheetPagerLogin(
    onTextFieldFocusedEmail: (Boolean) -> Unit,
    onTextFieldFocusedPassword: (Boolean) -> Unit,
    textFieldValueEmail: TextFieldValue,
    onTextChangedEmail: (TextFieldValue) -> Unit,
    textFieldValuePassword: TextFieldValue,
    onTextChangedPassword: (TextFieldValue) -> Unit,
    keyboardShownEmail: Boolean,
    keyboardShownPassword: Boolean,
    focusStateEmail: Boolean,
    focusStatePassword: Boolean,
    compositionLogo: LottieComposition?,
    onClickStart: ()-> Unit
    ){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        UserInputText(
            textFieldValue = textFieldValueEmail,
            onTextChanged = onTextChangedEmail,
            keyboardType = KeyboardType.Email,
            keyboardShown = keyboardShownEmail,
            onTextFieldFocused = onTextFieldFocusedEmail,
            focusState = focusStateEmail,
            hintText = "E-mail"
        )

        Spacer(modifier = Modifier.height(8.dp))
        UserInputText(
            textFieldValue = textFieldValuePassword,
            onTextChanged = onTextChangedPassword,
            keyboardType = KeyboardType.Password,
            keyboardShown = keyboardShownPassword,
            onTextFieldFocused = onTextFieldFocusedPassword,
            focusState = focusStatePassword,
            visualTransformation = PasswordVisualTransformation(),
            hintText = "Senha"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text("Esqueci a senha", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, fontFamily = nunitoFontFamily, fontWeight = FontWeight.Light)
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp),
            shape = RoundedCornerShape(16.dp),
            onClick = onClickStart
        ) {
            Text("Iniciar Sessão",fontFamily = nunitoFontFamily, fontWeight = FontWeight.Bold, color = Color(0xFF48280E)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text("Developer Renan Castro", fontSize = 10.sp, color = Color.LightGray)

        }

        LottieAnimation(composition = compositionLogo,
            modifier = Modifier
                .size(100.dp)
                .fillMaxWidth(),
            iterations = LottieConstants.IterateForever)
    }
}

fun CoroutineScope.animateVerticalMovementInfinite(
    offsetY: Animatable<Float, *>,
    distance: Float = 5f,
    durationMillis: Int = 900
): Job {
    return launch {
        while (isActive) {
            offsetY.animateTo(
                targetValue = distance,
                animationSpec = tween(durationMillis = durationMillis)
            )
            offsetY.animateTo(
                targetValue = -distance,
                animationSpec = tween(durationMillis = durationMillis)
            )
        }
    }
}

@Preview
@Composable
fun PreviewPagerState(){
    AnimatedLoginScreen(
        expanded = false,
        navController = null,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLoadingIndicator(){
    LoadingIndicator(
        isLoading = true,
        error= "",
        onRetry = {}
    )
}