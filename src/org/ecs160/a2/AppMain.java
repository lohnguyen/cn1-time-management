package org.ecs160.a2;

import static com.codename1.ui.CN.*;

import com.codename1.ui.*;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.codename1.io.Log;

import org.ecs160.a2.ui.*;

import org.ecs160.a2.utils.Database;

import java.lang.Object;

/**
 * This file was generated by <a href="https://www.codenameone.com/">Codename
 * One</a> for the purpose of building native mobile applications using Java.
 */
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

      Database.reset();
      Database.init();
   }

   public void start() {
      if (current != null) {
         current.show();
         return;
      }

      current = new Form("Task Management App", new BorderLayout());

      setAppTabs();
      setAppToolbars();

      current.show();
   }

   public void stop() {
      current = getCurrentForm();
      if (current instanceof Dialog) {
         ((Dialog) current).dispose();
         current = getCurrentForm();
      }
   }

   private void setAppToolbars() {
      new AppToolbars(current);
      current.setToolbar(AppToolbars.getTaskListToolbar());
   }

   private void setAppTabs() {
      new AppTabs(current);
      current.setPreferredTabIndex(0);
   }
}