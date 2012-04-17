package com.acuddlyheadcrab.util;

/** 
 * @author acuddlyheadcrab
 *
 * This is where all the config paths should lead to. 
 * That way, when a key here is edited, all statements that contain one of these enums point to the right config path.
 * If you're modifying this plugin's config, make sure to add/remove/edit the corresponding enum here!
 */

public enum ConfigKeys {
    
    OPTIONS("Options."),
    OPTS_DEBUG_ONCMD(OPTIONS.key().concat("onCommand")),
    OPTS_GMCANEDIT(OPTIONS.key().concat("Gamemakers_can_edit")),
    OPTS_DURING_GAME(OPTIONS.key().concat("During_games.")),
    OPTS_DURGM_KICKONDISC(OPTS_DURING_GAME.key().concat("kick_on_disconnect")),
    OPTS_DURGM_NOTP(OPTS_DURING_GAME.key().concat("noTeleporting")),
    OPTS_DURGM_NEARCHAT(OPTS_DURING_GAME.key().concat("nearbyTributeChat")),
    OPTS_DURGM_NOMOBS(OPTS_DURING_GAME.key().concat("noMobSpawnInArena")),
    CURRENT_GAMES("Currentgames"),
    GAME_COUNT("Game_count"),
    ARENAS("Arenas."),
    ARN_CENTER_WRLD(".cornucopia.World"),
    ARN_CENTER_X(".cornucopia.x"),
    ARN_CENTER_Y(".cornucopia.y"),
    ARN_CENTER_Z(".cornucopia.z"),
    ARN_MAXDIST(".Maxdistance"),
    ARN_GMS(".gamemakers"),
    ARN_TRIBS(".tributes"),
    ARN_INGAME(".in_game"),
    ARN_INCOUNTDOWN(".in_countdown(DO_NOT_TOUCH)")
    ;
    
    private final String key;
    
    
    private ConfigKeys(String configkey) {
        key = configkey;
    }
    
    public String key(){
        return key;
    }
}
