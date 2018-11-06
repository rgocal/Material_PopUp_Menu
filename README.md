# Material_PopUp_Menu
A nostalgic dialog action menu from Android 2.3 re-invented for Material Design. This popup menu was originally used in ADW Launcher from CM7. I wanted something nostalgic to return in Sapphyx Launcher and came up with this rather simple library that can be used for a number of things. Very useful in listview or recyclerview long click popups. I am seeking some help from material design devs to make it look even more cleaner. If you want to contribute, theirs a todo list at the bottom! Material Popup was originally built for my Launcher Project Sapphyx but alot of my users liked the idea of it returning, So I extracted it as a library. Enjoy! 

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

Just take a look at the example app. Once I get everything working to my standards, I'll do a fully documented setup.
  

#ToDo List
- Programicaly set background colors for assets instead of strings. (Mainly for Sapphyx Launcher so I can pull icon colors for the menu) 
- Badge Support for Action Items
- Wrap Menu against number of items available or set a maximum screen width consumption. 
- Vertical Orientation mode? 
- Center items available 
