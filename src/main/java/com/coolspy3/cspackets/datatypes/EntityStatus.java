package com.coolspy3.cspackets.datatypes;

public enum EntityStatus
{

    RESET_TIMER_OR_RABBIT_JUMP(1), HURT(2), DEAD(3), GOLEM_ARM_THROW(4), TAMING(6), TAMED(
            7), SHAKING_WATER(8), EATING_ACCEPTED(9), EATING_GRASS_OR_TNT_IGNITE(
                    10), HAND_ROSE(11), VILLAGER_MATING(12), VILLAGER_ANGRY(13), VILLAGER_HAPPY(
                            14), WITCH_ANIMATION(15), VILLAGER_TO_ZOMBIE(16), FIREWORK_EXPLODE(
                                    17), ANIMAL_IN_LOVE(
                                            18), RESET_SQUID_ROTATION(19), SPAWN_EXPLOSION(
                                                    20), GUARDIAN_SOUND(21), ENABLE_REDUCED_DEBUG(
                                                            22), DISABLE_REDUCED_DEBUG(23);

    public final byte id;

    private EntityStatus(int id)
    {
        this((byte) id);
    }

    private EntityStatus(byte id)
    {
        this.id = id;
    }

    public Byte getId()
    {
        return id;
    }

    public static EntityStatus withId(byte id)
    {
        for (EntityStatus val : values())
            if (val.id == id) return val;

        return null;
    }

}
