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
fun ContactsPage() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        item {
            // Один Text с большим межстрочным интервалом
            Text(
                text = "Contact Us",
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
                text = "We are always excited to hear from our customers, partners, and pet lovers around the world. Whether you have questions, suggestions, or just want to say hello, feel free to reach out to us through the following channels. We're here to help and make your experience with FluffyShop as delightful as possible.",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "Our Contact Details",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "Email",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "For general inquiries, product support, or feedback, reach us at:",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = "support@fluffyshop.com",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "Phone",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "Feel free to call us for urgent matters or immediate assistance:\n" +
                        "+1 (800) 123-4567",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "Address",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "If you prefer to visit us or send mail, here’s where you can find us:\n" +
                        "FluffyShop HQ, 1234 Pet Street, Minsk, Belarus",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "Social Media",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "Follow us on social media for updates, promotions, and more:",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "Get in Touch",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
        item {
            Text(
                text = "We value our community and are committed to providing exceptional customer service. If you need any help, don’t hesitate to contact us! We look forward to hearing from you and making your shopping experience with FluffyShop even better.",
                fontSize = 18.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
