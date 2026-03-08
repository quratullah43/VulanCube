package com.vulancube.lqxgb.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vulancube.lqxgb.data.AppViewModel
import com.vulancube.lqxgb.ui.theme.*

@Composable
fun MainMenuScreen(
    viewModel: AppViewModel,
    onPlayClick: () -> Unit,
    onPolicyClick: () -> Unit
) {
    val balance by viewModel.balance.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(BlueBackground, BlueSurface, BlueBackground)
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = "VULAN",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = GoldAccent
            )

            Text(
                text = "CUBE",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = BlueLight
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = BlueCard)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Your Balance",
                        fontSize = 16.sp,
                        color = GrayText
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = balance.toString(),
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldAccent
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Button(
                onClick = onPlayClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(64.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = BluePrimary
                )
            ) {
                Text(
                    text = "PLAY",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            val policyLink = viewModel.getPolicyLink()
            if (!policyLink.isNullOrEmpty()) {
                TextButton(
                    onClick = onPolicyClick,
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(
                        text = "Privacy Policy",
                        fontSize = 14.sp,
                        color = GrayText,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
