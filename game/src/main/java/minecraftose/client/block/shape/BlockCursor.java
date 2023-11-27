package minecraftose.client.block.shape;

import jpize.util.Disposable;
import jpize.gl.tesselation.GlPrimitive;
import jpize.gl.type.GlType;
import jpize.graphics.mesh.IndexedMesh;
import jpize.gl.vertex.GlVertexAttr;

public class BlockCursor implements Disposable{
    
    public final static BlockCursor SOLID = new SolidBlockCursor();
    public final static BlockCursor GRASS = new GrassBlockCursor();
    
    
    private final IndexedMesh mesh;
    private final float[] vertices;
    private final int[] quadIndices;
    
    public BlockCursor(float[] vertices, int[] indices, int[] quadIndices){
        this.vertices = vertices;
        this.quadIndices = quadIndices;

        this.mesh = new IndexedMesh(new GlVertexAttr(3, GlType.FLOAT));
        this.mesh.getBuffer().setData(vertices);
        this.mesh.getIndexBuffer().setData(indices);
        this.mesh.setMode(GlPrimitive.LINES);
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
