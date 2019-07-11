package sample;

public class TempRestPoint {
    public String itIs;
    public Object[] objects;

    public TempRestPoint(String itIs, Object... object){
        objects = new Object[100];
        this.itIs = itIs;
        int i =0;
        for( ;i<object.length;++i){
            this.objects[i] = object[i];
        }
    }
}
