package auto;

public enum YesNoType {

    YES("肯定", 1), NO("否定", 2), ALL("全部", 3);

    private String name;
    private int index;

    private YesNoType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    public static YesNoType getYesNoType(int index) {
        for (YesNoType c : YesNoType.values()) {
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
