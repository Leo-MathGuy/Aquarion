package aquarion.world.blocks.defense;



import arc.func.Floatf;
import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Lines;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.struct.EnumSet;
import arc.struct.FloatSeq;
import arc.struct.ObjectSet;
import arc.struct.Seq;
import arc.util.Time;
import arc.util.Tmp;
import mindustry.game.Team;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.Block;
import mindustry.world.Tile;
import mindustry.world.meta.BlockFlag;
import mindustry.world.meta.BlockGroup;

import static mindustry.Vars.*;

public class BarricadeAbsorb extends Block {
    boolean tooClose = false;
    public float range = 10;
    public Color baseColor = Pal.accentBack;


    public BarricadeAbsorb(String name) {
        super(name);

        size = 4;
        health = 8000;
        solid = true;
        update = true;
        hasPower = true;
        hasItems = true;
        emitLight = true;
        suppressable = true;
        rotateDraw = false;
        flags = EnumSet.of(BlockFlag.blockRepair);
    }
    @Override
    public void drawPlace(int x, int y, int rotation, boolean valid){
        super.drawPlace(x, y, rotation, valid);

        x *= tilesize;
        y *= tilesize;
        x += offset;
        y += offset;

        Drawf.dashSquare(baseColor, x, y, range * tilesize);
        indexer.eachBlock(player.team(), Tmp.r1.setCentered(x, y, range * tilesize), b -> true, t -> {
            Drawf.selected(t, Tmp.c1.set(baseColor).a(Mathf.absin(4f, 1f)));
        });
    }
    public class BarricadeAbsBuild extends Building {
        // Keeps track of which blocks have been boosted
        final ObjectSet<Building> boosted = new ObjectSet<>();
        //Make sure blocks onlt have Hp and armor added ONCE
        @Override
        public void updateTile(){
            indexer.eachBlock(team, Tmp.r1.setCentered(x, y, range * tilesize), b -> true, b -> {
                if (!boosted.contains(b)) {
                    b.maxHealth += 300f;
                    b.block.armor += 3f;
                    boosted.add(b);
                }
            });
        }

        @Override
        public void draw(){
            super.draw();
        }

        @Override
        public void drawSelect(){
            indexer.eachBlock(player.team(), Tmp.r1.setCentered(x, y, range * tilesize), b -> true, t ->
                    Drawf.selected(t, Tmp.c1.set(baseColor).a(Mathf.absin(4f, 1f)))
            );
            Drawf.dashSquare(baseColor.a(0.7f), x, y, range * tilesize);
        }
    }
}