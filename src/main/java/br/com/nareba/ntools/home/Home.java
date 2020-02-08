package br.com.nareba.ntools.home;

import cn.nukkit.math.Vector3;

public class Home {
    private String homeName;
    private double posX;
    private double posY;
    private double posZ;
    private String levelName;
    public Home(String homeName, double posX, double posY, double posZ, String levelName)   {
        this.homeName = homeName;
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.levelName = levelName;
    }
    public Home(String homeName, Vector3 pos, String levelName)   {
        this.homeName = homeName;
        this.posX = pos.x;
        this.posY = pos.y;
        this.posZ = pos.z;
        this.levelName = levelName;
    }

    public String getHomeName() {
        return homeName;
    }

    public void setHomeName(String homeName) {
        this.homeName = homeName;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public String getLevelName()   {
        return this.levelName;
    }
    public void setLevelName(String levelName)   {
        this.levelName = levelName;
    }

    public void setPosZ(double posZ) {
        this.posZ = posZ;
    }
    public Vector3 getPosition()   {
        return new Vector3(posX, posY, posZ);
    }
    public void setPosition(Vector3 pos)   {
        this.posX = pos.x;
        this.posY = pos.y;
        this.posZ = pos.z;
    }
}
