package sassa.searcher;
import kaptainwutax.biomeutils.biome.Biome;
import kaptainwutax.biomeutils.biome.Biomes;
import kaptainwutax.biomeutils.source.BiomeSource;
import kaptainwutax.mcutils.state.Dimension;
import kaptainwutax.mcutils.version.MCVersion;
import sassa.util.Singleton;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;

public class ContinentSearcher {

    //PERIMETER BASED VS AREA BASED

    private static final int Y = 60;

    public boolean getContinent(long worldSeed, int min, int max, int granularity) {
        return getContinent(
                BiomeSource.of(Dimension.OVERWORLD, Singleton.getInstance().getMinecraftVersion(), worldSeed),
                min, max, granularity);
    }
    public static boolean getContinent(BiomeSource bs, int min, int max, int granularity) {
        int area = getArea(bs,new HashSet<Point>(), 0,0,0, granularity, max);
        System.out.println("Area: " + area);
        return area < max && area > min;
    }


    private static int getArea(BiomeSource bs, HashSet<Point> ch, int x, int z, int t, int g, int max) {
        if(ch.contains(new Point(x,z)) || isOcean(bs,x,z, g) || t > max) {
            return 0;
        }
        ch.add(new Point(x,z));
        return 1 + getArea(bs,ch,x + 1, z, t + 1, g, max) + getArea(bs,ch,x - 1, z, t +1, g, max)
                + getArea(bs,ch,x, z + 1, t + 1, g, max) + getArea(bs,ch,x,z - 1, t + 1, g, max);
    }

    private static boolean isOcean(BiomeSource bs, int x, int z, int g) {
        return bs.getBiome(x * g, Y, z * g).getCategory().equals(Biome.Category.OCEAN);
    }
}
