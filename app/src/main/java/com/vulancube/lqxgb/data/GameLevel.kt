package com.vulancube.lqxgb.data

import androidx.compose.ui.graphics.Color
import com.vulancube.lqxgb.ui.theme.*

data class GameLevel(
    val id: Int,
    val title: String,
    val description: String,
    val primaryColor: Color,
    val secondaryColor: Color,
    val symbols: List<String>,
    val multipliers: List<Int>,
    val isUnlocked: Boolean = true
)

object GameLevels {
    val levels = listOf(
        GameLevel(
            id = 1,
            title = "Space Adventure",
            description = "Explore the cosmos",
            primaryColor = SpaceThemePrimary,
            secondaryColor = SpaceThemeSecondary,
            symbols = listOf("🚀", "🌟", "🪐", "👽", "🛸", "☄️"),
            multipliers = listOf(2, 3, 5, 10, 25)
        ),
        GameLevel(
            id = 2,
            title = "Ocean Depths",
            description = "Dive into the sea",
            primaryColor = OceanThemePrimary,
            secondaryColor = OceanThemeSecondary,
            symbols = listOf("🐬", "🦈", "🐙", "🦀", "🐚", "⚓"),
            multipliers = listOf(3, 5, 8, 15, 30)
        ),
        GameLevel(
            id = 3,
            title = "Jungle Quest",
            description = "Wild treasures await",
            primaryColor = JungleThemePrimary,
            secondaryColor = JungleThemeSecondary,
            symbols = listOf("🦁", "🐯", "🦎", "🌴", "💎", "🗿"),
            multipliers = listOf(4, 6, 10, 20, 40)
        ),
        GameLevel(
            id = 4,
            title = "Pyramid Secrets",
            description = "Ancient mysteries",
            primaryColor = PyramidThemePrimary,
            secondaryColor = PyramidThemeSecondary,
            symbols = listOf("🏺", "👁", "🐪", "☀️", "🔱", "⚱️"),
            multipliers = listOf(5, 8, 12, 25, 50)
        ),
        GameLevel(
            id = 5,
            title = "Crystal Realm",
            description = "Magical crystals",
            primaryColor = CrystalThemePrimary,
            secondaryColor = CrystalThemeSecondary,
            symbols = listOf("💜", "🔮", "✨", "🌙", "👑", "💫"),
            multipliers = listOf(6, 10, 15, 30, 75)
        )
    )
}
