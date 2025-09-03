package de.rogallab.mobile.ui.images
//
//import android.content.Context
//import androidx.lifecycle.Lifecycle
//import androidx.lifecycle.LifecycleOwner
//import androidx.lifecycle.LifecycleRegistry
//import androidx.test.core.app.ApplicationProvider
//import androidx.test.ext.junit.rules.ActivityScenarioRule
//import androidx.test.ext.junit.runners.AndroidJUnit4
//import de.rogallab.mobile.MainActivity
//import de.rogallab.mobile.domain.entities.DogImage
//import de.rogallab.mobile.ui.features.images.ImagesViewModel
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.runBlocking
//import kotlinx.coroutines.test.StandardTestDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.setMain
//import kotlinx.coroutines.withContext
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.junit.rules.TestWatcher
//import org.junit.runner.Description
//import org.junit.runner.RunWith
//import org.mockito.Mock
//import org.mockito.MockitoAnnotations
//
//@RunWith(AndroidJUnit4::class)
//class ImageViewModelTest {
//
//   @get:Rule
//   val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)
//
//
//   private lateinit var imageViewModel: ImagesViewModel
//
//   @Mock
//   private lateinit var mockDogs: MutableList<DogImage>
//   @Mock
//   private lateinit var lifecycleOwner: LifecycleOwner
//
//   @get:Rule
//   val mainThreadRule = MainThreadTestRule()
//
//   @Before
//   fun setup() {
//      Dispatchers.setMain(Dispatchers.Unconfined)
//
//      MockitoAnnotations.openMocks(this)
//
//      val context = ApplicationProvider.getApplicationContext<Context>()
//
//      val lifecycleRegistry = LifecycleRegistry(lifecycleOwner)
//
//      runBlocking {
//         withContext(Dispatchers.Main) { // Run this block on the main thread
//            lifecycleRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME)
//            imageViewModel = ImagesViewModel(context)
//            imageViewModel.onChangeDogs(imageViewModel.initialDogs)
//         }
//      }
//   }
//
//   @Test
//   fun testInitialDogs() {
//      val expectedInitialDogsSize = 8 // Number of dogs you initialized in the ViewModel
//      assertEquals(expectedInitialDogsSize, imageViewModel.initialDogs.size)
//   }
//
//   @Test
//   fun testSortedDogs() {
//      val sortedDogs = imageViewModel.imagesUiStateStateFlow
//      val sortedDogNames = sortedDogs.map { it.name }
//      val expectedSortedDogNames = sortedDogNames.sortedBy{it}
//      assertEquals(expectedSortedDogNames, sortedDogNames)
//   }
//
//   @Test
//   fun testOnDogsChange() {
//      val newDogs = listOf(
//         DogImage(R.string.dog_01, R.drawable.dog_01),
//         DogImage(R.string.dog_02, R.drawable.dog_02),
//         DogImage(R.string.dog_03, R.drawable.dog_03)
//      )
//
//      // When onDogsChange is called with newDogs
//      imageViewModel.onDogsChange(newDogs)
//
//      // Verify that the dogs list was cleared and newDogs were added
//      Mockito.verify(mockDogs).clear()
//      Mockito.verify(mockDogs).addAll(newDogs)
//
//      // Assert that the cleared list and the added list are the same
//      assertEquals(newDogs, imageViewModel.dogs)
//   }
//}
//
//class MainThreadTestRule : TestWatcher() {
//
//   private val testDispatcher = StandardTestDispatcher()
//
//
//   override fun starting(description: Description?) {
//      super.starting(description)
//      Dispatchers.setMain(testDispatcher) // Set the main dispatcher()) // Set the main dispatcher
//   }
//
//   override fun finished(description: Description?) {
//      super.finished(description)
//      Dispatchers.resetMain() // Reset main dispatcher after the tests
//   }
//}
