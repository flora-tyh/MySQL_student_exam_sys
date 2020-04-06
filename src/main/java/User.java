public class User {
    private String id;
    private String privilege;
    private String name;

    public User(String id, String privilege, String name) {
        this.id = id;
        this.privilege = privilege;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getPrivilege() {
        return privilege;
    }

    public String getName() {
        return name;
    }
}
