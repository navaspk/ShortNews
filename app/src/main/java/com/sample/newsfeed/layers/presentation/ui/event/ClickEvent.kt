package com.sample.newsfeed.layers.presentation.ui.event

sealed class ClickEvent {

    data class ItemClicked(val pos: Int): ClickEvent()

}
