package de.rogallab.mobile.domain

import de.rogallab.mobile.domain.entities.DogImage

interface IImagesRepository {
   fun getAll(): Result<List<DogImage>>
}