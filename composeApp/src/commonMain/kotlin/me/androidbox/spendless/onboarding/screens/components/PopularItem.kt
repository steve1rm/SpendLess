package me.androidbox.spendless.onboarding.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import me.androidbox.spendless.core.presentation.OnPrimary
import me.androidbox.spendless.core.presentation.PrimaryFixed
import me.androidbox.spendless.transactions.data.Transaction
import org.jetbrains.compose.resources.painterResource

@Composable
fun PopularItem(
    modifier: Modifier = Modifier,
    popularCategory: Transaction
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(16.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(modifier = Modifier
            .size(56.dp)
            .background(color = PrimaryFixed, shape = RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center) {
            Icon(
                modifier = Modifier
                    .size(30.dp),
                painter = painterResource(resource = popularCategory.category.iconRes),
                contentDescription = null,
                tint = Color.Unspecified
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = popularCategory.category.title,
                fontWeight = FontWeight.W600,
                fontSize = 20.sp,
                color = OnPrimary
            )

            Text(
                text = "Most popular category",
                fontWeight = FontWeight.W400,
                fontSize = 12.sp,
                color = OnPrimary
            )
        }
    }
}