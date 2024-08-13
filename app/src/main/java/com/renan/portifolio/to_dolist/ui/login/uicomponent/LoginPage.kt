package com.renan.portifolio.to_dolist.ui.login.uicomponent

import UserInputText
import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.renan.portifolio.to_dolist.R
import com.renan.portifolio.to_dolist.ui.theme.nunitoFontFamily
import com.renan.portifolio.to_dolist.util.CustomButtonAnimate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield

//navController:NavController? , loginViewModel: LoginViewModel = koinViewModel()

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimatedLoginScreen(expanded: Boolean = false) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    var expandedLayout by remember { mutableStateOf(expanded) }
    var isLooping by remember { mutableStateOf(true) }
    var animateSize by remember { mutableStateOf(false) }
    val transitionState = remember { MutableTransitionState(false) }
    val coroutineScope = rememberCoroutineScope()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val offsetY = remember { Animatable(0f) }
    var isAnimatingLogo by remember { mutableStateOf(false) }
    var animationJob by remember { mutableStateOf<Job?>(null) }

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
    var currentInputSelector by rememberSaveable { mutableStateOf(InputSelector.NONE) }
    val dismissKeyboardEmail = { currentInputSelectorEmail = InputSelector.NONE }
    val dismissKeyboard = { currentInputSelector = InputSelector.NONE }


    if (currentInputSelectorEmail != InputSelector.NONE) {
        BackHandler(onBack = dismissKeyboardEmail)
    }
    if (currentInputSelector != InputSelector.NONE) {
        BackHandler(onBack = dismissKeyboard)
    }

    var textStateEmail by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }
    var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue())
    }

    var textFieldFocusStateEmail by remember { mutableStateOf(false) }
    var textFieldFocusState by remember { mutableStateOf(false) }


    val pagerHeight by animateDpAsState(
        targetValue = targetPagerHeight, // Adjust sizes as needed
        animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing), label = ""
    )
    val compositionLogo by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.android_logo))
    val colorScheme = MaterialTheme.colorScheme

    val images = listOf(
        R.drawable.img_quarto,
        R.drawable.img_cozinha,
        R.drawable.img_escritorio
    )

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
                        .clickable {
                            expandedLayout = !expandedLayout
                            isLooping = !isLooping

                            if (!isAnimatingLogo) {
                                isAnimatingLogo = true
                                animationJob = coroutineScope.animateVerticalMovementInfinite(
                                    offsetY = offsetY
                                )
                            } else {
                                animationJob?.cancel() // Cancel the animation
                                isAnimatingLogo = false
                            }
                        }
                ) { page ->
                    val imageResId = images[page % images.size]
                    Image(
                        painter = painterResource(id = imageResId),
                        contentDescription = "Image ${page % images.size + 1}",
                        modifier = Modifier.fillMaxSize()
                            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                            .background(Color.Blue, shape = RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp)),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier
                        .height(targetPagerHeight)
                        .fillMaxWidth()
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
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        UserInputText(
                            textFieldValue = textStateEmail,
                            onTextChanged = { textStateEmail = it },
                            keyboardType = KeyboardType.Email,
                            keyboardShown = currentInputSelectorEmail == InputSelector.NONE && textFieldFocusStateEmail,
                            onTextFieldFocused = { focused ->
                                if (focused) {
                                    currentInputSelectorEmail = InputSelector.NONE
                                }
                                textFieldFocusStateEmail = focused
                            },
                            focusState = textFieldFocusStateEmail,
                            hintText = "E-mail"
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                        UserInputText(
                            textFieldValue = textState,
                            onTextChanged = { textState = it },
                            keyboardType = KeyboardType.Password,
                            keyboardShown = currentInputSelector == InputSelector.NONE && textFieldFocusState,
                            onTextFieldFocused = { focused ->
                                if (focused) {
                                    currentInputSelector = InputSelector.NONE
                                }
                                textFieldFocusState = focused
                            },
                            focusState = textFieldFocusState,
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
                            onClick = {  }
                        ) {
                            Text("Iniciar Sessão",fontFamily = nunitoFontFamily, fontWeight = FontWeight.Bold, color = colorScheme.primary
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
            }

        }

        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            val screenWidth = maxWidth
            val boxMaxHeight = maxHeight

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.sua_logo),
                    contentDescription = "Image B",
                    modifier = Modifier
                        .size(boxMaxHeight * 0.3f)
                        .padding(boxMaxHeight * 0.05f),
                    contentScale = ContentScale.Fit

                )

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = boxMaxHeight * 0.1f, start = 30.dp)
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
                            onClick = {
                                expandedLayout = !expandedLayout
                                isLooping = !isLooping

                                if (!isAnimatingLogo) {
                                    isAnimatingLogo = true
                                    coroutineScope.animateVerticalMovementInfinite(
                                        offsetY = offsetY
                                    )
                                }
                        })

                        CustomButtonAnimate(
                            text = "Registrar",
                            colorText = Color.White,
                            height = 48.dp,
                            weight = 156.dp,
                            colorStroke = Color.White,
                            animatedStroke = false,
                            onClick = {

                            })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewInputsLoginScreen() {
    AnimatedLoginScreen()
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewBreathingAnimationOnButtonClick() {
    AnimatedLoginScreen(true)
}

//@Preview
//@Composable
//fun PreviewBootomSheetPagerLogin(){
//    BootomSheetPagerLogin(
//        onTextFieldFocusedEmail = ,
//        onTextFieldFocusedPassword = ,
//        textFieldValueEmail = ,
//        textFieldValuePassword = ,
//        onTextChangedPassword = ,
//        keyboardShown = ,
//        focusStateEmail = ,
//        focusStatePassword = ,
//        compositionLogo =
//    )
//}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BootomSheetPagerLogin(
    onTextFieldFocusedEmail: (Boolean) -> Unit,
    onTextFieldFocusedPassword: (Boolean) -> Unit,
    textFieldValueEmail: TextFieldValue,
    onTextChangedEmail: (TextFieldValue) -> Unit,
    textFieldValuePassword: TextFieldValue,
    onTextChangedPassword: (TextFieldValue) -> Unit,
    keyboardShown: Boolean,
    focusStateEmail: Boolean,
    focusStatePassword: Boolean,
    compositionLogo: LottieComposition
    ){
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)){
        UserInputText(
            textFieldValue = textFieldValueEmail,
            onTextChanged = onTextChangedEmail,
            keyboardType = KeyboardType.Email,
            keyboardShown = keyboardShown,
            onTextFieldFocused = onTextFieldFocusedEmail,
            focusState = focusStateEmail,
            hintText = "E-mail"
        )

        Spacer(modifier = Modifier.height(8.dp))
        UserInputText(
            textFieldValue = textFieldValuePassword,
            onTextChanged = onTextChangedPassword,
            keyboardType = KeyboardType.Password,
            keyboardShown = keyboardShown,
            onTextFieldFocused = onTextFieldFocusedPassword,
            focusState = focusStatePassword,
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
            onClick = {  }
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
