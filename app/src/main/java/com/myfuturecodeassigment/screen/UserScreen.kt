package com.myfuturecodeassigment.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.myfuturecodeassigment.data.model.User
import com.myfuturecodeassigment.utils.SearchBar
import com.myfuturecodeassigment.utils.UserItemShimmer
import com.myfuturecodeassigment.viewModel.UserViewModel
@Composable
fun UserScreen(viewModel: UserViewModel = hiltViewModel()) {

    val users by viewModel.filteredUsers.collectAsState()
    val query by viewModel.searchQuery.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    Column(modifier = Modifier.padding(top = 30.dp)) {

        SearchBar(
            query = query,
            onQueryChange = {
                viewModel.searchQuery.value = it
            }
        )

        if (isLoading) {
            LazyColumn {
                items(6) {
                    UserItemShimmer()
                }
            }
        } else {
            LazyColumn {
                items(users) { user ->
                    UserItem(user)
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {

        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Profile Image + Online Indicator
            Box {

                AsyncImage(
                    model = user.profileImage,
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                )

                // Online indicator
                if (user.isOnline) {
                    Box(
                        modifier = Modifier
                            .size(14.dp)
                            .background(Color.Green, CircleShape)
                            .align(Alignment.BottomEnd)
                            .border(2.dp, Color.White, CircleShape)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {

                Row(verticalAlignment = Alignment.CenterVertically) {

                    Text(
                        text = user.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    // Verified Badge
                    if (user.isVerified) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("✔", color = Color.Blue, fontSize = 14.sp)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = user.email,
                    color = Color.Gray,
                    fontSize = 13.sp
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = user.status,
                    color = Color(0xFF4CAF50),
                    fontSize = 12.sp
                )
            }

            // Rating Badge
            Box(
                modifier = Modifier
                    .background(Color(0xFFFFC107), RoundedCornerShape(8.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "⭐ ${user.rating}",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
