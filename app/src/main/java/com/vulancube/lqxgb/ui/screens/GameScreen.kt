package com.vulancube.lqxgb.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vulancube.lqxgb.data.AppViewModel
import com.vulancube.lqxgb.data.GameLevel
import com.vulancube.lqxgb.ui.theme.*
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameScreen(
    level: GameLevel,
    viewModel: AppViewModel,
    onBackClick: () -> Unit
) {
    val balance by viewModel.balance.collectAsState()
    var reelCount by remember { mutableIntStateOf(3) }
    var currentSymbols by remember { mutableStateOf(List(3) { level.symbols.random() }) }
    var isRolling by remember { mutableStateOf(false) }
    var currentCost by remember { mutableIntStateOf(100) }
    var lastWin by remember { mutableIntStateOf(0) }
    var showResult by remember { mutableStateOf(false) }

    val reelOptions = listOf(3, 4, 5)
    val costOptions = listOf(100, 250, 500, 1000)

    LaunchedEffect(reelCount) {
        if (!isRolling) {
            currentSymbols = List(reelCount) { level.symbols.random() }
        }
    }

    LaunchedEffect(isRolling) {
        if (isRolling) {
            repeat(20) {
                currentSymbols = List(reelCount) { level.symbols.random() }
                delay(80)
            }
            val finalSymbols = List(reelCount) { level.symbols.random() }
            currentSymbols = finalSymbols

            val win = calculateWin(finalSymbols, level, currentCost)
            lastWin = win
            if (win > 0) {
                viewModel.updateBalance(win)
            }
            showResult = true
            isRolling = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = level.title,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = level.primaryColor,
                    titleContentColor = WhiteText,
                    navigationIconContentColor = WhiteText
                )
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            level.primaryColor.copy(alpha = 0.8f),
                            BlueBackground,
                            level.secondaryColor.copy(alpha = 0.3f)
                        )
                    )
                )
                .padding(padding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = BlueCard)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Balance", fontSize = 14.sp, color = GrayText)
                            Text(
                                text = balance.toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldAccent
                            )
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "Last Win", fontSize = 14.sp, color = GrayText)
                            Text(
                                text = if (showResult) lastWin.toString() else "-",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (lastWin > 0) GoldAccent else GrayText
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .border(4.dp, GoldAccent, RoundedCornerShape(20.dp)),
                    colors = CardDefaults.cardColors(containerColor = BlueSurface)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 24.dp, horizontal = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            val slotSize = when (reelCount) {
                                3 -> 90.dp
                                4 -> 75.dp
                                else -> 62.dp
                            }
                            val fontSize = when (reelCount) {
                                3 -> 48.sp
                                4 -> 40.sp
                                else -> 32.sp
                            }
                            currentSymbols.forEachIndexed { index, symbol ->
                                ReelSlot(
                                    symbol = symbol,
                                    isRolling = isRolling,
                                    delay = index * 100,
                                    primaryColor = level.primaryColor,
                                    size = slotSize,
                                    fontSize = fontSize
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                if (showResult && lastWin > 0) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = GoldAccent.copy(alpha = 0.2f)
                        )
                    ) {
                        Text(
                            text = "WIN: $lastWin",
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold,
                            color = GoldAccent,
                            modifier = Modifier.padding(horizontal = 32.dp, vertical = 12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "Reels Count",
                    fontSize = 16.sp,
                    color = GrayText
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    reelOptions.forEach { count ->
                        FilterChip(
                            selected = reelCount == count,
                            onClick = { if (!isRolling) reelCount = count },
                            label = {
                                Text(
                                    text = count.toString(),
                                    fontWeight = if (reelCount == count) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = level.secondaryColor,
                                selectedLabelColor = WhiteText
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Select Amount",
                    fontSize = 16.sp,
                    color = GrayText
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    costOptions.forEach { cost ->
                        FilterChip(
                            selected = currentCost == cost,
                            onClick = { if (!isRolling) currentCost = cost },
                            label = {
                                Text(
                                    text = cost.toString(),
                                    fontWeight = if (currentCost == cost) FontWeight.Bold else FontWeight.Normal
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = level.primaryColor,
                                selectedLabelColor = WhiteText
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        if (!isRolling && balance >= currentCost) {
                            viewModel.updateBalance(-currentCost)
                            showResult = false
                            lastWin = 0
                            isRolling = true
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = level.primaryColor,
                        disabledContainerColor = GrayText.copy(alpha = 0.3f)
                    ),
                    enabled = !isRolling && balance >= currentCost
                ) {
                    Text(
                        text = if (isRolling) "..." else "ROLL",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ReelSlot(
    symbol: String,
    isRolling: Boolean,
    delay: Int,
    primaryColor: androidx.compose.ui.graphics.Color,
    size: androidx.compose.ui.unit.Dp = 90.dp,
    fontSize: androidx.compose.ui.unit.TextUnit = 48.sp
) {
    val infiniteTransition = rememberInfiniteTransition(label = "reel")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (isRolling) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(300, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )

    Card(
        modifier = Modifier
            .size(size)
            .graphicsLayer {
                if (isRolling) {
                    rotationX = rotation
                }
            },
        colors = CardDefaults.cardColors(
            containerColor = primaryColor.copy(alpha = 0.3f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = symbol,
                fontSize = fontSize,
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun calculateWin(symbols: List<String>, level: GameLevel, cost: Int): Int {
    val uniqueSymbols = symbols.distinct()
    val totalCount = symbols.size

    return when {
        uniqueSymbols.size == 1 -> cost * level.multipliers[4]
        uniqueSymbols.size == 2 && symbols.groupingBy { it }.eachCount().values.max() >= totalCount - 1 -> cost * level.multipliers[2]
        uniqueSymbols.size == 2 -> cost * level.multipliers[1]
        Random.nextFloat() < 0.12f -> cost * level.multipliers[0]
        else -> 0
    }
}
