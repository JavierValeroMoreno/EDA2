package practica1.src;

import java.io.File;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        TreeMap<String, Player> jDict = new TreeMap<>();
        Player[] list;
        try {
            Scanner reader = new Scanner(new File("src/practica1/data/NbaStats.csv"));
            reader.nextLine();
            while(reader.hasNext()){
                String line = reader.nextLine();
                if(line == "")
                    continue;
                String[] charact = line.split(";");
                int score;
                try{
                    score = (int)(Double.parseDouble(charact[8].replace(',', '.'))  * Double.parseDouble(charact[7].replace(',', '.'))/100);
                }catch (Exception e){
                    score = 0;
                }
                String name = charact[2];
                String team = charact[6];
                if(jDict.containsKey(name)){
                    var j = jDict.get(charact[2]);
                    j.getTeams().add(team);
                    j.setScore((j.getScore()+ score)/2);
                }else{
                    Player aux = new Player(name, team, charact[4], score);
                    jDict.put(name, aux);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        list = jDict.values().toArray(new Player[0]);
        Player p = BestScore(list);
    }
    public static Player BestScore(Player [] list){
        Player p1, p2;
        if(list.length == 1)
            return list[0];
        if(list.length == 2){
            p1 = list[0];
            p2 = list[1];
        }else{
            p1 = BestScore(Arrays.copyOfRange(list,0, list.length/2));
            p2 = BestScore(Arrays.copyOfRange(list,(list.length/2) +1 , list.length));
        }
        return (p1.getScore() > p2.getScore())? p1:p2;
    }
}
