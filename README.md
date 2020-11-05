# Material_Component_Quickaction_Menu

[![JitPack](https://jitpack.io/v/rgocal/Material_PopUp_Menu.svg)](https://jitpack.io/#rgocal/Material_PopUp_Menu)

A nostalgic dialog action menu from Android 2.3 re-invented for Material Component Design. This popup menu was originally used in ADW Launcher from CM7. I wanted something nostalgic and usefull to use in my launcher projects and this was it. I built this menu in my latest launcher project and decided to share it with the community. It has recently been rewritten from scratch to be smaller, faster and more native to the android os while matching material components in use. This menu should only be used in list or grid views as a guideline. Using it as a native menu for your app is not recomended, use it as 

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
	        implementation 'com.github.rgocal:Material_PopUp_Menu:5.00'
}
```
    
## Action List

There are 4 types of Menus and what actions we add matter. If you are planning to use horizontal menus, use small titles (8-12 characters max). If you plan to have actions with long titles (12-24 characters), you will use these in the Vertical Menus. Each action requires an ID, a Title, a Resource and whether or not its "Sticky". The ID represents the position of the item when it is clicked. Making an item "Sticky" or not means whether or not to dismiss the menu after the item has been clicked or not.

```java
final ActionItem dummyOne = new ActionItem(1, "Dummy 1", getResources().getDrawable(R.drawable.ic_settings), true);
final ActionItem dummyTwo = new ActionItem(2, "Dummy 2", getResources().getDrawable(R.drawable.ic_settings), false);
final ActionItem dummyThree = new ActionItem(3, "Dummy 3", getResources().getDrawable(R.drawable.ic_settings), true);

final ActionItem dummyFour = new ActionItem(4, "Long Dummy Item", getResources().getDrawable(R.drawable.ic_settings), false);
```
    
## Keeping It Simple
	
Initialize the Popup Menu

```java
final HorizontalPopupMenu mQuickAction = new HorizontalPopupMenu(context);
final PixelPopupMenu mQuickAction = new PixelPopupMenu(context);
final VerticalListPopupMenu mQuickAction = new VerticalListPopupMenu(context);
final VerticalPopupMenu mQuickAction = new VerticalPopupMenu(context);
```

HorizontalPopupMenu is the orignal Quick Action Menu from CM7's ADW Launcher. This menu is to show a list of quick action items like "Share, Copy, Delete, Open". This menu also shows an icon above or below the title of the item. It comes with a few optional settings for the developer.

VerticalListPopupMenu is a menu that list a title and an icon beside the title. This is more for larger titled menu actions. "Share with Joe Smoe", "Connect with Henry". It comes with a few optional settings for the developer.

VerticalPopupMenu is an icon only menu and its behavior is very similar to VerticalListPopupMenu.

PixelPopupMenu is a replica of Pixel Launchers shortcut menu of which is shown when users long click on an app. This menu is a combo or both vertical and horizontal menus where the user can inflate 2 menus at once. 
		
These menus have serveral options available for environmental reasons or to make the menus more clear to read in certain themes and styles. Feel free to submit issues or suggestions to further enhance the user experience

```java
mQuickAction.setGravity(0);
mQuickAction.setBackgroundRadias(42);
mQuickAction.setHasActionTitles(true);
```
	
Programically tint the background to the menu. This comes in handy when and if you want to extract a color from an object to apply to the menu.

```java
mQuickAction.setBackgroundColor(colorValue);
```
	
Add your items you created up top, to your menu. This is where you can set boolean if statements to the IDs if you want to toggle them in your menu for customization. Horizontal menu should have anywhere from 3-7 items while the Vertical menu should have anywhere from 1-4 items. The guidelines are the same as if they were launcher shortcuts in the Pixel Launcher popups. If you are using the PixelPoupMenu, pay attention to which list your add your menu items.

```java
mQuickAction.addActionItem(dummyOne);
mQuickAction.addActionItem(dummyTwo);
mQuickAction.addActionItem(dummyThree);
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

Popup Menus Shouldn't be used to replace toolbar menus. These menus are specifically designed for items in a list or grid that are inflated by a long click or if the item in the listview has a 3dot menu. These popup menus hide action items you would of normally added in a toolbar or actionbar in the listview item.

# ToDo List
- Badge Support for Action Items
- More customization
- Material Component Guidelines
- A few more Menu Variants
