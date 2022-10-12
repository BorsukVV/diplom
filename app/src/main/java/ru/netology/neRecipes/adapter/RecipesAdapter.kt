package ru.netology.neRecipes.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.neRecipes.R
import ru.netology.neRecipes.data.Recipe
import ru.netology.neRecipes.databinding.RecipeListItemFragmentBinding
import ru.netology.neRecipes.util.RecipeUtils


internal class RecipesAdapter(

    private val interActionListener: RecipeInteractionListener

) : ListAdapter<Recipe, RecipesAdapter.RecipeViewHolder>(DiffCallBack) {

    inner class RecipeViewHolder(
        private val binding: RecipeListItemFragmentBinding,
        listener: RecipeInteractionListener

    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var recipe: Recipe

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.recipeHeader.options).apply {
                inflate(R.menu.options_recipe)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(recipe)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(recipe)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.recipeImageBlock.isFavouriteIcon.setOnClickListener {
                listener.chooseFavorite(recipe)
            }

            binding.recipeHeader.options.setOnClickListener {
                popupMenu.show()
            }

            binding.root.setOnClickListener {
                listener.viewRecipeDetails(recipe)
            }

        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe

            with(binding) {
                recipeHeader.title.text = recipe.title
                recipeHeader.authorName.text = recipe.authorName
                recipeHeader.recipeCategory.text = recipe.category

                if (recipe.imageUri != null) {
                    recipeImageBlock.recipeImage.setImageURI(recipe.imageUri)
                } else {
                    RecipeUtils.descriptionImageTemplateUri(binding.root.resources)
                }

                recipeImageBlock.imageGroup.visibility =
                    if (recipe.hasCustomImage) View.VISIBLE else View.GONE
                recipeImageBlock.isFavouriteIcon.isChecked = recipe.isFavourite
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeListItemFragmentBinding.inflate(inflater, parent, false)
        return RecipeViewHolder(binding, interActionListener)
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffCallBack : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
            oldItem == newItem
    }

}