package com.cpinto.gamecatalog.application.models.games

data class GamesAdapterDefinition(
    val type: Int,
    val typeName: String = "",
    val arrGames: MutableList<Games> = mutableListOf()
)