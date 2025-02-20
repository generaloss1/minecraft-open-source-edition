package minecraftose.client.chunk.mesh;

import jpize.util.Disposable;

public class ChunkMeshStack implements Disposable {
    
    private final ChunkMesh[] meshes;

    public ChunkMeshStack(){
        this.meshes = new ChunkMesh[]{
            new ChunkMesh(ChunkMeshType.SOLID),
            new ChunkMesh(ChunkMeshType.CUSTOM),
            new ChunkMesh(ChunkMeshType.TRANSLUCENT)
        };
    }


    public ChunkMesh getSolid(){
        return meshes[0];
    }

    public ChunkMesh getCustom(){
        return meshes[1];
    }

    public ChunkMesh getTranslucent(){
        return meshes[2];
    }


    @Override
    public void dispose(){
        for(ChunkMesh mesh: meshes)
            mesh.dispose();
    }
    
}
