package de.rogallab.mobile.ui.features.images

import androidx.lifecycle.ViewModel
import de.rogallab.mobile.domain.IImagesRepository
import de.rogallab.mobile.domain.entities.DogImage
import de.rogallab.mobile.domain.utilities.logDebug
import de.rogallab.mobile.domain.utilities.logError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class ImageViewModel(
   private val _repository: IImagesRepository
): ViewModel() {

   // StateFlow List<DogImage>
   private var _mutableImagesUiStateFlow:MutableStateFlow<ImagesUiState>  =
      MutableStateFlow(ImagesUiState())
   val imagesUiState: StateFlow<ImagesUiState> =
      _mutableImagesUiStateFlow.asStateFlow()

   // transform intent into an action
   fun onProcessIntent(intent: ImageIntent) {
      when (intent) {
         is ImageIntent.FetchDogs -> fetchDogs()
         is ImageIntent.SearchDog -> onSearch(intent.query)
         is ImageIntent.QueryChange -> onQueryChange(intent.query)
         is ImageIntent.ActiveChange -> onActiveChange(intent.active)
      }
   }

   private fun fetchDogs() {
      logDebug(TAG, "fetchDogs")
      _repository.getAll()
         .onSuccess { dogs ->
            _mutableImagesUiStateFlow.update { it: ImagesUiState ->
               val sortedDogs = dogs?.sortedBy { dog: DogImage ->
                  dog.name.lowercase(Locale.getDefault()) // sort by name
               } ?: emptyList() // if null, use an empty list
               it.copy(dogs = sortedDogs)
            }
         }
         .onFailure { logError(TAG, it.message ?: "Error in fetchDogs") }
   }
   // local function, only used from viewModel, no intent necessary
   private fun refreshDogs(dogs: List<DogImage>) {
      _mutableImagesUiStateFlow.update { it: ImagesUiState ->
         it.copy( dogs = dogs.toList() )
      }
   }

   // actual search query
   private val _mutableQueryStateFlow: MutableStateFlow<String> = MutableStateFlow("")
   val queryStateFlow: StateFlow<String> = _mutableQueryStateFlow.asStateFlow()
   private fun onQueryChange(text: String) {
      logDebug(TAG,"onQueryChange() '$text'")
      _mutableQueryStateFlow.update { text }
   }

   // is searching active?
   // event, when the user clicks on the search icon to
   // activate/deactivate searching
   private val _mutableIsActiveStateFlow = MutableStateFlow(false)
   val isActiveStateFlow = _mutableIsActiveStateFlow.asStateFlow()
   // event, when the user clicks on the search icon to  activate/deactivate searching
   private fun onActiveChange(active: Boolean) {
      _mutableIsActiveStateFlow.update { active }
      logDebug(TAG,"onActiveChange() isActive ${_mutableIsActiveStateFlow.value}")
      if (!_mutableIsActiveStateFlow.value) onQueryChange("")
   }

   // event, when the user triggers the Ime.Search action (Lupe)
   private fun onSearch(searchQuery: String) {
      logDebug(TAG,"onSearch() searchQuery $searchQuery")
      if (searchQuery.trim().isEmpty()) {
         // stop searching
         onActiveChange(false)
         fetchDogs()
      } else {
         // filter dogs by search query compared with dog name
         _mutableImagesUiStateFlow.value.dogs
            .filter { dog: DogImage ->
               val dogNameLower = dog.name.lowercase(Locale.getDefault())
               val searchLower  = searchQuery.trim().lowercase(Locale.getDefault())
               dogNameLower.startsWith(searchLower)  // ^filter
            }
            .apply { // this is the filtered list of dogs
               refreshDogs(this)
            }
         onActiveChange(false)
      }
   }

   companion object {
      const val TAG = "<-ImagesViewModel"
   }

}