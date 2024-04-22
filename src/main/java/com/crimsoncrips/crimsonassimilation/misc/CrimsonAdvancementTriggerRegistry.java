package com.crimsoncrips.crimsonassimilation.misc;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.resources.ResourceLocation;

public class CrimsonAdvancementTriggerRegistry {

    public static final CrimsonAdvancementTrigger WARDEN_HELL = new CrimsonAdvancementTrigger(new ResourceLocation("crimsonassimilation:warden_hell"));

    public static final CrimsonAdvancementTrigger WITHER_HELL = new CrimsonAdvancementTrigger(new ResourceLocation("crimsonassimilation:wither_hell"));

    public static final CrimsonAdvancementTrigger XPERIENCED = new CrimsonAdvancementTrigger(new ResourceLocation("crimsonassimilation:xperienced"));



    public static void init(){
        CriteriaTriggers.register(WARDEN_HELL);
        CriteriaTriggers.register(WITHER_HELL);
        CriteriaTriggers.register(XPERIENCED);

    }

}
