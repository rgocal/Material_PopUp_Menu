# Material_PopUp_Menu

[![JitPack](https://jitpack.io/v/rgocal/Material_PopUp_Menu.svg)](https://jitpack.io/#rgocal/Material_PopUp_Menu)

A nostalgic dialog action menu from Android 2.3 re-invented for Material Design. This popup menu was originally used in ADW Launcher from CM7. I wanted something nostalgic and usefull to use in my launcher projects and this was it. Very useful in listview or recyclerview long click popups. Material Popup was originally built for my Launcher Project Sapphyx but alot of my users liked the idea of it returning, So I extracted it as a library. Enjoy! 

![Preview Image](./.github/images/preview.jpg?raw=true)
![Preview Image](./.github/images/preview_new.jpg?raw=true)


# Getting set up!

```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
  
dependencies {
	        implementation 'com.github.rgocal:Material_PopUp_Menu:2.03'
}
```
  
## Add Color Strings (optional)
  
Add these color strings to your project only if you plan to set the colors to the popup via xml and not via code

```xml
<!--The body color is the top and bottom headers and the arrow color -->
<color name="popup_body_color">@android:color/holo_blue_dark</color>
<!--The scrim color is the scrollbar color if enabled -->
<color name="popup_scrim_color">@android:color/holo_blue_dark</color>
<!--The scroll color is the background menu color -->
<color name="popup_scroll_color">@android:color/holo_blue_dark</color>
<!--The track color is the menu left and right dividers, set to the same color as the scroll color to disable -->
<color name="popup_track_color">@android:color/holo_blue_dark</color>
```
    
## Action List

There are 2 types of Menus and what actions we add matters. If you are planning to use only the Horizontal Menu, use small titles (8-12 characters max). If you plan to have actions with long titles (12-24 characters), you will use these in the Vertical Menu. Each action requires an ID, a Title, and a Resource. The ID represents the position of the item. You can set boolean toggles to these IDs to programically hide them if you wish to add customization to your menu.

```java
final ActionItem dummyOne = new ActionItem(1, "Dummy 1", getResources().getDrawable(R.drawable.ic_settings));
final ActionItem dummyTwo = new ActionItem(2, "Dummy 2", getResources().getDrawable(R.drawable.ic_settings));
final ActionItem dummyThree = new ActionItem(3, "Dummy 3", getResources().getDrawable(R.drawable.ic_settings));

final ActionItem dummyThree = new ActionItem(3, "Long Dummy Item", getResources().getDrawable(R.drawable.ic_settings));
```
    
## Keeping It Simple
	
Make them Stick or UnSticky. Making them Sticky means after clicking the option, the menu is still popped up. If they are UnSticky, The menu will dismiss after the item has been clicked.

```java
dummyOne.setSticky(true);
dummyTwo.setSticky(true);
dummyThree.setSticky(false);
```
	
Initialize the Popup Menu

```java
final PopUpMenu mQuickAction = new PopUpMenu(context);
```
		
Modify the Menu to your liking. Set animate track to true if you would like to see it bounce into place. Set light theme to true if you want light icons vs dark icons. Set scrollbar enabled if you want to see a scrollbar. Anim Style has 5 variations, check out the source code of the library to decide what you want or set it to 4 for the sysytem to decide on its own. Set action titles to true if you want your items to have titles under their icons. Do not exceed the menu legth with your Strings or your menu's margin position will be off.

To enable the vertical menu expansion, you need to enable it. By enabling it, you also allow your menu to have a menu title if you wish for it to have one. This enables a new view group allowing your menu to grow to more possibilities.

```java
mQuickAction.mAnimateTrack(true);
mQuickAction.setLightTheme(true);
mQuickAction.setScrollBar(false);
mQuickAction.setAnimStyle(4);
mQuickAction.setHasActionTitles(true);

mQuickAction.hasVerticalExpansion(true);
mQuickAction.setHasHeaderTitle(true);
String menuTitle = "Material Popup";
mQuickAction.setMenuTitle(menuTitle);

mQuickAction.themeVerticalIcons(true);

```
	
Programically tint the backgrounds to the menu. This comes in handy when and if you want to extract a color from an object to apply to the menu. You can also style the menu by overriding the color strings in your project but this will only work past Android 6.

```java
mQuickAction.setHorizontalScrollColor(ContextCompat.getColor(this, R.color.orange));
mQuickAction.setVertScrollColor(ContextCompat.getColor(this, R.color.orange));
mQuickAction.setTitleBackgroundColor(ContextCompat.getColor(this, R.color.orange));
mQuickAction.setOuterColor(ContextCompat.getColor(this, R.color.orang
```
	
Add your items you created up top, to your menu. This is where you can set boolean if statements to the IDs if you want to toggle them in your menu for customization. Horizontal menu should have anywhere from 3-7 items while the Vertical menu should have anywhere from 1-4 items. The guidelines are the same as if they were launcher shortcuts in the Pixel Launcher popups.

```java
mQuickAction.addActionItem(dummyOne);
mQuickAction.addActionItem(dummyTwo);
mQuickAction.addActionItem(dummyThree);

mQuickAction.addVerticalActionItem(dummyVertOne);
```
	
Set a click listener for each item.

```java
mQuickAction.setOnActionItemClickListener(new PopUpMenu.OnActionItemClickListener() {
    @Override
    public void onItemClick(PopUpMenu source, int pos, int actionId) {
        switch (actionId) {
            case 1:
                Toast.makeText(context, messageOne, Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(context, messageTwo, Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(context, messageThree, Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
});
```
	
Want a dimiss listener for whatever reason?

```java
mQuickAction.setOnDismissListener(new PopUpMenu.OnDismissListener() {
    @Override
    public void onDismiss() {
        Toast.makeText(getBaseContext(), "Dismissed!", Toast.LENGTH_SHORT).show();
    }
});
```

Popup Menu Shouldn't be used in all menu cases. The most helpfull cases are on listviews or individual menu cases on recyclerview items. Popup Menu was originally used in ADW Launcher to supply menu actions to app shortcuts, now we have shortcut popups bubbles from Pixel Launcher. Let's not let a good menu die off. Let it live and be used. If it works, don't mess with it.

# ToDo List
- Badge Support for Action Items
