package com.example.bookchecker

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.bookchecker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import androidx.core.graphics.drawable.toDrawable


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    private val authDestinations = setOf(
        R.id.authEntryFragment,
        R.id.loginFragment,
        R.id.registerFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // NavController + Toolbar
        val host = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = host.navController

        appBarConfig = AppBarConfiguration(authDestinations) // мы не используем drawer здесь
//        setSupportActionBar(binding.topAppBar)
//        setupActionBarWithNavController(navController, appBarConfig)

        binding.topAppBar.apply {
            // Подключаем меню
            inflateMenu(R.menu.menu)

            // Обработка кликов по пунктам меню тулбара
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_account -> {
                        // TODO: навигация на экран профиля
//                        navController.navigate(R.id.profileFragment)
                        true
                    }
                    else -> false
                }
            }
        }

        // Скрываем тулбар на экранах авторизации
        navController.addOnDestinationChangedListener { _, dest, _ ->
            binding.topAppBar.visibility =
                if (dest.id in authDestinations) View.GONE else View.VISIBLE
            binding.topAppBar.title = dest.label

            // Всегда бургер, вместо стрелки
            binding.topAppBar.navigationIcon =
                ContextCompat.getDrawable(this, R.drawable.ic_menu)
        }

        // Клик по бургер‑иконке
        binding.topAppBar.setNavigationOnClickListener {
            val cur = navController.currentDestination?.id
            if (cur in authDestinations) return@setNavigationOnClickListener
            showCustomPopup()
        }
    }

    override fun onSupportNavigateUp(): Boolean =
        navController.navigateUp(appBarConfig) || super.onSupportNavigateUp()

    private fun showCustomPopup() {
        // Inflate с parent, чтобы wrap_content по высоте работал
        val popupView = layoutInflater.inflate(
            R.layout.popup_burger_menu,
            binding.root as ViewGroup,
            false
        )

        // PopupWindow по размеру контента
        val popup = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            isClippingEnabled = false
            isOutsideTouchable = true
        }

        popup.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
        // Анимация показа
        popupView.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.popup_slide_in)
        )

        // Настраиваем клики
        listOf(
            R.id.profileLogo to null,
            R.id.booksMain to R.id.bookListFragment,
            R.id.booksAdd to R.id.bookAddFragment,
//            R.id.booksCollection to R.id.bookCollectionFragment,
            R.id.booksStats to R.id.bookStatsFragment,
            R.id.booksSettings to R.id.appSettingsFragment
        ).forEach { (id, dest) ->
            popupView.findViewById<LinearLayout>(id).setOnClickListener {
                dest?.let { navController.navigate(it) }
                // анимация скрытия
                popupView.startAnimation(
                    AnimationUtils.loadAnimation(this, R.anim.popup_slide_out)
                )
                popup.dismiss()
            }
        }

        // Показываем слева от тулбара
        popup.showAsDropDown(binding.topAppBar, 0, 0, Gravity.START)
    }
}