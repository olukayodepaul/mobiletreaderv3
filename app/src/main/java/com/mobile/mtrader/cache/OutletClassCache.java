package com.mobile.mtrader.cache;

import java.util.ArrayList;

public class OutletClassCache {

    ArrayList<Caches> nodes;

    public OutletClassCache(){
        this.nodes = new ArrayList<>();
    }

    public void add(int id,String name){
        nodes.add(new Caches(id,name));
    }

    public void clear() {
        nodes.clear();
    }

    public int size() {
        return nodes.size();
    }

    public int getid(int index) {
        return nodes.get(index).getId();
    }

    public String getName(int index){
        return nodes.get(index).getName();
    }

    public int getIndexById(int id) {
        int index = 0;
        for(Caches nd : nodes){
            if(nd.getId() == id){break;}
            index++;
        }
        return index;
    }

    public int getValueId(String value) {
        int id = 0;
        for(Caches nd : nodes){
            if(nd.getName().equals(value)){
                id=nd.getId();
                break;
            }
        }
        return id;
    }

    class Caches{

        private int id;
        private String name;

        public Caches(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
