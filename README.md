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

License (Creative Commons Attribution 3.0 Unported)
---------------------------------------------------

This work is licensed under the Creative Commons Attribution 3.0 Unported License. To view a copy of this license, visit http://creativecommons.org/licenses/by/3.0/ or send a letter to Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.

The Code
--------

The sourcecode is splitted in seperated folders:

* /src contains the main code of QuarterBukkit and the API methods
* /integration contains the classes plugin developers use for integrate QuarterBukkit into their plugin
* /paused-src contains main code which isn't finished and paused for a time