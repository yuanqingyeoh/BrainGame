/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package braingame;

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Jing Chong
 */
public class Neuron {
    private Integer lifetime;
    private Integer connectNum;
    private ArrayList<Synapse> synapseList;
    private boolean visitStatus;
    private ArrayList<Node> nodeList ;
    
    /**A constructor that create an object with 
     * specific lifetime and number of connected node.
     * @param connectNum
     * @param lifetime
     */
    public Neuron(Integer connectNum , Integer lifetime ){
        this.lifetime  = lifetime ;
        this.connectNum = connectNum;
        synapseList = new ArrayList ();
        nodeList = new ArrayList();
    }

    /**Retrieve lifetime  of the neuron.
     * @return the lifetime 
     */
    public Integer getLifetime () {
        return lifetime ;
    }

    /**Set lifetime of the neuron.
     * @param lifetime the lifetime to set
     */
    public void setLifetime(Integer lifetime ) {
        this.lifetime = lifetime ;
    }

    /**Retrieve number of connected node to the neuron.
     * @return the connectNum
     */
    public Integer getConnectNum() {
        return connectNum;
    }

    /**Set number of connected node to the neuron.
     * @param connectNum the connectNum to set
     */
    public void setConnectNum(Integer connectNum) {
        this.connectNum = connectNum;
    }
    
    /**Convert all the info of this node to string
     * @return a string contain all instances in this class
     */
    public String toString(){
        String info = "";
        info += "Number of connected nodes: "+connectNum+"\tLifetime: "+lifetime+"\nConnection:\n";
        if(connectNum!=0)
            for(Synapse ptr: synapseList)
                info += ptr.toString();
        else
            info += "No Connection\n";
        return info;
    }
    
    public void setVisit(boolean status){
        this.visitStatus = status;
    }
    
    public boolean getVisit(){
        return this.visitStatus;
    }
    
    public void addSynapse(int toID, Integer time, int distance){
        if(synapseList.size()==0)
            synapseList.add(new Synapse(toID, time, distance, this));
        else
            for(int i=0; i<synapseList.size(); i++){
                if(synapseList.get(i).getTime().compareTo(time)>=0){
                    if(synapseList.get(i).getTime().compareTo(time)==0){
                        if(synapseList.get(i).getDistance().compareTo(distance)<0){
                            synapseList.add(i+1, new Synapse(toID, time, distance, this));
                            break;
                        }else{
                            synapseList.add(i, new Synapse(toID, time, distance, this));
                            break;
                        }
                    }else{
                        synapseList.add(i, new Synapse(toID, time, distance, this));
                        break;
                    }
                }else if(i == synapseList.size()-1){
                    synapseList.add(new Synapse(toID, time, distance, this));
                    break;
                }
            }
    }
    
    public void removeSynapse(int toID){
        if(containsSynapse(toID)){
            synapseList.remove(getIndexOf(toID));
            this.connectNum--;
        }
        else
            System.out.println("Synapse not found.");
    }
    
    public int getIndexOf(int id){
        for(Synapse ptr: synapseList)
            if(ptr.getID() == id)
                return synapseList.indexOf(ptr);
        return -1;
    }
    
    public boolean containsSynapse(int toID){
        return getIndexOf(toID)!=-1;
    }
    
    public boolean hasNext(int currentNode){
        return getIndexOf(currentNode)<synapseList.size()-1;
    }
    
    public int getNext(int currentNode){
        return synapseList.get(getIndexOf(currentNode)+1).getID();
    }
    
    public int getTimeTo(int toID){
        return synapseList.get(getIndexOf(toID)).getTime();
    }
    
    public int getDistanceTo(int toID){
        return synapseList.get(getIndexOf(toID)).getDistance();
    }
    
    public ArrayList<Synapse> getSynapseList(){
        return this.synapseList;
    }
    
    public int getRandomNext(HashSet<Integer> set){
        //boolean cond = false;
        for(int iterate = 0 ; iterate < synapseList.size() ; iterate ++){
            if(!(set.contains(synapseList.get(iterate).getID()))){
                //cond = true;
                return synapseList.get(iterate).getID();
            }
        }
        /*if(cond){
            Random r = new Random();
            int temp = r.nextInt(synapseList.size());
            while(set.contains(synapseList.get(temp).getID())){
                temp= r.nextInt(synapseList.size());
                System.out.println("fjafs");
            }
            int nextNode=synapseList.get(temp).getID();
            return nextNode;
        }else{
            return -1;
        }*/
        return -1;
    }

    
}