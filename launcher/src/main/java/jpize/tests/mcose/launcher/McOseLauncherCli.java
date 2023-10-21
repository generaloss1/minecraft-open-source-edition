package jpize.tests.mcose.launcher;

import jpize.util.io.FastReader;

public class McOseLauncherCli{

    private final McOseClient client;

    public McOseLauncherCli(){
        this.client = new McOseClient();

        final FastReader reader = new FastReader();
        while(!Thread.interrupted()){
            if(reader.hasNext()){
                final String line = reader.nextLine();
                if(line.equals("run")){
                    client.launch();
                }
                if(line.equals("download")){
                    client.downloadJar();
                }
            }
        }
    }

}
