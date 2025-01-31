package com.example.positiveattitude.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Thought (
    @StringRes val stringResourceId: Int,
    @DrawableRes val imageResourceId: Int
)