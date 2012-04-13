package com.acuddlyheadcrab.util;


public enum Perms {
    
    HG_RELOAD("HG.reload"),
    
    HGA_TP("HG.arena.tp"),
    HGA_TPALL("HG.arena.tpall"),
    HGA_INFO("HG.arena.info"),
    HGA_NEW("HG.arena.new"),
    HGA_DEL("HG.arena.del"),
    HGA_LIST("HG.arena.list"),
    HGA_RENAME("HG.arena.rename"),
    
    HGAE_SETCCP("HG.edit.setcornucopia"),
    HGAE_LIMIT("HG.edit.limit"),
    HGAE_ADDGM("HG.edit.addgm"),
    HGAE_ADDTRIB("HG.edit.addtrib"),
    HGAE_REMOVEGM("HG.edit.removegm"),
    HGAE_REMOVETRIB("HG.edit.removetrib"),
    
    HGG_START("HG.game.start"),
    HGG_STOP("HG.game.stop"),
    
    SPC("HG.spawncornucopia")
    ;
    
    private final String permission;
    
    Perms(String perm){
        permission = perm;
    }

    public String perm() {
        return permission;
    }
}

/*    String
            perm_hg_reload = "HG.reload",
            perm_hga = "HG.arena",
                perm_hga_tp = "HG.arena.tp",
                perm_hga_info = "HG.arena.info",
                perm_hga_new = "HG.arena.new",
                perm_hga_del = "HG.arena.del",
                perm_hga_list = "HG.arena.list",
            perm_hgae = "HG.edit",
                perm_hgae_setcorncp = "HG.edit.setcornucopia",
                perm_hgae_limit = "HG.edit.limit",
                perm_hgae_addgm = "HG.edit.addgm",
                perm_hgae_addtrib = "HG.edit.addtrib",
                perm_hgae_removegm = "HG.edit.removegm",
                perm_hgae_removetrib = "HG.edit.removetrib",
            perm_hgg = "HG.game",
                perm_hgg_start = "HG.game.start",
                perm_hgg_stop = "HG.game.stop",
            perm_spc = "HG.spawncornucopia"
        ;
*/