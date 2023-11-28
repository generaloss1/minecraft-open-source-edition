package minecraftose.server.worldgen.generator.testnew;

/* For classes/enums that supply infos on how to generate layers */ 

public interface IGenLayerSupplier {

	int getDepthMin();
	int getDepthMax();
	
	int getColor();
	
	int getWeight();
	
	int getSize();
}