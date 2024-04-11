package com.example.gameapp.util

fun String.containsRPGorEmptySpaces(): Boolean {
    return this == Constants.RPG || this.contains(" ")
}