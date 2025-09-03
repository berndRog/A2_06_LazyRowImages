package de.rogallab.mobile.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import de.rogallab.mobile.domain.utilities.logInfo

open class BaseActivity(
   private val _tag: String
) : ComponentActivity() {


   // Activity is first created
   override fun onCreate(savedInstanceState: Bundle?) {
      super.onCreate(savedInstanceState)
      if (savedInstanceState == null)
         logInfo(_tag, "onCreate() Bundle == null")
      else
         Log.i(_tag, "onCreate() Bundle != null")
   }

   // Activity is restarted
   override fun onRestart() {
      super.onRestart()
      Log.i(_tag, "onRestart()")
   }

   // Activity is visible
   override fun onStart() {
      super.onStart()
      Log.i(_tag, "onStart()")
   }

   // Activity interacts with the user
   override fun onResume() {
      super.onResume()
      Log.i(_tag, "onResume()")
   }

   // User is leaving activity
   override fun onPause() {
      Log.i(_tag, "onPause()")
      super.onPause()
   }

   // Activity is no longer visible
   override fun onStop() {
      Log.i(_tag, "onStop()")
      super.onStop()
   }

   // Called before the activity is destroyed.
   override fun onDestroy() {
      Log.i(_tag, "onDestroy()")
      super.onDestroy()
   }

   // Save instance state: invoked when the activity may be temporarily destroyed,
   override fun onSaveInstanceState(outState: Bundle) {
      super.onSaveInstanceState(outState)
      Log.i(_tag, "onSaveInstanceState()")
   }

   override fun onRestoreInstanceState(savedInstanceState: Bundle) {
      super.onRestoreInstanceState(savedInstanceState)
      Log.i(_tag, "onRestoreInstanceState()")
   }

   override fun onConfigurationChanged(newConfig: Configuration) {
      super.onConfigurationChanged(newConfig)

      // Checks the orientation of the screen
      if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
         Log.i(_tag, "landscape")
      } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
         Log.i(_tag, "portrait")
      }
   }

   override fun onWindowFocusChanged(hasFocus: Boolean) {
      Log.i(_tag, "onWindowFocusChanged() $hasFocus")
      super.onWindowFocusChanged(hasFocus)
   }
}