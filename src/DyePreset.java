/**
 * Created by Talicska on 2014.07.15..
 */

import java.util.Vector;

public class DyePreset {

    String name;
    Vector<DyeParent> dyes = new Vector<DyeParent>();

    public DyePreset(String name, Vector<DyeParent> dyes){
        this.name=name;
        this.dyes=dyes;
    }

    public String getName() {
        return name;
    }

    public Vector<DyeParent> getDyes() {
        return dyes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDyes(Vector<DyeParent> dyes) {
        this.dyes = dyes;
    }

    @Override
    public String toString(){
        return name;
    }

}
