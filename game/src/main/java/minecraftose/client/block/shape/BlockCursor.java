package minecraftose.client.block.shape;


import jpize.gl.tesselation.GlPrimitive;
import jpize.gl.type.GlType;
import jpize.gl.vertex.GlVertAttr;
import jpize.util.Disposable;
import jpize.util.mesh.IndexedMesh;

public class BlockCursor implements Disposable {
    
    public final static BlockCursor SOLID = new SolidBlockCursor();
    public final static BlockCursor GRASS = new GrassBlockCursor();
    public final static BlockCursor CACTUS = new CactusBlockCursor();

    
    private final IndexedMesh mesh;
    private final float[] vertices;
    private final int[] quadIndices;
    
    public BlockCursor(float[] vertices, int[] lineIndices, int[] quadIndices){
        this.vertices = vertices;
        this.quadIndices = quadIndices;

        this.mesh = new IndexedMesh(new GlVertAttr(3, GlType.FLOAT));
        this.mesh.setMode(GlPrimitive.LINES);
        this.mesh.vertices().setData(vertices);
        this.mesh.indices().setData(lineIndices);
    }

    public float[] getVertices(){
        return vertices;
    }

    public int[] getQuadIndices(){
        return quadIndices;
    }
    
    
    public void render(){
        mesh.render();
    }
    
    public IndexedMesh getMesh(){
        return mesh;
    }
    
    @Override
    public void dispose(){
        mesh.dispose();
    }
    
}
