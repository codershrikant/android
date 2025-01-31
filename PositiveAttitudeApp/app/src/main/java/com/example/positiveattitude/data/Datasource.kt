package com.example.positiveattitude.data

import com.example.positiveattitude.R
import com.example.positiveattitude.model.Thought

class Datasource() {
    fun loadThoughts(): List<Thought> {
        return listOf<Thought>(
            Thought(R.string.positive_attitude1, R.drawable.image__1_),
            Thought(R.string.positive_attitude2, R.drawable.image__2_),
            Thought(R.string.positive_attitude3, R.drawable.image__3_),
            Thought(R.string.positive_attitude4, R.drawable.image__4_),
            Thought(R.string.positive_attitude5, R.drawable.image__5_),
            Thought(R.string.positive_attitude6, R.drawable.image__6_),
            Thought(R.string.positive_attitude7, R.drawable.image__7_),
            Thought(R.string.positive_attitude8, R.drawable.image__8_),
            Thought(R.string.positive_attitude9, R.drawable.image__9_),
            Thought(R.string.positive_attitude10, R.drawable.image__10_)
        )
    }
}