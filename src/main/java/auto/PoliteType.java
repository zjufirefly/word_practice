package auto;

public enum PoliteType {

    NORMAL("简体", 1), POLITE("敬体", 2), ALL("全部", 3);

    private String name;
    private int index;

    private PoliteType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static PoliteType getPoliteType(int index) {
        for (PoliteType c : PoliteType.values()) {
            if (c.index == index) {
                return c;
            }
        }
        return null;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
