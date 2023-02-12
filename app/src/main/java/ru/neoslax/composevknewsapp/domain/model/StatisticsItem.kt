package ru.neoslax.composevknewsapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class StatisticsItem(val itemType: ItemType, val value: Int) : Parcelable

enum class ItemType {
    VIEWS, REPOSTS, COMMENTS, LIKES
}