package com.template.vpn;

import com.template.config.LibConfig;

/**
 * Profile from server
 */
public interface Constants {
    String PROTOCOL_PRIVACY = LibConfig.PROTOCOL_PRIVACY;
    String PROTOCOL_SERVICE = LibConfig.PROTOCOL_SERVICE;
    String FEEDBACK_EMAIL = LibConfig.FEEDBACK_EMAIL;

    String BASE_URL = LibConfig.BASE_URL;

    /**
     * Global Profile API
     */
    String PATH_CONFIG = LibConfig.PATH_CONFIG;

    /**
     * VPN list API
     */
    String PATH_GAME_NODES = LibConfig.PATH_GAME_NODES;

    /**
     * VPN Conn Status API
     */
    String PATH_REPORT_NODE = LibConfig.PATH_REPORT_NODE;


    String LOCATION_URL_1ST = LibConfig.PATH_LOCATION_1;

    String LOCATION_URL_2ND = LibConfig.PATH_LOCATION_2;
    String LOCATION_URL_3RD = LibConfig.PATH_LOCATION_3;
    String LOCATION_URL_4TH = LibConfig.PATH_LOCATION_4;
}
