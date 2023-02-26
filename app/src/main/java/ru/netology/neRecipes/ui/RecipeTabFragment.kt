package ru.netology.neRecipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import ru.netology.neRecipes.R
import ru.netology.neRecipes.databinding.RecipeTabFragmentBinding
import ru.netology.neRecipes.util.RecipeUtils

class RecipeTabFragment : Fragment() {

    private val args by navArgs<RecipeTabFragmentArgs>()

    private lateinit var binding: RecipeTabFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = RecipeTabFragmentBinding.inflate(
            inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabNames = listOf(
            binding.root.resources.getString(R.string.summary),
            binding.root.resources.getString(R.string.by_steps)
        )

        val fragmentsForOpenDetails = listOf(
            RecipeDescriptionDetailsFragment.newInstance(args.initialRecipeID),
            RecipeStepsListFragment.newInstance(args.initialRecipeID)
        )

        val fragmentsForEdit = listOf(
            RecipeDescriptionCreateFragment.newInstance(),
            RecipeStepsListCreateFragment.newInstance(args.initialRecipeID)
        )

        val fragmentsSet =
            if (args.operationCode == RecipeUtils.CREATE) fragmentsForEdit else fragmentsForOpenDetails

        val adapter = TabPagerAdapter(
            activity as AppCompatActivity, fragmentsSet
        )

        binding.viewPager.adapter = adapter

        TabLayoutMediator(
            binding.recipeDetailsTabLayout,
            binding.viewPager
        ) { tab, position ->
            tab.text = tabNames[position]
        }.attach()

    }



}

