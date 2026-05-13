
package Models;


public class Categories {
    //Creaccion de Variables
    private int id;
    private String name;
    private String Created;
    private String update;

    public Categories() {
    }

    public Categories(int id, String name, String Created, String update) {
        this.id = id;
        this.name = name;
        this.Created = Created;
        this.update = update;
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

    public String getCreated() {
        return Created;
    }

    public void setCreated(String Created) {
        this.Created = Created;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }
    
    
}
