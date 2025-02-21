package minecraftose.test;


import jpize.app.Jpize;
import jpize.app.JpizeApplication;
import jpize.gl.Gl;
import jpize.gl.texture.Texture2D;
import jpize.util.math.FastNoise;
import jpize.util.math.Maths;
import jpize.util.mesh.TextureBatch;
import jpize.util.pixmap.PixmapRGBA;

public class BiomeGenerator extends JpizeApplication {

    private final TextureBatch batch;
    private Texture2D mapTexture;
    
    private final FastNoise
        continentalnessNoise, erosionNoise, peaksValleysNoise, temperatureNoise, humidityNoise, randomNoise;

    public BiomeGenerator(){
        batch = new TextureBatch();

        continentalnessNoise = new FastNoise();
        erosionNoise = new FastNoise();
        peaksValleysNoise = new FastNoise();
        temperatureNoise = new FastNoise();
        humidityNoise = new FastNoise();
        randomNoise = new FastNoise();

        continentalnessNoise.setFrequency(0.002F);
        continentalnessNoise.setFractalType(FastNoise.FractalType.FBM);
        continentalnessNoise.setFractalOctaves(7);

        erosionNoise.setFrequency(0.002F);
        erosionNoise.setFractalType(FastNoise.FractalType.FBM);
        erosionNoise.setFractalOctaves(5);
        randomNoise.setFrequency(1);
    }

    
    public void init(){
        final PixmapRGBA map = new PixmapRGBA(1024, 1024);
        
        for(int x = 0; x < map.getWidth(); x++){
            for(int y = 0; y < map.getHeight(); y++){
                final float continentalness = continentalnessNoise.get(x, y);
                final float erosion = erosionNoise.get(x, y);
                
                
                final float grayscale = Maths.round(Maths.map(erosion, -0.55 * Maths.SQRT2, 0.55 * Maths.SQRT2, 0, 1) * 5) / 5F;
                
                map.setPixel(x, y, grayscale, grayscale, grayscale, 1);
            }
        }
        
        mapTexture = new Texture2D(map);
    }
    
    
    public void render(){
        Gl.clearColorBuffer();
        batch.setup();
        batch.draw(mapTexture, 0, 0, 1280, 1280);
        batch.render();
    }


    public static void main(String[] args) {
        Jpize.create(1280, 720, "Test").build().setApp(new BiomeGenerator());
        Jpize.run();
    }
    
}
