package com.fluffykittens.fluffy_shop


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutUsPage() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item {

            Text(
                text = "Welcome to\nFluffy shop!",
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                lineHeight = 56.sp
            )
        }
        item {
            Text(
                text = "We are FluffyKittens, a passionate team of three creative and dedicated individuals: Artem Shpakovsky, Elizaveta Shimkovich, and Timofey Petrikevich. As students at Belarusian State University, we share a deep enthusiasm for innovation, teamwork, and building a unique shopping experience for pet lovers everywhere.",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "Who Are We?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "Artem Shpakovsky, our tech wizard, ensures that every line of code is seamless and every feature works flawlessly. With a sharp eye for detail and a drive to innovate, Artem turns our ideas into reality with precision and efficiency.\n" +
                        "\n" +
                        "Elizaveta Shimkovich, the creative mind behind our brand, breathes life into FluffyShop with her design skills and customer-centric approach. Her vision ensures that our platform is as delightful to use as it is functional.\n" +
                        "\n" +
                        "Timofey Petrikevich, the strategist of the team, brings organization and purpose to our work. His leadership and ability to tackle challenges keep us moving forward and striving for excellence.\n",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "Our Mission",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "FluffyShop was born out of our shared love for animals and a desire to create something meaningful. Our mission is to provide a user-friendly platform where pet owners can find the best products for their fluffy friends. We believe in combining technology with creativity to make shopping an enjoyable and effortless experience.",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "Why FluffyShop?",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "As university students, we know the value of hard work and determination. Balancing studies and entrepreneurship has taught us resilience and the importance of working as a team. FluffyShop is more than just a project—it's a reflection of our dedication, collaboration, and love for what we do.",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "Get in Touch!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "Whether you're here to shop, explore, or simply learn about us, we are thrilled to have you join our journey. If you have any questions, feedback, or just want to say hello, feel free to reach out. Together, let's make the world a fluffier, happier place!",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
