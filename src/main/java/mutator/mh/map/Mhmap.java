package mutator.mh.map;


import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class Mhmap {

    public static Map<String ,Mhmap > ALLMAP = new HashMap<String, Mhmap>();
    private String name;
    private List<NPC> npcList;
    /**
     * 是否能使用飞行符
     */
    private boolean canFly = true;
    /**
     * 是否能使用棋子
     */
    private boolean can77 = true;

    public Mhmap(String name , boolean canFly , boolean can77){
        this.name = name;
        this.canFly =  canFly;
        this.can77 = can77;
    }

    public void initMap(){

        Mhmap mhmapJY = new Mhmap("JY", true , false);
        ALLMAP.put(mhmapJY.getName() , mhmapJY);
    }

    /**
     *
     * 创建长安地图
     */
    public void createCAMap(){
        Mhmap mhmapCA = new Mhmap("CA", true , true);
        NPC npc = new NPC("长安传送人", 100 ,100);
        npc.setNpcName("");
        ALLMAP.put(mhmapCA.getName() , mhmapCA);
    }

}
