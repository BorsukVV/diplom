package ru.netology.neRecipes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import ru.netology.neRecipes.databinding.RecipeTabFragmentBinding

class RecipeTabDetailsFragment : Fragment() {

//    private lateinit var adapter: TabPagerAdapter
//    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecipeTabFragmentBinding.inflate(layoutInflater, container, false).also {

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                // Handle tab select
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })


//        adapter = TabPagerAdapter(childFragmentManager)
//        viewPager = view?.findViewById(R.id.recipe_details_pager)!!
//        viewPager.adapter = adapter
//
////        tabLayout = findViewById(R.id.tab_layout)
////        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
////            tab.text = if(position == 0) "Description" else "Steps"
////        }.attach()
   }.root

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        adapter = NumberAdapter(this)
//        viewPager = findViewById(R.id.pager)
//        viewPager.adapter = adapter
//
//        tabLayout = findViewById(R.id.tab_layout)
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = tabNames[position]
////            tab.setIcon(tabNumbers[position])
//
////            if (position == 2) {
////                val badge = tab.getOrCreateBadge()
////                badge.number = 1
////            }
//
//        }.attach()
//    }
}

