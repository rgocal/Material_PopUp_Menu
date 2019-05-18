package com.gsd.mpm.materialpopupexample;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gsd.mpm.materialpopupmenu.ActionItem;
import com.gsd.mpm.materialpopupmenu.PopUpMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private PopUpMenu mQuickAction;
    private int messageOne;
    private int messageTwo;
    private int messageThree;
    private int messageFour;
    private int messageFive;

    private ArrayList<DataItems> itemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        messageOne = R.string.msg_one;
        messageTwo = R.string.msg_two;
        messageThree = R.string.msg_three;
        messageFour = R.string.msg_four;
        messageFive = R.string.msg_five;

        final RecyclerView itemGrid = findViewById(R.id.list);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
        itemGrid.setLayoutManager(layoutManager);
        MyAdapter myAdapter = new MyAdapter(this, itemList, new CustomClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                mQuickAction.setMenuTitle(itemList.get(position).getTitle());

                mQuickAction.setVertScrollColor(itemList.get(position).getColor());
                mQuickAction.setTitleBackgroundColor(itemList.get(position).getColor());
                mQuickAction.setHorizontalScrollColor(itemList.get(position).getColor());
                mQuickAction.setOuterColor(itemList.get(position).getColor());

                mQuickAction.show(itemGrid.getChildAt(position));

            }

            @Override
            public void onItemLongClick(View v, int position) {

            }
        });
        itemGrid.setAdapter(myAdapter);

        itemList.add(new DataItems(1, "ONE", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorAccent)));
        itemList.add(new DataItems(1, "TWO", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimary)));
        itemList.add(new DataItems(1, "THREE", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimaryDark)));
        itemList.add(new DataItems(1, "FOUR", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorAccent)));
        itemList.add(new DataItems(1, "FIVE", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimary)));
        itemList.add(new DataItems(1, "SIX", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimaryDark)));
        itemList.add(new DataItems(1, "SEVEN", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorAccent)));
        itemList.add(new DataItems(1, "EIGHT", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimary)));
        itemList.add(new DataItems(1, "NINE", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimaryDark)));
        itemList.add(new DataItems(1, "TEN", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorAccent)));
        itemList.add(new DataItems(1, "ELEVEN", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimary)));
        itemList.add(new DataItems(1, "TWELVE", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimaryDark)));


        itemList.add(new DataItems(1, "ONE", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorAccent)));
        itemList.add(new DataItems(1, "TWO", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimary)));
        itemList.add(new DataItems(1, "THREE", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimaryDark)));
        itemList.add(new DataItems(1, "FOUR", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorAccent)));
        itemList.add(new DataItems(1, "FIVE", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimary)));
        itemList.add(new DataItems(1, "SIX", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimaryDark)));
        itemList.add(new DataItems(1, "SEVEN", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorAccent)));
        itemList.add(new DataItems(1, "EIGHT", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimary)));
        itemList.add(new DataItems(1, "NINE", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimaryDark)));
        itemList.add(new DataItems(1, "TEN", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorAccent)));
        itemList.add(new DataItems(1, "ELEVEN", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimary)));
        itemList.add(new DataItems(1, "TWELVE", "Number", ContextCompat.getDrawable(this, R.drawable.ic_android), ContextCompat.getColor(this,R.color.colorPrimaryDark)));

        //Initialize the actions you want
        //Set an ID, a Title, and a Resource
        final ActionItem dummyOne = new ActionItem(1, "Dummy 1", getResources().getDrawable(R.drawable.ic_settings));
        final ActionItem dummyTwo = new ActionItem(2, "Dummy 2", getResources().getDrawable(R.drawable.ic_settings));
        final ActionItem dummyThree = new ActionItem(3, "Dummy 3", getResources().getDrawable(R.drawable.ic_settings));

        final ActionItem dummyVertOne = new ActionItem(4, "Shortcut One", getResources().getDrawable(R.drawable.ic_android));
        final ActionItem dummyVertTwo = new ActionItem(5, "Shortcut Two", getResources().getDrawable(R.drawable.ic_android));


        //Enable them to be sticky if you want the menu to not dismiss after clicking it
        dummyOne.setSticky(true);
        dummyTwo.setSticky(true);
        dummyThree.setSticky(false);

        //Initialize the popup
        mQuickAction = new PopUpMenu(MainActivity.this);
        //Pretty obvious what this is
        mQuickAction.mAnimateTrack(true);
        //Set true for white tinted action titles and resources, set false for black
        mQuickAction.setLightTheme(true);
        //And again, pretty obvious what this is
        mQuickAction.setScrollBar(false);
        //Set animation style
        mQuickAction.setAnimStyle(1);
        //Set has titles or not
        mQuickAction.setHasActionTitles(true);


        //Enable Header Title and Vertical Menu
        mQuickAction.hasVerticalExpansion(true);
        mQuickAction.setHasHeaderTitle(true);
        String menuTitle = "Material Popup";
        mQuickAction.setMenuTitle(menuTitle);

        //Set whether or not you want the vertical icons to follow the light/dark theme
        //If set to false, it will allow the icon to retain its colors
        //Usefull if being used like a launcher app shortcut menu list
        mQuickAction.themeVerticalIcons(true);

        //Set Popup Colors programically or override the project Style
        mQuickAction.setHorizontalScrollColor(ContextCompat.getColor(this, R.color.orange));
        mQuickAction.setVertScrollColor(ContextCompat.getColor(this, R.color.orange));
        mQuickAction.setTitleBackgroundColor(ContextCompat.getColor(this, R.color.orange));
        mQuickAction.setOuterColor(ContextCompat.getColor(this, R.color.orange));

        //Add the actions to the popup menu
        //Try adding if statements sometime to control what actions are present to the menu!
        //Guidelines: 3-7 items
        mQuickAction.addActionItem(dummyOne);
        mQuickAction.addActionItem(dummyTwo);
        mQuickAction.addActionItem(dummyThree);
        mQuickAction.addActionItem(dummyTwo);
        mQuickAction.addActionItem(dummyTwo);

        //Guidelines:1-4 items
        mQuickAction.addVerticalActionItem(dummyVertOne);
        mQuickAction.addVerticalActionItem(dummyVertTwo);
        mQuickAction.addVerticalActionItem(dummyVertTwo);
        mQuickAction.addVerticalActionItem(dummyVertTwo);

        //Add listeners to each action item
        mQuickAction.setOnActionItemClickListener(new PopUpMenu.OnActionItemClickListener() {
            @Override
            public void onItemClick(PopUpMenu source, int pos, int actionId) {
                if (actionId == 1) {
                    Toast.makeText(MainActivity.this, messageOne, Toast.LENGTH_SHORT).show();
                }
                if (actionId == 2) {
                    Toast.makeText(MainActivity.this, messageTwo, Toast.LENGTH_SHORT).show();

                }
                if (actionId == 3) {
                    Toast.makeText(MainActivity.this, messageThree, Toast.LENGTH_SHORT).show();
                }
                if (actionId == 4) {
                    Toast.makeText(MainActivity.this, messageFour, Toast.LENGTH_SHORT).show();
                }
                if (actionId == 5) {
                    Toast.makeText(MainActivity.this, messageFive, Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Add a dismiss listener
        //Remove if you want to disregard
        mQuickAction.setOnDismissListener(new PopUpMenu.OnDismissListener() {
            @Override
            public void onDismiss() {
                Toast.makeText(getBaseContext(), "Dismissed!", Toast.LENGTH_SHORT).show();
            }
        });


        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mQuickAction.show(fab);
            }
        });
    }
}

