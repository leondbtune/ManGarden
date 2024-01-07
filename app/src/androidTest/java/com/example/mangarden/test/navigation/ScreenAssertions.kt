package com.example.mangarden.test.navigation

import androidx.navigation.NavController
import junit.framework.TestCase.assertEquals

fun NavController.assertCurrentDestination(route: String) {
    assertEquals(route, currentBackStackEntry?.destination?.route)
}