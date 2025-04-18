package me.androidbox.spendless

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.ActionParameters
import androidx.glance.action.actionParametersOf
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.action.ActionCallback
import androidx.glance.appwidget.action.actionRunCallback
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import me.androidbox.speedless.R
import me.androidbox.spendless.core.presentation.OnPrimary
import me.androidbox.spendless.core.presentation.Primary

class TransactionWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        this.provideContent {

            Column(
                modifier = GlanceModifier
                    .fillMaxSize()
                    .background(color = Primary)
                    .padding(start = 16.dp, end = 16.dp)
                    .clickable(
                      /*  actionStartActivity<MainActivity>(
                            parameters = actionParametersOf(ActionParameters.Key<Boolean>("WIDGET") to true)
                        )*/

                                actionRunCallback<TransactionCallback>()

                        ),
                verticalAlignment = Alignment.Vertical.CenterVertically,
                horizontalAlignment = Alignment.Horizontal.Start
            ) {
                Image(
                    provider = ImageProvider(resId = R.drawable.logo),
                    contentDescription = "image"
                )

                Spacer(
                    modifier = GlanceModifier.height(8.dp)
                )

                Text(
                    text = "Create Transaction",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = ColorProvider(color = OnPrimary))
                )
            }
        }
    }
}

object TransactionCallback : ActionCallback {
    override suspend fun onAction(
        context: Context,
        glanceId: GlanceId,
        parameters: ActionParameters
    ) {
        println("TRANSACTION CALLBACK")
        val deepLinkUri = "spendless://dashboard/1".toUri()

        val intent = Intent(Intent.ACTION_VIEW, deepLinkUri).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            addCategory(Intent.CATEGORY_DEFAULT)
            addCategory(Intent.CATEGORY_BROWSABLE)
            setPackage(context.packageName)
        }

        context.startActivity(intent)
    }
}