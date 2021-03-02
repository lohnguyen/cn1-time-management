package org.ecs160.a2;

import static com.codename1.ui.CN.*;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;

import com.codename1.util.EasyThread;
import org.ecs160.a2.models.Task;
import org.ecs160.a2.ui.AddNewTask;

import org.ecs160.a2.ui.Summary;
import org.ecs160.a2.ui.TaskList;
import org.ecs160.a2.utils.Database;

import java.io.IOException;
import java.lang.Object;
import java.util.Timer;
import java.util.TimerTask;

public class AppMain {

   private Form current;
   private Resources theme;

   public void init(Object context) {
      // use two network threads instead of one
      updateNetworkThreadCount(2);

      theme = UIManager.initFirstTheme("/theme");

      // Enable Toolbar on all Forms by default
      Toolbar.setGlobalToolbar(true);

      // Pro only feature
      Log.bindCrashProtection(true);

      addNetworkErrorListener(err -> {
         // prevent the event from propagating
         err.consume();
         if (err.getError() != null) {
            Log.e(err.getError());
         }
         Log.sendLogAsync();
         Dialog.show("Connection Error",
                 "There was a networking error in the connection to " +
                         err.getConnectionRequest().getUrl(), "OK",
                 null);
      });

      Database.init();
   }

   public void start() {
      if (current != null) {
         current.show();
         return;
      }

      Database.deleteAll(Task.OBJECT_ID);
      current = new Form("Task Management App", new BorderLayout());

      setToolbar();
      setBottomTabs();

      current.show();
   }

   public void stop() {
      current = getCurrentForm();
      if (current instanceof Dialog) {
         ((Dialog) current).dispose();
         current = getCurrentForm();
      }
   }

   public void destroy() {
   }

   private void setToolbar() {
      Toolbar toolbar = new Toolbar();
      current.setToolbar(toolbar);
      toolbar.setTitle("Tasks");

      Button addButton = new Button();
      addButton.addActionListener(e -> new AddNewTask());

      try {
         addButton.setIcon(Image.createImage("/addbutton.png").scaled(80, 80));
      } catch (IOException e) {
         e.printStackTrace();
      }

      toolbar.addComponent(BorderLayout.EAST, addButton);
   }

   private void setBottomTabs() {
      FontImage taskIcon = FontImage.createMaterial(FontImage.MATERIAL_ALARM,
              "Label", 6);
      FontImage summaryIcon =
              FontImage.createMaterial(FontImage.MATERIAL_ASSESSMENT,
                      "Label", 6);

      Tabs tabs = new Tabs();

      current.add(BorderLayout.CENTER, tabs);
      tabs.addTab("Tasks", taskIcon, new TaskList());
      tabs.addTab("Summary", summaryIcon, new Summary());
      tabs.setSwipeActivated(false); // Disable the swipe to prevent competition with the cards
   }

}

