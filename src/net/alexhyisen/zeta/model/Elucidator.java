package net.alexhyisen.zeta.model;

import java.util.*;

/**
 * Created by Alex on 2016/9/25.
 * Elucidator includes functions to convert information of same meaning but in different expression
 */
public class Elucidator {
    public static int[][] elucidate(String[] orig){
        int[][] rtn=new int[orig.length][orig[0].length()];
        for(int i=0;i!=rtn.length;i++){
            for(int j=0;j!=rtn[0].length;j++){
                char ch=orig[i].toCharArray()[j];
                rtn[i][j]=ch-'0';
            }
        }
        return rtn;
    }

    public static int[][] elucidate(String orig){
        String[] lines=orig.split("[^01xX]");
        return elucidate(lines);
    }

    private static Set<String> amplify(String orig,int var){
        Set<String> rtn=new HashSet<>();
        if(orig.length()!=var){
            //System.out.print("amplify "+orig+" to ");
            char id='A';
            char[] cs=orig.toCharArray();
            int k=0;

            while(k!=cs.length&&Character.toUpperCase(cs[k])==id){
                id++;
                k++;
            }

            String second=(k==cs.length?"":orig.substring(k, orig.length()));
            /*
            System.out.println(orig.substring(0, k) + id + second+
            " & "+(orig.substring(0, k) + Character.toLowerCase(id) + second));
            */
            rtn.addAll(amplify((orig.substring(0, k) +
                    id +
                    second), var));
            rtn.addAll(amplify((orig.substring(0, k) +
                    Character.toLowerCase(id) +
                    second), var));
        }else{
            rtn.add(orig);
        }
        return rtn;
    }

    public static int[][] illustrate(String[] orig,int var){
        Set<String> lines=new HashSet<>();
        List<String> rtn=new LinkedList<>();
        Set<Position> ps=new HashSet<>();

        for(String str:orig){
            //System.out.println("orig "+str);
            lines.addAll(amplify(str,var));
        }
        for(String line:lines){
            //System.out.println(line);
            char[] array=line.toCharArray();
            int[] value=new int[var/2];
            for(int k=0;k!=array.length;k++){
                if(Character.isUpperCase(array[k])){
                    array[k]='1';
                }
                else if(Character.isLowerCase(array[k])){
                    array[k]='0';
                }else{
                    throw new RuntimeException("input != [:alpha:]");
                }
            }
            //System.out.println(array);
            for(int k=0;k!=var/2;++k)
            {
                if(array[2*k]=='1'){
                    value[k]+=2;
                }
                if(array[2*k+1]=='1'){
                    value[k]+=1;
                }
                if(value[k]==3){
                    value[k]=2;
                }else if(value[k]==2){
                    value[k]=3;
                }
            }
            Position p=new Position(value,new int[]{3,3});
            //System.out.println(p);
            ps.add(p);
        }
        for(int i=0;i!=4;i++){
            String str="";
            for(int j=0;j!=4;j++){
                if(ps.contains(new Position(new int[]{i,j},new int[]{3,3}))){
                    str+='1';
                }else{
                    str+='0';
                }
            }
            rtn.add(str);
        }
        return elucidate(rtn.toArray(new String[rtn.size()]));
    }

    public static int[][] illustrate(String orig,int var){
        String[] lines=orig.split("[^\\p{Alpha}]");
        return illustrate(lines,var);
    }

    public static void main(String[] args){
        //String input="00,01,11,0x";
        //Data data=Data.generateSimple(elucidate(input));

        //String input="AC+ba+bC+aD+CD ";
        //Data data=Data.generateSimple(illustrate(input,4));

        String[] orig=new String[]{"abcd","a"};
        Data data=Data.generateNormal(Elucidator.illustrate(orig[0],4),
                Elucidator.illustrate(orig[1],4));

        data.print();
        for(Area area:data.search()){
            System.out.println(area.toMeaning()+"\t"+area);
        }
        System.out.println();
        for(Area area:data.simplify()){
            System.out.println(area.toMeaning()+"\t"+area);
        }
    }
}
