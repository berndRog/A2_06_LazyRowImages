package de.rogallab.mobile.data

import de.rogallab.mobile.domain.IImagesRepository
import de.rogallab.mobile.domain.entities.DogImage

class ImagesRepository(
   private val _seed: Seed
): IImagesRepository {

   override fun getAll(): Result<List<DogImage>> {
      return try {
         Result.success(_seed.dogs)
      } catch (e: Exception) {
         Result.failure(e)
      }
   }
}