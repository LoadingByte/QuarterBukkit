/*
 * This file is part of QuarterBukkit-Plugin.
 * Copyright (c) 2012 QuarterCode <http://www.quartercode.com/>
 *
 * QuarterBukkit-Plugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * QuarterBukkit-Plugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with QuarterBukkit-Plugin. If not, see <http://www.gnu.org/licenses/>.
 */

package com.quartercode.quarterbukkit.api.objectsystem.object;

/**
 * An enumeration containing the different particle types that can be spawned by a {@link ParticleObject}.
 * Note that some particles support a parameter.
 * If that's the case, it is explicitly mentioned in the particle description and can be set in the {@link ParticleDefinition}.<br>
 * <br>
 * This class is derived from DarkBlade12's ParticleEffect Library.
 * All credit to the enumeration part of his library goes to him.
 * The original disclaimer of the class is shown below:
 * 
 * <pre>
 * <b>ParticleEffect Library</b>
 * 
 * This library was created by DarkBlade12 based on content related to particles of microgeek (names and packet parameters),
 * it allows you to display all Minecraft particle effects on a Bukkit server
 * You are welcome to use it, modify it and redistribute it under the following conditions:
 * 
 * - Don't claim this class as your own
 * - Don't remove this disclaimer
 * 
 * <i>It would be nice if you provide credit to me if you use this class in a published project</i>
 * 
 * Author: DarkBlade12
 * Version: 1.5
 * </pre>
 * 
 * @see ParticleDefinition
 */
public enum ParticleType {

    /**
     * A particle effect that looks like a crowd of gray balls which are fading away.
     * It is displayed by exploding tnt and creepers.
     */
    HUGE_EXPLOSION ("hugeexplosion", false),
    /**
     * A particle effect that looks like a gray ball which is fading away.
     * It is displayed by exploding ghast fireballs and wither skulls.
     * The parameter slightly influences the size of this particle effect.
     */
    LARGE_EXPLODE ("largeexplode", true),
    /**
     * A particle effect that looks like a white star which is sparkling.
     * It is displayed by launching fireworks.
     * The parameter influences the velocity at which the particle flies off in a random direction.
     */
    FIREWORKS_SPARK ("fireworksSpark", true),
    /**
     * A particle effect that looks like bubble.
     * It is displayed by swimming entities and arrows in water.
     * Furthermore, it can only be displayed inside water.
     * The parameter influences the velocity at which the particle flies off in a random direction.
     */
    BUBBLE ("bubble", true),
    /**
     * A particle effect that looks like a tiny blue square.
     * It is displayed by water.
     * Furthermore, it can only be displayed inside water.
     */
    SUSPEND ("suspend", false),
    /**
     * A particle effect that looks like a tiny gray square.
     * It is displayed by air when close to bedrock and the in the void.
     */
    DEPTH_SUSPEND ("depthSuspend", false),
    /**
     * A particle effect that looks like a tiny gray square.
     * It is displayed by mycelium.
     */
    TOWN_AURA ("townaura", false),
    /**
     * A particle effect that looks like a light brown cross.
     * It is displayed when landing a critical hit and by arrows.
     * The parameter influences the velocity at which the particle flies off in a random direction.
     */
    CRIT ("crit", true),
    /**
     * A particle effect that looks like a cyan star.
     * It is displayed when landing a hit with an enchanted weapon.
     * The parameter influences the velocity at which the particle flies off in a random direction.
     */
    MAGIC_CRIT ("magicCrit", true),
    /**
     * A particle effect that looks like a little gray cloud.
     * It is displayed by primed tnt, torches, droppers, dispensers, end portals, brewing stands and monster spawners.
     * The parameter influences the velocity at which the particle flies off in a random direction.
     */
    SMOKE ("smoke", true),
    /**
     * A particle effect that looks like a colored swirl.
     * It is displayed by entities with active potion effects.
     * The parameter causes the particle to be colored black when set to 0.
     */
    MOB_SPELL ("mobSpell", true),
    /**
     * A particle effect that looks like a transparent colored swirl.
     * It is displayed by entities with active potion effects applied through a beacon.
     * The parameter causes the particle to be always colored black when set to 0.
     */
    MOB_SPELL_AMBIENT ("mobSpellAmbient", true),
    /**
     * A particle effect that looks like a white swirl.
     * It is displayed when splash potions or bottles o' enchanting hit something.
     * The parameter causes the particle to only move upwards when set to 0.
     */
    SPELL ("spell", true),
    /**
     * A particle effect that looks like a white cross.
     * It is displayed when instant splash potions hit something.
     * The parameter causes the particle to only move upwards when set to 0.
     */
    INSTANT_SPELL ("instantSpell", true),
    /**
     * A particle effect that looks like a purple cross.
     * It is displayed by witches.
     * The parameter causes the particle to only move upwards when set to 0.
     */
    WITCH_MAGIC ("witchMagic", true),
    /**
     * A particle effect that looks like a colored note.
     * It is displayed by note blocks.
     * The parameter causes the particle to be colored green when set to 0.
     */
    NOTE ("note", true),
    /**
     * A particle effect that looks like a purple cloud.
     * It is displayed by nether portals, endermen, ender pearls, eyes of ender, ender chests and dragon eggs.
     * The parameter influences the spread of this particle effect.
     */
    PORTAL ("portal", true),
    /**
     * A particle effect that looks like a cryptic white letter.
     * It is displayed by enchantment tables which are nearby bookshelves.
     * The parameter influences the spread of this particle effect.
     */
    ENCHANTMENT_TABLE ("enchantmenttable", true),
    /**
     * A particle effect that looks like a white cloud.
     * It is displayed by exploding tnt and creepers.
     * The parameter influences the velocity at which the particle flies off in a random direction.
     */
    EXPLODE ("explode", true),
    /**
     * A particle effect that looks like a tiny flame.
     * It is displayed by torches, active furnaces, magma cubes and monster spawners.
     * The parameter influences the velocity at which the particle flies off in a random direction.
     */
    FLAME ("flame", true),
    /**
     * A particle effect that looks like a spark.
     * It is displayed by lava.
     */
    LAVA ("lava", false),
    /**
     * A particle effect that looks like a transparent gray square.
     * It is currently unused.
     */
    FOOTSTEP ("footstep", false),
    /**
     * A particle effect that looks like a blue drop.
     * It is displayed by swimming entities, rain dropping on the ground and shaking wolves.
     */
    SPLASH ("splash", false),
    /**
     * A particle effect that looks like a blue droplet.
     * It is displayed on water when fishing.
     * The parameter influences the velocity at which the particle flies off in a random direction.
     */
    WAKE ("wake", true),
    /**
     * A particle effect that looks like a large gray cloud.
     * It is displayed by fire, minecarts with furnace and blazes.
     * The parameter influences the velocity at which the particle flies off in a random direction.
     */
    LARGE_SMOKE ("largesmoke", true),
    /**
     * A particle effect that looks like a large white cloud.
     * It is displayed when a mob dies.
     * The parameter influences the velocity at which the particle flies off in a random direction.
     */
    CLOUD ("cloud", true),
    /**
     * A particle effect that looks like a tiny colored cloud.
     * It is displayed by redstone ore, powered redstone, redstone torches and redstone repeaters.
     * The parameter causes the particle to be colored red when set to 0.
     */
    RED_DUST ("reddust", true),
    /**
     * A particle effect that looks like a tiny part of the snowball icon.
     * It is displayed when snowballs or eggs hit something.
     */
    SNOWBALL_POOF ("snowballpoof", false),
    /**
     * A particle effect that looks like a blue drip.
     * It is displayed by blocks beneath a water source.
     */
    DRIP_WATER ("dripWater", false),
    /**
     * A particle effect that looks like an orange drip.
     * It is displayed by blocks beneath a lava source.
     */
    DRIP_LAVA ("dripLava", false),
    /**
     * A particle effect that looks like a tiny white cloud.
     * It is currently unused.
     * The parameter influences the velocity at which the particle flies off in a random direction.
     */
    SNOW_SHOVEL ("snowshovel", true),
    /**
     * A particle effect that looks like a tiny part of the slimeball icon.
     * It is displayed by slimes.
     */
    SLIME ("slime", false),
    /**
     * A particle effect that looks like a red heart.
     * It is displayed when breeding and taming animals.
     */
    HEART ("heart", false),
    /**
     * A particle effect that looks like a cracked gray heart.
     * It is displayed when attacking a villager in a village.
     */
    ANGRY_VILLAGER ("angryVillager", false),
    /**
     * A particle effect that looks like a green star.
     * It is displayed when using bone meal and trading with a villager in a village.
     */
    HAPPY_VILLAGER ("happyVillager", false);

    private final String  name;
    private final boolean hasParameter;

    private ParticleType(String name, boolean hasParameter) {

        this.name = name;
        this.hasParameter = hasParameter;
    }

    /**
     * Returns the name of the particle type.
     * It is internally used by the minecraft client to select and render the particle.
     * 
     * @return The particle name.
     */
    public String getName() {

        return name;
    }

    /**
     * Returns whether the particle types allows a parameter to be sent.
     * The parameter changes the look or the behavior of the particle on the minecraft client.
     * 
     * @return Whether a particle parameter is allowed.
     */
    public boolean hasParameter() {

        return hasParameter;
    }

}
