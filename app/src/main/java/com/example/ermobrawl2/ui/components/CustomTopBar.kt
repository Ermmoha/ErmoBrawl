package com.example.ermobrawl2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.ermobrawl2.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: (KeyboardActionScope) ->Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(247, 247, 249))
            ) {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(horizontal = 15.dp, vertical = 10.dp),
                    placeholder = {
                        Text(
                            text = "Поиск", color = Color(127,118,121)
                        )
                    },
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.searchicon),
                            contentDescription = null,
                            modifier = Modifier
                                .size(25.dp),
                            tint = Color(127,118,121)
                        )
                    },
                    maxLines = 1,
                    shape = RoundedCornerShape(30.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = onDone
                    )
                )
            }
        },
        modifier = Modifier
            .height(120.dp),
    )
}