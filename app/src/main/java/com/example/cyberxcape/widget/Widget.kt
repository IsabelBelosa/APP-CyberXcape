package com.example.cyberxcape.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.*
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.*
import androidx.glance.unit.ColorProvider
import androidx.glance.text.TextStyle as GlanceTextStyle
import androidx.glance.text.FontWeight as GlanceFontWeight

import com.example.cyberxcape.R
import com.example.cyberxcape.componentes.MainActivity
import com.example.cyberxcape.model.ReservaResumen
import com.example.cyberxcape.network.RetrofitClient
import com.example.cyberxcape.ui.theme.Blanco
import com.example.cyberxcape.ui.theme.Negro
import com.example.cyberxcape.ui.theme.Rosa
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun obtenerReservasDesdeApiWidget(): List<ReservaResumen> {
    return withContext(Dispatchers.IO) {
        try {
            val listaDesdeApi = RetrofitClient.apiService.obtenerReservas()
            listaDesdeApi
        } catch (e: Exception) {
            android.util.Log.e("CoworkingWidget", "Error al obtener reservas de la API: ${e.message}", e)
            emptyList<ReservaResumen>()
        }
    }
}

class CoworkingWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val reservas = obtenerReservasDesdeApiWidget()
        provideContent {
            CoworkingWidgetContent(reservas = reservas)
        }
    }
}

@SuppressLint("RestrictedApi")
@Composable
fun CoworkingWidgetContent(reservas: List<ReservaResumen>) {
    val colorFondoWidget = Negro
    val colorTitulo = Rosa
    val colorTextoPrincipal = Blanco
    val colorTextoSecundario = Blanco
    val colorDivisor = Blanco

    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ColorProvider(colorFondoWidget))
            .padding(16.dp)
            .clickable(onClick = actionStartActivity<MainActivity>()),
        horizontalAlignment = Alignment.Horizontal.CenterHorizontally
    ) {
        Row(
            modifier = GlanceModifier.fillMaxWidth(),
            verticalAlignment = Alignment.Vertical.CenterVertically
        ) {
            Image(
                provider = ImageProvider(R.drawable.logo),
                contentDescription = "Logo",
                modifier = GlanceModifier.size(40.dp)
            )
            androidx.glance.text.Text(
                text = "Mis Reservas",
                style = GlanceTextStyle(
                    color = ColorProvider(colorTitulo),
                    fontWeight = GlanceFontWeight.Bold,
                    fontSize = 18.sp
                ),
                modifier = GlanceModifier.padding(start = 8.dp)
            )
        }

        Spacer(GlanceModifier.height(16.dp))
        if (reservas.isEmpty()) {
            androidx.glance.text.Text(
                text = "No hay reservas disponibles o error al cargar.",
                style = GlanceTextStyle(
                    color = ColorProvider(colorTextoPrincipal)

                ),
                modifier = GlanceModifier.padding(top = 8.dp)
            )
        } else {
            Column(
                modifier = GlanceModifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Horizontal.Start
            ) {
                reservas.take(3).forEachIndexed { index, reserva ->
                    Column(
                        modifier = GlanceModifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        androidx.glance.text.Text(
                            text = "Sala: ${reserva.sala.take(20)}${if (reserva.sala.length > 20) "..." else ""}",
                            style = GlanceTextStyle(
                                color = ColorProvider(colorTextoPrincipal),
                                fontWeight = GlanceFontWeight.Bold, // Usando GlanceFontWeight
                                fontSize = 16.sp
                            ),
                            maxLines = 1
                        )
                        androidx.glance.text.Text(
                            text = "Usuario: ${reserva.nombre}",
                            style = GlanceTextStyle(
                                color = ColorProvider(colorTextoSecundario),
                                fontSize = 14.sp
                            ),
                            maxLines = 1
                        )
                        Spacer(GlanceModifier.height(2.dp))
                        androidx.glance.text.Text(
                            text = "Fecha: ${reserva.fecha} de ${reserva.horaInicio} a ${reserva.horaFin}",
                            style = GlanceTextStyle(
                                color = ColorProvider(colorTextoSecundario),
                                fontSize = 14.sp
                            ),
                            maxLines = 2
                        )
                    }
                    if (index < reservas.take(3).size - 1) {
                        Box(
                            modifier = GlanceModifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(ColorProvider(colorDivisor))
                        ) {
                        }
                    }
                }
            }
        }
    }
}

class CoworkingWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = CoworkingWidget()
}