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

package com.quartercode.quarterbukkit.api.protocol;

import java.util.List;
import com.quartercode.quarterbukkit.api.reflect.ClassTemplate;
import com.quartercode.quarterbukkit.api.reflect.SafeField;
import com.quartercode.quarterbukkit.api.reflect.refs.PacketRef;

/**
 * 
 * i will add the JavaDoc later!
 * 
 */

public enum PacketType {

    UNKNOW (-1), KEEP_ALIVE (0), LOGIN (1), CHAT (3), UPDATE_TIME (4), ENTITY_EQUIPMENT (5), SPAWN_POSITION (6), UPDATE_HEALTH (8), RESPAWN (9), FLYING (10), PLAYER_POSITION (11), PLAYER_LOOK (12), PLAYER_LOOK_MOVE (13), BLOCK_ITEM_SWITCH (16), ENTITY_LOCATION_ACTION (17), ARM_ANIMATION (18), NAMED_ENTITY_SPAWN (20), COLLECT (22), VEHICLE_SPAWN (23), MOB_SPAWN (24), ENTITY_PAINTING (25), ADD_EXP_ORB (26), ENTITY_VELOCITY (28), DESTROY_ENTITY (29), ENTITY (30), REL_ENTITY_MOVE (31), ENTITY_LOOK (32), REL_ENTITY_MOVE_LOOK (33), ENTITY_TELEPORT (34), ENTITY_HEAD_ROTATION (35), ENTITY_STATUS (38), ATTACH_ENTITY (39), ENTITY_METADATA (40), MOB_EFFECT (41), REMOVE_MOB_EFFECT (42), SET_EXPERIENCE (43), UPDATE_ATTRIBUTES (44), MAP_CHUNK (51), MULTI_BLOCK_CHANGE (52), BLOCK_CHANGE (53), PLAY_NOTE_BLOCK (54), BLOCK_BREAK_ANIMATION (55), MAP_CHUNK_BULK (56), EXPLOSION (60), WORLD_EVENT (61), NAMED_SOUND_EFFECT (62), WORLD_PARTICLES (63), BED (70), WEATHER (71), OPEN_WINDOW (100), CLOSE_WINDOW (101), SET_SLOT (103), WINDOW_ITEMS (104), CRAFT_PROGRESS_BAR (105), TRANSACTION (106), SET_CREATIVE_SLOT (107), UPDATE_SIGN (130), ITEM_DATA (131), TILE_ENTITY_DATA (132), OPEN_TILE_ENTITY (133), STATISTIC (200), PLAYER_INFO (201), ABILITIES (202), TAB_COMPLETE (203), SCOREBOARD_OBJECTIVE (206), UPDATE_SCORE (207), DISPLAY_SCOREBOARD (208), TEAMS (209), CUSTOM_PAYLOAD (250), KEY_RESPONSE (252), KEY_REQUEST (253), KICK_DISCONNECT (255);

    private int              id;
    private ClassTemplate<?> packetTemplate;
    private final String[]   fieldNames;

    private PacketType(int id) {

        this.id = id;
        Class<?> type = (Class<?>) PacketRef.getEvilMap().get(id);

        if (type == null) {
            packetTemplate = null;
            fieldNames = new String[0];
            return;
        }

        packetTemplate = ClassTemplate.create(type);

        List<SafeField<?>> fields = packetTemplate.getFields();
        fieldNames = new String[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            SafeField<?> field = fields.get(i);
            fieldNames[i] = field.getName();
        }
    }

    public Object getPacket() {

        if (packetTemplate == null) {
            return null;
        } else {
            return packetTemplate.newInstance();
        }
    }

    public String getField(int index) {

        return index >= 0 && index < fieldNames.length ? fieldNames[index] : null;
    }

    public ClassTemplate<?> getPacketTemplate() {

        return packetTemplate;
    }

    public int getId() {

        return id;
    }
}
