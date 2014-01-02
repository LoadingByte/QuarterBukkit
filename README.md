QuarterBukkit
=============

QuarterBukkit is a modern modular API-Extension for Bukkit. It helps finding and avoiding bugs and mistakes (like accessing the Bukkit API in different thread) and only load components when there are used. So you can use the Bukkit for developing even if QuarterBukkit contains code needing the CraftBukkit. The API makes a lot of things easier and speeds up the developing process.
There's no work for server operators because QuarterBukkit load itself from BukkitDev and always check for updates and donwload new versions.

QuarterBukkit - The Bukkit API-Extension

QuarterBukkit features:

* No work for server operators with self-download and self-update functions.
* New intuitive mistake-catching.
* Thread control.
* Many helping classes for scheduling, config, permissions, commands & more.
* Easy integration for developers.
* OpenSource: Repository on GitHub.

License
-------

Copyright (c) 2013 QuarterCode <http://www.quartercode.com/>

Disconnected may be used under the terms of either the GNU General Public License (GPL). See the LICENSE.md file for details.

The Code
--------

The sourcecode is splitted in seperated folders:

* /src contains the main code of QuarterBukkit and the API methods
* /integration contains the classes plugin developers use for integrate QuarterBukkit into their plugin
* /paused-src contains main code which isn't finished and paused for a time



This document should show the first steps with QuarterBukkit. If you want to read the complete tutorial, go on http://github.com/QuarterCode/QuarterBukkit/wiki.

Use QuarterBukkit in your plugin
================================

Setup
-----

First, you have to add the QuarterBukkit.jar to the classpath of your project.
For now, you have to do it manually, but we will set up a maven repository for the project soon.

Simply put all classes from /Integration into a package of your plugin (I would use a util-package) and call QuarterBukkitIntegration.integrate() in your onEnable()-method. The method returns
if the integration was sucessfully, so if your plugin doesn't work with QuarterBukkit, you can disable it and exit the method.

Example:

    @Override
    public void onEnable() {
    
        if (!QuarterBukkitIntegration.integrate(this)) {
            return;
        }
    
        // Your code here
    }

Then add QuarterBukkit to your softdepend-list in the plugin.yml. If you don't have this entry, simply add this line:

    softdepend: [QuarterBukkit]

And you're done! Now you can use all the amazing QuarterBukkit-features! See the JavaDoc and [API-Page](API Overview) for more information on those features.

Important Notes
---------------

If you want to use QuarterBukkit in your main class, remember one thing: Don't import/use any QuarterBukkit class or method anywhere in your main class. You cannot import something from QuarterBukkit
before using QuarterBukkitIntegration! That will cause NoClassDefFoundErrors because QuarterBukkit isn't installed yet!

We recommend to use a class like Enabler and put the enabling code there. Then you can call the external enabling method from your main class.
Keep in mind: Always check your plugins for this issue!
