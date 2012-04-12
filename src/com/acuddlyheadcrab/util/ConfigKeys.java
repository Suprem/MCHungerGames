package com.acuddlyheadcrab.util;


public enum ConfigKeys {
    
    OPTIONS("Options."),
    OPTS_DEBUG_ONCMD(OPTIONS.key().concat("onCommand")),
    OPTS_GMCANEDIT(OPTIONS.key().concat("Gamemakers_can_edit")),
    OPTS_DURING_GAME(OPTIONS.key().concat("During_games.")),
    OPTS_DURGM_KICKONDISC(OPTIONS.key().concat("kick_on_disconnect")),
    OPTS_DURGM_NOTP(OPTIONS.key().concat("noTeleporting")),
    OPTS_DURGM_NEARCHAT(OPTIONS.key().concat("nearbyTributeChat")),
    OPTS_DURGM_NOMOBS(OPTIONS.key().concat("noMobSpawnInArena")),
    CURRENT_GAMES("Currentgames"),
    ARENAS("Arenas."),
    ARN_CENTER_WRLD(".cornucopia.World"),
    ARN_CENTER_X(".cornucopia.x"),
    ARN_CENTER_Y(".cornucopia.y"),
    ARN_CENTER_Z(".cornucopia.z"),
    ARN_MAXDIST(".Maxdistance"),
    ARN_GMS(".gamemakers"),
    ARN_TRIBS(".tributes"),
    ARN_INGAME(".in_game");
    
    private final String key;
    
    
    private ConfigKeys(String configkey) {
        key = configkey;
    }
    
    public String key(){
        return key;
    }
}
