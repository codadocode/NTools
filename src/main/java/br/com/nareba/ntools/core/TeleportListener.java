package br.com.nareba.ntools.core;

import cn.nukkit.Player;

public class TeleportListener {
    private String playerName;
    private Player playerRequest;
    private boolean hasRequest = false;
    public TeleportListener(String playerName)   {
        this.playerName = playerName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Player getPlayerRequest() {
        return playerRequest;
    }

    public void setPlayerRequest(Player playerRequest) {
        this.playerRequest = playerRequest;
    }

    public boolean isHasRequest() {
        return hasRequest;
    }

    public void setHasRequest(boolean hasRequest) {
        this.hasRequest = hasRequest;
    }
}
