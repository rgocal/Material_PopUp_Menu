package com.gsd.mpm.materialpopupexample;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.chip.Chip;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.gsd.dynamicMenu.data.ActionItem;
import com.gsd.dynamicMenu.data.SimpleActionItem;
import com.gsd.dynamicMenu.menus.HorizontalPopupMenu;
import com.gsd.dynamicMenu.menus.PixelPopupMenu;
import com.gsd.dynamicMenu.menus.VerticalListPopupMenu;
import com.gsd.dynamicMenu.menus.VerticalPopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    private VerticalListPopupMenu verticalListPopupMenu;
    private HorizontalPopupMenu horizontalPopupMenu;
    private PixelPopupMenu pixelPopupMenu;
    private VerticalPopupMenu verticalPopupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setupVerticalList();
        setupHorizontalList();
        setupPixelList();
        setupVerticalSimpleList();

        Chip chipOne = findViewById(R.id.button_one);

        chipOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verticalListPopupMenu.show(view);
            }
        });

        Chip chipTwo = findViewById(R.id.button_two);
        chipTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                horizontalPopupMenu.show(view);
            }
        });

        Chip chipThree = findViewById(R.id.button_three);
        chipThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pixelPopupMenu.show(view);
            }
        });
        Chip chipFour = findViewById(R.id.button_four);
        chipFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verticalPopupMenu.show(view);
            }
        });

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setupVerticalSimpleList() {
        verticalPopupMenu = new VerticalPopupMenu(this);

        verticalPopupMenu.setBackgroundColor(ContextCompat.getColor(this, R.color.design_default_color_primary));
        verticalPopupMenu.setBackgroundRadias(42);

        verticalPopupMenu.setOnDismissListener(new VerticalPopupMenu.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        verticalPopupMenu.addActionItem(new SimpleActionItem(1,ContextCompat.getDrawable(this, android.R.drawable.ic_menu_call), true));
        verticalPopupMenu.addActionItem(new SimpleActionItem(2, ContextCompat.getDrawable(this, android.R.drawable.sym_action_call), false));
        verticalPopupMenu.addActionItem(new SimpleActionItem(3,ContextCompat.getDrawable(this, android.R.drawable.sym_call_missed), true));

        verticalPopupMenu.setOnActionItemClickListener(new VerticalPopupMenu.OnActionItemClickListener() {
            @Override
            public void onItemClick(VerticalPopupMenu source, int pos, int actionId) {
                if(actionId == 1){

                }else if(actionId == 2){

                }else if(actionId == 3){

                }
            }
        });
    }

    private void setupPixelList() {
        pixelPopupMenu = new PixelPopupMenu(this);

        pixelPopupMenu.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));
        pixelPopupMenu.setBackgroundRadias(42);
        pixelPopupMenu.setHasIconColorFilter(true);

        pixelPopupMenu.setOnDismissListener(new PixelPopupMenu.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        pixelPopupMenu.addVerticalActionItem(new ActionItem(1, "Call Info", ContextCompat.getDrawable(this, android.R.drawable.ic_menu_call), true));
        pixelPopupMenu.addVerticalActionItem(new ActionItem(2, "Call", ContextCompat.getDrawable(this, android.R.drawable.sym_action_call), false));
        pixelPopupMenu.addVerticalActionItem(new ActionItem(3, "End Call", ContextCompat.getDrawable(this, android.R.drawable.sym_call_missed), true));

        pixelPopupMenu.addHorizontalActionItem(new SimpleActionItem(4, ContextCompat.getDrawable(this, android.R.drawable.ic_menu_call), true));
        pixelPopupMenu.addHorizontalActionItem(new SimpleActionItem(5, ContextCompat.getDrawable(this, android.R.drawable.sym_action_call), false));
        pixelPopupMenu.addHorizontalActionItem(new SimpleActionItem(6, ContextCompat.getDrawable(this, android.R.drawable.sym_call_missed), true));
        pixelPopupMenu.addHorizontalActionItem(new SimpleActionItem(7,  ContextCompat.getDrawable(this, android.R.drawable.ic_menu_call), true));

        pixelPopupMenu.setOnActionItemClickListener(new PixelPopupMenu.OnActionItemClickListener() {
            @Override
            public void onItemClick(PixelPopupMenu source, int pos, int actionId) {
                if(actionId == 1){

                }else if(actionId == 2){

                }else if(actionId == 3){

                }
            }
        });
    }

    private void setupHorizontalList() {
        horizontalPopupMenu = new HorizontalPopupMenu(this);

        horizontalPopupMenu.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        horizontalPopupMenu.setBackgroundRadias(42);
        horizontalPopupMenu.setGravity(0);
        horizontalPopupMenu.setHasActionTitles(true);

        horizontalPopupMenu.setOnDismissListener(new HorizontalPopupMenu.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        horizontalPopupMenu.addActionItem(new ActionItem(1, "Call Info", ContextCompat.getDrawable(this, android.R.drawable.ic_menu_call), true));
        horizontalPopupMenu.addActionItem(new ActionItem(2, "Call", ContextCompat.getDrawable(this, android.R.drawable.sym_action_call), false));
        horizontalPopupMenu.addActionItem(new ActionItem(3, "End Call", ContextCompat.getDrawable(this, android.R.drawable.sym_call_missed), true));

        horizontalPopupMenu.setOnActionItemClickListener(new HorizontalPopupMenu.OnActionItemClickListener() {
            @Override
            public void onItemClick(HorizontalPopupMenu source, int pos, int actionId) {
                if(actionId == 1){

                }else if(actionId == 2){

                }else if(actionId == 3){

                }
            }
        });
    }

    private void setupVerticalList() {
        verticalListPopupMenu = new VerticalListPopupMenu(this);

        verticalListPopupMenu.setBackgroundColor(ContextCompat.getColor(this, R.color.popup_body_color));
        verticalListPopupMenu.setBackgroundRadias(42);

        verticalListPopupMenu.setOnDismissListener(new VerticalListPopupMenu.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });

        verticalListPopupMenu.addActionItem(new ActionItem(1, "Call Info", ContextCompat.getDrawable(this, android.R.drawable.ic_menu_call), true));
        verticalListPopupMenu.addActionItem(new ActionItem(2, "Call", ContextCompat.getDrawable(this, android.R.drawable.sym_action_call), false));
        verticalListPopupMenu.addActionItem(new ActionItem(3, "End Call", ContextCompat.getDrawable(this, android.R.drawable.sym_call_missed), true));

        verticalListPopupMenu.setOnActionItemClickListener(new VerticalListPopupMenu.OnActionItemClickListener() {
            @Override
            public void onItemClick(VerticalListPopupMenu source, int pos, int actionId) {
                if(actionId == 1){

                }else if(actionId == 2){

                }else if(actionId == 3){

                }
            }
        });
    }

}

