QuarterBukkit
=============

QuarterBukkit is a modern modular API extension for Bukkit.
It helps finding and avoiding bugs and mistakes (like accessing the Bukkit API in different thread) and only loading components if they are actually used.
So you can use the Bukkit specification for developing even if QuarterBukkit contains code needs the CraftBukkit implementation to compile.
The API makes a lot of things easier and speeds up the developing process.

There's no work for server operators since QuarterBukkit always checks for updates and new versions.
Furthermore, plugins which use QuarterBukkit automatically download the QuarterBukkit plugin if it is not installed yet.
A server operator doesn't need to download it manually from BukkitDev.

QuarterBukkit features:

* No work for server operators with self-installation and self-update functions.
* New intuitive mistake-catching.
* Thread control.
* Many helping classes for scheduling, config, permissions, commands & more.
* Easy integration for developers.
* OpenSource: Repository on GitHub.

License
-------

Copyright (c) 2013 QuarterCode <http://www.quartercode.com/>

QuarterBukkit may be used under the terms of the GNU General Public License (GPL) v3.0. See the LICENSE.md file or https://www.gnu.org/licenses/gpl-3.0.txt for details.

Using QuarterBukkit in your plugin
==================================

Since documentation can be quite volatile, we prefer to not put it into our readme files.
Therefore, you can find the complete tutorial and full API documentation at http://quartercode.com/wiki/QuarterBukkit.
