package my.project.techtestapp.di

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator
import com.bumptech.glide.module.AppGlideModule
import javax.inject.Singleton


@com.bumptech.glide.annotation.GlideModule
class GlideModule : AppGlideModule() {

    @Singleton
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        val calculator = MemorySizeCalculator.Builder(context)
            .setMemoryCacheScreens(2F)
            .build()
        builder.setMemoryCache(LruResourceCache(calculator.memoryCacheSize.toLong()))
    }
}
