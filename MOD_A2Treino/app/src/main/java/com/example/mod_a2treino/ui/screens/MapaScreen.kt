package com.example.mod_a2treino.ui.screens

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.mod_a2treino.ui.models.EscolasData
import com.example.mod_a2treino.ui.theme.Typography
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.tasks.await
import java.util.Locale
import java.util.concurrent.CancellationException

val BRASILIA = LatLng(-15.738387121157935,-47.92534498704637 )


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapaScreen(
    onBack: () -> Unit,
    escolas: List<EscolasData>,
    onDetalhes: (Int) -> Unit
){

    val context = LocalContext.current

    val activity = context as Activity


    val permissoes = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    var permitido by remember {
        mutableStateOf(
            permissoes.any{
                ContextCompat.checkSelfPermission(context,it) == PackageManager.PERMISSION_GRANTED
            }
        )
    }
    val permissionLauncher = rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){
            resultado->
        permitido = resultado.values.any{it}
    }

    var userLocation by remember { mutableStateOf<LatLng?>(null) }
    var modalEscola by remember { mutableStateOf<EscolasData?>(null) }
    var mapLoaded by remember { mutableStateOf(false)}
    val cameraState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(BRASILIA,12f)
    }


    fun formatDistance(metros: Float): String{
        if (metros<1000f){
            return String.format(Locale.US,"%.1f m",metros)
        }
        else{
            return  String.format(Locale.US,"%.1f km",metros/1000f)
        }
    }
    fun irAgora(ctx: Context, escola: EscolasData, userLocation: LatLng?) {
        val uri = Uri.parse("https://www.google.com/maps/dir/").buildUpon()
            .appendQueryParameter("api", "1")
            .appendQueryParameter("destination", "${escola.latitude},${escola.longitude}")
            .appendQueryParameter("dir_action", "navigate")
            .apply { userLocation?.let { appendQueryParameter("origin", "${it.latitude},${it.longitude}") } }
            .build()
        try {
            ctx.startActivity(Intent(Intent.ACTION_VIEW, uri).setPackage("com.google.android.apps.maps"))
        } catch (e: ActivityNotFoundException) {
            try {
                ctx.startActivity(Intent(Intent.ACTION_VIEW, uri))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(ctx, "Nenhum aplicativo de mapas disponível", Toast.LENGTH_LONG).show()
            }
        }
    }

    DisposableEffect(Unit) {
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        onDispose {
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        }
    }

    LaunchedEffect(Unit) {
        if (!permitido){
            permissionLauncher.launch(permissoes)
        }
    }

    LaunchedEffect(permitido) {
        if (!permitido) return@LaunchedEffect
        userLocation = try {
            LocationServices
                .getFusedLocationProviderClient(context)
                .getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY,null)
                .await()
                ?.let { LatLng(it.latitude,it.longitude) }

        } catch (e: CancellationException){
            throw  e
        } catch (e: Exception){
            null
        }
    }

    Box(
        Modifier.fillMaxSize()
    ){
        GoogleMap(
            properties = MapProperties(isMyLocationEnabled = permitido),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                mapToolbarEnabled = false,
                myLocationButtonEnabled = false
            ),
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraState,
            onMapLoaded = {mapLoaded = true}
        ){
            escolas.forEach { escola->
                key(escola.id) {
                    Marker(
                        onClick = {
                            modalEscola = escola
                            true
                        },
                        state = rememberMarkerState(position = LatLng(escola.latitude,escola.longitude)),
                        title = escola.nome,
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                    )
                }
            }
        }
        FloatingActionButton(
            onClick = onBack,
            modifier = Modifier.align(Alignment.TopStart).safeDrawingPadding().padding(16.dp)

        ) { Icon(Icons.Default.ArrowBack,contentDescription = null) }

        if (!mapLoaded){
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
    }

    if (modalEscola != null){

        modalEscola?.let { escola->

            val distancia = remember(userLocation,escola){
                userLocation?.let {
                    val r = FloatArray(1)
                    Location.distanceBetween(it.latitude,it.longitude,escola.latitude,escola.longitude,r)
                    formatDistance(r[0])
                } ?: "Localização Indisponivel"
            }

            ModalBottomSheet(
                onDismissRequest = {modalEscola = null}
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(escola.nome, style = Typography.bodyLarge)
                    Text("Avaliação: %.2f".format(Locale.US,escola.avaliacao))
                    Text("Distancia: $distancia")
                    Button(

                        onClick = { modalEscola = null; onDetalhes(escola.id) },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Detalhes da Escola") }

                    Button(
                        onClick = { irAgora(context, escola, userLocation) },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Ir Agora!") }

                    OutlinedButton(
                        onClick = { modalEscola = null },
                        modifier = Modifier.fillMaxWidth()
                    ) { Text("Fechar") }
                }
            }
        }
    }
}