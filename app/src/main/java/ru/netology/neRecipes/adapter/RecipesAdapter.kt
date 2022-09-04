package ru.netology.neRecipes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.neRecipes.R
import ru.netology.neRecipes.data.Recipe
import ru.netology.neRecipes.databinding.RecipeListItemFragmentBinding



internal class RecipesAdapter(

    private val interActionListener: RecipeInterActionListener

) : ListAdapter<Recipe, RecipesAdapter.PostViewHolder>(DiffCallBack) {

    inner class PostViewHolder(
        private val binding: RecipeListItemFragmentBinding,
        listener: RecipeInterActionListener

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

            binding.recipeImageBlock.recipeImage.setOnClickListener {
                listener.onImageClicked(recipe)
            }

            binding.recipeHeader.options.setOnClickListener { popupMenu.show() }

            binding.root.setOnClickListener{listener.viewRecipeDetails(recipe)}

        }

        fun bind(recipe: Recipe) {
            this.recipe = recipe

            val resources = binding.root.resources
            with(binding) {

                recipeHeader.title.text = recipe.title
                recipeHeader.authorName.text = recipe.authorName
                recipeHeader.recipeCategory.text = recipe.category
                //recipeImageBlock.recipeImage.setImageURI()
                recipeImageBlock.isFavouriteIcon.isChecked = recipe.isFavourite

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecipeListItemFragmentBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding, interActionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffCallBack : DiffUtil.ItemCallback<Recipe>() {
        override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
            oldItem == newItem
    }

}