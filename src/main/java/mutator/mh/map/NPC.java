package mutator.mh.map;

import lombok.Data;

@Data
public class NPC {

    private String npcName;
    private int x;
    private int y;

    public NPC(String npcName , int x ,int y){
        this.npcName = npcName;
        this.x = x;
        this.y = y;
    }
}
