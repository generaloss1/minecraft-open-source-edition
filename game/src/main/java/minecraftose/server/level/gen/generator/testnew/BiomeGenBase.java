package minecraftose.server.level.gen.generator.testnew;

import minecraftose.client.block.ClientBlocks;

import java.awt.*;

public class BiomeGenBase{

    public static final BiomeGenBase[] biomeList = new BiomeGenBase[256];


    public static final BiomeGenBase ocean = (new BiomeGenBase(0)).setColor(112)
        .setBiomeName("Ocean")
        .setMinMaxHeight(-1.0F, 0.4F);
    public static final BiomeGenBase plains = (new BiomeGenBase(1)).setColor(9286496)
        .setBiomeName("Plains")
        .setTemperatureRainfall(0.8F, 0.4F);
    public static final BiomeGenBase desert = (new BiomeGenBase(2)).setColor(16421912)
        .setBiomeName("Desert")
        .setDisableRain()
        .setTemperatureRainfall(2.0F, 0.0F)
        .setMinMaxHeight(0.1F, 0.2F);
    public static final BiomeGenBase extremeHills = (new BiomeGenBase(3)).setColor(6316128)
        .setBiomeName("Extreme Hills")
        .setMinMaxHeight(0.2F, 1.3F)
        .setTemperatureRainfall(0.2F, 0.3F);
    public static final BiomeGenBase forest = (new BiomeGenBase(4)).setColor(353825)
        .setBiomeName("Forest")
        .func_4124_a(5159473)
        .setTemperatureRainfall(0.7F, 0.8F);
    public static final BiomeGenBase taiga = (new BiomeGenBase(5)).setColor(747097)
        .setBiomeName("Taiga")
        .func_4124_a(5159473)
        .setEnableSnow()
        .setTemperatureRainfall(0.05F, 0.8F)
        .setMinMaxHeight(0.1F, 0.4F);
    public static final BiomeGenBase swampland = (new BiomeGenBase(6)).setColor(522674)
        .setBiomeName("Swampland")
        .func_4124_a(9154376)
        .setMinMaxHeight(-0.2F, 0.1F)
        .setTemperatureRainfall(0.8F, 0.9F);
    public static final BiomeGenBase river = (new BiomeGenBase(7)).setColor(255)
        .setBiomeName("River")
        .setMinMaxHeight(-0.5F, 0.0F);
    public static final BiomeGenBase hell = (new BiomeGenBase(8)).setColor(16711680)
        .setBiomeName("Hell")
        .setDisableRain()
        .setTemperatureRainfall(2.0F, 0.0F);

    public static final BiomeGenBase sky = (new BiomeGenBase(9)).setColor(8421631).setBiomeName("Sky").setDisableRain();
    public static final BiomeGenBase frozenOcean = (new BiomeGenBase(10)).setColor(9474208)
        .setBiomeName("FrozenOcean")
        .setEnableSnow()
        .setMinMaxHeight(-1.0F, 0.5F)
        .setTemperatureRainfall(0.0F, 0.5F);
    public static final BiomeGenBase frozenRiver = (new BiomeGenBase(11)).setColor(10526975)
        .setBiomeName("FrozenRiver")
        .setEnableSnow()
        .setMinMaxHeight(-0.5F, 0.0F)
        .setTemperatureRainfall(0.0F, 0.5F);
    public static final BiomeGenBase icePlains = (new BiomeGenBase(12)).setColor(16777215)
        .setBiomeName("Ice Plains")
        .setEnableSnow()
        .setTemperatureRainfall(0.0F, 0.5F);
    public static final BiomeGenBase iceMountains = (new BiomeGenBase(13)).setColor(10526880)
        .setBiomeName("Ice Mountains")
        .setEnableSnow()
        .setMinMaxHeight(0.2F, 1.2F)
        .setTemperatureRainfall(0.0F, 0.5F);
    public static final BiomeGenBase mushroomIsland = (new BiomeGenBase(14)).setColor(16711935)
        .setBiomeName("MushroomIsland")
        .setTemperatureRainfall(0.9F, 1.0F)
        .setMinMaxHeight(0.2F, 1.0F);
    public static final BiomeGenBase mushroomIslandShore = (new BiomeGenBase(15)).setColor(10486015)
        .setBiomeName("MushroomIslandShore")
        .setTemperatureRainfall(0.9F, 1.0F)
        .setMinMaxHeight(-1.0F, 0.1F);

    public static final BiomeGenBase beach = (new BiomeGenBase(16)).setColor(16440917)
        .setBiomeName("Beach")
        .setTemperatureRainfall(0.8F, 0.4F)
        .setMinMaxHeight(0.0F, 0.1F);

    public static final BiomeGenBase desertHills = (new BiomeGenBase(17)).setColor(13786898)
        .setBiomeName("DesertHills")
        .setDisableRain()
        .setTemperatureRainfall(2.0F, 0.0F)
        .setMinMaxHeight(0.2F, 0.7F);

    public static final BiomeGenBase forestHills = (new BiomeGenBase(18)).setColor(2250012)
        .setBiomeName("ForestHills")
        .func_4124_a(5159473)
        .setTemperatureRainfall(0.7F, 0.8F)
        .setMinMaxHeight(0.2F, 0.6F);

    public static final BiomeGenBase taigaHills = (new BiomeGenBase(19)).setColor(1456435)
        .setBiomeName("TaigaHills")
        .setEnableSnow()
        .func_4124_a(5159473)
        .setTemperatureRainfall(0.05F, 0.8F)
        .setMinMaxHeight(0.2F, 0.7F);

    public static final BiomeGenBase extremeHillsEdge = (new BiomeGenBase(20)).setColor(7501978)
        .setBiomeName("Extreme Hills Edge")
        .setMinMaxHeight(0.2F, 0.8F)
        .setTemperatureRainfall(0.2F, 0.3F);

    public static final BiomeGenBase jungle = (new BiomeGenBase(21)).setColor(5470985)
        .setBiomeName("Jungle")
        .func_4124_a(5470985)
        .setTemperatureRainfall(1.2F, 0.9F)
        .setMinMaxHeight(0.2F, 0.4F);
    public static final BiomeGenBase jungleHills = (new BiomeGenBase(22)).setColor(2900485)
        .setBiomeName("JungleHills")
        .func_4124_a(5470985)
        .setTemperatureRainfall(1.2F, 0.9F)
        .setMinMaxHeight(1.8F, 0.2F);


    public String biomeName;
    public int color;
    /**
     * The block expected to be on the top of this biome
     */
    public byte topBlock;
    /**
     * The block to fill spots in when not on the top
     */
    public byte fillerBlock;
    public int field_6502_q;
    /**
     * The minimum height of this biome. Default 0.1.
     */
    public float minHeight;
    /**
     * The maximum height of this biome. Default 0.3.
     */
    public float maxHeight;
    /**
     * The temperature of this biome.
     */
    public float temperature;
    /**
     * The rainfall in this biome.
     */
    public float rainfall;
    /**
     * Color tint applied to water depending on biome
     */
    public int waterColorMultiplier;
    /**
     * Set to true if snow is enabled for this biome.
     */
    private boolean enableSnow;
    /**
     * Is true (default) if the biome support rain (desert and nether can't have rain)
     */
    private boolean enableRain;
    /**
     * The id number to this biome, and its index in the biomeList array.
     */
    public final int biomeID;

    protected BiomeGenBase(int ID){
        this.topBlock = ClientBlocks.GRASS_BLOCK.getID();
        this.fillerBlock = ClientBlocks.DIRT.getID();
        this.field_6502_q = 5169201;
        this.minHeight = 0.1F;
        this.maxHeight = 0.3F;
        this.temperature = 0.5F;
        this.rainfall = 0.5F;
        this.waterColorMultiplier = 16777215;
        this.enableRain = true;
        this.biomeID = ID;
        biomeList[ID] = this;
    }

    /**
     * Sets the temperature and rainfall of this biome.
     */
    private BiomeGenBase setTemperatureRainfall(float par1, float par2){
        if(par1 > 0.1F && par1 < 0.2F){
            throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
        }else{
            this.temperature = par1;
            this.rainfall = par2;
            return this;
        }
    }

    /**
     * Sets the minimum and maximum height of this biome. Seems to go from -2.0 to 2.0.
     */
    private BiomeGenBase setMinMaxHeight(float par1, float par2){
        this.minHeight = par1;
        this.maxHeight = par2;
        return this;
    }

    /**
     * Disable the rain for the biome.
     */
    private BiomeGenBase setDisableRain(){
        this.enableRain = false;
        return this;
    }

    /**
     * sets enableSnow to true during biome initialization. returns BiomeGenBase.
     */
    protected BiomeGenBase setEnableSnow(){
        this.enableSnow = true;
        return this;
    }

    protected BiomeGenBase setBiomeName(String par1Str){
        this.biomeName = par1Str;
        return this;
    }

    protected BiomeGenBase func_4124_a(int par1){
        this.field_6502_q = par1;
        return this;
    }

    protected BiomeGenBase setColor(int par1){
        this.color = par1;
        return this;
    }

    /**
     * takes temperature, returns color
     */
    public int getSkyColorByTemp(float par1){
        par1 /= 3.0F;
        if(par1 < -1.0F){
            par1 = -1.0F;
        }
        if(par1 > 1.0F){
            par1 = 1.0F;
        }
        return Color.getHSBColor(0.62222224F - par1 * 0.05F, 0.5F + par1 * 0.1F, 1.0F).getRGB();
    }

    /**
     * Returns true if the biome have snowfall instead a normal rain.
     */
    public boolean getEnableSnow(){
        return this.enableSnow;
    }

    /**
     * Return true if the biome supports lightning bolt spawn, either by have the bolts enabled and have rain enabled.
     */
    public boolean canSpawnLightningBolt(){
        return !this.enableSnow && this.enableRain;
    }

    /**
     * Checks to see if the rainfall level of the biome is extremely high
     */
    public boolean isHighHumidity(){
        return this.rainfall > 0.85F;
    }

    /**
     * returns the chance a creature has to spawn.
     */
    public float getSpawningChance(){
        return 0.1F;
    }

    /**
     * Gets an integer representation of this biome's rainfall
     */
    public final int getIntRainfall(){
        return (int) (this.rainfall * 65536.0F);
    }

    /**
     * Gets an integer representation of this biome's temperature
     */
    public final int getIntTemperature(){
        return (int) (this.temperature * 65536.0F);
    }

    /**
     * Gets a floating point representation of this biome's rainfall
     */
    public final float getFloatRainfall(){
        return this.rainfall;
    }

    /**
     * Gets a floating point representation of this biome's temperature
     */
    public final float getFloatTemperature(){
        return this.temperature;
    }

}