package com.gsd.mpm.materialpopupexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.gsd.mpm.materialpopupmenu.ActionItem;
import com.gsd.mpm.materialpopupmenu.PopUpMenu;

public class MainActivity extends AppCompatActivity {

    private PopUpMenu mQuickAction;
    private int messageOne;
    private int messageTwo;
    private int messageThree;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.textView);

        messageOne = R.string.msg_one;
        messageTwo = R.string.msg_two;
        messageThree = R.string.msg_three;

        //Initialize the actions you want
        //Set an ID, a Title, and a Resource
        final ActionItem dummyOne = new ActionItem(1, "Dummy 1", getResources().getDrawable(R.drawable.ic_settings));
        final ActionItem dummyTwo = new ActionItem(2, "Dummy 2", getResources().getDrawable(R.drawable.ic_settings));
        final ActionItem dummyThree = new ActionItem(3, "Dummy 3", getResources().getDrawable(R.drawable.ic_settings));

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
        mQuickAction.setAnimStyle(4);
        //Set has titles or not
        mQuickAction.setHasTitles(true);

        //Not working yet...
        //mQuickAction.setScrollColor(R.color.orange);
        //mQuickAction.setTrackColor(R.color.orange);
        //mQuickAction.setBodyColor(R.color.orange);

        //Add the actions to the popup menu
        //Try adding if statements sometime to control what actions are present to the menu!
        mQuickAction.addActionItem(dummyOne);
        mQuickAction.addActionItem(dummyTwo);
        mQuickAction.addActionItem(dummyThree);

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
                mQuickAction.show(textView);
            }
        });
    }
}

