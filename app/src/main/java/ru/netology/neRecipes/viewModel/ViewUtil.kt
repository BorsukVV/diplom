package ru.netology.neRecipes.viewModel

import android.content.ContentResolver
import android.content.res.Resources
import android.net.Uri
import ru.netology.neRecipes.R

class ViewUtil {

    companion object {

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

    //val spinnerSet = Resources.getSystem().getStringArray(R.array.category_list)
}


}