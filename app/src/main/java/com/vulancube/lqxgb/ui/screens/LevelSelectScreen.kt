package com.vulancube.lqxgb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vulancube.lqxgb.data.GameLevel
import com.vulancube.lqxgb.data.GameLevels
import com.vulancube.lqxgb.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelSelectScreen(
    onBackClick: () -> Unit,
    onLevelSelect: (GameLevel) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Select Level",
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
                    containerColor = BlueSurface,
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
                        colors = listOf(BlueSurface, BlueBackground)
                    )
                )
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(GameLevels.levels) { level ->
                    LevelCard(level = level, onClick = { onLevelSelect(level) })
                }
            }
        }
    }
}

@Composable
fun LevelCard(level: GameLevel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = BlueCard)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(
                            level.primaryColor.copy(alpha = 0.3f),
                            level.secondaryColor.copy(alpha = 0.3f)
                        )
                    )
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Level ${level.id}",
                        fontSize = 14.sp,
                        color = GrayText
                    )
                    Text(
                        text = level.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = WhiteText
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = level.description,
                        fontSize = 14.sp,
                        color = GrayText
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row {
                        level.symbols.take(3).forEach { symbol ->
                            Text(
                                text = symbol,
                                fontSize = 28.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
