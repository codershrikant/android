package com.example.positiveattitude

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.positiveattitude.data.Datasource
import com.example.positiveattitude.model.Thought
import com.example.positiveattitude.ui.theme.PositiveAttitudeTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PositiveAttitudeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PositiveAttitudeApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PositiveAttitudeApp(modifier: Modifier = Modifier) {
    PositiveAttitude(
        thoughtList = Datasource().loadThoughts(),
        modifier = modifier
    )
}

@Composable
fun PositiveAttitude(thoughtList: List<Thought>, modifier: Modifier) {
    LazyColumn(modifier = modifier) {
        items(thoughtList) { thought ->
            FloatingAnimatedImageCard(
                thought = thought,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun FloatingAnimatedImageCard(thought: Thought, modifier: Modifier = Modifier) {
    // Animates the shimmer effect from dark to fully visible once
    val shimmerAlpha = remember { Animatable(0.3f) }

    LaunchedEffect(Unit) {
        shimmerAlpha.animateTo(
            targetValue = 1f, // Fully visible
            animationSpec = tween(durationMillis = 1500, easing = FastOutSlowInEasing)
        )
    }

    // Smooth floating effect (scaling)
    val infiniteTransition = rememberInfiniteTransition(label = "Floating Image Animation")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.03f, // Subtle pulsating effect
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2500, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ), label = "Scale Animation"
    )

    Card(
        modifier = modifier
            .padding(8.dp)
            .shadow(8.dp, shape = RoundedCornerShape(12.dp)),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .background(Color.Black) // Prevent background exposure
        ) {
            Image(
                painter = painterResource(thought.imageResourceId),
                contentDescription = stringResource(thought.stringResourceId),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        alpha = shimmerAlpha.value // Apply one-time shimmer effect
                    }
            )

            // Overlay text at the bottom with a gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                        )
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = LocalContext.current.getString(thought.stringResourceId),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PositiveAttitudeTheme {
        FloatingAnimatedImageCard(Thought(R.string.positive_attitude1, R.drawable.image__1_))
    }
}