package com.jinkai.nav_drawer;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;

public class ShortcutCreator {
    public static boolean createShortcut(Context context, String packageName, String url, String subject) {
        ShortcutManager shortcutManager = context.getSystemService(ShortcutManager.class);
        if (shortcutManager == null || !shortcutManager.isRequestPinShortcutSupported()) {
            throw new RuntimeException("ShortcutManager is not available");
        }

        Intent shortcutIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        shortcutIntent.setPackage(packageName);

        ShortcutInfo shortcut = new ShortcutInfo.Builder(context, "id" + url).setShortLabel(subject).setIcon(Icon.createWithResource(context, R.drawable.ic_launcher_background)).setIntent(shortcutIntent).build();
        return shortcutManager.requestPinShortcut(shortcut, null);
    }
}
