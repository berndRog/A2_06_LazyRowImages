package de.rogallab.mobile.di

import de.rogallab.mobile.data.ImagesRepository
import de.rogallab.mobile.data.Seed
import de.rogallab.mobile.domain.IImagesRepository
import de.rogallab.mobile.domain.utilities.logInfo
import de.rogallab.mobile.ui.features.images.ImageViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val defineModules: Module = module {
   val tag = "<-dataModules"

   logInfo(tag, "single    -> Seed")
   single<Seed> {
      Seed(_context = androidContext())  // dependency injection of Android context
   }

   logInfo(tag, "single    -> PersonRepository: IPersonRepository")
   single<IImagesRepository> {
      ImagesRepository(
         _seed = get<Seed>()  // dependency injection of DataStore
      )
   }

   logInfo(tag, "viewModel -> PersonViewModel")
   viewModel {
      ImageViewModel(
         _repository = get<IImagesRepository>(),
      )
   }
}

val appModules: Module = module {
   try {
      val testedModules = defineModules
      requireNotNull(testedModules) {
         "defineModules failed"
      }
      includes(
         testedModules,
         //testedUiModules,
         //useCaseModules
      )
   } catch (e: Exception) {
      logInfo("<-appModules", e.message!!)
   }
}