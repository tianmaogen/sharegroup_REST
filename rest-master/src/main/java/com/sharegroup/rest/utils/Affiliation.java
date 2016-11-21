package com.sharegroup.rest.utils;

/**
 * Created by Administrator on 2016/1/12.
 */
public enum Affiliation {

    /**
     * Owner of the room.
     */
    owner(10),

    /**
     * Administrator of the room.
     */
    admin(20),

    /**
     * A user who is on the "whitelist" for a members-only room or who is registered
     * with an open room.
     */
    member(30),

    /**
     * A user who has been banned from a room.
     */
    outcast(40),

    /**
     * A user who doesn't have an affiliation. This kind of users can register with members-only
     * rooms and may enter an open room.
     */
    none(50);

    private int value;

    Affiliation(int value) {
        this.value = value;
    }

    /**
     * Returns the value for the role.
     *
     * @return the value.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns the affiliation associated with the specified value.
     *
     * @param value the value.
     * @return the associated affiliation.
     */
    public static Affiliation valueOf(int value) {
        switch (value) {
            case 10:
                return owner;
            case 20:
                return admin;
            case 30:
                return member;
            case 40:
                return outcast;
            default:
                return none;
        }
    }
}
