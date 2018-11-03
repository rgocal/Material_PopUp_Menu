# Material_PopUp_Menu
A nostalgic dialog action menu from Android 2.3 re-invented for Material Design

#Getting Setup!

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
  
  dependencies {
	        implementation 'com.github.rgocal:Material_PopUp_Menu:master-SNAPSHOT'
	}
  
  #Add Color Strings
  
  add these color strings to your project
  <!--The body color is the top and bottom headers and the arrow color -->
    <color name="popup_body_color">@android:color/holo_blue_dark</color>
    <!--The scrim color is the scrollbar color if enabled -->
    <color name="popup_scrim_color">@android:color/holo_blue_dark</color>
    <!--The scroll color is the background menu color -->
    <color name="popup_scroll_color">@android:color/holo_blue_dark</color>
    <!--The track color is the menu left and right dividers, set to the same color as the scroll color to disable -->
    <color name="popup_track_color">@android:color/holo_blue_dark</color>
    
    #Keeping It Simple
    
    Just take a look at the example app. Once I get everything working to my standards, I'll a fully documented setup.
  
