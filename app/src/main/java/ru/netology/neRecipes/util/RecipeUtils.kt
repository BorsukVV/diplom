package ru.netology.neRecipes.util

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import ru.netology.neRecipes.R

class RecipeUtils {

    companion object {

        const val CREATE: Boolean = true
        const val EDIT: Boolean = false

    fun descriptionImageTemplateUri(resources: Resources): Uri = Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(resources.getResourcePackageName(R.drawable.recipe_image_template))
        .appendPath(resources.getResourceTypeName(R.drawable.recipe_image_template))
        .appendPath(resources.getResourceEntryName(R.drawable.recipe_image_template))
        .build()

    fun stepImageTemplateUri(resources: Resources): Uri = Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(resources.getResourcePackageName(R.drawable.step_image_template))
        .appendPath(resources.getResourceTypeName(R.drawable.step_image_template))
        .appendPath(resources.getResourceEntryName(R.drawable.step_image_template))
        .build()

}


}