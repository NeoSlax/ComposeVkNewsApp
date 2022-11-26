package ru.neoslax.composevknewsapp.domain.model

data class StatisticsItem(val itemType: ItemType, val value: Int)

enum class ItemType {
    VIEWS, REPOSTS, COMMENTS, LIKES
}