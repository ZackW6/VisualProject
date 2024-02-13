package Neat_Network;

import java.util.ArrayList;
import java.util.HashSet;
public class RandomHashSet<T> {
    
    HashSet<T> set;
    ArrayList<T> data;

    public RandomHashSet(){
        set  = new HashSet<> ();
        data = new ArrayList<>();
    }

    public boolean contains(T object){
        return set.contains(object);
    }

    public T random_element(){
        if(set.size() > 0){
            return data.get((int)(Math.random() * size()));
        }
        return null;
    }

    private int size(){
        return data.size();
    }

    public void add(T object){
        if(!set.contains(object)){
            set.add(object);
            data.add(object);
        }
    }

    public void clear(){
        set.clear();
        data.clear();
    }

    public T get(int index){
        if(index < 0 || index >= size()) return null;
        return data.get(index);
    }

    public T get(T template){
        return data.get(data.indexOf(template));
    }

    public void remove(int index){
        if(index < 0 || index >= size()) return ;
        set.remove(data.get(index));
        data.remove (index);

    }
    public ArrayList<T> getData(){
        return data;
    }
}
